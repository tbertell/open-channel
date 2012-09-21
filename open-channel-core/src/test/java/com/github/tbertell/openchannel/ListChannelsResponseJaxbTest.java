package com.github.tbertell.openchannel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.response.ChannelResponse;
import com.github.tbertell.openchannel.response.Link;
import com.github.tbertell.openchannel.response.ListChannelsResponse;

public class ListChannelsResponseJaxbTest {
	@Test
	public void shouldMarhallAndUnmarshall() throws Exception {

		JAXBContext context = JAXBContext.newInstance(ListChannelsResponse.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		Link link = new Link();
		link.setHref("href");

		ChannelResponse response = new ChannelResponse();
		response.setLink(link);
		response.setDescription("description");

		ListChannelsResponse list = new ListChannelsResponse();
		list.addChannelResponse(response);

		DOMResult result = new DOMResult();

		m.marshal(list, result);

		m.marshal(list, new StreamResult(System.out));

		// unmarshal from foo.xml
		Unmarshaller u = context.createUnmarshaller();

		ListChannelsResponse resultJaxb = (ListChannelsResponse) u.unmarshal(result.getNode());

		Assert.assertEquals(resultJaxb, list);
	}
}
