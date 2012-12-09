/*
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.frames;

import javax.swing.*;

import org.apache.log4j.Logger;
import com.p6.frames.dialogs.Function;
import com.p6.frames.dialogs.contact.AddContact;
import com.p6.frames.dialogs.contact.ViewContact;
import com.p6.frames.dialogs.customer.AddCustomer;
import com.p6.frames.dialogs.customer.ViewCustomer;
import com.p6.frames.dialogs.other.*;
import com.p6.frames.dialogs.personal.*;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.SetInfoBean;
import com.p6.toolkit.SortManager;
import com.p6.toolkit.SystemProperties;
import com.p6.toolkit.Table;
import com.p6.toolkit.Today;
import com.p6.toolkit.VectorFilter;
import com.p6.user.infoBean.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

/**
 * 销售助理主操作窗口
 * 实现Role接口create()方法
 */
public class SAFrame extends JFrame implements Role {
	/**
	 * 构造函数
	 */
	public SAFrame(int staffID) {
		//设置窗体属性
		setTitle(title);
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		SystemProperties sp = SystemProperties.getInstance();
		setLocation(sp.getScreenWidth() / 8, sp.getScreenHeight() / 8);
		
		staffInfo = GetInfo.getStaffInfo(staffID);
		createMenu(); //创建菜单
	}
	
