package top.lshaci.dt.rmfc.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import top.lshaci.dt.rmfc.common.model.DtMessage;
import top.lshaci.dt.rmfc.server.service.DtMessageService;


/**
 * 对外暴露的接口
 * 
 * @author lshaci
 */
@RestController
public class DtMessageController {
	
	@Autowired
	private DtMessageService dtMessageService;
	
	/**
	 * 预发送消息
	 * 
	 * @param message	消息实例
	 */
	@PostMapping("preSend")
	public void preSend(@RequestBody DtMessage message) {
		dtMessageService.preSend(message);
	}
	
	/**
	 * 根据messageId确认发送消息
	 * 
	 * @param messageId	消息id
	 */
	@PostMapping("confirmSend/{messageId}")
	public void confirmSend(@PathVariable("messageId") String messageId) {
		dtMessageService.confirmSend(messageId);
	}
	
	/**
	 * 根据messageId删除消息
	 * 
	 * @param messageId	消息id
	 */
	@PostMapping("delete/{messageId}")
	public void delete(@PathVariable("messageId") String messageId) {
		dtMessageService.delete(messageId);
	}
	
	/**
	 * 确定接收到消息
	 * 
	 * @param messageId	消息id
	 */
	@PostMapping("received/{messageId}")
	public void received(@PathVariable("messageId") String messageId) {
		dtMessageService.received(messageId);
	}
	
	/**
	 * 直接发送消息
	 * 
	 * @param message	消息实例
	 */
	@PostMapping("directSend")
	public void directSend(@RequestBody DtMessage message) {
		dtMessageService.directSend(message);
	}

}
