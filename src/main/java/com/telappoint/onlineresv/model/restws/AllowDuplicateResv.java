package com.telappoint.onlineresv.model.restws;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Murali G
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AllowDuplicateResv extends BaseResponse {
	
	private boolean allowDuplicateResv;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAllowDuplicateResv() {
		return allowDuplicateResv;
	}

	public void setAllowDuplicateResv(boolean allowDuplicateResv) {
		this.allowDuplicateResv = allowDuplicateResv;
	}
}
