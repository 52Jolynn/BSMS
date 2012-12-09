package com.p6.toolkit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author xiaogy
 * @version 1.0
 * 注意：如果用户直接关闭对话框，无论按钮中是否存在CANCEL，返回值都为ID_CANCEL,
 */
public class MessageBox extends JDialog {

	//消息框的类型常量
	public final static short TYPE_OK = 0;		//只有一个确定按钮
	public final static short TYPE_YES_NO = 1;	//是否
	public final static short TYPE_OK_CANCEL = 2; //确定 取消
	public final static short TYPE_YES_NO_CANCEL = 3;
	
	public final static short ID_OK = 1;
	public final static short ID_YES = 2;
	public final static short ID_NO = 3;
	public final static short ID_CANCEL = 0;
	
	/**
	 * @param message 显示的信息
	 * @param title 消息框的标题
	 * @param type 消息框的种类  
	 * @param owner 父窗口，用于定位
	 * @param canClose 消息框是否可以关闭
	 */
	public MessageBox(String message, String title, short type, Component owner, boolean canClose)
	{
		this.setTitle(title);
		this.setModal(true);
		
		Toolkit tk=Toolkit.getDefaultToolkit();   
		Dimension  d=tk.getScreenSize();   
		int   screenHeight=d.height;   
		int   screenWidth=d.width;   
		this.setLocation((screenWidth-300)/2,(screenHeight-120)/2);

		this.setLayout(new BorderLayout());
		this.setSize(300, 120);
		if(canClose != true)
			this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		boxType = type;
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		this.add(messagePanel,BorderLayout.CENTER);
		
		switch (boxType) {
		case TYPE_OK:
			firstButton = new JButton("确定");
			break;
		case TYPE_YES_NO:
		case TYPE_YES_NO_CANCEL:
			firstButton = new JButton("是");
			secondButton = new JButton("否");
			break;
		case TYPE_OK_CANCEL:
			firstButton = new JButton("确定");
			secondButton = new JButton("取消");
			break;
			
		default:
			return;
		}
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		if(firstButton != null){
			buttonPanel.add(firstButton, new GBC(0,0,1,1).setInsets(5).setAnchor(GBC.EAST));
			
			firstButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
		
					if(boxType ==TYPE_OK||boxType==TYPE_OK_CANCEL)
						choice = ID_OK;
					else
						choice = ID_YES;
					System.out.println("first");
					MessageBox.this.dispose();
				}
			});
		}
		if(secondButton != null){
			buttonPanel.add(secondButton,new GBC(1,0,1,1).setInsets(5).setAnchor(GBC.WEST));
			secondButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					
					if(boxType ==TYPE_YES_NO_CANCEL || boxType ==TYPE_YES_NO)
						choice = ID_NO;
					else
						choice = ID_CANCEL;
					MessageBox.this.dispose();
				}
			});
		}
		if(type == TYPE_YES_NO_CANCEL){
			JButton cancelButton = new JButton("取消");
			buttonPanel.add(cancelButton,new GBC(2,0,1,1).setInsets(5).setAnchor(GBC.WEST));
			cancelButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					
					choice = ID_CANCEL;
					MessageBox.this.dispose();
				}
			});
		}
		this.add(buttonPanel,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	/**
	 * 创建一个默认的消息框，只有一个确定按钮
	 * @param message 显示的消息
	 * @param title 标题
	 * @param owner 父窗口
	 */
	public MessageBox(String message, String title,Component owner)
	{
		this.setTitle(title);
		this.setModal(true);

		this.setLayout(new BorderLayout());
		this.setSize(300, 120);

		Toolkit tk=Toolkit.getDefaultToolkit();   
		Dimension  d=tk.getScreenSize();   
		int   screenHeight=d.height;   
		int   screenWidth=d.width;   
		this.setLocation((screenWidth-300)/2,(screenHeight-120)/2);

		
		boxType = TYPE_OK;
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		this.add(messagePanel,BorderLayout.CENTER);
		
		firstButton = new JButton("确定");
		choice = ID_OK;
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		if(firstButton != null){
			buttonPanel.add(firstButton, new GBC(0,0,1,1).setInsets(5).setAnchor(GBC.EAST));
			
			firstButton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					MessageBox.this.dispose();
				}
			});
		}
		this.add(buttonPanel,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	/**
	 * 
	 * @return 用户的选择 ID_OK; ID_YES; ID_NO; ID_CANCEL
	 */
	public short getChoice()
	{
		return choice;
	}
	
	private short choice = 0;	//用户的选择
	private JButton firstButton = null;
	private JButton secondButton = null;
	
	private short boxType = 0;	//消息框的类型
}
