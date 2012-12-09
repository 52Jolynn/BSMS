/**
 * @author: Laud
 * @version: 1.0.0
 * @date: 2007-12-1
 * copyright powersix 2007
 */
package com.p6.frames;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;
import com.p6.frames.dialogs.Function;
import com.p6.frames.dialogs.contact.*;
import com.p6.frames.dialogs.customer.*;
import com.p6.frames.dialogs.other.*;
import com.p6.frames.dialogs.personal.*;
import com.p6.frames.dialogs.project.*;
import com.p6.frames.dialogs.staff.*;
import com.p6.frames.dialogs.track.*;
import com.p6.toolkit.*;
import com.p6.user.infoBean.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Vector;

/**
 * 部门经理主操作界面
 * 实现Role接口的create方法
 */
public class DMFrame extends JFrame implements Role {
	/**
	 * 构造函数
	 */
	public DMFrame(int staffID) {
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
	 * 创建菜单栏
	 */
	private void createMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
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
		
		
		//创建菜单项
		//文件菜单项
		save = new JMenuItem("保存", new ImageIcon(DMFrame.class.getResource("/images/save.gif")));
		print = new JMenuItem("打印", new ImageIcon(DMFrame.class.getResource("/images/print.gif")));
		exit = new JMenuItem("退出", new ImageIcon(DMFrame.class.getResource("/images/exit.gif")));
		
		//设置快捷键
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		
		//设置文件菜单项字体
		save.setFont(font);
		print.setFont(font);
		exit.setFont(font);
		
		//管理菜单项
		staff = new JMenuItem("人员管理(S)", new ImageIcon(DMFrame.class.getResource("/images/staff.gif")));
		customer = new JMenuItem("客户管理(C)", new ImageIcon(DMFrame.class.getResource("/images/customer.gif")));
		contact = new JMenuItem("联系人管理(L)", new ImageIcon(DMFrame.class.getResource("/images/contact.gif")));
		project = new JMenuItem("项目管理(P)", new ImageIcon(DMFrame.class.getResource("/images/project.gif")));
		trackRecord = new JMenuItem("跟踪记录管理(T)", new ImageIcon(DMFrame.class.getResource("/images/trackRecord.gif")));
		
		//设置管理菜单项快捷键
		staff.setMnemonic('S');
		customer.setMnemonic('C');
		contact.setMnemonic('L');
		project.setMnemonic('P');
		trackRecord.setMnemonic('T');
		
		//设置管理菜单项字体
		staff.setFont(font);
		customer.setFont(font);
		contact.setFont(font);
		project.setFont(font);
		trackRecord.setFont(font);
		
		//个人管理菜单项
		viewSelfInfo = new JMenuItem("查看个人资料(V)", new ImageIcon(DMFrame.class.getResource("/images/view.png")));
		modifySelfInfo = new JMenuItem("修改个人资料(M)", new ImageIcon(DMFrame.class.getResource("/images/modify.png")));
		modifySelfPassword = new JMenuItem("修改个人密码(P)", new ImageIcon(DMFrame.class.getResource("/images/password.png")));
		
		//设置个人管理菜单项快捷键
		viewSelfInfo.setMnemonic('V');
		modifySelfInfo.setMnemonic('M');
		modifySelfPassword.setMnemonic('P');
		
		//设置个人管理菜单项字体
		viewSelfInfo.setFont(font);
		modifySelfInfo.setFont(font);
		modifySelfPassword.setFont(font);
		
		//查询菜单项
		generalSearch = new JMenuItem("普通查询(S)", new ImageIcon(DMFrame.class.getResource("/images/search.png")));
		advancedSearch = new JMenuItem("高级查询(A)", new ImageIcon(DMFrame.class.getResource("/images/advancedSearch.png")));
		
		//设置查询菜单项快捷键
		generalSearch.setMnemonic('S');
		advancedSearch.setMnemonic('A');
		
		//设置查询菜单项字体
		generalSearch.setFont(font);
		advancedSearch.setFont(font);
		
		//帮助菜单项
		helpDocument = new JMenuItem("帮助文档", new ImageIcon(DMFrame.class.getResource("/images/help.png")));
		about = new JMenuItem("关于我们", new ImageIcon(DMFrame.class.getResource("/images/aboutUS.png")));
		
		//设置帮助菜单项快捷键
		helpDocument.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		
		//设置帮助菜单项字体
		helpDocument.setFont(font);
		about.setFont(font);
		
		//将所有元素加入菜单栏
		menuBar.add(file);
		menuBar.add(manage);
		menuBar.add(personal);
		menuBar.add(search);
		menuBar.add(help);
		
		//加入文件菜单项
		file.add(save);
		file.add(print);
		file.add(exit);
		
		//加入管理菜单项
		manage.add(staff);
		manage.add(customer);
		manage.add(contact);
		manage.add(project);
		manage.add(trackRecord);
		
		//加入个人管理菜单项
		personal.add(viewSelfInfo);
		personal.add(modifySelfInfo);
		personal.add(modifySelfPassword);
		
		//加入查询菜单项
		search.add(generalSearch);
		search.add(advancedSearch);
		
		//加入帮助菜单项
		help.add(helpDocument);
		help.add(about);
		
		//设置菜单项活动性
		save.setEnabled(false);
		print.setEnabled(false);
		advancedSearch.setEnabled(false);
		
		//设置事件处理
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
		staff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				staff();
				
			}
			
		});
		
		//客户管理
		customer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customer();
				
			}
			
		});
		
		//联系人管理
		contact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contact();
				
			}
			
		});
		
		//项目管理
		project.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				project();
				
			}
			
		});
		
		//跟踪记录管理
		trackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				trackRecord();
				
			}
			
		});
		
		//查看个人资料
		viewSelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ViewSelfInfo(DMFrame.this, staffInfo));
				function.create();
				
			}
			
		});
		
		//修改个人资料
		modifySelfInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfInfo(DMFrame.this, staffInfo));
				function.create();
				
			}
			
		});
		
		//修改个人密码
		modifySelfPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				function.setFunctionDialog(new ModifySelfPwd(DMFrame.this));
				function.create();
				
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
		
		//查看销售人员
		viewStaff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何销售人员, 请选择一个进行查看.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个销售人员, 请只选择一个进行查看.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewStaff viewStaff = new ViewStaff(DMFrame.this, selectedStaffInfo);
					function.setFunctionDialog(viewStaff);
					function.create();
				}
				
			}
			
		});
		
		//增加销售人员
		addStaff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddStaff addStaff = new AddStaff(DMFrame.this);
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
		deleteStaff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何销售人员, 请至少选择一个进行删除.", "提示", DMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[i]));
						DeleteStaff deleteStaff = new DeleteStaff(DMFrame.this, selectedStaffInfo);
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
		modifyStaff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何销售人员, 请选择一个进行修改.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个销售人员, 请只选择一个进行修改.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setStaff(selectedStaffInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyStaff modifyStaff = new ModifyStaff(DMFrame.this, selectedStaffInfo);
					function.setFunctionDialog(modifyStaff);
					function.create();
					
					if (modifyStaff.isUpdate() == true) {
						staffContent = GetInfo.getStaffInfo();
						content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, staffContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//设置密码
		setPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				SetPwd setPwd = new SetPwd(DMFrame.this);
				function.setFunctionDialog(setPwd);
				function.create();
				
			}
			
		});
		
		//销售人员筛选
		staffSiftKey.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				char c = Character.toLowerCase(e.getKeyChar());
				StringBuffer str = new StringBuffer();
				str.append(staffSiftKey.getText());
				
				if (!Character.isISOControl(c)) {
					str.append(c);
				}
				
				content = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, VectorFilter.Filter(str.toString(), staffContent));
				setTable(content, header);
				
			}
			
		});
		
		
		//查看客户
		viewCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何客户, 请选择一个进行查看.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个客户, 请只选择一个进行查看.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setCustomer(customerInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewCustomer viewCustomer = new ViewCustomer(DMFrame.this, customerInfo);
					function.setFunctionDialog(viewCustomer);
					function.create();
				}
				
			}
			
		});
		
		//增加客户
		addCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddCustomer addCustomer = new AddCustomer(DMFrame.this);
				function.setFunctionDialog(addCustomer);
				function.create();
				
				if (addCustomer.isUpdate() == true) {
					customerContent = GetInfo.getAllCustomer();
					content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, customerContent);
					setTable(content, header);
				}
				
			}
			
		});
		
		//删除客户
		deleteCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何客户, 请至少选择一个进行删除.", "提示", DMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setCustomer(customerInfo, content.get(selectedRows[i]));
						DeleteCustomer deleteCustomer = new DeleteCustomer(DMFrame.this, customerInfo);
						function.setFunctionDialog(deleteCustomer);
						//function.create();
						
						if (isUpdate == false && deleteCustomer.isUpdate() == true) {
							isUpdate = true;
						}
					}
					if (isUpdate == true) {
						customerContent = GetInfo.getAllCustomer();
						content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, customerContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//修改客户资料
		modifyCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何客户, 请选择一个进行修改.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个客户, 请只选择一个进行修改.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setCustomer(customerInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyCustomer modifyCustomer = new ModifyCustomer(DMFrame.this, customerInfo);
					function.setFunctionDialog(modifyCustomer);
					function.create();
					
					if (modifyCustomer.isUpdate() == true) {
						customerContent = GetInfo.getAllCustomer();
						content = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, customerContent);
						setTable(content, header);
					}
				}
			}
			
		});
		
		//客户筛选
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
		
		//查看联系人
		viewContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何联系人, 请选择一个进行查看.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个联系人, 请只选择一个进行查看.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setContact(contactInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewContact viewContact = new ViewContact(DMFrame.this, contactInfo);
					function.setFunctionDialog(viewContact);
					function.create();
				}
				
			}
			
		});
		
		//增加联系人
		addContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddContact addContact = new AddContact(DMFrame.this);
				function.setFunctionDialog(addContact);
				function.create();
				
				if (addContact.isUpdate() == true) {
					contactContent = GetInfo.getAllContact();
					content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, contactContent);
					setTable(content, header);
				}
				
			}
			
		});
		
		//删除联系人
		deleteContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何联系人, 请至少选择一个进行删除.", "提示", DMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setContact(contactInfo, content.get(selectedRows[i]));
						DeleteContact deleteContact = new DeleteContact(DMFrame.this, contactInfo);
						function.setFunctionDialog(deleteContact);
						//function.create();
					
						if (isUpdate == false && deleteContact.isUpdate() == true) {
							isUpdate = true;
						}
					}
					if (isUpdate == true) {
						contactContent = GetInfo.getAllContact();
						content = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, contactContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//修改联系人
		modifyContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何联系人, 请选择一个进行修改.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个联系人, 请只选择一个进行修改.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setContact(contactInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyContact modifyContact = new ModifyContact(DMFrame.this, contactInfo);
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
				BirthdayNotify birthdayNotify = new BirthdayNotify(DMFrame.this);
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
		viewProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何项目, 请选择一个进行查看.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个项目, 请只选择一个进行查看.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setProject(projectInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewProject viewProject = new ViewProject(DMFrame.this, projectInfo);
					function.setFunctionDialog(viewProject);
					function.create();
				}
				
			}
			
		});
		
		//增加项目
		addProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddProject addProject = new AddProject(DMFrame.this);
				function.setFunctionDialog(addProject);
				function.create();
				
				if (addProject.isUpdate() == true) {
					projectContent = GetInfo.getAllProject();
					content = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, projectContent);
					setTable(content, header);
				}
				
			}
			
		});
		
		//删除项目
		deleteProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何项目, 请至少选择一个进行删除.", "提示", DMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setProject(projectInfo, content.get(selectedRows[i]));
						DeleteProject deleteProject = new DeleteProject(DMFrame.this, projectInfo);
						function.setFunctionDialog(deleteProject);
						//function.create();
						
						if (isUpdate == false && deleteProject.isUpdate() == true) {
							isUpdate = true;
						}
					}
					if (isUpdate == true) {
						projectContent = GetInfo.getAllProject();
						content = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, projectContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//修改项目
		modifyProject.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何项目, 请选择一个进行修改.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多个项目, 请只选择一个进行修改.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setProject(projectInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyProject modifyProject = new ModifyProject(DMFrame.this, projectInfo);
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
				ProjectNotify projectNotify = new ProjectNotify(DMFrame.this);
				function.setFunctionDialog(projectNotify);
				function.create();
				
			}
			
		});
		
		//统计信息
		statisticInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				StatisticInfo statisticInfo = new StatisticInfo(DMFrame.this);
				function.setFunctionDialog(statisticInfo);
				function.create();
				
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
		
		//查看跟踪记录
		viewTrackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length ==0) {
					new MessageBox("您没有选择任何跟踪记录, 请选择一条进行查看.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多条跟踪记录, 请只选择一条进行查看", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setTrackRecord(trackRecordInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ViewTrackRecord viewTrackRecord = new ViewTrackRecord(DMFrame.this, projectInfo, trackRecordInfo);
					function.setFunctionDialog(viewTrackRecord);
					function.create();
				}
				
			}
			
		});
		
		//增加跟踪记录
		addTrackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Function function = new Function();
				AddTrackRecord addTrackRecord = new AddTrackRecord(DMFrame.this, projectInfo);
				function.setFunctionDialog(addTrackRecord);
				function.create();
				
				if (addTrackRecord.isUpdate() == true) {
					trackRecordContent = GetInfo.getAllTrackRecord();
					content = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, trackRecordContent);
					setTable(content, header);
				}
				
			}
			
		});
		
		//删除跟踪记录
		deleteTrackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何跟踪记录, 请选择至少一条进行删除.", "提示", DMFrame.this);
				}
				else {
					Function function = new Function();
					boolean isUpdate = false;
					
					for (int i = 0; i < selectedRows.length; ++i) {
						SetInfoBean.setTrackRecord(trackRecordInfo, content.get(selectedRows[i]));
						DeleteTrackRecord deleteTrackRecord = new DeleteTrackRecord(DMFrame.this, trackRecordInfo);
						function.setFunctionDialog(deleteTrackRecord);
						//function.create();
						
						if (isUpdate == false && deleteTrackRecord.isUpdate() == true) {
							isUpdate = true;
						}
					}
					
					if (isUpdate == true) {
						trackRecordContent = GetInfo.getAllTrackRecord();
						content = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, trackRecordContent);
						setTable(content, header);
					}
				}
			}	
			
		});
		
		//修改跟踪记录
		modifyTrackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (selectedRows == null || selectedRows.length == 0) {
					new MessageBox("您没有选择任何跟踪记录, 请选择一条进行修改.", "提示", DMFrame.this);
				}
				else if (selectedRows.length > 1) {
					new MessageBox("您选择了多条跟踪记录, 请只选择一条进行修改.", "提示", DMFrame.this);
				}
				else {
					SetInfoBean.setTrackRecord(trackRecordInfo, content.get(selectedRows[0]));
					Function function = new Function();
					ModifyTrackRecord modifyTrackRecord = new ModifyTrackRecord(DMFrame.this, projectInfo, trackRecordInfo);
					function.setFunctionDialog(modifyTrackRecord);
					function.create();
					
					if (modifyTrackRecord.isUpdate() == true) {
						trackRecordContent = GetInfo.getAllTrackRecord();
						content = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, trackRecordContent);
						setTable(content, header);
					}
				}
				
			}
			
		});
		
		//table选择事件处理程序
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();
				logger.debug("Begin - " + DMFrame.class.getName());
				for (int row : selectedRows) {
					logger.debug("Selected Rows: " + row);
				}
				logger.debug("End - " + DMFrame.class.getName());
				
			}
			
		});
	}
	
	/**
	 * 创建工具栏
	 */
	private void createToolBar() {
		panel = new JPanel(new BorderLayout());
		add(panel);
		
		//创建工具栏
		toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		//保存
		saveAction = new AbstractAction("保存", new ImageIcon(DMFrame.class.getResource("/images/TSave.gif"))) {

			public void actionPerformed(ActionEvent e) {
				save();
				
			}
			
		};
		
		//打印
		printAction = new AbstractAction("打印", new ImageIcon(DMFrame.class.getResource("/images/TPrint.gif"))) {

			public void actionPerformed(ActionEvent e) {
				print();
				
			}
			
		};
		
		//退出
		Action exitAction = new AbstractAction("退出", new ImageIcon(DMFrame.class.getResource("/images/TExit.gif"))) {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		};
		
		//客户管理
		customerAction = new AbstractAction("客户管理", new ImageIcon(DMFrame.class.getResource("/images/TCustomer.gif"))) {

			public void actionPerformed(ActionEvent e) {
				customer();
				
			}
			
		};
		
		//联系人管理
		contactAction = new AbstractAction("联系人管理", new ImageIcon(DMFrame.class.getResource("/images/TContact.gif"))) {

			public void actionPerformed(ActionEvent e) {
				contact();
				
			}
			
		};
		
		//项目管理
		projectAction = new AbstractAction("项目管理", new ImageIcon(DMFrame.class.getResource("/images/TProject.gif"))) {

			public void actionPerformed(ActionEvent e) {
				project();
				
			}
			
		};
		
		//查询
		Action searchAction = new AbstractAction("查询", new ImageIcon(DMFrame.class.getResource("/images/TSearch.gif"))) {

			public void actionPerformed(ActionEvent e) {
				search();
				
			}
			
		};
		
		//帮助
		Action helpAction = new AbstractAction("帮助文档", new ImageIcon(DMFrame.class.getResource("/images/THelp.gif"))) {

			public void actionPerformed(ActionEvent e) {
				help();
				
			}
			
		};
		
		//关于我们
		Action aboutAction = new AbstractAction("关于我们", new ImageIcon(DMFrame.class.getResource("/images/TAboutUS.gif"))) {

			public void actionPerformed(ActionEvent e) {
				about();
				
			}
			
		};
		
		//设置工具栏图标提示
		saveAction.putValue(Action.SHORT_DESCRIPTION, "保存");
		printAction.putValue(Action.SHORT_DESCRIPTION, "打印");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "退出系统");
		customerAction.putValue(Action.SHORT_DESCRIPTION, "客户管理");
		contactAction.putValue(Action.SHORT_DESCRIPTION, "联系人管理");
		projectAction.putValue(Action.SHORT_DESCRIPTION, "项目管理");
		searchAction.putValue(Action.SHORT_DESCRIPTION, "查询");
		helpAction.putValue(Action.SHORT_DESCRIPTION, "帮助文档");
		aboutAction.putValue(Action.SHORT_DESCRIPTION, "关于我们");
		
		//将图标加入工具栏
		toolBar.add(saveAction);
		toolBar.add(printAction);
		toolBar.add(customerAction);
		toolBar.add(contactAction);
		toolBar.add(projectAction);
		toolBar.add(searchAction);
		toolBar.add(helpAction);
		toolBar.add(aboutAction);
		toolBar.add(exitAction);
		
		toolBar.setBorder(BorderFactory.createEtchedBorder());
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		//设置图标活动性
		saveAction.setEnabled(false);
		printAction.setEnabled(false);
	}
	
	/**
	 * 创建面板内容
	 */
	private void createContent() {
		contentPanel = new JPanel(new BorderLayout());
		panel.add(contentPanel, BorderLayout.CENTER);
		
		Box message = Box.createVerticalBox();
		message.setBorder(BorderFactory.createEtchedBorder());
		
		dateImg = new JLabel(new ImageIcon(DMFrame.class.getResource("/images/calendar.gif")));
		String today = Today.getYear() + "年" + Today.getMonth() + "月" + Today.getDate() + "日";
		date = new JLabel(today);
		date.setFont(font);
		week = new JLabel(Today.getWeek());
		week.setFont(font);
		nameImg = new JLabel(new ImageIcon(DMFrame.class.getResource("/images/role.png")));
		name = new JLabel(staffInfo.getStaffName());
		name.setFont(font);
		role = new JLabel("部门经理");
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
		function.setFunctionDialog(new Save(DMFrame.this));
		function.create();
	}
	
	/**
	 * 打印
	 */
	private void print() {
		try {
			table.print();
		} catch (PrinterException e) {
			logger.warn("打印未准备好. - " + e.getMessage() + DMFrame.class.getName());
		}
	}
	
	/**
	 * 人员管理
	 */
	private void staff() {
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
		staffSiftKey.setText("");
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewStaff.setText("查看");
		viewStaff.setFont(font);
		addStaff.setText("增加");
		addStaff.setFont(font);
		deleteStaff.setText("删除");
		deleteStaff.setFont(font);
		modifyStaff.setText("修改");
		modifyStaff.setFont(font);
		setPassword.setText("设置密码");
		setPassword.setFont(font);
		hideOutDate.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(staffSiftKey);
		functionCenter.add(Box.createHorizontalStrut(100));
		functionCenter.add(hideOutDate);
		functionCenter.add(Box.createHorizontalStrut(180));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewStaff);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addStaff);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(deleteStaff);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyStaff);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(setPassword);
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
	 * 客户管理
	 */
	private void customer() {
		print.setEnabled(true);
		printAction.setEnabled(true);
		
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
		Box functionUnderCenterTop = Box.createVerticalBox();
		Box functionUnderCenter = Box.createHorizontalBox();
		Box functionBottom = Box.createVerticalBox();
		
		functionTop.add(Box.createVerticalStrut(10));
		functionUnderCenterTop.add(Box.createVerticalStrut(10));
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
		deleteCustomer.setText("删除");
		deleteCustomer.setFont(font);
		modifyCustomer.setText("修改");
		modifyCustomer.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(customerSiftKey);
		functionCenter.add(Box.createHorizontalStrut(400));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewCustomer);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addCustomer);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(deleteCustomer);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyCustomer);
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
	 * 联系人管理
	 */
	private void contact() {
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
		viewContact.setText("查看");
		viewContact.setFont(font);
		addContact.setText("增加");
		addContact.setFont(font);
		deleteContact.setText("删除");
		deleteContact.setFont(font);
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
		functionUnderCenter.add(viewContact);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addContact);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(deleteContact);
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
	 * 项目管理
	 */
	private void project() {
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
		projectSiftKey.setText("");
		hideSign.setFont(font);
		hideEnded.setFont(font);
		JLabel fun = new JLabel("功能");
		fun.setFont(font);
		viewProject.setText("查看");
		viewProject.setFont(font);
		addProject.setText("增加");
		addProject.setFont(font);
		deleteProject.setText("删除");
		deleteProject.setFont(font);
		modifyProject.setText("修改");
		modifyProject.setFont(font);
		projectNotify.setText("项目提醒");
		projectNotify.setFont(font);
		statisticInfo.setText("统计信息");
		statisticInfo.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(sift);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(projectSiftKey);
		functionCenter.add(Box.createHorizontalStrut(60));
		functionCenter.add(hideSign);
		functionCenter.add(Box.createHorizontalStrut(20));
		functionCenter.add(hideEnded);
		functionCenter.add(Box.createHorizontalStrut(100));
		functionUnderCenter.add(Box.createHorizontalStrut(2));
		functionUnderCenter.add(fun);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(viewProject);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(addProject);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(deleteProject);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(modifyProject);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(projectNotify);
		functionUnderCenter.add(Box.createHorizontalStrut(10));
		functionUnderCenter.add(statisticInfo);
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
	 * 跟踪记录管理
	 */
	private void trackRecord() {
		print.setEnabled(true);
		printAction.setEnabled(true);
		
		
		//删除原有内容
		if (displayPanel != null && tips != null) {
			contentPanel.remove(displayPanel);
			contentPanel.remove(tips);
			validate();
			repaint();
		}
		
		trackRecordContent = GetInfo.getAllTrackRecord();
		trackRecordTitle = GetInfo.getTrackRecordTitle();
		
		content = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, trackRecordContent);
		header = VectorFilter.singleFilter(VectorFilter.TRACKRECORDFILTER, trackRecordTitle);
		
		//建立显示区面板
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "跟踪记录管理"));
		
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
		
		JLabel fun = new JLabel("功能选择");
		fun.setFont(font);
		viewTrackRecord.setText("查看");
		viewTrackRecord.setFont(font);
		addTrackRecord.setText("增加");
		addTrackRecord.setFont(font);
		deleteTrackRecord.setText("删除");
		deleteTrackRecord.setFont(font);
		modifyTrackRecord.setText("修改");
		modifyTrackRecord.setFont(font);
		functionCenter.add(Box.createHorizontalStrut(2));
		functionCenter.add(fun);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(viewTrackRecord);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(addTrackRecord);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(deleteTrackRecord);
		functionCenter.add(Box.createHorizontalStrut(10));
		functionCenter.add(modifyTrackRecord);
		functionCenter.add(Box.createHorizontalGlue());
		
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
				staff();
				break;
			case 1:
				customer();
				break;
			case 3:
				contact();
				break;
			case 4:
				project();
				break;
			case 5:
				trackRecord();
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
			logger.info("打开帮助文档. - " + DMFrame.class.getName());
			rt.exec("hh.exe help.chm");
			logger.info("打开帮助文档成功. - " + DMFrame.class.getName());
		} catch (IOException e) {
			logger.error("打开帮助文档失败! - " + DMFrame.class.getName());
			JOptionPane.showMessageDialog(this, "系统无法打开帮助文档，请联系开发人员。", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 关于我们
	 */
	private void about() {
		Function function = new Function();
		function.setFunctionDialog(new About(DMFrame.this));
		function.create();
	}
	/**
	 * create方法激活窗体
	 * @see com.p6.frames.Role#create()
	 */
	public void create() {
		setVisible(true);
	}
	
	/**
	 * 设置主操作界面的table内容
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
	private Action customerAction = null; //客户管理动作
	private Action contactAction = null; //联系人管理动作
	private Action projectAction = null; //项目管理动作
	
	private JMenu file = null; //文件菜单
	private JMenu manage = null; //管理菜单
	private JMenu personal = null; //个人管理菜单
	private JMenu search = null; //查询菜单
	private JMenu help = null; //帮助菜单
	
	private JMenuItem save = null; //保存
	private JMenuItem print = null; //打印
	private JMenuItem exit = null; //退出
	
	private JMenuItem staff = null; //人员管理
	private JMenuItem customer = null; //客户管理
	private JMenuItem contact = null; //联系人管理
	private JMenuItem project = null; //项目管理
	private JMenuItem trackRecord = null; //跟踪记录管理
	
	private JButton viewStaff = new JButton(); //查看销售人员
	private JButton addStaff = new JButton(); //增加销售人员
	private JButton deleteStaff = new JButton(); //删除销售人员
	private JButton modifyStaff = new JButton(); //修改销售人员
	private JButton setPassword = new JButton(); //设置销售人员密码
	private JButton viewCustomer = new JButton(); //查看客户
	private JButton addCustomer = new JButton(); //增加客户
	private JButton deleteCustomer = new JButton(); //删除客户
	private JButton modifyCustomer = new JButton(); //修改客户
	private JButton viewContact = new JButton(); //查看联系人
	private JButton addContact = new JButton(); //增加联系人
	private JButton deleteContact = new JButton(); //删除联系人
	private JButton modifyContact = new JButton(); //修改联系人
	private JButton birthdayNotify = new JButton(); //生日提醒
	private JButton viewProject = new JButton(); //查看项目
	private JButton addProject = new JButton(); //增加项目
	private JButton deleteProject = new JButton(); //删除项目
	private JButton modifyProject = new JButton(); //修改项目
	private JButton projectNotify = new JButton(); //项目提醒
	private JButton statisticInfo = new JButton(); //统计信息
	private JButton viewTrackRecord = new JButton(); //查看跟踪记录
	private JButton addTrackRecord = new JButton(); //增加跟踪记录
	private JButton deleteTrackRecord = new JButton(); //删除跟踪记录
	private JButton modifyTrackRecord = new JButton(); //修改跟踪记录
	
	private JMenuItem viewSelfInfo = null; //查看个人资料
	private JMenuItem modifySelfInfo = null; //修改个人资料
	private JMenuItem modifySelfPassword = null; //修改个人密码
	
	private JMenuItem generalSearch = null; //普通查询
	private JMenuItem advancedSearch = null; //高级查询
	
	private JMenuItem helpDocument = null; //帮助文档
	private JMenuItem about = null; //关于我们
	
	private JCheckBox hideSign = new JCheckBox("屏蔽已签约项目");
	private JCheckBox hideEnded = new JCheckBox("屏蔽已中止项目");
	private JCheckBox hideOutDate = new JCheckBox("屏蔽已离职人员");
	
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
	private JTextField staffSiftKey = new JTextField(); //销售人员筛选
	private JTextField customerSiftKey = new JTextField(); //客户筛选
	private JTextField contactSiftKey = new JTextField(); //联系人筛选
	private JTextField projectSiftKey = new JTextField(); //项目筛选
	
	private StaffInfo staffInfo = new StaffInfo(); //当前登陆人员信息
	private StaffInfo selectedStaffInfo = new StaffInfo(); //被操作销售人员信息
	private CustomerInfo customerInfo = new CustomerInfo(); //客户信息
	private ContactInfo contactInfo = new ContactInfo(); //联系人信息
	private ProjectInfo projectInfo = new ProjectInfo(); //项目信息
	private TrackRecordInfo trackRecordInfo = new TrackRecordInfo(); //跟踪记录信息
	
	private Vector<Vector<String>> content = null;
	private Vector<String> header = null;
	private Vector<Vector<String>> staffContent = new Vector<Vector<String>>();
	private Vector<String> staffTitle = new Vector<String>();
	private Vector<Vector<String>> customerContent = new Vector<Vector<String>>();
	private Vector<String> customerTitle = new Vector<String>();
	private Vector<Vector<String>> contactContent = new Vector<Vector<String>>();
	private Vector<String> contactTitle = new Vector<String>();
	private Vector<Vector<String>> projectContent = new Vector<Vector<String>>();
	private Vector<String> projectTitle = new Vector<String>();
	private Vector<Vector<String>> trackRecordContent = new Vector<Vector<String>>();
	private Vector<String> trackRecordTitle = new Vector<String>();
	private int[] selectedRows = null; //table中被选择的行

	private Font font = new Font("宋体", Font.PLAIN, 12);
	private String title = "售前管理系统---Power by PowerSix";
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	private static Logger logger = Logger.getLogger(DMFrame.class);
	
	public static void main(String[] args) {
		DMFrame frame = new DMFrame(2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.create();
	}
}
