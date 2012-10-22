package com.github.tbertell.opechannel.service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import net.webservicex.StockQuote;
import net.webservicex.StockQuoteSoap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockQuoteWSClient {
	private String url = "http://www.webservicex.net/stockquote.asmx";

	private long cacheTTL = 1000;

	private static final Logger LOGGER = LoggerFactory.getLogger(StockQuoteWSClient.class);

	private final String START_ELEMENT = "<Last>";
	private final String END_ELEMENT = "</Last>";

	private static final ConcurrentHashMap<String, CacheEntry> CACHE = new ConcurrentHashMap<String, CacheEntry>();

	public String getQuote(String symbol) {

		LOGGER.info("Start web service call with symbol: " + symbol);
		long starttime = System.currentTimeMillis();

		// if TTL is 0 cache is not used
		if (cacheTTL != 0 && CACHE.contains(symbol)) {
			CacheEntry entry = CACHE.get(symbol);
			if (isCacheEntryValid(entry, starttime)) {
				return entry.getQuote();
			}
		}

		StockQuote ss = new StockQuote();
		StockQuoteSoap port = ss.getStockQuoteSoap();
		String response = port.getQuote(symbol);

		long endtime = System.currentTimeMillis();
		String quote = parseQuote(response);
		CACHE.put(symbol, new CacheEntry(symbol, endtime));
		
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

	private boolean isCacheEntryValid(CacheEntry entry, long currentTime) {
		if (entry.getLastAccessed().longValue() + cacheTTL > currentTime) {
			LOGGER.debug("Cache hit");
			return true;
		} else {
			LOGGER.debug("Cache invalid");
			return false;
		}
	}
	
	private class CacheEntry implements Serializable {

		private static final long serialVersionUID = 3113230821706755430L;

		private String symbol;
		private Long lastAccessed;

		
		public CacheEntry(String symbol, Long lastAccessed) {
			super();
			this.symbol = symbol;
			this.lastAccessed = lastAccessed;
		}

		public String getQuote() {
			return symbol;
		}

		public void setQuote(String quote) {
			this.symbol = quote;
		}

		public Long getLastAccessed() {
			return lastAccessed;
		}

		public void setLastAccessed(Long lastAccessed) {
			this.lastAccessed = lastAccessed;
		}
	}

}
