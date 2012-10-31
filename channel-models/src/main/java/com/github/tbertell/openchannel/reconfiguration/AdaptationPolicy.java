package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public interface AdaptationPolicy {

	public ChannelVariabilityModel reconfigure(Map<String, String> params, ChannelVariabilityModel model);

}