	/**
	 * 创建菜单
	 */
	public void createMenu() {
		//创建菜单栏
		menuBar = new JMenuBar();
		
		//创建菜单
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
		
		//为菜单创建快捷键
		file.setMnemonic('F');
		manage.setMnemonic('M');
		personal.setMnemonic('P');
		search.setMnemonic('S');
		help.setMnemonic('H');
		
		//创建文件菜单项
		exit = new JMenuItem("退出");
		
		//创建管理菜单项
		customer = new JMenuItem("客户管理(C)", 'C');
		contact = new JMenuItem("联系人管理(L)", 'L');
		
		//创建个人管理菜单项
		viewSelfInfo = new JMenuItem("查看个人资料(V)", 'V');
		modifySelfInfo = new JMenuItem("修改个人资料(M)", 'M');
		modifySelfPassword = new JMenuItem("修改个人密码(P)", 'P');
		
		//创建查询菜单项
		generalSearch = new JMenuItem("普通查询(S)", 'S');
		advancedSearch = new JMenuItem("高级查询(A)", 'A');
		
		//创建帮助菜单项
		helpDocument = new JMenuItem("帮助文档");
		about = new JMenuItem("关于我们");
		
		//设置菜单项字体
		exit.setFont(font);
		customer.setFont(font);
		contact.setFont(font);
		viewSelfInfo.setFont(font);
		modifySelfInfo.setFont(font);
		modifySelfPassword.setFont(font);
		generalSearch.setFont(font);
		advancedSearch.setFont(font);
		helpDocument.setFont(font);
		about.setFont(font);
		
		//设置菜单项图标
		exit.setIcon(new ImageIcon(SAFrame.class.getResource("/images/exit.gif")));
		customer.setIcon(new ImageIcon(SAFrame.class.getResource("/images/customer.gif")));
		contact.setIcon(new ImageIcon(SAFrame.class.getResource("/images/contact.gif")));
		viewSelfInfo.setIcon(new ImageIcon(SAFrame.class.getResource("/images/view.png")));
		modifySelfInfo.setIcon(new ImageIcon(SAFrame.class.getResource("/images/modify.png")));
		modifySelfPassword.setIcon(new ImageIcon(SAFrame.class.getResource("/images/password.png")));
		generalSearch.setIcon(new ImageIcon(SAFrame.class.getResource("/images/search.png")));
		advancedSearch.setIcon(new ImageIcon(SAFrame.class.getResource("/images/advancedSearch.png")));
		helpDocument.setIcon(new ImageIcon(SAFrame.class.getResource("/images/help.png")));
		about.setIcon(new ImageIcon(SAFrame.class.getResource("/images/aboutUS.png")));
		
		//为菜单项创建快捷键
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		helpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		
		//将所有元素加入框架
		setJMenuBar(menuBar);
		
		//加入菜单
		menuBar.add(file);
		menuBar.add(manage);
		menuBar.add(personal);
		menuBar.add(search);
		menuBar.add(help);
		
		//加入菜单项
		file.add(exit);
		manage.add(customer);
		manage.add(contact);
		personal.add(viewSelfInfo);
		personal.add(modifySelfInfo);
		personal.add(modifySelfPassword);
		search.add(generalSearch);
		search.add(advancedSearch);
		help.add(helpDocument);
		help.add(about);
		
		//加入总面板
		panel = new JPanel(new BorderLayout());
		add(panel);
		
		//加入工具栏
		toolBar = new JToolBar("ToolBar");
		panel.add(toolBar, BorderLayout.NORTH);
		
		//设置元素活动性
		advancedSearch.setEnabled(false);
		
		
		//设置工具栏及其事件响应
		customerAction = new 
			AbstractAction("客户管理", new ImageIcon(SAFrame.class.getResource("/images/TCustomer.gif"))) {

				public void actionPerformed(ActionEvent e) {
					customerManage();
				}
		};
		
		contactAction = new
			AbstractAction("联系人管理", new ImageIcon(SAFrame.class.getResource("/images/TContact.gif"))) {
				public void actionPerformed(ActionEvent e) {
					contactManage();
				}	
		};
		
		Action searchAction = new 
			AbstractAction("查询", new ImageIcon(SAFrame.class.getResource("/images/TSearch.gif"))) {

				public void actionPerformed(ActionEvent e) {
					search();
				}
		};
		
		Action helpAction = new 
			AbstractAction("帮助文档", new ImageIcon(SAFrame.class.getResource("/images/THelp.gif"))) {

				public void actionPerformed(ActionEvent e) {
					help();
				}
			
		};
		
		Action aboutAction = new 
			AbstractAction("关于我们", new ImageIcon(SAFrame.class.getResource("/images/TAboutUS.gif"))) {

				public void actionPerformed(ActionEvent e) {
					about();
				}
			
		};
		
		Action exitAction = new 
			AbstractAction("退出系统", new ImageIcon(SAFrame.class.getResource("/images/TExit.gif"))) {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			
		};
		
		//将图标加入工具栏
		customerAction.putValue(Action.SHORT_DESCRIPTION, "客户管理");
		contactAction.putValue(Action.SHORT_DESCRIPTION, "联系人管理");
		searchAction.putValue(Action.SHORT_DESCRIPTION, "查询");
		helpAction.putValue(Action.SHORT_DESCRIPTION, "帮助文档");
		aboutAction.putValue(Action.SHORT_DESCRIPTION, "关于我们");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "退出系统");
		toolBar.add(customerAction);
		toolBar.add(contactAction);
		toolBar.add(searchAction);
		toolBar.add(helpAction);
		toolBar.add(aboutAction);
		toolBar.add(exitAction);
		toolBar.setFont(font);
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		//信息栏
		contentPanel = new JPanel(new BorderLayout());
		panel.add(contentPanel, BorderLayout.CENTER);
		
		Box message = Box.createVerticalBox();
		message.setBorder(BorderFactory.createEtchedBorder());
		
		dateImg = new JLabel(new ImageIcon(SAFrame.class.getResource("/images/calendar.gif")));
		String today = Today.getYear() + "年" + Today.getMonth() + "月" + Today.getDate() + "日";
		date = new JLabel(today);
		date.setFont(font);
		week = new JLabel(Today.getWeek());
		week.setFont(font);
		nameImg = new JLabel(new ImageIcon(SAFrame.class.getResource("/images/role.png")));
		name = new JLabel(staffInfo.getStaffName());
		name.setFont(font);
		role = new JLabel("销售助理");
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
		
