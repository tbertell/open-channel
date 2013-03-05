package com.github.tbertell.openchannel;

import java.util.List;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Includes operations for managing channels.
 *
 */
public interface ChannelManager {

	public void updateChannel(String channelId, ChannelVariabilityModel model);

	public ChannelVariabilityModel getChannel(String channelId);

	public List<ChannelVariabilityModel> listChannels();
	
	public void deleteChannel(String channelId);
	
	public void createChannel(ChannelVariabilityModel model);

}