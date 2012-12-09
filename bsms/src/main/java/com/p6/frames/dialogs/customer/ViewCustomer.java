package com.p6.frames.dialogs.customer;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.user.infoBean.CustomerInfo;

/**
 * <p>Description: 查看客户资料对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */

public class ViewCustomer extends CustomerDialog{
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldCustomerInfo	需要查看的客户信息
	 */
	public ViewCustomer(JFrame owner,CustomerInfo oldCustomerInfo){
		//设置对话框属性
		setLayout(new BorderLayout());
		setTitle("查看客户资料");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		tabs = new JTabbedPane(); //选项卡面板
		
		//三个选项卡的内容面板
		final JPanel basicInfo = new JPanel(new GridBagLayout());
		final JPanel contInfo = new JPanel(new GridBagLayout());
		final JPanel additionalInfo = new JPanel(new GridBagLayout());
		final JPanel buttonPanel = new JPanel();
		
		//基本信息面板内容
		customerName 		= new JTextField(10);
		customerIndustry 	= new JTextField(10);
		customerMUnit 		= new JTextField(10);
		customerArea 		= new JTextField(10);
		customerProduct 	= new JTextField(10);
	
		customerName.setEditable(false);
		customerIndustry.setEditable(false);
		customerMUnit.setEditable(false);
		customerArea.setEditable(false);
		customerProduct.setEditable(false);
		
		basicInfo.add(new JLabel("客户名称"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerName,new GBC(2,0,10,2).setInsets(3));
		basicInfo.add(new JLabel("所属行业"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerIndustry,new GBC(18,0,10,2).setInsets(3));
		basicInfo.add(new JLabel("主管单位"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerMUnit,new GBC(2,4,10,2).setInsets(3));
		basicInfo.add(new JLabel("所在地区"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerArea,new GBC(18,4,10,2).setInsets(3));
		basicInfo.add(new JLabel("适用产品"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerProduct,new GBC(2,8,10,2).setInsets(3));
		
		//联系信息面板内容
		customerAddress 	= new JTextField(20);
		customerMailNum 	= new JTextField(20);
		customerWeb 		= new JTextField(20);
		
		customerAddress.setEditable(false);
		customerMailNum.setEditable(false);
		customerWeb.setEditable(false);
		
		contInfo.add(new JLabel("地    址"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerAddress,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("邮    编"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerMailNum,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("网    站"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerWeb,new GBC(2,8,10,2).setInsets(3));
		
		//附加信息面板
		customerRemark 		= new JTextArea(6,15);
		customerRemark.setEditable(false);
		
		//customerRemark.setBorder(new LineBorder(Color.BLACK));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		additionalInfo.add(new JScrollPane(customerRemark),new GBC(2,0,15,6).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		
		//按钮面板
		submit = new JButton("确定");

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ViewCustomer.this.dispose();
			}
		});
		
			
		buttonPanel.add(submit);
		
		tabs = new JTabbedPane();
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系方式", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		getCustomerInfo(oldCustomerInfo);
	}
	

	/**
	 * 激活对话框
	 */
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
		
		return false;
	}
	

	/**
	 * 将InfoBean中的信息放入窗体对应的控件中
	 */
	private void getCustomerInfo(CustomerInfo oldCustomerInfo){
		Vector<Vector<String>> customer = GetInfo.getCustomerByID(oldCustomerInfo.getCustomerID());
		customerName.setText(oldCustomerInfo.getCustomerName());
		customerIndustry.setText(oldCustomerInfo.getCustomerIndustry());
		customerMUnit.setText(oldCustomerInfo.getCustomerMUnit());
		customerArea.setText(oldCustomerInfo.getCustomerArea());
		customerAddress.setText(oldCustomerInfo.getCustomerAddress());
		customerMailNum.setText(oldCustomerInfo.getCustomerMailNum());
		customerWeb.setText(customer.get(0).get(7));
		customerProduct.setText(customer.get(0).get(8));
		customerRemark.setText(customer.get(0).get(9));
	}
	
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField customerName 	= null;		//客户名称
	private JTextField customerIndustry = null;		//所属行业
	private JTextField customerMUnit 	= null;		//主管单位
	private JTextField customerArea 	= null;		//所在地区
	private JTextField customerAddress 	= null;		//地址
	private JTextField customerMailNum 	= null;		//邮编
	private JTextField customerWeb 		= null;		//网站
	private JTextField customerProduct 	= null;		//适用产品
	private JTextArea  customerRemark 	= null;		//备注
	
	private JButton submit = null; //确定

	//窗体默认的大小
	private final int DEFAULT_WIDTH  = 400;
	private final int DEFAULT_HEIGTH = 300;
}