		//菜单栏事件处理程序段
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		//客户管理事件处理
		customer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customerManage();
			}
			
		});
		
		//联系人管理事件处理
		contact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contactManage();
			}
			
		});
		
		//查看个人资料事件处理
		viewSelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ViewSelfInfo(SAFrame.this, staffInfo));
				function.create();
			}
			
		});
		
		//修改个人资料事件处理
		modifySelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfInfo(SAFrame.this, staffInfo));
				function.create();
			}
			
		});
		
		//修改个人密码事件处理
		modifySelfPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfPwd(SAFrame.this));
				function.create();
			}
			
		});
		
		//普通查询事件处理
		generalSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		//高级查询事件处理
		advancedSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				search();
			}
			
		});
		
		//帮助文档事件处理
		helpDocument.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				help();
			}
			
		});
		
		//关于我们事件处理
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});
		
		//查看客户资料
		viewCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows== null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何客户, 请选择一个进行查看.", "提示", SAFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个客户, 请只选择一个进行查看.", "提示", SAFrame.this);
				}
				else {
					SetInfoBean.setCustomer(customerInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewCustomer viewCustomer = new ViewCustomer(SAFrame.this, customerInfo);
					function.setFunctionDialog(viewCustomer);
					function.create();
				}
				
			}
			
		});
		
		//增加客户事件响应
		addCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddCustomer addCustomer = new AddCustomer(SAFrame.this);
				function.setFunctionDialog(addCustomer);
				function.create();
				if (addCustomer.isUpdate() == true) {
					customerContent = GetInfo.getAllCustomer();
					content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, customerContent);
					setTable(content, header);
				}
			}
			
		});
		
		//查看联系人
		viewContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何联系人, 请选择一个进行查看.", "提示", SAFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个联系人, 请只选择一个进行查看.", "提示", SAFrame.this);
				}
				else {
					SetInfoBean.setContact(contactInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewContact viewContact = new ViewContact(SAFrame.this, contactInfo);
					function.setFunctionDialog(viewContact);
					function.create();
				}
				
			}
			
		});
		
		//增加联系人事件响应
		addContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddContact addContact = new AddContact(SAFrame.this);
				function.setFunctionDialog(addContact);
				function.create();
				if (addContact.isUpdate() == true) {
					contactContent = GetInfo.getAllContact();
					content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, customerContent);	
					setTable(content, header);
				}
			}
			
		});
		
		//客户筛选事件处理
		customerSiftKey.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				char c = Character.toLowerCase(e.getKeyChar());
				StringBuffer str = new StringBuffer();
				str.append(customerSiftKey.getText());
				if (!Character.isISOControl(c)) {
					str.append(c);
				}
				
				content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, VectorFilter.Filter(str.toString(), customerContent));
				setTable(content, header);
			
			}
			
		});
		
		//联系人筛选事件处理
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
		
		//表格鼠标事件响应
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRows = table.getSelectedRows();
				logger.debug("Begin - " + SAFrame.class.getName());
				 for (int row : selectedRows) {
					 logger.debug("Selected Rows: " + row); 
				 }
				 logger.debug("End - " + SAFrame.class.getName());
			}
			
		});
		
	}
	
	
	/**
	 * 客户管理
	 */
	private void customerManage() {	
		//删除原有内容
		if (displayPanel != null && tips != null) {
			contentPanel.remove(displayPanel);
			contentPanel.remove(tips);
			validate();
			repaint();
		}
		
		customerContent = GetInfo.getAllCustomer();
		customerTitle = GetInfo.getCustomerTitle();
		
		content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, customerContent);
		header = VectorFilter.singleFilter(VectorFilter.CUSTOMERFILTER, customerTitle);
		
		//建立显示区面板
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "客户管理"));
		
		//加入JTable
		setTable(content, header);
		scrollPanel = new JScrollPane(table);
		displayPanel.add(scrollPanel, BorderLayout.CENTER);
		
		//加入功能区
		Box functionBox = Box.createVerticalBox();
		Box functionTop = Box.createVerticalBox();
		Box functionCenter = Box.createHorizontalBox();
		Box functionBottom = Box.createVerticalBox();
		
		functionTop.add(Box.createVerticalStrut(10));
		functionBottom.add(Box.createVerticalStrut(20));
		
		JLabel sift = new JLabel("筛选");
		sift.setFont(font);
		customerSiftKey.setText("");
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewCustomer.setText("查看");
		viewCustomer.setFont(font);
		addCustomer.setText("增加");
		addCustomer.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(customerSiftKey);
		functionCenter.add(Box.createHorizontalStrut(20));
		functionCenter.add(fun);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(viewCustomer);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(addCustomer);
		functionCenter.add(Box.createHorizontalStrut(180));
		
		functionBox.add(functionTop);
		functionBox.add(functionCenter);
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
	 * 联系人管理
	 */
	private void contactManage() {	
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
		Box functionBottom = Box.createVerticalBox();
		
		functionTop.add(Box.createVerticalStrut(10));
		functionBottom.add(Box.createVerticalStrut(20));
		
		JLabel sift = new JLabel("筛选");
		sift.setFont(font);
		contactSiftKey.setText("");
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewContact.setText("查看");
		viewContact.setFont(font);
		addContact.setText("增加");
		addContact.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(contactSiftKey);
		functionCenter.add(Box.createHorizontalStrut(20));
		functionCenter.add(fun);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(viewContact);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(addContact);
		functionCenter.add(Box.createHorizontalStrut(180));
		
		functionBox.add(functionTop);
		functionBox.add(functionCenter);
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
				customerManage();
				break;
			case 3:
				contactManage();
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
			logger.info("打开帮助文档. - " + SAFrame.class.getName());
			rt.exec("hh.exe help.chm");
			logger.info("打开帮助文档成功. - " + SAFrame.class.getName());
		} catch (IOException e) {
			logger.error("打开帮助文档失败! - " + SAFrame.class.getName());
			JOptionPane.showMessageDialog(this, "系统无法打开帮助文档，请联系开发人员。", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 *关于我们 
	 */
	private void about() {
		Function function = new Function();
		function.setFunctionDialog(new About(this));
		function.create();
	}
	
	/**
	 * create()方法激活窗体
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
	private Action customerAction = null; //客户管理动作
	private Action contactAction = null; //联系人管理动作
	
	//菜单
	private JMenu file = null; //文件
	private JMenu manage = null; //管理
	private JMenu personal = null; //个人管理
	private JMenu search = null; //查询
	private JMenu help = null; //帮助
	
	//文件菜单项
	private JMenuItem exit = null; //退出
	
	//管理菜单
	private JMenuItem customer = null; //客户管理
	private JMenuItem contact = null; //联系人管理
	
	//客户管理菜单项
	private JButton viewCustomer = new JButton(); //查看客户
	private JButton addCustomer = new JButton(); //增加客户
	private JButton viewContact = new JButton(); //查看联系人
	private JButton addContact = new JButton(); //增加联系人
	
	//个人管理菜单项
	private JMenuItem viewSelfInfo = null; //查看个人资料
	private JMenuItem modifySelfInfo = null; //修改个人资料
	private JMenuItem modifySelfPassword = null; //修改个人密码
	
	//查询菜单项
	private JMenuItem generalSearch = null; //一般查询
	private JMenuItem advancedSearch = null; //高级查询
	
	//帮助菜单项
	private JMenuItem helpDocument = null; //帮助文档
	private JMenuItem about = null; //关于
	
	private StaffInfo staffInfo = new StaffInfo(); //个人信息
	private CustomerInfo customerInfo = new CustomerInfo(); //客户信息
	private ContactInfo contactInfo = new ContactInfo(); //联系人信息
	
	private Font font = new Font("宋体", Font.PLAIN, 12);
	private String title = "售前管理系统---Power by PowerSix";
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
	private JTextField customerSiftKey = new JTextField(); //客户筛选
	private JTextField contactSiftKey = new JTextField(); //联系人筛选
	private Box tips = null; //系统提示
	
	private Vector<Vector<String>> content = null;
	private Vector<String> header = null;
	private Vector<Vector<String>> customerContent = new Vector<Vector<String>>(); //客户
	private Vector<String> customerTitle = new Vector<String>();
	private Vector<Vector<String>> contactContent = new Vector<Vector<String>>(); //联系人
	private Vector<String> contactTitle = new Vector<String>();
	private int[] selectedRows = null;
	
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	//日志记录器
	private static Logger logger = Logger.getLogger(SAFrame.class);
	
	public static void main(String[] args) {
		SAFrame frame = new SAFrame(4);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.create();
	}
}
