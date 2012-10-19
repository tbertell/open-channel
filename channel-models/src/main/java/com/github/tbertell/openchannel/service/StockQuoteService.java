package com.github.tbertell.openchannel.service;

import javax.jws.WebService;

@WebService
public interface StockQuoteService {

	public String getQuote(String symbol);

}
