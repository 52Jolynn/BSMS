package com.p6.frames.dialogs.staff;

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
import javax.swing.JTextField;

import com.p6.frames.Role;
import com.p6.toolkit.Ecrypt;
import com.p6.toolkit.GBC;
import com.p6.toolkit.MessageBox;
import com.p6.user.infoBean.StaffInfo;
import com.p6.user.staff.DoModifyStaffPassword;
/**
 * <p>Description: 设置他人密码对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author txz
 * @version 1.0
 */
public class SetPwd extends StaffDialog{
	/**
	 * 初始化
	 * @param owner 父窗体
	 */
	public SetPwd(final JFrame owner){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("修改密码");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setLocationRelativeTo(owner);
		setModal(true);
		
		//初始化控件
		newPassword = new JTextField(10);
		confirmPassword = new JPasswordField(10);
		JPanel pwdPanel = new JPanel(new GridBagLayout());
		
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
				String newPwd = Ecrypt.EcryptByMD5(String.valueOf(newPassword.getText()));	//新密码
				DoModifyStaffPassword handler = new DoModifyStaffPassword();
				handler.process(staffInfo, newPwd);
				
				if(handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功设置他人密码","成功",owner);
					SetPwd.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("设置他人密码失败","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SetPwd.this.dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
			
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
	private boolean checkData(){
		String newPwd = newPassword.getText();
		if(newPwd.length()==0 || newPwd.length()<6){
			new MessageBox("请输入新密码,大于6位","提示",this);
			return false;
		}
		
		String confirmPwd = confirmPassword.getText();
		if(confirmPwd.length()==0){
			new MessageBox("请重复输入新密码","提示",this);
			return false;
		}
		
		if(newPwd.equals(confirmPwd)){
			new MessageBox("两次输入的密码不一致，请重新输入","提示",this);
			System.out.println("1");
			return false;
		}	
		return true;
	}
	
	@Override
	public boolean isUpdate() {
		return isEdit;
	}
	
	//控件
	private JTextField newPassword = null;
	private JTextField confirmPassword = null;
	
	private boolean isEdit = false;		//记录操作是否成功
	
	//按钮
	private JButton submit = null;
	private JButton cancel = null;
	
	private StaffInfo staffInfo = null;	//销售人员信息
	
	//窗体默认大小
	private final int DEFAULT_WIDTH = 300;
	private final int DEFAULT_HEIGTH = 200;
}
