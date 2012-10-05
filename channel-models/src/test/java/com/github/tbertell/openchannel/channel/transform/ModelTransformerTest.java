package com.github.tbertell.openchannel.channel.transform;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channel.model.TimerLogChannelModel;
import com.github.tbertell.openchannel.channel.transform.ModelTransformer;
import com.github.tbertell.openchannel.channel.transform.ModelXslTransformer;

public class ModelTransformerTest {

	private static final String MESSAGE = "This works!";
	private static final Long PERIOD = (long) 10000;

	@Test
	public void shouldTransformModelToBlueprintAndBackToModel() {
		ModelTransformer transformer = new ModelXslTransformer();
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
