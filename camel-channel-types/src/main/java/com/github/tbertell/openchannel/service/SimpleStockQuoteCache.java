package com.github.tbertell.openchannel.service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleStockQuoteCache {

	private long cacheTTL = 100000;
	// just a placeholder for value
	private String useCache = "true";

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStockQuoteCache.class);

	private static final ConcurrentHashMap<String, CacheEntry> CACHE = new ConcurrentHashMap<String, CacheEntry>();

	public void getQuote(String symbol, Exchange exchange) {
		logCache();

		if (CACHE.containsKey(symbol)) {
			CacheEntry entry = CACHE.get(symbol);

			LOGGER.debug("Cache contains " + symbol + " " + entry.getQuote());

			if (isCacheEntryValid(entry, System.currentTimeMillis())) {
				exchange.getOut().setBody(entry.getQuote());
				exchange.getOut().setHeader("cacheHit", "true");
			}
		}
	}

	public void updateCache(Exchange exchange) {
		String symbol = (String) exchange.getIn().getHeader("symbol");
		String quote = (String) exchange.getIn().getHeader("quote");
		LOGGER.info("put " + "symbol " + symbol + " " + quote + " to cache");
		CACHE.put(symbol, new CacheEntry("<quote>" + quote + "</quote>", System.currentTimeMillis()));
	}

	public long getCacheTTL() {
		return cacheTTL;
	}

	public void setCacheTTL(long cacheTTL) {
		this.cacheTTL = cacheTTL;
	}

	public String getUseCache() {
		return useCache;
	}

	public void setUseCache(String useCache) {
		this.useCache = useCache;
	}

	private void logCache() {
		LOGGER.info("Cache size " + CACHE.size());
		for (CacheEntry entry : CACHE.values()) {
			LOGGER.info("Cache " + entry.getQuote() + " last accessed " + entry.getLastAccessed());
		}
	}

	private boolean isCacheEntryValid(CacheEntry entry, long currentTime) {
		if (entry.getLastAccessed().longValue() + cacheTTL > currentTime) {
			LOGGER.info("Cache hit!");
			return true;
		} else {
			LOGGER.info("Cache entry invalid! currentTime " + currentTime);
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
