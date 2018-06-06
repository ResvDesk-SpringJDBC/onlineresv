package com.telappoint.onlineresv.model.restws;

import java.util.List;

public class DepartmentListResponse extends BaseResponse {

	private String selectDepartmentLabel;
	private String selectDefaultDepartmentId = "-1";
	private List<Options> departmentList;

	public String getSelectDepartmentLabel() {
		return selectDepartmentLabel;
	}

	public void setSelectDepartmentLabel(String selectDepartmentLabel) {
		this.selectDepartmentLabel = selectDepartmentLabel;
	}

	public String getSelectDefaultDepartmentId() {
		return selectDefaultDepartmentId;
	}

	public void setSelectDefaultDepartmentId(String selectDefaultDepartmentId) {
		this.selectDefaultDepartmentId = selectDefaultDepartmentId;
	}

	public List<Options> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Options> departmentList) {
		this.departmentList = departmentList;
	}
}
