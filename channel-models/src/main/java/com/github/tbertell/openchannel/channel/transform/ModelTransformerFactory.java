package com.github.tbertell.openchannel.channel.transform;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Creates model specific transformers.
 *
 */
public class ModelTransformerFactory {

	public static ModelTransformer createModelTransformer(ChannelVariabilityModel model) {
		return new ModelXslTransformer();
	}
}
