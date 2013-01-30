package com.github.tbertell.openchannel.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * JAX-RS resource class for stock quote.
 *
 */
@Path("/stockquote")
public class StockQuoteResource {

	@GET
	@Path("/{id}/")
	public String getStockQuote(@PathParam("id") String id) {
		return null;
	}

}
