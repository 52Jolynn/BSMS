/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */

package com.p6.frames.dialogs.other;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;

import org.apache.log4j.Logger;

import com.p6.toolkit.GetInfo;
import com.p6.toolkit.VectorFilter;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Handler;
import com.p6.user.infoBean.*;
import com.p6.user.others.DoSearch;

public class Search extends OtherDialog {
	
	@SuppressWarnings("unused")
	private Search(){}
	
	/**
	 * 构造函数
	 * @param owner 父窗体
	 * @param staffInfo 销售人员信息Bean(JavaBean)
	 */
	public Search(JFrame owner, StaffInfo staffInfo){
		setSize(400, 330);
		setTitle("搜索资料");
		setModal(true);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		this.staffInfo = staffInfo;	
		
		content = new JPanel(new BorderLayout(0, 10));
		add(content);
		
		searchRange = new ButtonGroup();
		
		range = Box.createHorizontalBox();
		range.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索范围"));
		staff.setFont(font);
		staff.setSelected(true);
		customer.setFont(font);
		contact.setFont(font);
		project.setFont(font);
		trackRecord.setFont(font);
		
		//加入单选按钮组
		searchRange.add(staff);
		searchRange.add(customer);
		searchRange.add(contact);
		searchRange.add(project);
		searchRange.add(trackRecord);
			
		//加入Box中
		range.add(Box.createHorizontalStrut(10));
		range.add(staff);
		range.add(Box.createHorizontalStrut(10));
		range.add(customer);
		range.add(Box.createHorizontalStrut(10));
		range.add(contact);
		range.add(Box.createHorizontalStrut(10));
		range.add(project);
		range.add(Box.createHorizontalStrut(10));
		range.add(trackRecord);
		range.add(Box.createHorizontalStrut(36));
		
		content.add(range, BorderLayout.NORTH);
		logger.debug("内容面板加入搜索范围Box. - " + Search.class.getName());
		
		//判断是角色
		title = staffInfo.getPosName();
		if (title.equals("总经理")) {
			TMSearch();
			staffSearch();
		}
		else if (title.equals("部门经理")) {
			DMSearch();
			staffSearch();
		}
		else if (title.equals("销售经理")) {
			SMSearch();
			customerSearch();
		}
		else if (title.equals("销售助理")) {
			SASearch();
			customerSearch();
		}
		else {
			logger.warn("用户角色错误!" + Search.class.getName());
		}
		
		//关闭窗体
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Search.this.dispose();
				super.windowClosing(e);
			}
			
		});
		
		//销售人员单选按钮事件响应
		staff.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				staffSearch();
				staff.transferFocus();
				staff.setEnabled(false);
				customer.setEnabled(true);
				contact.setEnabled(true);
				if (!title.equals("销售助理")) {
					project.setEnabled(true);
					trackRecord.setEnabled(true);
				}
			}
			
		});
		
		//客户单选按钮事件响应
		customer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customerSearch();
				customerNameText.requestFocus();
				if (title.equals("总经理") || title.equals("部门经理"))
					staff.setEnabled(true);
				customer.setEnabled(false);
				contact.setEnabled(true);
				if (!title.equals("销售助理")) {
					project.setEnabled(true);
					trackRecord.setEnabled(true);
				}
				
			}
			
		});
		
		//联系人单选按钮事件响应
		contact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contactSearch();
				contactNameText.requestFocus();
				if (title.equals("总经理") || title.equals("部门经理"))
					staff.setEnabled(true);
				customer.setEnabled(true);
				contact.setEnabled(false);
				if (!title.equals("销售助理")) {
					project.setEnabled(true);
					trackRecord.setEnabled(true);
				}
				
			}
			
		});
		
		//项目单选按钮事件响应
		project.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				projectSearch();
				projectNameText.requestFocus();
				if (title.equals("总经理") || title.equals("部门经理"))
					staff.setEnabled(true);
				customer.setEnabled(true);
				contact.setEnabled(true);
				project.setEnabled(false);
				if (!title.equals("销售助理")) {
					trackRecord.setEnabled(true);
				}
				
			}
			
		});
		
		//跟踪记录单选按钮事件响应
		trackRecord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				trackRecordSearch();
				trackRecordProjectNameText.requestFocus();
				if (title.equals("总经理") || title.equals("部门经理"))
					staff.setEnabled(true);
				customer.setEnabled(true);
				contact.setEnabled(true);
				if (!title.equals("销售助理")) {
					project.setEnabled(true);
				}
				trackRecord.setEnabled(false);
			}
			
		});
		
	}
	
	/**
	 * 总经理查询
	 */
	private void TMSearch() {
		staff.setEnabled(false);
		staff.setSelected(true);
		staffNameText.requestFocus();
		customer.setEnabled(false);
		contact.setEnabled(false);
		project.setEnabled(false);
		trackRecord.setEnabled(false);
	}
	
	/**
	 * 部门经理查询
	 */
	private void DMSearch() {
		staff.setEnabled(false);
		staff.setSelected(true);
		staffNameText.requestFocus();
		customer.setEnabled(true);
		contact.setEnabled(true);
		project.setEnabled(true);
		trackRecord.setEnabled(true);
	}
	
	/**
	 * 销售经理查询
	 */
	private void SMSearch() {
		staff.setEnabled(false);
		customer.setEnabled(false);
		customer.setSelected(true);
		customerNameText.requestFocus();
		contact.setEnabled(true);
		project.setEnabled(true);
		trackRecord.setEnabled(true);
	}
	
	/**
	 * 销售助理查询
	 */
	private void SASearch() {
		staff.setEnabled(false);
		customer.setEnabled(false);
		customer.setSelected(true);
		customerNameText.requestFocus();
		contact.setEnabled(true);
		project.setEnabled(false);
		trackRecord.setEnabled(false);
	}
	
	/**
	 * 销售人员查询
	 */
	private void staffSearch() {
		if (conditionPanel != null && operationPanel != null) {
			content.remove(conditionPanel);
			logger.debug("销售人员:删除condition搜索条件区面板 - " + Search.class.getName());
			content.remove(operationPanel);
			logger.debug("销售人员:删除operation操作区面板 - " + Search.class.getName());
			validate();
			repaint();
		}
		
		//搜索条件(销售人员)
		condition = Box.createVerticalBox();
		Box conditionOne = Box.createHorizontalBox();
		Box conditionTwo = Box.createHorizontalBox();
		Box conditionThree = Box.createHorizontalBox();
		Box conditionFive = Box.createHorizontalBox();
		Box conditionSix = Box.createHorizontalBox();
		
		//加入条件
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionOne);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionTwo);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionThree);
		condition.add(Box.createVerticalStrut(5));

		condition.add(conditionFive);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionSix);
		condition.add(Box.createVerticalStrut(5));
		
		//姓名
		staffName = new JRadioButton("姓名", true);
		staffName.setFont(font);
		(new ButtonGroup()).add(staffName);
		//staffNameText = new JTextField();
		staffNameText.requestFocus();
		conditionOne.add(Box.createHorizontalStrut(5));
		conditionOne.add(staffName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(staffNameText);
		conditionOne.add(Box.createHorizontalStrut(160));
		
		//性别
		staffSex = new JCheckBox("性别");
		staffSex.setFont(font);
		staffSexList.setEnabled(false);
		staffSexList.setFont(font);
		conditionTwo.add(Box.createHorizontalStrut(5));
		conditionTwo.add(staffSex);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(staffSexList);
		conditionTwo.add(Box.createHorizontalStrut(270));
		
		//职位
		staffPosition = new JCheckBox("职位");
		staffPosition.setFont(font);
		staffPositionList = new JComboBox(GetInfo.POSITION);
		staffPositionList.setEnabled(false);
		staffPositionList.setFont(font);
		if (title.equals("总经理")) {
			staffPositionList.removeItemAt(0);
			staffPositionList.removeItemAt(1);
			staffPositionList.removeItemAt(1);
		}
		else if (title.equals("部门经理"))
			staffPositionList.removeItemAt(0);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(staffPosition);
		conditionThree.add(Box.createHorizontalStrut(10));
		conditionThree.add(staffPositionList);
		conditionThree.add(Box.createHorizontalStrut(240));
		
		//入职日期
		inDate = new JCheckBox("入职日期");
		inDate.setFont(font);
		inDateFrom = new JTextField();
		inDateFrom.setEnabled(false);
		inDateFrom.setEditable(false);
		inCalendarFrom.setEnabled(false);
		JLabel toOne = new JLabel("到");
		toOne.setFont(font);
		inDateTo = new JTextField();
		inDateTo.setEnabled(false);
		inDateTo.setEditable(false);
		inCalendarTo.setEnabled(false);
		conditionFive.add(Box.createHorizontalStrut(5));
		conditionFive.add(inDate);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(inDateFrom);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(inCalendarFrom);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(toOne);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(inDateTo);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(inCalendarTo);
		conditionFive.add(Box.createHorizontalStrut(20));
		
		
		//离职日期
		outDate = new JCheckBox("离职日期");
		outDate.setFont(font);
		outDateFrom = new JTextField();
		outDateFrom.setEnabled(false);
		outDateFrom.setEditable(false);
		outCalendarFrom.setEnabled(false);
		JLabel toTwo = new JLabel("到");
		toTwo.setFont(font);
		outDateTo = new JTextField();
		outDateTo.setEnabled(false);
		outDateTo.setEditable(false);
		outCalendarTo.setEnabled(false);
		conditionSix.add(Box.createHorizontalStrut(5));
		conditionSix.add(outDate);
		conditionSix.add(Box.createHorizontalStrut(10));
		conditionSix.add(outDateFrom);
		conditionSix.add(Box.createHorizontalStrut(10));
		conditionSix.add(outCalendarFrom);
		conditionSix.add(Box.createHorizontalStrut(10));
		conditionSix.add(toTwo);
		conditionSix.add(Box.createHorizontalStrut(10));
		conditionSix.add(outDateTo);
		conditionSix.add(Box.createHorizontalStrut(10));
		conditionSix.add(outCalendarTo);
		conditionSix.add(Box.createHorizontalStrut(20));
		
		//操作区
		operation = Box.createHorizontalBox();
		staffSearch = new JButton("搜索");
		staffSearch.setFont(font);
		staffReset = new JButton("重置");
		staffReset.setFont(font);
		staffCancel = new JButton("取消");
		staffCancel.setFont(font);
		operation.add(Box.createHorizontalStrut(5));
		operation.add(staffSearch);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(staffReset);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(staffCancel);
		operation.add(Box.createHorizontalGlue());
		
		conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索条件"));
		conditionPanel.add(condition);
		operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		operationPanel.setBorder(BorderFactory.createEmptyBorder());
		operationPanel.add(operation);
		content.add(conditionPanel, BorderLayout.CENTER);
		content.add(operationPanel, BorderLayout.SOUTH);
		
		setSize(400, 330);
		validate();
		
		//激活性别单选
		staffSex.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (staffSex.isSelected()) {
					staffSexList.setEnabled(true);
				}
				else {
					staffSexList.setEnabled(false);
				}
				
			}
			
		});
		
		//激活职位单选
		staffPosition.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (staffPosition.isSelected()) {
					staffPositionList.setEnabled(true);
				}
				else {
					staffPositionList.setEnabled(false);
				}
				
			}
			
		});
		
		//激活入职日期
		inDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (inDate.isSelected()) {
					inCalendarFrom.setEnabled(true);
					inCalendarTo.setEnabled(true);
				}
				else {
					inCalendarFrom.setEnabled(false);
					inCalendarTo.setEnabled(false);
					inDateFrom.setText("");
					inDateTo.setText("");
				}
				
			}
			
		});
		
		//激活离职日期
		outDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (outDate.isSelected()) {
					outCalendarFrom.setEnabled(true);
					outCalendarTo.setEnabled(true);
				}
				else {
					outCalendarFrom.setEnabled(false);
					outCalendarTo.setEnabled(false);
					outDateFrom.setText("");
					outDateTo.setText("");
				}
				
			}
			
		});
		
		//搜索按钮事件
		staffSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Handler handler = new Handler();
				DoSearch ds = new DoSearch(searchStaffInfo);
				handler.setDoHandler(ds);
				handler.process(staffInfo);
				
				if (handler.isSucced()) {
					searchContent = VectorFilter.doubleFilter(VectorFilter.STAFFFILTER, ds.getContent());
					resultHeader = VectorFilter.singleFilter(VectorFilter.STAFFFILTER, ds.getHeader());
					if (title.equals("总经理")) {
						String name = staffNameText.getText().trim();
						logger.debug("销售人员姓名:" + name.equals("") + " - " + Search.class.getName());
						String sex = null;
						if (staffSex.isSelected())
							sex = ((String)staffSexList.getSelectedItem()).trim();
						String position = null;
						if (staffPosition.isSelected())
							position = ((String)staffPositionList.getSelectedItem()).trim();
						Date inDateFrom = null;
						Date inDateTo = null;
						Date outDateFrom = null;
						Date outDateTo = null;
						if (inDate.isSelected()) {
							inDateFrom = ParseStringAndDate.parseString2Date(Search.this.inDateFrom.getText(), "-");
							inDateTo = ParseStringAndDate.parseString2Date(Search.this.inDateTo.getText(), "-");
						}
						if (outDate.isSelected()) {
							outDateFrom = ParseStringAndDate.parseString2Date(Search.this.outDateFrom.getText(), "-");
							outDateTo = ParseStringAndDate.parseString2Date(Search.this.outDateTo.getText(), "-");
						}
						logger.debug("过滤掉总经理 - " + Search.class.getName());
						searchContent = VectorFilter.dataFilter(4, "总经理", searchContent);
						logger.debug("过滤掉销售经理 - " + Search.class.getName());
						searchContent = VectorFilter.dataFilter(4, "销售经理", searchContent);
						logger.debug("过滤掉销售助理 - " + Search.class.getName());
						searchContent = VectorFilter.dataFilter(4, "销售助理", searchContent);
						logger.debug("销售人员姓名: " + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售人员性别: " + sex + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, sex, searchContent);
						logger.debug("销售人员职位: " + position + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, position, searchContent);
						logger.debug("入职日期:" + inDateFrom + "-" + inDateTo + " - " + Search.class.getName());
						searchContent = VectorFilter.dateFilter(6, inDateFrom, inDateTo, searchContent);
						logger.debug("离职日期:" + outDateFrom + "-" + outDateTo + " - " + Search.class.getName());
						resultContent = VectorFilter.dateFilter(7, outDateFrom, outDateTo, searchContent);
						isUpdate = true;
						searched = STAFF;
						Search.this.dispose();
					}
					else if (title.equals("部门经理")) {
						String name = staffNameText.getText().trim();
						String sex = null;
						if (staffSex.isSelected())
							sex = ((String)staffSexList.getSelectedItem()).trim();
						String position = null;
						if (staffPosition.isSelected())
							position = ((String)staffPositionList.getSelectedItem()).trim();
						Date inDateFrom = null;
						Date inDateTo = null;
						Date outDateFrom = null;
						Date outDateTo = null;
						if (inDate.isSelected()) {
							inDateFrom = ParseStringAndDate.parseString2Date(Search.this.inDateFrom.getText(), "-");
							inDateTo = ParseStringAndDate.parseString2Date(Search.this.inDateTo.getText(), "-");
						}
						if (outDate.isSelected()) {
							outDateFrom = ParseStringAndDate.parseString2Date(Search.this.outDateFrom.getText(), "-");
							outDateTo = ParseStringAndDate.parseString2Date(Search.this.outDateTo.getText(), "-");
						}
						logger.debug("过滤掉总经理 - " + Search.class.getName());
						searchContent = VectorFilter.dataFilter(4, "总经理", searchContent);
						logger.debug("销售人员姓名: " + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售人员性别: " + sex + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, sex, searchContent);
						logger.debug("销售人员职位: " + position + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, position, searchContent);
						logger.debug("入职日期:" + inDateFrom + "-" + inDateTo + " - " + Search.class.getName());
						searchContent = VectorFilter.dateFilter(6, inDateFrom, inDateTo, searchContent);
						logger.debug("离职日期:" + outDateFrom + "-" + outDateTo + " - " + Search.class.getName());
						resultContent = VectorFilter.dateFilter(7, outDateFrom, outDateTo, searchContent);
						isUpdate = true;
						searched = STAFF;
						Search.this.dispose();
					}
				}
			}
			
		});
		
		//重置按钮事件
		staffReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				staffNameText.setText("");
				staffNameText.requestFocus();
				staffSex.setSelected(false);
				staffSexList.setEnabled(false);
				staffPosition.setSelected(false);
				staffPositionList.setEnabled(false);
				inDate.setSelected(false);
				inCalendarFrom.setEnabled(false);
				inCalendarTo.setEnabled(false);
				outDate.setSelected(false);
				outCalendarFrom.setEnabled(false);
				outCalendarTo.setEnabled(false);
				
			}
			
		});
		
		//取消按钮事件
		staffCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Search.this.dispose();
				
			}
			
		});
		
		//起始入职日期
		inCalendarFrom.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (inDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					inDateFrom.setText(date.getChosenDate("-"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (inDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (inDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			
			
		});
		
		//终止入职日期
		inCalendarTo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (inDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					inDateTo.setText(date.getChosenDate("-"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (inDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (inDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		});
		
		//起始离职日期
		outCalendarFrom.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (outDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					outDateFrom.setText(date.getChosenDate("-"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (outDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (outDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		});
		
		//终止离职日期
		outCalendarTo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (outDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					outDateTo.setText(date.getChosenDate("-"));

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (outDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (outDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		});
	}
	
	/**
	 * 客户搜索
	 */
	private void customerSearch() {
		if (conditionPanel != null && operationPanel != null) {
			content.remove(conditionPanel);
			logger.debug("客户:删除condition搜索条件区面板 - " + Search.class.getName());
			content.remove(operationPanel);
			logger.debug("客户:删除operation操作区面板 - " + Search.class.getName());
			validate();
			repaint();
		}
		
		//搜索条件(客户)
		Box condition = Box.createVerticalBox();
		Box conditionOne = Box.createHorizontalBox();
		Box conditionTwo = Box.createHorizontalBox();
		Box conditionThree = Box.createHorizontalBox();
		Box conditionFour = Box.createHorizontalBox();
		
		//加入搜索条件
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionOne);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionTwo);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionThree);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionFour);
		condition.add(Box.createVerticalStrut(5));
		
		//客户名称
		customerName = new JRadioButton("客户名称", true);
		customerName.setFont(font);
		customerName.setSelected(true);
		(new ButtonGroup()).add(customerName);
		customerNameText.requestFocus();
		conditionOne.add(Box.createHorizontalStrut(5));
		conditionOne.add(customerName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(customerNameText);
		conditionOne.add(Box.createHorizontalGlue());
		
		//所在行业
		customerIndustry = new JCheckBox("所属行业");
		customerIndustry.setFont(font);
		customerIndustryText = new JTextField(10);
		customerIndustryText.setEnabled(false);
		conditionTwo.add(Box.createHorizontalStrut(5));
		conditionTwo.add(customerIndustry);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(customerIndustryText);
		conditionTwo.add(Box.createHorizontalGlue());
		
		//所在地区
		customerArea = new JCheckBox("所在地区");
		customerArea.setFont(font);
		customerAreaText = new JTextField(10);
		customerAreaText.setEnabled(false);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(customerArea);
		conditionThree.add(Box.createHorizontalStrut(10));
		conditionThree.add(customerAreaText);
		conditionThree.add(Box.createHorizontalGlue());
		
		//邮政编码
		customerPostCode = new JCheckBox("邮政编码");
		customerPostCode.setFont(font);
		customerPostCodeText = new JTextField(6);
		customerPostCodeText.setEnabled(false);
		conditionFour.add(Box.createHorizontalStrut(5));
		conditionFour.add(customerPostCode);
		conditionFour.add(Box.createHorizontalStrut(10));
		conditionFour.add(customerPostCodeText);
		conditionFour.add(Box.createHorizontalGlue());
		
		//操作区
		operation = Box.createHorizontalBox();
		customerSearch = new JButton("搜索");
		customerSearch.setFont(font);
		customerReset = new JButton("重置");
		customerReset.setFont(font);
		customerCancel = new JButton("取消");
		customerCancel.setFont(font);
		operation.add(Box.createHorizontalStrut(5));
		operation.add(customerSearch);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(customerReset);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(customerCancel);
		operation.add(Box.createHorizontalGlue());
		
		conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索条件"));
		conditionPanel.add(condition);
		operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		operationPanel.add(operation);
		content.add(conditionPanel, BorderLayout.CENTER);
		content.add(operationPanel, BorderLayout.SOUTH);
		
		setSize(400, 300);
		validate();
		
		//所属行业
		customerIndustry.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (customerIndustry.isSelected()) {
					customerIndustryText.setEnabled(true);
					customerIndustryText.requestFocus();
				}
				else {
					customerIndustryText.setEnabled(false);
				}
				
			}
			
		});
		
		//所在地区
		customerArea.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (customerArea.isSelected()) {
					customerAreaText.setEnabled(true);
					customerAreaText.requestFocus();
				}
				else {
					customerAreaText.setEnabled(false);
				}
				
			}
			
		});
		
		//邮政编码
		customerPostCode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (customerPostCode.isSelected()) {
					customerPostCodeText.setEnabled(true);
					customerPostCodeText.requestFocus();
				}
				else {
					customerPostCodeText.setEnabled(false);
				}
				
			}
			
		});
		
		//搜索
		customerSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Handler handler = new Handler();
				DoSearch ds = new DoSearch(customerInfo);
				handler.setDoHandler(ds);
				handler.process(staffInfo);
				
				if (handler.isSucced()) {
					searchContent = ds.getContent();
					resultHeader = VectorFilter.singleFilter(VectorFilter.CUSTOMERFILTER, ds.getHeader());
					
					String name = customerNameText.getText().trim();
					String industry = null;
					String area = null;
					String postcode = null;
					
					if (title.equals("部门经理")) {
						if (customerIndustry.isSelected())
							industry = customerIndustryText.getText().trim();
						if (customerArea.isSelected())
							area = customerAreaText.getText().trim();
						if (customerPostCode.isSelected())
							postcode = customerPostCodeText.getText().trim();
						logger.debug("部门经理-客户名称: " + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("部门经理-所属行业: " + industry + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, industry, searchContent);
						logger.debug("部门经理-所在地区: " + area + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, area, searchContent);
						logger.debug("部门经理-邮政编码: " + postcode + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(6, postcode, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, searchContent);
						isUpdate = true;
						searched = CUSTOMER;
						Search.this.dispose();
					}
					else if (title.equals("销售经理")) {
						String staffName = staffInfo.getStaffName().trim();
						if (customerIndustry.isSelected())
							industry = customerIndustryText.getText().trim();
						if (customerArea.isSelected())
							area = customerAreaText.getText().trim();
						if (customerPostCode.isSelected())
							postcode = customerPostCodeText.getText().trim();
						logger.debug("销售经理: " + staffName + "过滤掉不是自己负责的客户资料. - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(11, staffName, searchContent);
						logger.debug("销售经理-客户名称: " + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售经理-所属行业: " + industry + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, industry, searchContent);
						logger.debug("销售经理-所在地区: " + area + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, area, searchContent);
						logger.debug("销售经理-邮政编码: " + postcode + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(6, postcode, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, searchContent);
						isUpdate = true;
						searched = CUSTOMER;
						Search.this.dispose();
					}
					else if (title.equals("销售助理")) {
						String staffName = staffInfo.getStaffName().trim();
						if (customerIndustry.isSelected())
							industry = customerIndustryText.getText().trim();
						if (customerArea.isSelected())
							area = customerAreaText.getText().trim();
						if (customerPostCode.isSelected())
							postcode = customerPostCodeText.getText().trim();
						logger.debug("销售助理: " + staffName + "过滤掉不是自己负责的客户资料. - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(11, staffName, searchContent);
						logger.debug("销售助理-客户名称: " + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售助理-所属行业: " + industry + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, industry, searchContent);
						logger.debug("销售助理-所在地区: " + area + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, area, searchContent);
						logger.debug("销售助理-邮政编码: " + postcode + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(6, postcode, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CUSTOMERFILTER, searchContent);
						isUpdate = true;
						searched = CUSTOMER;
						Search.this.dispose();
					}
					
				}
				
			}
			
		});
		
		//重置
		customerReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customerNameText.setText("");
				customerNameText.requestFocus();
				customerIndustry.setSelected(false);
				customerIndustryText.setEnabled(false);
				customerArea.setSelected(false);
				customerAreaText.setEnabled(false);
				customerPostCode.setSelected(false);
				customerPostCodeText.setEnabled(false);
				
			}
			
		});
		
		//取消
		customerCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Search.this.dispose();
				
			}
			
		});
		
	}
	
	/**
	 * 联系人搜索
	 */
	private void contactSearch() {
		if (conditionPanel != null && operationPanel != null) {
			content.remove(conditionPanel);
			logger.debug("联系人:删除condition搜索条件区面板 - " + Search.class.getName());
			content.remove(operationPanel);
			logger.debug("联系人:删除operation操作区面板 - " + Search.class.getName());
			validate();
			repaint();
		}
		
		//搜索条件(联系人)
		Box condition = Box.createVerticalBox();
		Box conditionOne = Box.createHorizontalBox();
		Box conditionTwo = Box.createHorizontalBox();
		Box conditionThree = Box.createHorizontalBox();
		Box conditionFour = Box.createHorizontalBox();
		
		//加入条件
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionOne);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionTwo);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionThree);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionFour);
		condition.add(Box.createVerticalStrut(5));
		
		//姓名
		contactName = new JRadioButton("联系人");
		contactName.setFont(font);
		contactName.setSelected(true);
		(new ButtonGroup()).add(contactName);
		conditionOne.add(Box.createHorizontalStrut(5));
		conditionOne.add(contactName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(contactNameText);
		conditionOne.add(Box.createHorizontalStrut(160));
		
		//性别
		contactSex = new JCheckBox("性别");
		contactSex.setFont(font);
		contactSexList.setFont(font);
		contactSexList.setEnabled(false);
		conditionTwo.add(Box.createHorizontalStrut(5));
		conditionTwo.add(contactSex);
		conditionTwo.add(Box.createHorizontalStrut(22));
		conditionTwo.add(contactSexList);
		conditionTwo.add(Box.createHorizontalStrut(270));
		
		//部门
		contactDepart = new JCheckBox("部门");
		contactDepart.setFont(font);
		contactDepartText = new JTextField();
		contactDepartText.setFont(font);
		contactDepartText.setEnabled(false);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(contactDepart);
		conditionThree.add(Box.createHorizontalStrut(22));
		conditionThree.add(contactDepartText);
		conditionThree.add(Box.createHorizontalStrut(160));
		
		//职务
		contactDuty = new JCheckBox("职务");
		contactDuty.setFont(font);
		contactDutyText = new JTextField();
		contactDutyText.setEnabled(false);
		conditionFour.add(Box.createHorizontalStrut(5));
		conditionFour.add(contactDuty);
		conditionFour.add(Box.createHorizontalStrut(22));
		conditionFour.add(contactDutyText);
		conditionFour.add(Box.createHorizontalStrut(160));
		
		//操作区
		operation = Box.createHorizontalBox();
		contactSearch = new JButton("搜索");
		contactSearch.setFont(font);
		contactReset = new JButton("重置");
		contactReset.setFont(font);
		contactCancel = new JButton("取消");
		contactCancel.setFont(font);
		operation.add(Box.createHorizontalStrut(5));
		operation.add(contactSearch);
		operation.add(Box.createHorizontalStrut(5));
		operation.add(contactReset);
		operation.add(Box.createHorizontalStrut(5));
		operation.add(contactCancel);
		operation.add(Box.createHorizontalGlue());
		
		conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索条件"));
		conditionPanel.add(condition);
		operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		operationPanel.add(operation);
		content.add(conditionPanel, BorderLayout.CENTER);
		content.add(operationPanel, BorderLayout.SOUTH);
		
		setSize(400, 300);
		validate();
		
		//性别
		contactSex.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (contactSex.isSelected()) {
					contactSexList.setEnabled(true);
				}
				else {
					contactSexList.setEnabled(false);
				}
				
			}
			
		});
		
		//部门
		contactDepart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (contactDepart.isSelected()) {
					contactDepartText.setEnabled(true);
					contactDepartText.requestFocus();
				}
				else {
					contactDepartText.setEnabled(false);
				}
				
			}
			
		});
		
		//职务
		contactDuty.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (contactDuty.isSelected()) {
					contactDutyText.setEnabled(true);
					contactDutyText.requestFocus();
				}
				else {
					contactDutyText.setEnabled(false);
				}
				
			}
			
		});
		
		//搜索
		contactSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Handler handler = new Handler();
				DoSearch ds = new DoSearch(contactInfo);
				handler.setDoHandler(ds);
				handler.process(staffInfo);
				
				if (handler.isSucced()) {
					searchContent = ds.getContent();
					resultHeader = VectorFilter.singleFilter(VectorFilter.CONTACTFILTER, ds.getHeader());
					String name = contactNameText.getText().trim();
					String sex = null;
					String depart = null;
					String duty = null;
					
					if (title.equals("部门经理")) {
						if (contactSex.isSelected()) {
							sex = ((String)contactSexList.getSelectedItem()).trim();
						}
						if (contactDepart.isSelected()) {
							depart = contactDepartText.getText().trim();
						}
						if (contactDuty.isSelected()) {
							duty = contactDutyText.getText().trim();
						}
						logger.debug("部门经理-联系人名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("部门经理-联系人性别:" + sex + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, sex, searchContent);
						logger.debug("部门经理-联系人部门:" + depart + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, depart, searchContent);
						logger.debug("部门经理-联系人职务:" + duty + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(5, duty, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, searchContent);
						isUpdate = true;
						searched = CONTACT;
						Search.this.dispose();
					}
					else if (title.equals("销售经理")) {
						if (contactSex.isSelected()) {
							sex = ((String)contactSexList.getSelectedItem()).trim();
						}
						if (contactDepart.isSelected()) {
							depart = contactDepartText.getText().trim();
						}
						if (contactDuty.isSelected()) {
							duty = contactDutyText.getText().trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售经理过滤掉不是自己负责项目的联系人 - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(17, staffName, searchContent);
						logger.debug("销售经理-联系人名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售经理-联系人性别:" + sex + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, sex, searchContent);
						logger.debug("销售经理-联系人部门:" + depart + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, depart, searchContent);
						logger.debug("销售经理-联系人职务:" + duty + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(5, duty, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, searchContent);
						isUpdate = true;
						searched = CONTACT;
						Search.this.dispose();
					}
					else if (title.equals("销售助理")) {
						if (contactSex.isSelected()) {
							sex = ((String)contactSexList.getSelectedItem()).trim();
						}
						if (contactDepart.isSelected()) {
							depart = contactDepartText.getText().trim();
						}
						if (contactDuty.isSelected()) {
							duty = contactDutyText.getText().trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售助理过滤掉不是自己负责项目的联系人 - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(17, staffName, searchContent);
						logger.debug("销售助理-联系人名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售助理-联系人性别:" + sex + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, sex, searchContent);
						logger.debug("销售助理-联系人部门:" + depart + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, depart, searchContent);
						logger.debug("销售助理-联系人职务:" + duty + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(5, duty, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.CONTACTFILTER, searchContent);
						isUpdate = true;
						searched = CONTACT;
						Search.this.dispose();
					}
				}
				
			}
			
		});
		
		//重置
		contactReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				contactNameText.setText("");
				contactNameText.requestFocus();
				contactSex.setSelected(false);
				contactSexList.setEnabled(false);
				contactDepartText.setText("");
				contactDepart.setSelected(false);
				contactDutyText.setText("");
				contactDuty.setSelected(false);
					
			}
			
		});
		
		//取消
		contactCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Search.this.dispose();
				
			}
			
		});
	}
	
	/**
	 * 项目搜索
	 */
	private void projectSearch() {
		if (conditionPanel != null && operationPanel != null) {
			content.remove(conditionPanel);
			logger.debug("项目:删除condition搜索条件区面板 - " + Search.class.getName());
			content.remove(operationPanel);
			logger.debug("项目:删除operation操作区面板 - " + Search.class.getName());
			validate();
			repaint();
		}
		
		//搜索条件(项目)
		Box condition = Box.createVerticalBox();
		Box conditionOne = Box.createHorizontalBox();
		Box conditionTwo = Box.createHorizontalBox();
		Box conditionThree = Box.createHorizontalBox();
		Box conditionFour = Box.createHorizontalBox();
		Box conditionFive = Box.createHorizontalBox();
		
		//加入搜索条件
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionOne);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionTwo);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionThree);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionFour);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionFive);
		condition.add(Box.createVerticalStrut(5));
		
		//项目名称
		projectName = new JRadioButton("项目名称", true);
		projectName.setFont(font);
		(new ButtonGroup()).add(projectName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(projectName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(projectNameText);
		conditionOne.add(Box.createHorizontalStrut(160));
		
		//项目客户
		projectCustomer = new JCheckBox("客户名称");
		projectCustomer.setFont(font);
		projectCustomerText = new JTextField();
		projectCustomerText.setEnabled(false);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(projectCustomer);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(projectCustomerText);
		conditionTwo.add(Box.createHorizontalStrut(160));
		
		//项目联系人
		projectContact = new JCheckBox("联系人");
		projectContact.setFont(font);
		projectContactText = new JTextField();
		projectContactText.setEnabled(false);
		conditionThree.add(Box.createHorizontalStrut(10));
		conditionThree.add(projectContact);
		conditionThree.add(Box.createHorizontalStrut(22));
		conditionThree.add(projectContactText);
		conditionThree.add(Box.createHorizontalStrut(160));
		
		//项目负责人
		projectPrincipal = new JCheckBox("负责人");
		projectPrincipal.setFont(font);
		projectPrincipalText = new JTextField();
		projectPrincipalText.setEnabled(false);
		conditionFour.add(Box.createHorizontalStrut(10));
		conditionFour.add(projectPrincipal);
		conditionFour.add(Box.createHorizontalStrut(22));
		conditionFour.add(projectPrincipalText);
		conditionFour.add(Box.createHorizontalStrut(160));
		
		//项目状态
		projectState = new JCheckBox("项目状态");
		projectState.setFont(font);
		projectStateList.setFont(font);
		projectStateList.setEnabled(false);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(projectState);
		conditionFive.add(Box.createHorizontalStrut(10));
		conditionFive.add(projectStateList);
		conditionFive.add(Box.createHorizontalStrut(240));
		
		//操作区
		operation = Box.createHorizontalBox();
		projectSearch = new JButton("搜索");
		projectSearch.setFont(font);
		projectReset = new JButton("重置");
		projectReset.setFont(font);
		projectCancel = new JButton("取消");
		projectCancel.setFont(font);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(projectSearch);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(projectReset);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(projectCancel);
		operation.add(Box.createHorizontalGlue());
		
		conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索条件"));
		conditionPanel.add(condition);
		operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		operationPanel.add(operation);
		content.add(conditionPanel, BorderLayout.CENTER);
		content.add(operationPanel, BorderLayout.SOUTH);
		
		setSize(400, 330);
		validate();
		
		//客户名称
		projectCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (projectCustomer.isSelected()) {
					projectCustomerText.setEnabled(true);
					projectCustomerText.requestFocus();
				}
				else {
					projectCustomerText.setEnabled(false);
				}
				
			}
			
		});
		
		//联系人
		projectContact.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (projectContact.isSelected()) {
					projectContactText.setEnabled(true);
					projectContactText.requestFocus();
				}
				else {
					projectContactText.setEnabled(false);
				}
				
			}
			
		});
		
		//负责人
		projectPrincipal.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (projectPrincipal.isSelected()) {
					projectPrincipalText.setEnabled(true);
					projectPrincipalText.requestFocus();
				}
				else {
					projectPrincipalText.setEnabled(false);
				}
				
			}
			
		});
		
		//项目状态
		projectState.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (projectState.isSelected()) {
					projectStateList.setEnabled(true);
				}
				else {
					projectStateList.setEnabled(false);
				}
				
			}
			
		});
		
		//搜索
		projectSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Handler handler = new Handler();
				DoSearch ds = new DoSearch(projectInfo);
				handler.setDoHandler(ds);
				handler.process(staffInfo);
				
				if (handler.isSucced()) {
					searchContent = ds.getContent();
					resultHeader = VectorFilter.singleFilter(VectorFilter.PROJECTFILTER, ds.getHeader());
					
					String name = projectNameText.getText().trim();
					String customer = null;
					String contact = null;
					String staff = null;
					String state = null;
					
					if (title.equals("部门经理")) {
						if (projectCustomer.isSelected()) {
							customer = projectCustomerText.getText().trim();
						}
						if (projectContact.isSelected()) {
							contact = projectContactText.getText().trim();
						}
						if (projectPrincipal.isSelected()) {
							staff = projectPrincipalText.getText().trim();
						}
						if (projectState.isSelected()) {
							state = ((String)projectStateList.getSelectedItem()).trim();
						}
						logger.debug("部门经理-项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("部门经理-客户:" + customer + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, customer, searchContent);
						logger.debug("部门经理-联系人:" + contact + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(3, contact, searchContent);
						logger.debug("部门经理-负责人:" + staff + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, staff, searchContent);
						logger.debug("部门经理-项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(7, state, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, searchContent);
						isUpdate = true;
						searched = PROJECT;
						Search.this.dispose();
					}
					else if (title.equals("销售经理")) {
						if (projectCustomer.isSelected()) {
							customer = projectCustomerText.getText().trim();
						}
						if (projectContact.isSelected()) {
							contact = projectContactText.getText().trim();
						}
						if (projectPrincipal.isSelected()) {
							staff = projectPrincipalText.getText().trim();
						}
						if (projectState.isSelected()) {
							state = ((String)projectStateList.getSelectedItem()).trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售经理过滤掉不是自己负责的项目:" + staffName + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, staffName, searchContent);
						logger.debug("销售经理-项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售经理-客户:" + customer + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, customer, searchContent);
						logger.debug("销售经理-联系人:" + contact + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(3, contact, searchContent);
						logger.debug("销售经理-负责人:" + staff + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, staff, searchContent);
						logger.debug("销售经理-项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(7, state, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, searchContent);
						isUpdate = true;
						searched = PROJECT;
						Search.this.dispose();
						
					}
					else if (title.equals("销售助理")) {
						if (projectCustomer.isSelected()) {
							customer = projectCustomerText.getText().trim();
						}
						if (projectContact.isSelected()) {
							contact = projectContactText.getText().trim();
						}
						if (projectPrincipal.isSelected()) {
							staff = projectPrincipalText.getText().trim();
						}
						if (projectState.isSelected()) {
							state = ((String)projectStateList.getSelectedItem()).trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售助理过滤掉不是自己负责的项目:" + staffName + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, staffName, searchContent);
						logger.debug("销售助理-项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售助理-客户:" + customer + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, customer, searchContent);
						logger.debug("销售助理-联系人:" + contact + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(3, contact, searchContent);
						logger.debug("销售助理-负责人:" + staff + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, staff, searchContent);
						logger.debug("销售助理-项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(7, state, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.PROJECTFILTER, searchContent);
						isUpdate = true;
						searched = PROJECT;
						Search.this.dispose();
					}
				}
				
			}
			
		});
		
		//重置
		projectReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				projectNameText.setText("");
				projectNameText.requestFocus();
				projectCustomerText.setText("");
				projectContactText.setText("");
				projectPrincipalText.setText("");
				projectState.setSelected(false);
				projectStateList.setEnabled(false);
				
			}
			
		});
		
		//取消
		projectCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Search.this.dispose();
				
			}
			
		});
	}
	
	/**
	 * 跟踪记录搜索
	 */
	private void trackRecordSearch() {
		if (conditionPanel != null && operationPanel != null) {
			content.remove(conditionPanel);
			logger.debug("跟踪记录:删除condition搜索条件区面板 - " + Search.class.getName());
			content.remove(operationPanel);
			logger.debug("跟踪记录:删除operation操作区面板 - " + Search.class.getName());
			validate();
			repaint();
		}
		
		//搜索条件(跟踪记录)
		Box condition = Box.createVerticalBox();
		Box conditionOne = Box.createHorizontalBox();
		Box conditionTwo = Box.createHorizontalBox();
		Box conditionThree = Box.createHorizontalBox();
		Box conditionFour = Box.createHorizontalBox();
		
		//加入搜索条件
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionOne);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionTwo);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionThree);
		condition.add(Box.createVerticalStrut(5));
		condition.add(conditionFour);
		condition.add(Box.createVerticalStrut(5));
		
		//跟踪记录对应项目的项目名称
		trackRecordProjectName = new JRadioButton("项目名称", true);
		trackRecordProjectName.setFont(font);
		(new ButtonGroup()).add(trackRecordProjectName);
		trackRecordProjectNameText.requestFocus();
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(trackRecordProjectName);
		conditionOne.add(Box.createHorizontalStrut(10));
		conditionOne.add(trackRecordProjectNameText);
		conditionOne.add(Box.createHorizontalStrut(160));
		
		//跟踪记录对应项目的项目状态
		trackRecordProjectState = new JCheckBox("项目状态");
		trackRecordProjectState.setFont(font);
		trackRecordProjectStateList.setFont(font);
		trackRecordProjectStateList.setEnabled(false);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(trackRecordProjectState);
		conditionTwo.add(Box.createHorizontalStrut(10));
		conditionTwo.add(trackRecordProjectStateList);
		conditionTwo.add(Box.createHorizontalStrut(250));
		
		//跟踪记录日期
		trackRecordDate = new JCheckBox("跟踪日期");
		trackRecordDate.setFont(font);
		trackRecordDateFrom = new JTextField();
		trackRecordDateFrom.setEditable(false);
		trackRecordDateTo = new JTextField();
		trackRecordDateTo.setEditable(false);
		trackRecordCalendarFrom.setEnabled(false);
		trackRecordCalendarTo.setEnabled(false);
		JLabel to = new JLabel("到");
		to.setFont(font);
		conditionThree.add(Box.createHorizontalStrut(10));
		conditionThree.add(trackRecordDate);
		conditionThree.add(Box.createHorizontalStrut(10));
		conditionThree.add(trackRecordDateFrom);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(trackRecordCalendarFrom);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(to);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(trackRecordDateTo);
		conditionThree.add(Box.createHorizontalStrut(5));
		conditionThree.add(trackRecordCalendarTo);
		conditionThree.add(Box.createHorizontalStrut(20));
		
		//跟踪方式
		trackRecordWay = new JCheckBox("跟踪方式");
		trackRecordWay.setFont(font);
		trackRecordWayList.setFont(font);
		trackRecordWayList.setEnabled(false);
		conditionFour.add(Box.createHorizontalStrut(10));
		conditionFour.add(trackRecordWay);
		conditionFour.add(Box.createHorizontalStrut(10));
		conditionFour.add(trackRecordWayList);
		conditionFour.add(Box.createHorizontalStrut(250));
		
		//操作区
		operation = Box.createHorizontalBox();
		trackRecordSearch = new JButton("搜索");
		trackRecordSearch.setFont(font);
		trackRecordReset = new JButton("重置");
		trackRecordReset.setFont(font);
		trackRecordCancel = new JButton("取消");
		trackRecordCancel.setFont(font);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(trackRecordSearch);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(trackRecordReset);
		operation.add(Box.createHorizontalStrut(10));
		operation.add(trackRecordCancel);
		operation.add(Box.createHorizontalGlue());
		
		conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		conditionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "搜索条件"));
		conditionPanel.add(condition);
		operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		operationPanel.add(operation);
		content.add(conditionPanel, BorderLayout.CENTER);
		content.add(operationPanel, BorderLayout.SOUTH);
		
		setSize(400, 300);
		validate();
		
		//项目状态
		trackRecordProjectState.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (trackRecordProjectState.isSelected()) {
					trackRecordProjectStateList.setEnabled(true);
				}
				else {
					trackRecordProjectStateList.setEnabled(false);
				}
				
			}
			
		});
		
		//跟踪日期
		trackRecordDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (trackRecordDate.isSelected()) {
					trackRecordCalendarFrom.setEnabled(true);
					trackRecordCalendarTo.setEnabled(true);
				}
				else {
					trackRecordCalendarFrom.setEnabled(false);
					trackRecordCalendarTo.setEnabled(false);
				}
				
			}
			
		});
		
		trackRecordCalendarFrom.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (trackRecordDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					trackRecordDateFrom.setText(date.getChosenDate("-"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (trackRecordDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (trackRecordDate.isSelected())
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		});
		
		trackRecordCalendarTo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (trackRecordDate.isSelected()) {
					DateChooserDialog date = new DateChooserDialog(Search.this, "");
					date.setVisible(true);
					trackRecordDateTo.setText(date.getChosenDate("-"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				if (trackRecordDate.isSelected()) {
					Search.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (trackRecordDate.isSelected()) {
					Search.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			
		});
		
		//跟踪方式
		trackRecordWay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (trackRecordWay.isSelected()) {
					trackRecordWayList.setEnabled(true);
				}
				else {
					trackRecordWayList.setEnabled(false);
				}
				
			}
			
		});
		
		//搜索
		trackRecordSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Handler handler = new Handler();
				DoSearch ds = new DoSearch(trackRecordInfo);
				handler.setDoHandler(ds);
				handler.process(staffInfo);
				
				if (handler.isSucced()) {
					searchContent = ds.getContent();
					resultHeader = VectorFilter.singleFilter(VectorFilter.TRACKRECORDFILTER, ds.getHeader());
					
					String name = trackRecordProjectNameText.getText().trim();
					String state = null;
					Date trackDateFrom = null;
					Date trackDateTo = null;
					String trackWay = null;
					
					if (title.equals("部门经理")) {
						if (trackRecordProjectState.isSelected()) {
							state = ((String)trackRecordProjectStateList.getSelectedItem()).trim();
						}
						if (trackRecordDate.isSelected()) {
							trackDateFrom = ParseStringAndDate.parseString2Date(trackRecordDateFrom.getText(), "-");
							trackDateTo = ParseStringAndDate.parseString2Date(trackRecordDateTo.getText(), "-");
						}
						if (trackRecordWay.isSelected()) {
							trackWay = ((String)trackRecordWayList.getSelectedItem()).trim();
						}
						logger.debug("部门经理-跟踪记录对应的项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("部门经理-跟踪记录对应的项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, state, searchContent);
						logger.debug("部门经理-跟踪记录日期范围:" + trackDateFrom + "-" + trackDateTo + " - " + Search.class.getName());
						searchContent = VectorFilter.dateFilter(3, trackDateFrom, trackDateTo, searchContent);
						logger.debug("部门经理-跟踪方式:" + trackWay + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, trackWay, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, searchContent);
						isUpdate = true;
						searched = TRACKRECORD;
						Search.this.dispose();
					}
					else if (title.equals("销售经理")) {
						if (trackRecordProjectState.isSelected()) {
							state = ((String)trackRecordProjectStateList.getSelectedItem()).trim();
						}
						if (trackRecordDate.isSelected()) {
							trackDateFrom = ParseStringAndDate.parseString2Date(trackRecordDateFrom.getText(), "-");
							trackDateTo = ParseStringAndDate.parseString2Date(trackRecordDateTo.getText(), "-");
						}
						if (trackRecordWay.isSelected()) {
							trackWay = ((String)trackRecordWayList.getSelectedItem()).trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售经理过滤掉不是自己负责项目的跟踪记录:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(7, staffName, searchContent);
						logger.debug("销售经理-跟踪记录对应的项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售经理-跟踪记录对应的项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, state, searchContent);
						logger.debug("销售经理-跟踪记录日期范围:" + trackDateFrom + "-" + trackDateTo + " - " + Search.class.getName());
						searchContent = VectorFilter.dateFilter(3, trackDateFrom, trackDateTo, searchContent);
						logger.debug("销售经理-跟踪方式:" + trackWay + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, trackWay, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, searchContent);
						isUpdate = true;
						searched = TRACKRECORD;
						Search.this.dispose();
					}
					else if (title.equals("销售助理")) {
						if (trackRecordProjectState.isSelected()) {
							state = ((String)trackRecordProjectStateList.getSelectedItem()).trim();
						}
						if (trackRecordDate.isSelected()) {
							trackDateFrom = ParseStringAndDate.parseString2Date(trackRecordDateFrom.getText(), "-");
							trackDateTo = ParseStringAndDate.parseString2Date(trackRecordDateTo.getText(), "-");
						}
						if (trackRecordWay.isSelected()) {
							trackWay = ((String)trackRecordWayList.getSelectedItem()).trim();
						}
						String staffName = staffInfo.getStaffName().trim();
						logger.debug("销售助理过滤掉不是自己负责项目的跟踪记录:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(7, staffName, searchContent);
						logger.debug("销售助理-跟踪记录对应的项目名称:" + name + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(1, name, searchContent);
						logger.debug("销售助理-跟踪记录对应的项目状态:" + state + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(2, state, searchContent);
						logger.debug("销售助理-跟踪记录日期范围:" + trackDateFrom + "-" + trackDateTo + " - " + Search.class.getName());
						searchContent = VectorFilter.dateFilter(3, trackDateFrom, trackDateTo, searchContent);
						logger.debug("销售助理-跟踪方式:" + trackWay + " - " + Search.class.getName());
						searchContent = VectorFilter.positionFilter(4, trackWay, searchContent);
						resultContent = VectorFilter.doubleFilter(VectorFilter.TRACKRECORDFILTER, searchContent);
						isUpdate = true;
						searched = TRACKRECORD;
						Search.this.dispose();
					}
				}
				
			}
			
		});
		
		//重置
		trackRecordReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				trackRecordProjectNameText.setText("");
				trackRecordProjectNameText.requestFocus();
				trackRecordProjectState.setSelected(false);
				trackRecordProjectStateList.setEnabled(false);
				trackRecordDate.setSelected(false);
				trackRecordCalendarFrom.setEnabled(false);
				trackRecordCalendarTo.setEnabled(false);
				trackRecordWay.setSelected(false);
				trackRecordWayList.setEnabled(false);
				trackRecordDateFrom.setText("");
				trackRecordDateTo.setText("");
				
			}
			
		});
		
		//取消
		trackRecordCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Search.this.dispose();
				
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
		return isUpdate;
	}
	
	/**
	 * 得到搜索结果内容
	 */
	public Vector<Vector<String>> getResultContent() {
		return this.resultContent;
	}
	
	/**
	 * 得到搜索结果标头
	 */
	public Vector<String> getResultHeader() {
		return this.resultHeader;
	}
	
	/**
	 * 得到用户搜索的是哪类信息
	 */
	public int getSearched() {
		return searched;
	}
	
	private boolean isUpdate = false;
	private JPanel content = null;
	private Box range = null; //搜索范围
	private Box condition = null; //搜索条件
	private JPanel conditionPanel = null;
	private Box operation = null; //操作
	private JPanel operationPanel = null;
	
	private ButtonGroup searchRange = null; //搜索范围
	private JRadioButton staff = new JRadioButton("销售人员"); //销售人员
	private JRadioButton customer = new JRadioButton("客户"); //客户
	private JRadioButton contact = new JRadioButton("联系人"); //联系人
	private JRadioButton project = new JRadioButton("项目"); //项目
	private JRadioButton trackRecord = new JRadioButton("跟踪记录"); //跟踪记录
	
	//销售人员搜索条件
	private JRadioButton staffName = null; //姓名
	private JTextField staffNameText = new JTextField(); //姓名文本框
	private JCheckBox staffSex = null; //性别
	private JComboBox staffSexList = new JComboBox(GetInfo.SEX); //性别下拉列表
	private JCheckBox staffPosition = null; //职位
	private JComboBox staffPositionList = null; //职位下拉列表
	private JCheckBox inDate = null; //入职日期
	private JTextField inDateFrom = null; //起始入职日期
	private JTextField inDateTo = null; //终止入职日期
	private JCheckBox outDate = null; //离职日期
	private JTextField outDateFrom = null; //起始离职日期
	private JTextField outDateTo = null; //终止离职日期
	
	//客户搜索条件
	private JRadioButton customerName = null; //客户名称
	private JTextField customerNameText = new JTextField(); //客户名称文本框
	private JCheckBox customerIndustry = null; //所属行业
	private JTextField customerIndustryText = null; //所属行业文本框
	private JCheckBox customerArea = null; //所在地区
	private JTextField customerAreaText = null; //所在地区文本框
	private JCheckBox customerPostCode = null; //邮政编码
	private JTextField customerPostCodeText = null; //邮政编码文本框
	
	//联系人搜索条件
	private JRadioButton contactName = null; //联系人姓名
	private JTextField contactNameText = new JTextField(); //联系人姓名文本框
	private JCheckBox contactSex = null; //性别
	private JComboBox contactSexList = new JComboBox(GetInfo.SEX); //性别下拉列表
	private JCheckBox contactDepart = null; //部门
	private JTextField contactDepartText = null; //部门文本框
	private JCheckBox contactDuty = null; //职务
	private JTextField contactDutyText = null; //职务文本框
	
	//项目搜索条件
	private JRadioButton projectName = null; //项目名称
	private JTextField projectNameText = new JTextField(); //项目名称文本框
	private JCheckBox projectCustomer = null; //客户
	private JTextField projectCustomerText = null; //客户文本框
	private JCheckBox projectContact = null; //联系人
	private JTextField projectContactText = null; //联系人文本框
	private JCheckBox projectPrincipal = null; //负责人
	private JTextField projectPrincipalText = null; //负责人文本框
	private JCheckBox projectState = null; //项目状态
	private JComboBox projectStateList = new JComboBox(GetInfo.PROJECTSTATE); //项目状态下拉列表
	
	//跟踪记录搜索条件
	private JRadioButton trackRecordProjectName = null; //跟踪记录的项目名称
	private JTextField trackRecordProjectNameText = new JTextField(); //跟踪记录的项目名称文本框
	private JCheckBox trackRecordProjectState = null; //跟踪记录的项目状态
	private JComboBox trackRecordProjectStateList = new JComboBox(GetInfo.PROJECTSTATE); //跟踪记录的项目状态下拉列表
	private JCheckBox trackRecordDate = null; //跟踪记录日期
	private JTextField trackRecordDateFrom = null; //起始日期
	private JTextField trackRecordDateTo = null; //终止日期
	private JCheckBox trackRecordWay = null; //跟踪方式
	private JComboBox trackRecordWayList = new JComboBox(GetInfo.TRACKWAY); //跟踪方式下拉列表
	
	private JLabel inCalendarFrom = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	private JLabel inCalendarTo = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	private JLabel outCalendarFrom = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	private JLabel outCalendarTo = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	private JLabel trackRecordCalendarFrom = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	private JLabel trackRecordCalendarTo = new JLabel(new ImageIcon(Search.class.getResource("/images/calendar.gif")));
	
	private JButton staffSearch = null; //搜索销售人员
	private JButton staffReset = null; //重置销售人员
	private JButton staffCancel = null; //取消销售人员
	
	private JButton customerSearch = null; //搜索客户
	private JButton customerReset = null; //重置客户
	private JButton customerCancel = null; //取消客户
	
	private JButton contactSearch = null; //搜索联系人
	private JButton contactReset = null; //重置联系人
	private JButton contactCancel = null; //取消联系人
	
	private JButton projectSearch = null; //搜索项目
	private JButton projectReset = null; //重置项目
	private JButton projectCancel = null; //取消项目
	
	private JButton trackRecordSearch = null; //搜索跟踪记录
	private JButton trackRecordReset = null; //重置跟踪记录
	private JButton trackRecordCancel = null; //取消跟踪记录
	
	private StaffInfo staffInfo = null; //销售人员信息Bean
	private String title = null; //销售人员角色
	private StaffInfo searchStaffInfo = new StaffInfo(); //搜索时用的销售人员信息Bean
	private CustomerInfo customerInfo = new CustomerInfo(); //客户信息Bean
	private ContactInfo contactInfo = new ContactInfo(); //联系人信息Bean
	private ProjectInfo projectInfo = new ProjectInfo(); //项目信息Bean
	private TrackRecordInfo trackRecordInfo = new TrackRecordInfo(); //跟踪记录Bean
	
	private Vector<Vector<String>> searchContent = new Vector<Vector<String>>();
	private Vector<Vector<String>> resultContent = new Vector<Vector<String>>();
	private Vector<String> resultHeader = new Vector<String>();
	
	private static final Font font = new Font("宋体", Font.PLAIN, 12);
	private static Logger logger = Logger.getLogger(Search.class);
	
	//标记用户查找的是什么信息
	private final int STAFF = 0;
	private final int CUSTOMER = 1;
	private final int CONTACT = 2;
	private final int PROJECT = 3;
	private final int TRACKRECORD = 4;
	private int searched = STAFF;
	
	public static void main(String[] args) {
		StaffInfo staffInfo = new StaffInfo();
		staffInfo.setStaffID(1);
		staffInfo.setPosName("部门经理");
		Search s = new Search(null, staffInfo);
		s.create();
	}
}
