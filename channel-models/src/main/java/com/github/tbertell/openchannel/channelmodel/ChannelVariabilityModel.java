package com.github.tbertell.openchannel.channelmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({TestChannelModel.class, SecondTestChannelModel.class})
public abstract class ChannelVariabilityModel {
	
	@XmlElement	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public abstract String transformToCamelRoute();
	
}
