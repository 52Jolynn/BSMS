package com.p6.frames.dialogs.personal;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.p6.toolkit.GBC;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.NumOnlyDocument;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Handler;
import com.p6.user.infoBean.StaffInfo;
import com.p6.user.staff.DoModifyStaff;

/**
 * <p>Description: 修改个人信息对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xuanqj
 * @version 1.0
 */
public class ModifySelfInfo extends PersonalDialog{
	
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldStaffInfo	需要修改的个人信息
	 */
	public ModifySelfInfo(final JFrame owner,StaffInfo oldStaffInfo){
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
		staffSex		=	new JComboBox(new String[]{"男", "女"});
		staffBirth		=	new JTextField(6);
		staffInDate		=	new JTextField(6);
		staffOutDate	=	new JTextField(6);
		staffQuali		=	new JComboBox(new String[]{"小学", "初中","中专","高中","大专","本科","研究生","硕士","博士","其他"});

		posName			=	new JTextField(10);
		birthChooseButton = new JButton(new ImageIcon(ModifySelfInfo.class.getResource("/images/calendar.gif")));
		
		//设置各个日期选择按钮的事件响应函数
		birthChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				DateChooserDialog dlg = new DateChooserDialog(ModifySelfInfo.this,ModifySelfInfo.this.staffBirth.getText());
				dlg.setVisible(true);
				staffBirth.setText(dlg.getChosenDate("-"));
			}
		});
		inDateChooseButton= new JButton(new ImageIcon(ModifySelfInfo.class.getResource("/images/calendar.gif")));
		inDateChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				DateChooserDialog dlg = new DateChooserDialog(ModifySelfInfo.this,ModifySelfInfo.this.staffInDate.getText());
				dlg.setVisible(true);
				staffInDate.setText(dlg.getChosenDate("-"));
			}
		});
		outDateChooseButton= new JButton(new ImageIcon(ModifySelfInfo.class.getResource("/images/calendar.gif")));
		outDateChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				DateChooserDialog dlg = new DateChooserDialog(ModifySelfInfo.this,ModifySelfInfo.this.staffOutDate.getText());
				dlg.setVisible(true);
				staffOutDate.setText(dlg.getChosenDate("-"));
			}
		});
		
		//添加控件到窗体中
		basicInfo.add(new JLabel("姓名"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffName, new GBC(2, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffSex, new GBC(18, 0, 10, 2).setInsets(2).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffBirth, new GBC(2, 4, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(birthChooseButton, new GBC(8, 4, 1, 2).setInsets(1).setIPad(-18, -8));
		basicInfo.add(new JLabel("职位"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(posName, new GBC(2, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("学历"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffQuali, new GBC(18, 8, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("入职时间"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffInDate, new GBC(2, 12, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(inDateChooseButton, new GBC(8, 12, 1, 2).setInsets(1).setIPad(-18, -8));
		basicInfo.add(new JLabel("离职时间"), new GBC(16, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffOutDate, new GBC(18, 12, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(outDateChooseButton, new GBC(24, 12, 1, 2).setInsets(1).setIPad(-18, -8));	
		
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
		
		additionalInfo.add(new JLabel("备        注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (staffRemark),new GBC(2,0,16,5).setInsets(5));
		//以上是备注信息
		//以下是按钮信息
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				setStaffInfo();
				Handler handler = new Handler();
				handler.setDoHandler(new DoModifyStaff());
				handler.process(staffInfo);
				
				if (handler.isSucced()){
					ModifySelfInfo.this.isEdit = true;
					new MessageBox("成功修改个人信息","成功",owner);
					ModifySelfInfo.this.dispose();
				}
				else{
					ModifySelfInfo.this.isEdit = false;
					new MessageBox("修改客户信息失败，请重新尝试","失败",owner);
				}
			}
		});
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				ModifySelfInfo.this.isEdit = false;
				ModifySelfInfo.this.dispose();
			}
			
		});		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		//以下为控制面板信息
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系信息", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		getStaffInfo(oldStaffInfo);
	}
	
	@Override
	public void create() {
		setVisible(true);
		
	}

	@Override
	public JDialog getThis() {
		return this;
	}
	
	/**
	 * 将窗体中的数据放入InfoBean中
	 */
	private void setStaffInfo() {
		staffInfo.setStaffName(staffName.getText());
		staffInfo.setStaffSex((String)staffSex.getSelectedItem());
		staffInfo.setPosName(posName.getText());
		staffInfo.setStaffQuali((String)staffQuali.getSelectedItem());
		staffInfo.setStaffBirth(ParseStringAndDate.parseString2Date( staffBirth.getText(),"-"));
		staffInfo.setStaffInDate(ParseStringAndDate.parseString2Date( staffBirth.getText(),"-"));
		staffInfo.setStaffOutDate(ParseStringAndDate.parseString2Date( staffBirth.getText(),"-"));
		
		staffInfo.setStaffMTel(staffMTel.getText());
		staffInfo.setStaffOTel(staffOTel.getText());
		staffInfo.setStaffHTel(staffHTel.getText());
		staffInfo.setStaffETel(staffETel.getText());
		staffInfo.setStaffEMail(staffEmail.getText());
		
		staffInfo.setStaffRemark(staffRemark.getText());
	}
	
	/**
	 * 将原来的个人信息显示到窗体中
	 * @param staffBean 原来的个人信息
	 */
	private void getStaffInfo(StaffInfo staffBean)
	{
		staffName.setText(staffBean.getStaffName());
		staffSex.setSelectedItem(staffBean.getStaffSex());
		posName.setText(staffBean.getPosName());
		staffQuali.setSelectedItem(staffBean.getStaffQuali());
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
	public boolean isUpdate() {
		return true;
	}
	
	private JTabbedPane tabs = null;
	
	private JTextField staffName = null;
	private JComboBox  staffSex  = null;
	private JTextField staffBirth = null;
	private JComboBox staffQuali  = null;
	private JTextField staffOTel = null;
	private JTextField staffMTel = null;
	private JTextField staffHTel = null;
	private JTextField staffETel = null;
	private JTextField staffInDate = null;
	private JTextField staffOutDate = null;
	private JTextField staffEmail = null;
	private JTextField posName =null;
	private JTextArea staffRemark = null; 
	
	private JButton birthChooseButton = null;
	private JButton inDateChooseButton = null;
	private JButton outDateChooseButton = null;
	
	//按钮
	private JButton submit = null; 
	private JButton cancel = null;
	
	private boolean isEdit = false;	//记录操作是否成功
	
	private StaffInfo staffInfo = new StaffInfo();
	
	//窗体默认大小
	private final int DEFAULT_WIDTH  = 400;
	private final int DEFAULT_HEIGTH = 300;
}
