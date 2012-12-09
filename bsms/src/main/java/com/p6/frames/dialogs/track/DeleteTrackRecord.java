package com.p6.frames.dialogs.track;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.infoBean.TrackRecordInfo;
import com.p6.user.trackRecord.DoDeleteTrackRecord;

/**
 * <p>Description: 删除跟踪记录对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class DeleteTrackRecord extends TrackRecordDialog{
	
	/**
	 * 初始化
	 * @param owner 父窗体
	 * @param trackRecordInfo	跟踪记录
	 */
	public DeleteTrackRecord(JFrame owner, TrackRecordInfo trackRecordInfo){
		//要求用户确认删除
		MessageBox confirm = new MessageBox("是否删除该跟踪记录?","提示",MessageBox.TYPE_YES_NO,this,true);
		if(confirm.getChoice()!=MessageBox.ID_YES)
			return;
		
		Handler handler = new Handler();
		handler.setDoHandler(new DoDeleteTrackRecord());
		handler.process(trackRecordInfo);
		
		if (handler.isSucced()){	//操作成功
			isEdit = true;
			new MessageBox("成功删除跟踪记录","成功",owner);
		}
		else{						//操作失败
			isEdit = false;
			new MessageBox("删除跟踪记录失败，请重新尝试","失败",owner);
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
