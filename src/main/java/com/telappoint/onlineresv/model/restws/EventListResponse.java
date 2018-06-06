package com.telappoint.onlineresv.model.restws;

import java.util.List;

public class EventListResponse extends BaseResponse {

	private String selectEventLabel;
	private String selectDefaultEventId = "-1";
	private List<Options> eventList;

	public String getSelectEventLabel() {
		return selectEventLabel;
	}

	public void setSelectEventLabel(String selectEventLabel) {
		this.selectEventLabel = selectEventLabel;
	}

	public String getSelectDefaultEventId() {
		return selectDefaultEventId;
	}

	public void setSelectDefaultEventId(String selectDefaultEventId) {
		this.selectDefaultEventId = selectDefaultEventId;
	}

	public List<Options> getEventList() {
		return eventList;
	}

	public void setEventList(List<Options> eventList) {
		this.eventList = eventList;
	}

}
