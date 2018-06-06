<!doctype html>
<html lang="us">
<head>
<meta charset="utf-8">
<title>FedEx Handler Job Reservation</title>
<link href="<%=request.getContextPath() %>/static/${clientInfo.cssFileName}" rel="stylesheet" type="text/css">
<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

</head>

<script>
	function onSubmit() {
			
		if(!document.getElementById("userEntryYes").checked && !document.getElementById("userEntryNo").checked) {
			document.getElementById("userEntryErrorMessage").innerHTML="Please answer age eligibility question.";
			return false;
		}
		
		if(document.getElementById("userEntryYes").checked) {
			document.getElementById("userEntryErrorMessage").innerHTML="";
		}
		
		if(document.getElementById("userEntryNo").checked) {
			document.getElementById("userEntryErrorMessage").innerHTML="Sorry. You must be above 18 years to apply. ";
			return false;
		}
		
		if(!document.getElementById("USEntryYes").checked && !document.getElementById("USEntryNo").checked) {
			document.getElementById("USEntryErrorMessage").innerHTML="Please answer whether you have legal work status in US.";
			return false;
		}
		
		if(document.getElementById("USEntryNo").checked) {
			document.getElementById("USEntryErrorMessage").innerHTML="Sorry. You need to have a legal work status in US to apply.";
			return false;
		}
	}
</script>

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
	${clientInfo.landingPageText}
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
