<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres.js?version=1.1"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres_resv_details_page.js?version=2.5"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/JQueryCalender.js?version=1.8"></script>

	<input type="hidden" id="resv_scheduleId"  value="-1"/>	
	<input type="hidden"  id="loadDatesDataOnLoadingOfEventsData" value="${loadDatesDataOnLoadingOfEventsData}"/> 
	<!-- on page load we are checking that if event is populates then this value is changed to YES -->

	<!-- Reservation details starts -->
	<input type="hidden" id="display_company_asc_val"  value="${resvDetailsResponse.displayCompany}" >
	<input type="hidden" id="display_procedure_asc_val"  value="${resvDetailsResponse.displayProcedure}" >
	<input type="hidden" id="display_location_asc_val"   value="${resvDetailsResponse.displayLocation}" >
	<input type="hidden" id="display_department_asc_val"  value="${resvDetailsResponse.displayDepartment}" >
	<input type="hidden" id="display_event_asc_val"  value="${resvDetailsResponse.displayEvent}" >
	<input type="hidden" id="date_asc_val"  value="true" >
	<input type="hidden" id="display_time_asc_val"  value="true" >
	<input type="hidden" id="display_seat_asc_val"  value="${resvDetailsResponse.displaySeat}" >

	<input type="hidden" id="display_company_loadded"  value="false"/>
	<input type="hidden" id="display_procedure_loadded"  value="false"/>
	<input type="hidden" id="display_location_loadded"  value="false"/>
	<input type="hidden" id="display_department_loadded"  value="false"/>
	<input type="hidden" id="display_event_loadded"  value="false"/>
	<input type="hidden" id="display_seat_loadded"  value="false"/>

	<input type="hidden" id="display_company_label"  value="${resvDetailsResponse.displayCompanyLabel}" >
	<input type="hidden" id="display_procedure_label"  value="${resvDetailsResponse.displayProcedureLabel}" >
	<input type="hidden" id="display_location_label"   value="${resvDetailsResponse.displayLocationLabel}" >
	<input type="hidden" id="display_department_label"  value="${resvDetailsResponse.displayDepartmentLabel}" >
	<input type="hidden" id="display_event_label"  value="${resvDetailsResponse.displayEventLabel}" >
	<input type="hidden" id="date_label"  value="${resvDetailsResponse.dateLabel}" >
	<input type="hidden" id="display_time_label"  value="${resvDetailsResponse.timeLabel}" >
	<input type="hidden" id="display_seat_label"  value="${resvDetailsResponse.displaySeatLabel}" >

	<input type="hidden" id="time_select_label"  value="${resvDetailsResponse.selectTimeLabel}" >
	<input type="hidden" id="seat_select_label"  value="${resvDetailsResponse.selectSeatLabel}" >

	<input type="hidden" id="selectDateLabel_asc_val"  value="${resvDetailsResponse.selectDateLabel}" >

