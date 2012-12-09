package com.p6.frames.dialogs.project;

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
import com.p6.user.infoBean.ProjectInfo;

/**
 * <p>Description: 查看项目对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class ViewProject extends ProjectDialog{
	
	/**
	 * 
	 * @param owner 父窗体
	 * @param oldProjectInfo	需要显示的项目信息
	 */
	public ViewProject(JFrame owner, ProjectInfo oldProjectInfo){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("查看项目资料");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		tabs = new JTabbedPane(); //选项卡面板
		
		//三个选项卡的内容面板
		final JPanel basicInfo = new JPanel(new GridBagLayout());
		final JPanel projectDetialInfo = new JPanel(new GridBagLayout());
		final JPanel additionalInfo = new JPanel(new GridBagLayout());
		final JPanel buttonPanel = new JPanel();
		
		//基本信息面板内容
		projectName = new JTextField(10);
		customerNames = new JTextField(10);
		contactNames = new JTextField(10);
		staffNames 	= new JTextField(10);
		
		projectName.setEditable(false);
		customerNames.setEditable(false);
		contactNames.setEditable(false);
		staffNames.setEditable(false);
		
		basicInfo.add(new JLabel("项目名称"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(projectName,new GBC(2,0,10,2).setInsets(3));
		basicInfo.add(new JLabel("客        户"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerNames,new GBC(18,0,10,2).setInsets(3));
		basicInfo.add(new JLabel("联  系  人"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactNames,new GBC(2,4,10,2).setInsets(3));
		basicInfo.add(new JLabel("销售人员"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffNames,new GBC(18,4,10,2).setInsets(3));
		
		projectInterval = new JTextField(10);
		projectNotify 	= new JTextField(10);
		projectState  	= new JTextField(10);
		projectCreateDate = new JTextField(10);
		projectSignDate = new JTextField(10);
		projectEndDate  = new JTextField(10);
		projectContent 	= new JTextArea(5,26);
		
		projectInterval.setEditable(false);
		projectNotify.setEditable(false);
		projectState.setEditable(false);
		projectCreateDate.setEditable(false);
		projectSignDate.setEditable(false);
		projectEndDate.setEditable(false);
		projectContent.setEditable(false);
		
		projectDetialInfo.add(new JLabel("联系频度"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectInterval,new GBC(2,0,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("项目提醒"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectNotify,new GBC(18,0,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("项目状态"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectState,new GBC(2,4,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("创建日期"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectCreateDate,new GBC(18,4,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("签约日期"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectSignDate,new GBC(2,8,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("终止日期"),new GBC(16,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectEndDate,new GBC(18,8,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("项目内容"),new GBC(0,12,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(new JScrollPane(projectContent),new GBC(2,12,30,6).setInsets(3));
		
		projectExp 		= new JTextArea(8,25);
		projectExp.setEditable(false);
		additionalInfo.add(new JLabel("经验教训"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		additionalInfo.add(new JScrollPane(projectExp),new GBC(2,0,30,8).setInsets(3));
		
		submit = new JButton("确定");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ViewProject.this.dispose();
			}
		});

		buttonPanel.add(submit);		
		
		tabs = new JTabbedPane();
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("详细项目信息", projectDetialInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

		//将信息显示到窗体中
		getProjectInfo(oldProjectInfo);
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
	 * 将InfoBean中的信息放入窗口对应的控件中
	 */
	private void getProjectInfo(ProjectInfo oldProjectInfo){
		Vector<Vector<String>> project = GetInfo.getProjectByID(oldProjectInfo.getProjectID());
		
		projectName.setText(oldProjectInfo.getProjectName());
		customerNames.setText(project.get(0).get(2));
		contactNames.setText(oldProjectInfo.getContactName());
		staffNames.setText(oldProjectInfo.getStaffName());
		projectInterval.setText(project.get(0).get(5));
		projectNotify.setText(project.get(0).get(6));
		projectState.setText(GetInfo.PROJECTSTATE[oldProjectInfo.getProjectState() - 1]);
		projectContent.setText(project.get(0).get(8));
		projectCreateDate.setText(project.get(0).get(9));
		projectSignDate.setText(project.get(0).get(10));
		projectEndDate.setText(project.get(0).get(11));
		projectExp.setText(project.get(0).get(12));
	}
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField 	projectName = null;			//项目名称
	private JTextField 	customerNames 	= null;		//客户ID
	private JTextField 	contactNames 	= null;		//联系人ID
	private JTextField	staffNames 	= null;			//销售人员ID
	private JTextField	projectInterval = null;		//联系频度
	private JTextField 	projectNotify 	= null;		//项目提醒
	private JTextField 	projectState 	= null;		//项目状态
	private JTextArea 	projectContent 	= null;		//项目内容描述
	private JTextField 	projectCreateDate 	= null;	//创建日期
	private JTextField 	projectSignDate 	= null;	//签约日期
	private JTextField	projectEndDate 		= null;	//终止日期
	private JTextArea 	projectExp 	= null;			//经验教训
	
	private JButton submit = null; //确定
	
	//默认的窗体大小
	private final int DEFAULT_WIDTH  = 400;
	private final int DEFAULT_HEIGTH = 300;	
}
