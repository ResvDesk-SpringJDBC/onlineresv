package com.telappoint.onlineresv.controller;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.telappoint.onlineresv.builder.impl.OnlineLoginFormDataBuilder;
import com.telappoint.onlineresv.common.JSPPageNameConstants;
import com.telappoint.onlineresv.common.OnlineResContants;
import com.telappoint.onlineresv.exceptions.BuilderException;
import com.telappoint.onlineresv.form.LoginForm;
import com.telappoint.onlineresv.form.validation.OnlineResValidatorImpl;
import com.telappoint.onlineresv.model.online.OnlineJSONResponse;
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
import com.telappoint.onlineresv.model.restws.EventDate;
import com.telappoint.onlineresv.model.restws.EventDatesResponse;
import com.telappoint.onlineresv.model.restws.EventHistoryResponse;
import com.telappoint.onlineresv.model.restws.EventListResponse;
import com.telappoint.onlineresv.model.restws.EventTimesResponse;
import com.telappoint.onlineresv.model.restws.HoldResvResponse;
import com.telappoint.onlineresv.model.restws.ListOfThingsResponse;
import com.telappoint.onlineresv.model.restws.LocationListResponse;
import com.telappoint.onlineresv.model.restws.LoginInfoResponse;
import com.telappoint.onlineresv.model.restws.LoginInfoRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.Options;
import com.telappoint.onlineresv.model.restws.ProcedureListResponse;
import com.telappoint.onlineresv.model.restws.ReservedEventResponse;
import com.telappoint.onlineresv.model.restws.ResvDetailsResponse;
import com.telappoint.onlineresv.model.restws.ResvDetailsRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.ResvVerificationDetailsRightSideContentResponse;
import com.telappoint.onlineresv.model.restws.SeatResponse;
import com.telappoint.onlineresv.model.restws.VerifyResvResponse;
import com.telappoint.onlineresv.service.IOnlineResService;
import com.telappoint.onlineresv.utils.OnlineResUtils;

/**
 * @author Murali
 * 
 */
@Controller
public class OnlineResController {
	private Logger logger = Logger.getLogger(OnlineResController.class);

