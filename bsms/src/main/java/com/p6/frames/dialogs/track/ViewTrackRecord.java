package com.p6.frames.dialogs.track;

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

import org.apache.log4j.Logger;

import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.infoBean.TrackRecordInfo;

/**
 * <p>Description: 查看跟踪记录对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class ViewTrackRecord extends TrackRecordDialog{

	/**
	 * 初始化
	 * @param owner 父窗体
	 * @param projectInfo	项目信息
	 * @param oldTrackRecordInfo	跟踪记录信息
	 */
	public ViewTrackRecord(JFrame owner,ProjectInfo projectInfo ,TrackRecordInfo oldTrackRecordInfo){
		//设置窗体属性
		setLayout(new BorderLayout());
		setTitle("查看跟踪记录");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		tabs = new JTabbedPane();
		JPanel basicInfo = new JPanel(new GridBagLayout());
		JPanel additionalInfo = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel();
	
		//控件
		projectName = new JTextField(10);
		projectState = new JTextField(5);
		projectState.setEditable(false);
		contactName = new JTextField(6);
		contactName.setEditable(false);
		
		trackDate = new JTextField(6);
		trackWay = new JTextField(6);
		trackContent = new JTextArea(6,26);
		
		trackDate.setEditable(false);		
		
		basicInfo.add(new JLabel("项目名称"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(projectName, new GBC(2, 0, 10, 2).setInsets(3).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("项目状态"), new GBC(0, 4, 2, 2).setInsets(3));
		basicInfo.add(projectState, new GBC(2, 4, 5, 2).setInsets(3));
		basicInfo.add(new JLabel("联系人"), new GBC(16, 4, 2, 2).setInsets(3));
		basicInfo.add(contactName,new GBC(18, 4, 5, 2).setInsets(3));
		basicInfo.add(new JLabel("日期"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(trackDate, new GBC(2, 8, 6, 2).setInsets(3).setAnchor(GBC.WEST));
		
		basicInfo.add(new JLabel("跟踪方式"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(trackWay, new GBC(18, 8, 10, 2).setInsets(3).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("主要内容"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(new JScrollPane(trackContent), new GBC(2, 12, 30, 6).setInsets(3).setAnchor(GBC.WEST));
		
		trackRemark = new JTextArea(6,26);
		additionalInfo.add(new JLabel("备注"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(trackRemark),new GBC(2,0,30,6).setInsets(5));
		
		submit = new JButton("确定");
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ViewTrackRecord.this.dispose();
			}
		});
		
		
		buttonPanel.add(submit);
		
		//将选项卡加入选项卡面板
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//加入选项卡面板
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);
		
		Vector<Vector<String>> trackRecord = GetInfo.getAllTrackRecordByID(oldTrackRecordInfo.getTrackID());
		if(trackRecord.size()==0){
			logger.error("No trackRecord id is "+oldTrackRecordInfo.getTrackID());
		}
			
		else{
			projectName.setText(trackRecord.get(0).get(1));
			contactName.setText(trackRecord.get(0).get(4));
			projectState.setText(trackRecord.get(0).get(2));
			trackDate.setText(trackRecord.get(0).get(3));
			trackWay.setText(trackRecord.get(0).get(5));
			trackContent.setText(trackRecord.get(0).get(6));
			trackRemark.setText(trackRecord.get(0).get(7));

		}		
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


	private JTabbedPane tabs = null; 			//选项卡

	private JTextField	projectName = null;		//项目名称
	private JTextField  projectState = null;	//项目状态
	private JTextField	contactName = null;		//联系人名称
	private JTextField 	trackDate;				//日期
	private JTextField	trackWay;				//跟踪方式
	private JTextArea 	trackContent;			//主要内容
	private JTextArea 	trackRemark;			//备注
	
	private JButton submit = null; 		//确定按钮

	//默认窗体大小
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 350;
	
	private static Logger logger = Logger.getLogger(ModifyTrackRecord.class);
	private boolean isEdit = false;		//记录操作是否成功
}
