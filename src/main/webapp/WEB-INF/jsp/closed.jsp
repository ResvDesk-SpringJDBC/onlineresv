<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Online Reservation system</title>
<link href="<%=request.getContextPath() %>/static/${clientInfo.cssFileName}" rel="stylesheet" type="text/css">
</head>

<body onunload="">
<div id="wrapper"> 
  <!-- Header starts here -->
  <header id="branding">
    <h1 class="logo"><img src="${clientInfo.logoFileName}" height="90"></h1>
    <div class="contactDetails">
      <%-- <h2>${clientInfo.loginCallHeader} ${clientInfo.contactPhone}</h2> --%>
    </div>
  </header>
  <!-- Header ends here --> 
  <!-- Page section starts -->
  <div id="page">
    <!-- Page Header starts -->
    <div id="PageHeader">
       <h1 >${clientInfo.closedPageHeaderTextLegend}</h1>
	  
      <div class=""></div>
    </div>
    <!-- Page Header ends -->
    
    <!-- Page Container starts -->
    <div id="PageContainer">
      <!-- Page Content starts -->
      <div id="PageContentFull">
<!--        <h1 class="required">Appointment booking is closed</h1>       -->
        <div class="content">
			${clientInfo.closedLandingPageTextLegend}
        </div>	
      </div>
      <!-- Page Content ends -->      
    </div>
    <!-- Page Container ends -->
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
  </div>
  <!-- Page section ends --> 
</div>
</body>
</html>
