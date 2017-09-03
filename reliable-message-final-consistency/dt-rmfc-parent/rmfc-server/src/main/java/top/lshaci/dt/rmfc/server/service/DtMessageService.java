package top.lshaci.dt.rmfc.server.service;

import java.util.List;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;
import top.lshaci.dt.rmfc.common.exception.DtMessageException;
import top.lshaci.dt.rmfc.common.model.DtMessage;

/**
 * 事务消息业务接口
 * 
 * @author lshaci
 */
public interface DtMessageService {
	
	/**
	 * 预发送消息
	 * 
	 * @param message	消息实例
	 * 
	 * @throws DtMessageException
	 */
	void preSend(DtMessage message) throws DtMessageException;
	
	/**
	 * 根据messageId确认发送消息
	 * 
	 * @param messageId	消息id
	 * 
	 * @throws DtMessageException
	 */
	void confirmSend(String messageId) throws DtMessageException;
	
	/**
	 * 根据messageId再次发送消息
	 * 
	 * @param messageId	消息id
	 * @throws DtMessageException
	 */
	void resend(String messageId) throws DtMessageException;
	
	/**
	 * 直接发送消息
	 * 
	 * @param message	消息实例
	 * 
	 * @throws DtMessageException
	 */
	void directSend(DtMessage message) throws DtMessageException;
	
	/**
	 * 确定接收到消息
	 * 
	 * @param messageId	消息id
	 * 
	 * @throws DtMessageException
	 */
	void received(String messageId) throws DtMessageException;
	
	/**
	 * 根据messageId删除消息
	 * 
	 * @param messageId	消息id
	 * 
	 * @throws DtMessageException
	 */
	void delete(String messageId) throws DtMessageException;
	
	/**
	 * 更新消息
	 * 
	 * @param message	消息实例
	 * 
	 * @throws DtMessageException
	 */
	void updateMessage(DtMessage message) throws DtMessageException;
	
	/**
	 * 根据消息状态查询消息集合
	 * 
	 * @param status	消息状态
	 * @return
	 */
	List<DtMessage> findByStatus(MessageStatus status) throws DtMessageException;

}
