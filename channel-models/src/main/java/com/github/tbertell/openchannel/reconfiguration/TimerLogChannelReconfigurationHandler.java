package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channelmodel.TimerLogChannelModel;

public class TimerLogChannelReconfigurationHandler implements ReconfigurationHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.tbertell.openchannel.reconfiguration.ReconfigurationHandler
	 * #isReconfigurationNeeded(java.util.Map)
	 */
	@Override
	public boolean isReconfigurationNeeded(Map<String, String> params) {
		String responseTime = params.get("responseTime");
		if (responseTime != null) {
			Long rt = Long.valueOf(responseTime);

			if (rt > 100) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.tbertell.openchannel.reconfiguration.ReconfigurationHandler
	 * #reconfigure(java.util.Map,
	 * com.github.tbertell.openchannel.channelmodel.TimerLogChannelModel)
	 */
	@Override
	public ChannelVariabilityModel reconfigure(Map<String, String> params, ChannelVariabilityModel model) {

		String responseTime = params.get("responseTime");

		TimerLogChannelModel newModel = new TimerLogChannelModel();
		TimerLogChannelModel oldModel = (TimerLogChannelModel) model;
		newModel.setTimerPeriodInMillis(Long.valueOf(oldModel.getTimerPeriodInMillis()));
		if (responseTime != null) {
			Long rt = Long.valueOf(responseTime);
			newModel.setMessage("TimerLogChannelModel is reconfigured at " + System.currentTimeMillis());
			newModel.setTimerPeriodInMillis(Long.valueOf(oldModel.getTimerPeriodInMillis()));
		} else {
			newModel.setMessage(oldModel.getMessage());
		}
		return newModel;
	}
}
