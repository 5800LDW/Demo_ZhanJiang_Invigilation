package com.tecsun.jc.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class myTimeUtils {

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}

	public static String getCurrentTime2() {
		return getCurrentTime("yyyyMMddHHmmss");
	}

	public static String getCurrentTime3() {
		return getCurrentTime("yyyyMMdd");
	}
	public static String getCurrentTime4() {
		return getCurrentTime("yyyy-MM-dd");
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sDateFormat.format(new Date());
	}

	public static boolean isDateBefore(String date1, String date2,
									   String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}
	public static String getDateyyyy_MM_ss(String yyyyMMss) {
		if (yyyyMMss.length() == 8) {
			String year = yyyyMMss.substring(0, 4);
			String month = yyyyMMss.substring(4, 6);
			String day = yyyyMMss.substring(6, 8);
			return year + "-" + month + "-" + day;
		}
		return null;
	}
	/**
	 * 获取格式为yyyy/mm/ss的日期
	 */
	public static String getDateYYYY_MM_SS(int year, int month, int day) {
		String dateStr = null;
		String monthStr = null;
		String dayStr = null;
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = String.valueOf(month);
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = String.valueOf(day);
		}
		dateStr = year + "-" + monthStr + "-" + dayStr;
		return dateStr;

	}
	
	/**
	 * 时间对比
	 */
	public static boolean compareData(String startDate, String endDate) {
//		LogUtils.d("xiaoliang", "startDate=" + startDate + "endDate=" + endDate);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(startDate);
			Date d2 = df.parse(endDate);
			long diff = d1.getTime() - d2.getTime();
//			LogUtils.d("xiaoliang", "diff=" + diff);
			if (diff >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
