package com.github.tbertell.openchannel.listener;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import com.github.tbertell.openchannel.AdaptationManager;

/**
 * 
 * Basic JMS-listener.
 *
 */
@Component
public class QueueListener implements MessageListener {

	@Autowired
	AdaptationManager adaptationManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);

	public void onMessage(final Message message) {

		String channelId = null;

		if (message instanceof MapMessage) {
			final MapMessage mapMessage = (MapMessage) message;
			SimpleMessageConverter converter = new SimpleMessageConverter();

			try {
				Map<String, String> params = (Map<String, String>) converter.fromMessage(mapMessage);
				channelId = params.get("channelId");

				LOGGER.debug("Received message with channelId " + channelId);

				adaptationManager.reconfigure(channelId, params);
			} catch (MessageConversionException e) {
				LOGGER.error("JMS message conversion failed for channel " + channelId, e);
			} catch (JMSException e) {
				LOGGER.error("JMS message conversion failed for channel " + channelId, e);
			}
		}
	}
}