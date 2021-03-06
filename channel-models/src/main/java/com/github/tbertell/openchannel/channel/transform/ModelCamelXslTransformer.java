package com.github.tbertell.openchannel.channel.transform;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

/**
 * 
 * Xsl implementation of model tranformer.
 *
 */
public class ModelCamelXslTransformer implements ModelTransformer {

	// counter used for versioning of osgi bundles
	private static final AtomicInteger COUNTER = new AtomicInteger(0);

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelCamelXslTransformer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tbertell.openchannel.channelmodel.ModelTransformer#
	 * transformFromModel
	 * (com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel)
	 */
	@Override
	public String transformFromModel(ChannelVariabilityModel model) throws Exception {

		String result = null;
		try {
			// set up XSLT transformation
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates templates = factory.newTemplates(new StreamSource((new ModelCamelXslTransformer()).getClass()
					.getResourceAsStream(model.getId() + "Model2Channel.xsl")));
			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			int counter = COUNTER.incrementAndGet();
			transformer.setParameter("counter", counter);
			// run transformation
			StringWriter sw = new StringWriter();
			JAXBSource source = new JAXBSource(JAXBContext.newInstance(ChannelVariabilityModel.class), model);
			transformer.transform(source, new StreamResult(sw));

			result = sw.toString();

		} catch (Exception e) {
			LOGGER.error("XSL transformation failed for model " + model, e);
			throw e;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tbertell.openchannel.channelmodel.ModelTransformer#
	 * transformToModel(java.lang.String, java.lang.String)
	 */
	@Override
	public ChannelVariabilityModel transformToModel(String blueprint, String channelId) throws Exception {

		Source xmlSource = new StreamSource(new java.io.StringReader(blueprint));

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		ChannelVariabilityModel model = null;
		try {
			templates = stf.newTemplates(new StreamSource((new ModelCamelXslTransformer()).getClass().getResourceAsStream(
					channelId + "2Model.xsl")));

			Transformer transformer = templates.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			JAXBResult result = new JAXBResult(JAXBContext.newInstance(ChannelVariabilityModel.class));
			transformer.transform(xmlSource, result);

			model = (ChannelVariabilityModel) result.getResult();
		} catch (Exception e) {
			LOGGER.error("XSL transformation failed for channel " + channelId, e);
			throw e;
		}
		return model;
	}
}
