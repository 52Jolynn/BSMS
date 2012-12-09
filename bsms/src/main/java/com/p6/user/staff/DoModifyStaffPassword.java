package com.p6.user.staff;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Personal;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.StaffInfo;

public class DoModifyStaffPassword extends Personal {

	@Override
	public boolean isSucced() {
		// TODO Auto-generated method stub
		return result==0?false:true;
	}

	
	public void process(Info infoBean, String newPwd) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			StaffInfo staffInfo = (StaffInfo)infoBean;
			
			String sql = "update LogInfo set userPassWord= '"+newPwd+"' where staffID="+staffInfo.getStaffID();
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int result = 0;
	
	@Override
	public void process(Info infoBean) {
		// TODO Auto-generated method stub
		
	}
}
