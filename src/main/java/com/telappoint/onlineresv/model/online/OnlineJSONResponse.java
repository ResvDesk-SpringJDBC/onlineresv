package com.telappoint.onlineresv.model.online;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class OnlineJSONResponse {

	private boolean formErrors;
	private String response;
	private String message;
	private String firstName;
	private String lastName;
	
	private String scheduleId = "-1";
	
	private String welcomeHeader;
	
	@JsonIgnore
	private boolean notAvailDateEntered;
	
	public boolean isFormErrors() {
		return formErrors;
	}
	public void setFormErrors(boolean formErrors) {
		this.formErrors = formErrors;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getWelcomeHeader() {
		return welcomeHeader;
	}
	public void setWelcomeHeader(String welcomeHeader) {
		this.welcomeHeader = welcomeHeader;
	}
	public boolean isNotAvailDateEntered() {
		return notAvailDateEntered;
	}
	public void setNotAvailDateEntered(boolean notAvailDateEntered) {
		this.notAvailDateEntered = notAvailDateEntered;
	}
	
	
}
