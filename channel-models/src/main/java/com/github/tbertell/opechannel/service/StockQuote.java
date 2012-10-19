package com.github.tbertell.opechannel.service;

import javax.jws.WebService;

@WebService
public interface StockQuote {

	public String getQuote(String symbol);

}
