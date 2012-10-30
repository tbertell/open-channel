package com.github.tbertell.openchannel.channel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StockQuoteChannelModel extends ChannelVariabilityModel {

	private static final long serialVersionUID = 7132028137150247870L;
	
	public StockQuoteChannelModel() {
		super();
		setId("StockQuoteChannel");
		setDescription("Channel for getting stock quote.");
	}

	@XmlElement
	private Boolean useCache;
	
	@XmlElement
	private Long cacheTTL;
	
	@XmlElement
	private Long responseTimeLimit; 

	@XmlElement
	private StockQuoteServiceProvider serviceProvider;
	
	@Override
	public void validate() {
		// not needed

	}

	public Boolean getUseCache() {
		return useCache;
	}

	public void setUseCache(Boolean useCache) {
		this.useCache = useCache;
	}

	public Long getCacheTTL() {
		return cacheTTL;
	}

	public void setCacheTTL(Long cacheTTL) {
		this.cacheTTL = cacheTTL;
	}

	public Long getResponseTimeLimit() {
		return responseTimeLimit;
	}

	public void setResponseTimeLimit(Long resposeTimeLimit) {
		this.responseTimeLimit = resposeTimeLimit;
	}

	public StockQuoteServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(StockQuoteServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((cacheTTL == null) ? 0 : cacheTTL.hashCode());
		result = prime
				* result
				+ ((responseTimeLimit == null) ? 0 : responseTimeLimit.hashCode());
		result = prime * result
				+ ((serviceProvider == null) ? 0 : serviceProvider.hashCode());
		result = prime * result
				+ ((useCache == null) ? 0 : useCache.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockQuoteChannelModel other = (StockQuoteChannelModel) obj;
		if (cacheTTL == null) {
			if (other.cacheTTL != null)
				return false;
		} else if (!cacheTTL.equals(other.cacheTTL))
			return false;
		if (responseTimeLimit == null) {
			if (other.responseTimeLimit != null)
				return false;
		} else if (!responseTimeLimit.equals(other.responseTimeLimit))
			return false;
		if (serviceProvider != other.serviceProvider)
			return false;
		if (useCache == null) {
			if (other.useCache != null)
				return false;
		} else if (!useCache.equals(other.useCache))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StockQuoteChannelModel [useCache=" + useCache + ", cacheTTL="
				+ cacheTTL + ", resposeTimeLimit=" + responseTimeLimit
				+ ", serviceProvider=" + serviceProvider + "]";
	}

}
