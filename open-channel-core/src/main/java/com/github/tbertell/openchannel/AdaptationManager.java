package com.github.tbertell.openchannel;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.tbertell.openchannel.channel.adaptation.AdaptationPolicy;
import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Responsible for receiving reconfiguration parameters and
 * redirecting them to correct handler.
 *
 */
@Component
public class AdaptationManager {

	@Autowired
	@Qualifier("camelChannelManager")
	private ChannelManager channelManager;

	private static String HANDLER_PACKAGE = "com.github.tbertell.openchannel.channel.adaptation";

	public void reconfigure(String channelId, Map<String, String> params) {
		ChannelVariabilityModel model = null;
		try {
			model = channelManager.getChannel(channelId);
		} catch (Exception e) {
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
