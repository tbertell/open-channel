package com.github.tbertell.openchannel.channelmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimerLogChannelModel extends ChannelVariabilityModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6542809587871419249L;

	@XmlElement
	private Long timerPeriodInMillis;

	@XmlElement
	private String message;

	public TimerLogChannelModel() {
		super();
		setId("TimerLogChannel");
	}

	public Long getTimerPeriodInMillis() {
		return timerPeriodInMillis;
	}

	public void setTimerPeriodInMillis(Long timerPeriodInMillis) {
		this.timerPeriodInMillis = timerPeriodInMillis;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void validate() {
		// no implementation

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((timerPeriodInMillis == null) ? 0 : timerPeriodInMillis.hashCode());
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
		TimerLogChannelModel other = (TimerLogChannelModel) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (timerPeriodInMillis == null) {
			if (other.timerPeriodInMillis != null)
				return false;
		} else if (!timerPeriodInMillis.equals(other.timerPeriodInMillis))
			return false;
		return true;
	}

}
