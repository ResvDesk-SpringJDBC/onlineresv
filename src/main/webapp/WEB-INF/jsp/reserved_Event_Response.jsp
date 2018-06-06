<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:if test ="${fn:length(reservedEventResponse.reservedEvents) gt 0}">
	<div id="currentReservation">
	  <div class="resvHeader"><h2>${reservedEventResponse.rightSideEventHeader}</h2></div>          
		   <c:set var="count" value="0" scope="page" />
		  <c:forEach var="reservedEvent" items="${reservedEventResponse.reservedEvents}">	
		   <div class="${count%2 == 0 ? 'resvBoxEven' : 'resvBoxOdd'}">
				<span>
					<%--
					<fmt:parseDate value="${reservedEvent.date}" pattern="${REST_WS_DATE_FORMAT}" var="parsedDate"/>
					<fmt:formatDate value="${parsedDate}" var="formatedDate" pattern="${FRONT_END_DATE_FORMAT}"/>
					<c:out value="${formatedDate}" />
					@
					<fmt:parseDate value="${reservedEvent.time}" pattern="${REST_WS_TIME_FORMAT}" var="parsedTime"/>
					<fmt:formatDate value="${parsedTime}" var="formatedTime" pattern="${FRONT_END_TIME_FORMAT}"/>
					<c:out value="${formatedTime}" />
					--%>
					${reservedEvent.date} @  ${reservedEvent.time}
					<a href="javascript:void(0)" onclick="cancelScheduledReservation('${reservedEvent.scheduleId}','${reservedEventResponseDiv}');"><img src="static/images/delete.png"></a>
				</span>
				<c:if test ="${reservedEventResponse.displayEvent == 'y' || reservedEventResponse.displayEvent == 'Y'}">
					<br/><span><strong>${reservedEventResponse.displayEventLabel}:</strong> ${reservedEvent.eventName}</span>
				</c:if>
				<c:if test ="${reservedEventResponse.displayLocation == 'y' || reservedEventResponse.displayLocation == 'Y'}">
					<br/><span><strong>${reservedEventResponse.displayLocationLabel}:</strong> ${reservedEvent.locationName}</span>
				</c:if>
		  </div>
		 <c:set var="count" value="${count + 1}" scope="page"/>
		</c:forEach>
	</div>
</c:if>