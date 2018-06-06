$(document).ready(function () {		
		$(function() {
			dissableAllFieldsExceptFirstField();
			hideErrorDivs();

			if($("#loadDatesDataOnLoadingOfEventsData").val()=="YES"){
				//alert("Load Dates Data");
				populateNextFieldData('display_event');
			}
		});

		$('#resDetailsNextBtn').click(function(){
			var validationResponse = validateReservationDetsilsSelectionForm();

			if(validationResponse){
				var selectedDate = $("#date").val();
				var request = $.post("validateSelectedDate.html?date="+selectedDate, function(data) {
					data = JSON.parse(data);
					//alert("Response ::::::: "+data.response);
					//alert("Message ::::::: "+data.message);
					 
					if(data.response=="SUCESSES"){
							applyEnableDissableButtonEffects(false,"resDetailsNextBtn");
							applyEnableDissableButtonEffects(false,"resDetailsBackBtn");

							var companyId = getSelectedCompanyId();
							var procedureId = getSelectedProdedureId();
							var locationId = getSelectedLocationId();
							var departmentId = getSelectedDepartmentId();
							var eventId = getSelectedEventId();		
							var dateStr = $("#date").val();
							var time = $("#display_time").val();
							var seatId = getSelectedSeatId();

							var url = "holdReservation.html?"
										+"companyId="+companyId
										+"&procedureId="+procedureId
										+"&locationId="+locationId
										+"&departmentId="+departmentId
										+"&eventId="+eventId
										+"&date="+dateStr
										+"&timeId="+time
										+"&seatId="+seatId;
							url = url.replace(/ /g, '%20');
						
							$.get(url, function(data) {
								data = JSON.parse(data);
					
								if(data.response=="SUCCESS"){

									$( "#languageDetailsHeader" ).hide();

									var scheduleId=data.scheduleId;
								
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
											if($("#secondMsg").html().trim()==""){
												url = "get_res_verification_right_section.html";
												$("#secondMsg").load(url,function(data){		
													applyResDetailsNextBtnEffects();
												});			
											}else{
												applyResDetailsNextBtnEffects();
											}
										});
									}
								}else{
									if(data.notAvailDateEntered==false){
											alert(data.message);
											applyEnableDissableButtonEffects(true,"resDetailsNextBtn");
											applyEnableDissableButtonEffects(true,"resDetailsBackBtn");
									 }else{
										//alert(data.message);
										window.location = contextPath+"/error.html";
									 }
								}
							});	
					 }else{
						 alert(data.message);
					}
				});
			}
		});

		$('#resDetailsBackBtn').click(function(){
			$("#resv_scheduleId").val("-1");
			//dissableAllFieldsExceptFirstField();
			applyEnableDissableButtonEffects(true,"loginNextBtn");
			applyResDetailsBackBtnEffects();
		});
});


		function applyResDetailsBackBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftPrevDiv = ""; // represents prev  div Id
			 var rightPrevDiv = ""; // represents prev  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					leftCurrentDiv = "cont2";
			        leftPrevDiv = "cont1";
					rightPrevDiv = "firstMsg"; 
			  }else{
			  }
			  applyBackBtnClickedEffects(leftCurrentDiv,leftPrevDiv,rightPrevDiv);
		}

		function applyResDetailsNextBtnEffects(){
			 var leftCurrentDiv = ""; // represents ccurrent div Id
			 var leftNextDiv = ""; // represents next  div Id
			 var rightNextDiv = ""; // represents next  div Id

			  var login_first = $("#login_first").val();
			  if(login_first=='Y'){
					leftCurrentDiv = "cont2";
			        leftNextDiv = "cont3";
					rightNextDiv = "thirdMsg"; 
			  }else{
					leftCurrentDiv = "cont1";
			        leftNextDiv = "cont2";
					rightNextDiv = "secondMsg"; 
			  }
			  applyNextBtnClickedEffects(leftCurrentDiv,leftNextDiv,rightNextDiv);
		}


