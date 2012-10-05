package com.github.tbertell.openchannel.channel.transform;

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

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public class ModelXslTransformer implements ModelTransformer {

	private static final Map<String, Templates> CACHE = null;

	/* (non-Javadoc)
	 * @see com.github.tbertell.openchannel.channelmodel.ModelTransformer#transformFromModel(com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel)
	 */
	@Override
	public String transformFromModel(ChannelVariabilityModel model) {

		try {

			// set up XSLT transformation
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates templates = factory.newTemplates(new StreamSource((new ModelXslTransformer()).getClass()
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

	/* (non-Javadoc)
	 * @see com.github.tbertell.openchannel.channelmodel.ModelTransformer#transformToModel(java.lang.String, java.lang.String)
	 */
	@Override
	public ChannelVariabilityModel transformToModel(String blueprint, String channelId) {

		Source xmlSource = new StreamSource(new java.io.StringReader(blueprint));

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		ChannelVariabilityModel model = null;
		try {
			templates = stf.newTemplates(new StreamSource((new ModelXslTransformer()).getClass().getResourceAsStream(
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
