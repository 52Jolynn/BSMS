/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */

package com.p6.frames.dialogs.other;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.p6.toolkit.Ecrypt;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.infoBean.LogInfo;
import com.p6.user.others.DoModifyPassword;

public class ModifyPassword extends OtherDialog {
	
	/**
	 * 构造函数
	 * @see com.p6.frames.dialogs.other.OtherDialog#create()
	 */
	public ModifyPassword(JFrame owner) {
		//设置窗体属性
		setModal(true);
		setTitle("修改密码");
		setSize(320, 200);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		//设置布局
		Box content = Box.createVerticalBox();
		Box user = Box.createHorizontalBox();
		Box oldPassword = Box.createHorizontalBox();
		Box newPassword = Box.createHorizontalBox();
		Box confirmPassword = Box.createHorizontalBox();
		Box operation = Box.createHorizontalBox();
		
		//添加控件
		add(content);
		content.add(Box.createVerticalStrut(10));
		content.add(user);
		content.add(Box.createVerticalStrut(5));
		content.add(oldPassword);
		content.add(Box.createVerticalStrut(5));
		content.add(newPassword);
		content.add(Box.createVerticalStrut(5));
		content.add(confirmPassword);
		content.add(Box.createVerticalStrut(10));
		content.add(operation);
		content.add(Box.createVerticalStrut(20));
		
		//标签
		JLabel userLabel = new JLabel("用户名");
		JLabel opLabel = new JLabel("旧密码");
		JLabel npLabel = new JLabel("新密码");
		JLabel cpLabel = new JLabel("确认密码");
		userValid = new JLabel("用户名不能为空");
		userValid.setForeground(color);
		passwordValid = new JLabel("旧密码不能为空");
		passwordValid.setForeground(color);
		
		valid = new JLabel("两次密码不一致");
		valid.setForeground(color);
		
		//设置字体
		userLabel.setFont(font);
		opLabel.setFont(font);
		npLabel.setFont(font);
		cpLabel.setFont(font);
		userValid.setFont(font);
		passwordValid.setFont(font);
		valid.setFont(font);
		
		//文本框
		username = new JTextField();
		username.requestFocus();
		op = new JPasswordField(); //旧密码
		np = new JPasswordField(); //新密码
		cp = new JPasswordField(); //确认新密码
		
		//按钮
		submit = new JButton("确定");
		reset = new JButton("重置");
		cancle = new JButton("取消");
		submit.setFont(font);
		reset.setFont(font);
		cancle.setFont(font);
		
		//用户名称
		user.add(Box.createHorizontalStrut(10));
		user.add(userLabel);
		user.add(Box.createHorizontalStrut(25));
		user.add(username);
		user.add(Box.createHorizontalStrut(10));
		user.add(userValid);
		user.add(Box.createHorizontalStrut(25));
		
		//旧密码
		oldPassword.add(Box.createHorizontalStrut(10));
		oldPassword.add(opLabel);
		oldPassword.add(Box.createHorizontalStrut(25));
		oldPassword.add(op);
		oldPassword.add(Box.createHorizontalStrut(10));
		oldPassword.add(passwordValid);
		oldPassword.add(Box.createHorizontalStrut(25));
		
		//新密码
		newPassword.add(Box.createHorizontalStrut(10));
		newPassword.add(npLabel);
		newPassword.add(Box.createHorizontalStrut(25));
		newPassword.add(np);
		newPassword.add(Box.createHorizontalStrut(118));
		
		//确认密码
		confirmPassword.add(Box.createHorizontalStrut(10));
		confirmPassword.add(cpLabel);
		confirmPassword.add(Box.createHorizontalStrut(12));
		confirmPassword.add(cp);
		confirmPassword.add(Box.createHorizontalStrut(10));
		confirmPassword.add(valid);
		confirmPassword.add(Box.createHorizontalStrut(24));
		
		//确定 重置 取消按钮
		operation.add(Box.createHorizontalStrut(10));
		operation.add(submit);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(reset);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(cancle);
		operation.add(Box.createHorizontalStrut(120));
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				ModifyPassword.this.dispose();
			}
			
		});
		
		//用户名不能为空
		username.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (username.getText().equals("")) {
					setUserValid(Color.red);
				}
				else {
					setUserValid(color);
				}
				super.keyTyped(e);
			}
			
		});
		
		//旧密码不能为空
		op.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (String.valueOf(op.getPassword()).equals("")) {
					setPasswordVaild(Color.red);
				}
				else {
					setPasswordVaild(color);
				}
				super.keyTyped(e);
			}
			
		});
		
		op.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (username.getText().equals("")) {
					setUserValid(Color.red);
				}
				super.focusGained(e);
			}
			
		});
		
		np.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (op.getPassword().length == 0) {
					setPasswordVaild(Color.red);
				}
				super.focusGained(e);
			}
			
		});
		
		//确定按钮事件
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				if (username.getText().equals("")) {
					setUserValid(Color.red);
					username.requestFocus();
					valid = false;
				}
				if (op.getPassword().length == 0) {
					setPasswordVaild(Color.red);
					op.requestFocus();
					valid = false;
				}
				if (!String.valueOf(np.getPassword()).equals(String.valueOf(cp.getPassword()))) {
					setValid(Color.red);
					np.requestFocus();
					valid = false;
				}
				if (valid) {
					logInfo = GetInfo.getLogInfo(username.getText());
					String password = Ecrypt.EcryptByMD5(String.valueOf(op.getPassword()));
					if (logInfo == null || !password.equals(logInfo.getUserPassWord())) {
						new MessageBox("您的用户名/密码不正确, 请重新输入.", "提示", ModifyPassword.this);
						clean();
						setUserValid(color);
						setPasswordVaild(color);
						setValid(color);
					}
					else {
						logInfo.setUserID(username.getText());
						logInfo.setUserPassWord(String.valueOf(np.getPassword()));
						Handler handler = new Handler();
						handler.setDoHandler(new DoModifyPassword());
						handler.process(logInfo);

						if (handler.isSucced()) {
							new MessageBox("密码修改成功!", "提示", ModifyPassword.this);
							ModifyPassword.this.dispose();
						}
						else {
							new MessageBox("密码修改失败, 请检查网络或数据库连接情况!", "提示", ModifyPassword.this);
							clean();
							setUserValid(color);
							setPasswordVaild(color);
							setValid(color);
						}
					}
				}
			}
			
		});
		
		//重置按钮事件
		reset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clean();
				setUserValid(color);
				setPasswordVaild(color);
				setValid(color);
			}
			
		});
		
		//取消按钮事件
		cancle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ModifyPassword.this.dispose();
				
			}
			
		});
		
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
		return isSucced;
	}
	
	/**
	 * 设置userValid是否可见
	 */
	public void setUserValid(Color color) {
		this.userValid.setForeground(color);
	}
	
	/**
	 * 设置passwordValid是否可见
	 */
	public void setPasswordVaild(Color color) {
		this.passwordValid.setForeground(color);
	}
	
	/**
	 * 设置valid是否可见
	 */
	public void setValid(Color color) {
		this.valid.setForeground(color);
	}
	
	/**
	 * 清除界面文字
	 */
	public void clean() {
		username.setText("");
		username.requestFocus();
		op.setText("");
		np.setText("");
		cp.setText("");
	}
	
	private JTextField username = null; //用户名
	private JPasswordField op = null; //旧密码
	private JPasswordField np = null; //新密码
	private JPasswordField cp = null; //确认密码
	private JLabel userValid = null; //用户名不能为空
	private JLabel passwordValid = null; //密码不能为空
	private JLabel valid = null; //两次密码不一致
	private JButton submit = null; //确定
	private JButton reset = null; //重置
	private JButton cancle = null; //取消
	
	private Color color = new Color(238, 238, 238); //错误信息初始颜色
	private boolean isSucced = false; //修改密码操作是否成功
	private LogInfo logInfo = new LogInfo(); //登陆信息InfoBean
	private Font font = new Font("宋体", Font.PLAIN, 12);

	public static void main(String[] args) {
		(new ModifyPassword(null)).create();
	}
}
