package com.github.tbertell.openchannel.channel.adaptation;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Every channel adaptation policy should implement this interface.
 *
 * @param <T>
 */
public interface AdaptationPolicy<T extends ChannelVariabilityModel> {

	/**
	 * 
	 * Contains logic for reconfiguring specified channel variability model.
	 * 
	 * @param params generic parameters used for reconfiguration
	 * @param model ChannelVariabilityModel
	 * @return updated model
	 */
	public T reconfigure(Map<String, String> params, T model);

}