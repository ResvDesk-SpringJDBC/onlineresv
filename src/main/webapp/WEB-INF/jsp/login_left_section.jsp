<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres.js?version=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres_login_page.js?version=1.6"></script>

<script type="text/javascript" >
	$( document ).ready(function() {
			$("form :input").attr("autocomplete", "off");
	});
</script>

<!-- Login section starts -->
	<form:form id="loginForm" name="loginForm"  method="POST" modelAttribute="loginForm" commandName="loginForm" >
				
			<c:forEach var="ldetails" items="${loginFieldList}">
		<%--
				HTML input field attributes details
				----------------------------------------------
				maxlength=The maximum number of characters allowed in the <input> element. Default value is 524288
				size=Specifies the width of an <input> element, in characters. Default value is 20
		--%>
				<c:if test="${ldetails.displayType != 'button'}">
					<label for="${ldetails.fieldName}">${ldetails.displayTitle}</label>
					
					<c:if test ="${ldetails.displayType == 'textbox'}">
						<form:input type="${ldetails.displayType}" path="${ldetails.fieldName}" id="${ldetails.fieldName}" maxlength="${ldetails.maxLength}"  size="${ldetails.displaySize}" value="${ldetails.initValue}"/>
					</c:if>
				
					<c:if test ="${fn:contains(ldetails.displayType, 'textbox-')}">								
						<c:set var = "textsplit" value="${fn:split(ldetails.displayType, '-')}"/>
						<%-- <c:set var="len" value="${fn:length(textsplit)}"/> --%>
						<c:forEach var="i" begin="1" end="${fn:length(textsplit)-1}">
							<form:input type="${textsplit[0]}" path="${ldetails.fieldName}_${i}" id="${ldetails.fieldName}_${i}"  maxlength="${textsplit[i]}" size="${textsplit[i]}" style="margin-right: 20px;width: 61px;" class="phoneField"/>		
						</c:forEach>
					</c:if>
					<%-- style="margin-right: 20px;width: 61px;" --%>
				
					<c:if test ="${ldetails.displayType == 'select'}">
						<c:set var = "labelsplit" value="${fn:split(ldetails.listLabels, ',')}"/>
						<c:set var = "valuesplit" value="${fn:split(ldetails.listValues, ',')}"/>
						<form:select path="${ldetails.fieldName}">
							<c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">
								<option value="${valuesplit[i]}">${labelsplit[i]}</option>
							</c:forEach>
						</form:select>
					</c:if>
				
					<c:if test ="${ldetails.displayType == 'radio'}">			    
						<c:set var = "labelsplit" value="${fn:split(ldetails.listLabels, ',')}"/>
						<c:set var = "valuesplit" value="${fn:split(ldetails.listValues, ',')}"/>
						<table><tr valign="top">
						<c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">
							<td>${labelsplit[i]}</td>
							<td><form:radiobutton class="inputSmall" path="${ldetails.fieldName}" id="${ldetails.fieldName}" value="${valuesplit[i]}"/></td>
						</c:forEach></tr>
						</table>			
					</c:if>
													
					<c:set var="errors"><form:errors path="${ldetails.fieldName}" /></c:set>
					<c:if test="${not empty errors}">
						<div class="errorMsg ui-state-error"><span class="ui-icon ui-icon-alert float_left"></span><form:errors path="${ldetails.fieldName}" /></div>
					</c:if>	

			<%--	<div class="notes">${ldetails.fieldNotes}</div>--%>
				 </c:if>  
			</c:forEach>
						
			<div class="errorMsg ui-state-error" id="isAllowDuplicateResvMsgDiv"></div>
			<br/>
			<input type="button" value="${loginInfoResponse.nextBtn}" class="next float_right" id="loginNextBtn">
			<c:if test ="${sessionData.loginFirst == 'N' || sessionData.loginFirst == 'n'}">
				<input type="button" value="${loginInfoResponse.backBtn}" class="back float_right" id="loginBackBtn">
			</c:if>	

	  </form:form>
<!-- Login section ends -->