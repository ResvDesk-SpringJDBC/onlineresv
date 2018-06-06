<script>
   $(document).ready(function () {			
	   $('.thelink').click(function(){
		  $( "div#thedialog" ).load( "getEventHistory.html", function() {
				  $('div#thedialog').dialog('open'); 
				  $( "#thedialog" ).dialog( "option", "draggable", false );
				  $("#thedialog").dialog(
					   { title: $("#eventHeader").val() }
				  );
			});
		});				
	});
</script>		
${loginRightSideContentResponse.loginInfoRightSideContent}	