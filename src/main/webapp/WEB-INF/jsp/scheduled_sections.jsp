<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<input type="hidden" id="eventHeader"  value="${eventHistoryResponse.rightSideEventHeader}" >

<!-- Scheduled Course / Seminar pop up starts -->	 
<table width="600" border="0" cellspacing="0" cellpadding="0" class="table_content">
  <tr>
	<th width="32%">${eventHistoryResponse.displayEventLabel}</th>
	<th width="32%">${eventHistoryResponse.displayLocationLabel}</td>
	<th width="33%">${eventHistoryResponse.dateLabel} & ${eventHistoryResponse.timeLabel}</th>
  </tr>
  <c:forEach var="eventHisory" items="${eventHistoryResponse.eventHisory}">
	  <tr>
		<td>${eventHisory.eventName}</td>
		<td>${eventHisory.locationName}</td>
		<td>${eventHisory.date}  ${eventHisory.time}</td>
		<%--
		<td>
			<fmt:parseDate value="${eventHisory.date}" pattern="${REST_WS_DATE_FORMAT}" var="parsedDate"/>
			<fmt:formatDate value="${parsedDate}" var="formatedDate" pattern="${FRONT_END_DATE_FORMAT}"/>
			<c:out value="${formatedDate}" />
			<c:out value=" " />
			<fmt:parseDate value="${eventHisory.time}" pattern="${REST_WS_TIME_FORMAT}" var="parsedTime"/>
			<fmt:formatDate value="${parsedTime}" var="formatedTime" pattern="${FRONT_END_TIME_FORMAT}"/>
			<c:out value="${formatedTime}" />
		</td>
		--%>
		 
	  </tr>
  </c:forEach>
</table>		 