function dissableAllFieldsExceptFirstField(){
	var isFirstFieldExcluded = false;
	var fieldType = "";

	var displayCompany = $("#display_company_asc_val").val();
	if(displayCompany=='Y' || displayCompany=='y'){
		fieldType = $("#display_company").attr("type");
		//alert(" display_company ::: fieldType ::: "+fieldType);
		if(typeof fieldType === 'undefined'){ //ie, field is a dropdown not a hidden field
			if(isFirstFieldExcluded){
				if($("#display_company_loadded").val()=="false"){
					$("#display_company").val(-1);
					$("#display_company").prop("disabled", true);	 				
				}
			}else{
				isFirstFieldExcluded = true;
				//$("#display_company").val(-1);
			}
		}
	}	

	var displayProcedure = $("#display_procedure_asc_val").val();
	if(displayProcedure=='Y' || displayProcedure=='y'){
		fieldType = $("#display_procedure").attr("type");
		//alert(" display_procedure ::: fieldType ::: "+fieldType);
		if(typeof fieldType === 'undefined'){ //ie, field is a dropdown not a hidden field
			if(isFirstFieldExcluded){
				if($("#display_procedure_loadded").val()=="false"){
					$("#display_procedure").val(-1);
					$("#display_procedure").prop("disabled", true);	 				
				}
			}else{
				isFirstFieldExcluded = true;
				//$("#display_procedure").val(-1);
			}
		}
	}	

	var displayLocation = $("#display_location_asc_val").val();
	if(displayLocation=='Y' || displayLocation=='y'){
		fieldType = $("#display_location").attr("type");
		//alert(" display_location ::: fieldType ::: "+fieldType);
		if(typeof fieldType === 'undefined'){ //ie, field is a dropdown not a hidden field
			if(isFirstFieldExcluded){
				if($("#display_location_loadded").val()=="false"){
					$("#display_location").val(-1);	 
					$("#display_location").prop("disabled", true);	 
				}
			}else{
				isFirstFieldExcluded = true;
				//$("#display_location").val(-1);	 
			}
		}
	}	

	var displayDepartment = $("#display_department_asc_val").val();
	if(displayDepartment=='Y' || displayDepartment=='y'){
		fieldType = $("#display_department").attr("type");
		//alert(" display_department ::: fieldType ::: "+fieldType);
		if(typeof fieldType === 'undefined'){ //ie, field is a dropdown not a hidden field
			if(isFirstFieldExcluded){
				if($("#display_department_loadded").val()=="false"){
					$("#display_department").val(-1);	 
					$("#display_department").prop("disabled", true);	 				
				}
			}else{
				isFirstFieldExcluded = true;
				//$("#display_department").val(-1);	 
			}
		}
	}	

	var displayevent = $("#display_event_asc_val").val();
	if(displayevent=='Y' || displayevent=='y'){
		fieldType = $("#display_event").attr("type");
		//alert(" display_event ::: fieldType ::: "+fieldType);
		if(typeof fieldType === 'undefined'){ //ie, field is a dropdown not a hidden field
			if(isFirstFieldExcluded){
				if($("#display_event_loadded").val()=="false"){
					$("#display_event").val(-1);	 
					$("#display_event").prop("disabled", true);	 				
				}
			}else{
				isFirstFieldExcluded = true;
				//$("#display_event").val(-1);	 
			}
		}
	}	

	if(isFirstFieldExcluded){
		var eventId = getSelectedEventId();
		//alert("eventId------------------> "+eventId);
		if(eventId==""  || eventId=="-1"){
			$("#date").val($('#selectDateLabel_asc_val').val());
			$("#date").prop("disabled", true);
		}

		$("#display_time").prop("disabled", true);
		$("#display_time").val(-1);
		
		//$("#resDetailsNextBtn").prop("disabled", true);
		//$('#resDetailsNextBtn').css('opacity', '0.5');
	}

	var displayseat = $("#display_seat_asc_val").val();
	if(displayseat=='Y' || displayseat=='y'){
		if(isFirstFieldExcluded){
			if($("#display_seat_loadded").val()=="false"){
				$("#display_seat").val(-1);	 
				$("#display_seat").prop("disabled", true);	 				
			}
		}else{
			//$("#display_seat").val(-1);	 
		}
	}	
}

function getSelectedCompanyId() {
	var companyId = -1;
	try {
		companyId = $("#display_company").val();
	} catch(e){
		//alert("Error : getSelectedCompanyId  :: "+e);
	}
	if(typeof companyId == "undefined"){
		companyId = -1;
	}
	return companyId;
}

