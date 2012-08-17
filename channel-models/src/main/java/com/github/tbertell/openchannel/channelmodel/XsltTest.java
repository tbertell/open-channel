package com.github.tbertell.openchannel.channelmodel;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltTest {

	public static void main(String[] args) throws Exception {
		File xmlFile = new File("src/main/resources/com/github/tbertell/openchannel/channelmodel/blueprint.xml");

		Source xmlSource = new StreamSource(xmlFile);

		SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();
		Templates templates;

		// TODO add cache
		templates = stf.newTemplates(new StreamSource((new XsltTest()).getClass().getResourceAsStream(
				"TestChannel2Model.xsl")));

		Transformer transformer = templates.newTransformer();
		transformer.setOutputProperty("indent", "yes");

		transformer.transform(xmlSource, new StreamResult(System.out));
		// trans.transform(null, new StreamResult(new
		// FileOutputStream("test.html")));
	}
}
