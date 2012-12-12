package com.p6.frames.dialogs.staff;

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

import com.p6.frames.Role;
import com.p6.toolkit.CheckTools;
import com.p6.toolkit.CnToSpell;
import com.p6.toolkit.Ecrypt;
import com.p6.toolkit.GBC;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.infoBean.LogInfo;
import com.p6.user.infoBean.StaffInfo;
import com.p6.user.staff.DoAddStaff;

/**
 * <p>Description: 添加销售人员对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class AddStaff extends StaffDialog{
	
	/**
	 * 初始化
	 * @param owner 父窗体
	 */
	public AddStaff(final JFrame owner){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("新增销售人员");
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
		staffSex 	= new JComboBox(new String[]{"男", "女"});
		
		//根据用户职位提供不同的选择
		if (owner instanceof Role) {
			StaffInfo staffInfo = ((Role)owner).getStaffInfo();
			if(staffInfo.getPosName().equals("部门经理")){
				posName		= new JComboBox(new String[]{"销售助理", "销售经理"});
			}
			else if(staffInfo.getPosName().equals("总经理")){
				posName		= new JComboBox(new String[]{"部门经理"});
			}
			else{
				new MessageBox("你是"+staffInfo.getPosName()+"没有权限新增","Error",this);
				this.dispose();
			}
		}

		staffBirth	= new JTextField(6);
		staffQuali	= new JComboBox(new String[]{"小学", "中学","中专","高中","大专","本科","研究生","硕士","博士","其他"});
		logName = new JTextField(6);
		staffBirth.setEditable(false);
		
		birthChooseButton = new JButton(new ImageIcon(AddStaff.class.getResource("/images/calendar.gif")));
		birthChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddStaff.this,staffBirth.getText());
				dlg.setVisible(true);
				staffBirth.setText(dlg.getChosenDate("-"));
			}
		}); 
		basicInfo.add(new JLabel("姓名"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffName, new GBC(2, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffSex, new GBC(18, 0, 10, 2).setInsets(3).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("职位"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(posName, new GBC(2, 4, 10, 2).setInsets(3).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("学历"), new GBC(16, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffQuali, new GBC(18, 4, 10, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffBirth, new GBC(2, 8, 6, 2).setInsets(3));
		basicInfo.add(birthChooseButton, new GBC(8, 8, 1, 2).setInsets(1).setIPad(-18, -8));
		basicInfo.add(new JLabel("登录名"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(logName, new GBC(2, 12, 6, 2).setInsets(3));
		
		staffMTel	= new JTextField(10);
		staffOTel	= new JTextField(10);
		staffHTel	= new JTextField(10);
		staffETel	= new JTextField(10);
		staffEMail	= new JTextField(10);
		
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
		
		staffInDate	= new JTextField(6);
		staffOutDate= new JTextField(6);
		staffRemark = new JTextArea(5,27);	
		staffInDate.setEditable(false);
		staffOutDate.setEditable(false);
		inDateChooseButton = new JButton(new ImageIcon(AddStaff.class.getResource("/images/calendar.gif")));
		inDateChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddStaff.this,staffInDate.getText());
				dlg.setVisible(true);
				staffInDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		outDateChooseButton = new JButton(new ImageIcon(AddStaff.class.getResource("/images/calendar.gif")));
		outDateChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddStaff.this,staffOutDate.getText());
				dlg.setVisible(true);
				staffOutDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		
		additionalInfo.add(new JLabel("入职日期"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(staffInDate,new GBC(2,0,6,3).setInsets(5));
		additionalInfo.add(inDateChooseButton,new GBC(8,0,1,3).setInsets(5).setInsets(1).setIPad(-18, -8));
		additionalInfo.add(new JLabel("离职日期"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(staffOutDate,new GBC(18,0,6,3).setInsets(5));
		additionalInfo.add(outDateChooseButton,new GBC(24,0,1,3).setInsets(5).setInsets(1).setIPad(-18, -8));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(staffRemark),new GBC(2,4,28,3).setInsets(5));
		
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				if(setStaffInfo() == false)
					return;
				DoAddStaff handler = new DoAddStaff();
				handler.process(staffInfo,logInfo);
				if(handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功添加销售人员,默认登陆密码为“123456”","成功",owner);
					AddStaff.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("添加销售人员失败，请重新尝试","失败",owner);
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				isEdit = false;
				AddStaff.this.dispose();
			}
			
		});		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		//将选项卡加入选项卡面板
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系信息", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//加入选项卡面板
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);		
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
	 * 检查用户输入的数据是否合法
	 * @return true 如果合法，否则false
	 */
	private boolean checkDate(){
		if(staffName.getText().equals("")){
			new MessageBox("请输入销售人员姓名","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(!staffEMail.getText().equals("") && !CheckTools.isEmailValid(staffEMail.getText())){
			new MessageBox("请输入销售人员姓名","提示",this);
			tabs.setSelectedIndex(1);
			return false;
		}
		if(logName.getText().trim().equals("")){
			new MessageBox("请输入销售人员登录用户名","提示",this);
			tabs.setSelectedIndex(1);
			return false;
		}
		return true;
	}
	
	/**
	 * 将窗体中的数据放入InfoBean中
	 */
	private boolean setStaffInfo()
	{
		if(checkDate()==false)
			return false;
		staffInfo.setStaffName(staffName.getText());		//姓名
		staffInfo.setStaffSex((String)staffSex.getSelectedItem());		//性别
		staffInfo.setStaffBirth(ParseStringAndDate.parseString2Date(staffBirth.getText(),"-"));		//生日
		staffInfo.setPosName((String)posName.getSelectedItem());		//职位
		staffInfo.setStaffQuali((String)staffQuali.getSelectedItem());		//学历
		staffInfo.setStaffOTel(staffOTel.getText());		//办公电话
		staffInfo.setStaffMTel(staffMTel.getText());		//手机
		staffInfo.setStaffHTel(staffHTel.getText());		//家庭电话
		staffInfo.setStaffETel(staffETel.getText());		//其他电话
		staffInfo.setStaffEMail(staffEMail.getText());		//电子邮件
		staffInfo.setStaffInDate(ParseStringAndDate.parseString2Date(staffInDate.getText(),"-"));	//入职日期
		staffInfo.setStaffOutDate(ParseStringAndDate.parseString2Date(staffInDate.getText(),"-"));	//离职日期
		staffInfo.setStaffRemark(staffRemark.getText());	//备注
		staffInfo.setSiftKey(CnToSpell.getFullSpell(staffName.getText()));
		
		logInfo.setUserID(logName.getText().trim());
		logInfo.setUserPassWord(Ecrypt.EcryptByMD5("123456"));	//默认密码
		logInfo.setStaffTitle(staffInfo.getPosName());
		logInfo.setUserBirthNotifyConfig(30);
		return true;
	}
	
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField 	staffName;		//姓名
	private JComboBox 	staffSex;		//性别
	private JTextField 	staffBirth;		//生日
	private JComboBox 	posName;		//职位
	private JComboBox 	staffQuali;		//学历
	private JTextField 	staffOTel;		//办公电话
	private JTextField 	staffMTel;		//手机
	private JTextField 	staffHTel;		//家庭电话
	private JTextField 	staffETel;		//其他电话
	private JTextField 	staffEMail;		//电子邮件
	private JTextField 	staffInDate;	//入职日期
	private JTextField 	staffOutDate;	//离职日期
	private JTextArea 	staffRemark;	//备注
	
	private JTextField	logName;		//登录的用户名
	
	private JButton submit = null; //确定
	private JButton cancel = null; //取消
	
	//日期选择按钮
	private JButton birthChooseButton = null;
	private JButton inDateChooseButton = null;
	private JButton outDateChooseButton = null;
	
	//窗体默认大小
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 300;
	
	private boolean isEdit = false;		//记录操作是否成功
	private StaffInfo staffInfo = new StaffInfo();	//销售人员信息
	private LogInfo logInfo = new LogInfo(); 		//登录信息
}
