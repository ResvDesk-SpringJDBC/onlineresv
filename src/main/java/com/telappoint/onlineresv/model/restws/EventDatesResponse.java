package com.telappoint.onlineresv.model.restws;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class EventDatesResponse extends BaseResponse {
	private Map<String, EventDate> eventDateMap;

	public Map<String,EventDate> getEventDateMap() {
		return eventDateMap;
	}

	public void setEventDateMap(Map<String,EventDate> eventDateMap) {
		this.eventDateMap = eventDateMap;
	}
}