	@Autowired
	private IOnlineResService onlineResService;
	@Autowired
	private OnlineResValidatorImpl onlineResValidator;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPage(HttpServletRequest request) throws BuilderException {
		String viewName = JSPPageNameConstants.RESVDESK_INDEX.getViewName();
		ModelMap modelMap = new ModelMap();
		HttpSession session = request.getSession(false); 
		ClientInfo clientInfo = (ClientInfo)session.getAttribute("clientInfo");	
		modelMap.addAttribute("clientInfo", clientInfo);
		return new ModelAndView(viewName, modelMap);
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@RequestParam("client_code") String clientcode,@RequestParam(value="lang_code", required = false) String lang_code,HttpServletRequest request) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_MEMPHIS_DETAILS.getViewName();
		try {String ipAddress = InetAddress.getLocalHost().getHostAddress();
			if (lang_code == null || "".equals(lang_code)) {
				lang_code = "us-en";
			}
			
			HttpSession session = request.getSession(false);
			if (null != session) {
				session.invalidate();
				session = null;
				session = request.getSession(true);
			} else {
				session = request.getSession(true);
			}	
			ClientInfo clientInfo = onlineResService.getClientInfo(clientcode,ipAddress,session.getId(),"",lang_code);	
			session.setAttribute("clientInfo", clientInfo);
			if(StringUtils.isEmpty(clientInfo.getLandingPageText())) {
				return getLoginPageAfterLanding(request);
			}
			modelMap.addAttribute("clientInfo", clientInfo);
		} catch (Exception e) {
			logger.error("Error, Passing client code: " + clientcode, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineResController for Client - " + clientcode;
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("clientcode=[" + clientcode + "]");
			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView(viewName, modelMap);
	}
	
	
	@RequestMapping(value = "/loginAfterLanding", method = RequestMethod.POST)
	public ModelAndView getLoginPageAfterLanding(HttpServletRequest request) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_LOGIN_FIRST_PAGE.getViewName();
		String clientcode = "";
		try {
			HttpSession session = request.getSession(false); 
			ClientInfo clientInfo = (ClientInfo)session.getAttribute("clientInfo");	
			clientcode = clientInfo.getClientCode();
			if(clientInfo!=null && clientInfo.isResponseStatus() && !clientInfo.isErrorStatus()){
				if ("N".equalsIgnoreCase(String.valueOf(clientInfo.getSchedulerClosed()))) {
					if ("N".equalsIgnoreCase(String.valueOf(clientInfo.getNoFunding()))) {
						viewName = populateData(modelMap, session, clientInfo);						
					} else {
						logger.error("Error: Scheduler_closed , Passing client code: " + clientcode);
						viewName = JSPPageNameConstants.RESVDESK_NO_FUNDING_PAGE.getViewName();
					}
				} else {
					logger.error("Error: No_funding , Passing client code: " + clientcode);
					viewName = JSPPageNameConstants.RESVDESK_SCHEDULE_CLOSED_PAGE.getViewName();
				}
			} else {
				logger.error("Error: ClientInfo is null, Passing client code: " + clientcode);
				viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
			}	
			modelMap.addAttribute("clientInfo", clientInfo);
		} catch (Exception e) {
			logger.error("Error, Passing client code: " + clientcode, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineResController for Client - " + clientcode;
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("clientcode=[" + clientcode + "]");
			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		logger.info("View page : " + viewName + " ,  Passing client code: " + clientcode);
		return new ModelAndView(viewName, modelMap);
	}

	private String populateData(ModelMap modelMap,HttpSession session,ClientInfo clientInfo) throws NoSuchFieldException, BuilderException {
		String viewName = null;
		OnlineResvSessionData sessionData = null;
		if (clientInfo != null && clientInfo.isResponseStatus() && !clientInfo.isErrorStatus()) {
			//Populating to session only required data
			sessionData = OnlineLoginFormDataBuilder.getInstance().prepareOnlineResvSessionData(clientInfo);
			if("Y".equals(sessionData.getLoginFirst())){
				LoginInfoResponse loginInfoResponse = onlineResService.getLoginInfo(sessionData);
				if(loginInfoResponse!=null){
					viewName = JSPPageNameConstants.RESVDESK_LOGIN_FIRST_PAGE.getViewName();
					LoginForm loginForm = new LoginForm();
					modelMap.addAttribute("loginForm", loginForm);
					modelMap.addAttribute("loginInfoResponse",loginInfoResponse);
					modelMap.addAttribute("loginFieldList",loginInfoResponse.getLoginFieldList());

					LoginInfoRightSideContentResponse loginRightSideContentResponse = onlineResService.getLoginInfoRightSideContent(sessionData);
					modelMap.addAttribute("loginRightSideContentResponse",loginRightSideContentResponse);	
				}else{
					viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
				}					
			}else{					
				ResvDetailsResponse  resvDetailsResponse  = onlineResService.getResvDetailsSelectionInfo(sessionData);
				if(resvDetailsResponse!=null){
					viewName = JSPPageNameConstants.RESVDESK_LOGIN_LAST_PAGE.getViewName();
					populateReservationDetailsLeftSection(modelMap,sessionData,resvDetailsResponse);
					
					ResvDetailsRightSideContentResponse resvDetailsRightSideContent = onlineResService.getResvDetailsRightSideContent(sessionData);
					modelMap.addAttribute("resvDetailsRightSideContent",resvDetailsRightSideContent);					
				}else{
					viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
				}
			}
			session.setAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue(),sessionData);
			/*//To Show Scheduled Available Events
			EventHistoryResponse eventHistoryResponse =onlineResService.getEventHistory(sessionData);
			modelMap.addAttribute("eventHistoryResponse",eventHistoryResponse);*/
		}else{
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
		}
		return viewName;
	}

	@RequestMapping(value = "/authenticateCustomer", method = RequestMethod.POST)
	public @ResponseBody String authenticateCustomer(@ModelAttribute LoginForm loginForm,ModelMap map,HttpServletRequest request,HttpServletResponse response, BindingResult result)throws BuilderException {
		OnlineJSONResponse jsonResponse = new OnlineJSONResponse();
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			loginForm.setClientCode(sessionData.getClientCode());
			LoginInfoResponse loginInfoResponse = onlineResService.getLoginInfo(sessionData);			
			onlineResValidator.validate(loginForm, result,loginInfoResponse.getLoginFieldList());

			if (result.hasErrors()) {
				jsonResponse.setFormErrors(true);
				jsonResponse.setResponse("INVALID_LOGIN_DATA");
				jsonResponse.setMessage("INVALID_LOGIN_DATA");//TODO : Messages have to change dynamically
			} else {
				AuthResponse authResponse  = onlineResService.authenticateCustomer(loginForm,sessionData,loginInfoResponse.getLoginFieldList());
				logger.info("OnlineResController -> authenticateCustomer , AuthResponse : " + authResponse + "  , Success : " + (authResponse == null ? "" : authResponse.isAuthSuccess())
						+ "  , Authenticate Msg : " + (authResponse == null ? "" :  authResponse.getAuthMessage())+ " , customerId : "+ (authResponse == null ? -1 : authResponse.getCustomerId()));

				if(authResponse!=null && authResponse.isAuthSuccess() && 
						!authResponse.isErrorStatus() && authResponse.isResponseStatus()){
					jsonResponse.setFormErrors(false);
					jsonResponse.setResponse("SUCCESS");
					jsonResponse.setMessage(authResponse.getAuthMessage());
					jsonResponse.setFirstName(authResponse.getFirstName());
					jsonResponse.setLastName(authResponse.getLastName());
					sessionData.setCustomerId(authResponse.getCustomerId());
					jsonResponse.setWelcomeHeader(authResponse.getWelcomeHeader());
					session.setAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue(),sessionData);
				}else{
					jsonResponse.setFormErrors(false);
					jsonResponse.setResponse("AUTH_FAILED");
					jsonResponse.setMessage(authResponse.getAuthMessage());
				}
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController authenticateCustomer method :" + e, e);
			jsonResponse.setFormErrors(false);
			jsonResponse.setResponse("FAILURE");
			jsonResponse.setMessage("FAILURE");

			String subject = "Error in OnlineRes for Client - " + loginForm.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		Gson gson = new Gson();
		return gson.toJson(jsonResponse);
	}

	@RequestMapping(value = "/get_invalid_data_login_left_page", method = RequestMethod.GET)
	public ModelAndView getInvalidDataLoginLeftPage(@ModelAttribute LoginForm loginForm,
			ModelMap map,HttpServletRequest request,HttpServletResponse response, BindingResult result)throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = null;
		viewName = JSPPageNameConstants.RESVESK_LOGIN_LEFT_SECTION.getViewName();
		try {
			HttpSession session = request.getSession(false);
			OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			loginForm.setClientCode(sessionData.getClientCode());

			LoginInfoResponse loginInfoResponse = onlineResService.getLoginInfo(sessionData);			
			onlineResValidator.validate(loginForm, result,loginInfoResponse.getLoginFieldList());
			modelMap.addAttribute("loginFieldList",loginInfoResponse.getLoginFieldList());
			if (result.hasErrors()) {
				viewName = JSPPageNameConstants.RESVESK_LOGIN_LEFT_SECTION.getViewName();
			} else {
				//TODO : Need to discusses what page we have to use in this case 
			}			
			modelMap.addAttribute("loginForm",loginForm);
			modelMap.addAttribute("loginInfoResponse",loginInfoResponse);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getInvalidDataLoginLeftPage method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + loginForm.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		return new ModelAndView(viewName, modelMap);
	}


	@RequestMapping(value = "/get_reservation_left_section", method = RequestMethod.GET)
	public ModelAndView getReservationLeftSection(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_RESERVATION_LEFT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());		

			ResvDetailsResponse  resvDetailsResponse  = onlineResService.getResvDetailsSelectionInfo(sessionData);
			if(resvDetailsResponse!=null){	
				populateReservationDetailsLeftSection(modelMap,sessionData,resvDetailsResponse);
			}else{
				viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController getReservationLeftSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}

	private void populateReservationDetailsLeftSection(ModelMap modelMap,OnlineResvSessionData sessionData, ResvDetailsResponse resvDetailsResponse)throws NoSuchFieldException {
		LoginForm loginForm = new LoginForm();
		modelMap.addAttribute("resvDetailsResponse",resvDetailsResponse);
		
		boolean is_First_Display_Field_Loaded_Whose_Size_GT_1 = false;

		String companyId = "-1";
		String procedureId = "-1";
		String locationId = "-1";
		String departmentId = "-1";
		String eventId = "-1";

		//populating Company drop down details
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			CompanyListResponse  companyListResponse  = onlineResService.getCompanyList(sessionData);				
			if("Y".equalsIgnoreCase(resvDetailsResponse.getDisplayCompany())){
				modelMap.addAttribute("companyListResponse",companyListResponse);
				if(companyListResponse!=null && companyListResponse.getCompanyList()!=null){
					if(companyListResponse.getCompanyList().size()>1){
						is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
					}else if(companyListResponse.getCompanyList().size()==1){
						companyId = companyListResponse.getCompanyList().get(0).getOptionKey();
						loginForm.setDisplay_company(companyId);
					}else{
						companyId = "0";
						loginForm.setDisplay_company(companyId);
					}					
				}else{
					is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
				}
			}else{
				if(companyListResponse!=null && null!=companyListResponse.getCompanyList()){
					if(companyListResponse.getCompanyList().size()>0){
						companyId = companyListResponse.getCompanyList().get(0).getOptionKey();						
					}else{
						companyId = "0";
					}
					loginForm.setDisplay_company(companyId);
				}
			}
		}				
		//populating Procedure drop down details
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			ProcedureListResponse  procedureListResponse  = onlineResService.getProcedureList(sessionData, companyId);				
			if("Y".equalsIgnoreCase(resvDetailsResponse.getDisplayProcedure())){
				modelMap.addAttribute("procedureListResponse",procedureListResponse);
				if(procedureListResponse!=null && procedureListResponse.getProcedureList()!=null){
					if(procedureListResponse.getProcedureList().size()>1){
						is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
					}else if(procedureListResponse.getProcedureList().size()==1){
						procedureId = procedureListResponse.getProcedureList().get(0).getOptionKey();
						loginForm.setDisplay_procedure(procedureId);
					}else{
						procedureId = "0";
						loginForm.setDisplay_procedure(procedureId);
					}					
				}else{
					is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
				}
			}else{
				if(procedureListResponse!=null && null!=procedureListResponse.getProcedureList()){
					if(procedureListResponse.getProcedureList().size()>0){
						procedureId = procedureListResponse.getProcedureList().get(0).getOptionKey();
					}else{
						procedureId = "0";
					}					
					loginForm.setDisplay_procedure(procedureId);
				}
			}
		}				
		//populating Location drop down details
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			LocationListResponse  locationListResponse  = onlineResService.getLocationList(sessionData, procedureId);			
			if("Y".equalsIgnoreCase(resvDetailsResponse.getDisplayLocation())){
				modelMap.addAttribute("locationListResponse",locationListResponse);
				if(locationListResponse!=null && locationListResponse.getLocationList()!=null){
					if(locationListResponse.getLocationList().size()>1){
						is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
					}else if(locationListResponse.getLocationList().size()==1){
						locationId = locationListResponse.getLocationList().get(0).getOptionKey();
						loginForm.setDisplay_location(locationId);
					}else{
						locationId = "0";
						loginForm.setDisplay_location(locationId);
					}						
				}else{
					is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
				}
			}else{
				if(locationListResponse!=null && null!=locationListResponse.getLocationList()){
					if(locationListResponse.getLocationList().size()>0){
						locationId = locationListResponse.getLocationList().get(0).getOptionKey();
					}else{
						locationId = "0";
					}					
					loginForm.setDisplay_location(locationId);							
				}
			}
		}				
		//populating Department drop down details
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			DepartmentListResponse  departmentListResponse  = onlineResService.getDepartmentList(sessionData, locationId);		
			if("Y".equalsIgnoreCase(resvDetailsResponse.getDisplayDepartment())){
				modelMap.addAttribute("departmentListResponse",departmentListResponse);
				if(departmentListResponse!=null && departmentListResponse.getDepartmentList()!=null){
					if(departmentListResponse.getDepartmentList().size()>1){
						is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
					}else if(departmentListResponse.getDepartmentList().size()==1){
						departmentId = departmentListResponse.getDepartmentList().get(0).getOptionKey();
						loginForm.setDisplay_department(departmentId);
					}else{
						departmentId = "0";
						loginForm.setDisplay_department(departmentId);
					}					
				}else{
					is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
				}
			}else{
				if(departmentListResponse!=null && null!=departmentListResponse.getDepartmentList()){
					if(departmentListResponse.getDepartmentList().size()>0){
						departmentId = departmentListResponse.getDepartmentList().get(0).getOptionKey();
					}else{
						departmentId = "0";
					}					
					loginForm.setDisplay_department(departmentId);
				}
			}
		}				
		//populating Event drop down details
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			EventListResponse  eventListResponse  = onlineResService.getEventList(sessionData,locationId,departmentId);	
			if("Y".equalsIgnoreCase(resvDetailsResponse.getDisplayEvent())){
				modelMap.addAttribute("eventListResponse",eventListResponse);
				if(eventListResponse!=null && eventListResponse.getEventList()!=null){
					if(eventListResponse.getEventList().size()>1){
						is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
					}else if(eventListResponse.getEventList().size()==1){
						eventId = eventListResponse.getEventList().get(0).getOptionKey();
						loginForm.setDisplay_event(eventId);
					}else{
						eventId = "0";
						loginForm.setDisplay_event(eventId);
					}					
				}else{
					is_First_Display_Field_Loaded_Whose_Size_GT_1 = true;
				}
			}else{
				if(eventListResponse!=null && null!=eventListResponse.getEventList()){
					if(eventListResponse.getEventList().size()>0){
						eventId = eventListResponse.getEventList().get(0).getOptionKey();
					}else{
						eventId = "0";
					}					
					loginForm.setDisplay_event(eventId);
				}
			}
		}	
		if(!is_First_Display_Field_Loaded_Whose_Size_GT_1){
			modelMap.addAttribute("loadDatesDataOnLoadingOfEventsData","YES");
		}else{
			modelMap.addAttribute("loadDatesDataOnLoadingOfEventsData","NO");
		}
		modelMap.addAttribute("loginForm",loginForm);
	}

	/* Method to populate drop down values dynamically */
	@RequestMapping(value = "/populateDropdownData/{nextSelectDropdownID}", method = RequestMethod.GET)
	public ModelAndView populateDropdownData(@PathVariable String nextSelectDropdownID,@RequestParam String display_nextDropdown,@RequestParam String nextDropdown_label,
			@RequestParam String companyId,@RequestParam String procedureId,@RequestParam String locationId, 
			@RequestParam String departmentId, @RequestParam String eventId,HttpServletRequest request,HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		String viewName = null;
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			viewName = JSPPageNameConstants.RESVDESK_POPULATE_PAGE.getViewName();

			logger.error("OnlineResController -> populateDropdownData/{nextSelectDropdownID} Method  , nextSelectDropdownID : " + nextSelectDropdownID);
			
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());

			String selectLabel="";
			String selectDefaultId = "-1";
			List<Options> optionList = new ArrayList<Options>();
			boolean  errorStatus = false;
			String errorMessage="";
			
			if(sessionData!=null){
				sessionData.setEventDatesResponse(null);
				if ("display_company".equalsIgnoreCase(nextSelectDropdownID)) {
					CompanyListResponse companyListResponse  = onlineResService.getCompanyList(sessionData);
					if(companyListResponse!=null && companyListResponse.isResponseStatus() && !companyListResponse.isErrorStatus()){
						selectLabel=companyListResponse.getSelectCompanyLabel();
						selectDefaultId = companyListResponse.getSelectDefaultCompanyId();
						optionList =companyListResponse.getCompanyList();
					}else{
						errorStatus = companyListResponse.isErrorStatus();
						errorMessage= companyListResponse.getErrorMessage();
					}
				} else if ("display_procedure".equalsIgnoreCase(nextSelectDropdownID)) {
					ProcedureListResponse procedureListResponse = onlineResService.getProcedureList(sessionData,companyId);
					if(procedureListResponse!=null  && procedureListResponse.isResponseStatus() && !procedureListResponse.isErrorStatus()){
						selectLabel=procedureListResponse.getSelectProcedureLabel();
						selectDefaultId = procedureListResponse.getSelectDefaultProcedureId();
						optionList =procedureListResponse.getProcedureList();
					}else{
						errorStatus = procedureListResponse.isErrorStatus();
						errorMessage= procedureListResponse.getErrorMessage();
					}
				} else if ("display_location".equalsIgnoreCase(nextSelectDropdownID)) {
					LocationListResponse locationListResponse = onlineResService.getLocationList(sessionData,procedureId);
					if(locationListResponse!=null  && locationListResponse.isResponseStatus() && !locationListResponse.isErrorStatus()){
						selectLabel=locationListResponse.getSelectLocationLabel();
						selectDefaultId = locationListResponse.getSelectDefaultLocationId();
						optionList =locationListResponse.getLocationList();
					}else{
						errorStatus = locationListResponse.isErrorStatus();
						errorMessage= locationListResponse.getErrorMessage();
					}
				} else if ("display_department".equalsIgnoreCase(nextSelectDropdownID)) {
					DepartmentListResponse departmentListResponse = onlineResService.getDepartmentList(sessionData,locationId);
					if(departmentListResponse!=null  && departmentListResponse.isResponseStatus() && !departmentListResponse.isErrorStatus()){
						selectLabel=departmentListResponse.getSelectDepartmentLabel();
						selectDefaultId = departmentListResponse.getSelectDefaultDepartmentId();
						optionList =departmentListResponse.getDepartmentList();
					}else{
						errorStatus = departmentListResponse.isErrorStatus();
						errorMessage= departmentListResponse.getErrorMessage();
					}
				} else if ("display_event".equalsIgnoreCase(nextSelectDropdownID)) {
					EventListResponse eventListResponse  = onlineResService.getEventList(sessionData,locationId,departmentId);
					if(eventListResponse!=null  && eventListResponse.isResponseStatus() && !eventListResponse.isErrorStatus()){
						selectLabel=eventListResponse.getSelectEventLabel();
						selectDefaultId = eventListResponse.getSelectDefaultEventId();
						optionList =eventListResponse.getEventList();
					}else{
						errorStatus = eventListResponse.isErrorStatus();
						errorMessage= eventListResponse.getErrorMessage();
					}
				}
			}			
			modelMap.addAttribute("nextSelectDropdownID", nextSelectDropdownID);
			modelMap.addAttribute("selectLabel", selectLabel);
			modelMap.addAttribute("selectDefaultId", selectDefaultId);
			modelMap.addAttribute("optionList", optionList);
			modelMap.addAttribute("display_nextDropdown", display_nextDropdown);
			modelMap.addAttribute("label", nextDropdown_label);
			//specifies error ones
			modelMap.addAttribute("errorStatus",errorStatus);
			modelMap.addAttribute("errorMessage",errorMessage);		
		} catch (Exception e) {
			logger.error("Error in OnlineResController populateDropdownData method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder("nextSelectDropdownID=[" + nextSelectDropdownID + "],");
			inputParams.append("companyId=[" + companyId + "],");
			inputParams.append("procedureId=[" + procedureId + "],");
			inputParams.append("locationId=[" + locationId + "],");
			inputParams.append("departmentId=[" + departmentId + "],");
			inputParams.append("eventId=[" + eventId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/getEventDates", method = RequestMethod.GET)
	public ModelAndView getEventDates(@RequestParam String dateLabel,@RequestParam String companyId,@RequestParam String procedureId,@RequestParam String locationId, 
			@RequestParam String departmentId, @RequestParam String eventId,HttpServletRequest request,HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_POPULATE_CAL_DATA_PAGE.getViewName();
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			sessionData.setEventDatesResponse(null);
			
			EventDatesResponse eventDatesResponse  = onlineResService.getEventDates(sessionData, locationId, eventId);			
			modelMap.addAttribute("eventDatesResponse", eventDatesResponse);
			if(eventDatesResponse!=null && eventDatesResponse.isResponseStatus() && !eventDatesResponse.isErrorStatus()
				&& eventDatesResponse.getEventDateMap()!=null && eventDatesResponse.getEventDateMap().size()>0){
				modelMap.addAttribute("resvFirstAvailableDate",eventDatesResponse.getEventDateMap().keySet().toArray()[0]);
			}else{
				modelMap.addAttribute("resvFirstAvailableDate","");
			}	
			sessionData.setEventDatesResponse(eventDatesResponse);
			
			modelMap.addAttribute("dateLabel", dateLabel);			
			//specifies error ones
			modelMap.addAttribute("errorStatus",eventDatesResponse.isErrorStatus());
			modelMap.addAttribute("errorMessage",eventDatesResponse.getErrorMessage());
			modelMap.addAttribute("responseMessage",eventDatesResponse.getResponseMessage());			
		} catch (Exception e) {
			logger.error("Error in OnlineResController getEventDates method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("companyId=[" + companyId + "],");
			inputParams.append("procedureId=[" + procedureId + "],");
			inputParams.append("locationId=[" + locationId + "],");
			inputParams.append("departmentId=[" + departmentId + "],");
			inputParams.append("eventId=[" + eventId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/getEventTimes", method = RequestMethod.GET)
	public ModelAndView getEventTimes(@RequestParam String timeLabel,@RequestParam String companyId,@RequestParam String procedureId,@RequestParam String locationId, 
			@RequestParam String departmentId,@RequestParam String eventId,@RequestParam String date,@RequestParam String selectTimeLabel,
			HttpServletRequest request,HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_POPULATE_TIME_PAGE.getViewName();
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			EventTimesResponse eventTimesResponse = onlineResService.getEventTimes(sessionData, locationId, eventId, date);
			modelMap.addAttribute("eventTimesResponse", eventTimesResponse);
			modelMap.addAttribute("timeLabel", timeLabel);
			modelMap.addAttribute("selectTimeLabel", selectTimeLabel);
			
			//specifies error ones
			modelMap.addAttribute("errorStatus",eventTimesResponse.isErrorStatus());
			modelMap.addAttribute("errorMessage",eventTimesResponse.getErrorMessage());
		} catch (Exception e) {
			logger.error("Error in OnlineResController getEventTimes method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("companyId=[" + companyId + "],");
			inputParams.append("procedureId=[" + procedureId + "],");
			inputParams.append("locationId=[" + locationId + "],");
			inputParams.append("departmentId=[" + departmentId + "],");
			inputParams.append("eventId=[" + eventId + "]");
			inputParams.append("date=[" + date + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/getEventSeats", method = RequestMethod.GET)
	public ModelAndView getEventSeats(@RequestParam String seatLabel,@RequestParam String companyId,@RequestParam String procedureId,@RequestParam String locationId, 
			@RequestParam String departmentId,@RequestParam String eventId,@RequestParam String date,@RequestParam String timeId,@RequestParam String selectSeatLabel,
			@RequestParam String display_nextDropdown,HttpServletRequest request,HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_POPULATE_EVENTS_SEATS_PAGE.getViewName();
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			SeatResponse eventSeatsResponse = onlineResService.getEventSeats(sessionData,eventId,timeId);
			modelMap.addAttribute("eventSeatsResponse", eventSeatsResponse);
			modelMap.addAttribute("display_nextDropdown",display_nextDropdown);
			modelMap.addAttribute("seatLabel", seatLabel);
			modelMap.addAttribute("selectSeatLabel", selectSeatLabel);
			//specifies error ones
			modelMap.addAttribute("errorStatus",eventSeatsResponse.isErrorStatus());
			modelMap.addAttribute("errorMessage",eventSeatsResponse.getErrorMessage());
		} catch (Exception e) {
			logger.error("Error in OnlineResController getEventSeats method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("companyId=[" + companyId + "],");
			inputParams.append("procedureId=[" + procedureId + "],");
			inputParams.append("locationId=[" + locationId + "],");
			inputParams.append("departmentId=[" + departmentId + "],");
			inputParams.append("eventId=[" + eventId + "]");
			inputParams.append("date=[" + date + "]");
			inputParams.append("timeId=[" + timeId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/holdReservation", method = RequestMethod.GET)
	public @ResponseBody String holdReservation(@RequestParam String companyId,@RequestParam String procedureId,@RequestParam String locationId, 
			@RequestParam String departmentId,@RequestParam String eventId,@RequestParam String date,@RequestParam String timeId,
			@RequestParam String seatId,HttpServletRequest request,HttpServletResponse response) {
		OnlineJSONResponse jsonResponse = new OnlineJSONResponse();
		OnlineResvSessionData sessionData = null;
		try {			
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			
			try {
				new SimpleDateFormat("MM/dd/yyyy").parse(date); //To ensure that proper date format
				String customerId = getCustomerId(sessionData);		
				
				//To ensure that date is available in avail days
				boolean isDateAvailable = false;
				EventDatesResponse eventDatesResponse = sessionData.getEventDatesResponse();
				EventDate eventDate = null;
				for (String key : eventDatesResponse.getEventDateMap().keySet()) {
					eventDate = eventDatesResponse.getEventDateMap().get(key);
					//System.out.println("Key :: "+key + " Value ::: "+eventDate.getDate()+ " Status ::: "+eventDate.getStatus());
					if (eventDate != null){
						if (eventDate.getStatus() != null && "0".equals(eventDate.getStatus())) {							
							if(eventDate.getDate()!=null && eventDate.getDate().equals(date)){
								isDateAvailable = true;
							}							
						}
					}
				}
				
				eventDatesResponse = null;
				
				if(isDateAvailable){
					HoldResvResponse holdResvResponse = onlineResService.holdReservation(sessionData,companyId,procedureId,locationId,departmentId,eventId,timeId,seatId,customerId);
					if(holdResvResponse.isResponseStatus() && !holdResvResponse.isErrorStatus() 
							&& !"-1".equals(holdResvResponse.getScheduleId()) && !"0".equals(holdResvResponse.getScheduleId())){
						jsonResponse.setResponse("SUCCESS");
						jsonResponse.setMessage("Reservation holded successfully!");
						jsonResponse.setScheduleId(holdResvResponse.getScheduleId());
					}else{
						jsonResponse.setResponse("FAILURE");
						jsonResponse.setMessage(holdResvResponse.getErrorMessage());
					}
				}else{
					jsonResponse.setFormErrors(true);
					jsonResponse.setResponse("FAILURE");
					jsonResponse.setMessage("Please select proper data!");//TODO: we have get dynamically
					jsonResponse.setNotAvailDateEntered(true);
				}
			} catch (Exception ex) {
				jsonResponse.setFormErrors(true);
				jsonResponse.setResponse("FAILURE");
				jsonResponse.setMessage("Please select proper data!");//TODO: we have get dynamically
				jsonResponse.setNotAvailDateEntered(true);
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController holdReservation method :" + e, e);

			jsonResponse.setResponse("FAILURE");
			jsonResponse.setMessage("Error while holding reservation!");

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("companyId=[" + companyId + "],");
			inputParams.append("procedureId=[" + procedureId + "],");
			inputParams.append("locationId=[" + locationId + "],");
			inputParams.append("departmentId=[" + departmentId + "],");
			inputParams.append("eventId=[" + eventId + "]");
			inputParams.append("date=[" + date + "]");
			inputParams.append("timeId=[" + timeId + "]");
			inputParams.append("seatId=[" + seatId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		Gson gson = new Gson();		
		return gson.toJson(jsonResponse);
	}

	private String getCustomerId(OnlineResvSessionData sessionData) {
		String customerId = "0";
		//if login first means we are reading customer id from session other wise we are passing 0
		if(sessionData.getLoginFirst()!=null && "Y".equalsIgnoreCase(sessionData.getLoginFirst())){
			customerId = String.valueOf(sessionData.getCustomerId());	
		}else{
			customerId = "0";
		}
		return customerId;
	}

	@RequestMapping(value = "/releaseHoldEventTime", method = RequestMethod.GET)
	public @ResponseBody String releaseHoldEventTime(HttpServletRequest request,HttpServletResponse response,@RequestParam String scheduleId) throws BuilderException {
		OnlineJSONResponse jsonResponse = new OnlineJSONResponse();

		HttpSession session = request.getSession(false);
		OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
		String customerId = String.valueOf(sessionData.getCustomerId());
		try{
			BaseResponse baseResponse  = onlineResService.releaseHoldEventTime(sessionData,scheduleId);
			if(baseResponse.isResponseStatus() && !baseResponse.isErrorStatus()){
				jsonResponse.setResponse("SUCCESS");
			}else{
				jsonResponse.setResponse("FAILURE");
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController releaseHoldEventTime method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("scheduleId=[" + scheduleId + "],");
			inputParams.append("customerId=[" + customerId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);		
		}
		Gson gson = new Gson();		
		return gson.toJson(jsonResponse);
	}

	@RequestMapping(value = "/get_res_verification_left_section", method = RequestMethod.GET)
	public ModelAndView getResVerificationLeftSection(@RequestParam String scheduleId,HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_RESERVATION_VERIFICATION_LEFT_SECTION.getViewName();
		HttpSession session = request.getSession(false);
		OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
		String customerId = "";
		try{
			customerId = getCustomerId(sessionData);
			VerifyResvResponse resvVerifacationResponse  = onlineResService.getVerifyReservationDetails(sessionData,scheduleId,customerId);
			modelMap.addAttribute("resvVerifacationResponse", resvVerifacationResponse);
			logger.info("View page : " + viewName);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getResVerificationLeftSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("customerId=[" + customerId + "],");
			inputParams.append("scheduleId=[" + scheduleId + "],");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/get_confirmation_left_section", method = RequestMethod.GET)
	public ModelAndView getConfirmationLeftSection(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String scheduleId,@RequestParam String comment) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_CONFIRMATION_LEFT_SECTION.getViewName();
		HttpSession session = request.getSession(false);
		OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
		String customerId = String.valueOf(sessionData.getCustomerId());
		try{
			ConfirmResvResponse confirmResvResponse  = onlineResService.confirmReservation(sessionData,scheduleId,customerId,comment);
			modelMap.addAttribute("confirmResvResponse", confirmResvResponse);
			sessionData.setEventDatesResponse(null);
			session.setAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue(),sessionData);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getConfirmationLeftSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("scheduleId=[" + scheduleId + "],");
			inputParams.append("customerId=[" + customerId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);		
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/get_login_left_section", method = RequestMethod.GET)
	public ModelAndView getLoginLeftSection(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVESK_LOGIN_LEFT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			LoginForm loginForm = new LoginForm();
			modelMap.addAttribute("loginForm", loginForm);			
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			LoginInfoResponse loginInfoResponse = onlineResService.getLoginInfo(sessionData);			
			modelMap.addAttribute("loginFieldList",loginInfoResponse.getLoginFieldList());	
			modelMap.addAttribute("loginInfoResponse",loginInfoResponse);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getLoginLeftSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/get_login_right_section", method = RequestMethod.GET)
	public ModelAndView getLoginRightSection(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_LOGIN_RIGHT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			LoginInfoRightSideContentResponse loginRightSideContentResponse = onlineResService.getLoginInfoRightSideContent(sessionData);
			modelMap.addAttribute("loginRightSideContentResponse",loginRightSideContentResponse);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getLoginRightSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}
	
	@RequestMapping(value = "/get_reservation_right_section", method = RequestMethod.GET)
	public ModelAndView getReservationRightSection(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_RESERVATION_RIGHT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			ResvDetailsRightSideContentResponse resvDetailsRightSideContent = onlineResService.getResvDetailsRightSideContent(sessionData);
			modelMap.addAttribute("resvDetailsRightSideContent",resvDetailsRightSideContent);
						
			getReservedEventDetails(modelMap, sessionData,false);
			modelMap.addAttribute("reservedEventResponseDiv","resv_details_reservedEventResponseDiv");			
		} catch (Exception e) {
			logger.error("Error in OnlineResController getReservationRightSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}

	@RequestMapping(value = "/get_res_verification_right_section", method = RequestMethod.GET)
	public ModelAndView getResVerificationRightSection(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_RESERVATION_VERIFICATION_RIGHT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			ResvVerificationDetailsRightSideContentResponse resvVerificationDetailsRightSideContent =  onlineResService.getResvVerifyDetailsRightSideContent(sessionData);
			modelMap.addAttribute("resvVerificationDetailsRightSideContent",resvVerificationDetailsRightSideContent);
			
			if(sessionData.getLoginFirst()!=null && "Y".equalsIgnoreCase(sessionData.getLoginFirst())){
				getReservedEventDetails(modelMap, sessionData,false);
				modelMap.addAttribute("reservedEventResponseDiv","resv_Verify_ReservedEventResponseDiv");
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController getResVerificationRightSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}
	
	@RequestMapping(value = "/get_reserved_Event_Response", method = RequestMethod.GET)
	public ModelAndView getReservedEventResponse(@RequestParam String reservedEventResponseDiv,HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_RESERVED_EVENT_RESPONSE.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			getReservedEventDetails(modelMap, sessionData,false);
			modelMap.addAttribute("reservedEventResponseDiv",reservedEventResponseDiv);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getResVerificationRightSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}

	private void getReservedEventDetails(ModelMap modelMap,OnlineResvSessionData sessionData,boolean inConfirmPage) throws NoSuchFieldException,BuilderException {
		if(sessionData.getLoginFirst()!=null && "Y".equalsIgnoreCase(sessionData.getLoginFirst()) || inConfirmPage){
			ReservedEventResponse reservedEventResponse =onlineResService.getReservedEvents(sessionData);
			modelMap.addAttribute("reservedEventResponse",reservedEventResponse);
		}
	}

	@RequestMapping(value = "/get_confirmation_right_section", method = RequestMethod.GET)
	public ModelAndView getConfirmationRightSection(@RequestParam String eventId,@RequestParam String locationId,HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_CONFIRMATION_RIGHT_SECTION.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			ConfirmationPageRightSideContentResponse confirmationPageContent = onlineResService.getResvConfirmationPageRightSideContent(sessionData);
			modelMap.addAttribute("confirmationPageContent",confirmationPageContent);
			ListOfThingsResponse listOfThingsToBring = onlineResService.getListOfThingsToBringResponse(sessionData,eventId);
			modelMap.addAttribute("listOfThingsToBring",listOfThingsToBring);
			ConfPageContactDetailsResponse confPageContactDetails =  onlineResService.getConfPageContactDetails(sessionData,locationId);
			modelMap.addAttribute("confPageContactDetails",confPageContactDetails);
			
			getReservedEventDetails(modelMap, sessionData,true);
			modelMap.addAttribute("reservedEventResponseDiv","resv_Verify_ReservedEventResponseDiv");
			
		} catch (Exception e) {
			logger.error("Error in OnlineResController getConfirmationRightSection method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}	

	@RequestMapping(value = "/cancelReservation", method = RequestMethod.GET)
	public @ResponseBody String cancelReservation(HttpServletRequest request,HttpServletResponse response,@RequestParam String scheduleId) throws BuilderException {
		OnlineJSONResponse jsonResponse = new OnlineJSONResponse();

		HttpSession session = request.getSession(false);
		OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
		String customerId = String.valueOf(sessionData.getCustomerId());
		try{
			BaseResponse baseResponse  = onlineResService.cancelReservation(sessionData,scheduleId,customerId);
			if(baseResponse.isResponseStatus() && !baseResponse.isErrorStatus()){
				jsonResponse.setResponse("SUCCESS");				
			}else{
				jsonResponse.setResponse("FAILURE");
			}
			jsonResponse.setMessage(baseResponse.getErrorMessage());
		} catch (Exception e) {
			jsonResponse.setResponse("FAILURE");
			logger.error("Error in OnlineResController cancelReservation method :" + e, e);

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("scheduleId=[" + scheduleId + "],");
			inputParams.append("customerId=[" + customerId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);		
		}
		Gson gson = new Gson();		
		return gson.toJson(jsonResponse);
	}
		
	@RequestMapping(value = "/getEventHistory", method = RequestMethod.GET)
	public ModelAndView getEventHistory(HttpServletRequest request,HttpServletResponse response) throws BuilderException {
		ModelMap modelMap = new ModelMap();
		String viewName = JSPPageNameConstants.RESVDESK_SCHEDULED_EVENT_RESPONSE.getViewName();
		OnlineResvSessionData sessionData = null;
		try{
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			
			EventHistoryResponse eventHistoryResponse =onlineResService.getEventHistory(sessionData);
			modelMap.addAttribute("eventHistoryResponse",eventHistoryResponse);
		} catch (Exception e) {
			logger.error("Error in OnlineResController getEventHistory method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();

			String subject = "Error in OnlineRes for Client - " + (sessionData!=null ? sessionData.getClientCode() : "");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString("No Input paramters"));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);			
		}
		logger.info("View page : " + viewName);
		return new ModelAndView(viewName, modelMap);
	}	
	
	

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		String clientcode = null;
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				OnlineResvSessionData sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
				clientcode = sessionData.getClientCode();
				session.invalidate();
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController logout method :" + e, e);
			String subject = "Error in OnlineRes for Client - " + clientcode;
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString(""));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		return new ModelAndView("redirect:/login.html?client_code="+clientcode);
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController error method :" + e, e);
		}
		return new ModelAndView("error");
	}
	
	/* This method validates selected date. */
	@RequestMapping(value = "/validateSelectedDate", method = RequestMethod.POST)
	public @ResponseBody
	String validateSelectedDate(@RequestParam String date, HttpServletRequest request) throws Exception {
		String response = "";
    	String message = "";
    	boolean isDateAvailable = false;
    	OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			try {
				String dateStr = date;	
				new SimpleDateFormat("MM/dd/yyyy").parse(dateStr); //To ensure that enter date is proper date format
				
				if (dateStr != null && !"".equals(dateStr) && dateStr.contains("/")) {					
					String[] splitStr = dateStr.split("/");// 01/17/2014 ===> 2014-01-17
					dateStr = splitStr[2] + "-" + splitStr[0] + "-" + splitStr[1];
				}
				
				//To ensure that date is available in avail days				
				EventDatesResponse eventDatesResponse = sessionData.getEventDatesResponse();
				EventDate eventDate = null;
				for (String key : eventDatesResponse.getEventDateMap().keySet()) {
					eventDate = eventDatesResponse.getEventDateMap().get(key);
					//System.out.println("Key :: "+key + " Value ::: "+eventDate.getDate()+ " Status ::: "+eventDate.getStatus());
					if (eventDate != null){
						if (eventDate.getStatus() != null && "0".equals(eventDate.getStatus())) {							
							if(eventDate.getDate()!=null && eventDate.getDate().equals(date)){
								isDateAvailable = true;
							}							
						}
					}
				}
				
				if(isDateAvailable){
					response = "SUCESSES";
            		message = "Valid Date format and Date is available!"; 
				}else{
					response = "ERROR";
            		message = "Invalid date!"; 
            		sendErrorMail(date, sessionData.getClientCode()," not available date ",null);
				}					
			} catch (Exception ex) {
				logger.error("Error in OnlineApptController validateSelectedDate method :" + ex, ex);
				response = "ERROR";
        		message = "Invalid date!"; 
        		
        		sendErrorMail(date, sessionData.getClientCode(), "invalid date format",ex);
			}
		} catch (Exception e) {
			logger.error("Error in OnlineApptController validateSelectedDate method :" + e, e);

			sendErrorMail(date, sessionData.getClientCode(), "invalid date format",e);
			
			response = "ERROR";
    		message = "Invalid date!"; 
		}
		//System.out.println("{\"message\": \""+message+"\",\"response\": \""+response+"\"}");
		return "{\"message\": \""+message+"\",\"response\": \""+response+"\"}";
	}

	private void sendErrorMail(String date,String clientCode,String message, Exception ex) {
		String subject = " Error in OnlineAppt for Client - " + clientCode;
		StringBuilder errorMsg = new StringBuilder();
		errorMsg.append("MethodName:com.telappoint.onlineappt.controller.OnlineApptController.validateSelectedDate(OnlineApptController.java)");
		errorMsg.append("<br/>");

		errorMsg.append(OnlineResUtils.getJSONString("Input paramters"));
		if(ex!=null){
			errorMsg.append("<br/> Caused By : " + ((ex.getMessage() !=null)?ex.getMessage():"") +ex.toString() + "<br/>");
		}
		errorMsg.append("<br/> Selected/Entered Date : " +date + "<br/>");
		errorMsg.append("<br/> <b> <div style=\"color:red;\"> Some one is trying to enter ");
		errorMsg.append(message);
		errorMsg.append(" !!!!!! </b> </div><br/>");
		OnlineResUtils.sendErrorEmail(errorMsg, ex, subject);
	}
	
	
	@RequestMapping(value = "/viewExtResvDtls", method = RequestMethod.GET)
	public ModelAndView viewExtResvDtls(ModelMap map, HttpServletRequest request, HttpServletResponse response)throws BuilderException {
		String viewName = JSPPageNameConstants.RESVDESK_LOGIN_FIRST_PAGE.getViewName();
		ModelMap modelMap = new ModelMap();
		OnlineResvSessionData sessionData = null;
		try {
			HttpSession session = request.getSession(false);
		    sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
		    String ipAddress = InetAddress.getLocalHost().getHostAddress();
		    
			ClientInfo clientInfo = onlineResService.getClientInfo(sessionData.getClientCode(),ipAddress,session.getId(),"",sessionData.getLangCode());
			clientInfo.setLoginFirst("Y");			
			viewName = populateData(modelMap, session, clientInfo);
			modelMap.addAttribute("clientInfo", clientInfo);
		} catch (Exception e) {
			logger.error("Error in OnlineResvController viewExtResvDtls method :" + e, e);
			viewName = JSPPageNameConstants.RESVDESK_ERROR_PAGE.getViewName();
			String subject = "Error in OnlineResv for Client - " + (sessionData!=null ? sessionData.getClientCode() : " Null (Session expired)");
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			errorMsg.append(OnlineResUtils.getJSONString(""));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);

		}
		return new ModelAndView(viewName, modelMap);
	}
	
	@RequestMapping(value = "/isAllowDuplicateResv", method = RequestMethod.GET)
	public @ResponseBody String isAllowDuplicateResv(@RequestParam String eventId,HttpServletRequest request) {
		OnlineJSONResponse jsonResponse = new OnlineJSONResponse();
		OnlineResvSessionData sessionData = null;
		String customerId = "";
		try {			
			HttpSession session = request.getSession(false);
			sessionData = (OnlineResvSessionData)session.getAttribute(OnlineResContants.ONLINE_RESV_SESSION_DATA_SESSION_VARIABLE.getValue());
			//customerId = getCustomerId(sessionData);	
			customerId = String.valueOf(sessionData.getCustomerId());	
			if(customerId!=null && !"".equals(customerId) && !"-1".equals(customerId)){
				AllowDuplicateResv allowDuplicateResv = onlineResService.isAllowDuplicateResv(sessionData, eventId, customerId);
				if(allowDuplicateResv.isAllowDuplicateResv() && allowDuplicateResv.isResponseStatus() && !allowDuplicateResv.isErrorStatus()){
					jsonResponse.setResponse("SUCCESS");
					jsonResponse.setMessage(allowDuplicateResv.getMessage());
				}else{
					jsonResponse.setResponse("FAILURE");
					jsonResponse.setMessage(allowDuplicateResv.getMessage());
				}
			}else{
				jsonResponse.setResponse("FAILURE");
				jsonResponse.setMessage("Error while checking isAllowDuplicateResv!");
			}
		} catch (Exception e) {
			logger.error("Error in OnlineResController isAllowDuplicateResv method :" + e, e);

			jsonResponse.setResponse("FAILURE");
			jsonResponse.setMessage("Error while checking isAllowDuplicateResv!");

			String subject = "Error in OnlineRes for Client - " + sessionData.getClientCode();
			StringBuilder errorMsg = new StringBuilder();
			errorMsg.append("MethodName:" + OnlineResUtils.removeErrorNumber(OnlineResUtils.getMethodName(1)));
			errorMsg.append("<br/>");

			StringBuilder inputParams = new StringBuilder();
			inputParams.append("customerId=[" + customerId + "],");
			inputParams.append("eventId=[" + eventId + "]");

			errorMsg.append(OnlineResUtils.getJSONString(inputParams.toString()));
			errorMsg.append("<br/> Caused By:" + ((e.getMessage() !=null)?e.getMessage():"") +e.toString() + "<br/>");
			OnlineResUtils.sendErrorEmail(errorMsg, e, subject);
		}
		Gson gson = new Gson();		
		return gson.toJson(jsonResponse);
	}
	
	@RequestMapping(value = "/session-expired", method = RequestMethod.GET)
	public ModelAndView sessionExpired(ModelMap map, HttpServletRequest request, HttpServletResponse response)throws BuilderException {
		String viewName = JSPPageNameConstants.RESVDESK_SESSION_EXPIRED_PAGE.getViewName();
		ModelMap modelMap = new ModelMap();
		try {
			/*HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}*/
		} catch (Exception e) {
			logger.error("Error in OnlineResvController sessionExpired method :" + e, e);
		}
		return new ModelAndView(viewName, modelMap);
	}
}