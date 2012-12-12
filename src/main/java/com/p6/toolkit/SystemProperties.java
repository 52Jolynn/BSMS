/**
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.toolkit;

import java.awt.*;

public class SystemProperties {

	/**
	 * 构造函数
	 */
	private SystemProperties() {
	}
	
	public static SystemProperties getInstance() {
		return sp;
	}
	
	/**
	 * 获取系统屏幕宽度,整型
	 */
	public int getScreenWidth() {
		return DEFAULT_INTEGER_WIDTH;
	}
	
	/**
	 * 获取系统屏幕高度,整型
	 */
	public int getScreenHeight() {
		return DEFAULT_INTEGER_HEIGTH;
	}
	
	private static final SystemProperties sp = new SystemProperties();
	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Dimension screenSize = kit.getScreenSize();
	private final int DEFAULT_INTEGER_WIDTH = screenSize.width;
	private final int DEFAULT_INTEGER_HEIGTH = screenSize.height;
}
