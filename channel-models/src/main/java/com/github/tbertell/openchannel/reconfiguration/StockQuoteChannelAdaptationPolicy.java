package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;

public class StockQuoteChannelAdaptationPolicy implements AdaptationPolicy<StockQuoteChannelModel> {

	/**
	 * There are three different cases:
	 * 	1. response time is below limit and primary service provider is used -> no action,
	 *  2. response time is below limit and secondary service provider is used -> switch back to primary,
	 *  3. response time is above limit and primary service provider is used -> switch to secondary.
	 */
	@Override
	public StockQuoteChannelModel reconfigure(Map<String, String> params,
			StockQuoteChannelModel model) {

		StockQuoteChannelModel newModel = new StockQuoteChannelModel();

		String responseTime = params.get("responseTime");
		if (responseTime != null) {
			Long rt = Long.valueOf(responseTime);
			
			// check if reconfiguration is needed
			if (rt > model.getResponseTimeLimit()) {
				newModel.setServiceProvider(StockQuoteServiceProvider.SECONDARY);
			} else if (StockQuoteServiceProvider.SECONDARY.equals(model.getServiceProvider())) {
				newModel.setServiceProvider(StockQuoteServiceProvider.PRIMARY);		
			} else {
				return model;
			}
			
		}
		newModel.setCacheTTL(model.getCacheTTL());
		newModel.setResponseTimeLimit(model.getResponseTimeLimit());
		newModel.setUseCache(model.getUseCache());
		
		return newModel;
	}

}
