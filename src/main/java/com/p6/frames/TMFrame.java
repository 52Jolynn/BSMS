package com.p6.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.p6.frames.dialogs.Function;
import com.p6.frames.dialogs.other.*;
import com.p6.frames.dialogs.personal.*;
import com.p6.frames.dialogs.staff.AddStaff;
import com.p6.frames.dialogs.staff.DeleteStaff;
import com.p6.frames.dialogs.staff.ModifyStaff;
import com.p6.frames.dialogs.staff.SetPwd;
import com.p6.frames.dialogs.staff.ViewStaff;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.SetInfoBean;
import com.p6.toolkit.SortManager;
import com.p6.toolkit.SystemProperties;
import com.p6.toolkit.Table;
import com.p6.toolkit.Today;
import com.p6.toolkit.VectorFilter;
import com.p6.user.infoBean.StaffInfo;

public class TMFrame extends JFrame implements Role {

	/**
	 *构造函数 
	 */
	public TMFrame(int staffID) {
		//设置框架属性
		setTitle(title);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setResizable(false);
		SystemProperties sp = SystemProperties.getInstance();
		setLocation(sp.getScreenWidth() / 8, sp.getScreenHeight() / 8);
		
		staffInfo = GetInfo.getStaffInfo(staffID);
		createMenu();
		createToolBar();
		createContent();
	}
	
