package com.github.tbertell.openchannel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;

@Path("/channel")
public class ChannelResource {

	@Autowired
	private ChannelManager channelManager;

	@GET
	@Path("/{channelId}")
	@Produces(MediaType.APPLICATION_XML)
	public ChannelVariabilityModel getChannel(@PathParam("channelId") String channelId) {
		ChannelVariabilityModel model = channelManager.getChannel(channelId);

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
	public Response updateChannel(@PathParam("channelId") String channelId, ChannelVariabilityModel model) {

		channelManager.updateChannel(channelId, model);

		return Response.ok().build();
	}
}
