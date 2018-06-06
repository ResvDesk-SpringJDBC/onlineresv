package com.telappoint.onlineresv.restclient;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.telappoint.onlineresv.common.OnlineResContants;
import com.telappoint.onlineresv.common.OnlineResRestClientConstants;
import com.telappoint.onlineresv.model.online.OnlineResvSessionData;
import com.telappoint.onlineresv.model.restws.JsonDataHandler;
import com.telappoint.onlineresv.utils.FileUtils;

/**
 * @author Murali
 * 
 */
public class OnlineResRestClient {
	
	private Logger logger = Logger.getLogger(OnlineResRestClient.class);

	private static Client client;

	private static volatile OnlineResRestClient instance;

	private OnlineResRestClient() {
		getApptdeskClient();
	}

	public static OnlineResRestClient getInstance() {
		if (instance == null) {
			synchronized (OnlineResRestClient.class) {
				if (instance == null)
					instance = new OnlineResRestClient();
			}
		}
		return instance;
	}

	public static Client getApptdeskClient() {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(config);
		return client;
	}
	
	/*Rest client method to getClientInfo by invoking Web service call */
	public JsonDataHandler getClientInfo(String clientcode,String ipAddress,String sessionId,String loginFirst,String langCode) {		
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+  OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_CLIENT_INFO.getValue();
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",clientcode);
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",OnlineResContants.ONLINE.getValue());
		endPointUrl = endPointUrl.replaceAll("@param1Param@",ipAddress);
		endPointUrl = endPointUrl.replaceAll("@param2Param@",sessionId);
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",loginFirst);
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",langCode);
		
		logger.error("getClientInfo : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);

		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	private String populateBasicRequestParamsDetails(OnlineResvSessionData sessionData,String endPointUrl) {
		endPointUrl = endPointUrl.replaceAll("@clientCodeParam@",sessionData.getClientCode());
		endPointUrl = endPointUrl.replaceAll("@deviceParam@",OnlineResContants.ONLINE.getValue());
		endPointUrl = endPointUrl.replaceAll("@langCodeParam@",sessionData.getLangCode());
		endPointUrl = endPointUrl.replaceAll("@tokenParam@", sessionData.getToken());	
		endPointUrl = endPointUrl.replaceAll("@transIdParam@", sessionData.getTransId());
		return endPointUrl;
	}
	
	public JsonDataHandler getLoginInfo(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_FIELDS_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getLoginInfo : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler authenticateCustomer(OnlineResvSessionData sessionData,String loginParams) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_AUTHENTICATE_CUSTOMR.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@loginParamsParam@",loginParams);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("authenticateCustomer : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getResvDetailsSelectionInfo(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_RESV_DETAILS_SECTION_INFO.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getResvDetailsSelectionInfo : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler getCompanyList(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_COMPANY_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getCompanyList : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler getProcedureList(OnlineResvSessionData sessionData,String companyId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_PROCEDURE_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@companyIdParam@",companyId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getProcedureList : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getLocationList(OnlineResvSessionData sessionData,String procedureId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_LOCATION_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@procedureIdParam@",procedureId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getLocationList : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getDepartmentList(OnlineResvSessionData sessionData,String locationId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_DEPARTMENT_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("geDepartmentList : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getEventList(OnlineResvSessionData sessionData,String locationId,String departmentId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_LIST.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@departmentIdParam@",departmentId);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getEventList : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler getEventDates(OnlineResvSessionData sessionData,String locationId,String eventId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_DATES.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getEventDates : endPointUrl :  "+endPointUrl);
		System.out.println("getEventDates : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler getEventTimes(OnlineResvSessionData sessionData,String locationId,String eventId,String date) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_TIMES.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@dateParam@",date);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getEventTimes : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getEventSeats(OnlineResvSessionData sessionData,String eventId,String eventDateTimeId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_SEATS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@",eventDateTimeId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getEventSeats : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler holdReservation(OnlineResvSessionData sessionData, String companyId,String procedureId, String locationId, String departmentId,String eventId, String eventDateTimeId, String seatId,String customerId){
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_HOLD_RESERVATION.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@companyIdParam@",companyId);
		endPointUrl = endPointUrl.replaceAll("@procedureIdParam@",procedureId);		
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll("@departmentIdParam@",departmentId);		
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@seatIdParam@",seatId);		
		endPointUrl = endPointUrl.replaceAll("@eventDateTimeIdParam@",eventDateTimeId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("holdReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getVerifyReservationDetails(OnlineResvSessionData sessionData, String scheduleId, String customerId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_RESERVATION_VERIFICATION.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",sessionData.getLoginFirst());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("reservationVerification : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler isAllowDuplicateResv(OnlineResvSessionData sessionData, String eventId, String customerId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_IS_ALLOW_DUPLICATE_RESERVATION.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		System.out.println("isAllowDuplicateResv : endPointUrl :  "+endPointUrl);
		logger.error("isAllowDuplicateResv : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}

	public JsonDataHandler confirmReservation(OnlineResvSessionData sessionData, String scheduleId, String customerId, String comment) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_CONFIRM_RESERVATION.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll("@commentParam@",comment);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("confirmReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler releaseHoldEventTime(OnlineResvSessionData sessionData, String scheduleId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_RELEASE_HOLDED_EVENT_TIME.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("releaseHoldEventTime : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler cancelReservation(OnlineResvSessionData sessionData, String scheduleId, String customerId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_CANCEL_RESERVATION.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@scheduleIdParam@",scheduleId);
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",customerId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("cancelReservation : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getLoginInfoRightSideContent(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_LOGIN_RIGHT_SIDE_CONTENT.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_LOGIN_FIRST_PARAMETERS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",sessionData.getLoginFirst());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getLoginInfoRightSideContent : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getResvDetailsRightSideContent(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_RESEV_DETAILS_RIGHT_SIDE_CONTENT.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_LOGIN_FIRST_PARAMETERS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",sessionData.getLoginFirst());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getResvDetailsRightSideContent : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getResvVerifyDetailsRightSideContent(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_RESEV_VERIFICATION_RIGHT_SIDE_CONTENT.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_LOGIN_FIRST_PARAMETERS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",sessionData.getLoginFirst());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getResvVerifyDetailsRightSideContent : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getResvConfirmationPageRightSideContent(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_RESV_CONFIRMATION_RIGHT_SIDE_CONTENT.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_LOGIN_FIRST_PARAMETERS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);
		endPointUrl = endPointUrl.replaceAll("@loginFirstParam@",sessionData.getLoginFirst());
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getResvConfirmationPageRightSideContent : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getListOfThingsToBringResponse(OnlineResvSessionData sessionData,String eventId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_LIST_OF_THINGS_TO_BRING_RESPONSE.getValue();

		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@eventIdParam@",eventId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getListOfThingsToBringResponse : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getConfPageContactDetails(OnlineResvSessionData sessionData,String locationId) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_CONFIRMATION_PAGE_CONTACT_DETAILS.getValue();

		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@locationIdParam@",locationId);
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getConfPageContactDetails : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getEventHistory(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_EVENT_HISTORY.getValue()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_BASIC_PARAMETERS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);		
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getEventHistory : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
	public JsonDataHandler getReservedEvents(OnlineResvSessionData sessionData) {
		String endPointUrl = FileUtils.getResvDESKRestWSEndPointURL()
				+ OnlineResRestClientConstants.ONLINE_RESVDESK_REST_SERVICE_GET_RESERVES_EVENTS.getValue();
		
		endPointUrl = populateBasicRequestParamsDetails(sessionData, endPointUrl);	
		endPointUrl = endPointUrl.replaceAll("@customerIdParam@",String.valueOf(sessionData.getCustomerId()));
		endPointUrl = endPointUrl.replaceAll(" ", "%20");
		
		logger.error("getReservedEvents : endPointUrl :  "+endPointUrl);
		WebResource resource = client.resource(endPointUrl);
		return resource.type(MediaType.APPLICATION_JSON).get(JsonDataHandler.class);
	}
	
}
