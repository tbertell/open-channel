package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;

public class StockQuoteChannelAdaptationPolicy implements AdaptationPolicy {


	@Override
	public ChannelVariabilityModel reconfigure(Map<String, String> params,
			ChannelVariabilityModel model) {

		StockQuoteChannelModel newModel = new StockQuoteChannelModel();
		StockQuoteChannelModel oldModel = (StockQuoteChannelModel) model;

		String responseTime = params.get("responseTime");
		if (responseTime != null) {
			Long rt = Long.valueOf(responseTime);
			StockQuoteChannelModel stockQuoteChannelModel = (StockQuoteChannelModel) model;
			return oldModel;
			
		}
		return newModel;
	}

}
