<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Online Reservation system</title>
<link href="<%=request.getContextPath() %>/static/css/${clientInfo.cssFileName}" rel="stylesheet" type="text/css">
</head>

<body>
<div id="wrapper"> 
  <!-- Header starts here -->
  <header id="branding">
    <h1 class="logo"></h1>
    <div class="contactDetails">
      <%-- <h2>${clientInfo.loginCallHeader} ${clientInfo.contactPhone}</h2> --%>
    </div>
  </header>
  <!-- Header ends here --> 

  <!-- Page section starts -->
  <div id="page">
    <!-- Page Header starts -->
    <div id="PageHeader">
       <h1 >Session Expired</h1>	  
      <div class=""></div>
    </div>
    <!-- Page Header ends -->
    
    <!-- Page Container starts -->
    <div id="PageContainer">
      <!-- Page Content starts -->
      <div id="PageContentFull">
		<h1 class="required"> </h1>   
        <div class="content">
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
