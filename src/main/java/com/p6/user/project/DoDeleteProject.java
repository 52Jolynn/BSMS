package com.p6.user.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Project;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.ProjectInfo;

public class DoDeleteProject extends Project {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			ProjectInfo projectInfo= (ProjectInfo)infoBean;
			String sql = "delete from ProjectInfo where projectID = '"+projectInfo.getProjectID()+"'";
			System.out.println(sql);
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result=0;
}
