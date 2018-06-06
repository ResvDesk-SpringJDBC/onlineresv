package com.telappoint.onlineresv.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 
 * @author Murali
 *
 */
public class OnlineResUtils {
	private static Log log = LogFactory.getLog(OnlineResUtils.class);
	public static Object getPropertyValue(Object object,String fieldName) throws NoSuchFieldException {
		try {
			BeanInfo info = Introspector.getBeanInfo(object.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors())
				if (pd.getName().equals(fieldName)) {
					Method getter = pd.getReadMethod();
					if(getter != null) {
						getter.setAccessible(true);
						return getter.invoke(object, null);
					}
					
				}
		} catch (Exception e) {
			throw new NoSuchFieldException(object.getClass() + " has no field " + fieldName);
		}
		return "";
	}

	public static void setPropertyValue(Object object, String propertyName, Object propertyValue) throws Exception {
		try {
			BeanInfo bi = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor pds[] = bi.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				if (pd.getName().equals(propertyName)) {
					Method setter = pd.getWriteMethod();
					if (setter != null) {
						setter.invoke(object, new Object[] { propertyValue });
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static boolean isEmpty(String string) {
		return string != null ? string.length() > 0 ? true : false : false;
	}
	
	public static Object getInitCaseValue(Object value) {
		if(value!=null) {
			  String name = (String) value;
			  StringBuilder nameBuilder = new StringBuilder();
			  String[] nameStrs = name.split("\\s+");
			  if(nameStrs!=null && nameStrs.length>0){            		 
				  for(String nameStr : nameStrs){
					  if(nameStr!=null && !" ".equals(nameStr) && nameStr.length()>1){
						  nameBuilder.append(nameStr.substring(0,1)!=null ? nameStr.substring(0, 1).toUpperCase() : "");
						  nameBuilder.append(nameStr.substring(1));
						  nameBuilder.append(" ");
					  }
				  }            		  
			  }	  
			  if(nameBuilder.toString()!=null && !"".equals(nameBuilder.toString().trim())){
				  value = nameBuilder.toString().trim(); 
			  }
		}
		return value;
	}
	
	//Required one please do not delete
	/*public static String encode(String input) {
        StringBuilder resultStr = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (isUnsafe(ch)) {
                resultStr.append('%');
                resultStr.append(toHex(ch / 16));
                resultStr.append(toHex(ch % 16));
            } else {
                resultStr.append(ch);
            }
        }
        return resultStr.toString();
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    private static boolean isUnsafe(char ch) {
        if (ch > 128 || ch < 0)
            return true;
        return " %$&+'!%^(){}[]|-*`,/:;=?@<>#%".indexOf(ch) >= 0;
        //return " %$`~!@#$%^&*()-_=+{}|[]\:"<>?;''+'!%^(){}[]|-*`,/:;=?@<>#%".indexOf(ch) >= 0;
    }*/
 
    public static String getEncodedData(String input) {
		try {
			if(null!=input){
				URLCodec encoder = new URLCodec();
				String safeData = encoder.encode(input);
				return safeData;
			}else{
				return "";
			}
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	    return input;
    }
    
	public static String removeErrorNumber(String stMethodName) {
		int lastIndex = stMethodName.lastIndexOf(":");
		return stMethodName.substring(0,lastIndex);
	}
	
	public static String getMethodName(final int depth) { 
	  StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
      return stackTraceElements[depth].toString();
	}
	
	public static String getJSONString(Object object) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String response = gson.toJson(object);
		return "<br/>InputParameters: ["+response+"]";
	}
	
	public static void sendErrorEmail(StringBuilder body,Exception e,String subject) {
		body.append("<br/><br/>");
		body.append("Exception:");
		body.append("<br/>");
		if(e!=null){
			body.append(OnlineResUtils.getStackTrace(e));	
		}else{
			body.append(" ");
		}		
		try {
	        PropertyUtils.sendErrorEmail(subject,body.toString());
		} catch(Exception ex) {
			log.error("Error: Unable to send application error email!"+ex,ex);
			e.printStackTrace();
		}
	}
	
	public static String getStackTrace(Exception e){
		final StringBuilder result = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace() ){
	      result.append(element);
	      result.append("</br>");
	    }
	    return result.toString();
	}
	
	public static String getNumbersOnlyFromMixedString(String mixedString){
	    return mixedString.replaceAll("[^0-9]", "");
	}
	
}
