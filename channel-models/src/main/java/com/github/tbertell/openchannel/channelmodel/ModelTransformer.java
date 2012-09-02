package com.github.tbertell.openchannel.channelmodel;

import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ModelTransformer {

	private static final Map<String, Templates> CACHE = null;

	public String transformFromModel(ChannelVariabilityModel model) {

		try {

			// set up XSLT transformation
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates templates = factory.newTemplates(new StreamSource((new ModelTransformer()).getClass()
					.getResourceAsStream(model.getId() + "Model2Channel.xsl")));
			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			// run transformation
			StringWriter sw = new StringWriter();
			JAXBSource source = new JAXBSource(JAXBContext.newInstance(ChannelVariabilityModel.class), model);
			transformer.transform(source, new StreamResult(sw));

			return sw.toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	public ChannelVariabilityModel transformToModel(String blueprint, String channelId) {

		// TODO muunna string Sourceksi
		Source xmlSource = new StreamSource();

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		ChannelVariabilityModel model = null;
		try {
			templates = stf.newTemplates(new StreamSource((new ModelTransformer()).getClass().getResourceAsStream(
					channelId + "2Model.xsl")));

			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			JAXBResult result = new JAXBResult(JAXBContext.newInstance(ChannelVariabilityModel.class));
			transformer.transform(xmlSource, result);

			model = (ChannelVariabilityModel) result.getResult();
		} catch (Exception e) {

		}
		return model;
	}
}
