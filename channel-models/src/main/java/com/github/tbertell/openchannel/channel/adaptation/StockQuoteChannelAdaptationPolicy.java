package com.github.tbertell.openchannel.channel.adaptation;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;

public class StockQuoteChannelAdaptationPolicy implements AdaptationPolicy<StockQuoteChannelModel> {

	private static long lastChanged = 0;
	private static int counter = 1;
	private long stickyTime = 5000;

	private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteChannelAdaptationPolicy.class);

	/**
	 * There are three different cases: 1. response time is below limit and
	 * primary service provider is used -> no action, 2. response time is below
	 * limit and secondary service provider is used -> switch back to primary,
	 * 3. response time is above limit and primary service provider is used ->
	 * switch to secondary.
	 */
	@Override
	public StockQuoteChannelModel reconfigure(Map<String, String> params, StockQuoteChannelModel model) {

		StockQuoteChannelModel newModel = new StockQuoteChannelModel();

		String responseTime = params.get("responseTime");
		if (responseTime != null) {
			Long rt = Long.valueOf(responseTime);

			// check if reconfiguration is needed
			LOGGER.debug("rt on " + rt + " limit on " + model.getResponseTimeLimit() + " sp on "
					+ model.getServiceProvider() + " numero " + counter);

			counter++;
			if (rt > model.getResponseTimeLimit().longValue()
					&& StockQuoteServiceProvider.PRIMARY.equals(model.getServiceProvider())) {
				newModel.setServiceProvider(StockQuoteServiceProvider.SECONDARY);
				StockQuoteChannelAdaptationPolicy.lastChanged = System.currentTimeMillis();
			} else if (shouldChangeBackToPrimary(model.getServiceProvider(), rt, model.getResponseTimeLimit()
					.longValue())) {
				newModel.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
			} else {
				newModel.setServiceProvider(model.getServiceProvider());
			}

		}
		newModel.setCacheTTL(model.getCacheTTL());
		newModel.setResponseTimeLimit(model.getResponseTimeLimit());
		newModel.setUseCache(model.getUseCache());

		return newModel;
	}

	/**
	 * If secondary service provider has been used for at least 5 seconds change
	 * back to primary.
	 * 
	 * @param rt
	 */
	private boolean shouldChangeBackToPrimary(StockQuoteServiceProvider provider, Long responseTime,
			Long responseTimeLimit) {
		if (responseTime <= responseTimeLimit && StockQuoteServiceProvider.SECONDARY.equals(provider)
				&& (lastChanged + stickyTime) < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public void setStickyTime(long stickyTime) {
		this.stickyTime = stickyTime;
	}

}
