package com.telappoint.onlineresv.common;

/**
 * @author Murali
 *
 */
public enum OnlineResRestClientConstants {
	ONLINE_RESVDESK_REST_SERVICE_GET_CLIENT_INFO("getClientInfo?clientCode=@clientCodeParam@&device=@deviceParam@&param1=@param1Param@&param2=@param2Param@&loginFirst=@loginFirstParam@&langCode=@langCodeParam@"),
	ONLINE_RESVDESK_REST_SERVICE_AUTHENTICATE_CUSTOMR("authenticateCustomer?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&loginParams=@loginParamsParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_FIELDS_LIST("getLoginInfo?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_RESV_DETAILS_SECTION_INFO("getResvDetailsSelectionInfo?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@"),
		
	ONLINE_RESVDESK_REST_SERVICE_GET_COMPANY_LIST("getCompanyList?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@"),	
	ONLINE_RESVDESK_REST_SERVICE_GET_PROCEDURE_LIST("getProcedureList?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&companyId=@companyIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_LOCATION_LIST("getLocationList?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&procedureId=@procedureIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_DEPARTMENT_LIST("getDepartmentList?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&locationId=@locationIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_LIST("getEventList?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&departmentId=@departmentIdParam@&locationId=@locationIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_DATES("getEventDates?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&eventId=@eventIdParam@&locationId=@locationIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_TIMES("getEventTimes?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&eventId=@eventIdParam@&locationId=@locationIdParam@&date=@dateParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_SEATS("getEventSeats?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&eventId=@eventIdParam@&eventDateTimeId=@eventDateTimeIdParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_HOLD_RESERVATION("holdReservation?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&companyId=@companyIdParam@&procedureId=@procedureIdParam@&locationId=@locationIdParam@&departmentId=@departmentIdParam@&eventId=@eventIdParam@&seatId=@seatIdParam@&eventDateTimeId=@eventDateTimeIdParam@&customerId=@customerIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_RESERVATION_VERIFICATION("getVerifyReservationDetails?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&scheduleId=@scheduleIdParam@&customerId=@customerIdParam@&loginFirst=@loginFirstParam@"),
	ONLINE_RESVDESK_REST_SERVICE_CONFIRM_RESERVATION("confirmReservation?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&scheduleId=@scheduleIdParam@&customerId=@customerIdParam@&comment=@commentParam@"),
	ONLINE_RESVDESK_REST_SERVICE_RELEASE_HOLDED_EVENT_TIME("releaseHoldEventTime?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&scheduleId=@scheduleIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_CANCEL_RESERVATION("cancelReservation?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&scheduleId=@scheduleIdParam@&customerId=@customerIdParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS("?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_RIGHT_SIDE_CONTENT("getLoginInfoRightSideContent"),
	ONLINE_RESVDESK_REST_SERVICE_GET_RESEV_DETAILS_RIGHT_SIDE_CONTENT("getResvDetailsRightSideContent"),
	ONLINE_RESVDESK_REST_SERVICE_GET_RESEV_VERIFICATION_RIGHT_SIDE_CONTENT("getResvVerifyDetailsRightSideContent"),
	ONLINE_RESVDESK_REST_SERVICE_GET_RESV_CONFIRMATION_RIGHT_SIDE_CONTENT("getResvConfirmationPageRightSideContent"),
	
	ONLINE_RESVDESK_REST_SERVICE_GET_LIST_OF_THINGS_TO_BRING_RESPONSE("listOfThingsToBring?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&eventId=@eventIdParam@"),
	ONLINE_RESVDESK_REST_SERVICE_GET_CONFIRMATION_PAGE_CONTACT_DETAILS("getConfPageContactDetails?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&locationId=@locationIdParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_HISTORY("getEventHistory"),
	
	ONLINE_RESVDESK_REST_SERVICE_LOGIN_FIRST_PARAMETERS("&loginFirst=@loginFirstParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_GET_RESERVES_EVENTS("getReservedEvents?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&customerId=@customerIdParam@"),
	
	ONLINE_RESVDESK_REST_SERVICE_IS_ALLOW_DUPLICATE_RESERVATION("isAllowDuplicateResv?clientCode=@clientCodeParam@&device=@deviceParam@&langCode=@langCodeParam@&token=@tokenParam@&transId=@transIdParam@&customerId=@customerIdParam@&eventId=@eventIdParam@"),
	;
		
	private String value;
	
	OnlineResRestClientConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
