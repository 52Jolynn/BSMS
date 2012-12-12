package com.p6.frames.dialogs.customer;

import javax.swing.JDialog;

import com.p6.frames.dialogs.FunctionDialog;

/**
 * <p>Description: 所有客户资料管理对话框的基类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public abstract class CustomerDialog extends FunctionDialog {
	public abstract void create();
	public abstract JDialog getThis();
	public abstract boolean isUpdate();
}