<form:form id="reservationForm" name="reservationForm"  method="POST" modelAttribute="loginForm" commandName="loginForm" >

	<div id="display_company_DivId">		
		<c:if test ="${resvDetailsResponse.displayCompany == 'y' || resvDetailsResponse.displayCompany == 'Y'}">	
			<c:if test ="${loginForm.display_company != '0'}">
				<label for="Company"> ${resvDetailsResponse.displayCompanyLabel}</label>
				<c:if test= "${fn:length(companyListResponse.companyList) gt  1}">	
					<form:select path="display_company" onchange="populateNextFieldData('display_company');" >
						<form:option value="-1">${resvDetailsResponse.selectCompanyLabel}</form:option>
						<%-- <form:options items="${companyListResponse.companyList}" itemLabel="optionValue" itemValue="optionKey"></form:options> --%>
						<c:forEach items="${companyListResponse.companyList}" var="company">						
							<form:option value="${company.optionKey}"  selected="${company.optionKey==resvDetailsResponse.selectDefaultCompanyId ? 'selected' : '' }"> ${company.optionValue}</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
							$("#display_company_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(companyListResponse.companyList) eq  1}">	
					<label class="label_big" for="companyLabel"> ${companyListResponse.companyList[0].optionValue} </label>
					<input type="hidden" name="display_company" id="display_company" value="${companyListResponse.companyList[0].optionKey}"/>
					<script type="text/javascript">
							//populateNextFieldData('display_company');
							$("#display_company_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(companyListResponse.companyList) lt  1}">	
					<form:select path="display_company" onchange="populateNextFieldData('display_company');">
						<form:option value="-1">${resvDetailsResponse.selectCompanyLabel}</form:option>
					</form:select>
				</c:if>
			</c:if>
			<c:if test ="${loginForm.display_company == '0'}">
				<input type="hidden" name="display_company" id="display_company" value="${loginForm.display_company}"/>
			</c:if>
		</c:if>
		<c:if test ="${resvDetailsResponse.displayCompany == 'n' || resvDetailsResponse.displayCompany == 'N'}">
				<input type="hidden" name="display_company" id="display_company" value="${loginForm.display_company}"/>		
		</c:if>		
	</div>
	<div id="display_company_error_DivId" class="errorMsg ui-state-error"></div>

	<div id="display_procedure_DivId">
		<c:if test ="${resvDetailsResponse.displayProcedure == 'y' || resvDetailsResponse.displayProcedure == 'Y'}">	
			<c:if test ="${loginForm.display_procedure != '0'}">
				<label for="Procedure"> ${resvDetailsResponse.displayProcedureLabel}</label> 								
				<c:if test= "${fn:length(procedureListResponse.procedureList) gt  1}">	
					<form:select path="display_procedure" onchange="populateNextFieldData('display_procedure');">
						<form:option value="-1">${resvDetailsResponse.selectProcedureLabel}</form:option>
						<c:forEach items="${procedureListResponse.procedureList}" var="procedure">						
							<form:option value="${procedure.optionKey}"  selected="${procedure.optionKey==resvDetailsResponse.selectDefaultProcedureId ? 'selected' : '' }"> ${procedure.optionValue}</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
							$("#display_procedure_loadded").val("true");
					</script>
				</c:if>		
				<c:if test= "${fn:length(procedureListResponse.procedureList) eq  1}">	
					<label class="label_big" for="procedureLabel"> ${procedureListResponse.procedureList[0].optionValue} </label> 
					<input type="hidden" name="display_procedure" id="display_procedure" value="${procedureListResponse.procedureList[0].optionKey}"/>
					<script type="text/javascript">
							//populateNextFieldData('display_procedure');
							$("#display_procedure_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(procedureListResponse.procedureList) lt  1}">	
					<form:select path="display_procedure" onchange="populateNextFieldData('display_procedure');">
						<form:option value="-1">${resvDetailsResponse.selectProcedureLabel}</form:option>
					</form:select>
				</c:if>	
			</c:if>	
			<c:if test ="${loginForm.display_procedure == '0'}">
				<input type="hidden" name="display_procedure" id="display_procedure" value="${loginForm.display_procedure}"/>
			</c:if>	
		</c:if>
		<c:if test ="${resvDetailsResponse.displayProcedure == 'n' || resvDetailsResponse.displayProcedure == 'N'}">	
				<input type="hidden" name="display_procedure" id="display_procedure" value="${loginForm.display_procedure}"/>
		</c:if>
	</div>
	<div  id="display_procedure_error_DivId" class="errorMsg ui-state-error"></div>

	<div id="display_location_DivId">	
		<c:if test ="${resvDetailsResponse.displayLocation == 'y' || resvDetailsResponse.displayLocation == 'Y'}">	
			<c:if test ="${loginForm.display_location != '0'}">
				<label for="Location"> ${resvDetailsResponse.displayLocationLabel}</label>							
				<c:if test= "${fn:length(locationListResponse.locationList) gt  1}">	
					<form:select path="display_location" onchange="populateNextFieldData('display_location');">
						<form:option value="-1">${resvDetailsResponse.selectLocationLabel}</form:option>
						<c:forEach items="${locationListResponse.locationList}" var="location">						
							<form:option value="${location.optionKey}"  selected="${location.optionKey==resvDetailsResponse.selectDefaultLocationId ? 'selected' : '' }"> ${location.optionValue}</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
							$("#display_location_loadded").val("true");
					</script>
				</c:if>		
				<c:if test= "${fn:length(locationListResponse.locationList) eq  1}">	
					<label class="label_big" for="locationLabel"> ${locationListResponse.locationList[0].optionValue} </label>
					<input type="hidden" name="display_location" id="display_location" value="${locationListResponse.locationList[0].optionKey}"/>
					<script type="text/javascript">
							//populateNextFieldData('display_location');
							$("#display_location_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(locationListResponse.locationList) lt  1}">	
					<form:select path="display_location" onchange="populateNextFieldData('display_location');">
						<form:option value="-1">${resvDetailsResponse.selectLocationLabel}</form:option>
					</form:select>
				</c:if>
			</c:if>
			<c:if test ="${loginForm.display_location == '0'}">
				<input type="hidden" name="display_location" id="display_location" value="${loginForm.display_location}"/>
			</c:if>
		</c:if>
		<c:if test ="${resvDetailsResponse.displayLocation == 'n' || resvDetailsResponse.displayLocation == 'N'}">	
				<input type="hidden" name="display_location" id="display_location" value="${loginForm.display_location}"/>
		</c:if>
	</div>
	<div id="display_location_error_DivId" class="errorMsg ui-state-error"></div>

	<div id="display_department_DivId">	
		<c:if test ="${resvDetailsResponse.displayDepartment == 'y' || resvDetailsResponse.displayDepartment == 'Y'}">	
			<c:if test ="${loginForm.display_department != '0'}">
				<label for="Department">${resvDetailsResponse.displayDepartmentLabel}</label>		
				<c:if test= "${fn:length(departmentListResponse.departmentList) gt  1}">	
					<form:select path="display_department" onchange="populateNextFieldData('display_department');">
						<form:option value="-1">${resvDetailsResponse.selectDepartmentLabel}</form:option>
						<c:forEach items="${departmentListResponse.departmentList}" var="department">						
							<form:option value="${department.optionKey}"  selected="${department.optionKey==resvDetailsResponse.selectDefaultDepartmentId ? 'selected' : '' }"> ${department.optionValue}</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
							$("#display_department_loadded").val("true");
					</script>
				</c:if>		
				<c:if test= "${fn:length(departmentListResponse.departmentList) eq  1}">	
					<label class="label_big" for="departmentLabel"> ${departmentListResponse.departmentList[0].optionValue} </label>
					<input type="hidden" name="display_department" id="display_department" value="${departmentListResponse.departmentList[0].optionKey}"/>
					<script type="text/javascript">
							//populateNextFieldData('display_department');
							$("#display_department_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(departmentListResponse.departmentList) lt  1}">	
					<form:select path="display_department" onchange="populateNextFieldData('display_department');">
						<form:option value="-1">${resvDetailsResponse.selectDepartmentLabel}</form:option>
					</form:select>
				</c:if>	
			</c:if>	
			<c:if test ="${loginForm.display_department == '0'}">
				<input type="hidden" name="display_department" id="display_department" value="${loginForm.display_department}"/>
			</c:if>	
		</c:if>
		<c:if test ="${resvDetailsResponse.displayDepartment == 'n' || resvDetailsResponse.displayDepartment == 'N'}">
				<input type="hidden" name="display_department" id="display_department" value="${loginForm.display_department}"/>
		</c:if>
	</div>
	<div id="display_department_error_DivId" class="errorMsg ui-state-error"></div>

	<div id="display_event_DivId">		
		<c:if test ="${resvDetailsResponse.displayEvent == 'y' || resvDetailsResponse.displayEvent == 'Y'}">
			<c:if test ="${loginForm.display_event != '0'}">
				<label for="Event">${resvDetailsResponse.displayEventLabel}</label>						
				<c:if test= "${fn:length(eventListResponse.eventList) gt  1}">	
					<form:select path="display_event" onchange="populateNextFieldData('display_event');">
						<form:option value="-1">${resvDetailsResponse.selectEventLabel}</form:option>
						<c:forEach items="${eventListResponse.eventList}" var="event">						
							<form:option value="${event.optionKey}"  selected="${event.optionKey==resvDetailsResponse.selectDefaultEventId ? 'selected' : '' }"> ${event.optionValue}</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
							$("#display_event_loadded").val("true");
					</script>
				</c:if>		
				<c:if test= "${fn:length(eventListResponse.eventList) eq  1}">	
					<label class="label_big" for="eventLabel"> ${eventListResponse.eventList[0].optionValue} </label>
					<input type="hidden" name="display_event" id="display_event" value="${eventListResponse.eventList[0].optionKey}"/>
					<script type="text/javascript">
							$("#loadDatesDataOnLoadingOfEventsData").val("YES");
							$("#display_event_loadded").val("true");
					</script>
				</c:if>
				<c:if test= "${fn:length(eventListResponse.eventList) lt  1}">	
					<form:select path="display_event" onchange="populateNextFieldData('display_event');">
						<form:option value="-1">${resvDetailsResponse.selectEventLabel}</form:option>
					</form:select>
				</c:if>	
			</c:if>	
			<c:if test ="${loginForm.display_event == '0'}">
				<input type="hidden" name="display_event" id="display_event"  value="${loginForm.display_event}"/>
				<script type="text/javascript">
						$("#loadDatesDataOnLoadingOfEventsData").val("YES");
						$("#display_event_loadded").val("true");
				</script>
			</c:if>	
		</c:if>
		<c:if test ="${resvDetailsResponse.displayEvent == 'n' || resvDetailsResponse.displayEvent == 'N'}">
				<input type="hidden" name="display_event" id="display_event"  value="${loginForm.display_event}"/>
				<script type="text/javascript">
						$("#loadDatesDataOnLoadingOfEventsData").val("YES");
						$("#display_event_loadded").val("true");
				</script>
		</c:if>
	</div>
	<div id="display_event_error_DivId" class="errorMsg ui-state-error"></div>
	
	<div class="datePicker_Class" id="dateHiddenDivId"></div>
	<label for="txtDate">${resvDetailsResponse.dateLabel}</label>	
	<form:input  path="date" id="date"  value="-1" onfocus='if(this.value=="${resvDetailsResponse.selectDateLabel}"){this.value="";}' />
	 <div id="date_error_DivId" class="errorMsg ui-state-error"></div>
	 
	  <div id="date_time_div_id">
		<label for="cblTime">${resvDetailsResponse.timeLabel}</label>	  		
		 <form:select path="display_time" id="display_time"  onchange="loadSeatDetailsOnSelectionOfTime('display_time');">
			   <form:option value="-1">${resvDetailsResponse.selectTimeLabel}</form:option>
		 </form:select>
	  </div>
	  <div  id="display_time_error_DivId"  class="errorMsg ui-state-error"></div>
	
	<div id="display_seat_DivId">		
		<c:if test ="${resvDetailsResponse.displaySeat == 'y' || resvDetailsResponse.displaySeat == 'Y'}">
			<label for="Seat">${resvDetailsResponse.displaySeatLabel}</label>
			<form:select path="display_seat" onchange="validateSeatSelection();">
				<form:option value="-1">${resvDetailsResponse.selectSeatLabel}</form:option>
			</form:select>
		</c:if>
	</div>
	<div  id="display_seat_error_DivId"   class="errorMsg ui-state-error"></div>

   <input type="button" value="${resvDetailsResponse.nextBtn}" class="next float_right"  id="resDetailsNextBtn">
	<c:if test ="${sessionData.loginFirst == 'Y' || sessionData.loginFirst == 'y'}">
		<input type="button" value="${resvDetailsResponse.backBtn}" class="back float_right" id="resDetailsBackBtn">
	</c:if>	

  </form:form>
 <!-- Reservation details ends -->               