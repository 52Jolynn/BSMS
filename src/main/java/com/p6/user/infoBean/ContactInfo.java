package com.p6.user.infoBean;
import java.util.Date;
public class ContactInfo implements Info {
	
	private int		contactID = 0;		//联系人ID
	private int 	customerID = 0;		//客户ID
	private String  customerName = null; //客户名称
	private String 	contactName = "";	//姓名
	private String 	contactSex = "";		//性别
	private Date 	contactBirth = null;	//生日
	private String 	contactDepart = "";	//部门
	private String 	contactDuty = "";	//职务
	private String 	contactTitle = "";	//职称
	private String 	contactOTel = "";	//办公电话
	private String 	contactMTel = "";	//手机
	private String 	contactHTel = "";	//家庭电话
	private String 	contactETel = "";	//其他电话
	private String 	contactFax = "";		//传真
	private String 	contactEMail = "";	//电子邮件
	private String 	contactChar = "";	//性格描述
	private String 	contactHobby = "";	//爱好描述
	private String 	contactRemark = "";	//备注
	private String 	siftKey = "";		//简易筛选KEY
	

	public boolean	setContactID(int contactID)
		{this.contactID = contactID; return true;}			//联系人ID
	public boolean setCustomerID(int customerID)
		{this.customerID = customerID; return true;}		//客户ID
	public boolean setCustomerName(String customerName)
		{this.customerName = customerName; return true;}	//客户名称
	public boolean setContactName(String contactName)
		{this.contactName = contactName; return true;}		//姓名
	public boolean setContactSex(String contactSex)
		{this.contactSex = contactSex; return true;}		//性别
	public boolean setContactBirth(Date contactBirth)
		{this.contactBirth = contactBirth; return true;}	//生日
	public boolean setContactDepart(String contactDepart)
		{this.contactDepart = contactDepart; return true;}	//部门
	public boolean setContactDuty(String contactDuty)
		{this.contactDuty = contactDuty; return true;}		//职务
	public boolean setContactTitle(String contactTitle)
		{this.contactTitle = contactTitle; return true;}	//职称
	public boolean setContactOTel(String contactOTel)
		{this.contactOTel = contactOTel; return true;}		//办公电话
	public boolean setContactMTel(String contactMTel)
		{this.contactMTel = contactMTel; return true;}		//手机
	public boolean setContactHTel(String contactHTel)
		{this.contactHTel = contactHTel; return true;}		//家庭电话
	public boolean setContactETel(String contactETel)
		{this.contactETel = contactETel; return true;}		//其他电话
	public boolean setContactFax(String contactFax)
		{this.contactFax = contactFax; return true;}		//传真
	public boolean setContactEMail(String contactEMail)
		{this.contactEMail = contactEMail; return true;}	//电子邮件
	public boolean setContactChar(String contactChar)
		{this.contactChar = contactChar; return true;}		//性格描述
	public boolean setContactHobby(String contactHobby)
		{this.contactHobby = contactHobby; return true;}	//爱好描述
	public boolean setContactRemark(String contactRemark)
		{this.contactRemark = contactRemark; return true;}	//备注
	public boolean setSiftKey(String siftKey)
		{this.siftKey = siftKey; return true;}				//简易筛选KEY
	
	public ContactInfo getInfo(){return this;}
	public int 		getContactID()	{return contactID;}
	public int 		getCustomerID()	{return customerID;}		//客户ID
	public String	getCustoemrName() {return customerName;}	//客户名称
	public String 	getContactName(){return contactName;}		//姓名
	public String 	getContactSex()	{return contactSex;}		//性别
	public Date 	getContactBirth(){return contactBirth;}		//生日
	public String 	getContactDepart(){return contactDepart;}	//部门
	public String 	getContactDuty(){return contactDuty;}		//职务
	public String 	getContactTitle(){return contactTitle;}		//职称
	public String 	getContactOTel(){return contactOTel;}		//办公电话
	public String 	getContactMTel(){return contactMTel;}		//手机
	public String 	getContactHTel(){return contactHTel;}		//家庭电话
	public String 	getContactETel(){return contactETel;}		//其他电话
	public String 	getContactFax(){return contactFax;}			//传真
	public String 	getContactEMail(){return contactEMail;}		//电子邮件
	public String 	getContactChar(){return contactChar;}		//性格描述
	public String 	getContactHobby(){return contactHobby;}		//爱好描述
	public String 	getContactRemark(){return contactRemark;}	//备注
	public String 	getSiftKey(){return siftKey;}				//简易筛选KEY
}
