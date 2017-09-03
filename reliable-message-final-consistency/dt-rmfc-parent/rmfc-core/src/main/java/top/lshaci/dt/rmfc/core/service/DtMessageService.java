package top.lshaci.dt.rmfc.core.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import top.lshaci.dt.rmfc.common.exception.DtMessageException;
import top.lshaci.dt.rmfc.common.model.DtMessage;

/**
 * 事务消息业务接口
 * 
 * @author lshaci
 */
@FeignClient(value = "PROVIDER-DT-MESSAGE-SERVER")
public interface DtMessageService {
	
	String PATH_PREFIX = "/dtMessage/";
	
	/**
	 * 预发送消息
	 * 
	 * @param message	消息实例
	 */
	@RequestMapping(value = PATH_PREFIX + "preSend", method = RequestMethod.POST)
	void preSend(@RequestBody DtMessage message) throws DtMessageException;
	
	/**
	 * 根据messageId确认发送消息
	 * 
	 * @param messageId	消息id
	 */
	@RequestMapping(value = PATH_PREFIX + "confirmSend/{messageId}", method = RequestMethod.POST)
	void confirmSend(@PathVariable("messageId") String messageId) throws DtMessageException;
	
	/**
	 * 根据messageId删除消息
	 * 
	 * @param messageId	消息id
	 */
	@RequestMapping(value = PATH_PREFIX + "delete/{messageId}", method = RequestMethod.POST)
	void delete(@PathVariable("messageId") String messageId) throws DtMessageException;
	
	/**
	 * 确定接收到消息
	 * 
	 * @param messageId	消息id
	 */
	@RequestMapping(value = PATH_PREFIX + "received/{messageId}", method = RequestMethod.POST)
	void received(@PathVariable("messageId") String messageId) throws DtMessageException;
	
	/**
	 * 直接发送消息
	 * 
	 * @param message	消息实例
	 */
	@RequestMapping(value = PATH_PREFIX + "directSend", method = RequestMethod.POST)
	void directSend(@RequestBody DtMessage message) throws DtMessageException;
	
}
