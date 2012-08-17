package com.github.tbertell.openchannel.channelmodel;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
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

	@XmlTransient
	private final String CHANNEL_DIR = "/home/tomppa/temp/";

	@XmlElement
	private Long timerPeriodInMillis;

	@XmlElement
	private String message;

	public TestChannelModel() {
		super();
	}

	public TestChannelModel(String id) {
		String channel = convertXMLFileToString(CHANNEL_DIR + id + ".xml");
		if (channel != null) {
			setValuesFromChannel(channel);
		}
	}

	@Override
	public String transformToChannel() {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<blueprint xmlns=\"http://www.osgi.org/xmlns/blueprint/v1.0.0\""
				+ "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				+ "       xmlns:camel=\"http://camel.apache.org/schema/blueprint\""
				+ "       xsi:schemaLocation=\""
				+ "       http://www.osgi.org/xmlns/blueprint/v1.0.0 http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd"
				+ "       http://camel.apache.org/schema/blueprint http://camel.apache.org/schema/blueprint/camel-blueprint.xsd\">");

		sb.append("  <bean id=\"helloBean\" class=\"com.github.tbertell.openchannel.camel.channel.HelloBean\">")
				.append("<property name=\"say\" value=\"")
				.append(message)
				.append("\"/>")
				.append("</bean>")
				.append("")
				.append(""
						+ "<camelContext id=\"blueprintContext\" trace=\"false\" xmlns=\"http://camel.apache.org/schema/blueprint\">"
						+ "  <route id=\"timerToLog\">" + "    <from uri=\"timer:foo?period=" + timerPeriodInMillis
						+ "\"/>" + "    <setBody>" + "        <method ref=\"helloBean\" method=\"hello\"/> "
						+ "    </setBody>" + "    <log message=\"The message contains ${body}\"/>" + "  </route> "
						+ "</camelContext>" + "" + "</blueprint>");

		return format(sb.toString());
	}

	@Override
	public void setValuesFromChannel(String channel) {
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

}
