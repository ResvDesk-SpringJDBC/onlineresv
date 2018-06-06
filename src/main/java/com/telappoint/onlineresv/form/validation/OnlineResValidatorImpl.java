package com.telappoint.onlineresv.form.validation;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.telappoint.onlineresv.common.OnlineResValidationConstants;
import com.telappoint.onlineresv.form.LoginForm;
import com.telappoint.onlineresv.model.restws.LoginField;
import com.telappoint.onlineresv.utils.OnlineResUtils;

/**
 * 
 * @author Murali
 */

@Service
public class OnlineResValidatorImpl implements IOnlineResValidator {
	private Logger logger = Logger.getLogger(OnlineResValidatorImpl.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}	
	
	/*Method to validate Login Form*/
	@Override
	public void validate(Object target, Errors errors,List<LoginField> loginFieldList) {
		LoginForm loginForm = (LoginForm) target;
		String fieldValueStr = null;
		String maxChars = null;
		String minChars = null;
		try {			
			/*Iterating through LoginDetails list*/
			for (LoginField loginField : loginFieldList) {
				maxChars = loginField.getValidateMaxChars();
				minChars = loginField.getValidateMinChars();
				String displayType = loginField.getDisplayType();
				String emptyErrorMsg = loginField.getEmptyErrorMessage();
				String invalidErrorMsg = loginField.getInvalidErrorMessage();
				String fieldName = loginField.getFieldName();
			
				if (displayType != null && !displayType.contains("button")) {
					Object fieldValue = OnlineResUtils.getPropertyValue(loginForm, fieldName);		
					
					if(fieldValue!=null) {					
						if (fieldValue instanceof String) {
							fieldValueStr = (String) fieldValue;
						}
						
						if('Y'!=loginField.getIsMandatory() && (fieldValueStr==null || "".equals(fieldValueStr))){
							continue;
						}
						
						if((('Y'==loginField.getIsMandatory())) || (fieldValue!=null && fieldValueStr!=null && !"".equals(fieldValueStr))) {
							/*Checking that validation is required or not for this field */
							if (displayType != null && (displayType.contains("textbox-")|| loginField.getValidateRules().contains("phone") || loginField.getValidateRules().contains("numeric"))) {
								if (fieldValue instanceof String) {
									fieldValueStr = (String) fieldValue;
								}								
								if(loginField.getValidateRules().contains("phone")) {
									if ("".equals(fieldValueStr.trim()) || fieldValueStr == null) {
										errors.rejectValue(fieldName, emptyErrorMsg, emptyErrorMsg);
									}
									else{
										check(fieldValue.toString(), OnlineResValidationConstants.PHONE.getValidateExp(), fieldName, invalidErrorMsg, errors);
									}
								}								
								else if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
									errors.rejectValue(fieldName, invalidErrorMsg, invalidErrorMsg);
								}
								else if ( maxChars != null && fieldValueStr.length() > Integer.parseInt(maxChars) ) {
									errors.rejectValue(fieldName, invalidErrorMsg, invalidErrorMsg);
								}
								else if(loginField.getValidateRules().contains("numeric")) {
									check(fieldValue.toString(), OnlineResValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, errors);
								}
							} else if(!(fieldName.equals("date") || fieldName.equals("Date&Time"))) {
								if (fieldValue instanceof String) {
									fieldValueStr = (String) fieldValue;
								}
								
								if ("".equals(fieldValueStr.trim()) || fieldValueStr == null) {
									errors.rejectValue(fieldName, emptyErrorMsg, emptyErrorMsg);
								} 
								else if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
									errors.rejectValue(fieldName, invalidErrorMsg, invalidErrorMsg);
								}
								
								else if(maxChars != null && fieldValueStr.length() > Integer.parseInt(maxChars)) {
									errors.rejectValue(fieldName, invalidErrorMsg, invalidErrorMsg);
								} else {										
									boolean isValid = FormValidationUtils.validateFieldValue(loginField.getValidateRules(),fieldValue.toString());
									if(!isValid) {
										errors.rejectValue(fieldName, invalidErrorMsg, invalidErrorMsg);
									}
								}
							}							
						}else{
							errors.rejectValue(fieldName, emptyErrorMsg, emptyErrorMsg);
						}
					}else{
						if('Y'!=loginField.getIsMandatory()){
							errors.rejectValue(fieldName, emptyErrorMsg, emptyErrorMsg);
						} else if('Y' == loginField.getIsMandatory()){
							errors.rejectValue(fieldName, emptyErrorMsg, emptyErrorMsg);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
	}
	
	private void check(String fieldValue, String reg, String fieldName, String message, Errors errors) {
		fieldValue = fieldValue.replaceAll(OnlineResValidationConstants.HYPHEN.getValidateExp(), "");
		if(!fieldValue.matches(reg)) {
			errors.rejectValue(fieldName, message, message);
		}
	}
}
