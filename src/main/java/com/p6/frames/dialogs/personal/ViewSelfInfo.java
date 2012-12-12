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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.p6.toolkit.GBC;
import com.p6.toolkit.NumOnlyDocument;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.infoBean.StaffInfo;

/**
 * <p>Description: 查看个人信息对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xuanqj
 * @version 1.0
 */
public class ViewSelfInfo extends PersonalDialog{
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldStaffInfo	需要查看的个人信息
	 */
	public ViewSelfInfo(JFrame owner,StaffInfo oldStaffInfo){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("修改个人信息");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		tabs = new JTabbedPane();
		//以下为基本信息
		final JPanel basicInfo = new JPanel(new GridBagLayout());
		final JPanel contInfo = new JPanel(new GridBagLayout());
		final JPanel additionalInfo = new JPanel(new GridBagLayout());
		final JPanel buttonPanel = new JPanel();
		
		staffName       =	new JTextField(10);
		staffSex		=	new JTextField(6);
		
		staffBirth		=	new JTextField(6);
		staffInDate		=	new JTextField(6);
		staffOutDate	=	new JTextField(6);
		staffQuali		=	new JTextField(6);

		posName			=	new JTextField(10);
		
		
		//定义不可写入操作
		staffName.setEditable(false);
		staffSex.setEditable(false);
		staffBirth.setEditable(false);
		staffInDate.setEditable(false);
		staffOutDate.setEditable(false);
		posName.setEditable(false);
		staffQuali.setEditable(false);
		
		basicInfo.add(new JLabel("姓名"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffName, new GBC(2, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffSex, new GBC(18, 0, 10, 2).setInsets(2).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffBirth, new GBC(2, 4, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));

		basicInfo.add(new JLabel("职位"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(posName, new GBC(2, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("学历"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffQuali, new GBC(18, 8, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("入职时间"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffInDate, new GBC(2, 12, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));

		basicInfo.add(new JLabel("离职时间"), new GBC(16, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffOutDate, new GBC(18, 12, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
	
		//以上为基本信息
		//以下为联系信息
		staffOTel		=	new JTextField(10);
		staffMTel		=	new JTextField(10);
		staffHTel		=	new JTextField(10);
		staffETel		=	new JTextField(10);
		staffEmail		=	new JTextField(10);
		staffMTel.setDocument(new NumOnlyDocument());
		staffOTel.setDocument(new NumOnlyDocument());
		staffHTel.setDocument(new NumOnlyDocument());
		staffETel.setDocument(new NumOnlyDocument());
		
		staffOTel.setEditable(false);
		staffMTel.setEditable(false);
		staffHTel.setEditable(false);
		staffETel.setEditable(false);
		staffEmail.setEditable(false);
		
		
		contInfo.add(new JLabel("手机"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffMTel,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("办公电话"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffOTel,new GBC(18,0,10,2).setInsets(3));
		contInfo.add(new JLabel("家庭电话"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffHTel,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("其他电话"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffETel,new GBC(18,4,10,2).setInsets(3));
		contInfo.add(new JLabel("电子邮件"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(staffEmail,new GBC(2,8,2,2).setInsets(3));
		//以上是联系信息
		//以下是备注信息
		
		staffRemark			= new JTextArea(5,16);
		staffRemark.setEditable(false);
		additionalInfo.add(new JLabel("备        注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (staffRemark),new GBC(2,0,16,5).setInsets(5));
		//以上是备注信息
		//以下是按钮信息
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
			
				ViewSelfInfo.this.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
			
				ViewSelfInfo.this.isEdit = false;
				ViewSelfInfo.this.dispose();
			}
			
		});		
		buttonPanel.add(submit);
		//以下为控制面板信息
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系信息", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		//将信息显示到窗体中
		getStaffInfo(oldStaffInfo);
	}
	
	/**
	 * 将需要查看的信息显示到窗体中
	 * @param staffBean 需要查看的信息
	 */
	private void getStaffInfo(StaffInfo staffBean)
	{
		staffName.setText(staffBean.getStaffName());
		staffSex.setText(staffBean.getStaffSex());
		posName.setText(staffBean.getPosName());
		staffQuali.setText(staffBean.getStaffQuali());
		staffBirth.setText(ParseStringAndDate.date2String(staffBean.getStaffBirth(),"-"));
		staffInDate.setText(ParseStringAndDate.date2String(staffBean.getStaffInDate(),"-"));
		staffOutDate.setText(ParseStringAndDate.date2String(staffBean.getStaffOutDate(),"-"));
		
		staffMTel.setText(staffBean.getStaffMTel());
		staffMTel.setText(staffBean.getStaffHTel());
		staffOTel.setText(staffBean.getStaffOTel());
		staffETel.setText(staffBean.getStaffETel());
		staffEmail.setText(staffBean.getStaffEMail());
		
		staffRemark.setText(staffBean.getStaffRemark());
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
	
	private JTabbedPane tabs = null;
	
	private JTextField staffName = null;		//姓名
	private JTextField  staffSex  = null;		//性别
	
	private JTextField staffBirth = null;		//生日
	private JTextField staffQuali  = null;		//学历
	private JTextField staffOTel = null;		//办公电话
	private JTextField staffMTel = null;		//移动电话
	private JTextField staffHTel = null;		//家庭电话
	private JTextField staffETel = null;		//其他电话
	private JTextField staffInDate = null;		//入职日期
	private JTextField staffOutDate = null;		//离职日期
	private JTextField staffEmail = null;		//电子邮件
	
	private JTextField posName =null;			//职位
	private JTextArea staffRemark = null; 		//备注
	
	//按钮
	private JButton submit = null; 
	private JButton cancel = null;
	
	private boolean isEdit = false;		//记录操作是否成功 
	
	//窗体的默认大小
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 300;	
}
