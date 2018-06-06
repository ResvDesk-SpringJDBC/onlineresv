<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<label for="cblTime">${timeLabel}</label>
<c:if test= "${!errorStatus}">
		<select id="display_time" name="display_time" onchange="loadSeatDetailsOnSelectionOfTime('display_time');">
			<option value="-1">${selectTimeLabel}</option>
			<c:forEach var="availableTime"  items="${eventTimesResponse.availableTimes}">
				<option value="${availableTime.key}">${availableTime.value}</option>
			</c:forEach>
		</select>
</c:if>

<c:if test= "${errorStatus}">
	<script type="text/javascript">	
		var fieldIDErrorMsg = "${errorMessage}";
		$("#display_time_error_DivId").html("");
		$("#display_time_error_DivId").show();
		$("#display_time_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
	</script>
</c:if>