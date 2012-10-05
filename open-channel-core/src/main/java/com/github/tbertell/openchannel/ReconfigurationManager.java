package com.github.tbertell.openchannel;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.reconfiguration.ReconfigurationHandler;
import com.github.tbertell.openchannel.reconfiguration.TimerLogChannelReconfigurationHandler;

public class ReconfigurationManager {

	@Autowired
	private ChannelManager channelManager;

	public void reconfigure(Map<String, String> params, String channelId) {
		ChannelVariabilityModel model = channelManager.getChannel(channelId);

		ReconfigurationHandler handler = new TimerLogChannelReconfigurationHandler();

		if (handler.isReconfigurationNeeded(params)) {
			ChannelVariabilityModel newModel = handler.reconfigure(params, model);
			channelManager.updateChannel(channelId, newModel);
		}
	}

}
