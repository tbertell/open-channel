package com.github.tbertell.openchannel.channelmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SecondTestChannelModel extends ChannelVariabilityModel {

	private static final long serialVersionUID = -2320114219536439363L;

	@XmlElement
	private Integer timeout;

	
	public SecondTestChannelModel() {
		super();
		setId("SecondTestChannel");
		setDescription("I'm just a test channel");
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
