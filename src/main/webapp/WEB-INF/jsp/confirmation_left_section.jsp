<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres.js?version=1.0"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres_confirm_page.js?version=1.0"></script>

<!-- Confirmation starts -->
   <div class="labelName">${confirmResvResponse.confirmationNoLabel}</div>
   <div class="labelDisplay">${confirmResvResponse.confNumber}</div>
   
   <div class="labelName">${confirmResvResponse.nameLabel}</div>
   <div class="labelDisplay">${confirmResvResponse.firstName} ${confirmResvResponse.lastName}</div>
  
	<c:if test ="${confirmResvResponse.displayCompany == 'y' || confirmResvResponse.displayCompany == 'Y'}">
		<c:if test ="${( confirmResvResponse.companyName ne 'dummy' && confirmResvResponse.companyName  ne 'Dummy'
						&& confirmResvResponse.companyName ne 'dummay' && confirmResvResponse.companyName ne 'Dummay')}">
			<div class="labelName"> ${confirmResvResponse.displayCompanyLabel}</div>
			<div class="labelDisplay">${confirmResvResponse.companyName}</div>
		</c:if>
	</c:if>

	<c:if test ="${confirmResvResponse.displayProcedure == 'y' || confirmResvResponse.displayProcedure == 'Y'}">
		<c:if test ="${( confirmResvResponse.procedure ne 'dummy' && confirmResvResponse.procedure  ne 'Dummy'
						&& confirmResvResponse.procedure ne 'dummay' && confirmResvResponse.procedure ne 'Dummay')}">
			<div class="labelName">${confirmResvResponse.displayProcedureLabel}</div>
			<div class="labelDisplay">${confirmResvResponse.procedure}</div>
		</c:if>
	</c:if>
	
	<c:if test ="${confirmResvResponse.displayLocation == 'y' || confirmResvResponse.displayLocation == 'Y'}">	
		 <div class="labelName">${confirmResvResponse.displayLocationLabel}</div>
		<div class="labelDisplay">${confirmResvResponse.location}</div>
	</c:if>

	<c:if test ="${confirmResvResponse.displayDepartment == 'y' || confirmResvResponse.displayDepartment == 'Y'}">	
		<c:if test ="${( confirmResvResponse.department ne 'dummy' && confirmResvResponse.department  ne 'Dummy'
						&& confirmResvResponse.department ne 'dummay' && confirmResvResponse.department ne 'Dummay')}">
			<div class="labelName">${confirmResvResponse.displayDepartmentLabel}</div>
			<div class="labelDisplay">${confirmResvResponse.department}</div>
		</c:if>
	</c:if>
	
	<c:if test ="${confirmResvResponse.displayEvent == 'y' || confirmResvResponse.displayEvent == 'Y'}">
		 <div class="labelName">${confirmResvResponse.displayEventLabel}</div>
		<div class="labelDisplay">${confirmResvResponse.event}</div>
	</c:if>

	<div class="labelName">${confirmResvResponse.dateLabel}</div>
	<div class="labelDisplay">${confirmResvResponse.displayDate}</div>

	<div class="labelName">${confirmResvResponse.timeLabel}</div>
    <div class="labelDisplay">${confirmResvResponse.displayTime}</div>
	 
	<c:if test ="${confirmResvResponse.displaySeat == 'y' || confirmResvResponse.displaySeat == 'Y'}">
		 <div class="labelName">${confirmResvResponse.displaySeatLabel}</div>
		 <div class="labelDisplay">${confirmResvResponse.seatNumber}</div>
	</c:if>

	<%--
	<c:if test ="${confirmResvResponse.message != '' &&  confirmResvResponse.message !=null}">
		 <div class="labelName">Message</div>
		 <div class="labelDisplay">${confirmResvResponse.message}</div>
	</c:if>
	--%>

  <input type="button" value="${confirmResvResponse.printBtn}" class="float_right print" id="confirmationPrintBtn">
<!-- Confirmation ends -->