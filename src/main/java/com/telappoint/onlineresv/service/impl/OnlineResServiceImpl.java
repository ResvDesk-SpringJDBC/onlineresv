package com.telappoint.onlineresv.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telappoint.onlineresv.builder.IDataBuilder;
import com.telappoint.onlineresv.builder.impl.OnlineLoginFormDataBuilder;
import com.telappoint.onlineresv.exceptions.BuilderException;
import com.telappoint.onlineresv.form.LoginForm;
import com.telappoint.onlineresv.model.online.OnlineResRequest;
import com.telappoint.onlineresv.model.online.OnlineResvSessionData;
import com.telappoint.onlineresv.model.restws.AllowDuplicateResv;
import com.telappoint.onlineresv.model.restws.AuthResponse;
import com.telappoint.onlineresv.model.restws.BaseResponse;
import com.telappoint.onlineresv.model.restws.ClientInfo;
import com.telappoint.onlineresv.model.restws.CompanyListResponse;
import com.telappoint.onlineresv.model.restws.ConfPageContactDetailsResponse;
import com.telappoint.onlineresv.model.restws.ConfirmResvResponse;
import com.telappoint.onlineresv.model.restws.ConfirmationPageRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.DepartmentListResponse;
import com.telappoint.onlineresv.model.restws.EventDatesResponse;
import com.telappoint.onlineresv.model.restws.EventHistoryResponse;
import com.telappoint.onlineresv.model.restws.EventListResponse;
import com.telappoint.onlineresv.model.restws.EventTimesResponse;
import com.telappoint.onlineresv.model.restws.HoldResvResponse;
import com.telappoint.onlineresv.model.restws.JsonDataHandler;
import com.telappoint.onlineresv.model.restws.ListOfThingsResponse;
import com.telappoint.onlineresv.model.restws.LocationListResponse;
import com.telappoint.onlineresv.model.restws.LoginField;
import com.telappoint.onlineresv.model.restws.LoginInfoResponse;
import com.telappoint.onlineresv.model.restws.LoginInfoRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.ProcedureListResponse;
import com.telappoint.onlineresv.model.restws.ReservedEventResponse;
import com.telappoint.onlineresv.model.restws.ResvDetailsResponse;
import com.telappoint.onlineresv.model.restws.ResvDetailsRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.ResvVerificationDetailsRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.SeatResponse;
import com.telappoint.onlineresv.model.restws.VerifyResvResponse;
import com.telappoint.onlineresv.restclient.OnlineResRestClient;
import com.telappoint.onlineresv.service.IOnlineResService;

/**
 * @author Murali
 */

@Service
public class OnlineResServiceImpl implements IOnlineResService {

	private Logger logger = Logger.getLogger(OnlineResServiceImpl.class);
	
	/*Service method to getClientInfo */
	@Override
	public ClientInfo getClientInfo(String clientcode,String ipAddress,String sessionId,String loginFirst,String langCode) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getClientInfo(clientcode,ipAddress,sessionId,loginFirst,langCode);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getClientInfo : response :  "+response);
		ClientInfo clientInfo = gson.fromJson(response, new TypeToken<ClientInfo>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return clientInfo;
	}
	
