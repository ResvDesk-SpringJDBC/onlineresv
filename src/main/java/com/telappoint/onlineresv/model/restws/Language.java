package com.telappoint.onlineresv.model.restws;

public class Language {
	
	private String langCode;
	private String langName;
	private char isDefault;
	private String linkDisplay;

	public Language(String langCode, String langName, char isDefault, String linkDisplay) {
		this.langCode = langCode;
		this.langName = langName;
		this.isDefault = isDefault;
		this.linkDisplay = linkDisplay;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public char getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}

	public String getLinkDisplay() {
		return linkDisplay;
	}

	public void setLinkDisplay(String linkDisplay) {
		this.linkDisplay = linkDisplay;
	}
}
