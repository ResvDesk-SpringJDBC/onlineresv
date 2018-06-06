package com.telappoint.onlineresv.model.restws;

public class AvailableDates {
	private String date;
	// 0=available 1=not available 2=holiday 3=closed 4=not opened
	private String availableStatus;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(String availableStatus) {
		this.availableStatus = availableStatus;
	}
}