function getSelectedProdedureId() {
	var procedureId = -1;
	try{
		procedureId = $("#display_procedure").val();	
	} catch(e){
		//alert("Error : getSelectedProdedureId  :: "+e);
	}
	if(typeof procedureId == "undefined"){
		procedureId = -1;
	}
	return procedureId;
}

function getSelectedLocationId() {
	var locationId = -1;
	try{
		locationId = $("#display_location").val();
	} catch(e){
		//alert("Error : getSelectedLocationId  :: "+e);
	}
	if(typeof locationId == "undefined"){
		locationId = -1;
	}
	return locationId;
}

function getSelectedDepartmentId() {
	var departmentId = -1;
	try{
		departmentId = $("#display_department").val();
	} catch(e){
		//alert("Error : getSelectedDepartmentId  :: "+e);
	}
	if(typeof departmentId == "undefined"){
		departmentId = -1;
	}
	return departmentId;
}

function getSelectedEventId() {
	var eventId = -1;
	try{
		eventId = $("#display_event").val();
	} catch(e){
		//alert("Error : getSelectedEventId  :: "+e);
	}
	if(typeof eventId == "undefined"){
		eventId = -1;
	}
	return eventId;
}

function getSelectedSeatId() {
	var seatId = -1;
	try{
		seatId = $("#display_seat").val();
	} catch(e){
		//alert("Error : getSelectedSeatId  :: "+e);
	}
	if(typeof seatId == "undefined"){
		seatId = -1;
	}
	return seatId;
}

//Hiding previously displayed error messages when Upper field value is changed.
function hideAllNextFieldsErrorMessages(selectedDropdownID){
	var selBoxIDs     =new Array ('display_company','display_procedure','display_location','display_department','display_event','date','display_time','display_seat');
	var netSelCtBoxId = selBoxIDs.indexOf(selectedDropdownID);
	var nextSelectDropdownID = selBoxIDs[netSelCtBoxId+1];
	
	for(var intCnt  = netSelCtBoxId+1   ; intCnt < selBoxIDs.length ; intCnt++) {
		try{
			var field_id = selBoxIDs[intCnt] ;
			$('#'+field_id+"_error_DivId").html("");
			$('#'+field_id+"_error_DivId").hide();
		}catch(ex){
			//alert("Error ::: "+ex);
		}
	}	
}

