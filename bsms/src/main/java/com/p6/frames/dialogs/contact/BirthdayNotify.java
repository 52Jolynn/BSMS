package com.p6.frames.dialogs.contact;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.p6.frames.Role;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.NumOnlyDocument;
import com.p6.user.Handler;
import com.p6.user.contact.DoBirthdayNotify;
import com.p6.user.infoBean.StaffInfo;

/**
 * <p>Description: 生日提醒对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class BirthdayNotify extends ContactDialog{

	/**
	 * 
	 * @param owner 父窗体
	 */
	public BirthdayNotify(final JFrame owner){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("设置生日提醒");
		setResizable(false);
		setModal(true);
		
		//将窗体居中显示
		Toolkit tk=Toolkit.getDefaultToolkit();   
		Dimension  d=tk.getScreenSize();   
		int   screenHeight=d.height;   
		int   screenWidth=d.width;   
		this.setLocation((screenWidth-300)/2,(screenHeight-120)/2);

		//设置窗体大小
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGTH);
		
		//判断当前用户的角色
		if (owner instanceof Role) {
			staffInfo = ((Role)owner).getStaffInfo();
		}
		else{
			staffInfo = new StaffInfo();
			staffInfo.setStaffID(1);
		}
		
		//初始化窗体中的控件
		birthNotify = new JTextField(3);
		birthNotify.setDocument(new NumOnlyDocument());
		birthNotify.setText(GetInfo.getBirthNotifyByStaffID(staffInfo.getStaffID()));
		JPanel birthPanel = new JPanel();
		
		//将控件放入面板中
		birthPanel.add(new JLabel("提前"));
		birthPanel.add(birthNotify);
		birthPanel.add(new JLabel("天提醒"));
		
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		//确认按钮
		submit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//检查输入的信息是否有效
				if(checkData()==false)
					return;
				Handler handler = new Handler();
				handler.setDoHandler(new DoBirthdayNotify());
				handler.process(staffInfo);
				
				if (handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功修改生日提醒","成功",owner);
					BirthdayNotify.this.dispose();
				}
				else{
					isEdit = false;			//操作失败
					new MessageBox("修改生日提醒失败,请重新尝试","失败",owner);
				}
			}
			
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				BirthdayNotify.this.dispose();	//关闭窗口
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		this.add(new JLabel(" "),BorderLayout.NORTH);
		this.add(birthPanel, BorderLayout.CENTER);
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
	 * @return 如果有效返回 true，否则返回 false
	 */
	private boolean checkData(){
		String temp = birthNotify.getText();
		int count = 0;
		if(temp.equals("")){
			new MessageBox("请输入生日提醒","提示",this);
			birthNotify.setText("");
			return false;
		}
		else{
			count = new Integer(temp);
			if(count<0||count>=365){
				new MessageBox("输入的生日提醒天数必须小于365","输入的生日提醒提前天数无效",this);
				birthNotify.setText("");
				return false;
			}
		}
		
		staffInfo.setBirthNotify(count);
		return true;
	}
	
	//窗体的默认大小
	private final int DEFAULT_WIDTH = 300;
	private final int DEFAULT_HEIGTH = 120;
	
	private JTextField birthNotify = null; 
	private JButton submit = null;	//确定
	private JButton cancel = null; 	//取消
	
	StaffInfo staffInfo = null;
	private boolean isEdit = false;	//记录用户是否对资料进行了修改
}
