package com.p6.frames.dialogs.contact;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.contact.DoDeleteContact;
import com.p6.user.infoBean.ContactInfo;

/**
 * <p>Description: 删除联系人对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class DeleteContact extends ContactDialog{
	
	
	/**
	 * 
	 * @param owner 父窗体
	 * @param contactInfo	需要删除的联系人的信息
	 */
	public DeleteContact(JFrame owner,ContactInfo contactInfo){
		//要用户确认删除操作
		MessageBox confirm = new MessageBox("是否删除联系人 "+contactInfo.getContactName()+" ?","提示",MessageBox.TYPE_YES_NO,this,true);
		
		//如果用户不删除，则返回，否则继续执行删除操作
		if(confirm.getChoice()!=MessageBox.ID_YES)
			return;
		
		Handler handler = new Handler();
		handler.setDoHandler(new DoDeleteContact());
		handler.process(contactInfo);
		
		if (handler.isSucced()){		//操作成功
			isEdit = true;
			new MessageBox("成功删除联系人","成功",owner);
		}
		else {							//操作失败
			isEdit = false;
			new MessageBox("删除联系人失败，请重新尝试","失败",owner);
		}
	}
	
	@Override
	public void create() {
		//setVisible(false);
		
	}

	@Override
	public JDialog getThis() {
		return this;
	}

	@Override
	public boolean isUpdate() {
		return isEdit;
	}
	private boolean isEdit = false;		//记录是否成功修改
}
