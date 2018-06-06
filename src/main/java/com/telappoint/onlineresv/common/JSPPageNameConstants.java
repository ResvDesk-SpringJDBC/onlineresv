package com.telappoint.onlineresv.common;

/**
 * 
 * @author Murali
 * 
 */
public enum JSPPageNameConstants {
	
	RESVDESK_LOGIN_FIRST_PAGE("login_first"),
	RESVDESK_LOGIN_LAST_PAGE("login_last"),
	
	RESVESK_LOGIN_LEFT_SECTION("login_left_section"),
	RESVDESK_LOGIN_RIGHT_SECTION("login_right_section"),
	
	RESVDESK_RESERVATION_LEFT_SECTION("reservation_left_section"),
	RESVDESK_RESERVATION_RIGHT_SECTION("reservation_right_section"),
	
	RESVDESK_RESERVATION_VERIFICATION_LEFT_SECTION("res_verification_left_section"),
	RESVDESK_RESERVATION_VERIFICATION_RIGHT_SECTION("res_verification_right_section"),
	
	RESVDESK_CONFIRMATION_LEFT_SECTION("confirmation_left_section"),
	RESVDESK_CONFIRMATION_RIGHT_SECTION("confirmation_right_section"),
	
	RESVDESK_POPULATE_PAGE("populate"),
	RESVDESK_POPULATE_CAL_DATA_PAGE("populateCalData"),
	RESVDESK_POPULATE_TIME_PAGE("populateTime"),
	RESVDESK_POPULATE_EVENTS_SEATS_PAGE("populateEventSeats"),
	
	RESVDESK_RESERVED_EVENT_RESPONSE("reserved_Event_Response"),
	
	RESVDESK_SCHEDULED_EVENT_RESPONSE("scheduled_sections"),
	
	RESVDESK_SCHEDULE_CLOSED_PAGE("closed"),
	RESVDESK_NO_FUNDING_PAGE("no_funding"),
	RESVDESK_MEMPHIS_DETAILS("landing_page"),
	RESVDESK_INDEX("index"),
	RESVDESK_ERROR_PAGE("error"),
	RESVDESK_SESSION_EXPIRED_PAGE("session-expired");
	
	private String viewName;

	private JSPPageNameConstants(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}
}
