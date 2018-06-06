package com.telappoint.onlineresv.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.telappoint.onlineresv.common.CommonDateContants;

public class DateUtils {
	private static final Logger logger = Logger.getLogger(DateUtils.class);

	public static String convertRestWSToFrontEndDateFormat(String restWSFormatDate) throws ParseException {
		ThreadLocal<DateFormat> restWSFormat = getSimpleDateFormat(CommonDateContants.REST_WS_DATE_FORMAT.getValue());
		ThreadLocal<DateFormat> rontEndFormat = getSimpleDateFormat(CommonDateContants.FRONT_END_DATE_FORMAT.getValue());
		return rontEndFormat.get().format(restWSFormat.get().parse(restWSFormatDate));
	}

	public static String convertRestWSToFrontEndTimeFormat(String restWSFormatTime) throws ParseException {
		ThreadLocal<DateFormat> restWSFormat = getSimpleDateFormat(CommonDateContants.REST_WS_TIME_FORMAT.getValue());
		ThreadLocal<DateFormat> rontEndFormat = getSimpleDateFormat(CommonDateContants.FRONT_END_TIME_FORMAT.getValue());
		return rontEndFormat.get().format(restWSFormat.get().parse(restWSFormatTime));
	}
	public static ThreadLocal<DateFormat> getSimpleDateFormat(String dateTimeFormatStr) {
		ThreadLocal<DateFormat> tldf = null;
		try {
			tldf = getThreadLocal(dateTimeFormatStr);
			return tldf;
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
		return tldf;
	}

	public static ThreadLocal<DateFormat> getThreadLocal(final String dateTimeForamtStr) {
		final ThreadLocal<DateFormat> tldf_ = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat(dateTimeForamtStr);
			}
		};
		return tldf_;
	}

	public static Date getDateObject(String dateTimeString, String formate) {
		Date date = null;
		if (dateTimeString != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(formate);
				date = dateFormat.get().parse(dateTimeString.trim());
				return date;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	public static String getStringFromDate(Date date, String formate) {
		String dateTimeString = "";
		if (date != null) {
			try {
				ThreadLocal<DateFormat> dateFormat = getSimpleDateFormat(formate);
				dateTimeString = dateFormat.get().format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dateTimeString;
	}

	/*public static void main(String... args) throws Exception {
		//System.out.println("::"+getCurrentDate("yyyy-MM-dd HH:mm:ss","US/Central"));
		DateFormat df = new SimpleDateFormat("hh:mm a");
		Date startDate = df.parse("10:10 PM");
		
		System.out.println(DateUtils.getSimpleDateFormat("h:mm a").get().format(startDate));
	}*/
}
