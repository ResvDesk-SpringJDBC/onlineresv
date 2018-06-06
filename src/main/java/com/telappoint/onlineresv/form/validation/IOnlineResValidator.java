package com.telappoint.onlineresv.form.validation;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.telappoint.onlineresv.model.restws.LoginField;
/**
 * 
 * @author Murali
 * 
 */
public interface IOnlineResValidator extends Validator {
	
	public void validate(Object target, Errors errors,List<LoginField> loginFieldList);
}
