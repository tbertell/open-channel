package com.github.tbertell.openchannel;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.tbertell.openchannel.channelmodel.ChannelVariabilityModel;

public class ChannelManagerTest {

	private static final String FS = System.getProperty("file.separator");
	private static final String TEST_RESOURCES = FS + "src" + FS + "test" + FS + "resources" + FS + "com" + FS
			+ "github" + FS + "tbertell" + FS + "openchannel" + FS;

	@Test
	public void shouldGetChannel() {

	}

	@Test
	public void shouldListChannels() {
		String userDir = System.getProperty("user.dir");
		String channelDir = userDir + TEST_RESOURCES;

		ChannelManager manager = new ChannelManager(channelDir);

		List<ChannelVariabilityModel> list = manager.listChannels();

		Assert.assertEquals(list.size(), 2);
	}
}
