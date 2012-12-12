package com.p6.frames.dialogs;

import javax.swing.JDialog;

/**
 * <p>Description: 所有对话框的基类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */

public abstract class FunctionDialog extends JDialog{
	/*
	 * 创建窗体
	 */
	public abstract void create();
	
	/*
	 * 窗体内容是否与数据库交互成功
	 * @return boolean true表示成功,false表示失败
	 */
	public abstract boolean isUpdate();
	
	/*
	 * 得到当前窗体的引用
	 * @return JDialog JDialog对象
	 */
	public abstract JDialog getThis();
}
