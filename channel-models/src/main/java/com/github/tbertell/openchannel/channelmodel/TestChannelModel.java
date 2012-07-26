package com.github.tbertell.openchannel.channelmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestChannelModel extends ChannelVariabilityModel {

	@XmlElement
	private Boolean logging;

	public Boolean getLogging() {
		return logging;
	}

	public void setLogging(Boolean logging) {
		this.logging = logging;
	}

	@Override
	public String transformToCamelRoute() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
