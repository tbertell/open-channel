package com.github.tbertell.openchannel.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventPublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisher.class);
	
	public void publishEvent(Exchange exchange) {
		Long responseTime = (Long) exchange.getIn().getHeader("responseTime");
		String channelId = (String) exchange.getIn().getHeader("channelId");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("responseTime", responseTime.toString());
		params.put("channelId", channelId);
	
		LOGGER.info("Here we are " +responseTime);
	}
}
