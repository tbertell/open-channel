package com.github.tbertell.openchannel.channelmodel;

public interface ModelTransformer {

	public String transformFromModel(ChannelVariabilityModel model);

	public ChannelVariabilityModel transformToModel(String blueprint, String channelId);

}