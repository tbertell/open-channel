package com.github.tbertell.openchannel.service;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

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

	// used just as a placeholder for the value
	private Long responseTimeLimit = Long.valueOf(0);

	public void getQuote(String symbol, Exchange exchange) {

		LOGGER.info("Start web service call with symbol: " + symbol + " url " + url);
		long starttime = System.currentTimeMillis();

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

		LOGGER.info("End SECONDARY web service call: " + quote + ", response time: " + responseTime + " ms");
	}

	public static void main(String... args) {
		StockQuote ss = new StockQuote();

		StockQuoteSoap port = ss.getStockQuoteSoap();
		BindingProvider bp = (BindingProvider)port;

		Map<String, Object> context = bp.getRequestContext();

		Object oldAddress = context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);

		context.put(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				"url");



		StockQuoteWSClient client = new StockQuoteWSClient();

		for (int i = 0; i < 30; i++) {
			long alku = System.currentTimeMillis();
			String quotes = port.getQuote("NOK");
			long loppu = System.currentTimeMillis();
			System.out.println(client.parseQuote(quotes) + " kesti " +(loppu - alku));

		}
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

	public Long getResponseTimeLimit() {
		return responseTimeLimit;
	}

	public void setResponseTimeLimit(Long responseTimeLimit) {
		this.responseTimeLimit = responseTimeLimit;
	}

}
