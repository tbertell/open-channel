package com.github.tbertell.openchannel.listener;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import com.github.tbertell.openchannel.AdaptationPolicyManager;

@Component
public class QueueListener implements MessageListener {
	
	public void onMessage(final Message message) {

		if (message instanceof MapMessage) {
			final MapMessage mapMessage = (MapMessage) message;
			SimpleMessageConverter converter = new SimpleMessageConverter();
			try {
				Map<String, String> params = (Map<String, String>) converter.fromMessage(mapMessage);
				String channelId = params.get("channelId");
				
				System.out.println("Received message with channelId" +channelId);
				
				AdaptationPolicyManager reconfManager = new AdaptationPolicyManager();
				reconfManager.reconfigure(channelId, params);
			} catch (MessageConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}