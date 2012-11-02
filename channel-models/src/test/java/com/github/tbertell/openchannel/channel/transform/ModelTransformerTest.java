package com.github.tbertell.openchannel.channel.transform;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;
import com.github.tbertell.openchannel.channel.model.TimerLogChannelModel;

public class ModelTransformerTest {

	private static final String MESSAGE = "This works!";
	private static final Long PERIOD = (long) 10000;

	@Test
	public void shouldTransformTimerLogChannelModelToBlueprintAndBackToModel() {
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

	@Test
	public void shouldTransformStockQuotesChannelModelToBlueprintAndBackToModel() {

		StockQuoteChannelModel sourceModel = new StockQuoteChannelModel();
		ModelTransformer transformer = ModelTransformerFactory.createModelTransformer(sourceModel);

		Assert.assertEquals(sourceModel.getId(), "StockQuoteChannel");

		sourceModel.setCacheTTL(Long.valueOf(123));
		sourceModel.setResponseTimeLimit(Long.valueOf(321));
		sourceModel.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
		sourceModel.setUseCache(Boolean.FALSE);

		String blueprint = transformer.transformFromModel(sourceModel);

		System.out.println(blueprint);
		StockQuoteChannelModel resultModel = (StockQuoteChannelModel) transformer.transformToModel(blueprint,
				sourceModel.getId());

		System.out.println(resultModel);
		Assert.assertEquals(resultModel, sourceModel);
	}
}
