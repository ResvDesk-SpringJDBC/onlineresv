package com.telappoint.onlineresv.model.restws;

import java.util.List;

public class LocationListResponse extends BaseResponse {

	private String selectLocationLabel;
	private String selectDefaultLocationId = "-1";
	private List<Options> locationList;

	public String getSelectLocationLabel() {
		return selectLocationLabel;
	}

	public void setSelectLocationLabel(String selectLocationLabel) {
		this.selectLocationLabel = selectLocationLabel;
	}

	public String getSelectDefaultLocationId() {
		return selectDefaultLocationId;
	}

	public void setSelectDefaultLocationId(String selectDefaultLocationId) {
		this.selectDefaultLocationId = selectDefaultLocationId;
	}

	public List<Options> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Options> locationList) {
		this.locationList = locationList;
	}
}
