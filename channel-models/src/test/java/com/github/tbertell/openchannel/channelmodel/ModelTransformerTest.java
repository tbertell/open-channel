package com.github.tbertell.openchannel.channelmodel;

import org.testng.annotations.Test;

public class ModelTransformerTest {

	private static final String MESSAGE = "This works!";
	private static final Long PERIOD = (long) 1000;

	@Test
	public void shouldTransformModelToBlueprint() {
		ModelTransformer transformer = new ModelTransformer();
		TimerLogChannelModel model = new TimerLogChannelModel();
		model.setMessage(MESSAGE);
		model.setTimerPeriodInMillis(PERIOD);
	}
}
