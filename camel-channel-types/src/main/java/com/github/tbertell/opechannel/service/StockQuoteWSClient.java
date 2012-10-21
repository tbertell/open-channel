package com.github.tbertell.opechannel.service;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockQuoteWSClient {
	private String url = "http://www.webservicex.net/stockquote.asmx";

	private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteWSClient.class);

	private final String START_ELEMENT = "<Last>";
	private final String END_ELEMENT = "</Last>";

	public String getQuote(String symbol) {

		LOGGER.info("Start web service call with symbol: " + symbol);
		long starttime = System.currentTimeMillis();

		StockQuote ss = new StockQuote();
		StockQuoteSoap port = ss.getStockQuoteSoap();
		String response = port.getQuote(symbol);

		long endtime = System.currentTimeMillis();
		String quote = parseQuote(response);
		LOGGER.info("End web service call with response: " + quote + ", respose time: " + (endtime - starttime) + " ms");
		return quote;
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

}
