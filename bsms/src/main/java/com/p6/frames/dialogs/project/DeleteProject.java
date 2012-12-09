package com.p6.frames.dialogs.project;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.project.DoDeleteProject;

/**
 * <p>Description: 删除项目对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class DeleteProject extends ProjectDialog{
	
	/**
	 * 初始化
	 * @param owner 父窗体
	 * @param projectInfo 需要删除的项目信息
	 */
	public DeleteProject(JFrame owner, ProjectInfo projectInfo){
		//要求用户再次确认删除
		MessageBox confirm = new MessageBox("是否删除项目 "+projectInfo.getProjectName()+" ?","提示",MessageBox.TYPE_YES_NO,this,true);
		if(confirm.getChoice()!=MessageBox.ID_YES)
			return;
		
		Handler handler = new Handler();
		handler.setDoHandler(new DoDeleteProject());
		handler.process(projectInfo);
		
		if (handler.isSucced()){		//操作成功
			isEdit = true;
			new MessageBox("成功删除项目","成功",owner);
		}
		else{							//操作失败
			isEdit = false;
			new MessageBox("删除项目失败，请重新尝试","失败",owner);
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
