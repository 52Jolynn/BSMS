package com.p6.frames.dialogs.customer;

import com.p6.toolkit.CnToSpell;
import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.user.Handler;
import com.p6.user.customer.DoModifyCustomer;
import com.p6.user.infoBean.CustomerInfo;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

/**
 * <p>Description: 修改客户资料对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */

public class ModifyCustomer extends CustomerDialog{
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldCustomerInfo	需要修改的客户信息
	 */
	public ModifyCustomer(final JFrame owner,CustomerInfo oldCustomerInfo){
		//设置对话框属性
		setLayout(new BorderLayout());
		setTitle("修改客户资料");
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
		
		contInfo.add(new JLabel("地    址"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerAddress,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("邮    编"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerMailNum,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("网    站"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(customerWeb,new GBC(2,8,10,2).setInsets(3));
		
		//附加信息面板
		customerRemark 		= new JTextArea(6,15);
		//customerRemark.setBorder(new LineBorder(Color.BLACK));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		additionalInfo.add(new JScrollPane(customerRemark),new GBC(2,0,15,6).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		
		//按钮面板
		submit = new JButton("确定");
		cancel = new JButton("取消");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				//判断用户输入是否有效，若有效将信息放入InfoBean
				if(setCustomerInfo()==false)
					return;
				Handler handler = new Handler();
				handler.setDoHandler(new DoModifyCustomer());
				handler.process(customerInfo);
				if(handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功修改客户信息","成功",owner);
					ModifyCustomer.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("修改客户信息失败","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				isEdit = false;
				ModifyCustomer.this.dispose();
			}
		});		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		//将控件放入面板中
		tabs = new JTabbedPane();
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系方式", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//将面板放入窗体中
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		//将信息显示到窗体中
		getCustomerInfo(oldCustomerInfo);
		customerInfo = oldCustomerInfo;
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
		
		return isEdit;
	}
	
	/**
	 * 检查用户输入是否有效
	 * @return	若有效返回true，否则返回false
	 */
	public boolean checkData(){
		if((customerName.getText()).equals("")){
			new MessageBox("请输入客户名称","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		return true;
	}
	
	/**
	 * 将窗口内信息存入InfoBean中
	 */
	private boolean setCustomerInfo() {
		if(checkData()==false)
			return false;
		customerInfo.setCustomerName(customerName.getText());
		customerInfo.setCustomerIndustry(customerIndustry.getText());
		customerInfo.setCustomerMUnit(customerMUnit.getText());
		customerInfo.setCustomerArea(customerArea.getText());
		customerInfo.setCustomerAddress(customerAddress.getText());
		customerInfo.setCustomerMailNum(customerMailNum.getText());
		customerInfo.setCustomerWeb(customerWeb.getText());
		customerInfo.setCustomerProduct(customerProduct.getText());
		customerInfo.setCustomerRemark(customerRemark.getText());
		customerInfo.setSiftKey(CnToSpell.getFullSpell(customerName.getText()));
		return true;
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
	
	private JButton submit = null; 		//确定
	private JButton cancel = null; 		//取消
	private boolean isEdit = false;		//记录操作是否成功
	private CustomerInfo customerInfo = new CustomerInfo();	//客户信息
	
	//窗体默认的大小
	private final int DEFAULT_WIDTH  = 400;
	private final int DEFAULT_HEIGTH = 300;
}
