package com.p6.frames.dialogs.staff;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.infoBean.StaffInfo;
import com.p6.user.staff.DoDeleteStaff;

/**
 * <p>Description: 删除销售人员对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class DeleteStaff extends StaffDialog{
	
	/**
	 * 初始化
	 * @param owner	父窗体
	 * @param staffInfo	需要删除的销售人员
	 */
	public DeleteStaff(JFrame owner,StaffInfo staffInfo){
		//需要用户确认删除
		MessageBox confirm = new MessageBox("是否删除销售人员 "+staffInfo.getStaffName()+" ?","提示",MessageBox.TYPE_YES_NO,this,true);
		if(confirm.getChoice()!=MessageBox.ID_YES)
			return;
		Handler handler = new Handler();
		handler.setDoHandler(new DoDeleteStaff());
		handler.process(staffInfo);
		
		if (handler.isSucced()){	//操作成功
			isEdit = true;
			new MessageBox("成功删除销售人员","成功",owner);
		}
		else{						//操作失败
			isEdit = false;
			new MessageBox("删除销售人员失败","失败",owner);
		}
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
		return isEdit;
	}
	private boolean isEdit = false;	//记录操作是否成功
}
