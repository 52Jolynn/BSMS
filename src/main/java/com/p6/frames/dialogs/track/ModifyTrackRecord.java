package com.p6.frames.dialogs.track;

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

import org.apache.log4j.Logger;

import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Handler;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.infoBean.TrackRecordInfo;
import com.p6.user.trackRecord.DoModifyTrackRecord;

/**
 * <p>Description: 修改跟踪记录对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class ModifyTrackRecord extends TrackRecordDialog{

	/**
	 * 初始化 
	 * @param owner 父窗体
	 * @param projectInfo	项目信息
	 * @param oldTrackRecordInfo	跟踪记录信息
	 */
	public ModifyTrackRecord(final JFrame owner,ProjectInfo projectInfo ,TrackRecordInfo oldTrackRecordInfo){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("修改跟踪记录");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		this.projectInfo = projectInfo;
		this.trackRecordInfo = oldTrackRecordInfo;
		
		tabs = new JTabbedPane();
		JPanel basicInfo = new JPanel(new GridBagLayout());
		JPanel additionalInfo = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel();
	
		projectName = new JComboBox();
		projectState = new JTextField(5);
		projectState.setEditable(false);
		contactName = new JTextField(6);
		contactName.setEditable(false);
		
		trackDate = new JTextField(6);
		trackWay = new JComboBox(new String[]{"电话","邮件","面谈","信函"});//电话、邮件、面谈、信函
		trackContent = new JTextArea(6,26);
		
		trackDate.setEditable(false);		
		trackDateChooser = new JButton(new ImageIcon(ModifyTrackRecord.class.getResource("/images/calendar.gif")));
		
		trackDateChooser.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				DateChooserDialog dlg = new DateChooserDialog(ModifyTrackRecord.this,trackDate.getText());
				dlg.setVisible(true);
				trackDate.setText(dlg.getChosenDate("-"));
			}
		}); 
		basicInfo.add(new JLabel("项目名称"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(projectName, new GBC(2, 0, 10, 2).setInsets(3).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("项目状态"), new GBC(0, 4, 2, 2).setInsets(3));
		basicInfo.add(projectState, new GBC(2, 4, 5, 2).setInsets(3));
		basicInfo.add(new JLabel("联系人"), new GBC(16, 4, 2, 2).setInsets(3));
		basicInfo.add(contactName,new GBC(18, 4, 5, 2).setInsets(3));
		basicInfo.add(new JLabel("日期"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(trackDate, new GBC(2, 8, 6, 2).setInsets(3).setAnchor(GBC.WEST));
		basicInfo.add(trackDateChooser, new GBC(8, 8, 1, 2).setInsets(3).setAnchor(GBC.WEST).setIPad(-18, -8));
		basicInfo.add(new JLabel("跟踪方式"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(trackWay, new GBC(18, 8, 10, 2).setInsets(3).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("主要内容"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(new JScrollPane(trackContent), new GBC(2, 12, 30, 6).setInsets(3).setAnchor(GBC.WEST));
		
		trackRemark = new JTextArea(6,26);
		additionalInfo.add(new JLabel("备注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(trackRemark),new GBC(2,0,30,6).setInsets(5));
		
		submit = new JButton("确定");
		cancel = new JButton("取消");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				//检查用户输入的数据是否有效
				if(setTrackRecordInfo(ModifyTrackRecord.this.projectInfo)== false)
					return;
				
				Handler handler = new Handler();
				handler.setDoHandler(new DoModifyTrackRecord());
				handler.process(trackRecordInfo);
				
				if (handler.isSucced()) {	//操作成功
					isEdit = true;
					new MessageBox("成功修改跟踪记录信息","成功",owner);
					ModifyTrackRecord.this.dispose();
				} else {					//操作失败
					isEdit = false;
					new MessageBox("修改跟踪记录信息失败","失败",owner);
				}
			}
		});
		
		//取消按钮
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				isEdit = false;
				ModifyTrackRecord.this.dispose();
			}
			
		});		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		//将选项卡加入选项卡面板
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//加入选项卡面板
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		//得到所有的跟踪记录信息
		Vector<Vector<String>> trackRecord = GetInfo.getAllTrackRecordByID(oldTrackRecordInfo.getTrackID());
		if(trackRecord.size()==0){
			logger.error("No trackRecord id is "+oldTrackRecordInfo.getTrackID());
		}
		else{
			trackRecordInfo.setProjectID(new Integer(trackRecord.get(0).get(8)));
			trackRecordInfo.setProjectName(trackRecord.get(0).get(1));
			projectState.setText(trackRecord.get(0).get(2));
			trackDate.setText(trackRecord.get(0).get(3));
			trackWay.setSelectedItem(trackRecord.get(0).get(5));
			trackContent.setText(trackRecord.get(0).get(6));
			trackRemark.setText(trackRecord.get(0).get(7));

		}
		
		//得到所有的项目信息
		Vector<Vector<String>> allProjectInfo = GetInfo.getAllProject();
		int projectCount = allProjectInfo.size();
		allProjectNames = new String[projectCount];
		allProjectIDs = new int[projectCount];
		allProjectState = new String[projectCount];
		allContactIDs = new int[projectCount];
		allContactNames = new String[projectCount];
		int projectNameIndex = 0;
		for(int i=0; i<projectCount; i++){
			allProjectIDs[i] = new Integer(allProjectInfo.get(i).get(0));
			allProjectNames[i] = allProjectInfo.get(i).get(1);
			allProjectState[i] = allProjectInfo.get(i).get(8);
			allContactIDs[i] =  new Integer(allProjectInfo.get(i).get(3));
			allContactNames[i] =  allProjectInfo.get(i).get(4);
			projectName.addItem(allProjectNames[i]);
			if(allProjectIDs[i]==trackRecordInfo.getProjectID())
				projectNameIndex = i;
		}
		projectName.setSelectedIndex(projectNameIndex);
		contactName.setText(allContactNames[projectNameIndex]);
		projectName.addItemListener(new ItemListener(){   
			public void itemStateChanged(ItemEvent e) {
				int selectProjectIndex  = projectName.getSelectedIndex();
					projectState.setText(allProjectState[selectProjectIndex]);
					contactName.setText(allContactNames[selectProjectIndex]);
			}   
		}); 
		
		
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
	 * 检查用户输入的数据的合法性
	 * @return 若有效返回true，否则返回false
	 */
	private boolean checkDate(){
		if(projectName.getSelectedIndex() == 0){
			new MessageBox("请选择项目名称","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(trackDate.getText().equals("")){
			new MessageBox("请选择项跟踪记录日期","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		if(trackContent.getText().equals("")){
			new MessageBox("请输入跟踪记录内容","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		return true;
	}
	/**
	 * 将窗体的构造函数中的数据放入InfoBean中
	 */
	private boolean setTrackRecordInfo(ProjectInfo info){
		if(checkDate()==false)
			return false;
		
		trackRecordInfo.setProjectID(allProjectIDs[projectName.getSelectedIndex()]);
		trackRecordInfo.setContactID(allContactIDs[projectName.getSelectedIndex()]);
		String []state = {"跟踪","方案","竞标","谈判","签约","中止"};
		int i=0;
		for(; i<=state.length; i++){
			if(projectState.getText().equals(state[i]))
				break;
		}
		trackRecordInfo.setProjectState((short)(i+1));
		
		trackRecordInfo.setTrackDate(ParseStringAndDate.parseString2Date(trackDate.getText(), "-"));
		trackRecordInfo.setTrackWay((short)(trackWay.getSelectedIndex()+1));
		trackRecordInfo.setTrackContent(trackContent.getText());
		trackRecordInfo.setTrackRemark(trackRemark.getText());
		return true;
	}
	

	private JTabbedPane tabs = null; 			//选项卡

	private JComboBox	projectName = null;		//项目名称
	private JTextField  projectState = null;	//项目状态
	private JTextField	contactName = null;		//联系人名称
	private JTextField 	trackDate;				//日期
	private JComboBox	trackWay;				//跟踪方式
	private JTextArea 	trackContent;			//主要内容
	private JTextArea 	trackRemark;			//备注
	
	private JButton submit = null; //确定
	private JButton cancel = null; //取消
	
	private JButton trackDateChooser = null;	//日期选择按钮
	private TrackRecordInfo trackRecordInfo = new TrackRecordInfo();	//跟踪记录
	private ProjectInfo projectInfo = null;		//项目信息
	
	//窗体默认大小
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 350;
	
	String[] allProjectNames = null;		//所有的项目名称
	int[] 	allProjectIDs = null;			//所有的项目ID
	String[] allProjectState = null;		//所有项目状态
	int[] allContactIDs = null;				//所有联系人ID
	String[] allContactNames = null;		//所有联系人名称
	
	private static Logger logger = Logger.getLogger(ModifyTrackRecord.class);
	private boolean isEdit = false;			//记录操作是否成功
}
