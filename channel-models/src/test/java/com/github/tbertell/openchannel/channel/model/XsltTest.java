package com.github.tbertell.openchannel.channel.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;

public class XsltTest {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File("src/main/resources/com/github/tbertell/openchannel/channel/model/blueprint.xml");

		Source xmlSource = new StreamSource(xmlFile);

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		templates = stf.newTemplates(new StreamSource((new XsltTest()).getClass().getResourceAsStream(
				"TimerLogChannel2Model.xsl")));

		Transformer transformer = templates.newTransformer();
		transformer.setOutputProperty("indent", "yes");

		transformer.transform(xmlSource, new StreamResult(System.out));

		JAXBResult result = new JAXBResult(JAXBContext.newInstance(ChannelVariabilityModel.class));
		transformer.transform(xmlSource, result);
		Object o = result.getResult();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		StreamResult rs = new StreamResult(os);
		transformer.transform(xmlSource, rs);

		templates = stf.newTemplates(new StreamSource((new XsltTest()).getClass().getResourceAsStream(
				"TimerLogChannelModel2Channel.xsl")));
		transformer = templates.newTransformer();
		transformer.setOutputProperty("indent", "yes");

		System.out.println("********************************");
		Source s = new StreamSource(new ByteArrayInputStream(os.toByteArray()));
		// transformer.setOutputProperty("b;http://xml.apache.org/xsltd;indent-amount",
		// "4");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(s, new StreamResult(System.out));
	}
}
