package com.telappoint.onlineresv.model.restws;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ConfirmationPageRightSideContentResponse extends BaseResponse {
	
	// online field
	private String confirmationPageRightSideContent;

	public String getConfirmationPageRightSideContent() {
		return confirmationPageRightSideContent;
	}

	public void setConfirmationPageRightSideContent(String confirmationPageRightSideContent) {
		this.confirmationPageRightSideContent = confirmationPageRightSideContent;
	}
}
