package com.github.tbertell.openchannel.reconfiguration;

import java.util.Map;

public interface AdaptationPolicy<T> {

	public T reconfigure(Map<String, String> params, T model);

}