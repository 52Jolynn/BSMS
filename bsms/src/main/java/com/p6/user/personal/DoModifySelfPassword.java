package com.p6.user.personal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Personal;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.StaffInfo;

public class DoModifySelfPassword extends Personal {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	/**
	 * 处理修改个人密码
	 * @param infoBean	个人信息
	 * @param oldPwd	旧密码
	 * @param newPwd	新密码
	 */
	public void process(Info infoBean,String oldPwd, String newPwd) {
		
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			StaffInfo staffInfo = (StaffInfo)infoBean;
			ResultSet rs = null;
			String sql = "select * from LogInfo where staffID = "+staffInfo.getStaffID();
			rs = stmt.executeQuery(sql);
			rs.next();
			String password = rs.getString(2);

			if(password.equals(oldPwd)){
				sql = "update LogInfo set userPassWord= '"+newPwd+"' where staffID="+staffInfo.getStaffID();
				result = stmt.executeUpdate(sql);
			}
				
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
