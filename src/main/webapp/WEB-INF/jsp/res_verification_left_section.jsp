<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres.js?version=1.0"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres_resv_verify_page.js?version=1.1"></script>

<!-- Reservation Verification starts -->

	<c:if test ="${resvVerifacationResponse.displayCompany == 'y' || resvVerifacationResponse.displayCompany == 'Y'}">
		<c:if test ="${( resvVerifacationResponse.companyName ne 'dummy' && resvVerifacationResponse.companyName  ne 'Dummy'
						&& resvVerifacationResponse.companyName ne 'dummay' && resvVerifacationResponse.companyName ne 'Dummay')}">
			<div class="labelName"> ${resvVerifacationResponse.displayCompanyLabel}</div>
			<div class="labelDisplay">${resvVerifacationResponse.companyName}</div>
		</c:if>
	</c:if>

	<c:if test ="${resvVerifacationResponse.displayProcedure == 'y' || resvVerifacationResponse.displayProcedure == 'Y'}">	
		<c:if test ="${( resvVerifacationResponse.procedure ne 'dummy' && resvVerifacationResponse.procedure  ne 'Dummy'
						&& resvVerifacationResponse.procedure ne 'dummay' && resvVerifacationResponse.procedure ne 'Dummay')}">
			<div class="labelName">${resvVerifacationResponse.displayProcedureLabel}</div>
			<div class="labelDisplay">${resvVerifacationResponse.procedure}</div>
		</c:if>
	</c:if>
	
	<c:if test ="${resvVerifacationResponse.displayLocation == 'y' || resvVerifacationResponse.displayLocation == 'Y'}">	
		 <div class="labelName">${resvVerifacationResponse.displayLocationLabel}</div>
		<div class="labelDisplay">${resvVerifacationResponse.location}</div>
	</c:if>

	<c:if test ="${resvVerifacationResponse.displayDepartment == 'y' || resvVerifacationResponse.displayDepartment == 'Y'}">	
		<c:if test ="${( resvVerifacationResponse.department ne 'dummy' && resvVerifacationResponse.department  ne 'Dummy'
						&& resvVerifacationResponse.department ne 'dummay' && resvVerifacationResponse.department ne 'Dummay')}">
			<div class="labelName">${resvVerifacationResponse.displayDepartmentLabel}</div>
			<div class="labelDisplay">${resvVerifacationResponse.department}</div>
		</c:if>
	</c:if>
	
	<c:if test ="${resvVerifacationResponse.displayEvent == 'y' || resvVerifacationResponse.displayEvent == 'Y'}">
		 <div class="labelName">${resvVerifacationResponse.displayEventLabel}</div>
		<div class="labelDisplay">${resvVerifacationResponse.event}</div>
	</c:if>

	<div class="labelName">${resvVerifacationResponse.dateLabel}</div>
	<div class="labelDisplay">${resvVerifacationResponse.reservationDate}</div>

	<div class="labelName">${resvVerifacationResponse.timeLabel}</div>
    <div class="labelDisplay">${resvVerifacationResponse.reservationTime}</div>
	 
	<c:if test ="${resvVerifacationResponse.displaySeat == 'y' || resvVerifacationResponse.displaySeat == 'Y'}">
		 <div class="labelName">${resvVerifacationResponse.displaySeatLabel}</div>
		 <div class="labelDisplay">${resvVerifacationResponse.seatNumber}</div>
	</c:if>
						  
  <input type="button" value="${resvVerifacationResponse.nextBtn}" class="next float_right" id="resVerificationNextBtn">
  <input type="button" value="${resvVerifacationResponse.backBtn}" class="back float_right" id="resVerificationBackBtn">
<!-- Reservation Verification ends -->