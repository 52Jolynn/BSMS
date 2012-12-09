package com.p6.frames.dialogs.personal;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.p6.frames.Role;
import com.p6.toolkit.Ecrypt;
import com.p6.toolkit.GBC;
import com.p6.toolkit.MessageBox;

import com.p6.user.infoBean.StaffInfo;
import com.p6.user.personal.DoModifySelfPassword;

/**
 * <p>Description: 修改个人密码对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author txz
 * @version 1.0
 */
public class ModifySelfPwd extends PersonalDialog{
	/**
	 * 
	 * @param owner 父窗体
	 */
	public ModifySelfPwd(final JFrame owner){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("修改密码");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setLocationRelativeTo(owner);
		setModal(true);
		
		//初始化各个控件
		oldPassword = new JPasswordField(10);
		newPassword = new JPasswordField(10);
		confirmPassword = new JPasswordField(10);
		
		JPanel pwdPanel = new JPanel(new GridBagLayout());
		pwdPanel.add(new JLabel("旧密码"),new GBC(0,0,2,1).setInsets(5).setAnchor(GBC.WEST));
		pwdPanel.add(oldPassword,new GBC(2,0,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
		
		pwdPanel.add(new JLabel("新密码"),new GBC(0,4,2,1).setInsets(5).setAnchor(GBC.WEST));
		pwdPanel.add(newPassword,new GBC(2,4,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
		
		pwdPanel.add(new JLabel("重复新密码"),new GBC(0,8,2,1).setInsets(5).setAnchor(GBC.WEST));
		pwdPanel.add(confirmPassword,new GBC(2,8,10,1).setInsets(5).setFill(GBC.HORIZONTAL));
		
		pwdPanel.add(new JLabel("密码由6-16个字符组成，区分大小写"),new GBC(0,12,12,1).setInsets(5).setAnchor(GBC.WEST));
		
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		//判断用户的职位
		if (owner instanceof Role) {
			staffInfo = ((Role)owner).getStaffInfo();
		}
		else{
			System.out.println("modifySelfPwd error");
		}
		
		//确认按钮
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(checkData() ==false)
					return;
				String oldPwd = Ecrypt.EcryptByMD5(String.valueOf(oldPassword.getPassword()));	//得到就密码
				String newPwd = Ecrypt.EcryptByMD5(String.valueOf(newPassword.getPassword()));	//新密码
				DoModifySelfPassword handler = new DoModifySelfPassword();
				handler.process(staffInfo, oldPwd, newPwd);
				
				if(handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功修改个人密码","成功",owner);
					ModifySelfPwd.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("修改个人密码失败，请重新尝试","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModifySelfPwd.this.dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
			
		//将控件放入窗体
		this.add(pwdPanel,BorderLayout.CENTER);
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
	 * 判断用户输入的信息是否有效
	 * @return 若有效返回true，否则返回false
	 */
	private boolean checkData(){
		char [] oldPwd = oldPassword.getPassword();
		if(oldPwd.length==0){
			new MessageBox("请输入旧密码","提示",this);
			return false;
		}
		
		char [] newPwd = newPassword.getPassword();
		if(newPwd.length==0 || newPwd.length<6){
			new MessageBox("请输入新密码,大于6位","提示",this);
			return false;
		}
		
		char [] confirmPwd = confirmPassword.getPassword();
		if(confirmPwd.length==0){
			new MessageBox("请重复输入新密码","提示",this);
			return false;
		}
		
		if(newPwd.length != confirmPwd.length){
			new MessageBox("两次输入的密码不一致，请重新输入","提示",this);
			System.out.println("1");
			return false;
		}
		
		int i=0;
		for(; i<newPwd.length; i++){
			if(newPwd[i] != confirmPwd[i])
				break;
		}
		if(i!= newPwd.length){
			new MessageBox("两次输入的密码不一致，请重新输入","提示",this);
			System.out.println("2");
			return false;
		}
	
		return true;
	}

	//窗体默认的大小
	private final int DEFAULT_WIDTH = 300;
	private final int DEFAULT_HEIGTH = 200;
	
	private JPasswordField oldPassword = null;		//旧密码
	private JPasswordField newPassword = null;		//新密码
	private JPasswordField confirmPassword = null;	//确认密码
	
	private boolean isEdit = false;		//记录操作是否成功
	
	//按钮
	private JButton submit = null;
	private JButton cancel = null;
	
	private StaffInfo staffInfo = null;
}
