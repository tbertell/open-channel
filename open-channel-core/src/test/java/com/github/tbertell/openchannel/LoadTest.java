package com.github.tbertell.openchannel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoadTest {

	public static void main(String[] args) throws Exception {
		List<Long> times = new LinkedList<Long>();
		try {
			for (int i = 0; i < 20; i++) {
				times.add(getQuote());
				Thread.sleep(1500);
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		Thread.sleep(5000);
		for (Long l : times) {
			System.out.println("Response time " + l);
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
}
