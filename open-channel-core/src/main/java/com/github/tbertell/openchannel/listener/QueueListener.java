package com.github.tbertell.openchannel.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class QueueListener implements MessageListener {
	public void onMessage(final Message message) {

		if (message instanceof MapMessage) {
			final MapMessage mapMessage = (MapMessage) message;
			try {
				System.out.println(mapMessage.getString("channelId"));
			} catch (final JMSException e) {
				e.printStackTrace();
			}
		}
	}
}