package com.p6.frames.dialogs;

import javax.swing.JDialog;

public class Function {

	public Function() {
		
	}
	
	public void setFunctionDialog(FunctionDialog dialog) {
		this.dialog = dialog;
	}
	
	/*
	 * 创建窗体
	 */
	public void create() {
		dialog.create();
	}
	
	/*
	 * 窗体内容是否与数据库交互成功
	 */
	public boolean isUpdate() {
		return dialog.isUpdate();
	}
	
	/*
	 * 得到当前窗体对象的引用
	 */
	public JDialog getThis() {
		return dialog.getThis();
	}
	
	private FunctionDialog dialog = null;
}
