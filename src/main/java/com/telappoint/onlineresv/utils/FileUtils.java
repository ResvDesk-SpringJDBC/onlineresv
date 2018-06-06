package com.telappoint.onlineresv.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 
 * @author Murali
 * 
 */
public class FileUtils {
	private static final Logger logger = Logger.getLogger(FileUtils.class);

	private static Map<String, Properties> propsMap = new HashMap<String, Properties>();
	public static final String REST_WS_URL_PROPFILE_FILE_PATH = "onlineresv.properties";
	public static final String MAIL_PROPFILE_FILE_PATH = "notifymail.properties";
	
	public static InputStream getResourceAsStream(String fileName) throws Exception {
		InputStream propsIn = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (propsIn == null) {
			propsIn = FileUtils.class.getResourceAsStream(fileName);
		}
		if (propsIn == null) {
			propsIn = ClassLoader.getSystemResourceAsStream(fileName);
		}

		if (propsIn == null) {
			logger.error(fileName + " not found");

			throw new Exception(fileName + " not found");
		}
		return propsIn;
	}

	public static Properties getProperties(String fileName) throws Exception {

		Properties properties = propsMap.get(fileName);
		if (properties != null) {
			return properties;
		}
		try {
			properties = new Properties();
			properties.load(getResourceAsStream(fileName));
			propsMap.put(fileName, properties);
		} catch (IOException e) {
			logger.error(e);
			throw new Exception();
		}

		return properties;
	}


	public static Properties refreshProperties(String fileName) throws Exception {
		Properties properties = new Properties();
		try {
			properties.load(getResourceAsStream(fileName));
			propsMap.put(fileName, null);
		} catch (IOException e) {
			logger.error(e);
			throw new Exception();
		}
		return properties;
	}

	public static Properties getOnlineResvProperties() {
		Properties properties = new Properties();
		if (propsMap.get("onlineresv.properties") == null) {
			try {
				properties.load(getResourceAsStream(REST_WS_URL_PROPFILE_FILE_PATH));
				propsMap.put("onlineresv.properties", properties);
			} catch (Exception e) {
				try {
					properties = getProperties(REST_WS_URL_PROPFILE_FILE_PATH);
				} catch (Exception e1) {
					logger.error("Error:" + e1, e1);
				}
				logger.error("Error:"+e,e);
			}			
		} else{
			properties = propsMap.get("onlineresv.properties");
		}	
		return properties;
	}
	
	public static String getResvDESKRestWSEndPointURL() {
		Properties properties = getOnlineResvProperties();
		String restWSEndPointURL = properties.getProperty("ONLINE_RESVDESK_REST_SERVICE_URL_ENDPOINT");
		return restWSEndPointURL;
	}
	
	public static String getResvDESKRegistrationRestWSEndPointURL() {
		Properties properties = getOnlineResvProperties();
		String restWSEndPointURL = properties.getProperty("ONLINE_RESVDESK_REGISTRATION_REST_SERVICE_URL_ENDPOINT");
		return restWSEndPointURL;
	}
	
	public static Properties getMailProperties() {
		Properties properties = new Properties();
		if (propsMap.get("mailProperties") == null) {
			try {
				properties.load(new FileInputStream(MAIL_PROPFILE_FILE_PATH));
				propsMap.put("mailProperties", properties);
			} catch (Exception e) {
				logger.error("Error:"+e,e);				
			}			
		} else{
			properties = propsMap.get("mailProperties");
		}			
		return properties;
	}
	
}
