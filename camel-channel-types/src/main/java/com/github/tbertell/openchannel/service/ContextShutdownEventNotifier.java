package com.github.tbertell.openchannel.service;

import java.util.EventObject;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.management.EventNotifierSupport;
import org.apache.camel.management.event.CamelContextStartedEvent;
import org.apache.camel.management.event.CamelContextStoppingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextShutdownEventNotifier extends EventNotifierSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextShutdownEventNotifier.class);

	private static Boolean shouldWait = Boolean.FALSE;
	
	private static ConcurrentHashMap<String, Boolean> routeStatuses = new ConcurrentHashMap<String, Boolean>();

	public void notify(EventObject event) throws Exception {

		if (event instanceof CamelContextStoppingEvent) {
			CamelContextStoppingEvent stopEvent = (CamelContextStoppingEvent) event;
			stopEvent.getSource();
			// TODO add handling
			shouldWait = Boolean.TRUE;
			LOGGER.error("Should wait true!");
		} else if (event instanceof CamelContextStartedEvent) {
			shouldWait = Boolean.FALSE;
			LOGGER.error("Should wait changed to false!");
		}

	}

	public static Boolean shouldWait(String routeId) {
		// implement me
		return Boolean.FALSE;
	}
	public boolean isEnabled(EventObject event) {
		if (event instanceof CamelContextStoppingEvent || event instanceof CamelContextStartedEvent) {
			return true;
		} else {
			return false;
		}
	}

	protected void doStart() throws Exception {
		// noop
	}

	protected void doStop() throws Exception {
		// noop
	}

	public synchronized static Boolean getShouldWait() {
		return shouldWait;
	}

}
