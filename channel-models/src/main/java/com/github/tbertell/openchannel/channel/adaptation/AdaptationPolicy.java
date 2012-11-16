package com.github.tbertell.openchannel.channel.adaptation;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public interface AdaptationPolicy<T extends ChannelVariabilityModel> {

	public T reconfigure(Map<String, String> params, T model);

}