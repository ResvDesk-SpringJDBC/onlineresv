package com.telappoint.onlineresv.common;

public enum CommonDateContants {
	EMPTY_STRING(""),
	REST_WS_DATE_FORMAT("yyyy-MM-dd"),
	FRONT_END_DATE_FORMAT("MM/dd/yyyy"),
	
	REST_WS_TIME_FORMAT("hh:mm a"),
	FRONT_END_TIME_FORMAT("h:mm a");
	
	private String value;

	private CommonDateContants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
