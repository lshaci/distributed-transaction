package top.lshaci.dt.rmfc.server.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ActiveMQProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducer.class);
	
	private static final Gson gson = new Gson();
	
	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
	private JmsMessagingTemplate jmsTemplate;
	

	/**
	 * 根据队列名称发送消息
	 * 
	 * @param queueName		队列名称
	 * @param message		消息内容
	 */
	public void sendMessage(String queueName, final Object message) {
		jmsTemplate.setDefaultDestinationName(queueName);
		jmsTemplate.convertAndSend(gson.toJson(message));
		logger.debug("Send MQ message success!");
	}

}
