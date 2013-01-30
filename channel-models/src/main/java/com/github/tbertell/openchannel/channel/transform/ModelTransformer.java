package com.github.tbertell.openchannel.channel.transform;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Includes methods for transforming model to channel and vice versa.
 *
 */
public interface ModelTransformer {

	/**
	 * Transforms channel variability model to Camel blueprint.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String transformFromModel(ChannelVariabilityModel model) throws Exception;

	/**
	 * Transforms Camel blueprint to channel variability model.
	 * @param blueprint
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public ChannelVariabilityModel transformToModel(String blueprint, String channelId) throws Exception;

}