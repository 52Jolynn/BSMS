package com.p6.user.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Project;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.ProjectInfo;

public class DoModifyProject extends Project {

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
			String sql = "update ProjectInfo set " +
				"projectName ='"		+projectInfo.getProjectName()+"',"+
				"customerID ='" 		+projectInfo.getCustomerID()+"',"+
				"contactID ='"			+projectInfo.getContactID()+"',"+
				"staffID ='"			+projectInfo.getStaffID()+"',"+
				"projectInterval ='"	+projectInfo.getProjectInterval()+"',"+
				"projectNotify ='" 		+projectInfo.getProjectNotify()+"',"+
				"projectState ='"		+projectInfo.getProjectState()+"',"+
				"projectContent ='"		+projectInfo.getProjectContent()+"',"+
				"projectCreateDate ='"	+ParseStringAndDate.date2String(projectInfo.getProjectCreateDate(),"-")+"',"+
				"projectSignDate ='"	+ParseStringAndDate.date2String(projectInfo.getProjectSignDate(),"-")+"',"+
				"projectEndDate ='"		+ParseStringAndDate.date2String(projectInfo.getProjectEndDate(),"-")+"',"+
				"projectExp ='"			+projectInfo.getProjectExp()+"',"+
				"siftKey ='"			+projectInfo.getSiftKey()+"'"+
				"where projectID = '"	+projectInfo.getProjectID()+"'";
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
