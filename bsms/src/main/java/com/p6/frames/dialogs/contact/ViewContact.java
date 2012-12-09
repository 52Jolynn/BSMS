package com.p6.frames.dialogs.contact;

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
import com.p6.toolkit.NumOnlyDocument;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.infoBean.ContactInfo;
/**
 * <p>Description: 查看联系人对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class ViewContact extends ContactDialog {
	
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldContactInfo	需要查看的联系人信息
	 */
	public ViewContact(final JFrame owner, ContactInfo oldContactInfo) {
		//设置对话框属性
		setLayout(new BorderLayout());
		setTitle("查看联系人资料");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		tabs = new JTabbedPane(); //选项卡面板
		
		//三个选项卡的内容面板
		JPanel basicInfo = new JPanel(new GridBagLayout());
		JPanel contInfo  = new JPanel(new GridBagLayout());
		JPanel additionalInfo = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel();
		
		//基本信息面板内容
		customerNames  = new JTextField(10);
		contactName = new JTextField(10);
		contactSex  = new JTextField(3);
		contactBirth = new JTextField(10);
		contactDepart= new JTextField(10);
		contactDuty  = new JTextField(10);
		contactTitle = new JTextField(10);
	
		customerNames.setEditable(false);
		contactName.setEditable(false);
		contactSex.setEditable(false);
		contactBirth.setEditable(false);
		contactDepart.setEditable(false);
		contactDuty.setEditable(false);
		contactTitle.setEditable(false);
		
		//为基本信息选项卡面板加入内容
		basicInfo.add(new JLabel("客户"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerNames, new GBC(2, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("姓名"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactName, new GBC(18, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactSex, new GBC(2, 4, 10, 2).setInsets(2).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(16, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactBirth, new GBC(18, 4, 10, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(new JLabel("部门"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactDepart, new GBC(2, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("职位"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactDuty, new GBC(18, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("职称"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactTitle, new GBC(2, 12, 10, 2).setInsets(3));
		
		//联系信息面板内容
		contactMTel = new JTextField(10); 	//手机
		contactFax = new JTextField(10); 	//传真
		contactOTel = new JTextField(10); 	//办公电话
		contactHTel = new JTextField(10); 	//家庭电话
		contactETel = new JTextField(10); 	//其他电话
		contactEMail = new JTextField(10); 	//电子邮件
		
		//设置只能输入数字
		contactMTel.setDocument(new NumOnlyDocument());
		contactFax.setDocument(new NumOnlyDocument());
		contactOTel.setDocument(new NumOnlyDocument());
		contactHTel.setDocument(new NumOnlyDocument());
		contactETel.setDocument(new NumOnlyDocument());	
		
		//设置不可编辑
		contactMTel.setEditable(false);
		contactFax.setEditable(false);
		contactOTel.setEditable(false);
		contactHTel.setEditable(false);
		contactETel.setEditable(false);
		contactEMail.setEditable(false);

		contInfo.add(new JLabel("手机"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactMTel,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("传真"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactFax,new GBC(18,0,10,2).setInsets(3));
		contInfo.add(new JLabel("办公电话"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactOTel,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("家庭电话"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactHTel,new GBC(18,4,10,2).setInsets(3));
		contInfo.add(new JLabel("其他电话"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactETel,new GBC(2,8,2,2).setInsets(3));
		contInfo.add(new JLabel("电子邮件"),new GBC(16,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactEMail,new GBC(18,8,2,2).setInsets(3));
		
		//附加信息面板
		contactChar = new JTextArea(5,10); //性格描述
		contactHobby = new JTextArea(5,10); //爱好描述
		contactRemark = new JTextArea(3,16); //备注
		
		contactChar.setEditable(false);
		contactHobby.setEditable(false);
		contactRemark.setEditable(false);
		
		additionalInfo.add(new JLabel("性格描述"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (contactChar),new GBC(2,0,10,5).setInsets(5));
		additionalInfo.add(new JLabel("爱好描述"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(contactHobby),new GBC(18,0,10,5).setInsets(5));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,6,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (contactRemark),new GBC(2,6,16,3).setInsets(5));
		
		//按钮面板
		submit = new JButton("确定");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				ViewContact.this.dispose();
			}
		});
		
			
		buttonPanel.add(submit);
		
		//将选项卡加入选项卡面板
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系信息", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//加入选项卡面板
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		contact = GetInfo.getContactByID(oldContactInfo.getContactID());
		oldContactInfo.setCustomerID(new Integer(contact.get(0).get(3)));
		//将原来的联系人信息显示到窗口中对应的控件
		getContactInfo(oldContactInfo);
	}
	
	/*
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
	 * 将InfoBean中的信息放入窗口中
	 */
	private void getContactInfo(ContactInfo contactBean)
	{
		customerNames.setText(contactBean.getCustoemrName());
		contactName.setText(contactBean.getContactName());
		contactSex.setText(contactBean.getContactSex());
		contactBirth.setText(ParseStringAndDate.date2String(contactBean.getContactBirth(),"-"));
		contactDepart.setText(contactBean.getContactDepart());
		contactDuty.setText(contactBean.getContactDuty());
		contactTitle.setText(contact.get(0).get(18));
		
		contactHTel.setText(contact.get(0).get(10));
		contactMTel.setText(contact.get(0).get(9));
		contactFax.setText(contact.get(0).get(12));
		contactOTel.setText(contact.get(0).get(8));
		contactETel.setText(contact.get(0).get(11));
		contactEMail.setText(contact.get(0).get(13));
		
		contactChar.setText(contact.get(0).get(14));
		contactHobby.setText(contact.get(0).get(15));
		contactRemark.setText(contact.get(0).get(16));
	}
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField customerNames = null; //客户
	private JTextField contactName = null; //姓名
	private JTextField contactSex = null; //性别
	private JTextField contactBirth = null; //生日
	private JTextField contactDepart = null; //部门
	private JTextField contactDuty = null; //职位
	private JTextField contactTitle = null; //职称
	
	private JTextField contactMTel = null; //手机
	private JTextField contactFax = null; //传真
	private JTextField contactOTel = null; //办公电话
	private JTextField contactHTel = null; //家庭电话
	private JTextField contactETel = null; //其他电话
	private JTextField contactEMail = null; //电子邮件
	
	private JTextArea contactChar = null; //性格描述
	private JTextArea contactHobby = null; //爱好描述
	private JTextArea contactRemark = null; //备注
	
	private JButton submit = null; //确定
	
	//窗体默认的宽和高
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 300;
	
	Vector<Vector<String>> contact = null;		//联系人信息
}
