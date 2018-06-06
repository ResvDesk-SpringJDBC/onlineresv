<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script type="text/javascript">	
	var contextPath="<%=request.getContextPath() %>";
	try{		
		var id = "${nextSelectDropdownID}"+"_loadded";
		$("#"+id).val("true");
		//alert("length  ::::::::::   ${fn:length(dropDownList)} ");
	}catch(e){
		//alert("Error ::::::::::  "+e);
	}	
</script>


	<c:if test= "${ optionList!=null}">	 

		<c:if test ="${display_nextDropdown == 'y' || display_nextDropdown == 'Y'}">	
			
			<c:if test= "${!errorStatus}">
					<c:if test= "${fn:length(optionList) gt 0}">
						<label for="${label}">${label}</label>
						<c:if test= "${fn:length(optionList) gt  1}">	 
							<select id="${nextSelectDropdownID}" name="${nextSelectDropdownID}" onchange="populateNextFieldData('${nextSelectDropdownID}');">
								<option value="-1">${selectLabel}</option>
								<c:forEach var="option"  items="${optionList}">
									<option value="${option.optionKey}" ${option.optionKey==selectDefaultId ? 'selected' : '' }>${option.optionValue}</option>
								</c:forEach>
							</select>
						</c:if>

						<c:if test= "${fn:length(optionList) eq  1}">
							<script type="text/javascript">
								populateNextFieldData('${nextSelectDropdownID}');
							</script>
							<label class="label_big" for="label">${optionList[0].optionValue}</label>
							<input type="hidden" name="${nextSelectDropdownID}" id="${nextSelectDropdownID}" value="${optionList[0].optionKey}"/>
						</c:if>
						
						<!-- Here This condition wont occur -->
						<%--
						<c:if test= "${fn:length(optionList) lt  1}">
							<select id="${nextSelectDropdownID}" name="${nextSelectDropdownID}" onchange="populateNextFieldData('${nextSelectDropdownID}');">
								<option value="-1">${selectLabel}</option>
							</select>
						</c:if>
						--%>
				</c:if>
				<c:if test= "${fn:length(optionList) eq  0}">
					<input type="hidden" name="${nextSelectDropdownID}" id="${nextSelectDropdownID}" value="0"/>
					<script type="text/javascript">
						populateNextFieldData('${nextSelectDropdownID}');
					</script>
				</c:if>
			</c:if>

			<c:if test= "${errorStatus}">
				<label for="${label}">${label}</label>
				<script type="text/javascript">
					var  fieldID = "${nextSelectDropdownID}";
					var fieldIDErrorMsg = "${errorMessage}";
					$('#'+fieldID+"_error_DivId").html("");
					$('#'+fieldID+"_error_DivId").show();
					$('#'+fieldID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
				</script>
			</c:if>

		</c:if>

		<c:if test ="${display_nextDropdown == 'n' || display_nextDropdown == 'N'}">	
			<c:if test= "${fn:length(optionList) gt 0}">
				<input type="hidden" name="${nextSelectDropdownID}" id="${nextSelectDropdownID}" value="${optionList[0].optionKey}"/>
			</c:if>
			<c:if test= "${fn:length(optionList) eq  0}">
				<input type="hidden" name="${nextSelectDropdownID}" id="${nextSelectDropdownID}" value="0"/>
			</c:if>
			<script type="text/javascript">
				populateNextFieldData('${nextSelectDropdownID}');
			</script>
		</c:if>

	</c:if>

	<c:if test= "${optionList==null}">	
		<script type="text/javascript">
			//alert("Null Data for ${nextSelectDropdownID}");
			var  fieldID = "${nextSelectDropdownID}";
			var fieldIDErrorMsg = "${errorMessage}";
			$('#'+fieldID+"_error_DivId").html("");
			$('#'+fieldID+"_error_DivId").show();
			$('#'+fieldID+"_error_DivId").html("<span class='ui-icon ui-icon-alert float_left'></span>"+fieldIDErrorMsg);
		</script>
	</c:if>