	/**
	 * 创建菜单
	 */
	private void createMenu() {
		//建立菜单栏
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//建立菜单
		file = new JMenu("文件(F)");
		manage = new JMenu("管理(M)");
		personal = new JMenu("个人管理(P)");
		advanced = new JMenu("高级(A)");
		search = new JMenu("查询(S)");
		help = new JMenu("帮助(H)");
		
		//设置菜单字体
		file.setFont(font);
		manage.setFont(font);
		personal.setFont(font);
		advanced.setFont(font);
		search.setFont(font);
		help.setFont(font);
		
		//为菜单建立快捷键
		file.setMnemonic('F');
		manage.setMnemonic('M');
		personal.setMnemonic('P');
		advanced.setMnemonic('A');
		search.setMnemonic('S');
		help.setMnemonic('H');
		
		//建立菜单项
		//文件菜单项
		save = new JMenuItem("保存");
		print = new JMenuItem("打印");
		exit = new JMenuItem("退出");
		
		//设置文件菜单项字体
		save.setFont(font);
		print.setFont(font);
		exit.setFont(font);
		
		//设置图标
		save.setIcon(new ImageIcon(TMFrame.class.getResource("/images/save.gif")));
		print.setIcon(new ImageIcon(TMFrame.class.getResource("/images/print.gif")));
		exit.setIcon(new ImageIcon(TMFrame.class.getResource("/images/exit.gif")));
		
		//为文件菜单项设置快捷键
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		
		//管理菜单项
		DMManage = new JMenuItem("人员管理(P)", 'P');
		DMManage.setFont(font);
		
		//设置图标
		DMManage.setIcon(new ImageIcon(TMFrame.class.getResource("/images/staff.gif")));
		
		//个人管理菜单项
		viewSelfInfo = new JMenuItem("查看个人资料(V)", 'V');
		modifySelfInfo = new JMenuItem("修改个人资料(M)", 'M');
		modifySelfPassword = new JMenuItem("修改个人密码(P)", 'P');
		
		//设置个人菜单项字体
		viewSelfInfo.setFont(font);
		modifySelfInfo.setFont(font);
		modifySelfPassword.setFont(font);
		
		//设置图标
		viewSelfInfo.setIcon(new ImageIcon(TMFrame.class.getResource("/images/view.png")));
		modifySelfInfo.setIcon(new ImageIcon(TMFrame.class.getResource("/images/modify.png")));
		modifySelfPassword.setIcon(new ImageIcon(TMFrame.class.getResource("/images/password.png")));
		
		//高级菜单项
		backup = new JMenuItem("备份数据库(B)", 'B');
		restore = new JMenuItem("恢复数据库(R)", 'R');
		
		//设置高级菜单项字体
		backup.setFont(font);
		restore.setFont(font);
		
		//设置图标
		backup.setIcon(new ImageIcon(TMFrame.class.getResource("/images/backup.gif")));
		restore.setIcon(new ImageIcon(TMFrame.class.getResource("/images/restore.gif")));
		
		//查询菜单项
		generalSearch = new JMenuItem("普通查询(S)", 'S');
		advancedSearch = new JMenuItem("高级查询(A)", 'A');
		
		//设置查询菜单项字体
		generalSearch.setFont(font);
		advancedSearch.setFont(font);
		
		//设置图标
		generalSearch.setIcon(new ImageIcon(TMFrame.class.getResource("/images/search.png")));
		advancedSearch.setIcon(new ImageIcon(TMFrame.class.getResource("/images/advancedSearch.png")));
		
		//帮助菜单项
		helpDocument = new JMenuItem("帮助文档");
		about = new JMenuItem("关于我们");
		
		//设置帮助菜单项字体
		helpDocument.setFont(font);
		about.setFont(font);
		
		//设置图标
		helpDocument.setIcon(new ImageIcon(TMFrame.class.getResource("/images/help.png")));
		about.setIcon(new ImageIcon(TMFrame.class.getResource("/images/aboutUS.png")));
		
		//为帮助菜单项设置快捷键
		helpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		
		//事件处理程序段
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
		
		//人员管理
		DMManage.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DMManage();
				
			}
			
		});
		
		//查看个人资料
		viewSelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ViewSelfInfo(TMFrame.this, staffInfo));
				function.create();
			}
			
		});
		
		//修改个人资料
		modifySelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfInfo(TMFrame.this, staffInfo));
				function.create();
				
			}
			
		});
		
		//修改个人密码
		modifySelfPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfPwd(TMFrame.this));
				function.create();
				
			}
			
		});
		
		//备份数据库
		backup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				backup();
				
			}
			
		});
		
		//恢复数据库
		restore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				restore();
				
			}
			
		});
		
		//普通查询
		generalSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				search();
				
			}
			
		});
		
		//高级查询
		advancedSearch.addActionListener(new ActionListener() {

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
		
		//将所有元素加入框架中
		menuBar.add(file);
		menuBar.add(manage);
		menuBar.add(personal);
		menuBar.add(advanced);
		menuBar.add(search);
		menuBar.add(help);
		
		file.add(save);
		file.add(print);
		file.add(exit);
		
		manage.add(DMManage);
		
		personal.add(viewSelfInfo);
		personal.add(modifySelfInfo);
		personal.add(modifySelfPassword);
		
		advanced.add(backup);
		advanced.add(restore);
		
		search.add(generalSearch);
		search.add(advancedSearch);
		
		help.add(helpDocument);
		help.add(about);
		
		//设置菜单活动性
		save.setEnabled(false);
		print.setEnabled(false);
		advancedSearch.setEnabled(false);
		backup.setEnabled(false);
		restore.setEnabled(false);
		
		//按钮事件处理程序段
		//查看销售人员
		viewDM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何销售人员, 请选择一个进行查看.", "提示", TMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个销售人员, 请只选择一个进行查看.", "提示", TMFrame.this);
				}
				else {
					SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[0]));
					Function function  = new Function();
					ViewStaff viewStaff = new ViewStaff(TMFrame.this, selectedStaffInfo);
					function.setFunctionDialog(viewStaff);
					function.create();
				}
			}
			
		});
		
		//增加销售人员
		addDM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddStaff addStaff = new AddStaff(TMFrame.this);
				function.setFunctionDialog(addStaff);
				function.create();
				if (addStaff.isUpdate() == true) {
					staffContent = GetInfo.getStaffInfo();
					content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, staffContent);
					setTable(content, header);
				}
			}
			
		});
		
		//删除销售人员
		deleteDM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何销售人员, 请选择至少一个进行删除.", "提示", TMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[i]));
						DeleteStaff deleteStaff = new DeleteStaff(TMFrame.this, selectedStaffInfo);
						function.setFunctionDialog(deleteStaff);
						//function.create();
						if (isUpdate == false && deleteStaff.isUpdate() == true) {
							isUpdate = true;
						}
					}
					if (isUpdate == true) {
						staffContent = GetInfo.getStaffInfo();
						content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, staffContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//修改销售人员
		modifyDM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何销售人员, 请选择一个进行修改.", "提示", TMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个销售人员, 请只选择一个进行修改.", "提示", TMFrame.this);
				}
				else {
					SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyStaff modifyStaff = new ModifyStaff(TMFrame.this, selectedStaffInfo); 
					function.setFunctionDialog(modifyStaff);
					function.create();
					if (modifyStaff.isUpdate()) {
						staffContent = GetInfo.getStaffInfo();
						content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, staffContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//修改销售人员密码
		modifyDMPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				SetPwd setPassword = new SetPwd(TMFrame.this);
				function.setFunctionDialog(setPassword);
				function.create();
			}
			
			
		});
		
		//销售人员筛选
		siftKey.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				char c = Character.toLowerCase(e.getKeyChar());
				StringBuffer str = new StringBuffer();
				str.append(siftKey.getText());
				
				if (!Character.isISOControl(c)) {
					str.append(c);
				}
				
				content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, VectorFilter.Filter(str.toString(), staffContent));
				setTable(content, header);
			}
			
		});
		
		//table选择事件处理
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();
				logger.debug("Begin - " + TMFrame.class.getName());
				for (int row: selectedRows) {
					logger.debug("Selected Rows: " + row);
				}
				logger.debug("End - " + TMFrame.class.getName());
				
			}
			
		});
		
		//屏蔽已离职人员
		hideOutDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (hideOutDate.isSelected()) {
					Vector<Vector<String>> hide = new Vector<Vector<String>>();
					hide = VectorFilter.outDateFilter(content);
					setTable(hide, header);
				}
				else {
					setTable(content, header);
				}
				
			}
			
		});
	}
	
	/**
	 * 创建工具栏
	 */
	private void createToolBar() {
		panel = new JPanel(new BorderLayout());
		
		//加入工具栏
		toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		//保存
		saveAction = new AbstractAction("保存", new ImageIcon(TMFrame.class.getResource("/images/TSave.gif"))) {

			public void actionPerformed(ActionEvent e) {
				save();
				
			}
			
		};
		
		//打印
		printAction = new AbstractAction("打印", new ImageIcon(TMFrame.class.getResource("/images/TPrint.gif"))) {

			public void actionPerformed(ActionEvent e) {
				print();
				
			}
			
		};
		
		//人员管理
		DMManageAction = new AbstractAction("人员管理", new ImageIcon(TMFrame.class.getResource("/images/TStaff.gif"))) {

			public void actionPerformed(ActionEvent e) {
				DMManage();
				
			}
			
		};
		
		//备份数据库
		Action backupAction = new AbstractAction("备份数据库", new ImageIcon(TMFrame.class.getResource("/images/TBackup.gif"))) {

			public void actionPerformed(ActionEvent e) {
				backup();
				
			}
			
		};
		
		//恢复数据库
		Action restoreAction = new AbstractAction("恢复数据库", new ImageIcon(TMFrame.class.getResource("/images/TRestore.gif"))) {

			public void actionPerformed(ActionEvent e) {
				restore();
				
			}
			
		};
		
		Action searchAction = new AbstractAction("查询", new ImageIcon(TMFrame.class.getResource("/images/TSearch.gif"))) {

			public void actionPerformed(ActionEvent e) {
				search();
				
			}
			
		};
		
		//帮助文档
		Action helpAction = new AbstractAction("帮助", new ImageIcon(TMFrame.class.getResource("/images/THelp.gif"))) {

			public void actionPerformed(ActionEvent e) {
				help();
				
			}
			
		};
		
		//关于我们
		Action aboutAction = new AbstractAction("关于我们", new ImageIcon(TMFrame.class.getResource("/images/TAboutUS.gif"))) {

			public void actionPerformed(ActionEvent e) {
				about();
				
			}
			
		};
		
		Action exitAction = new AbstractAction("退出", new ImageIcon(TMFrame.class.getResource("/images/TExit.gif"))) {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		};

		//为工具栏图标加入提示
		saveAction.putValue(Action.SHORT_DESCRIPTION, "保存");
		printAction.putValue(Action.SHORT_DESCRIPTION, "打印");
		DMManageAction.putValue(Action.SHORT_DESCRIPTION, "人员管理");
		backupAction.putValue(Action.SHORT_DESCRIPTION, "备份数据库");
		restoreAction.putValue(Action.SHORT_DESCRIPTION, "恢复数据库");
		searchAction.putValue(Action.SHORT_DESCRIPTION, "查询");
		helpAction.putValue(Action.SHORT_DESCRIPTION, "帮助文档");
		aboutAction.putValue(Action.SHORT_DESCRIPTION, "关于我们");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "退出系统");
		
		toolBar.add(saveAction);
		toolBar.add(printAction);
		toolBar.add(DMManageAction);
		toolBar.add(backupAction);
		toolBar.add(restoreAction);
		toolBar.add(searchAction);
		toolBar.add(helpAction);
		toolBar.add(aboutAction);
		toolBar.add(exitAction);
		
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		add(panel);
		
		//设置图标活动性
		saveAction.setEnabled(false);
		printAction.setEnabled(false);
		backupAction.setEnabled(false);
		restoreAction.setEnabled(false);
	}
	
	/**
	 * 生成面板内容
	 */
	private void createContent() {
		contentPanel = new JPanel(new BorderLayout());
		panel.add(contentPanel, BorderLayout.CENTER);
		
		Box message = Box.createVerticalBox();
		message.setBorder(BorderFactory.createEtchedBorder());
		
		dateImg = new JLabel(new ImageIcon(TMFrame.class.getResource("/images/calendar.gif")));
		String today = Today.getYear() + "年" + Today.getMonth() + "月" + Today.getDate() + "日";
		date = new JLabel(today);
		date.setFont(font);
		week = new JLabel(Today.getWeek());
		week.setFont(font);
		nameImg = new JLabel(new ImageIcon(TMFrame.class.getResource("/images/role.png")));
		name = new JLabel(staffInfo.getStaffName());
		name.setFont(font);
		role = new JLabel("总经理");
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
	 * 保存
	 */
	private void save() {
		Function function = new Function();
		function.setFunctionDialog(new Save(TMFrame.this));
		function.create();
	}
	
	/**
	 * 打印
	 */
	private void print() {
		try {
			table.print();
		} catch (PrinterException e) {
			logger.warn("打印机未准备好. - " + e.getMessage() + TMFrame.class.getName());
		}
	}
	
	/**
	 * 人员管理
	 */
	private void DMManage() {
		print.setEnabled(true);
		printAction.setEnabled(true);	
			
		//删除原有内容
		if (displayPanel != null && tips != null) {
			contentPanel.remove(displayPanel);
			contentPanel.remove(tips);
			validate();
			repaint();
		}
		
		staffContent = GetInfo.getStaffInfo();
		staffTitle = GetInfo.getStaffTitle();
		
		content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, staffContent);
		content = VectorFilter.dataFilter(1, staffInfo.getStaffName(), content); //过滤自己
		content = VectorFilter.positionFilter(4, "部门经理", content); //过滤得到部门经理
		header = VectorFilter.singleFilter(VectorFilter.STAFFFILTER, staffTitle);
		
		//建立显示区面板
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "人员管理"));
		
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
		siftKey.setText("");
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewDM.setText("查看");
		viewDM.setFont(font);
		addDM.setText("增加");
		addDM.setFont(font);
		deleteDM.setText("删除");
		deleteDM.setFont(font);
		modifyDM.setText("修改");
		modifyDM.setFont(font);
		modifyDMPassword.setText("设置密码");
		modifyDMPassword.setFont(font);
		hideOutDate.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(siftKey);
		functionCenter.add(Box.createHorizontalStrut(100));
		functionCenter.add(hideOutDate);
		functionCenter.add(Box.createHorizontalStrut(180));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewDM);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addDM);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(deleteDM);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyDM);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyDMPassword);
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
	 * 备份数据库
	 */
	private void backup() {
		Function function = new Function();
		function.setFunctionDialog(new Backup(TMFrame.this));
		function.create();
	}
	
	/**
	 * 恢复数据库
	 */
	private void restore() {
		Function function = new Function();
		function.setFunctionDialog(new Restore(TMFrame.this));
		function.create();
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
				DMManage();
				break;
			case 1:
				break;
			case 3:
				break;
			case 4:
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
	 * 帮助文档
	 */
	private void help() {
		Runtime rt = Runtime.getRuntime();
		try {
			logger.info("打开帮助文档. - " + TMFrame.class.getName());
			rt.exec("hh.exe help.chm");
			logger.info("打开帮助文档成功. - " + TMFrame.class.getName());
		} catch (IOException e) {
			logger.error("打开帮助文档失败! - " + TMFrame.class.getName());
			JOptionPane.showMessageDialog(this, "系统无法打开帮助文档，请联系开发人员。", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 关于我们
	 */
	private void about() {
		Function function = new Function();
		function.setFunctionDialog(new About(TMFrame.this));
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
	private Action DMManageAction = null; //销售人员
	
	private JMenu file = null; //文件菜单
	private JMenu manage = null; //管理菜单
	private JMenu personal = null; //个人菜单
	private JMenu advanced = null; //高级菜单
	private JMenu search = null; //查询菜单
	private JMenu help = null; //帮助菜单
	
	private JMenuItem save = null; //保存
	private JMenuItem print = null; //打印
	private JMenuItem exit = null; //退出
	
	private JMenuItem DMManage = null; //人员管理
	private JButton viewDM = new JButton(); //查看
	private JButton addDM = new JButton(); //新增
	private JButton deleteDM = new JButton(); //删除
	private JButton modifyDM = new JButton(); //修改
	private JButton modifyDMPassword = new JButton(); //设置密码
	private JCheckBox hideOutDate = new JCheckBox("屏蔽已离职人员");
	
	private JMenuItem viewSelfInfo = null; //查看个人资料
	private JMenuItem modifySelfInfo = null; //修改个人资料
	private JMenuItem modifySelfPassword = null; //修改个人密码
	
	private JMenuItem backup = null; //备份数据库
	private JMenuItem restore = null; //恢复数据库
	
	private JMenuItem generalSearch = null; //普通查询
	private JMenuItem advancedSearch = null; //高级查询
	
	private JMenuItem helpDocument = null; //帮助文档
	private JMenuItem about = null; //关于我们
	
	private JPanel panel = null; //总面板
	private JPanel contentPanel = null; //内容面板
	private JPanel displayPanel = null; //显示区面板
	private Box tips = null; //系统提示
	private JLabel dateImg = null; //时间图片
	private JLabel date = null; //时间
	private JLabel week = null; //星期
	private JLabel nameImg = null; //姓名图片
	private JLabel name = null; //姓名
	private JLabel role = null; //角色
	private JTable table = new JTable();; //表格
	private JScrollPane scrollPanel = null; //滚动面板
	private JTextField siftKey = new JTextField(); //筛选
	
	private StaffInfo staffInfo = new StaffInfo(); //销售人员信息
	private StaffInfo selectedStaffInfo = new StaffInfo(); //被选中的销售人员信息
	private Vector<Vector<String>> content = null;
	private Vector<String> header = null;
	private Vector<Vector<String>> staffContent = new Vector<Vector<String>>();
	private Vector<String> staffTitle = new Vector<String>();
	private int[] selectedRows = null; //table中被选择的行

	private Font font = new Font("宋体", Font.PLAIN, 12);
	private String title = "售前管理系统---Power by PowerSix";
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	private static Logger logger = Logger.getLogger(TMFrame.class);
	
	public static void main(String[] args) {
		TMFrame frame = new TMFrame(1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.create();
	}
}
