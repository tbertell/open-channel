package com.github.tbertell.openchannel;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.BeforeClass;

public class HelloWorldIT {
	private static String endpointUrl;

	@BeforeClass
	public static void beforeClass() {
		// endpointUrl = System.getProperty("service.url");
		endpointUrl = "http://localhost:8080";
	}

	// @Test
	public void testPing() throws Exception {
		WebClient client = WebClient.create(endpointUrl + "/channel/channel1");
		Response r = client.accept("application/xml").get();
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
		String value = IOUtils.toString((InputStream) r.getEntity());
		assertEquals("<thisisxml>xml</thisisxml>", value);
	}

	// @Test
	// public void testJsonRoundtrip() throws Exception {
	// List<Object> providers = new ArrayList<Object>();
	// providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
	// JsonBean inputBean = new JsonBean();
	// inputBean.setVal1("Maple");
	// WebClient client = WebClient.create(endpointUrl + "/hello/jsonBean",
	// providers);
	// Response r = client.accept("application/json")
	// .type("application/json")
	// .post(inputBean);
	// assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
	// MappingJsonFactory factory = new MappingJsonFactory();
	// JsonParser parser = factory.createJsonParser((InputStream)r.getEntity());
	// JsonBean output = parser.readValueAs(JsonBean.class);
	// assertEquals("Maple", output.getVal2());
	// }
}
