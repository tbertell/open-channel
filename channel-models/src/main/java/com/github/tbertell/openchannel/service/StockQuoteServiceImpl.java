package com.github.tbertell.openchannel.service;

import javax.jws.WebService;

@WebService(endpointInterface = "com.github.tbertell.opechannel.service.StockQuote")
public class StockQuoteServiceImpl implements StockQuoteService {

	@Override
	public String getQuote(String symbol) {
		System.out.println("Toimii!");
		return null;
	}

}
