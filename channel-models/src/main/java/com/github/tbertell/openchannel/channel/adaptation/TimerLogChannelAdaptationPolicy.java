package com.github.tbertell.openchannel.channel.adaptation;

import java.util.Map;

import com.github.tbertell.openchannel.channel.model.TimerLogChannelModel;

public class TimerLogChannelAdaptationPolicy implements AdaptationPolicy<TimerLogChannelModel> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.tbertell.openchannel.reconfiguration.ReconfigurationHandler
	 * #reconfigure(java.util.Map,
	 * com.github.tbertell.openchannel.channelmodel.TimerLogChannelModel)
	 */
	@Override
	public TimerLogChannelModel reconfigure(Map<String, String> params, TimerLogChannelModel model) {

		String responseTime = params.get("responseTime");

		TimerLogChannelModel newModel = new TimerLogChannelModel();
		newModel.setTimerPeriodInMillis(Long.valueOf(model.getTimerPeriodInMillis()));
		if (responseTime != null) {
			newModel.setMessage("TimerLogChannelModel is reconfigured at " + System.currentTimeMillis());
			newModel.setTimerPeriodInMillis(Long.valueOf(model.getTimerPeriodInMillis()));
		} else {
			newModel.setMessage(model.getMessage());
		}
		return newModel;
	}
}
