package com.github.tbertell.openchannel.channel.transform;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;
import com.github.tbertell.openchannel.channel.model.TimerLogChannelModel;

public class ModelTransformerTest {

	private static final String MESSAGE = "This works!";
	private static final Long PERIOD = (long) 10000;

	@Test
	public void shouldTransformTimerLogChannelModelToBlueprintAndBackToModel() throws Exception {
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
	public void shouldTransformStockQuotesChannelModelToBlueprintAndBackToModel() throws Exception {

		StockQuoteChannelModel sourceModel = new StockQuoteChannelModel();
		ModelTransformer transformer = ModelTransformerFactory.createModelTransformer(sourceModel);

		Assert.assertEquals(sourceModel.getId(), "StockQuoteChannel");

		sourceModel.setCacheTTL(BigInteger.valueOf(123));
		sourceModel.setResponseTimeLimit(BigInteger.valueOf(321));
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
