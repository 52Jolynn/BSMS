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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.infoBean.StaffInfo;

/**
 * <p>Description: 查看销售人员对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class ViewStaff extends StaffDialog{

	/**
	 * 初始化
	 * @param owner 父窗体
	 * @param oldStaffInfo	需要显示的销售人员信息
	 */
	public ViewStaff(JFrame owner,StaffInfo oldStaffInfo){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("查看销售人员资料");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setLocationRelativeTo(owner);
		setModal(true);		
		
		tabs = new JTabbedPane(); //选项卡面板
		
		//三个选项卡的内容面板
		final JPanel basicInfo = new JPanel(new GridBagLayout());
		final JPanel contInfo  = new JPanel(new GridBagLayout());
		final JPanel additionalInfo = new JPanel(new GridBagLayout());
		final JPanel buttonPanel = new JPanel();
		
		staffName 	= new JTextField(10);
		staffSex 	= new JTextField(5);
		posName		= new JTextField(10);
		staffBirth	= new JTextField(10);
		staffQuali	= new JTextField(5);
		
		staffName.setEditable(false);
		staffSex.setEditable(false);
		posName.setEditable(false);
		staffBirth.setEditable(false);
		staffBirth.setEditable(false);
		staffQuali.setEditable(false);
		
	
		basicInfo.add(new JLabel("姓名"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffName, new GBC(2, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffSex, new GBC(18, 0, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("职位"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(posName, new GBC(2, 4, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("学历"), new GBC(16, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffQuali, new GBC(18, 4, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffBirth, new GBC(2, 8, 10, 2).setInsets(3));
		
		staffMTel	= new JTextField(10);
		staffOTel	= new JTextField(10);
		staffHTel	= new JTextField(10);
		staffETel	= new JTextField(10);
		staffEMail	= new JTextField(10);
		
		staffMTel.setEditable(false);
		staffOTel.setEditable(false);
		staffHTel.setEditable(false);
		staffETel.setEditable(false);
		staffEMail.setEditable(false);
		
		contInfo.add(new JLabel("手机"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffMTel,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("办公电话"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffOTel,new GBC(18,0,10,2).setInsets(3));
		contInfo.add(new JLabel("家庭电话"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffHTel,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("其他电话"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffETel,new GBC(18,4,10,2).setInsets(3));
		contInfo.add(new JLabel("电子邮件"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffEMail,new GBC(2,8,2,2).setInsets(3));
		
		staffInDate	= new JTextField(10);
		staffOutDate= new JTextField(10);
		staffRemark = new JTextArea(5,27);	
		staffInDate.setEditable(false);
		staffOutDate.setEditable(false);
		staffRemark.setEditable(false);
		
		additionalInfo.add(new JLabel("入职日期"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(staffInDate,new GBC(2,0,6,3).setInsets(5));
		additionalInfo.add(new JLabel("离职日期"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(staffOutDate,new GBC(18,0,6,3).setInsets(5));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(staffRemark),new GBC(2,4,28,3).setInsets(5));
		
		submit = new JButton("确定");
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ViewStaff.this.dispose();
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
		
		staffInfo = GetInfo.getStaffInfo(oldStaffInfo.getStaffID());
		getStaffInfo(staffInfo);
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
		return true;
	}
	
	/**
	 * 将InfoBean中的数据放入窗体对应的控件中
	 */
	private void getStaffInfo(StaffInfo oldStaffInfo){
		staffName.setText(oldStaffInfo.getStaffName());
		staffSex.setText(oldStaffInfo.getStaffSex());
		staffBirth.setText(ParseStringAndDate.date2String(oldStaffInfo.getStaffBirth(),"-"));
		posName.setText(oldStaffInfo.getPosName());
		staffQuali.setText(oldStaffInfo.getStaffQuali());
		staffOTel.setText(oldStaffInfo.getStaffOTel());
		staffMTel.setText(oldStaffInfo.getStaffMTel());
		staffHTel.setText(oldStaffInfo.getStaffHTel());
		staffETel.setText(oldStaffInfo.getStaffETel());
		staffEMail.setText(oldStaffInfo.getStaffEMail());
		staffInDate.setText(ParseStringAndDate.date2String(oldStaffInfo.getStaffInDate(),"-"));
		staffOutDate.setText(ParseStringAndDate.date2String(oldStaffInfo.getStaffOutDate(),"-"));
		staffRemark.setText(oldStaffInfo.getStaffRemark());
	}
	
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField 	staffName;		//姓名
	private JTextField 	staffSex;		//性别
	private JTextField 	staffBirth;		//生日
	private JTextField 	posName;		//职位
	private JTextField 	staffQuali;		//学历
	private JTextField 	staffOTel;		//办公电话
	private JTextField 	staffMTel;		//手机
	private JTextField 	staffHTel;		//家庭电话
	private JTextField 	staffETel;		//其他电话
	private JTextField 	staffEMail;		//电子邮件
	private JTextField 	staffInDate;	//入职日期
	private JTextField 	staffOutDate;	//离职日期
	private JTextArea 	staffRemark;	//备注
	
	private JButton submit = null; //确定

	//窗体默认大小
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 300;
	
	private StaffInfo staffInfo = new StaffInfo();	//销售人员信息
}
