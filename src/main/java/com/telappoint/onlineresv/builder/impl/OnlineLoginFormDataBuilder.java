package com.telappoint.onlineresv.builder.impl;

import java.util.List;

import com.telappoint.onlineresv.builder.IDataBuilder;
import com.telappoint.onlineresv.form.LoginForm;
import com.telappoint.onlineresv.model.online.OnlineResRequest;
import com.telappoint.onlineresv.model.online.OnlineResvSessionData;
import com.telappoint.onlineresv.model.restws.ClientInfo;
import com.telappoint.onlineresv.model.restws.LoginField;
import com.telappoint.onlineresv.utils.OnlineResUtils;

/**
 * @author Murali
 * 
 */
public class OnlineLoginFormDataBuilder implements IDataBuilder<LoginForm, OnlineResRequest> {
	
	private static volatile OnlineLoginFormDataBuilder instance;

	private OnlineLoginFormDataBuilder() {
	}

	public static OnlineLoginFormDataBuilder getInstance() {
		if (instance == null) {
			synchronized (OnlineLoginFormDataBuilder.class) {
				if (instance == null)
					instance = new OnlineLoginFormDataBuilder();
			}
		}
		return instance;
	}
	
	@Override
	public String prepareLoginParamsData(List<LoginField> loginFieldList,LoginForm loginForm) throws NoSuchFieldException {
		StringBuilder loginParams = new StringBuilder();
		for (LoginField loginField : loginFieldList) {
			String displayType = loginField.getDisplayType();
			if (displayType != null && !displayType.contains("button")) {
				Object fieldValue = OnlineResUtils.getPropertyValue(loginForm,loginField.getFieldName());
				loginParams.append((String)fieldValue).append("%7C");// '%7C'  represents '|'
			}
		}
		return loginParams.toString().substring(0,loginParams.toString().length()-3);
	}
	
	@Override
	public OnlineResvSessionData prepareOnlineResvSessionData(ClientInfo clientInfo) throws NoSuchFieldException {
		OnlineResvSessionData sessionData = new OnlineResvSessionData();
		if(clientInfo!=null){
			sessionData.setClientCode(clientInfo.getClientCode());
			sessionData.setLangCode(clientInfo.getLangCode());
			sessionData.setLoginFirst(clientInfo.getLoginFirst());
			sessionData.setToken(clientInfo.getToken());
			sessionData.setTransId(clientInfo.getTransId());
		}
		return sessionData;
	}
}
