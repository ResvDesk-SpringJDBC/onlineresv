package com.telappoint.onlineresv.common;

/**
 * @author Murali
 */
public enum OnlineResContants {

	EMPTY_STRING(""),
	ONLINE("online"),
	
	/*CLIENT_INFO_SESSION_VARIABLE("clientInfo"),
	CUSTOMERID_SESSION_VARIABLE("customerId"),*/
	ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE("sessionData"),

	SYSTME_ERROR("Some System Error. Please try again later.");
	
	private String value;

	private OnlineResContants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
