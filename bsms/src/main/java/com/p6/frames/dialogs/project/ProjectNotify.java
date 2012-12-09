package com.p6.frames.dialogs.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import com.p6.toolkit.GBC;
import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.project.DoProjectNotify;

/**
 * <p>Description: 项目提醒对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author 陈璨
 * @version 1.0
 */
public class ProjectNotify extends ProjectDialog{
	
	/**
	 * 
	 * @param owner 父窗体
	 */
	public ProjectNotify(final JFrame owner){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("项目提醒");
		setResizable(false);
		setModal(true);
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGTH);
		
		//居中显示
		Toolkit tk=Toolkit.getDefaultToolkit();   
		Dimension  d=tk.getScreenSize();   
		this.setLocation((d.width-DEFAULT_WIDTH)/2,(d.height-DEFAULT_HEIGTH)/2);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JPanel setRemind = new JPanel();
		
		//面板内容
		projectSignDate = new JTextField(10);
		projectInterval = new JTextField(10);
		projectNotify = new JTextField(10);
		
	
		//为信息面板加入内容		
		setRemind.add(new JLabel("下个联系日   "),new GBC(0,0,2,1).setInsets(5).setAnchor(GBC.WEST));
		setRemind.add(projectSignDate,new GBC(2,0,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
		
		setRemind.add(new JLabel("  剩余天数      "),new GBC(0,4,2,1).setInsets(5).setAnchor(GBC.WEST));
		setRemind.add(projectInterval,new GBC(2,4,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
		
		setRemind.add(new JLabel("提前提醒天数"),new GBC(0,8,2,1).setInsets(5).setAnchor(GBC.WEST));
		setRemind.add(projectNotify,new GBC(2,8,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
	
		
		//按钮面板
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		//与数据库交互
		submit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(checkData()==false)
					return;
				Handler handler = new Handler();
				handler.setDoHandler(new DoProjectNotify());
				handler.process(projectInfo);
				
				if (handler.isSucced()){	//操作成功
					isEdit = true;
					new MessageBox("成功修改项目提醒","成功",owner);
					ProjectNotify.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("修改项目提醒失败，请重新尝试","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ProjectNotify.this.dispose();
			}
		});
		
		this.add(setRemind, BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
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
	
	/**
	 * 检查用户输入的数据是否有效
	 * @return 若有效返回true，否则返回false
	 */
	private boolean checkData(){
		String temp = projectNotify.getText();
		int count = 0;
		if(temp.equals("")){
			new MessageBox("请输入项目提醒","提示",this);
			projectNotify.setText("");
			return false;
		}
		else{
			count = new Integer(temp);
			if(count<0||count>=365){
				new MessageBox("输入的项目提醒天数必须小于365","输入的生日提醒提前天数无效",this);
				projectNotify.setFocusable(true);
				projectNotify.setText("");
				return false;
			}
		}
		
		projectInfo.setProjectNotify(count);
		return true;
	}
	
	//窗体默认大小
	private final int DEFAULT_WIDTH = 250;
	private final int DEFAULT_HEIGTH = 160;
	
	//控件
	private JTextField projectNotify = null; 
	private JTextField projectSignDate = null;
	private JTextField projectInterval = null;
	
	//按钮
	private JButton submit = null;	//确定
	private JButton cancel = null;	//取消
	
	private ProjectInfo projectInfo;	//项目信息
	
	private boolean isEdit = false;		//记录操作是否成功
}