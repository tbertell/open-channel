package com.github.tbertell.openchannel.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockQuoteWSClient {
	private String url = "http://www.webservicex.net/stockquote.asmx";

	private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteWSClient.class);

	private final String START_ELEMENT = "<Last>";
	private final String END_ELEMENT = "</Last>";
	private Boolean slow = Boolean.TRUE;

	// used just as a placeholder for the value
	private Long responseTimeLimit = Long.valueOf(0);

	public void getQuote(String symbol, Exchange exchange) {

		LOGGER.info("Start web service call with symbol: " + symbol + " url " + url);
		long starttime = System.currentTimeMillis();

		// simulate slow response time
		if (slow) {
			waitRandomTime();
		}

		StockQuote ss = new StockQuote();
		StockQuoteSoap port = ss.getStockQuoteSoap();
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

		// return "<quote>" + quote + "</quote>";
	}

	public static void main(String... args) {
		StockQuote ss = new StockQuote();
		StockQuoteSoap port = ss.getStockQuoteSoap();
		String quotes = port.getQuote("NOK");
		System.out.println(new StockQuoteWSClient().parseQuote(quotes));
	}

	private String parseQuote(String response) {
		int beginIndex = response.indexOf(START_ELEMENT) + 6;
		int endIndex = response.indexOf(END_ELEMENT);
		String quote = response.substring(beginIndex, endIndex);

		return quote;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getSlow() {
		return slow;
	}

	public void setSlow(Boolean slow) {
		this.slow = slow;
	}

	public Long getResponseTimeLimit() {
		return responseTimeLimit;
	}

	public void setResponseTimeLimit(Long responseTimeLimit) {
		this.responseTimeLimit = responseTimeLimit;
	}

	private void waitRandomTime() {
		int sleepTime = new Random().nextInt(5000);
		LOGGER.info("Wait for " + sleepTime + " ms");
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// don't sleep then
			e.printStackTrace();
		}
	}

}
