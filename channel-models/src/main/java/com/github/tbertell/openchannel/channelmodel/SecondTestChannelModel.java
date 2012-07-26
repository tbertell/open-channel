package com.github.tbertell.openchannel.channelmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SecondTestChannelModel extends ChannelVariabilityModel {

	@XmlElement
	private Integer timeout;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public String transformToCamelRoute() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
