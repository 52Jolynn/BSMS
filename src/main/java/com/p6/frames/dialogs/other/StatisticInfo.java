package com.p6.frames.dialogs.other;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * <p>Description: 统计信息对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class StatisticInfo extends OtherDialog{
	
	@SuppressWarnings("unused")
	private StatisticInfo(){}
	
	public StatisticInfo(JFrame owner){
	}
	
	@Override
	public void create() {
		setVisible(true);
		
	}

	@Override
	public JDialog getThis() {
		return this;
	}

	@Override
	public boolean isUpdate() {
		return true;
	}
}
