package com.p6.frames.dialogs.project;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

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
import com.p6.toolkit.CnToSpell;
import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Handler;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.infoBean.StaffInfo;
import com.p6.user.project.DoAddProject;

/**
 * <p>Description: 添加项目对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class AddProject extends ProjectDialog{
	
	/**
	 * 
	 * @param owner 父窗体
	 */
	public AddProject(final JFrame owner){
		setLayout(new BorderLayout());
		setTitle("新增项目");
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
		customerNames 	= new JComboBox();
		contactNames 	= new JComboBox();
		staffNames 	= new JComboBox();
		
		basicInfo.add(new JLabel("项目名称"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(projectName,new GBC(2,0,10,2).setInsets(3));
		basicInfo.add(new JLabel("客        户"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerNames,new GBC(18,0,10,2).setInsets(3).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("联  系  人"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactNames,new GBC(2,4,10,2).setInsets(3).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("销售人员"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(staffNames,new GBC(18,4,10,2).setInsets(3).setFill(GBC.HORIZONTAL));
		
		//项目详细信息控件
		projectInterval = new JTextField(10);
		projectNotify 	= new JTextField(10);
		projectState  	= new JComboBox(new String[]{"跟踪","方案","竞标","谈判","签约","中止"}); //跟踪、方案、竞标、谈判、签约、中止
		projectCreateDate = new JTextField(6);
		projectSignDate = new JTextField(6);
		projectEndDate  = new JTextField(6);
		projectContent 	= new JTextArea(5,26);
		projectState.setSelectedIndex(0);
		projectInterval.setText("0");
		projectNotify.setText("0");
		
		projectCreateDate.setEditable(false);
		projectSignDate.setEditable(false);
		projectEndDate.setEditable(false);
		createDateButton = new JButton(new ImageIcon(AddProject.class.getResource("/images/calendar.gif")));
		signDateButton = new JButton(new ImageIcon(AddProject.class.getResource("/images/calendar.gif")));
		endDateButton = new JButton(new ImageIcon(AddProject.class.getResource("/images/calendar.gif")));
		
		//初始化日期选择按钮
		createDateButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddProject.this,projectCreateDate.getText());
				dlg.setVisible(true);
				projectCreateDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		
		signDateButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddProject.this,projectSignDate.getText());
				dlg.setVisible(true);
				projectSignDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		endDateButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				
				DateChooserDialog dlg = new DateChooserDialog(AddProject.this,projectEndDate.getText());
				dlg.setVisible(true);
				projectEndDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		
		//将控件放入面板中
		projectDetialInfo.add(new JLabel("联系频度"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectInterval,new GBC(2,0,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("项目提醒"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectNotify,new GBC(18,0,10,2).setInsets(3));
		projectDetialInfo.add(new JLabel("项目状态"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectState,new GBC(2,4,10,2).setInsets(3).setFill(GBC.HORIZONTAL));
		projectDetialInfo.add(new JLabel("创建日期"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectCreateDate,new GBC(18,4,6,2).setInsets(3));
		projectDetialInfo.add(createDateButton,new GBC(24,4,1,2).setInsets(5).setInsets(1).setIPad(-18, -8));
		projectDetialInfo.add(new JLabel("签约日期"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectSignDate,new GBC(2,8,6,2).setInsets(3));
		projectDetialInfo.add(signDateButton,new GBC(8,8,1,2).setInsets(5).setInsets(1).setIPad(-18, -8));
		projectDetialInfo.add(new JLabel("终止日期"),new GBC(16,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(projectEndDate,new GBC(18,8,6,2).setInsets(3));
		projectDetialInfo.add(endDateButton,new GBC(24,8,1,2).setInsets(5).setInsets(1).setIPad(-18, -8));
		projectDetialInfo.add(new JLabel("项目内容"),new GBC(0,12,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		projectDetialInfo.add(new JScrollPane(projectContent),new GBC(2,12,30,6).setInsets(3));
		
		projectExp 		= new JTextArea(8,25);
		additionalInfo.add(new JLabel("经验教训"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		additionalInfo.add(new JScrollPane(projectExp),new GBC(2,0,30,8).setInsets(3));
		
		//按钮
		submit = new JButton("确定");
		cancel = new JButton("取消");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				//判断用户输入是否有效
				if(setProjectInfo()==false)
					return;
				Handler handler = new Handler();
				handler.setDoHandler(new DoAddProject());
				handler.process(projectInfo);
				if(handler.isSucced()){		//操作成功
					isEdit = true;
					new MessageBox("成功添加项目","成功",owner);
					AddProject.this.dispose();
				}
				else{						//操作失败
					isEdit = false;
					new MessageBox("添加项目失败，请重新尝试","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				isEdit = false;
				AddProject.this.dispose();
			}
		});	
		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		tabs = new JTabbedPane();
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("详细项目信息", projectDetialInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		//得到所有客户名称，并显示到窗体对应的控件中
		Vector<Vector<String>> customerNamesVector = GetInfo.getAllCustomer();
		int customerCount = customerNamesVector.size();
		allCustomerIDs = new int[customerCount+1];
		allCustomerNames = new String[customerCount];
		customerNames.addItem("请选择客户");
		for(int i=0; i<customerCount; i++){
			allCustomerIDs[i+1] = new Integer(customerNamesVector.get(i).get(0)); //index1 customer ID 在表customerInfo的位置 
			allCustomerNames[i] = customerNamesVector.get(i).get(1);//index2 customer Names 在表customerInfo的位置
			customerNames.addItem(allCustomerNames[i]);
			System.out.println(""+allCustomerIDs[i]+"  "+allCustomerNames[i]);
		}
		
		updateContactList();
		
		//对客户名称控件添加事件响应
		customerNames.addItemListener(new ItemListener(){   
			public void itemStateChanged(ItemEvent e) {
				contactNames.removeAllItems();
				allContactIDs.removeAllElements();
				
				updateContactList();
			}   
		}); 
	
		
		//得到所有可以负责的销售人员
		if (owner instanceof Role) {
			staffInfo = ((Role)owner).getStaffInfo();
			staffNames.addItem("请选择销售人员");
			if(staffInfo.getPosName().equals("部门经理")){
				
				Vector<Vector<String>> allSmNames = GetInfo.getStaffInfoByPosition("销售经理");
				int staffCount = allSmNames.size();
				System.out.println(staffCount);
				allStaffIDs = new int[staffCount+1];
				allStaffNames = new String[staffCount+1];
				allStaffIDs[0] = staffInfo.getStaffID();
				allStaffNames[0] = staffInfo.getStaffName();
				staffNames.addItem(allStaffNames[0]);
				for(int i=1; i<=staffCount; i++){
					allStaffIDs[i] = new Integer(allSmNames.get(i-1).get(0));
					allStaffNames[i] = allSmNames.get(i-1).get(1);
					staffNames.addItem(allStaffNames[i]);
					System.out.println(""+allStaffIDs[i]+"  "+allStaffNames[i]);
				}
			}
			else if(staffInfo.getPosName().equals("销售经理")){
				staffNames.addItem(staffInfo.getStaffName());
			}
		}
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
	 * 检查用户输入的数据是否有效
	 * @return 若有效返回true，否则返回false
	 */
	private boolean checkData(){
		if((projectName.getText()).equals("")){
			new MessageBox("请输入项目名称","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(customerNames.getSelectedIndex()==0){
			new MessageBox("请选择客户","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(contactNames.getSelectedIndex()==0){
			new MessageBox("请选择联系人","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(staffNames.getSelectedIndex() ==0){
			new MessageBox("请选择销售人员","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 将窗口内信息存入InfoBean中
	 */
	private boolean setProjectInfo() {
		if(checkData() ==false)
			return false;
		projectInfo.setProjectName(projectName.getText());
		projectInfo.setCustomerID(allCustomerIDs[customerNames.getSelectedIndex()]);
		projectInfo.setContactID(allContactIDs.get(contactNames.getSelectedIndex()-1));
		projectInfo.setStaffID(allStaffIDs[staffNames.getSelectedIndex()-1]);
		projectInfo.setProjectInterval(new Integer(projectInterval.getText()));
		projectInfo.setProjectNotify(new Integer(projectNotify.getText()));
		projectInfo.setProjectState((short)(projectState.getSelectedIndex() + 1));
		projectInfo.setProjectContent(projectContent.getText());
		projectInfo.setProjectCreateDate(ParseStringAndDate.parseString2Date(projectCreateDate.getText(),"-"));
		projectInfo.setProjectSignDate(ParseStringAndDate.parseString2Date(projectSignDate.getText(),"-"));
		projectInfo.setProjectEndDate(ParseStringAndDate.parseString2Date(projectEndDate.getText(),"-"));
		projectInfo.setProjectExp(projectExp.getText());
		projectInfo.setSiftKey(CnToSpell.getFullSpell(projectName.getText()));
		return true;
	}
	
	/**
	 * 根据当前选择的客户，得到该客户的所有联系人，并将其放入对应的联系人控件中
	 */
	private void updateContactList(){
		int selectedCustomerID = allCustomerIDs[customerNames.getSelectedIndex()]; //得到选中的customer的ID
		
		Vector<Vector<String>> contactNamesVector = GetInfo.getAllContact();
		int contactCount = contactNamesVector.size();

		boolean hasContact = false; 	//判断客户是否有联系人
		for(int i=0; i<contactCount; i++){
			if(selectedCustomerID == new Integer(contactNamesVector.get(i).get(3)))
			{
				contactNames.addItem(contactNamesVector.get(i).get(1));
				allContactIDs.add(new Integer(contactNamesVector.get(i).get(0)));
				hasContact = true;
				//System.out.println(""+allContactIDs[i]+"  "+allContactNames[i]);
			}
		}
		
		if(hasContact)		//该客户没有联系人
			contactNames.insertItemAt("请选择联系人",0);
		else
			contactNames.insertItemAt("没有联系人", 0);
		contactNames.setSelectedIndex(0);
	}
	
	private Vector<Integer> allContactIDs = new Vector<Integer>();	//所有的联系人信息
	private int [] allCustomerIDs = null;	//所有的客户的ID
	private int [] allStaffIDs = null;		//所有的销售人员ID

	private String [] allCustomerNames = null;	//所有客户名称
	private String [] allStaffNames = null;		//所有销售人员名称
	
	private JTabbedPane tabs = null; //选项卡
	
	private JTextField 	projectName = null;			//项目名称
	private JComboBox 	customerNames 	= null;		//客户ID
	private JComboBox 	contactNames 	= null;		//联系人ID
	private JComboBox	staffNames 	= null;			//销售人员ID
	private JTextField	projectInterval = null;		//联系频度
	private JTextField 	projectNotify 	= null;		//项目提醒
	private JComboBox 	projectState 	= null;		//项目状态
	private JTextArea 	projectContent 	= null;		//项目内容描述
	private JTextField 	projectCreateDate 	= null;	//创建日期
	private JTextField 	projectSignDate 	= null;	//签约日期
	private JTextField	projectEndDate 		= null;	//终止日期
	private JTextArea 	projectExp 	= null;			//经验教训
	
	private JButton submit = null; //确定
	private JButton cancel = null; //取消
	
	//日期选择按钮
	private JButton createDateButton = null;
	private JButton signDateButton = null;
	private JButton endDateButton = null;
	
	private ProjectInfo projectInfo = new ProjectInfo();	//项目信息
	
	//窗体默认大小
	private final int DEFAULT_WIDTH  = 400;
	private final int DEFAULT_HEIGTH = 300;
	private boolean isEdit = false;			//记录操作是否成功
	
	private StaffInfo staffInfo = null;		//销售人员信息
}
