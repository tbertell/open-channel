package com.github.tbertell.openchannel;

import java.math.BigInteger;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channel.model.ChannelVariabilityModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteChannelModel;
import com.github.tbertell.openchannel.channel.model.StockQuoteServiceProvider;

public class ChannelManagerTest {

	private static final String FS = System.getProperty("file.separator");
	private static final String TEST_RESOURCES = FS + "target" + FS + "test-classes" + FS + "com" + FS
			+ "github" + FS + "tbertell" + FS + "openchannel" + FS;
	
	private static final String USER_DIR = System.getProperty("user.dir");

	@Test(priority = 4)
	public void shouldGetChannel() {
		String channelDir = USER_DIR + TEST_RESOURCES;
		System.out.println(channelDir);

		ChannelManager manager = new CamelChannelManager(channelDir);
		ChannelVariabilityModel model = manager.getChannel("TimerLogChannel");
		Assert.assertEquals(model.getId(), "TimerLogChannel");

	}

	@Test(priority = 3)
	public void shouldDeleteChannel() {
		String channelDir = USER_DIR + TEST_RESOURCES;
		ChannelManager manager = new CamelChannelManager(channelDir);
		manager.deleteChannel("StockQuoteChannel");
		
		boolean gotException = false;
		try {
			manager.deleteChannel("StockQuoteChannel");
		} catch (RuntimeException e) {
			gotException = true;
		}
		Assert.assertTrue(gotException);
	}
	
	@Test(priority = 2)
	public void shouldCreateChannel() {
		String channelDir = USER_DIR + TEST_RESOURCES;
		ChannelManager manager = new CamelChannelManager(channelDir);
		StockQuoteChannelModel model = new StockQuoteChannelModel();
		model.setServiceProvider(StockQuoteServiceProvider.PRIMARY);
		model.setResponseTimeLimit(new BigInteger("1000"));
		model.setUseCache(Boolean.FALSE);
		manager.updateChannel("StockQuoteChannel", model);
	}
	
	@Test(priority = 2)
	public void shouldListChannels() {
		String channelDir = USER_DIR + TEST_RESOURCES;
		System.out.println(channelDir);

		ChannelManager manager = new CamelChannelManager(channelDir);

		List<ChannelVariabilityModel> list = manager.listChannels();

		Assert.assertEquals(list.size(), 2);
	}
}
