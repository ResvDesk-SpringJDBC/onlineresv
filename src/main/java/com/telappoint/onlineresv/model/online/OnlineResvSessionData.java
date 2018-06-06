package com.telappoint.onlineresv.model.online;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.telappoint.onlineresv.model.restws.EventDatesResponse;


public class OnlineResvSessionData {

	private String transId;
	private String token;
	private String clientCode;
	private String loginFirst;
	private String langCode;
	
	private Long customerId;
	
	//private List<LoginField> loginFieldList;
	
	@JsonIgnore
	private EventDatesResponse eventDatesResponse; //This is added to validate the selected date whenever we are holding reservation.
	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getLoginFirst() {
		return loginFirst;
	}
	public void setLoginFirst(String loginFirst) {
		this.loginFirst = loginFirst;
	}
	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/*public List<LoginField> getLoginFieldList() {
		return loginFieldList;
	}
	public void setLoginFieldList(List<LoginField> loginFieldList) {
		this.loginFieldList = loginFieldList;
	}*/
	public EventDatesResponse getEventDatesResponse() {
		return eventDatesResponse;
	}
	public void setEventDatesResponse(EventDatesResponse eventDatesResponse) {
		this.eventDatesResponse = eventDatesResponse;
	}
}
