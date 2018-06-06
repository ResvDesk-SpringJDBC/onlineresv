$(document).ready(function () {	
		
		$("#isAllowDuplicateResvMsgDiv").hide();

		//Auto Tab
		$("input[class='phoneField']").bind('keyup', function() {
			 var limit = parseInt($(this).attr('maxlength'));  
			 var text = $(this).val();  			
			 var chars = text.length; 
			 //check if there are more characters then allowed
			 if(chars >=limit){  							
				var nextIndex = $(this).next().index();				
				$("#"+$(this).next().attr("id")).focus();
			 }
		});	

		$('#loginNextBtn').click(function(){
			applyEnableDissableButtonEffects(false,"loginNextBtn");			
			var login_first=$("#login_first").val();						
			if(login_first=='N' || login_first=='n'){
					applyEnableDissableButtonEffects(false,"loginBackBtn");
			}

			var str = $("#loginForm").serialize();	
			var url = "authenticateCustomer.html";		

			$.post(url, str,function(data) {
				data = JSON.parse(data);	

				var loginDataDiv = "cont1";
				if(login_first=='Y' || login_first=='y'){
					loginDataDiv = "cont1";
				}else{
					loginDataDiv = "cont3";
				}

				if(data.formErrors==false){		
					//alert("data.response  :::::::: "+(data.response));
					if(data.response=="SUCCESS"){	
						$("#pageHeaderDivId").css("border-bottom","1px solid #e5e5e5");
						$( "#languageDetailsHeader" ).hide();
						$( "#viewResvDtlsBtn" ).hide();
						$( "#logoutBtn" ).show();
						$('#errorMessage').html(data.message);

						if(data.firstName!=null && data.firstName!=""){
							$('#customerWelcomeDivId').html("<h1>"+data.welcomeHeader+"<span id='FirstLastName'>"+data.firstName+" "+data.lastName+"</span> </h1>");
						}
						
						//str = str+"&loginNextBtn="+$("#loginNextBtnVal").val()+"&loginBackBtn="+$("#loginBackBtnVal").val();						
						 
						$("#"+loginDataDiv).load("get_invalid_data_login_left_page.html",str,function(data){	
							
							applyEnableDissableButtonEffects(false,"loginNextBtn");
							if(login_first=='N' || login_first=='n'){
									applyEnableDissableButtonEffects(false,"loginBackBtn");
							}

							if(login_first=='Y' || login_first=='y'){ 
								if($("#cont2").html().trim()==""){
									$("#cont2").load("get_reservation_left_section.html",function(data){	
										$("#secondMsg").load("get_reservation_right_section.html",function(data){	
											applyLoginPageNextBtnEffects();
										});
									});
								}else{
									url = "get_reserved_Event_Response.html?reservedEventResponseDiv=resv_details_reservedEventResponseDiv";
									url = url.replace(/ /g, '%20');
									$("#resv_details_reservedEventResponseDiv").load(url,function(data){
									});
									applyLoginPageNextBtnEffects();
								}
							}else{
								var eventId=$("#display_event").val();
								var url = "isAllowDuplicateResv.html?eventId="+eventId;
								$.get(url, function(data) {
									data = JSON.parse(data);	
									//alert("Response ::::  "+(data.response));
									//alert("Message ::::  "+(data.message));
									$("#isAllowDuplicateResvMsgDiv").html("");
									if(data.response=="SUCCESS"){										
										var locationId=$("#display_location").val();
										var requestData = "eventId="+eventId+"&locationId="+locationId;
										requestData = requestData.replace(/ /g, '%20');
										$("#fourthMsg").load("get_confirmation_right_section.html",requestData,function(data){	
											var scheduleId=$("#resv_scheduleId").val();
											var comment="";
											var requestData = "scheduleId="+scheduleId+"&comment="+comment;
											requestData = requestData.replace(/ /g, '%20');
											$("#cont4").load("get_confirmation_left_section.html",requestData,function(data){					
												applyLoginPageNextBtnEffects();
												$("#resv_scheduleId").val("-1");
											});				
										});
									}else{
										applyEnableDissableButtonEffects(true,"loginNextBtn");
										applyEnableDissableButtonEffects(true,"loginBackBtn");
										$("#isAllowDuplicateResvMsgDiv").show();
										$("#isAllowDuplicateResvMsgDiv").html("<span class='ui-icon ui-icon-alert float_left'></span>"+(data.message));
									}
								});
							}
						});
					}else if(data.response=="AUTH_FAILED"){
						applyEnableDissableButtonEffects(true,"loginNextBtn");
						$( "#logoutBtn" ).hide();	
						$('#errorMessage').html(data.message);
						return false;
					}else if(data.response=="FAILURE"){
						applyEnableDissableButtonEffects(true,"loginNextBtn");
						$( "#logoutBtn" ).hide();	
						$('#errorMessage').html(data.message);
						return false;
					}else{
						applyEnableDissableButtonEffects(true,"loginNextBtn");
						$('#errorMessage').html(data.message);
					}
				}else{					
					applyEnableDissableButtonEffects(true,"loginNextBtn");
					if(data.response=="INVALID_LOGIN_DATA"){
						$( "#logoutBtn" ).hide();	
						//$('#errorMessage').html(data.message);
						//str = str+"&loginNextBtn="+$("#loginNextBtnVal").val()+"&loginBackBtn="+$("#loginBackBtnVal").val();
						$("#"+loginDataDiv).load("get_invalid_data_login_left_page.html",str,function(data){	
						});
					}
				}
		  });
		});

		$('#loginBackBtn').click(function(){			
			applyLoginPageBackBtnEffects();
			applyEnableDissableButtonEffects(true,"resVerificationNextBtn");
			applyEnableDissableButtonEffects(true,"resVerificationBackBtn");

			applyEnableDissableButtonEffects(false,"loginBackBtn");
			applyEnableDissableButtonEffects(false,"loginNextBtn");
			
			/*
			$("#resv_scheduleId").val(scheduleId);
				var login_first=$("#login_first").val();
				url = "get_res_verification_left_section.html";					
				var requestData = "scheduleId="+scheduleId;
				requestData = requestData.replace(/ /g, '%20');

				if(login_first=='Y' || login_first=='y'){
					$("#cont3").load(url,requestData,function(data){
						url = "get_res_verification_right_section.html";
						$("#thirdMsg").load(url,function(data){	
							applyResDetailsNextBtnEffects();
						});				
					});
				}else{						
					var url = "get_res_verification_left_section.html";
					$("#cont2").load(url,requestData,function(data){
						url = "get_res_verification_right_section.html";
						$("#secondMsg").load(url,function(data){		
							applyResDetailsNextBtnEffects();
						});				
					});
				}
				*/
		});

		function applyLoginPageNextBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftNextDiv = ""; // represents next  div Id
			 var rightNextDiv = ""; // represents next  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					leftCurrentDiv = "cont1";
			        leftNextDiv = "cont2";
					rightNextDiv = "secondMsg"; 
			  }else{
				  leftCurrentDiv = "cont3";
			      leftNextDiv = "cont4";
				  rightNextDiv = "fourthMsg"; 
			  }
			  applyNextBtnClickedEffects(leftCurrentDiv,leftNextDiv,rightNextDiv);
		}

		function applyLoginPageBackBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftPrevDiv = ""; // represents prev  div Id
			 var rightPrevDiv = ""; // represents prev  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					//leftCurrentDiv = "cont3";
			        //leftPrevDiv = "cont2";
					//rightPrevDiv = "secondMsg"; 
			  }else{
				  leftCurrentDiv = "cont3";
			      leftPrevDiv = "cont2";
				  rightPrevDiv = "secondMsg"; 
			  }
			  applyBackBtnClickedEffects(leftCurrentDiv,leftPrevDiv,rightPrevDiv);
		}
});