package com.github.tbertell.openchannel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channelmodel.TestChannelModel;

public class ChannelManager {

	private final String CHANNEL_DIR;

	public ChannelManager(String channelDir) {
		CHANNEL_DIR = channelDir;
	}

	public void updateChannel(String channelId, ChannelVariabilityModel model) {
		String channel = model.transformToChannel();

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
		String channel = convertXMLFileToString(CHANNEL_DIR + channelId + ".xml");
		ChannelVariabilityModel m = new TestChannelModel(channel);
		return m;
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
