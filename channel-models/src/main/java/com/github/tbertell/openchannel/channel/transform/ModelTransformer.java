package com.github.tbertell.openchannel.channel.transform;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public interface ModelTransformer {

	public String transformFromModel(ChannelVariabilityModel model);

	public ChannelVariabilityModel transformToModel(String blueprint, String channelId);

}