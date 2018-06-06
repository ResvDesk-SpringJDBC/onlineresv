package com.telappoint.onlineresv.service;

import java.util.List;

import com.telappoint.onlineresv.exceptions.BuilderException;
import com.telappoint.onlineresv.form.LoginForm;
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

/**
 * @author Murali
 *
 */
public interface IOnlineResService {
		
	public ClientInfo getClientInfo(String clientcode,String ipAddress,String sessionId,String loginFirst,String langCode) throws BuilderException;
	public LoginInfoResponse getLoginInfo(OnlineResvSessionData sessionData)throws BuilderException;
	public AuthResponse authenticateCustomer(LoginForm loginForm,OnlineResvSessionData sessionData,List<LoginField> loginFieldList)throws NoSuchFieldException;	
	public ResvDetailsResponse getResvDetailsSelectionInfo(OnlineResvSessionData sessionData)throws NoSuchFieldException;

	public CompanyListResponse getCompanyList(OnlineResvSessionData sessionData) throws NoSuchFieldException;
	public ProcedureListResponse getProcedureList(OnlineResvSessionData sessionData,String companyId) throws NoSuchFieldException;
	public LocationListResponse getLocationList(OnlineResvSessionData sessionData,String procedureId) throws NoSuchFieldException;
	public DepartmentListResponse getDepartmentList(OnlineResvSessionData sessionData,String locationId) throws NoSuchFieldException;
	public EventListResponse getEventList(OnlineResvSessionData sessionData,String locationId,String departmentId)throws NoSuchFieldException;
	public EventDatesResponse getEventDates(OnlineResvSessionData sessionData,String locationId, String eventId) throws NoSuchFieldException;
	public EventTimesResponse getEventTimes(OnlineResvSessionData sessionData,String locationId, String eventId, String date)throws NoSuchFieldException;
	public SeatResponse getEventSeats(OnlineResvSessionData sessionData,String eventId, String eventDateTimeId)throws NoSuchFieldException;
	
	public HoldResvResponse holdReservation(OnlineResvSessionData sessionData, String companyId,String procedureId, String locationId, String departmentId,String eventId, String eventDateTimeId, String seatId,String customerId) throws NoSuchFieldException;
	public VerifyResvResponse getVerifyReservationDetails(OnlineResvSessionData sessionData,String scheduleId, String customerId) throws NoSuchFieldException;
	public ConfirmResvResponse confirmReservation(OnlineResvSessionData sessionData,String scheduleId, String customerId, String comment)throws NoSuchFieldException;
	public BaseResponse releaseHoldEventTime(OnlineResvSessionData sessionData, String scheduleId)throws NoSuchFieldException;
	public BaseResponse cancelReservation(OnlineResvSessionData sessionData, String scheduleId,String customerId) throws NoSuchFieldException;
	
	public LoginInfoRightSideContentResponse getLoginInfoRightSideContent(OnlineResvSessionData sessionData) throws BuilderException;
	public ResvDetailsRightSideContentResponse getResvDetailsRightSideContent(OnlineResvSessionData sessionData) throws BuilderException;
	public ResvVerificationDetailsRightSideContentResponse getResvVerifyDetailsRightSideContent(OnlineResvSessionData sessionData) throws BuilderException;
	public ConfirmationPageRightSideContentResponse getResvConfirmationPageRightSideContent(OnlineResvSessionData sessionData) throws BuilderException;
	
	public ListOfThingsResponse getListOfThingsToBringResponse(OnlineResvSessionData sessionData, String eventId)throws BuilderException;
	public ConfPageContactDetailsResponse getConfPageContactDetails(OnlineResvSessionData sessionData, String locationId)throws BuilderException;
	
	public EventHistoryResponse getEventHistory(OnlineResvSessionData sessionData)throws BuilderException;
	public ReservedEventResponse getReservedEvents(OnlineResvSessionData sessionData) throws BuilderException;
	
	public AllowDuplicateResv isAllowDuplicateResv(OnlineResvSessionData sessionData, String eventId, String customerId) throws NoSuchFieldException;
	
		
}
