package com.github.tbertell.openchannel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;

public class LoadTest {

	public static void main(String[] args) throws Exception {

		List<LoadTestResult> times = new LinkedList<LoadTestResult>();
		try {
			for (int i = 0; i < 40; i++) {
				StockQuoteChannelModel channel = getChannel();
				times.add(new LoadTestResult(getQuote(), channel));
//				Thread.sleep(1500);
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		Thread.sleep(5000);
		for (LoadTestResult l : times) {
			System.out.println("Response time " + l.responseTime + " channel " + l.channel + " date " + l.date);
		}
	}

	private static long getQuote() throws IOException, ClientProtocolException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:9000/stockquote/NOK");
		getRequest.addHeader("accept", "text/html");
		long startime = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(getRequest);
		long endtime = System.currentTimeMillis();
		if (response.getStatusLine().getStatusCode() != 200) {
			// throw new RuntimeException("Failed : HTTP error code : " +
			// response.getStatusLine().getStatusCode());
			return 0;
		}

		// BufferedReader br = new BufferedReader(new
		// InputStreamReader((response.getEntity().getContent())));
		//
		// String output;
		// System.out.println("Output from Server .... \n");
		// while ((output = br.readLine()) != null) {
		// System.out.println(output);
		// }

		httpClient.getConnectionManager().shutdown();
		return (endtime - startime);
	}

	private static StockQuoteChannelModel getChannel() throws IOException, ClientProtocolException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:8080/channels/StockQuoteChannel");
		getRequest.addHeader("accept", "application/xml");
		HttpResponse response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			// throw new RuntimeException("Failed : HTTP error code : " +
			// response.getStatusLine().getStatusCode());
			return null;
		}
		StockQuoteChannelModel model = getModel(response.getEntity().getContent());
		httpClient.getConnectionManager().shutdown();

		return model;
	}

	private static StockQuoteChannelModel getModel(InputStream stream) {
		try {
			JAXBContext context = JAXBContext.newInstance(ChannelVariabilityModel.class);

			// unmarshal from foo.xml
			Unmarshaller u = context.createUnmarshaller();

			StockQuoteChannelModel resultJaxb = (StockQuoteChannelModel) u.unmarshal(stream);
			return resultJaxb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static class LoadTestResult {
		public Long responseTime;
		public StockQuoteChannelModel channel;
		public Date date;

		public LoadTestResult(Long responseTime, StockQuoteChannelModel channel) {
			super();
			this.responseTime = responseTime;
			this.channel = channel;
			this.date = new Date();
		}

	}

}
