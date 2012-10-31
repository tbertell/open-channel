package com.github.tbertell.openchannel.reconfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;

public class StockQuoteChannelAdaptationPolicyTest {

	private StockQuoteChannelAdaptationPolicy policy = new StockQuoteChannelAdaptationPolicy();
	private StockQuoteChannelModel model = new StockQuoteChannelModel();

	@BeforeTest
	public void setUp() {	
		model.setCacheTTL(Long.valueOf(123));
		model.setResponseTimeLimit(Long.valueOf(10000));
		model.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
		model.setUseCache(Boolean.FALSE);
	}
	@Test
	public void testChange() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("responseTime", "10001");
		
		StockQuoteChannelModel changedToSecondary = (StockQuoteChannelModel) policy.reconfigure(params, model);
		
		// changes to secondary
		assertEquals(changedToSecondary.getServiceProvider(), StockQuoteServiceProvider.SECONDARY);
		assertEquals(changedToSecondary.getCacheTTL(), model.getCacheTTL());
		assertEquals(changedToSecondary.getResponseTimeLimit(), model.getResponseTimeLimit());
		assertEquals(changedToSecondary.getUseCache(), model.getUseCache());
		
		// stays secondary
		StockQuoteChannelModel noChange = (StockQuoteChannelModel) policy.reconfigure(params, model);
		assertEquals(noChange, changedToSecondary);
		
		// change back to primary
		params.put("responseTime", "10000");
		StockQuoteChannelModel changedToPrimary = (StockQuoteChannelModel) policy.reconfigure(params, changedToSecondary);	
		assertEquals(changedToPrimary.getServiceProvider(), StockQuoteServiceProvider.PRIMARY);
		
		// stays primary
		StockQuoteChannelModel staysPrimary = (StockQuoteChannelModel) policy.reconfigure(params, changedToSecondary);
		assertEquals(staysPrimary, changedToPrimary);
	}
}
