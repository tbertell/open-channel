package com.github.tbertell.openchannel.service;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteStatusChecker {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteStatusChecker.class);

	private String routeId;
	
	public void waitIfRouteStopped(Exchange exchange) {
				
		while (ContextEventNotifier.shouldWait(routeId)) {
			try {
				LOGGER.info("Waiting for route start.");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.error("InterruptedException " + e);
			}
		}
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
}
