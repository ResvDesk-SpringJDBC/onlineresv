package com.telappoint.onlineresv.model.restws;

public class EventDate {
	private String date;
	// 0=available 1=booked
	private String status;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
