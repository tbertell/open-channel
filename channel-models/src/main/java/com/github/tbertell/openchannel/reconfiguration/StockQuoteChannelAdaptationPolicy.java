package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public class StockQuoteChannelAdaptationPolicy implements AdaptationPolicy {

	@Override
	public boolean isReconfigurationNeeded(Map<String, String> params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChannelVariabilityModel reconfigure(Map<String, String> params,
			ChannelVariabilityModel model) {
		// TODO Auto-generated method stub
		return null;
	}

}
