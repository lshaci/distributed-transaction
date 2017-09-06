package top.lshaci.dt.rmfc.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.lshaci.validate.utils.ValidateUtils;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;
import top.lshaci.dt.rmfc.common.exception.DtMessageException;
import top.lshaci.dt.rmfc.common.model.DtMessage;
import top.lshaci.dt.rmfc.server.mapper.DtMessageMapper;
import top.lshaci.dt.rmfc.server.mq.producer.ActiveMQProducer;
import top.lshaci.dt.rmfc.server.service.DtMessageService;

@Service
@Transactional(
			isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, 
			rollbackFor = Exception.class, transactionManager = "transactionManager"
		)
public class DtMessageServiceImpl implements DtMessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(DtMessageServiceImpl.class);
	
	private static final String SEND_SUCCESSED = "Send message({}) successed.";
	
	@Autowired
	private DtMessageMapper mapper;
	
	@Autowired
	private ActiveMQProducer producer;
	
	@PostConstruct
	private void createTable() {
		mapper.createTable();
		logger.debug("Create table successed.");
	}

	@Override
	public void preSend(DtMessage message) throws DtMessageException {
		validateMessage(message);
		
		if (mapper.save(message) != 1) {
			logger.error("Save this message(" + message.getId() + ") failed!");
			throw new DtMessageException("Save this message(" + message.getId() + ")  failed!");
		}
		logger.debug("Save this message({}) successed.", message.getId());
	}

	@Override
	public void confirmSend(String messageId) throws DtMessageException {
		DtMessage message = findMessageById(messageId);
		
		message.setStatus(MessageStatus.SENDING);
		message.setSendTime(new Date());
		
		updateMessage(message);
		
		producer.sendMessage(message.getQueueName(), message);
		
		logger.debug(SEND_SUCCESSED, messageId);
	}
	
	@Override
	public void resend(String messageId) throws DtMessageException {
		DtMessage message = findMessageById(messageId);
		
		message.addRetry();
		message.setSendTime(new Date());
		
		updateMessage(message);
		
		producer.sendMessage(message.getQueueName(), message);
		
		logger.debug(SEND_SUCCESSED, messageId);
	}

	@Override
	public void directSend(DtMessage message) throws DtMessageException {
		validateMessage(message);

		producer.sendMessage(message.getQueueName(), message);

		logger.debug(SEND_SUCCESSED, message.getId());
	}
	
	@Override
	public void received(String messageId) throws DtMessageException {
		DtMessage message = findMessageById(messageId);
		
		message.setReceivedTime(new Date());
		message.setStatus(MessageStatus.SENT);
		
		updateMessage(message);
	}

	@Override
	public void delete(String messageId) throws DtMessageException {
		findMessageById(messageId);
		
		if (mapper.delete(messageId) != 1) {
			logger.error("Delete this message(" +messageId + ") failed!");
			throw new DtMessageException("Delete this message(" +messageId + ") failed!");
		}
		
		logger.debug("Delete this message({}) successed!", messageId);
	}
	
	/**
	 * 更新数据库中的消息
	 * 
	 * @param message	需要更新的消息
	 * @throws DtMessageException	更新失败后抛出
	 */
	@Override
	public void updateMessage(DtMessage message) throws DtMessageException {
		if (mapper.update(message) != 1) {
			logger.error("Update this message(" + message.getId() + ") failed!");
			throw new DtMessageException("Update this message(" + message.getId() + ")  failed!");
		}
	}
	
	@Override
	public List<DtMessage> findByStatus(MessageStatus status) throws DtMessageException {
		if (status == null) {
			logger.error("Message status is null!");
			throw new DtMessageException("Message status is null!");
		}
		
		return mapper.findByStatus(status);
	}
	
	/**
	 * 根据消息id查询消息
	 * 
	 * @param messageId		需要判断的消息id
	 * @throws DtMessageException 	消息id为empty或者消息为null时抛出
	 */
	private DtMessage findMessageById(String messageId) throws DtMessageException {
		if (StringUtils.isEmpty(messageId)) {
			logger.error("This messageId(" + messageId + ") is empty!");
			throw new DtMessageException("This messageId(" + messageId + ") is empty!");
		}
		
		DtMessage message = mapper.find(messageId);
		
		if (message == null) {
			logger.error("This message(" + messageId + ") is null!");
			throw new DtMessageException("This message(" + messageId + ") is null!");
		}
		
		return message;
	}
	
	/**
	 * 验证消息
	 * 
	 * @param message		需要验证的消息
	 * @throws DtMessageException	message验证不通过时抛出
	 */
	private void validateMessage(DtMessage message) throws DtMessageException {
		if (!ValidateUtils.validate(message)) {
			logger.error(ValidateUtils.detailValidate(message).toString());
			throw new DtMessageException("This message not validated!");
		}
	}
	
}
