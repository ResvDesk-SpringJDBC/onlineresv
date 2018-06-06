package com.telappoint.onlineresv.builder;

import java.util.List;

import com.telappoint.onlineresv.form.LoginForm;
import com.telappoint.onlineresv.model.online.OnlineResvSessionData;
import com.telappoint.onlineresv.model.restws.ClientInfo;
import com.telappoint.onlineresv.model.restws.LoginField;

/**
 * 
 * @author Murali
 * 
 */
public interface IDataBuilder<F, T> {
	public String prepareLoginParamsData(List<LoginField> loginFieldList,LoginForm loginForm) throws NoSuchFieldException;

	public OnlineResvSessionData prepareOnlineResvSessionData(ClientInfo clientInfo) throws NoSuchFieldException;
}
