package com.github.tbertell.openchannel;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channelmodel.SecondTestChannelModel;
import com.github.tbertell.openchannel.channelmodel.TestChannelModel;

@Path("/channel")
public class ChannelResource {

	@GET
	@Path("/{channelId}")
	@Produces(MediaType.APPLICATION_XML)
	public ChannelVariabilityModel getChannel(@PathParam("channelId") String channelId) {
		ChannelVariabilityModel model = null;
		if (channelId.equals("1")) {
			model = new TestChannelModel();
			model.setId("112");
		} else {
			model = new SecondTestChannelModel();
			model.setId("2212");
		}
		return model;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_XML)
	public Response getChannels() {
		return Response.noContent().build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{channelId}")
	public Response updateChannel(@PathParam("channelId") String channelId, ChannelVariabilityModel input) {

//		try {
//			JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);
//			// unmarshal from foo.xml
//			Unmarshaller unmarshaller = context.createUnmarshaller();
//			ChannelVariabilityModel channelModel = (ChannelVariabilityModel) unmarshaller.unmarshal(input);
//			System.out.println(channelModel.getId());
//		} catch (JAXBException e) {
//			e.printStackTrace();
//			return Response.status(Status.BAD_REQUEST).build();
//		}

		return Response.ok().build();
	}
}

