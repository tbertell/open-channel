package com.github.tbertell.openchannel.channelmodel;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ModelTransformer {

	private static final Map<String, Templates> CACHE = null;

	public String transformFromModel(ChannelVariabilityModel model, String channelId) {

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

	public ChannelVariabilityModel transformToModel(String blueprint, String channelId) {
		Source xmlSource = new StreamSource();

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		ChannelVariabilityModel model = null;
		try {
			templates = stf.newTemplates(new StreamSource((new XsltTest()).getClass().getResourceAsStream(
					channelId +"2Model.xsl")));

			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			transformer.transform(xmlSource, new StreamResult(System.out));

			JAXBResult result = new JAXBResult(
					JAXBContext.newInstance(ChannelVariabilityModel.class));
			transformer.transform(xmlSource, result);
		

			model = (ChannelVariabilityModel) result.getResult();
		} catch (Exception e) {

		}
		return model;
	}
}
