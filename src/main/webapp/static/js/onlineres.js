$(document).ready(function () {				
	$('div#thedialog').dialog({ autoOpen: false, width:'auto', modal:true, height: 400, });
	//http://www.tutorialspoint.com/jqueryui/jqueryui_dialog.htm
});

	 function updateLanguageSelection(langCode){
			var url = String( window.location);
			var index = url.indexOf("lang_code");
			if(index!=-1){
				 url =  url.substr(0,url.lastIndexOf("lang_code")-1);
			}
			if(url.indexOf("?")!=-1){
				url = url +"&";
			}else{
				url = url +"?";
			}
			url = url +"lang_code="+langCode;
			window.location = url;
	 }

	   function onLogoutClick(){
		   if($("#resv_scheduleId").val()!="-1"){
			   var url = "releaseHoldEventTime.html?scheduleId="+$("#resv_scheduleId").val();			
				$.get(url, function(data) {
					 window.location="logout.html";
				});
		   }else{
			    window.location="logout.html";
		   }
	   }

	   function onViewExtResvDtlsClick(){
		   if($("#resv_scheduleId").val()!="-1"){
			   var url = "releaseHoldEventTime.html?scheduleId="+$("#resv_scheduleId").val();			
				$.get(url, function(data) {
					 window.location="viewExtResvDtls.html";
				});
		   }else{
			    window.location="viewExtResvDtls.html";
		   }
	   }

	   function applyNextBtnClickedEffects(leftCurrentDiv,leftNextDiv,rightNextDiv){
			  $("#"+leftCurrentDiv).slideUp(300);
			  $("#"+leftNextDiv).slideDown(300);
			  $("#"+leftCurrentDiv).prev().removeClass("ui-state-active").addClass("ui-state-default");
			  $("#"+leftNextDiv).prev().removeClass("ui-state-default").addClass("ui-state-active");
			  $("#"+leftCurrentDiv).prev().find("span").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
			  $("#"+leftNextDiv).prev().find("span").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
			
			  if ( $( "#"+leftCurrentDiv ).next().hasClass( "ui-state-active" ) ) {
					$('#righCont div').removeClass('active');
					$("#"+rightNextDiv).addClass('active');
			  }			 
	   }

	    function  applyBackBtnClickedEffects(leftCurrentDiv,leftPrevDiv,rightPrevDiv){
				$("#"+leftCurrentDiv).slideUp(300);
				$("#"+leftPrevDiv).slideDown(300);
				$("#"+leftCurrentDiv).removeClass("ui-state-active").addClass("ui-state-default");
				$("#"+leftPrevDiv).prev().removeClass("ui-state-default").addClass("ui-state-active");
				$("#"+leftCurrentDiv).prev().find("span").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
				$("#"+leftPrevDiv).prev().find("span").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
				
				if ($("#"+leftCurrentDiv).prev().hasClass( "ui-state-active" ) ) {
					$(".ui-state-active span").addClass("ui-icon-triangle-1-s").removeClass("ui-icon-triangle-1-e");
					$('#righCont div').removeClass('active');
					$("#"+rightPrevDiv).addClass('active');
				}
		}

		 function cancelScheduledReservation(scheduleId,reservedEventResponseDiv){
			var isConfirmed = confirm("Do you want to cancel this reservation?"); 
			 
			 if(isConfirmed){
				  var url = "cancelReservation.html?scheduleId="+scheduleId;
				  url = url.replace(/ /g, '%20');
					
					$.get(url, function(data) {
						data = JSON.parse(data);
						if(data.response=="SUCCESS"){
							//var login_first=$("#login_first").val();
							url = "get_reserved_Event_Response.html?reservedEventResponseDiv="+reservedEventResponseDiv;
							url = url.replace(/ /g, '%20');
							$("#"+reservedEventResponseDiv).load(url,function(data){
							});
						}else{
							alert(data.message);
						}
					});		
			 }
		 }

		 function applyEnableDissableButtonEffects(isEnable,buttonId){	
			try{
				if(isEnable){
						$("#"+buttonId).prop("disabled", false);
						$('#'+buttonId).css('opacity', '1.0');					
				  }else{
						$("#"+buttonId).prop("disabled", true);
						$('#'+buttonId).css('opacity', '0.5');
				  }
			}catch(e){
				//alert("Error : "+e);
			}
			 // alert("isEnable --> "+isEnable);
		}

