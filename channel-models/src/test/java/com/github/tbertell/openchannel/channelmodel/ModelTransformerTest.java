package com.github.tbertell.openchannel.channelmodel;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ModelTransformerTest {

	private static final String MESSAGE = "This works!";
	private static final Long PERIOD = (long) 10000;

	@Test
	public void shouldTransformModelToBlueprintAndBackToModel() {
		ModelTransformer transformer = new ModelTransformer();
		TimerLogChannelModel sourceModel = new TimerLogChannelModel();

		Assert.assertEquals(sourceModel.getId(), "TimerLogChannel");

		sourceModel.setMessage(MESSAGE);
		sourceModel.setTimerPeriodInMillis(PERIOD);

		String blueprint = transformer.transformFromModel(sourceModel);

		TimerLogChannelModel resultModel = (TimerLogChannelModel) transformer.transformToModel(blueprint,
				"TimerLogChannel");

		Assert.assertEquals(resultModel, sourceModel);
	}
}
