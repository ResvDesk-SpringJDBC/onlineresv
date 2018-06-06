<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
	
	<%-- Reservation Verification starts --%>	
	<c:if test ="${sessionData.loginFirst == 'y' || sessionData.loginFirst == 'Y'}">
		${resvVerificationDetailsRightSideContent.resvVerificationDetailsRightSideContent}
		
		<div id="resv_Verify_ReservedEventResponseDiv">
			<%@ include file="reserved_Event_Response.jsp"%>
		</div>
	</c:if>

		<c:if test ="${sessionData.loginFirst == 'n' || sessionData.loginFirst == 'N'}">
			${resvVerificationDetailsRightSideContent.resvVerificationDetailsRightSideContent}
		</c:if>
     <%-- Reservation Verification ends --%>