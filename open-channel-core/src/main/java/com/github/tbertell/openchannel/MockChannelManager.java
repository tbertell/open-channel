package com.github.tbertell.openchannel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;
import com.github.tbertell.openchannel.channel.model.TimerLogChannelModel;

public class MockChannelManager implements ChannelManager {

	private static Map<String, ChannelVariabilityModel> models;

	static {
		models = new HashMap<String, ChannelVariabilityModel>();

		TimerLogChannelModel model1 = new TimerLogChannelModel();
		model1.setMessage("Log message");
		model1.setTimerPeriodInMillis(Long.valueOf(10000));
		models.put(model1.getId(), model1);

		StockQuoteChannelModel model2 = new StockQuoteChannelModel();
		model2.setCacheTTL(BigInteger.valueOf(800));
		model2.setResponseTimeLimit(BigInteger.valueOf(1000));
		model2.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
		model2.setUseCache(false);
		models.put(model2.getId(), model2);
	}

	@Override
	public void updateChannel(String channelId, ChannelVariabilityModel model) {
		model.validate();
		models.put(channelId, model);
	}

	@Override
	public ChannelVariabilityModel getChannel(String channelId) {
		if (channelId != null) {
			return models.get(channelId);
		}
		return null;
	}

	@Override
	public List<ChannelVariabilityModel> listChannels() {
		List<ChannelVariabilityModel> list = new ArrayList<ChannelVariabilityModel>();

		for (ChannelVariabilityModel model : models.values()) {
			list.add(model);
		}
		return list;
	}

	@Override
	public void deleteChannel(String channelId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createChannel(ChannelVariabilityModel model) {
		// TODO Auto-generated method stub
		
	}

}
