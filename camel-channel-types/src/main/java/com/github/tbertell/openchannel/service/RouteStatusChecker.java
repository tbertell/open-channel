package com.github.tbertell.openchannel.service;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteStatusChecker {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteStatusChecker.class);

	public void waitIfRouteStopped(String symbol, Exchange exchange) {

		int index = symbol.indexOf("_");
		String correlationId = null;
		if (index > 0 ) {
			correlationId = symbol.substring(index + 1);
			symbol = symbol.substring(0, index);
		}
		
		while (ContextShutdownEventNotifier.getShouldWait()) {
			try {
				if (correlationId != null) {
					LOGGER.info("CID " +correlationId +" Waiting!");
				} else {
					LOGGER.debug("Waiting!");
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.debug("InterruptedException " + e);
			}
		}
	}
}
