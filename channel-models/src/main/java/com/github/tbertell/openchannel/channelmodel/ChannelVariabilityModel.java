package com.github.tbertell.openchannel.channelmodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ TimerLogChannelModel.class, SecondTestChannelModel.class })
public abstract class ChannelVariabilityModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6352231488578833070L;

	@XmlElement
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract void validate();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelVariabilityModel other = (ChannelVariabilityModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
