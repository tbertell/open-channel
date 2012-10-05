package com.github.tbertell.openchannel.channel.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ChannelJaxbTest {
	
	private static final String MSG = "msg";
	private static final Long TIME = (long) 100;
	

	@Test
	public void shouldMarshallAndUnmarshallChannel() throws Exception {
		// create JAXB context and instantiate marshaller
		//		JAXBContext context = JAXBContext.newInstance("com.github.tbertell.channel");
		JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		TimerLogChannelModel tc = new TimerLogChannelModel();
		tc.setMessage(MSG);
		tc.setTimerPeriodInMillis(TIME);
		
		DOMResult result = new DOMResult();
		
		m.marshal(tc, result);

		// unmarshal from foo.xml
		Unmarshaller u = context.createUnmarshaller();

		TimerLogChannelModel resultJaxb = (TimerLogChannelModel) u.unmarshal(result.getNode());
		
		Assert.assertEquals(resultJaxb, tc);
	}

}