function populateNextFieldData(selectedDropdownID){
	try {		

		var selectedDropdownIDVal = $("#"+selectedDropdownID).val();
		if(selectedDropdownIDVal!=-1){ // && (typeof selectedDropdownIDVal == "undefined")
			$('#'+selectedDropdownID+"_error_DivId").html("");
			$('#'+selectedDropdownID+"_error_DivId").hide();
			dissableNextAllFields(selectedDropdownID);
			hideAllNextFieldsErrorMessages(selectedDropdownID);

			var selBoxIDs     =new Array ('display_company','display_procedure','display_location','display_department','display_event');
			var netSelCtBoxId = selBoxIDs.indexOf(selectedDropdownID);
			var nextSelectDropdownID = selBoxIDs[netSelCtBoxId+1];
			
			if(nextSelectDropdownID!="" && (typeof nextSelectDropdownID != "undefined")){
				var divId = nextSelectDropdownID+"_DivId";			
				var url = prepareURLToPopulateDropdownData(nextSelectDropdownID);
				$("#"+divId).load(url,function(data){					
				});
				try{
					$("#"+nextSelectDropdownID).prop("disabled", false);
					$('#'+nextSelectDropdownID).css('opacity', '1');
					//enableDateField(selectedDropdownID);
				} catch (e) {
					//alert("Error - "+e);
				}
				enableDateField(selectedDropdownID);
			}else{
				var beforeDropDownId = getBeforeDropDownIdForADateField();
				
				if(selectedDropdownID==beforeDropDownId){					
					var eventId = getSelectedEventId();
					if(eventId!=-1){ // && (typeof selectedDropdownIDVal == "undefined")
						var login_first=$("#login_first").val();
						if(login_first=='Y' || login_first=='y'){
							var url = "isAllowDuplicateResv.html?eventId="+eventId;
							url = url.replace(/ /g, '%20');

							$.get(url, function(data) {
								data = JSON.parse(data);	
								//alert("Response ::::  "+(data.response));
								//alert("Message ::::  "+(data.message));
								//data.response="SUCCESS"
								if(data.response=="SUCCESS"){
									$("#date").prop("disabled", false);
									enableDateField(selectedDropdownID);	
								}else{
									$('#'+selectedDropdownID+"_error_DivId").html("");
									$('#'+selectedDropdownID+"_error_DivId").show();
									$("#"+selectedDropdownID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+(data.message));
								}
							});	
						}else{
							$("#date").prop("disabled", false);
							enableDateField(selectedDropdownID);	
						}
					}else{
						$('#'+selectedDropdownID+"_error_DivId").html("");
						$('#'+selectedDropdownID+"_error_DivId").show();
						fieldIDErrorMsg = $('#'+selectedDropdownID+"_label").val() ;
						$("#"+selectedDropdownID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
					}
				}
				//enableDateField(selectedDropdownID);
			}			
		}else{
			$('#'+selectedDropdownID+"_error_DivId").html("");
			$('#'+selectedDropdownID+"_error_DivId").show();
			fieldIDErrorMsg = $('#'+selectedDropdownID+"_label").val() ;
			$("#"+selectedDropdownID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
			//alert("Please select proper value");
			dissableNextAllFields(selectedDropdownID);
		}
	} catch (e) {
		//alert("Error - "+e);
	}
}


 function dissableNextAllFields(selectedDropdownID){
	 try {
		var selBoxIDs     =new Array ('display_company','display_procedure','display_location','display_department','display_event','display_time','display_seat' );
		var netSelCtBoxId = selBoxIDs.indexOf(selectedDropdownID);
		var netSelCtBox = selBoxIDs[netSelCtBoxId+1];
		
		for(var intCnt  = netSelCtBoxId+1   ; intCnt < selBoxIDs.length ; intCnt++) {
			try{
				  var field_id = selBoxIDs[intCnt] ;
				  $('#'+field_id).val("-1") ;
				  $('#'+field_id).prop("disabled", true);
			} catch (e) {
				//alert('exe   '+e);
			} 
		} 
		if(selectedDropdownID!='display_time'){
				$('#date').val($('#selectDateLabel_asc_val').val()) ;
				$('#date').prop("disabled", true);
		}
		
		//$("#resDetailsNextBtn").prop("disabled", true);
		//$('#resDetailsNextBtn').css('opacity', '0.5');

	  } catch (e) {
		//alert('exe   '+e);
	  } 
  }

  function enableDateField(selectedDropdownID){
	 try{
			var beforeDropDownIdForDateField = getBeforeDropDownIdForADateField();
			var isdisabled = $("#"+beforeDropDownIdForDateField).is(":disabled");
			if((selectedDropdownID==beforeDropDownIdForDateField) && ( !isdisabled || $("#display_event").val()!="-1")){
				var selectedDropdownIDVal = $("#"+selectedDropdownID).val();
				if(selectedDropdownIDVal!="" && selectedDropdownIDVal!="-1" 
					&& selectedDropdownIDVal!=undefined && selectedDropdownIDVal!=null){
						$("#date").prop("disabled", false);
						var calenderId=$('input[class="dateField"]').attr("id");
						populateDataForDateField();	
				}
			}
	 } catch (e) {
		//alert('exe :::::::::::  '+e);
	 } 
}

function getBeforeDropDownIdForADateField(){
	var beforeDropDownId = "";
	var displayCompany = $("#display_company_asc_val").val();
	var displayProcedure = $("#display_procedure_asc_val").val();
	var displayLocation = $("#display_location_asc_val").val();
	var displayDepartment = $("#display_department_asc_val").val();
	var displayEvent = $("#display_event_asc_val").val();
	
	if((displayEvent=='Y' || displayEvent=='y') || $("#display_event").val()!="-1"){
		beforeDropDownId = 'display_event';
	}else if(displayDepartment=='Y' || displayDepartment=='y'){
		beforeDropDownId = 'display_department';
	}else if(displayLocation=='Y' || displayLocation=='y'){
		beforeDropDownId = 'display_location';
	}else if(displayProcedure=='Y' || displayProcedure=='y'){
		beforeDropDownId = 'display_procedure';
	}else if(displayCompany=='Y' || displayCompany=='y'){
		beforeDropDownId = 'display_company';
	}
	return beforeDropDownId;
}

  function prepareURLToPopulateDropdownData(nextDropdownID){
	var companyId = getSelectedCompanyId();
	var procedureId = getSelectedProdedureId();
	var locationId = getSelectedLocationId();
	var departmentId = getSelectedDepartmentId();
	var eventId = getSelectedEventId();
	
	var id = nextDropdownID+"_asc_val";
	var display_nextDropdown = $("#"+id).val();

	var nextDropdown_label_id = nextDropdownID+"_label";
	var nextDropdown_label = $("#"+nextDropdown_label_id).val();


	var url = "populateDropdownData/"+nextDropdownID+".html?display_nextDropdown="+display_nextDropdown
				+"&nextDropdown_label="+nextDropdown_label
				+"&companyId="+companyId
				+"&procedureId="+procedureId
				+"&locationId="+locationId
				+"&departmentId="+departmentId
				+"&eventId="+eventId;

	url = url.replace(/ /g, "%20");

	return url;	    
}

function populateDataForDateField(){
	var companyId = getSelectedCompanyId();
	var procedureId = getSelectedProdedureId();
	var locationId = getSelectedLocationId();
	var departmentId = getSelectedDepartmentId();
	var eventId = getSelectedEventId();
	
	var dateLabel = $("#date_label").val();

	var url = "getEventDates.html?dateLabel="+dateLabel
				+"&companyId="+companyId
				+"&procedureId="+procedureId
				+"&locationId="+locationId
				+"&departmentId="+departmentId
				+"&eventId="+eventId;
	
	url = url.replace(/ /g, "%20");

	$("#dateHiddenDivId").load(url,function(data){		
		prepareMapAfterAvailDatesLoaded();			
		initiateCalender();	
	});
}


 function loadSeatDetailsOnSelectionOfTime(selectedDropdownID){
	 try {
			var selectedDropdownIDVal = $("#"+selectedDropdownID).val();
			if(selectedDropdownIDVal!=-1){ // && (typeof selectedDropdownIDVal == "undefined")
					$('#'+selectedDropdownID+"_error_DivId").html("");
					$('#'+selectedDropdownID+"_error_DivId").hide();

					//this we have to check with balaji what value he is accepting
					// 01/17/2014  ===>  2014-01-17
					 //var splitStr = $("#date").val().split("/");
					// dateStr = splitStr[2]+"-"+splitStr[0]+"-"+splitStr[1];
					 var dateStr = $("#date").val()

					var companyId = getSelectedCompanyId();
					var procedureId = getSelectedProdedureId();
					var locationId = getSelectedLocationId();
					var departmentId = getSelectedDepartmentId();
					var eventId = getSelectedEventId();		
					var time = $("#display_time").val();
					var display_nextDropdown = $("#display_seat_asc_val").val();
					var seatLabel = $("#display_seat_label").val();
					var selectSeatLabel = $("#seat_select_label").val();

					var url = "getEventSeats.html?seatLabel="+seatLabel
								+"&companyId="+companyId
								+"&procedureId="+procedureId
								+"&locationId="+locationId
								+"&departmentId="+departmentId
								+"&eventId="+eventId
								+"&date="+dateStr
								+"&timeId="+time
								+"&display_nextDropdown="+display_nextDropdown
								+"&selectSeatLabel="+selectSeatLabel;
					url = url.replace(/ /g, '%20');

					 $("#display_seat_DivId").load(url,function(data){	
						 try{
								 $("#display_seat").prop("disabled", false);
								 $('#display_seat').css('opacity', '1');
						 } catch (e) {
								//alert("Error - "+e);
						 }						
					});
			}else{
				$('#'+selectedDropdownID+"_error_DivId").html("");
				$('#'+selectedDropdownID+"_error_DivId").show();
				fieldIDErrorMsg = $('#'+selectedDropdownID+"_label").val() ;
				$("#"+selectedDropdownID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
				//alert("Please select proper value");
				dissableNextAllFields(selectedDropdownID);
			}
	  } catch (e) {
			//alert('exe   '+e);
	  } 
  }

 function validateSeatSelection(){
	 try {
			var display_seat = $("#display_seat").val();
			if(display_seat!=-1){ // && (typeof selectedDropdownIDVal == "undefined")
				$("#display_seat_error_DivId").html("");
				$("#display_seat_error_DivId").hide();
		   }else{
				$("#display_seat_error_DivId").html("");
				$("#display_seat_error_DivId").show();
				fieldIDErrorMsg = $("#display_seat_label").val() ;
				$("#display_seat_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
		}
	  } catch (e) {
		//alert('exe   '+e);
	  } 
  }

   function validateReservationDetsilsSelectionForm(){
	 try {
			var fieldIDs     =new Array ('display_company','display_procedure','display_location','display_department','display_event','date','display_time','display_seat' );
				
			var fieldIDVal = "";
			var fieldIDErrorMsg = "";
			for(var intCnt  = 0   ; intCnt < fieldIDs.length ; intCnt++) {
				try{
					  var fieldID = fieldIDs[intCnt] ;
					  if($('#'+fieldID).prop("disabled")=="false" ||  !($('#'+fieldID).prop("disabled"))){
							fieldIDVal = $('#'+fieldID).val() ;
							fieldIDErrorMsg = $('#'+fieldID+"_label").val() ;
							//alert("fieldID -----> "+fieldID+" \n fieldIDVal -----> "+fieldIDVal);
							if(fieldIDVal=="-1" || typeof fieldIDVal == "undefined"){
								$("#"+fieldID+"_error_DivId").show();
								$("#"+fieldID+"_error_DivId").html("");
								$("#"+fieldID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
								return false;
							}else{
								//alert("fieldID ::: "+fieldID + "     fieldIDVal ::: "+fieldIDVal);
								if("date"==fieldID){
									if(fieldIDVal!=""){//06/30/2015												
										//var pattern = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
										//var sucess= pattern.test(fieldIDVal);
										//if (!sucess) {
											//$("#"+fieldID+"_error_DivId").show();
											//$("#"+fieldID+"_error_DivId").html("");
											//$("#"+fieldID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
											//window.location = contextPath+"/error.html";
											//return false;
										//}	
									}else{
										    $("#"+fieldID+"_error_DivId").show();
											$("#"+fieldID+"_error_DivId").html("");
											$("#"+fieldID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>Please select "+fieldIDErrorMsg);
											return false;
									}									
								}
							}
					  }
				} catch (e) {
					//alert('Exception   :   '+e);
					return false;
				} 
			} 
	  } catch (e) {
		//alert('Exception  :   '+e);
		return false;
	  } 
	  return true;
  }

  function validateDateFormat(dateStr){
	 // alert("checkDateFormat");
  	  var isCorrect=false;
  	  //var pattern = /^([0-9]{2})-([a-zA-Z]{3})-([0-9]{4})$/;	
	  var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
	  var sucess= pattern.test(dateStr);
	 // alert("sucess ----> "+sucess);
     if (sucess) {
			isCorrect=true;
	}
  	return isCorrect;
   }

  function hideErrorDivs(){
		var errorDivIds = new Array ('display_company_error_DivId','display_procedure_error_DivId','display_location_error_DivId','display_department_error_DivId',
				'display_event_error_DivId','date_error_DivId','display_time_error_DivId','display_seat_error_DivId' );
					
		for(var intCnt  = 0   ; intCnt < errorDivIds.length ; intCnt++) {
			try{
				  var errorDivId = errorDivIds[intCnt] ;
				  $('#'+errorDivId).hide();
			} catch (e) {
				//alert('Exception    :  '+e);
			} 
		} 
  }

//Needs to remove
  /*
function getNextSelectDropdownID(selectedDropdownID){
    var nextSelectDropdownID = "";
	var displayCompany = $("#display_company_asc_val").val();
	var displayProcedure = $("#display_procedure_asc_val").val();
	var displayLocation = $("#display_location_asc_val").val();
	var displayDepartment = $("#display_department_asc_val").val();
	var displayEvent = $("#display_event_asc_val").val();

	if(selectedDropdownID=='display_company'){
		if(displayProcedure=='Y' || displayProcedure=='y'){
			nextSelectDropdownID = 'display_procedure';
		}else if(displayLocation=='Y' || displayLocation=='y'){
			nextSelectDropdownID = 'display_location';
		}else if(displayDepartment=='Y' || displayDepartment=='y'){
			nextSelectDropdownID = 'display_department';
		}else if(displayEvent=='Y' || displayEvent=='y'){
			nextSelectDropdownID = 'display_event';
		}
	}else if(selectedDropdownID=='display_procedure'){
		if(displayLocation=='Y' || displayLocation=='y'){
			nextSelectDropdownID = 'display_location';
		}else if(displayDepartment=='Y' || displayDepartment=='y'){
			nextSelectDropdownID = 'display_department';
		}else if(displayEvent=='Y' || displayEvent=='y'){
			nextSelectDropdownID = 'display_event';
		}
	}else if(selectedDropdownID=='display_location'){
		if(displayDepartment=='Y' || displayDepartment=='y'){
			nextSelectDropdownID = 'display_department';
		}else if(displayEvent=='Y' || displayEvent=='y'){
			nextSelectDropdownID = 'display_event';
		}
	}else if(selectedDropdownID=='display_department'){
		if(displayEvent=='Y' || displayEvent=='y'){
			nextSelectDropdownID = 'display_event';
		}
	}
	return nextSelectDropdownID;
}



function getSelectedCompanyId() {
	var companyId = -1;
	try {
		//var displayCompany = $("#display_company_asc_val").val();
		//if(displayCompany=='Y' || displayCompany=='y'){
			 var isdisabled = $("#display_company").is(":disabled");
			 if(!isdisabled){
				companyId = $("#display_company").val();
			 }
		//}	
	} catch(e){
		alert("Error : getSelectedCompanyId  :: "+e);
	}
	if(typeof companyId == "undefined"){
		companyId = -1;
	}
	return companyId;
}

function getSelectedProdedureId() {
	var procedureId = -1;
	try{
		//var displayProcedure = $("#display_procedure_asc_val").val();
		//if(displayProcedure=='Y' || displayProcedure=='y'){
			 var isdisabled = $("#display_procedure").is(":disabled");
			 if(!isdisabled){
				procedureId = $("#display_procedure").val();
			 }
		//}	
	} catch(e){
		alert("Error : getSelectedProdedureId  :: "+e);
	}
	if(typeof procedureId == "undefined"){
		procedureId = -1;
	}
	return procedureId;
}

function getSelectedLocationId() {
	var locationId = -1;
	try{
		//var displayLocation = $("#display_location_asc_val").val();
		//if(displayLocation=='Y' || displayLocation=='y'){
			 var isdisabled = $("#display_location").is(":disabled");
			 if(!isdisabled){
				locationId = $("#display_location").val();
			 }
		//}	
	} catch(e){
		alert("Error : getSelectedLocationId  :: "+e);
	}
	if(typeof locationId == "undefined"){
		locationId = -1;
	}
	return locationId;
}

function getSelectedDepartmentId() {
	var departmentId = -1;
	try{
		//var displayDepartment = $("#display_department_asc_val").val();
		//if(displayDepartment=='Y' || displayDepartment=='y'){
			 var isdisabled = $("#display_department").is(":disabled");
			 if(!isdisabled){
				departmentId = $("#display_department").val();
			 }
		//}	
	} catch(e){
		alert("Error : getSelectedDepartmentId  :: "+e);
	}
	if(typeof departmentId == "undefined"){
		departmentId = -1;
	}
	return departmentId;
}

function getSelectedEventId() {
	var eventId = -1;
	try{
		var displayevent = $("#display_event_asc_val").val();
		if(displayevent=='Y' || displayevent=='y'){
			 var isdisabled = $("#display_event").is(":disabled");
			 if(!isdisabled){
				eventId = $("#display_event").val();
			 }
		}else{
			eventId = $("#display_event").val();
		}
	} catch(e){
		alert("Error : getSelectedEventId  :: "+e);
	}
	if(typeof eventId == "undefined"){
		eventId = -1;
	}
	return eventId;
}

*/