	@Override
	public LoginInfoResponse getLoginInfo(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getLoginInfo(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getLoginInfo : response :  "+response);
		LoginInfoResponse loginInfoResponse = gson.fromJson(response, new TypeToken<LoginInfoResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return loginInfoResponse;
	}
		
	@Override
	public AuthResponse authenticateCustomer(LoginForm loginForm,OnlineResvSessionData sessionData,List<LoginField> loginFieldList) throws NoSuchFieldException {
		IDataBuilder<LoginForm, OnlineResRequest> builder = OnlineLoginFormDataBuilder.getInstance();
		String loginParams = builder.prepareLoginParamsData(loginFieldList, loginForm);
		System.out.println("loginParams ::: "+loginParams);
		JsonDataHandler responseData = OnlineResRestClient.getInstance().authenticateCustomer(sessionData,loginParams);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("authenticateCustomer : response :  "+response);
		AuthResponse authResponse  = gson.fromJson(response, new TypeToken<AuthResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return authResponse;
	}
	
	@Override
	public ResvDetailsResponse getResvDetailsSelectionInfo(OnlineResvSessionData sessionData) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getResvDetailsSelectionInfo(sessionData);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getResvDetailsSelectionInfo : response :  "+response);
		ResvDetailsResponse resvDetailsResponse  = gson.fromJson(response, new TypeToken<ResvDetailsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvDetailsResponse;
	}
		
	@Override
	public CompanyListResponse getCompanyList(OnlineResvSessionData sessionData) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getCompanyList(sessionData);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getCompanyList : response :  "+response);
		CompanyListResponse companyListResponse  = gson.fromJson(response, new TypeToken<CompanyListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return companyListResponse;
	}
	
	@Override
	public ProcedureListResponse getProcedureList(OnlineResvSessionData sessionData,String companyId) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getProcedureList(sessionData,companyId);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getProcedureList : response :  "+response);
		ProcedureListResponse procedureListResponse  = gson.fromJson(response, new TypeToken<ProcedureListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return procedureListResponse;
	}
	
	@Override
	public LocationListResponse getLocationList(OnlineResvSessionData sessionData,String procedureId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getLocationList(sessionData,procedureId);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getLocationList : response :  "+response);
		LocationListResponse locationListResponse  = gson.fromJson(response, new TypeToken<LocationListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return locationListResponse;
	}
	
	@Override
	public DepartmentListResponse getDepartmentList(OnlineResvSessionData sessionData,String locationId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getDepartmentList(sessionData,locationId);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getDepartmentList : response :  "+response);
		DepartmentListResponse departmentListResponse  = gson.fromJson(response, new TypeToken<DepartmentListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return departmentListResponse;
	}
	
	@Override
	public EventListResponse getEventList(OnlineResvSessionData sessionData,String locationId,String departmentId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getEventList(sessionData,locationId,departmentId);		
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getEventList : response :  "+response);
		EventListResponse eventListResponse  = gson.fromJson(response, new TypeToken<EventListResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventListResponse;
	}
	
	@Override
	public EventDatesResponse getEventDates(OnlineResvSessionData sessionData,String locationId,String eventId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getEventDates(sessionData,locationId,eventId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getEventDates : response :  "+response);
		System.out.println("getEventDates : response :  "+response);
		EventDatesResponse eventDatesResponse  = gson.fromJson(response, new TypeToken<EventDatesResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventDatesResponse;
	}
	
	@Override
	public EventTimesResponse getEventTimes(OnlineResvSessionData sessionData,String locationId,String eventId,String date)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getEventTimes(sessionData,locationId,eventId,date);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getEventTimes : response :  "+response);
		EventTimesResponse eventTimesResponse  = gson.fromJson(response, new TypeToken<EventTimesResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return eventTimesResponse;
	}
	
	@Override
	public SeatResponse getEventSeats(OnlineResvSessionData sessionData,String eventId,String eventDateTimeId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getEventSeats(sessionData,eventId,eventDateTimeId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getEventSeats : response :  "+response);
		SeatResponse seatResponse  = gson.fromJson(response, new TypeToken<SeatResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return seatResponse;
	}
	
	@Override
	public HoldResvResponse holdReservation(OnlineResvSessionData sessionData,String companyId,String procedureId, String locationId,String departmentId,String eventId,
			String eventDateTimeId,String seatId,String customerId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().holdReservation(sessionData,companyId,procedureId,locationId,departmentId,eventId,eventDateTimeId,seatId,customerId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("holdReservation : response :  "+response);
		HoldResvResponse holdResvResponse  = gson.fromJson(response, new TypeToken<HoldResvResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return holdResvResponse;
	}
	
	@Override
	public VerifyResvResponse getVerifyReservationDetails(OnlineResvSessionData sessionData, String scheduleId, String customerId)throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getVerifyReservationDetails(sessionData,scheduleId,customerId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("reservationVerification : response :  "+response);
		VerifyResvResponse verifyResvResponse  = gson.fromJson(response, new TypeToken<VerifyResvResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return verifyResvResponse;
	}
	
	@Override
	public AllowDuplicateResv isAllowDuplicateResv(OnlineResvSessionData sessionData, String eventId, String customerId) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().isAllowDuplicateResv(sessionData,eventId,customerId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("isAllowDuplicateResv : response :  "+response);
		System.out.println("isAllowDuplicateResv : response :  "+response);
		AllowDuplicateResv allowDuplicateResv = gson.fromJson(response, new TypeToken<AllowDuplicateResv>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return allowDuplicateResv;
	}	
	
	@Override
	public ConfirmResvResponse confirmReservation(OnlineResvSessionData sessionData, String scheduleId, String customerId, String comment) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().confirmReservation(sessionData,scheduleId,customerId,comment);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("confirmReservation : response :  "+response);
		ConfirmResvResponse confirmResvResponse  = gson.fromJson(response, new TypeToken<ConfirmResvResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return confirmResvResponse;
	}	
	
	@Override
	public BaseResponse releaseHoldEventTime(OnlineResvSessionData sessionData, String scheduleId) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().releaseHoldEventTime(sessionData,scheduleId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("releaseHoldEventTime : response :  "+response);
		BaseResponse baseResponse  = gson.fromJson(response, new TypeToken<BaseResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return baseResponse;
	}	
	
	@Override
	public BaseResponse cancelReservation(OnlineResvSessionData sessionData, String scheduleId, String customerId) throws NoSuchFieldException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().cancelReservation(sessionData,scheduleId,customerId);	
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("cancelReservation : response :  "+response);
		BaseResponse baseResponse  = gson.fromJson(response, new TypeToken<BaseResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return baseResponse;
	}	
	
	@Override
	public LoginInfoRightSideContentResponse getLoginInfoRightSideContent(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getLoginInfoRightSideContent(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getLoginInfoRightSideContent : response :  "+response);
		LoginInfoRightSideContentResponse loginRightSideContent = gson.fromJson(response, new TypeToken<LoginInfoRightSideContentResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return loginRightSideContent;
	}
	
	@Override
	public ResvDetailsRightSideContentResponse getResvDetailsRightSideContent(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getResvDetailsRightSideContent(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getResvDetailsRightSideContent : response :  "+response);
		ResvDetailsRightSideContentResponse resvDetailsRightSideContent = gson.fromJson(response, new TypeToken<ResvDetailsRightSideContentResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvDetailsRightSideContent;
	}
	
	@Override
	public ResvVerificationDetailsRightSideContentResponse getResvVerifyDetailsRightSideContent(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getResvVerifyDetailsRightSideContent(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("ResvVerificationDetailsRightSideContentResponse : response :  "+response);
		ResvVerificationDetailsRightSideContentResponse resvVerificationDetailsRightSideContent =  gson.fromJson(response, new TypeToken<ResvVerificationDetailsRightSideContentResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return resvVerificationDetailsRightSideContent;
	}
	
	@Override
	public ConfirmationPageRightSideContentResponse getResvConfirmationPageRightSideContent(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getResvConfirmationPageRightSideContent(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getResvConfirmationPageRightSideContent : response :  "+response);
		ConfirmationPageRightSideContentResponse confirmationPageContent = gson.fromJson(response, new TypeToken<ConfirmationPageRightSideContentResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return confirmationPageContent;
	}
	
	@Override
	public ListOfThingsResponse getListOfThingsToBringResponse(OnlineResvSessionData sessionData,String eventId) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getListOfThingsToBringResponse(sessionData,eventId);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getListOfThingsToBringResponse : response :  "+response);
		ListOfThingsResponse listOfThingsToBring =  gson.fromJson(response, new TypeToken<ListOfThingsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return listOfThingsToBring;
	}
	
	@Override
	public ConfPageContactDetailsResponse getConfPageContactDetails(OnlineResvSessionData sessionData,String locationId) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getConfPageContactDetails(sessionData,locationId);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getConfPageContactDetails : response :  "+response);
		ConfPageContactDetailsResponse confPageContactDetails =  gson.fromJson(response, new TypeToken<ConfPageContactDetailsResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());
		return confPageContactDetails;
	}
	
	@Override
	public EventHistoryResponse getEventHistory(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getEventHistory(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getEventHistory : response :  "+response);
		EventHistoryResponse eventHistoryResponse =  gson.fromJson(response, new TypeToken<EventHistoryResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());		
		return eventHistoryResponse;
	}
	
	@Override
	public ReservedEventResponse getReservedEvents(OnlineResvSessionData sessionData) throws BuilderException {
		JsonDataHandler responseData = OnlineResRestClient.getInstance().getReservedEvents(sessionData);
		Gson gson = new GsonBuilder().create();
		String response = gson.toJson(responseData.getData());
		logger.error("getReservedEvents : response :  "+response);
		ReservedEventResponse reservedEventResponse =  gson.fromJson(response, new TypeToken<ReservedEventResponse>() {
			private static final long serialVersionUID = 1L;
		}.getType());		
		return reservedEventResponse;
	}
}
