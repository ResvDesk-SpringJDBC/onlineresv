$(document).ready(function () {		
		$('#resVerificationNextBtn').click(function(){
			applyEnableDissableButtonEffects(false,"resVerificationNextBtn");
			applyEnableDissableButtonEffects(false,"resVerificationBackBtn");

			var login_first=$("#login_first").val();
			if(login_first=='Y' || login_first=='y'){
				var eventId=$("#display_event").val();
				var locationId=$("#display_location").val();
				var requestData = "eventId="+eventId+"&locationId="+locationId;
				requestData = requestData.replace(/ /g, '%20');
				$("#fourthMsg").load("get_confirmation_right_section.html",requestData,function(data){	
					var scheduleId=$("#resv_scheduleId").val();
					var comment="";
					var requestData = "scheduleId="+scheduleId+"&comment="+comment;
					requestData = requestData.replace(/ /g, '%20');
					$("#cont4").load("get_confirmation_left_section.html",requestData,function(data){					
						applyResDetailsNextBtnEffects();
						$("#resv_scheduleId").val("-1");
					});				
				});
			}else{
				if($("#cont3").html()==null || $("#cont3").html().trim()==""){
					$("#cont3").load("get_login_left_section.html",function(data){
						$("#thirdMsg").load( "get_login_right_section.html",function(data){	
							applyResDetailsNextBtnEffects();
							applyEnableDissableButtonEffects(true,"loginBackBtn");
							applyEnableDissableButtonEffects(true,"loginNextBtn");
						});				
					});
				}else{
						applyResDetailsNextBtnEffects();
						applyEnableDissableButtonEffects(true,"loginBackBtn");
						applyEnableDissableButtonEffects(true,"loginNextBtn");
				}
			}
		});

		$('#resVerificationBackBtn').click(function(){
			var url = "releaseHoldEventTime.html?scheduleId="+$("#resv_scheduleId").val();
		
			$.get(url, function(data) {
				data = JSON.parse(data);
				if(data.response=="SUCCESS"){
					applyEnableDissableButtonEffects(true,"resDetailsNextBtn");
					applyEnableDissableButtonEffects(true,"resDetailsBackBtn");

					$("#resv_scheduleId").val("-1");
					applyResDetailsBackBtnEffects();
					var login_first=$("#login_first").val();						
					if(login_first=='Y' || login_first=='y'){
						if($("#cont2").html()==""){
							$("#cont2").load("get_reservation_left_section.html",function(data){	
								$("#secondMsg").load("get_reservation_right_section.html",function(data){	
									applyResDetailsBackBtnEffects();
								});
							});
						}else{
							url = "get_reserved_Event_Response.html?reservedEventResponseDiv=resv_details_reservedEventResponseDiv";
							url = url.replace(/ /g, '%20');
							$("#resv_details_reservedEventResponseDiv").load(url,function(data){
							});
							applyResDetailsBackBtnEffects();
						}
					}else{
						if($("#cont1").html().trim()==""){
							$("#cont1").load("get_reservation_left_section.html",function(data){
								$("#firstMsg").load("get_reservation_right_section.html",function(data){	
									applyResDetailsBackBtnEffects();
								});				
							});
						}
					}					
				}else{
					$("#resv_scheduleId").val("-1");
					applyResDetailsBackBtnEffects();
				}
			});			
		});

		function applyResDetailsBackBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftPrevDiv = ""; // represents prev  div Id
			 var rightPrevDiv = ""; // represents prev  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					leftCurrentDiv = "cont3";
			        leftPrevDiv = "cont2";
					rightPrevDiv = "secondMsg"; 
			  }else{
				  leftCurrentDiv = "cont2";
			      leftPrevDiv = "cont1";
				  rightPrevDiv = "firstMsg"; 
			  }
			  applyBackBtnClickedEffects(leftCurrentDiv,leftPrevDiv,rightPrevDiv);
		}

		function applyResDetailsNextBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftNextDiv = ""; // represents next  div Id
			 var rightNextDiv = ""; // represents next  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					leftCurrentDiv = "cont3";
			        leftNextDiv = "cont4";
					rightNextDiv = "fourthMsg"; 
			  }else{
				  leftCurrentDiv = "cont2";
			      leftNextDiv = "cont3";
				  rightNextDiv = "thirdMsg"; 
			  }
			  applyNextBtnClickedEffects(leftCurrentDiv,leftNextDiv,rightNextDiv);
		}
});