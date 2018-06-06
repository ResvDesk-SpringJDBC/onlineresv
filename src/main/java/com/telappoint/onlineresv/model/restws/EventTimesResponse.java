package com.telappoint.onlineresv.model.restws;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class EventTimesResponse extends BaseResponse {
	private Map<String,String> availableTimes;
	
	public Map<String,String> getAvailableTimes() {
		return availableTimes;
	}

	public void setAvailableTimes(Map<String,String> availableTimes) {
		this.availableTimes = availableTimes;
	}
}
