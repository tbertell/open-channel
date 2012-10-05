package com.github.tbertell.openchannel;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.response.ChannelResponse;
import com.github.tbertell.openchannel.response.ListChannelsResponse;

@Path("/channels")
public class ChannelResource {

	@Autowired
	private ChannelManager channelManager;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{channelId}")
	@Produces(MediaType.APPLICATION_XML)
	public String getChannel(@PathParam("channelId") String channelId) {
		
		ChannelVariabilityModel model = channelManager.getChannel(channelId);

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(ChannelVariabilityModel.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			String schemaLocation = uriInfo.getBaseUri().toString() + model.getId() +".xsd";
			marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, schemaLocation);
			Writer writer = new StringWriter();
			marshaller.marshal(model, writer);
			return writer.toString();
		} catch (JAXBException e) {
			// TODO throw apporriate exception
			e.printStackTrace();
		}
		
		
		return "";
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_XML)
	public Response listChannels() {

		List<ChannelVariabilityModel> modelList = channelManager.listChannels();

		ListChannelsResponse listResponse = convertListToListChannelResponse(modelList);
		
		final GenericEntity<ListChannelsResponse> entity = new GenericEntity<ListChannelsResponse>(listResponse) {
		};
		
		return Response.ok().entity(entity).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{channelId}")
	public Response updateChannel(@PathParam("channelId") String channelId, ChannelVariabilityModel model) {

		channelManager.updateChannel(channelId, model);

		return Response.ok().build();
	}

	private ListChannelsResponse convertListToListChannelResponse(List<ChannelVariabilityModel> list) {
		
		ListChannelsResponse response = new ListChannelsResponse();

		for (ChannelVariabilityModel model : list) {
			response.addChannelResponse(new ChannelResponse(uriInfo.getAbsolutePath().toString() +"/" + model.getId(), model.getDescription()));
		}
		return response;
	}

}
