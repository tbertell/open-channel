package com.github.tbertell.openchannel.channelmodel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channelmodel.TestChannelModel;

public class ChannelJaxbTest {

	@Test
	public void unmarshallChannel() throws Exception {
		// create JAXB context and instantiate marshaller
		//		JAXBContext context = JAXBContext.newInstance("com.github.tbertell.channel");
		JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		TestChannelModel tc = new TestChannelModel();
		tc.setMessage("msg");
		tc.setId("id1");
		m.marshal(tc, System.out);
	}

	@Test
	public void marshallChannel() throws Exception {

		JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);
		// unmarshal from foo.xml
		Unmarshaller u = context.createUnmarshaller();
		
		ChannelVariabilityModel fooObj = (ChannelVariabilityModel) u.unmarshal( new File( "src/test/resources/com/github/tbertell/openchannel/channelmodel/channel.xml" ) );

		// marshal to System.out
		Marshaller m = context.createMarshaller();
		m.marshal(fooObj, System.out);
	}



}
