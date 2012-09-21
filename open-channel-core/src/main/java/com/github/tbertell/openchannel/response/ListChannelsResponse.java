package com.github.tbertell.openchannel.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "channels")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListChannelsResponse {

	@XmlElement(name = "channel")
	private List<ChannelResponse> list;

	public List<ChannelResponse> getList() {
		if (this.list == null) {
			this.list = new ArrayList<ChannelResponse>();
		}
		return list;
	}

	public void setList(List<ChannelResponse> list) {
		this.list = list;
	}

	public void addChannelResponse(ChannelResponse response) {
		getList().add(response);
	}

	@Override
	public String toString() {
		return "ListChannelsResponse [list=" + list + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
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
		ListChannelsResponse other = (ListChannelsResponse) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

}
