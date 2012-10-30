package com.github.tbertell.openchannel.channel.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ChannelJaxbTest {
	
	private static final String MSG = "msg";
	private static final Long TIME = (long) 100;
	

	@Test
	public void shouldMarshallAndUnmarshallTimerLogChannelModel() throws Exception {
		// create JAXB context and instantiate marshaller
		//		JAXBContext context = JAXBContext.newInstance("com.github.tbertell.channel");


		TimerLogChannelModel tc = new TimerLogChannelModel();
		tc.setMessage(MSG);
		tc.setTimerPeriodInMillis(TIME);
		
		TimerLogChannelModel resultJaxb = (TimerLogChannelModel) marshallAndUnmarshall(tc);
		
		Assert.assertEquals(resultJaxb, tc);
	}
	
	@Test
	public void shouldMarshallAndUnmarshallStockQuoteChannelModel() throws Exception {
		// create JAXB context and instantiate marshaller
		//		JAXBContext context = JAXBContext.newInstance("com.github.tbertell.channel");


		StockQuoteChannelModel model = new StockQuoteChannelModel();
		model.setCacheTTL(Long.valueOf(123));
		model.setResposeTimeLimit(Long.valueOf(321));
		model.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
		model.setUseCache(Boolean.TRUE);
		
		StockQuoteChannelModel resultJaxb = (StockQuoteChannelModel) marshallAndUnmarshall(model);
		
		Assert.assertEquals(resultJaxb, model);
	}


	private ChannelVariabilityModel marshallAndUnmarshall(ChannelVariabilityModel tc) throws JAXBException {
		
		JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		DOMResult result = new DOMResult();
		
		m.marshal(tc, result);
		
		StreamResult sResult = new StreamResult(System.out);
		m.marshal(tc, sResult);

		// unmarshal from foo.xml
		Unmarshaller u = context.createUnmarshaller();

		ChannelVariabilityModel resultJaxb = (ChannelVariabilityModel) u.unmarshal(result.getNode());
		return resultJaxb;
	}

}
