/*
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.frames;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;

import com.p6.frames.dialogs.Function;
import com.p6.frames.dialogs.contact.AddContact;
import com.p6.frames.dialogs.contact.BirthdayNotify;
import com.p6.frames.dialogs.contact.ModifyContact;
import com.p6.frames.dialogs.contact.ViewContact;
import com.p6.frames.dialogs.other.About;
import com.p6.frames.dialogs.other.Save;
import com.p6.frames.dialogs.other.Search;
import com.p6.frames.dialogs.other.StatisticInfo;
import com.p6.frames.dialogs.personal.ModifySelfInfo;
import com.p6.frames.dialogs.personal.ModifySelfPwd;
import com.p6.frames.dialogs.personal.ViewSelfInfo;
import com.p6.frames.dialogs.project.ModifyProject;
import com.p6.frames.dialogs.project.ProjectNotify;
import com.p6.frames.dialogs.project.ViewProject;
import com.p6.frames.dialogs.track.AddTrackRecord;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.SetInfoBean;
import com.p6.toolkit.SortManager;
import com.p6.toolkit.SystemProperties;
import com.p6.toolkit.Table;
import com.p6.toolkit.Today;
import com.p6.toolkit.VectorFilter;
import com.p6.user.infoBean.ContactInfo;
import com.p6.user.infoBean.ProjectInfo;
import com.p6.user.infoBean.StaffInfo;

public class SMFrame extends JFrame implements Role{

	/**
	 * 构造函数
	 */
	public SMFrame(int staffID) {
		//设置窗体属性
		setTitle(title);
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		SystemProperties sp = SystemProperties.getInstance();
		setLocation(sp.getScreenWidth() / 8, sp.getScreenHeight() / 8);

		staffInfo = GetInfo.getStaffInfo(staffID);
		createMenu();
		createToolBar();
		createContent();
	}

	/**
	 * 建立菜单
	 */
	private void createMenu() {
		//建立菜单栏
		menuBar = new JMenuBar();

		//建立菜单
		file = new JMenu("文件(F)");
		manage = new JMenu("管理(M)");
		personal = new JMenu("个人管理(P)");
		search = new JMenu("查询(S)");
		help = new JMenu("帮助(H)");

		//设置菜单字体
		file.setFont(font);
		manage.setFont(font);
		personal.setFont(font);
		search.setFont(font);
		help.setFont(font);

		//建立菜单项
		//文件菜单项
		save = new JMenuItem("保存", new ImageIcon(SMFrame.class.getResource("/images/save.gif")));
		print = new JMenuItem("打印", new ImageIcon(SMFrame.class.getResource("/images/print.gif")));
		exit = new JMenuItem("退出", new ImageIcon(SMFrame.class.getResource("/images/exit.gif")));

		//设置字体
		save.setFont(font);
		print.setFont(font);
		exit.setFont(font);

		//为文件菜单设置快捷键
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

		//管理菜单项
		contact = new JMenuItem("联系人管理(C)", new ImageIcon(SMFrame.class.getResource("/images/contact.gif")));
		project = new JMenuItem("项目管理(P)", new ImageIcon(SMFrame.class.getResource("/images/project.gif")));

		//设置管理菜单字体
		contact.setFont(font);
		project.setFont(font);

		//为管理菜单项设置快捷键
		contact.setMnemonic('C');
		project.setMnemonic('P');

		//个人管理菜单项
		viewSelfInfo = new JMenuItem("查看个人资料(V)", new ImageIcon(SMFrame.class.getResource("/images/view.png")));
		modifySelfInfo = new JMenuItem("修改个人资料(M)", new ImageIcon(SMFrame.class.getResource("/images/modify.png")));
		modifySelfPassword = new JMenuItem("修改个人密码(P)", new ImageIcon(SMFrame.class.getResource("/images/password.png")));

		//设置个人管理菜单项字体
		viewSelfInfo.setFont(font);
		modifySelfInfo.setFont(font);
		modifySelfPassword.setFont(font);

		//为个人管理菜单设置快捷键
		viewSelfInfo.setMnemonic('V');
		modifySelfInfo.setMnemonic('M');
		modifySelfPassword.setMnemonic('P');

		//查询菜单项
		generalSearch = new JMenuItem("普通查询(S)", new ImageIcon(SMFrame.class.getResource("/images/search.png")));
		advancedSearch = new JMenuItem("高级查询(A)", new ImageIcon(SMFrame.class.getResource("/images/advancedSearch.png")));

		//设置查询菜单项字体
		generalSearch.setFont(font);
		advancedSearch.setFont(font);

		//为查询菜单项设置快捷键
		generalSearch.setMnemonic('S');
		advancedSearch.setMnemonic('A');

		//帮助菜单项
		helpDocument = new JMenuItem("帮助文档", new ImageIcon(SMFrame.class.getResource("/images/help.png")));
		about = new JMenuItem("关于我们", new ImageIcon(SMFrame.class.getResource("/images/aboutUS.png")));

		//设置帮助菜单项字体
		helpDocument.setFont(font);
		about.setFont(font);

		//为帮助菜单设置快捷键
		helpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		//将菜单栏所有元素加入框架中
		setJMenuBar(menuBar);

		//加入菜单
		menuBar.add(file);
		menuBar.add(manage);
		menuBar.add(personal);
		menuBar.add(search);
		menuBar.add(help);

		//加入菜单项
		file.add(save);
		file.add(print);
		file.add(exit);

		manage.add(contact);
		manage.add(project);

		personal.add(viewSelfInfo);
		personal.add(modifySelfInfo);
		personal.add(modifySelfPassword);

		search.add(generalSearch);
		search.add(advancedSearch);

		help.add(helpDocument);
		help.add(about);

		//设置事件处理程序
		//保存
		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				save();

			}

		});

		//打印
		print.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				print();

			}

		});

		//退出
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});

		//联系人管理
		contact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contactManage();
			}

		});

		//项目管理
		project.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				projectManage();

			}

		});

		//查看个人资料
		viewSelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ViewSelfInfo(SMFrame.this, staffInfo));
				function.create();

			}

		});

		//修改个人资料
		modifySelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfInfo(SMFrame.this, staffInfo));
				function.create();

			}

		});

		//修改个人密码
		modifySelfPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfPwd(SMFrame.this));
				function.create();

			}

		});

		//普通查询
		generalSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				search();

			}

		});

		//帮助文档
		helpDocument.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				help();

			}

		});

		//关于我们
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				about();

			}

		});
		
		//设置元素活动性
		save.setEnabled(false);
		print.setEnabled(false);
		advancedSearch.setEnabled(false);
		
		//按钮事件处理程序段
		//查看联系人资料
		viewContactInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何联系人, 请选择一个进行查看.", "提示", SMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个联系人, 请只选择一个进行查看.", "提示", SMFrame.this);
				}
				else {
					SetInfoBean.setContact(contactInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewContact viewContact = new ViewContact(SMFrame.this, contactInfo);
					function.setFunctionDialog(viewContact);
					function.create();
				}
				
			}
			
		});
		
		//增加联系人
		addContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddContact addContact = new AddContact(SMFrame.this);
				function.setFunctionDialog(addContact);
				function.create();
				
				if (addContact.isUpdate() == true) {
					contactContent = GetInfo.getAllContact();
					content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, contactContent);
					setTable(content, header);
				}
				
			}
			
		});
		
		//修改联系人
		modifyContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何联系人, 请选择一个进行修改.", "提示", SMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个联系人, 请只选择一个进行修改.", "提示", SMFrame.this);
				}
				else {
					SetInfoBean.setContact(contactInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyContact modifyContact = new ModifyContact(SMFrame.this, contactInfo);
					function.setFunctionDialog(modifyContact);
					function.create();
					
					if (modifyContact.isUpdate() == true) {
						contactContent = GetInfo.getAllContact();
						content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, contactContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//生日提醒
		birthdayNotify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				BirthdayNotify birthdayNotify = new BirthdayNotify(SMFrame.this);
				function.setFunctionDialog(birthdayNotify);
				function.create();
				
			}
			
		});
		
		//联系人筛选
		contactSiftKey.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				char c = Character.toLowerCase(e.getKeyChar());
				StringBuffer str = new StringBuffer();
				str.append(contactSiftKey.getText());
				if (!Character.isISOControl(c)) {
					str.append(c);
				}
				
				content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, VectorFilter.Filter(str.toString(), contactContent));
				setTable(content, header);
			}
			
		});
		
		//查看项目
		viewProjectInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何项目, 请选择一个进行查看.", "提示", SMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个项目, 请只选择一个进行查看.", "提示", SMFrame.this);
				}
				else {
					SetInfoBean.setProject(projectInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewProject viewProject = new ViewProject(SMFrame.this, projectInfo);
					function.setFunctionDialog(viewProject);
					function.create();
				}
			}
			
		});
		
		//修改项目
		modifyProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何项目, 请选择一个进行修改.", "提示", SMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择多个项目, 请只选择一个进行修改.", "提示", SMFrame.this);
				}
				else {
					SetInfoBean.setProject(projectInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyProject modifyProject = new ModifyProject(SMFrame.this, projectInfo);
					function.setFunctionDialog(modifyProject);
					function.create();
					
					if (modifyProject.isUpdate() == true) {
						projectContent = GetInfo.getAllProject();
						content = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, projectContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//项目提醒
		projectNotify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				ProjectNotify projectNotify = new ProjectNotify(SMFrame.this);
				function.setFunctionDialog(projectNotify);
				function.create();
				
			}
			
		});
		
		//统计信息
		statistics.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				StatisticInfo statisticInfo = new StatisticInfo(SMFrame.this);
				function.setFunctionDialog(statisticInfo);
				function.create();
				
			}
			
		});
		
		//增加跟踪记录
		addTrackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddTrackRecord addTrackRecord = new AddTrackRecord(SMFrame.this, projectInfo);
				function.setFunctionDialog(addTrackRecord);
				function.create();
				
				//此处不用更新JTable, 销售经理对跟踪记录不进行其他操作
				
			}
			
		});
		
		//项目筛选
		projectSiftKey.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				char c = Character.toLowerCase(e.getKeyChar());
				StringBuffer str = new StringBuffer();
				str.append(projectSiftKey.getText());
				
				if (!Character.isISOControl(c)) {
					str.append(c);
				}
				
				content = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, VectorFilter.Filter(str.toString(), projectContent));
				setTable(content, header);
			}
			
		});
		
		//屏蔽已签约项目事件处理
		hideSign.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Vector<Vector<String>> hide = new Vector<Vector<String>>();
				if (hideSign.isSelected() && hideEnded.isSelected()) {
					hide = VectorFilter.signedFilter(content);
					hide = VectorFilter.endedFilter(hide);
					setTable(hide, header);
				}
				else if (hideSign.isSelected() && !hideEnded.isSelected()) {
					hide = VectorFilter.signedFilter(content);
					setTable(hide, header);
				}
				else if (!hideSign.isSelected() && hideEnded.isSelected()) {
					hide = VectorFilter.endedFilter(content);
					setTable(hide, header);
				}
				else if (!hideSign.isSelected() && !hideEnded.isSelected()) {
					setTable(content, header);
				}
			}
			
		});
		
		//屏蔽已终止项目事件处理
		hideEnded.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Vector<Vector<String>> hide = new Vector<Vector<String>>(); 
				if (hideSign.isSelected() && hideEnded.isSelected()) {
					hide = VectorFilter.signedFilter(content);
					hide = VectorFilter.endedFilter(hide);
					setTable(hide, header);
				}
				else if (hideSign.isSelected() && !hideEnded.isSelected()) {
					hide = VectorFilter.signedFilter(content);
					setTable(hide, header);
				}
				else if (!hideSign.isSelected() && hideEnded.isSelected()) {
					hide = VectorFilter.endedFilter(content);
					setTable(hide, header);
				}
				else if (!hideSign.isSelected() && !hideEnded.isSelected()) {
					setTable(content, header);
				}
				
			}
			
		});
		
		//table选择事件响应
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();
				logger.debug("Begin - " + SMFrame.class.getName());
				for (int row : selectedRows) {
					logger.debug("Selected Rows: " + row);
				}
				logger.debug("End - " + SMFrame.class.getName());
				
			}
			
		});
	}

	/**
	 * 生成工具栏
	 */
	private void createToolBar() {
		panel = new JPanel(new BorderLayout());
		add(panel);

		//加入工具栏
		toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);

		//设置工具栏及其事件响应
		//保存
		saveAction = new AbstractAction("保存", new ImageIcon(SMFrame.class.getResource("/images/TSave.gif"))) {

			public void actionPerformed(ActionEvent e) {
				save();
			}

		};

		//打印
		printAction = new AbstractAction("打印", new ImageIcon(SMFrame.class.getResource("/images/TPrint.gif"))) {

			public void actionPerformed(ActionEvent e) {
				print();
			}

		};

		//联系人管理
		contactAction = new AbstractAction("联系人管理", new ImageIcon(SMFrame.class.getResource("/images/TContact.gif"))) {

			public void actionPerformed(ActionEvent e) {
				contactManage();
			}

		};

		//项目管理
		projectAction = new AbstractAction("项目管理", new ImageIcon(SMFrame.class.getResource("/images/TProject.gif"))) {

			public void actionPerformed(ActionEvent e) {
				projectManage();
			}

		};

		//查询
		Action searchAction = new AbstractAction("查询", new ImageIcon(SMFrame.class.getResource("/images/TSearch.gif"))) {

			public void actionPerformed(ActionEvent e) {
				search();
			}
		};

		//帮助
		Action helpAction = new 
		AbstractAction("帮助文档", new ImageIcon(SMFrame.class.getResource("/images/THelp.gif"))) {

			public void actionPerformed(ActionEvent e) {
				help();
			}

		};

		//关于我们
		Action aboutAction = new 
		AbstractAction("关于我们", new ImageIcon(SMFrame.class.getResource("/images/TAboutUS.gif"))) {

			public void actionPerformed(ActionEvent e) {
				about();
			}

		};

		//退出
		Action exitAction = new 
		AbstractAction("退出系统", new ImageIcon(SMFrame.class.getResource("/images/TExit.gif"))) {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		};
		
		//为工具加入提示
		saveAction.putValue(Action.SHORT_DESCRIPTION, "保存");
		printAction.putValue(Action.SHORT_DESCRIPTION, "打印");
		contactAction.putValue(Action.SHORT_DESCRIPTION, "联系人管理");
		projectAction.putValue(Action.SHORT_DESCRIPTION, "项目管理");
		searchAction.putValue(Action.SHORT_DESCRIPTION, "查询");
		helpAction.putValue(Action.SHORT_DESCRIPTION, "帮助文档");
		aboutAction.putValue(Action.SHORT_DESCRIPTION, "关于我们");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "退出系统");
		
		//将图标加入工具栏
		toolBar.add(saveAction);
		toolBar.add(printAction);
		toolBar.add(contactAction);
		toolBar.add(projectAction);
		toolBar.add(searchAction);
		toolBar.add(helpAction);
		toolBar.add(aboutAction);
		toolBar.add(exitAction);
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		//设置工具活动性
		saveAction.setEnabled(false);
		printAction.setEnabled(false);
	}
	
	/**
	 * 生成内容
	 */
	private void createContent() {
		contentPanel = new JPanel(new BorderLayout());
		panel.add(contentPanel, BorderLayout.CENTER);
		
		Box message = Box.createVerticalBox();
		message.setBorder(BorderFactory.createEtchedBorder());
		
		dateImg = new JLabel(new ImageIcon(SMFrame.class.getResource("/images/calendar.gif")));
		String today = Today.getYear() + "年" + Today.getMonth() + "月" + Today.getDate() + "日";
		date = new JLabel(today);
		date.setFont(font);
		week = new JLabel(Today.getWeek());
		week.setFont(font);
		nameImg = new JLabel(new ImageIcon(SMFrame.class.getResource("/images/role.png")));
		name = new JLabel(staffInfo.getStaffName());
		name.setFont(font);
		role = new JLabel("销售经理");
		role.setFont(font);
		
		Box messageTop = Box.createVerticalBox();
		Box messageCenter = Box.createHorizontalBox();
		Box messageBottom = Box.createVerticalBox();
		
		messageTop.add(Box.createVerticalStrut(1));
		messageCenter.add(Box.createHorizontalStrut(5));
		messageCenter.add(dateImg);
		messageCenter.add(Box.createHorizontalStrut(2));
		messageCenter.add(date);
		messageCenter.add(Box.createHorizontalStrut(20));
		messageCenter.add(week);
		messageCenter.add(Box.createHorizontalStrut(50));
		messageCenter.add(nameImg);
		messageCenter.add(Box.createHorizontalStrut(2));
		messageCenter.add(name);
		messageCenter.add(Box.createHorizontalStrut(20));
		messageCenter.add(role);
		messageCenter.add(Box.createHorizontalGlue());
		messageBottom.add(Box.createVerticalStrut(1));
		
		message.add(messageTop);
		message.add(messageCenter);
		message.add(messageBottom);
		contentPanel.add(message, BorderLayout.NORTH);
	}

	/**
	 * 生成联系人管理面板
	 */
	private void contactManage() {
		print.setEnabled(true);
		printAction.setEnabled(true);
		
		//删除原有内容
		if (displayPanel != null && tips != null) {
			contentPanel.remove(displayPanel);
			contentPanel.remove(tips);
			validate();
			repaint();
		}
		
		contactContent = GetInfo.getAllContact();
		contactTitle = GetInfo.getContactTitle();
		
		content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, contactContent);
		header = VectorFilter.singleFilter(VectorFilter.CONTACTFILTER, contactTitle);
		
		//建立显示区面板
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "联系人管理"));
		
		//加入JTable
		setTable(content, header);
		scrollPanel = new JScrollPane(table);
		displayPanel.add(scrollPanel, BorderLayout.CENTER);
		
		//加入功能区
		Box functionBox = Box.createVerticalBox();
		Box functionTop = Box.createVerticalBox();
		Box functionCenter = Box.createHorizontalBox();
		Box functionUnderCenterTop = Box.createVerticalBox();
		Box functionUnderCenter = Box.createHorizontalBox();
		Box functionBottom = Box.createVerticalBox();
		
		functionTop.add(Box.createVerticalStrut(10));
		functionUnderCenterTop.add(Box.createVerticalStrut(10));
		functionBottom.add(Box.createVerticalStrut(20));
		
		JLabel sift = new JLabel("筛选");
		sift.setFont(font);
		contactSiftKey.setText("");
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewContactInfo.setText("查看");
		viewContactInfo.setFont(font);
		addContact.setText("增加");
		addContact.setFont(font);
		modifyContact.setText("修改");
		modifyContact.setFont(font);
		birthdayNotify.setText("生日提醒");
		birthdayNotify.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(contactSiftKey);
		functionCenter.add(Box.createHorizontalStrut(400));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewContactInfo);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addContact);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyContact);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(birthdayNotify);
		functionUnderCenter.add(Box.createHorizontalGlue());
		
		functionBox.add(functionTop);
		functionBox.add(functionCenter);
		functionBox.add(functionUnderCenterTop);
		functionBox.add(functionUnderCenter);
		functionBox.add(functionBottom);
		displayPanel.add(functionBox, BorderLayout.SOUTH);
		
		//建立提示区面板
		tips = Box.createHorizontalBox();
		tips.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "系统提示"));
		tips.add(Box.createHorizontalStrut(200));
		
		//加入系统提示
		// TODO 加入系统提示信息
		
		contentPanel.add(displayPanel, BorderLayout.CENTER);
		contentPanel.add(tips, BorderLayout.EAST);
		
		validate();
	}

	/**
	 * 生成项目管理面板
	 */
	private void projectManage() {
		print.setEnabled(true);
		printAction.setEnabled(true);
		
		//删除原有内容
		if (displayPanel != null && tips != null) {
			contentPanel.remove(displayPanel);
			contentPanel.remove(tips);
			validate();
			repaint();
		}
		
		projectContent = GetInfo.getAllProject();
		projectTitle = GetInfo.getProjectTitle();
		
		content = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, projectContent);
		header = VectorFilter.singleFilter(VectorFilter.PROJECTFILTER, projectTitle);
		
		//建立显示区面板
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "项目管理"));
		
		//加入JTable
		setTable(content, header);
		scrollPanel = new JScrollPane(table);
		displayPanel.add(scrollPanel, BorderLayout.CENTER);
		
		//加入功能区
		Box functionBox = Box.createVerticalBox();
		Box functionTop = Box.createVerticalBox();
		Box functionCenter = Box.createHorizontalBox();
		Box functionUnderCenterTop = Box.createVerticalBox();
		Box functionUnderCenter = Box.createHorizontalBox();
		Box functionBottom = Box.createVerticalBox();
		
		functionTop.add(Box.createVerticalStrut(10));
		functionUnderCenterTop.add(Box.createVerticalStrut(10));
		functionBottom.add(Box.createVerticalStrut(20));
		
		JLabel sift = new JLabel("筛选");
		sift.setFont(font);
		contactSiftKey.setText("");
		hideSign.setFont(font);
		hideEnded.setFont(font);
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewProjectInfo.setText("查看");
		viewProjectInfo.setFont(font);
		modifyProject.setText("修改");
		modifyProject.setFont(font);
		projectNotify.setText("项目提醒");
		projectNotify.setFont(font);
		statistics.setText("统计信息");
		statistics.setFont(font);
		addTrackRecord.setText("增加跟踪记录");
		addTrackRecord.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(contactSiftKey);
		functionCenter.add(Box.createHorizontalStrut(60));
		functionCenter.add(hideSign);
		functionCenter.add(Box.createHorizontalStrut(20));
		functionCenter.add(hideEnded);
		functionCenter.add(Box.createHorizontalStrut(100));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewProjectInfo);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyProject);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(projectNotify);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(statistics);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addTrackRecord);
		functionUnderCenter.add(Box.createHorizontalGlue());
		
		functionBox.add(functionTop);
		functionBox.add(functionCenter);
		functionBox.add(functionUnderCenterTop);
		functionBox.add(functionUnderCenter);
		functionBox.add(functionBottom);
		displayPanel.add(functionBox, BorderLayout.SOUTH);
		
		//建立提示区面板
		tips = Box.createHorizontalBox();
		tips.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "系统提示"));
		tips.add(Box.createHorizontalStrut(200));
		
		//加入系统提示
		// TODO 加入系统提示信息
		
		contentPanel.add(displayPanel, BorderLayout.CENTER);
		contentPanel.add(tips, BorderLayout.EAST);
		
		validate();
	}

	/**
	 * 保存
	 */
	private void save() {
		Function function = new Function();
		function.setFunctionDialog(new Save(SMFrame.this));
		function.create();
	}

	/**
	 * 打印
	 */
	private void print() {
		try {
			table.print();
		} catch (PrinterException e) {
			logger.warn("打印机未准备好. - " + e.getMessage() + SMFrame.class.getName());
		}
	}

	/**
	 * 查询
	 */
	private void search() {
		//调用查询窗口
		Function function = new Function();
		Search search = new Search(this, staffInfo);
		function.setFunctionDialog(search);
		function.create();
		if (search.isUpdate()) {
			int searched = search.getSearched();
			
			switch (searched) {
			case 0:
				break;
			case 1:
				break;
			case 3:
				contactManage();
				break;
			case 4:
				projectManage();
				break;
			case 5:
				break;
			}
			content = search.getResultContent();
			header = search.getResultHeader();
			displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "查询结果"));
			setTable(content, header);
		}
	}

	/**
	 * 打开帮助文件
	 */
	private void help() {
		Runtime rt = Runtime.getRuntime();
		try {
			logger.info("打开帮助文档. - " + SMFrame.class.getName());
			rt.exec("hh.exe help.chm");
			logger.info("打开帮助文档成功. - " + SMFrame.class.getName());
		} catch (IOException e) {
			logger.warn("打开帮助文档失败! - " + SMFrame.class.getName());
			JOptionPane.showMessageDialog(this, "系统无法打开帮助文档，请联系开发人员。", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 关于窗口
	 */
	private void about() {
		Function function = new Function();
		function.setFunctionDialog(new About(this));
		function.create();
	}

	/**
	 * 激活窗体
	 * @see com.p6.frames.Role#create()
	 */
	public void create() {
		setVisible(true);
	}
	
	/**
	 * 设置主操作面板table的内容
	 */
	public void setTable(Vector<Vector<String>> content, Vector<String> header) {
		table.setModel(new Table(content, header));
		new SortManager(table);
		selectedRows = null;
		Table.FitTableColumns(table);
		table.getTableHeader().setReorderingAllowed(false);
		table.updateUI();
	}
	
	/**
	 * 得到登陆系统的销售人员
	 * @return StaffInfo
	 */
	public StaffInfo getStaffInfo() {
		return staffInfo;
	}

	private JMenuBar menuBar = null; //菜单栏
	private JToolBar toolBar = null; //工具栏
	private Action saveAction = null; //保存动作
	private Action printAction = null; //打印动作
	private Action contactAction = null; //联系人管理动作
	private Action projectAction = null; //项目管理动作

	private JMenu file = null; //文件菜单
	private JMenu manage = null; //管理菜单
	private JMenu personal = null; //个人管理菜单
	private JMenu search = null; //查询菜单
	private JMenu help = null; //帮助菜单

	//文件菜单项
	private JMenuItem save = null; //保存
	private JMenuItem print = null; //打印
	private JMenuItem exit = null; //退出

	//管理菜单
	private JMenuItem contact = null; //联系人管理菜单
	private JMenuItem project = null; //项目管理菜单

	//联系人管理
	private JButton viewContactInfo = new JButton(); //查看联系人资料
	private JButton birthdayNotify = new JButton(); //生日提醒
	private JButton addContact = new JButton(); //新增联系人
	private JButton modifyContact = new JButton(); //修改联系人

	//项目管理
	private JButton viewProjectInfo = new JButton(); //查看项目资料
	private JButton projectNotify = new JButton(); //项目提醒
	private JButton modifyProject = new JButton(); //修改项目资料
	private JButton addTrackRecord = new JButton(); //新增跟踪记录

	//个人管理菜单
	private JMenuItem viewSelfInfo = null; //查看个人资料
	private JMenuItem modifySelfInfo = null; //修改个人资料
	private JMenuItem modifySelfPassword = null; //修改个人密码

	//高级菜单
	private JButton statistics = new JButton(); //统计信息

	//查询菜单
	private JMenuItem generalSearch = null; //查询
	private JMenuItem advancedSearch = null; //高级查询
	
	//屏蔽已签约项目
	private JCheckBox hideSign = new JCheckBox("屏蔽已签约项目");
	//屏蔽已终止项目
	private JCheckBox hideEnded = new JCheckBox("屏蔽已终止项目");

	//帮助菜单
	private JMenuItem helpDocument = null; //帮助文档
	private JMenuItem about = null; //关于

	private StaffInfo staffInfo = new StaffInfo(); //个人信息
	private ContactInfo contactInfo = new ContactInfo(); //联系人
	private ProjectInfo projectInfo = new ProjectInfo(); //项目

	private Font font = new Font("宋体", Font.PLAIN, 12);
	private JPanel panel = null; //总面板
	private JPanel contentPanel = null; //内容面板
	private JPanel displayPanel = null; //显示区面板
	private JLabel dateImg = null; //时间图片
	private JLabel date = null; //时间
	private JLabel week = null; //星期
	private JLabel nameImg = null; //姓名图片
	private JLabel name = null; //姓名
	private JLabel role = null; //角色
	private JTable table = new JTable();; //表格
	private JScrollPane scrollPanel = null; //滚动面板
	private JTextField contactSiftKey = new JTextField(); //联系人筛选
	private JTextField projectSiftKey = new JTextField(); //项目筛选
	private Box tips = null; //系统提示
	
	private Vector<Vector<String>> content = null;
	private Vector<String> header = null;
	private Vector<Vector<String>> contactContent = new Vector<Vector<String>>(); //联系人内容
	private Vector<String> contactTitle = new Vector<String>(); //联系人标头
	private Vector<Vector<String>> projectContent = new Vector<Vector<String>>(); //项目内容
	private Vector<String> projectTitle = new Vector<String>(); //项目标头
	private int[] selectedRows = null; //table中被选择的行

	private String title = "售前管理系统---Power by PowerSix";
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;

	//日志记录器
	private static Logger logger = Logger.getLogger(SMFrame.class);

	public static void main(String[] args) {
		SMFrame frame = new SMFrame(3);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.create();
	}
}
