package top.lshaci.dt.rmfc.server.task;

import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;
import top.lshaci.dt.rmfc.common.model.DtMessage;
import top.lshaci.dt.rmfc.server.service.DtMessageService;
import top.lshaci.dt.rmfc.server.utils.ThreadUtils;

@Component
public class DtMessageTask {
	
	private static final Logger logger = LoggerFactory.getLogger(DtMessageTask.class);
	
	private static final CloseableHttpClient client = HttpClients.createDefault();
	
	@Autowired
	private DtMessageService dtMessageService;
	
	/**
	 * 确认消息(确认系统业务是否完成)
	 */
	@Scheduled(cron = "0 0/3 * * * ?")
	public void confirmMessage() {
		List<DtMessage> messages = dtMessageService.findByStatus(MessageStatus.WAITSEND);
		logger.debug("There are {} messages need to confirm.", messages.size());
		if (!messages.isEmpty()) {
			for (DtMessage message : messages) {
				ThreadUtils.execute(new Runnable() {
					
					@Override
					public void run() {
						String uri = message.getConfirmUrl() + "?" + message.getConfirmParam();
						HttpGet httpget = new HttpGet(uri);
						try (CloseableHttpResponse response = client.execute(httpget);) {
							String body = EntityUtils.toString(response.getEntity());
							if ("TRUE".equals(body.toUpperCase())) {
								dtMessageService.confirmSend(message.getId());
							} else {
								dtMessageService.delete(message.getId());
							}
						} catch (Exception e) {
							logger.error("Confirm message(" + message.getId() + ") failed!", e);
						}
					}
					
				});
			}
		}
	}
	
	/**
	 * 重新发送消息(未接收成功的消息, 状态为发送中的消息)
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void resendMessage() {
		List<DtMessage> messages = dtMessageService.findByStatus(MessageStatus.SENDING);
		logger.debug("There are {} messages need to resend.", messages.size());
		if (!messages.isEmpty()) {
			for (DtMessage message : messages) {
				ThreadUtils.execute(new Runnable() {
					
					@Override
					public void run() {
						if (message.getCurrentRetry() < message.getRetries()) {
							dtMessageService.resend(message.getId());
						} else {
							message.setStatus(MessageStatus.DEAD);
							
							dtMessageService.updateMessage(message);
						}
					}
					
				});
			}
		}
	}
	

}
