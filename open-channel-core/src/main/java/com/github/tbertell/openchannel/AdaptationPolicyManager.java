package com.github.tbertell.openchannel;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.reconfiguration.AdaptationPolicy;

@Component
public class AdaptationPolicyManager {

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
			AdaptationPolicy handler = findHandler(channelId);

			if (handler != null) {
				ChannelVariabilityModel newModel = handler.reconfigure(params, model);
				// update channel only if it's changed
				if (!model.equals(newModel)) {
					channelManager.updateChannel(channelId, newModel);
				}
			}
		}
	}

	private AdaptationPolicy<?> findHandler(String channelId) {
		try {
			Class<AdaptationPolicy> clazz = (Class<AdaptationPolicy>) Class.forName(HANDLER_PACKAGE + "." + channelId
					+ "AdaptationPolicy");
			AdaptationPolicy handler = clazz.newInstance();
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			// no need to do anything
		}
		return null;
	}

	public ChannelManager getChannelManager() {
		return channelManager;
	}

	public void setChannelManager(ChannelManager channelManager) {
		this.channelManager = channelManager;
	}
}
