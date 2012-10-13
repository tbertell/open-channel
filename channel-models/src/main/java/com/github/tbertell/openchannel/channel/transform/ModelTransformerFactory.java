package com.github.tbertell.openchannel.channel.transform;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public class ModelTransformerFactory {

	public static ModelTransformer createModelTransformer(ChannelVariabilityModel model) {
		return new ModelXslTransformer();
	}
}
