package com.github.tbertell.opechannel.service;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.tbertell.opechannel.service.StockQuote")
public class StockQuoteImpl implements StockQuote {

	@Override
	public String getQuote(String symbol) {
		// TODO Auto-generated method stub
		return null;
	}

}
