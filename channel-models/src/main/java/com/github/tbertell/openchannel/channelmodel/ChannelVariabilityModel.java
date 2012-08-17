package com.github.tbertell.openchannel.channelmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ TestChannelModel.class, SecondTestChannelModel.class })
public abstract class ChannelVariabilityModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6352231488578833070L;

	@XmlElement
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract String transformToChannel();

	public abstract void setValuesFromChannel(String channel);

	public String convertXMLFileToString(String fileName) {
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
