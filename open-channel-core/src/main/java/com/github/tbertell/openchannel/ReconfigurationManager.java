package com.github.tbertell.openchannel;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.reconfiguration.ReconfigurationHandler;

public class ReconfigurationManager {

	@Autowired
	private ChannelManager channelManager;

	private static String HANDLER_PACKAGE = "com.github.tbertell.openchannel.reconfiguration";

	public void reconfigure(String channelId, Map<String, String> params) {
		ChannelVariabilityModel model = null;
		try {
			model = channelManager.getChannel(channelId);
		} catch (Exception e) {
			e.printStackTrace();
			// do nothing
		}
		if (model != null) {
			ReconfigurationHandler handler = findHandler(channelId);

			if (handler != null && handler.isReconfigurationNeeded(params)) {
				ChannelVariabilityModel newModel = handler.reconfigure(params, model);
				channelManager.updateChannel(channelId, newModel);
			}
		}
	}

	private ReconfigurationHandler findHandler(String channelId) {
		try {
			Class<ReconfigurationHandler> clazz = (Class<ReconfigurationHandler>) Class.forName(HANDLER_PACKAGE + "."
					+ channelId + "ReconfigurationHandler");
			ReconfigurationHandler handler = clazz.newInstance();
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			// no need to do anything
		}
		return null;
	}

}
