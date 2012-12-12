package com.p6.frames.dialogs.customer;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.customer.DoDeleteCustomer;
import com.p6.user.infoBean.CustomerInfo;

/**
 * <p>Description: 删除客户资料对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class DeleteCustomer extends CustomerDialog{
	
	/**
	 * 
	 * @param owner 父窗体
	 * @param customerInfo	需要删除的客户信息
	 */
	public DeleteCustomer(JFrame owner, CustomerInfo customerInfo){
		MessageBox confirm = new MessageBox("是否删除客户 "+customerInfo.getCustomerName()+" ?","提示",MessageBox.TYPE_YES_NO,this,true);
		if(confirm.getChoice()!=MessageBox.ID_YES)
			return;
		Handler handler = new Handler();
		handler.setDoHandler(new DoDeleteCustomer());
		handler.process(customerInfo);
		
		if (handler.isSucced()){		//操作成功
			isEdit = true;
			new MessageBox("成功删除客户","成功",owner);
		}
		else {							//操作失败
			isEdit = false;
			new MessageBox("删除客户失败","失败",owner);
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
	private boolean isEdit = false;		//记录操作是否成功
}
