package com.telappoint.onlineresv.model.restws;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginInfoRightSideContentResponse extends BaseResponse {
	
	// online field
	private String loginInfoRightSideContent;

	public String getLoginInfoRightSideContent() {
		return loginInfoRightSideContent;
	}

	public void setLoginInfoRightSideContent(String loginInfoRightSideContent) {
		this.loginInfoRightSideContent = loginInfoRightSideContent;
	}
}
