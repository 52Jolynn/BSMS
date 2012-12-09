package com.p6.frames.dialogs.contact;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.p6.toolkit.CheckTools;
import com.p6.toolkit.CnToSpell;
import com.p6.toolkit.GBC;
import com.p6.toolkit.GetInfo;
import com.p6.toolkit.MessageBox;
import com.p6.toolkit.NumOnlyDocument;
import com.p6.toolkit.DateChooser.DateChooserDialog;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Handler;
import com.p6.user.contact.DoAddContact;
import com.p6.user.infoBean.ContactInfo;
/**
 * <p>Description: 添加联系人对话框</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: P6</p>
 * @author xiaogy
 * @version 1.0
 */
public class AddContact extends ContactDialog {
	
	
	/**
	 * 
	 * @param owner 父窗体
	 */
	public AddContact(final JFrame owner) {
		//设置对话框属性
		setLayout(new BorderLayout());
		setTitle("新增联系人");
		setResizable(false);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
		setModal(true);
		setLocationRelativeTo(owner);
		
		tabs = new JTabbedPane(); //选项卡面板
		
		//三个选项卡的内容面板
		JPanel basicInfo = new JPanel(new GridBagLayout());
		JPanel contInfo  = new JPanel(new GridBagLayout());
		JPanel additionalInfo = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel();
		
		//基本信息面板内容
		customerNames  = new JComboBox();
		contactName = new JTextField(10);
		contactSex  = new JComboBox(new String[]{"男", "女"});
		contactSex.setSelectedIndex(0);
		contactBirth = new JTextField(6);
		contactDepart= new JTextField(10);
		contactDuty  = new JTextField(10);
		contactTitle = new JTextField(10);
		contactBirth.setEditable(false);
		birthChooseButton = new JButton(new ImageIcon(AddContact.class.getResource("/images/calendar.gif")));
		birthChooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				DateChooserDialog dlg = new DateChooserDialog(AddContact.this,"");
				dlg.setVisible(true);
				contactBirth.setText(dlg.getChosenDate("-"));
			}
		});
		//为基本信息选项卡面板加入内容
		basicInfo.add(new JLabel("客户"), new GBC(0, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(customerNames, new GBC(2, 0, 10, 2).setInsets(3).setFill(GBC.HORIZONTAL));
		basicInfo.add(new JLabel("姓名"), new GBC(16, 0, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactName, new GBC(18, 0, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("性别"), new GBC(0, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactSex, new GBC(2, 4, 10, 2).setInsets(2).setAnchor(GBC.WEST));
		basicInfo.add(new JLabel("生日"), new GBC(16, 4, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactBirth, new GBC(18, 4, 6, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(birthChooseButton, new GBC(24, 4, 1, 2).setInsets(1).setIPad(-18, -8));
		basicInfo.add(new JLabel("部门"), new GBC(0, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactDepart, new GBC(2, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("职位"), new GBC(16, 8, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactDuty, new GBC(18, 8, 10, 2).setInsets(3));
		basicInfo.add(new JLabel("职称"), new GBC(0, 12, 2, 2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		basicInfo.add(contactTitle, new GBC(2, 12, 10, 2).setInsets(3));
		
		//联系信息面板内容
		contactMTel = new JTextField(10); 	//手机
		contactFax = new JTextField(10); 	//传真
		contactOTel = new JTextField(10); 	//办公电话
		contactHTel = new JTextField(10); 	//家庭电话
		contactETel = new JTextField(10); 	//其他电话
		contactEMail = new JTextField(10); 	//电子邮件
		contactMTel.setDocument(new NumOnlyDocument());
		contactFax.setDocument(new NumOnlyDocument());
		contactOTel.setDocument(new NumOnlyDocument());
		contactHTel.setDocument(new NumOnlyDocument());
		contactETel.setDocument(new NumOnlyDocument());		
		
		contInfo.add(new JLabel("手机"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactMTel,new GBC(2,0,10,2).setInsets(3));
		contInfo.add(new JLabel("传真"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactFax,new GBC(18,0,10,2).setInsets(3));
		contInfo.add(new JLabel("办公电话"),new GBC(0,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactOTel,new GBC(2,4,10,2).setInsets(3));
		contInfo.add(new JLabel("家庭电话"),new GBC(16,4,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactHTel,new GBC(18,4,10,2).setInsets(3));
		contInfo.add(new JLabel("其他电话"),new GBC(0,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactETel,new GBC(2,8,2,2).setInsets(3));
		contInfo.add(new JLabel("电子邮件"),new GBC(16,8,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(3));
		contInfo.add(contactEMail,new GBC(18,8,2,2).setInsets(3));
		
		//附加信息面板
		contactChar = new JTextArea(5,10); //性格描述
		contactHobby = new JTextArea(5,10); //爱好描述
		contactRemark = new JTextArea(3,16); //备注
	
		additionalInfo.add(new JLabel("性格描述"),new GBC(0,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (contactChar),new GBC(2,0,10,5).setInsets(5));
		additionalInfo.add(new JLabel("爱好描述"),new GBC(16,0,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane(contactHobby),new GBC(18,0,10,5).setInsets(5));
		additionalInfo.add(new JLabel("备        注"),new GBC(0,6,2,2).setAnchor(GBC.WEST).setAnchor(GBC.CENTER).setInsets(5));
		additionalInfo.add(new JScrollPane (contactRemark),new GBC(2,6,16,3).setInsets(5));
		
		//按钮面板
		submit = new JButton("确定");
		cancel = new JButton("取消");
		
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				if(setContactInfo()==false)
					return;
				Handler handler = new Handler();
				handler.setDoHandler(new DoAddContact());
				handler.process(contactInfo);
				
				if (handler.isSucced()){		//操作成功
					AddContact.this.isEdit = true;
					new MessageBox("成功添加联系人","成功",owner);
					AddContact.this.dispose();
				}
				else {							//操作失败
					AddContact.this.isEdit = false;
					new MessageBox("添加联系人失败，请重新尝试","失败",owner);
				}
			}
		});
		//取消键的响应函数
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				AddContact.this.isEdit = false;
				AddContact.this.dispose();
			}
		});		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		//将选项卡加入选项卡面板
		tabs.addTab("基本信息", basicInfo);
		tabs.addTab("联系信息", contInfo);
		tabs.addTab("附加信息", additionalInfo);
		
		//加入选项卡面板
		add(tabs,BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);	
		
		//得到所有的用户名，并将其显示在对应的控件中
		Vector<Vector<String>> customerNamesVector = GetInfo.getAllCustomer();
		int customerCount = customerNamesVector.size();
		allCustomerIDs = new int[customerCount];
		allCustomerNames = new String[customerCount];
		customerNames.addItem("请选择客户名称");
		for(int i=0; i<customerCount; i++){
			allCustomerIDs[i] = new Integer(customerNamesVector.get(i).get(0)); //index1 customer ID 在表customerInfo的位置 
			allCustomerNames[i] = customerNamesVector.get(i).get(1);//index2 customer Names 在表customerInfo的位置
			customerNames.addItem(allCustomerNames[i]);
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
	 * 将窗口内信息存入InfoBean中
	 */
	private boolean setContactInfo() {
		//检查填写的信息是否有效
		if(checkData()==false)
			return false;
		//从窗体中得到用户的输入
		contactInfo.setCustomerID(allCustomerIDs[customerNames.getSelectedIndex()-1]);
		contactInfo.setContactName(contactName.getText());
		contactInfo.setContactSex((String)contactSex.getSelectedItem());
		contactInfo.setContactBirth(ParseStringAndDate.parseString2Date( contactBirth.getText(),"-"));
		contactInfo.setContactDepart(contactDepart.getText());
		contactInfo.setContactDuty(contactDuty.getText());
		contactInfo.setContactTitle(contactTitle.getText());
		
		contactInfo.setContactMTel(contactMTel.getText());
		contactInfo.setContactFax(contactFax.getText());
		contactInfo.setContactOTel(contactOTel.getText());
		contactInfo.setContactHTel(contactHTel.getText());
		contactInfo.setContactETel(contactETel.getText());
		contactInfo.setContactEMail(contactEMail.getText());
		
		contactInfo.setContactChar(contactChar.getText());
		contactInfo.setContactHobby(contactHobby.getText());
		contactInfo.setContactRemark(contactRemark.getText());
		contactInfo.setSiftKey(CnToSpell.getFullSpell(contactInfo.getContactName()));
		
		return true;
	}
	
	/**
	 * 检查用户输入的数据的有效性。如果有错，显示错误提示框
	 * @return 有效返回true，否则false
	 */
	private boolean checkData(){
		//初始化是否成功
		if( allCustomerIDs ==null){		
			new MessageBox("客户信息初始化中,请等待","错误",this);
			return false;
		}
		
		//是否已经选择了客户名称
		if(customerNames.getSelectedIndex()==0){	
			new MessageBox("请选择客户名称","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		
		 //是否填写联系人名称
		if((contactName.getText()).equals("")){  
			new MessageBox("请填写联系人名称","提示",this);
			tabs.setSelectedIndex(0);
			return false;
		}
		
		//如果email非空，检查Email是否有效
		if(!(contactEMail.getText()).equals("") &&  !CheckTools.isEmailValid(contactEMail.getText())){
			new MessageBox("请填写有效的电子邮件地址","提示",this);
			tabs.setSelectedIndex(1);
			return false;
		}
		return true;
			
	}
	private JTabbedPane tabs = null; 			//选项卡
	
	private JComboBox customerNames = null;		//客户
	private JTextField contactName = null; 		//姓名
	private JComboBox  contactSex = null; 		//性别
	private JTextField contactBirth = null; 	//生日
	private JTextField contactDepart = null;	//部门
	private JTextField contactDuty = null; 		//职位
	private JTextField contactTitle = null; 	//职称
	
	private JTextField contactMTel = null; 	//手机
	private JTextField contactFax = null; 	//传真
	private JTextField contactOTel = null; 	//办公电话
	private JTextField contactHTel = null; 	//家庭电话
	private JTextField contactETel = null; 	//其他电话
	private JTextField contactEMail = null; //电子邮件
	
	private JTextArea contactChar = null; 	//性格描述
	private JTextArea contactHobby = null; 	//爱好描述
	private JTextArea contactRemark = null; //备注
	
	private JButton submit = null; 		//确定
	private JButton cancel = null; 		//取消
	private JButton birthChooseButton = null;
	private boolean isEdit = false; 	//是否按下确定键
	private ContactInfo contactInfo = new ContactInfo();	//联系人信息
	
	//窗口默认的宽和高
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGTH = 300;
	
	private int [] allCustomerIDs =null;		//存放所有客户的ID
	private String [] allCustomerNames = null;	//所有客户名称
}