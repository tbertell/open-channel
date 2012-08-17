package com.github.tbertell.openchannel.channelmodel;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestChannelModel extends ChannelVariabilityModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6542809587871419249L;

	@XmlElement
	private Long timerPeriodInMillis;

	@XmlElement
	private String message;

	public TestChannelModel() {
		super();
	}

	public TestChannelModel(String channel) {

		if (channel != null) {
			transformFromChannel(channel);
		}
	}

	@Override
	public String transformToChannel() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("timerPeriodInMillis", timerPeriodInMillis.toString());
		params.put("message", message);

		return (new ModelTransformer()).transform(params, this.getId());
	}

	@Override
	public void transformFromChannel(String channel) {
		int beginning = channel.indexOf("value=\"");
		int end = channel.indexOf("\"/>", beginning);
		String message = channel.substring(beginning + 7, end);

		beginning = channel.indexOf("timer:foo?period=");
		end = channel.indexOf("\"/>", beginning);

		String millis = channel.substring(beginning + 17, end);
		Long timerInMillis = Long.valueOf(millis);

		this.message = message;
		this.timerPeriodInMillis = timerInMillis;
	}

	public String format(String unformattedXml) {
		try {
			final Document document = parseXmlFile(unformattedXml);

			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);

			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Long getTimerPeriodInMillis() {
		return timerPeriodInMillis;
	}

	public void setTimerPeriodInMillis(Long timerPeriodInMillis) {
		this.timerPeriodInMillis = timerPeriodInMillis;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
