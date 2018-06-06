<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<%-- Confirmation info starts --%>	

		
	   ${confirmationPageContent.confirmationPageRightSideContent}

        <%-- Required Documents starts --%>
		
		<c:if test ="${listOfThingsToBring.deplayText != '' &&  listOfThingsToBring.deplayText != null}">
			<div id="currentReservation">
			  <div class="infoHeader"><h2>${listOfThingsToBring.rightSideDisplayHeader}</h2></div>
			  ${listOfThingsToBring.deplayText}
			</div>
		</c:if>
        <%-- Required Documents starts --%>	
        
        <%-- Contact details starts--%>	
		<c:if test ="${confPageContactDetails.responseStatus &&  !confPageContactDetails.errorStatus}">	 
			<div id="contactDetails">
			<div class="contactHeader"><h2>  ${confPageContactDetails.rightSideContactDetailsHeader} </h2></div>
			  <p>
				<%--
					${confPageContactDetails.address}${(confPageContactDetails.address != "" && confPageContactDetails.address != null) ? "," :""}	 
					${confPageContactDetails.address2}${(confPageContactDetails.address2 != "" && confPageContactDetails.address2 != null) ? "," :""}	 <br />

					${confPageContactDetails.city}${(confPageContactDetails.city != "" && confPageContactDetails.city != null) ? "," :""}	 		
					${confPageContactDetails.state}${(confPageContactDetails.state != "" && confPageContactDetails.state != null) ? "," :""}	 
					${confPageContactDetails.country}${(confPageContactDetails.country != "" && confPageContactDetails.country != null) ? "," :""}	 
					${confPageContactDetails.zip}
					<c:if test ="${confPageContactDetails.email != '' &&  confPageContactDetails.email != ' ' && confPageContactDetails.email != null}">
						<br />${confPageContactDetails.emailLabel}: ${confPageContactDetails.email}</a>
					</c:if>
					<c:if test ="${confPageContactDetails.website != '' &&  confPageContactDetails.website != ' ' && confPageContactDetails.website != null}">
						<br />${confPageContactDetails.websiteLabel}: <a href="http://${confPageContactDetails.website}" target="_blank">${confPageContactDetails.website}</a>
					</c:if>
				--%>
				<c:if test ="${confPageContactDetails.contactDetails!=null &&  confPageContactDetails.contactDetails!=''}">	 
					${confPageContactDetails.contactDetails}
				</c:if>				
			 </p>
			</div>
		</c:if>

		<div id="resv_details_reservedEventResponseDiv">
			<%@ include file="reserved_Event_Response.jsp"%>
		</div>
	
        <%-- Contact details ends--%>	  
		<c:if test ="${confPageContactDetails.locationGoogleMap!=null &&  confPageContactDetails.locationGoogleMap!=''}">	 
			<iframe width="328" height="200" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="${confPageContactDetails.locationGoogleMap}"></iframe>
		</c:if>
      <%-- Confirmation info ends --%>	