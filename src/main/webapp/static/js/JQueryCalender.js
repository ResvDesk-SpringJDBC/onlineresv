function initiateCalender(){
	try {
		$( "#date").datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat: 'mm/dd/yy',
			onSelect: populateTimeForSelectdate,
			beforeShowDay: showResAvaliableDates
		});	
		//This is to set  default dates
		//$( "#"+id).datepicker( "option", "minDate", getMin_Date());
		//$( "#"+id).datepicker( "option", "maxDate", getMax_Date());
		$( "#date").datepicker( "option", "defaultDate", getDefault_Date());
	} catch (e) {
		//alert("error - "+e)
	}
}

	function getDefault_Date() {
		var defaultDate = "";
		var resvFirstAvailableDate = $("#resvFirstAvailableDate").val();
		if(resvFirstAvailableDate=="" || resvFirstAvailableDate==undefined){
			//defaultDate = $.datepicker.formatDate('mm/dd/yy', new Date());
		}else {
			//var splitStr = resvFirstAvailableDate.split("-");
			//defaultDate = splitStr[1]+"/"+splitStr[2]+"/"+splitStr[0];
			defaultDate = resvFirstAvailableDate;
		}
		return defaultDate;
	}

var Map = {};

function prepareMapAfterAvailDatesLoaded(){
	Map = {};
	var currentDate=$.datepicker.formatDate('mm/dd/yy', new Date());	//date from Back end -- 06/20/15 
	try {
		$('div[class="datePicker_Class"] input[type="hidden"]').each(function(){			
			var key=$(this).attr("id");
			var value=$(this).val();
			//alert("key : "+key +" ---  value : "+value);
			//this we have to check with balaji what value he is accepting
			// 2014-01-17  ===>  01/17/2014
		    //var splitStr = key.split("-");
		    //key = splitStr[1]+"/"+splitStr[2]+"/"+splitStr[0];
			if(key != "populate_dateTime" && key != "resvFirstAvailableDate" && (comparedates_new(key,currentDate)>=0)) {
				//var parsedDate = $.datepicker.parseDate("mm/dd/yy",key);
				//var splitStr = key.split("/"); //06/20/15  --> 15-06-20
				//var dateStr = splitStr[2]+"-"+splitStr[0]+"-"+splitStr[1];
				//var formatedDate = $.datepicker.formatDate('yy-mm-dd', date);
				Map[key]=value;
				//var selectKey=Map[formatedDate];
				//alert("Date : "+key +" ---  Status : "+value);
			}	
		 });
		 /*
		 for (var key in Map) {
			  if (Map.hasOwnProperty(key)) {
					alert(key + " -> " + Map[key]);
			  }
		}
		 */
	} catch (e) {
		//alert("Error -- "+e);
	}
}

function showResAvaliableDates(date) {
	
	try {
		Map[date]
		date = $.datepicker.formatDate('mm/dd/yy', date);		//date from Back end -- 06/20/15 
		//alert("date  7777777 : "+date);
		if(date in Map) {
			//var values=Map[date];
			//var statusMsg=values.split("|");
			//var status=statusMsg[0];
			var status=Map[date];
			var zero="0";
			var one="1";
			var two="2";
			var three="3";
			var four="4";
		   //alert("date : "+date +" --7777777777777777-  status : "+status);
		   if($.trim(status) == $.trim(one)) {
				 return [true,"notAvailable ui-datepicker-unselectable","notAvailable"];
			}
			else if($.trim(status) == $.trim(zero)) {
				 return [true,"available","available"];
			}
			/*
			else if($.trim(status) == $.trim(two)) {
				 return [true,"holiday ui-datepicker-unselectable",'holiday'];
			}
			else if($.trim(status) == $.trim(three)) {
				 return [true,"closed ui-datepicker-unselectable",'closed'];
			}
			else if($.trim(status) == $.trim(four)) {
				 return [true,"notOpened ui-datepicker-unselectable",'notOpened'];
			}
			*/
			else {
				 return [false,"","Disabled"];
			}
		}else{
			 //return [true,"","Disabled"];
			 return [false,"","Disabled"];
		}
	} catch (e) {
		//alert("Error -- "+e);
	}
}




 function populateTimeForSelectdate(dateStr){
	 try {
		 //this we have to check with balaji what value he is accepting
		 // 01/17/2014  ===>  2014-01-17
		 //var splitStr = dateStr.split("/");
		 //dateStr = splitStr[2]+"-"+splitStr[0]+"-"+splitStr[1];
		 //$('#display_time').prop("disabled", true);
		 //$("#submitbtn").prop("disabled", true);
		 //$('#submitbtn').css('opacity', '0.5');
		dissableNextAllFields('display_time');
		$("#date_error_DivId").html("");
		$("#date_error_DivId").hide();

		var companyId = getSelectedCompanyId();
		var procedureId = getSelectedProdedureId();
		var locationId = getSelectedLocationId();
		var departmentId = getSelectedDepartmentId();
		var eventId = getSelectedEventId();		
		//var date = $("#date").val();
		var timeLabel = $("#display_time_label").val();
		var time_select_label = $("#time_select_label").val();

		var url = "getEventTimes.html?timeLabel="+timeLabel
					+"&companyId="+companyId
					+"&procedureId="+procedureId
					+"&locationId="+locationId
					+"&departmentId="+departmentId
					+"&eventId="+eventId
					+"&date="+dateStr
					+"&selectTimeLabel="+time_select_label;
		 url = url.replace(/ /g, "%20");
		
		 $("#date_time_div_id").load(url,function(data){		
		 });
	}catch (e) {
		//alert("Error -- "+e);
	}
}

function highlightOdds(date) {
     return [true, date.getDate() % 2 == 1 ? 'specialDate' : ''];
}


function comparedates_new(dateStr,dateStr1) {	
		var datearray = dateStr.split("/");
		var date = datearray[1];
		//var month = getIndex(datearray[1]);
		var month = datearray[0]
		var year = datearray[2];
		
		var datearray1 = dateStr1.split("/");
		var date1 = datearray1[1];
		//var month1 = getIndex(datearray1[1]);
		var month1 = datearray1[0];
		var year1 = datearray1[2];
		
		if(year>year1){
			//alert('Year is greater');
			return 1;
		} else if(year<year1){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			if(month>month1){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<month1){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>date1){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<date1){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}