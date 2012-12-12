/**
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.toolkit;

import java.util.regex.*;

public class CheckTools {

	/**
	 * 私有构造函数
	 */
	private CheckTools() {
		
	}
	
	/**
	 * 检测email是否有效
	 * @param email 待检测email字符串
	 */
	public static boolean isEmailValid(String email) {
		String regex = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z[-]*])*@(([0-9a-zA-Z])+([-\\w]*" +
				"[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 检测手机号
	 * @param mobile 待检测的手机号
	 */
	public static boolean isMobileValid(String mobile) {
		String regex = "1((3[0-9])|(5[8-9]))[0-9]{8}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mobile);
		if(matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 检测固定电话号码
	 * @param phone 待检测的固定电话号码
	 */
	public static boolean isPhoneValid(String phone) {
		String regex = "0\\d{2,3}-?\\d{7,8}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}
}
