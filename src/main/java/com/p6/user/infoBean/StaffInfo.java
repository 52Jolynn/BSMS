package com.p6.user.infoBean;
import java.util.Date;
public class StaffInfo implements Info {
	private int 	staffID = 0;		//销售人员ID
	private String 	staffName = "";		//姓名
	private String 	staffSex = "";		//性别
	private Date 	staffBirth = null;		//生日
	private String 	posName = "";		//职位
	private String 	staffQuali = "";		//学历
	private String 	staffOTel = "";		//办公电话
	private String 	staffMTel = "";		//手机
	private String 	staffHTel = "";		//家庭电话
	private String 	staffETel = "";		//其他电话
	private String 	staffEMail = "";		//电子邮件
	private Date 	staffInDate = null;	//入职日期
	private Date 	staffOutDate = null;	//离职日期
	private String 	staffRemark = "";	//备注
	private String 	siftKey = "";		//简易筛选KEY
	
	private int birthdayNofity = 0;
	
	public boolean setStaffID(int staffID)
		{this.staffID = staffID; return true;}
	public boolean setStaffName(String staffName)
		{this.staffName = staffName; return true;}		//姓名
	public boolean setStaffSex(String staffSex)
		{this.staffSex = staffSex; return true;}			//性别
	public boolean setStaffBirth(Date staffBirth)
		{this.staffBirth = staffBirth; return true;}		//生日
	public boolean setPosName(String posName)
		{this.posName = posName; return true;}				//职位
	public boolean setStaffQuali(String staffQuali)
		{this.staffQuali = staffQuali; return true;}	//学历
	public boolean setStaffOTel(String staffOTel)
		{this.staffOTel = staffOTel; return true;}		//办公电话
	public boolean setStaffMTel(String staffMTel)
		{this.staffMTel = staffMTel; return true;}		//手机
	public boolean setStaffHTel(String staffHTel)
		{this.staffHTel = staffHTel; return true;}		//家庭电话
	public boolean setStaffETel(String staffETel)
		{this.staffETel = staffETel; return true;}		//其他电话
	public boolean setStaffEMail(String staffEMail)
		{this.staffEMail = staffEMail; return true;}	//电子邮件
	public boolean setStaffInDate(Date staffInDate)
		{this.staffInDate = staffInDate; return true;}	//入职日期
	public boolean setStaffOutDate(Date staffOutDate)
		{this.staffOutDate = staffOutDate; return true;}	//离职日期
	public boolean setStaffRemark(String staffRemark)
		{this.staffRemark = staffRemark; return true;}	//备注
	public boolean setSiftKey(String siftKey)
		{this.siftKey = siftKey; return true;};				//简易筛选KEY
	
	public boolean setBirthNotify(int birthdayNofity)		//生日提醒
		{this.birthdayNofity = birthdayNofity; return true;}
		
	public StaffInfo getInfo(){return this;}
	public int 		getStaffID(){return staffID;}			//销售人员ID
	public String 	getStaffName(){return staffName;}		//姓名
	public String 	getStaffSex(){return staffSex;}			//性别
	public Date 	getStaffBirth(){return staffBirth;}		//生日
	public String 	getPosName(){return posName;}			//职位
	public String 	getStaffQuali(){return staffQuali;}		//学历
	public String 	getStaffOTel(){return staffOTel;}		//办公电话
	public String 	getStaffMTel(){return staffMTel;}		//手机
	public String 	getStaffHTel(){return staffHTel;}				//家庭电话
	public String 	getStaffETel(){return staffETel;}		//其他电话
	public String 	getStaffEMail(){return staffEMail;}		//电子邮件
	public Date 	getStaffInDate(){return staffInDate;}	//入职日期
	public Date 	getStaffOutDate(){return staffOutDate;}	//离职日期
	public String 	getStaffRemark(){return staffRemark;}	//备注
	public String 	getSiftKey(){return siftKey;}			//简易筛选KEY
	
	public int getBirthNotify(){return birthdayNofity;}		//生日提醒
}
