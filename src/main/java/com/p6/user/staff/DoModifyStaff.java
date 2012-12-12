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

public class DoModifyStaff extends Staff{

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}


	public void process(Info infoBean,LogInfo logInfo) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			StaffInfo staffInfo = (StaffInfo)infoBean;
			String sql = "update StaffInfo set " +
				"staffName ='"		+ staffInfo.getStaffName()+"',"+
				"staffSex ='" 		+ staffInfo.getStaffSex()+"',"+
				"staffBirth ='"		+ ParseStringAndDate.date2String(staffInfo.getStaffBirth(),"-")+"',"+
				"posName ='"		+ staffInfo.getPosName()+"',"+
				"staffQuali ='"		+ staffInfo.getStaffQuali()+"',"+
				"staffOTel ='" 		+ staffInfo.getStaffOTel()+"',"+
				"staffMTel ='"		+ staffInfo.getStaffMTel()+"',"+
				"staffHTel ='"		+ staffInfo.getStaffHTel()+"',"+
				"staffETel ='"		+ staffInfo.getStaffETel()+"',"+
				"staffEMail ='"		+ staffInfo.getStaffEMail()+"',"+
				"staffInDate ='"	+ ParseStringAndDate.date2String(staffInfo.getStaffInDate(),"-")+"',"+
				"staffOutDate ='"	+ ParseStringAndDate.date2String(staffInfo.getStaffOutDate(),"-")+"',"+
				"staffRemark ='"	+ staffInfo.getStaffRemark()+"',"+
				"siftKey ='"		+ staffInfo.getSiftKey()+"'"+
				"where staffID = '"	+ staffInfo.getStaffID()+"';";
			String logSql = "update logInfo set "+
							"userID = '"+ logInfo.getUserID()+"',"+
							"staffTitle = '"+logInfo.getStaffTitle()+"' "+
							"where staffID = "+logInfo.getStaffID();
			System.out.println(sql+"\n"+logSql);
			
			result = stmt.executeUpdate(sql+logSql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void process(Info infoBean){};
	private int result=0;
	
}
