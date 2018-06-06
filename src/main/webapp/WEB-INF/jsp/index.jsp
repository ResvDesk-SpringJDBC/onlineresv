<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<title>FedEx Handler Job Reservation</title>
<link href="<%=request.getContextPath() %>/static/css/landing-page.css" rel="stylesheet" type="text/css">

</head>

<body>

<!-- Header starts -->
<header id="branding">
  <div class="header_container">
    <div class="logo"><img src="${clientInfo.logoFileName}"  height="90"></div>
    <!--<div class="header_baseline">Some text goes here</div>-->
  </div>
</header>
<!-- Header ends --> 

<!-- Page starts -->
<div id="page">
  <div class="page_container">
    <!-- Page header starts -->
      <div class="page_header">
        <h1>Welcome </h1>
        <div class="links"><a href="javascript:bookmarksite('http://www.FedExHandlerJobs.com')" class="bookmark" title="Bookmark Us"></a></div>
      </div>
      <p>At our locations around the U.S., FedEx Express handlers move millions of packages each day. Our handlers are the driving force behind our business, making absolutely sure packages are loaded safely and securely into FedEx Express planes and trucks.</p>
      <h2 class="align_center">We invite YOU to join this elite team!</h2>
    <!-- Page header ends -->
    <!-- Box container starts -->
    <div class="box_container">
      <div class="box">
        <h2 class="align_center">Memphis, TN Hub</h2>
        <p>Memphis Hub is currently accepting applicants by appointment. To apply for Memphis Hub, please click the below link to reserve your appointment.</p>
        <div class="align_center"><a href="login.html?client_code=${clientInfo.clientCode}" class="button_link">Apply Now</a></div>
      </div>
      <div class="box">
        <h2 class="align_center">Newark, NJ Hub</h2>
        <p>Newark Hub is currently accepting applicants by appointment. To apply for Memphis Hub, please click the below link to reserve your appointment.</p>
        <div class="align_center"><a href="login.html?client_code=${clientInfo.clientCode}" class="button_link">Apply Now</a></div>
      </div>
      <div class="box box_last">
        <h2 class="align_center">Indianapolis, IN Hub</h2>
        <p>Indianapolis Hub is currently accepting applicants by appointment. To apply for Memphis Hub, please click the below link to reserve your appointment.</p>
        <div class="align_center"><a href="login.html?client_code=${clientInfo.clientCode}" class="button_link">Apply Now</a></div>
      </div><div class="clearAll"></div>
    </div>
    <!-- Box container ends -->
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
</body>
</html>