// ******************************************* NOT Needed we have to remove ****************************  
/*
  function applyNextBtnEffects(thisEle){
		  $(thisEle).parent().slideUp(300);
		  $(thisEle).parent().next().next().slideDown(300);
		  $(thisEle).parent().prev().removeClass("ui-state-active").addClass("ui-state-default");
		  $(thisEle).parent().next().removeClass("ui-state-default").addClass("ui-state-active");
		   $(thisEle).parent().prev().find("span").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
		   $(this).parent().next().find("span").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
		  if ( $( thisEle ).parent().next().hasClass( "ui-state-active" ) ) {
			    var leftElem = $('.ui-state-active').index();
				var childElem = $('#righCont').children();
				$('#righCont div').removeClass('active');
				$(childElem[leftElem/2]).addClass('active');
		  }
   }

    function applyBackBtnEffects(thisEle){
			$(thisEle).parent().slideUp(300);
			$(thisEle).parent().prev().prev().slideDown(300);
			$(thisEle).parent().prev().removeClass("ui-state-active").addClass("ui-state-default");
		    $(thisEle).parent().prev().prev().prev().removeClass("ui-state-default").addClass("ui-state-active");
			$(thisEle).parent().prev().find("span").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
		    $(thisEle).parent().prev().prev().prev().find("span").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
			if ($(thisEle).parent().prev().prev().prev().hasClass( "ui-state-active" ) ) {
				$(".ui-state-active span").addClass("ui-icon-triangle-1-s").removeClass("ui-icon-triangle-1-e");
				var leftElem = $('.ui-state-active').index();
				var childElem = $('#righCont').children();
				$('#righCont div').removeClass('active');
				$(childElem[leftElem/2]).addClass('active');
		}
   }
   */


   /* $(document).ready(function () {		
		//This is no need, after yagnesh fixes for next btn we have to remove
		
		$(function() {
			$( "#accordion" ).accordion({
			  heightStyle: "content"
			});
		});
		 
		$('div#thedialog').dialog({ 
			autoOpen: false, 
			width:'auto', 
			modal:true
		});
		$( "div#thedialog" ).dialog( "option", "draggable", false );
		

		$('#thedialog').dialog({ 
				autoOpen: false, 
				width:'auto', 
				modal:true
			});


$("#thedialog").load("getEventHistory.html").dialog('open');
			$( "#thedialog" ).dialog( "option", "draggable", false );
			

			$.get("http://localhost:9000/onlineresv/login.html?client_code=DEMORESV", function(data){
					$('div#thedialog').html(data);
					$('div#thedialog').dialog('open');
					$( 'div#thedialog' ).dialog( 'option', 'draggable', false );
			   }, 'html');

			 //$('div#thedialog').dialog('open'); 
			
			 alert("Link Clicked");
			 var url = "http://localhost:9000/onlineresv/login.html?client_code=DEMORESV";
			 $('div#thedialog').dialog("destroy");
			$('div#thedialog').dialog({
				dialogClass: 'DynamicDialogStyle',
				modal: true,
				width:'auto', 
				height:'auto', 
				open: function () {
					$(this).load(url);           
				}
			});
			
				$.get("http://www.google.com", function(data){
					$('div#thedialog').html(data);
					$('div#thedialog').dialog('open');
				}, "html");			
				$('div#thedialog').load('http://www.google.com');
				$('div#thedialog').dialog('open'); 			
				$( "div#thedialog").dialog({
					open: function(event, ui) {
						   $('#content').load('http://www.google.com');
					}
				}).dialog("open");
		
  });*/
