package com.telappoint.onlineresv.model.restws;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ResvDetailsRightSideContentResponse extends BaseResponse {
	
	// online field
	private String resvDetailsRightSideContent;

	public String getResvDetailsRightSideContent() {
		return resvDetailsRightSideContent;
	}

	public void setResvDetailsRightSideContent(String resvDetailsRightSideContent) {
		this.resvDetailsRightSideContent = resvDetailsRightSideContent;
	}
}
