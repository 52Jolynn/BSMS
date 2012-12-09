package com.p6.toolkit.DateChooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * 日期选择对话框
 * @author xiaogy
 *
 */
public class DateChooserDialog extends JDialog{
	
	/**
	 * 初始化日期选择对话框
	 * @param owner 父窗体
	 * @param dateInTextBox	在文本框中日期
	 */
	public DateChooserDialog (Component owner,String dateInTextBox)
	{	
		this.setLocationRelativeTo(owner);
		dateChooserPanel = new DateChooserPanel();
		chooseDate = new Date();
		year = chooseDate.getYear()+1900;
		month = chooseDate.getMonth()+1;
		date = chooseDate.getDate();
		oldDateText = dateInTextBox;
		this.setLayout(new BorderLayout());
		if(getChosenDate("-").equals(""))
			this.setTitle("选择日期");
		else
			this.setTitle("选择日期  当前日期为: "+getChosenDate("-"));
		this.setModal(true);
		this.setResizable(false);
		this.setSize(300,250);
		this.add(dateChooserPanel, BorderLayout.CENTER);
				
		JButton ok = new JButton("确定");
		JButton cancel = new JButton("取消");
	
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e) {
				chooseDate = dateChooserPanel.getDate();	
				year = chooseDate.getYear()+1900;
				month = chooseDate.getMonth()+1;
				date = chooseDate.getDate();
				choseDate = true;
				DateChooserDialog.this.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e) {
		
				oldDate = ParseStringAndDate.parseString2Date(oldDateText, "-");
				if(oldDate !=null){
					year = oldDate.getYear()+1900;
					month = oldDate.getMonth()+1;
					date = oldDate.getDate();
				}
				
				choseDate = false;
				DateChooserDialog.this.dispose();
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		this.add(buttonPanel,BorderLayout.SOUTH);
		
	
	}
	/**
	 * 得到用户选择的日期（年月日）
	 *@param separator String 
	 *分割日期的字符
	 *@return 返回用户选择的日期
	 *如果用户没有选择日期，则返回当前系统日期
	 */
	public String getChosenDate(String separator)
	{
		if(choseDate == true)
			return year.toString()+separator+month.toString()+separator+date.toString();
		else{
			if(oldDate == null)
				return "";
			else
				return year.toString()+separator+month.toString()+separator+date.toString();
		}
	}
	/**
	 * 返回用户选择的年
	 */
	public String getYear()	{
		year = chooseDate.getYear()+1900;
		return year.toString();
	}
	
	/**
	 * 返回用户选择的月
	 */
	public String getMonth(){
		month = chooseDate.getMonth()+1;
		return month.toString();
	}
	
	/**
	 * 返回用户选择的日
	 * @return 以字符串形式返回日
	 */
	public String getDate(){
		date = chooseDate.getDate();
		return date.toString();
	}
	
	/**
	 * 返回选择日期
	 * @return 选择的日期
	 */
	public Date returnDate()
	{
		return chooseDate;
	}
	
	private DateChooserPanel dateChooserPanel = null;	//日期选择面板
	private Date chooseDate = null;		//用户选择的日期
	private Integer year;				//年
	private Integer month;				//月
	private Integer date;				//日
	boolean choseDate = false;			//是否已经选择日期了
	private static Date oldDate;		//之前选择的日期
	private String oldDateText;			//之前选择的日期的字符串形式
	
}
