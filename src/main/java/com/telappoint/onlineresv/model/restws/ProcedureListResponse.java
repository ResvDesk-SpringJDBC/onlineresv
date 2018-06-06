package com.telappoint.onlineresv.model.restws;

import java.util.List;

public class ProcedureListResponse extends BaseResponse {

	private String selectProcedureLabel;
	private String selectDefaultProcedureId = "-1";
	private List<Options> procedureList;

	public String getSelectProcedureLabel() {
		return selectProcedureLabel;
	}

	public void setSelectProcedureLabel(String selectProcedureLabel) {
		this.selectProcedureLabel = selectProcedureLabel;
	}

	public String getSelectDefaultProcedureId() {
		return selectDefaultProcedureId;
	}

	public void setSelectDefaultProcedureId(String selectDefaultProcedureId) {
		this.selectDefaultProcedureId = selectDefaultProcedureId;
	}

	public List<Options> getProcedureList() {
		return procedureList;
	}

	public void setProcedureList(List<Options> procedureList) {
		this.procedureList = procedureList;
	}

}
