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
				LOGGER.debug("Waiting!!!!!!!!!!!!!!!!!!1");
				waited = true;
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.debug("InterruptedException " + e);
			}
		}

		LOGGER.debug("Waited " + waited);
	}
}
