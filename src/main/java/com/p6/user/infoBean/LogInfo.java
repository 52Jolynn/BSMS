package com.p6.user.infoBean;

public class LogInfo implements Info {

	private String 	userID = "";			//账号
	private String 	userPassWord = "";	//密码
	private int 	staffID = 0;		//销售人员ID
	private String 	staffTitle = "";		//职位
	private int 	userBirthNotifyConfig = 0;	//设置
	
	public boolean setUserID(String userID)						//账号
		{this.userID = userID; return true;}
	public boolean setUserPassWord(String userPassWord)
		{this.userPassWord = userPassWord; return true;}	//密码
	public boolean setStaffID(int staffID)
		{this.staffID = staffID; return true;}				//销售人员ID
	public boolean setStaffTitle(String staffTitle)
		{this.staffTitle = staffTitle; return true;}		//职位
	public boolean setUserBirthNotifyConfig(int userBirthNotifyConfig)
		{this.userBirthNotifyConfig = userBirthNotifyConfig; return true;}	//设置
	
	public LogInfo 	getInfo(){return this;}
	public String 	getUserID(){return userID;};				//账号
	public String 	getUserPassWord(){return userPassWord;};	//密码
	public int 		getStaffID(){return staffID;}				//销售人员ID
	public String 	getStaffTitle(){return staffTitle;}			//职位
	public int 		getUserBirthNotifyConfig(){return userBirthNotifyConfig;}	//设置
}
