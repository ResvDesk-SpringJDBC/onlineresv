<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
	
<script>
   $(document).ready(function () {			
	   $('.thelink').click(function(){
		   $( "div#thedialog" ).load( "getEventHistory.html", function() {
				  $('div#thedialog').dialog('open'); 
				  $( "#thedialog" ).dialog( "option", "draggable", false );
				  $("#thedialog").dialog(
					   { title: $("#eventHeader").val() }
				  );
			});
		});				
	});
</script>

<%-- Reservation Info starts --%>

	${resvDetailsRightSideContent.resvDetailsRightSideContent}
	
	<%-- Displaying already booked reservations  --%>
	<c:if test ="${sessionData.loginFirst == 'y' || sessionData.loginFirst == 'Y'}">			
		<div id="resv_details_reservedEventResponseDiv">
			<%@ include file="reserved_Event_Response.jsp"%>
		</div>
	</c:if>
<%-- Reservation Info ends  --%>