<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<title>Online Reservation system</title>

<link href="<%=request.getContextPath() %>/static/css/jquery-ui.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/static/${clientInfo.cssFileName}?version=1.0" rel="stylesheet" >
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<script src="<%=request.getContextPath() %>/static/js/jquery/jquery-1.11.1.min.js"></script> 
<script src="<%=request.getContextPath() %>/static/js/jquery/jquery-ui.js"></script> 

<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/onlineres.js?version=1.3"></script>

<script >
 $(document).ready(function () {		
	  $( "#logoutBtn" ).hide();		 
	  $("#pageHeaderDivId").css("border-bottom","0px solid #e5e5e5");
  });
</script>

</head>

<body>

<!-- Header starts -->
<header id="branding">
  <div class="header_container">
    <div class="logo"><img src="${clientInfo.logoFileName}"   height="90"></div>
   <%--  <div class="header_baseline">${clientInfo.clientName}</div> --%>
  </div>
</header>
<!-- Header ends --> 

<!-- Page starts -->
<div id="page">
  <div class="page_container">
   
	<!-- header starts -->
    <div class="page_header" id="pageHeaderDivId">
      <span  id="customerWelcomeDivId"><!-- Customer Welcome details goes here--> </span >
      <input type="button"  id="logoutBtn" value="${clientInfo.logoutBtn}" class="float_right logout" onclick="onLogoutClick();">

	  	<c:if test ="${fn:length(clientInfo.languageList) gt 1}">
		   <div id="languageDetailsHeader"   class="float_right" >	
				<c:forEach var="language" items="${clientInfo.languageList}">
						<a href="javascript:void(0)" onclick="updateLanguageSelection('${language.langCode}');" >
							<%--
								<c:if test ="${language.langCode== clientInfo.langCode}">	
									<b>${language.linkDisplay}</b>
								</c:if>
							--%>
							<c:if test ="${language.langCode!= sessionData.langCode}">	
								${language.linkDisplay}
							</c:if>
						</a>
				  </c:forEach>
		  </div>
		  <br/>
		</c:if>

   </div>
    <!-- header ends -->
    
    <!-- Appointment Confirmation message starts -->
    <!--<div id="confirmation">Congratulations! You have made a Course / Seminar reservation for the date, time, and location listed below.</div>-->
    <!-- Appointment Confirmation message ends -->
    
	<input type="hidden" id="login_first" value="Y"/>

	<div id="errorMessage" style="color:red"></div>
	
    <!-- Accordion starts -->
    <div id="leftCont">
      <div id="accordion">
      
        <!-- Login section starts -->
        <h3 class="ui-header ui-state-active"><span  class="ui-accordion-header-icon ui-icon-triangle-1-s"></span>${clientInfo.leftSideLoginHeader}</h3>
		<div id="cont1" class="ui-content">				
				<!-- Login Left JSP PAGE -->
				<%@ include file="login_left_section.jsp"%>
        </div>
		 
        <!-- Login section ends -->
        
        <!-- Reservation details starts -->
        <h3 class="ui-header ui-state-default"> <span class="ui-accordion-header-icon ui-icon-triangle-1-e"></span>${clientInfo.leftSideResvDetailsHeader}</h3>
        <div id="cont2" class="ui-content ui-content-hide">
			
        </div>
        <!-- Reservation details ends -->
        
        <!-- Reservation Verification starts -->
        <h3 class="ui-header ui-state-default"> <span class="ui-accordion-header-icon ui-icon-triangle-1-e"></span>${clientInfo.leftSideResvVerifyHeader}</h3>
        <div id="cont3" class="ui-content ui-content-hide">
			
        </div>
        <!-- Reservation Verification ends -->
        
        <!-- Confirmation starts -->
        <h3 class="ui-header ui-state-default"> <span class="ui-accordion-header-icon ui-icon-triangle-1-e"></span>${clientInfo.leftSideConfirmHeader}</h3>
        <div id="cont4" class="ui-content ui-content-hide">
          
         
        </div>
        <!-- Confirmation ends -->
        
      </div>
    </div>
    <!-- Accordion ends -->
    
    <!-- Info Message starts -->
    <div id="righCont">
      <!-- Login Info starts -->
      <div id="firstMsg" class="active">
			<!-- Login Left JSP PAGE -->
			<%@ include file="login_right_section.jsp"%>			
      </div>
      <!-- Login Info ends -->
      
      <!-- Reservation info starts -->
      <div id="secondMsg">
          
      </div>
      <!-- Reservation info ends -->
      
      <!-- Reservation Verification info starts -->
      <div id="thirdMsg">
  		 
      </div>
      <!-- Reservation Verification info ends -->
      
      <!-- Confirmation info starts -->
      <div id="fourthMsg">
            
      </div>
      <!-- Confirmation info ends -->
      
    </div>
    <!-- Info Message ends -->
    
    <div class="clearAll"></div>
  </div>
</div>
<!-- Page ends -->

<!-- Footer starts -->
<footer id="footer">
	<div class="footerContent">
		<p>${clientInfo.footerContent}
		</p>
	</div>
	<div class="footerLinks">
           ${clientInfo.footerLinks} 				
	
	</div>
</footer>
<!-- Footer ends -->

<!-- Scheduled Sections JSP PAGE -->
<div id="thedialog"> </div>
</body>
</html>
