package com.github.tbertell.openchannel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.transform.ModelTransformer;
import com.github.tbertell.openchannel.channel.transform.ModelTransformerFactory;

/**
 * 
 * Manages Camel based channels.
 *
 */
@Component
public class CamelChannelManager implements ChannelManager {

	private final String CHANNEL_DIR;

	private static final Logger LOGGER = LoggerFactory.getLogger(CamelChannelManager.class);

	public CamelChannelManager(String channelDir) {
		super();
		CHANNEL_DIR = channelDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.tbertell.openchannel.ChannelManager#updateChannel(java.lang
	 * .String,
	 * com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel)
	 */
	@Override
	public void updateChannel(String channelId, ChannelVariabilityModel model) {

		model.validate();

		ModelTransformer transformer = ModelTransformerFactory.createModelTransformer(model);

		String channel;
		try {
			channel = transformer.transformFromModel(model);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(CHANNEL_DIR + channelId + ".xml"));
			out.write(channel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// never here
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.tbertell.openchannel.ChannelManager#getChannel(java.lang.String
	 * )
	 */
	@Override
	public ChannelVariabilityModel getChannel(String channelId) {
		String blueprint = convertXMLFileToString(CHANNEL_DIR + channelId + ".xml");

		ChannelVariabilityModel model = null;

		if (blueprint != null) {
			ModelTransformer transformer = ModelTransformerFactory.createModelTransformer(model);
			try {
				model = transformer.transformToModel(blueprint, channelId);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tbertell.openchannel.ChannelManager#listChannels()
	 */
	@Override
	public List<ChannelVariabilityModel> listChannels() {

		File channelDir = new File(CHANNEL_DIR);
		
		File[] files = channelDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith("Channel.xml") && !name.contains("Static")) {
					return true;
				} else {
					return false;
				}
			}
		});

		List<ChannelVariabilityModel> channels = new ArrayList<ChannelVariabilityModel>();
		if (files != null) {
			for (File file : files) {
				String channelName = file.getName();
				String channelId = channelName.substring(0, channelName.indexOf("."));
				channels.add(getChannel(channelId));
			}
		}
		return channels;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tbertell.openchannel.ChannelManager#deleteChannel()
	 */
	@Override
	public void deleteChannel(String channelId) {
		
		File channel = new File(CHANNEL_DIR +channelId + ".xml");
		
		if (channel.exists()) {
			channel.delete();
		} else {
			throw new RuntimeException("Channel not found");
		}
		
	}

	private String convertXMLFileToString(String fileName) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			InputStream inputStream = new FileInputStream(new File(fileName));
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			StringWriter stw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(doc), new StreamResult(stw));
			return stw.toString();
		} catch (FileNotFoundException fnfe) {
			LOGGER.debug("File " + fileName + " not found.");
		} catch (Exception e) {
			LOGGER.debug("convertXMLFileToString failed with exception ", e);
		}
		return null;
	}

}
