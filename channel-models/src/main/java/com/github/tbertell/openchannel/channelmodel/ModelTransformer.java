package com.github.tbertell.openchannel.channelmodel;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ModelTransformer {

	private static final Map<String, Templates> CACHE = null;

	public String transform(ChannelVariabilityModel model, String channelId) {

		// TODO parametrit modelilta
		Map<String, String> params = new HashMap<String, String>();

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		try {
			// TODO add cache
			templates = stf.newTemplates(new StreamSource(this.getClass().getResourceAsStream(
					channelId + "2Channel.xsl")));

			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			for (Entry<String, String> entry : params.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}

			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(), new StreamResult(sw));

			return sw.toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	public ChannelVariabilityModel transform(String blueprint, String channelId) {
		return null;
	}
}
