package com.p6.user.staff;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Staff;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.LogInfo;
import com.p6.user.infoBean.StaffInfo;

public class DoAddStaff extends Staff{

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	public void process(Info infoBean, LogInfo logInfo) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			StaffInfo staffInfo = (StaffInfo)infoBean;
			
			String sql = "insert into StaffInfo(" +
					"staffName,staffSex," +
					"staffBirth,posName,staffQuali," +
					"staffOTel,staffMTel,staffHTel," +
					"staffETel,staffEMail,staffInDate," +
					"staffOutDate, staffRemark, siftKey)"+
					"values('" +
					staffInfo.getStaffName()+"','"+
					staffInfo.getStaffSex()+"','"+
					ParseStringAndDate.date2String(staffInfo.getStaffBirth(),"-")+"','"+
					staffInfo.getPosName()+"','"+
					staffInfo.getStaffQuali()+"','"+
					staffInfo.getStaffOTel()+"','"+
					staffInfo.getStaffMTel()+"','"+
					staffInfo.getStaffHTel()+"','"+
					staffInfo.getStaffETel()+"','"+
					staffInfo.getStaffEMail()+"','"+
					ParseStringAndDate.date2String(staffInfo.getStaffInDate(),"-")+"','"+
					ParseStringAndDate.date2String(staffInfo.getStaffOutDate(),"-")+"','"+
					staffInfo.getStaffRemark()+"','"+
					staffInfo.getSiftKey()+"');";
			String logSql = "declare @num int;"+
							"select  @num=max(staffID) from staffinfo;"+
							"insert into LogInfo"+
							"(userID,userPassWord,staffID,staffTitle,userBirthNotifyConfig)"+
							"values('" +
							logInfo.getUserID()+"','"+
							logInfo.getUserPassWord()+"',@num,'"+
							logInfo.getStaffTitle()+"',"+
							logInfo.getUserBirthNotifyConfig()+")";
			System.out.println(sql);
			System.out.println(logSql);
			result = stmt.executeUpdate(sql+logSql);
			//stmt.executeUpdate(logSql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void process(Info infoBean){};
	private int result=0;
	
	//staffID,staffName,staffSex,staffBirth,posName,	staffQuali,	staffOTel,	staffMTel,	staffHTel,	staffETel, staffEMail,	staffInDate, staffOutDate, staffRemark, siftKey
}
