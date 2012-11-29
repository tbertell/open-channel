package com.github.tbertell.openchannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;

public class LoadTest {

	private static AtomicInteger counter = new AtomicInteger(0);

	private static long endtime = 0;

	public static void main(String[] args) throws Exception {

		List<LoadTestResult> times = new LinkedList<LoadTestResult>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			new Thread("Thread" + i) {
				public void run() {
					for (int i = 0; i < 20; i++) {
						try {
							int c = counter.incrementAndGet();
							long start = System.currentTimeMillis();

							String result = getQuote();
							long loppu = System.currentTimeMillis();
							
							System.out.println("Taski " + c +" " +Thread.currentThread().getName() +" on valmis " + (loppu - endtime) + " tulos " + result);
							endtime = loppu;
							Thread.sleep(new Random().nextInt(200));
						} catch (Exception e) {

						}
					}
				}
			}.start();
		}

		Thread.sleep(100000);
		System.out.println("Total time " + (endtime - start));
		for (LoadTestResult l : times) {
			// System.out.println("Response time " + l.responseTime +
			// " channel " + l.channel + " date " + l.date);
		}
	}

	private static String getQuote() throws IOException, ClientProtocolException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:9000/stockquote/NOK");
		getRequest.addHeader("accept", "text/html");
		long startime = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(getRequest);
		long endtime = System.currentTimeMillis();
		if (response.getStatusLine().getStatusCode() != 200) {
			// throw new RuntimeException("Failed : HTTP error code : " +
			// response.getStatusLine().getStatusCode());
			return response.getStatusLine().getStatusCode() + "";
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		StringBuilder result = new StringBuilder();

		while ((output = br.readLine()) != null) {
			result.append(output);
			break;
		}

		httpClient.getConnectionManager().shutdown();
		return result.toString();
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
		public DateTime date;

		public LoadTestResult(Long responseTime, StockQuoteChannelModel channel) {
			super();
			this.responseTime = responseTime;
			this.channel = channel;
			this.date = new DateTime();
		}

	}

}
