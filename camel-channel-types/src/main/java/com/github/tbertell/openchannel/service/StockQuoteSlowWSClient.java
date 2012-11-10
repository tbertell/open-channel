package com.github.tbertell.openchannel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockQuoteSlowWSClient {
	private String url = "http://www.webservicex.net/stockquote.asmx";

	private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteSlowWSClient.class);

	// used just as a placeholder for the value
	private Long responseTimeLimit = Long.valueOf(0);

	private final String START_ELEMENT = "<Last>";
	private final String END_ELEMENT = "</Last>";

	public void getQuote(String symbol, Exchange exchange) {
		LOGGER.info("Start web service call with symbol: " + symbol + " url " + url);
		long starttime = System.currentTimeMillis();

		StockQuote ss = new StockQuote();
		StockQuoteSoap port = ss.getStockQuoteSoap();

		waitRandomTime();

		String response = port.getQuote(symbol);

		long endtime = System.currentTimeMillis();
		Long responseTime = (endtime - starttime);

		String quote = parseQuote(response);

		// setup headers
		Map<String, String> params = new HashMap<String, String>();
		params.put("responseTime", responseTime.toString());
		params.put("channelId", "StockQuoteChannel");

		exchange.getOut().setHeader("params", params);
		exchange.getOut().setHeader("quote", quote);
		exchange.getOut().setHeader("symbol", symbol);
		exchange.getOut().setBody("<quote>" + quote + "</quote>");

		LOGGER.info("End web service call with response: " + quote + ", respose time: " + responseTime + " ms");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getResponseTimeLimit() {
		return responseTimeLimit;
	}

	public void setResponseTimeLimit(Long responseTimeLimit) {
		this.responseTimeLimit = responseTimeLimit;
	}

	private String parseQuote(String response) {
		int beginIndex = response.indexOf(START_ELEMENT) + 6;
		int endIndex = response.indexOf(END_ELEMENT);
		String quote = response.substring(beginIndex, endIndex);

		return quote;
	}

	private void waitRandomTime() {
		int sleepTime = new Random().nextInt(1500);
		LOGGER.error("Wait for " + sleepTime + " ms");
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			LOGGER.error("Sleep failed ", e);
		}
	}
}
