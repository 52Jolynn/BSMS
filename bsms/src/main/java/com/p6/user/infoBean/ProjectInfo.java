package com.p6.user.infoBean;
import java.util.Date;
public class ProjectInfo implements Info {

	private int		projectID = 0;			//项目ID
	private String 	projectName = "";		//项目名称
	private int 	customerID = 0;			//客户ID
	private String customerName = null;		//客户名称
	private int 	contactID = 0;			//联系人ID
	private String contactName = null;		//联系人名称
	private int		staffID = 0;			//销售人员ID
	private String staffName = null;		//销售人员
	private int		projectInterval = 0;	//联系频度
	private int 	projectNotify = 0;		//项目提醒
	private short 	projectState = 0;		//项目状态
	private String 	projectContent = "";		//项目内容描述
	private Date 	projectCreateDate = null;	//创建日期
	private Date 	projectSignDate = null;	//签约日期
	private Date	projectEndDate  =null;		//终止日期
	private String 	projectExp = "";			//经验教训
	private String 	siftKey = "";			//简易筛选KEY

	public boolean setProjectID(int projectID)
		{this.projectID = projectID; return true;}
	public boolean setProjectName(String projectName)
		{this.projectName = projectName; return true;}
	public boolean setCustomerID(int customerID)
		{this.customerID = customerID; return true;}
	public boolean setCustomerName(String customerName)
		{this.contactName = customerName; return true;}
	public boolean setContactID(int contactID)
		{this.contactID=contactID; return true;}
	public boolean setContactName(String contactName)
		{this.contactName = contactName; return true;}
	public boolean setStaffID(int staffID)
		{this.staffID = staffID; return true;}
	public boolean setStaffName(String staffName)
		{this.staffName = staffName; return true;}
	public boolean setProjectInterval(int projectInterval)
		{this.projectInterval = projectInterval; return true;}
	public boolean setProjectNotify(int projectNotify)
		{this.projectNotify = projectNotify; return true;}
	public boolean setProjectState(short projectState)
		{this.projectState = projectState; return true;}
	public boolean setProjectContent(String projectContent)
		{this.projectContent = projectContent; return true;}
	public boolean setProjectCreateDate(Date projectCreateDate)
		{this.projectCreateDate = projectCreateDate; return true;}
	public boolean setProjectSignDate(Date projectSignDate)
		{this.projectSignDate = projectSignDate; return true;}
	public boolean setProjectEndDate(Date projectEndDate)
		{this.projectEndDate = projectEndDate; return true;}
	public boolean setProjectExp(String projectExp)
		{this.projectExp = projectExp; return true;}
	public boolean setSiftKey(String siftKey)
		{this.siftKey = siftKey; return true;}
	
	public ProjectInfo getInfo(){return this;}
	public int		getProjectID(){return projectID;}			//项目ID
	public String 	getProjectName(){return projectName;}		//项目名称
	public int 		getCustomerID(){return customerID;}			//客户ID
	public String	getCustomername(){return customerName;}		//客户名称
	public int 		getContactID(){return contactID;}			//联系人ID
	public String	getContactName(){return contactName;}		//联系人姓名
	public int		getStaffID(){return staffID;}				//销售人员ID
	public String	getStaffName(){return staffName;}			//销售人员
	public int		getProjectInterval(){return projectInterval;}	//联系频度
	public int 		getProjectNotify(){return projectNotify;}		//项目提醒
	public short 	getProjectState(){return projectState;}			//项目状态
	public String 	getProjectContent(){return projectContent;}		//项目内容描述
	public Date 	getProjectCreateDate(){return projectCreateDate;}	//创建日期
	public Date 	getProjectSignDate(){return projectSignDate;}	//签约日期
	public Date		getProjectEndDate(){return projectEndDate;}		//终止日期
	public String 	getProjectExp(){return projectExp;}			//经验教训
	public String 	getSiftKey(){return siftKey;}				//简易筛选KEY
	
	public static String [] projectStateString = new String[]{"","跟踪","方案","竞标","谈判","签约","中止"};
}
