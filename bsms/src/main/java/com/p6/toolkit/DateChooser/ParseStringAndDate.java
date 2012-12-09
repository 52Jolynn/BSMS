package com.p6.toolkit.DateChooser;

import java.util.Calendar;
import java.util.Date;

/**
 * 字符串与日期的互相转换函数
 * @author xiaogy
 *
 */
public class ParseStringAndDate {

	private static int gregorianCutoverYear = 1582;
	static int[] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	/**
	 * 将字符串dateStrng转换为字符
	 * @param date String
	 * 被转换的字符串，格式必须为“2007-10-12”，其中“-”为separator
	 * @param separator String 
	 * 分割年月日的字符串 
	 */
	public static String date2String(Date date,String separator)
	{
		if(date ==null)
			return "";
		Integer year = date.getYear()+1900;
		Integer month = date.getMonth()+1;
		Integer days = date.getDate();
		return year.toString()+	separator + month.toString() + separator +days;
	}
	
	/**
	 * 将字符串dateStrng转换为字符
	 * @param dateString String
	 * 被转换的字符串，格式必须为“2007-10-12”，其中“-”为separator
	 * @param separator String 
	 * 分割年月日的字符串 
	 */
	public static Date parseString2Date(String dateString,String separator)
	{
		if(dateString.equals(""))
			return null;
		
		Date d = new Date();
		int firstSeparatorPos = dateString.indexOf(separator);
		int secondSeparatorPos = dateString.indexOf(separator,firstSeparatorPos+1);
		
		Integer year = new Integer(dateString.substring(0, firstSeparatorPos));
		Integer month = new Integer(dateString.substring(firstSeparatorPos+1, secondSeparatorPos));
		Integer date = new Integer(dateString.substring(secondSeparatorPos+1));
		if(year-1900<0)
			year = 1901;
		if(month<1||month>12)
			month = 1;
		if(date>daysInMonths[month-1] + isLeapYear(year))
		       date = daysInMonths[month-1]+ isLeapYear(year);
		else if(date<1)
			date = 1;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, date);
		d = calendar.getTime();
		return d;
	}
	
	
	/**
	 * @param year int
	 * 判断的年份
	 * @return
	 * 如果是闰年，返回1；否则返回0
	 */
	public static int isLeapYear(int year)
	{
		 if( year >= gregorianCutoverYear ? ((year%4 == 0) && ((year%100 != 0) || (year%400 == 0))):(year%4 == 0))
			 return 1;
		 else
			 return 0;
	}
}
