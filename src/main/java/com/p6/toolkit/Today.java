/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.util.Calendar;

public class Today {

	
	/**
	 * 私有构造函数
	 */
	private Today() {
		
	}
	
	/**
	 * 得到四位数年份
	 */
	public static String getYear() {
		return Integer.toString(calendar.get(calendar.YEAR));
	}
	
	/**
	 * 得到月份
	 */
	public static String getMonth() {
		return Integer.toString(calendar.get(calendar.MONTH) + 1);
	}
	
	/**
	 * 得到日期
	 */
	public static String getDate() {
		return Integer.toString(calendar.get(calendar.DATE));
	}
	
	/**
	 * 得到星期
	 */
	public static String getWeek() {
		int week = calendar.get(calendar.DAY_OF_WEEK);
		String str = null;
		
		switch (week) {
		case 1:
			str = "星期日";
			break;
		case 2:
			str = "星期一";
			break;
		case 3:
			str = "星期二";
			break;
		case 4:
			str = "星期三";
			break;
		case 5:
			str = "星期四";
			break;
		case 6:
			str = "星期五";
			break;
		case 7:
			str = "星期六";
			break;
		}
		return str;
	}
	
	/**
	 * 得到小时
	 */
	public static String getHour() {
		return Integer.toString(calendar.get(calendar.HOUR));
	}
	
	/**
	 * 得到分钟
	 */
	public static String getMinute() {
		return Integer.toString(calendar.get(calendar.MINUTE));
	}
	
	/**
	 * 得到秒数
	 */
	public static String getSecond() {
		return Integer.toString(calendar.get(calendar.SECOND));
	}
	
	private static Calendar calendar = Calendar.getInstance();
	
}
