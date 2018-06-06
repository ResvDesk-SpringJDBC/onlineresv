package com.telappoint.onlineresv.model.restws;

import java.util.List;

public class CompanyListResponse extends BaseResponse {
 
	private String selectCompanyLabel;
	private String selectDefaultCompanyId = "-1";
	private List<Options> companyList;
	
	public String getSelectCompanyLabel() {
		return selectCompanyLabel;
	}
	public void setSelectCompanyLabel(String selectCompanyLabel) {
		this.selectCompanyLabel = selectCompanyLabel;
	}
	public String getSelectDefaultCompanyId() {
		return selectDefaultCompanyId;
	}
	public void setSelectDefaultCompanyId(String selectDefaultCompanyId) {
		this.selectDefaultCompanyId = selectDefaultCompanyId;
	}
	public List<Options> getCompanyList() {
		return companyList;
	}
	public void setCompanyList(List<Options> companyList) {
		this.companyList = companyList;
	}
}
