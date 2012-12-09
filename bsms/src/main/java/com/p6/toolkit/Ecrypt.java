/**
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.toolkit;

import java.io.*;
import java.security.*;
import org.apache.log4j.*;

import sun.misc.BASE64Encoder;

/**
 * 加密程序,MD5加密
 */
public class Ecrypt {
	
	/**
	 * 私有构造函数
	 */
	private Ecrypt() {
	}
	
	/**
	 * 使用MD5算法加密字符串
	 * @param str 待加密的字符串
	 */
	public static String EcryptByMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BASE64Encoder base64Encoder = new BASE64Encoder();
			
			//加密字符串
			logger.debug("使用MD5加密字符串" + str);
			result = base64Encoder.encode(md.digest(str.getBytes("UTF-8")));
		}
		catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException-\"MD5\"" + Ecrypt.class.getName());
		}
		catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException-\"UTF-8\"" + Ecrypt.class.getName());
		}
		
		return result;
	}
	
	private static Logger logger = Logger.getLogger(Ecrypt.class.getName());
	private static String result = null; //加密结果
	
	public static void main(String[] args) {
		logger.debug(Ecrypt.EcryptByMD5("dfjaskjfkdsajfioweirueiwujrijsdfk"));
	}
}
