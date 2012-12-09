package com.p6.user.infoBean;
import java.util.Date;
public class TrackRecordInfo implements Info {
	private int		trackID = 0;		//跟踪记录ID
	private int 	projectID = 0;		//项目ID
	private String projectName = null;	//项目名称
	private short 	projectState = 0;	//项目状态
	private Date 	trackDate = null;		//日期
	private int 	contactID = 0;		//联系人ID
	private String	contactName = null;	//联系人
	private short	trackWay = 0;		//跟踪方式
	private String 	trackContent = "";	//主要内容
	private String 	trackRemark = "";	//备注
	
	public boolean setTrackID(int trackID)
		{this.trackID = trackID; return true;}
	public boolean setProjectID(int projectID)
		{this.projectID = projectID; return true;}
	public boolean setProjectName(String projectName)
		{this.projectName = projectName; return true;}
	public boolean setProjectState(short projectState)
		{this.projectState = projectState; return true;}
	public boolean setTrackDate(Date trackDate)
		{this.trackDate = trackDate; return true;}
	public boolean setContactID(int contactID)
		{this.contactID = contactID; return true;}
	public boolean setContactName(String contactName)
		{this.contactName = contactName; return true;}
	public boolean setTrackWay(short trackWay)
		{this.trackWay = trackWay; return true;}
	public boolean setTrackContent(String trackContent)
		{this.trackContent = trackContent; return true;}
	public boolean setTrackRemark(String trackRemark)
		{this.trackRemark = trackRemark; return true;}
	
	public TrackRecordInfo getInfo(){return this;}
	public int		getTrackID(){return trackID;}			//跟踪记录ID
	public int 		getProjectID(){return projectID;}		//项目ID
	public String	getProjectName(){return projectName;}	//项目名称
	public short 	getProjectState(){return projectState;}	//项目状态
	public Date 	getTrackDate(){return trackDate;}		//日期
	public int 		getContactID(){return contactID;}		//联系人ID
	public String	getContactName(){return contactName;}	//联系人
	public short	getTrackWay(){return trackWay;}			//跟踪方式
	public String 	getTrackContent(){return trackContent;}	//主要内容
	public String 	getTrackRemark(){return trackRemark;}	//备注
}
