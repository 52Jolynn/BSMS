/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix 
 */
package com.p6.toolkit;

import java.util.Vector;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.infoBean.*;

public class SetInfoBean {

	/**
	 * 私有构造函数
	 */
	private SetInfoBean() {
		
	}
	
	/**
	 * 设置staffInfo
	 * @param staffInfo 销售人员信息
	 */
	public static void setStaff(StaffInfo staffInfo, Vector<String> vector) {
		staffInfo.setStaffID(Integer.parseInt(vector.get(0)));
		staffInfo.setStaffName(vector.get(1));
		staffInfo.setStaffSex(vector.get(2));
		String date = vector.get(3);
		if (date != null) {
			staffInfo.setStaffBirth(ParseStringAndDate.parseString2Date(date, "-"));
		}
		staffInfo.setPosName(vector.get(4));
		staffInfo.setStaffOTel(vector.get(5));
		date = vector.get(6);
		if (date != null) {
			staffInfo.setStaffInDate(ParseStringAndDate.parseString2Date(date, "-"));
		}
		date = vector.get(7);
		if (date != null) {
			staffInfo.setStaffOutDate(ParseStringAndDate.parseString2Date(date, "-"));
		}
		
	}
	
	/**
	 * 设置customerInfo
	 * @param customerInfo 客户信息
	 */
	public static void setCustomer(CustomerInfo customerInfo, Vector<String> vector) {
		customerInfo.setCustomerID(Integer.parseInt(vector.get(0)));
		customerInfo.setCustomerName(vector.get(1));
		customerInfo.setCustomerIndustry(vector.get(2));
		customerInfo.setCustomerMUnit(vector.get(3));
		customerInfo.setCustomerArea(vector.get(4));
		customerInfo.setCustomerAddress(vector.get(5));
		customerInfo.setCustomerMailNum(vector.get(6));
	}
	
	/**
	 * 设置contactInfo
	 * @param contactInfo 联系人信息
	 */
	public static void setContact(ContactInfo contactInfo, Vector<String> vector) {
		contactInfo.setContactID(Integer.parseInt(vector.get(0)));
		contactInfo.setContactName(vector.get(1));
		contactInfo.setCustomerName(vector.get(2));
		contactInfo.setContactSex(vector.get(3));
		String date = vector.get(4);
		if (date != null) {
			contactInfo.setContactBirth(ParseStringAndDate.parseString2Date(date, "-"));
		}
		contactInfo.setContactDepart(vector.get(5));
		contactInfo.setContactDuty(vector.get(6));
		contactInfo.setContactOTel(vector.get(7));
	}
	
	/**
	 * 设置projectInfo
	 * @param projectInfo 项目信息
	 */
	public static void setProject(ProjectInfo projectInfo, Vector<String> vector) {
		projectInfo.setProjectID(Integer.parseInt(vector.get(0)));
		projectInfo.setProjectName(vector.get(1));
		projectInfo.setCustomerName(vector.get(2));
		projectInfo.setContactName(vector.get(3));
		projectInfo.setStaffName(vector.get(4));
		projectInfo.setProjectInterval(Integer.parseInt(vector.get(5)));
		
		short i = 0;	//xiaogy
		for(;i<6;i++)
			if(vector.get(6).equals(GetInfo.PROJECTSTATE[i]))
				break;
		projectInfo.setProjectState((short)(i+1));	//项目状态为1～6
		
		String date = vector.get(7);
		if (date != null) {
			projectInfo.setProjectCreateDate(ParseStringAndDate.parseString2Date(date, "-"));	
		}
	}
	
	/**
	 * 设置trackRecordInfo
	 * @param trackRecordInfo 跟踪记录信息
	 */
	public static void setTrackRecord(TrackRecordInfo trackRecordInfo, Vector<String> vector) {
		trackRecordInfo.setTrackID(Integer.parseInt(vector.get(0)));
		trackRecordInfo.setProjectName(vector.get(1));
		
		int i = 0;
		for(; i < 6; i++)
			if(vector.get(2).equals(GetInfo.PROJECTSTATE[i]))
				break;	
		trackRecordInfo.setProjectState((short)(i + 1)); //项目状态为1～6
		String date = vector.get(3);
		if (date != null) {
			trackRecordInfo.setTrackDate(ParseStringAndDate.parseString2Date(date, "-"));
		}
		trackRecordInfo.setContactName(vector.get(4));
		for(i = 0; i < 3; i++)
			if(vector.get(3).equals(GetInfo.TRACKWAY[i]))
				break;
		trackRecordInfo.setTrackWay((short)(i + 1));	
	}
}
