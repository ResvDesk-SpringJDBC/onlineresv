<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test= "${!errorStatus}">	
	<input type="hidden" id="resvFirstAvailableDate"  value="${resvFirstAvailableDate}" >

	<c:forEach var="eventDate"  items="${eventDatesResponse.eventDateMap}"> 
		<input type="hidden" id="${eventDate.value.date}" value="${eventDate.value.status}"/>
	</c:forEach>

	<c:if  test="${responseMessage!=null && responseMessage!=''}">
		<script type="text/javascript">	
			$("#date").val("");
			var fieldIDErrorMsg = "${responseMessage}";
			$("#date_error_DivId").html("");
			$("#date_error_DivId").show();
			$("#date_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
		</script>
	</c:if >
	<c:if  test="${responseMessage==null || responseMessage==''}">
		<script type="text/javascript">	
			$("#date").val("");
			$("#date_error_DivId").html("");
			$("#date_error_DivId").hide();
		</script>
	</c:if >
</c:if>

<c:if test= "${errorStatus}">
	<script type="text/javascript">		
		//$("#date").val($("#selectDateLabel_asc_val").val());
		$("#date").prop("disabled", true);
		var fieldIDErrorMsg = "${errorMessage}";
		$("#date_error_DivId").html("");
		$("#date_error_DivId").show();
		$("#date_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
	</script>
</c:if>