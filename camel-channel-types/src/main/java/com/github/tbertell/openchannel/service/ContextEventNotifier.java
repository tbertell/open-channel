package com.github.tbertell.openchannel.service;

import java.util.EventObject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.Route;
import org.apache.camel.blueprint.BlueprintCamelContext;
import org.apache.camel.management.EventNotifierSupport;
import org.apache.camel.management.event.CamelContextStartedEvent;
import org.apache.camel.management.event.CamelContextStoppingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Receives Camel contexts events and collects information on route states.
 *
 */
public class ContextEventNotifier extends EventNotifierSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextEventNotifier.class);

	private static ConcurrentHashMap<String, Boolean> routeStates = new ConcurrentHashMap<String, Boolean>();

	public void notify(EventObject event) throws Exception {

		if (event instanceof CamelContextStoppingEvent) {
			CamelContextStoppingEvent stopEvent = (CamelContextStoppingEvent) event;

			BlueprintCamelContext context = (BlueprintCamelContext) stopEvent.getSource();
			List<Route> routes = context.getRoutes();
			updateRouteState(routes, Boolean.TRUE);

		} else if (event instanceof CamelContextStartedEvent) {
			CamelContextStartedEvent startEvent = (CamelContextStartedEvent) event;
			BlueprintCamelContext context = (BlueprintCamelContext) startEvent.getSource();

			List<Route> routes = context.getRoutes();
			updateRouteState(routes, Boolean.FALSE);
		}

	}

	private void updateRouteState(List<Route> routes, Boolean stopping) {
		for (Route route: routes) {
			if (stopping) {
				LOGGER.info("Route id: " +route.getId() +" stopping.");
			} else {
				LOGGER.info("Route id: " +route.getId() +" started.");
			}
			routeStates.put(route.getId(), stopping);
		}
	}


	/**
	 * Returns true if route is shutting down.
	 * @param routeId
	 * @return
	 */
	public synchronized static Boolean shouldWait(String routeId) {
		Boolean shouldWait = routeStates.get(routeId);
		if (shouldWait != null) {
			return shouldWait;
		} else {
			return Boolean.FALSE;
		}
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

}
