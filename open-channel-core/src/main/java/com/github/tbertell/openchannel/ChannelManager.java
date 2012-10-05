package com.github.tbertell.openchannel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.transform.ModelTransformer;
import com.github.tbertell.openchannel.channel.transform.ModelXslTransformer;

public class ChannelManager {

	private final String CHANNEL_DIR;

	public ChannelManager(String channelDir) {
		CHANNEL_DIR = channelDir;
	}

	public void updateChannel(String channelId, ChannelVariabilityModel model) {

		model.validate();
		ModelTransformer transformer = new ModelXslTransformer();
		String channel = transformer.transformFromModel(model);

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(CHANNEL_DIR + channelId + ".xml"));
			out.write(channel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public ChannelVariabilityModel getChannel(String channelId) {
		String blueprint = convertXMLFileToString(CHANNEL_DIR + channelId + ".xml");

		ModelTransformer transformer = new ModelXslTransformer();

		ChannelVariabilityModel model = transformer.transformToModel(blueprint, channelId);

		return model;
	}

	public List<ChannelVariabilityModel> listChannels() {

		File channelDir = new File(CHANNEL_DIR);

		File[] files = channelDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith("Channel.xml")) {
					return true;
				} else {
					return false;
				}
			}
		});

		List<ChannelVariabilityModel> channels = new ArrayList<ChannelVariabilityModel>();

		for (File file : files) {
			String channelName = file.getName();
			String channelId = channelName.substring(0, channelName.indexOf("."));
			channels.add(getChannel(channelId));
		}

		return channels;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
