package com.p6.user.contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Contact;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.StaffInfo;

public class DoBirthdayNotify extends Contact {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			StaffInfo staffInfo = (StaffInfo)infoBean;
			
			String sql = "update logInfo set userBirthNotifyConfig = " +
						staffInfo.getBirthNotify()+
						" where staffID ='"+staffInfo.getStaffID()+"'";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result = 0;
}
