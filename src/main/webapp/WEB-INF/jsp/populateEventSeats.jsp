<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">	
	var contextPath="<%=request.getContextPath() %>";
	try{		
		$("#display_seat_loadded").val("true");
	}catch(e){
		//alert("Error ::::::::::  "+e);
	}	
</script>

<c:if test= "${eventSeatsResponse.seats!=null}">	 
	<c:if test ="${display_nextDropdown == 'y' || display_nextDropdown == 'Y'}">
		<label for="Seat">${seatLabel}</label>

		<c:if test= "${!errorStatus}">
				<c:if test= "${fn:length(eventSeatsResponse.seats) gt  1}">	 
					<select id="display_seat" name="display_seat" onchange="validateSeatSelection();">
						<option value="-1">${selectSeatLabel}</option>
						<c:forEach var="seat"  items="${eventSeatsResponse.seats}">
							<option value="${seat.seatId}"  ${seat.seatNumber==selectDefaultId ? 'selected' : '' }>${seat.seatNumber}</option>
						</c:forEach>
					</select>
				</c:if>

				<c:if test= "${fn:length(eventSeatsResponse.seats) eq  1}">
					<script type="text/javascript">
						//populateNextFieldData('display_seat');
						validateSeatSelection();
					</script>
					<label class="label_big" for="label"> ${eventSeatsResponse.seats[0].seatNumber}</label>
					<input type="hidden" name="display_seat" id="display_seat" value="${eventSeatsResponse.seats[0].seatId}"/>
				</c:if>

				<c:if test= "${fn:length(eventSeatsResponse.seats) lt  1}">
					<select id="display_seat" name="display_seat" onchange="validateSeatSelection();">
						<option value="-1">${selectSeatLabel}</option>
					</select>
				</c:if>
			</c:if>

			<c:if test= "${errorStatus}">
				<script type="text/javascript">
					var fieldIDErrorMsg = "${errorMessage}";
					$("#display_seat_error_DivId").html("");
					$("#display_seat_error_DivId").show();
					$("#display_seat_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
				</script>
			</c:if>

	</c:if>

	<c:if test ="${display_nextDropdown == 'n' || display_nextDropdown == 'N'}">	
		<input type="hidden" name="display_seat" id="display_seat" value="${eventSeatsResponse.seats[0].seatId}"/>
		<script type="text/javascript">
			validateSeatSelection();
		</script>
	</c:if>

</c:if>

<c:if test= "${eventSeatsResponse.seats==null}">	
	<script type="text/javascript">
		var fieldIDErrorMsg = "${errorMessage}";
		$("#display_seat_error_DivId").html("");
		$("#display_seat_error_DivId").show();
		$("#display_seat_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
	</script>
</c:if>