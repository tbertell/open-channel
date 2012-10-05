package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public interface ReconfigurationHandler {

	public boolean isReconfigurationNeeded(Map<String, String> params);

	public ChannelVariabilityModel reconfigure(Map<String, String> params, ChannelVariabilityModel model);

}