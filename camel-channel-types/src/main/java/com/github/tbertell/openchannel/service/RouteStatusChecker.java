package com.github.tbertell.openchannel.service;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteStatusChecker {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteStatusChecker.class);

	public void waitIfRouteStopped(Exchange exchange) {

		boolean waited = false;
		while (ContextShutdownEventNotifier.getShouldWait()) {
			try {
				LOGGER.error("Waiting!!!!!!!!!!!!!!!!!!1");
				waited = true;
				Thread.sleep(200);
			} catch (InterruptedException e) {
				LOGGER.error("InterruptedException " + e);
			}
		}

		LOGGER.error("Waited " + waited);
	}
}
