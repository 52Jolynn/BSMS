package com.p6.user.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Project;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.ProjectInfo;

public class DoAddProject extends Project {

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
			
			String sql = "insert into ProjectInfo(" +
					"projectName,customerID," +
					"contactID,staffID,projectInterval," +
					"projectNotify,projectState,projectContent," +
					"projectCreateDate,projectSignDate,projectEndDate," +
					"projectExp,siftKey)"+
					" values('" +
					projectInfo.getProjectName()+"','"+
					projectInfo.getCustomerID()+"','"+
					projectInfo.getContactID()+"','"+
					projectInfo.getStaffID()+"','"+
					projectInfo.getProjectInterval()+"','"+
					projectInfo.getProjectNotify()+"','"+
					projectInfo.getProjectState()+"','"+
					projectInfo.getProjectContent()+"','"+
					ParseStringAndDate.date2String(projectInfo.getProjectCreateDate(),"-")+"','"+
					ParseStringAndDate.date2String(projectInfo.getProjectSignDate(),"-")+"','"+
					ParseStringAndDate.date2String(projectInfo.getProjectEndDate(),"-")+"','"+
					projectInfo.getProjectExp()+"','"+
					projectInfo.getSiftKey()+"')";
			System.out.println(sql);
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result=0;
	//projectID,projectName,customerID,contactID,staffID,projectInterval,projectNotify,projectState,projectContent,projectCreateDate,projectSignDate,projectEndDate,projectExp,siftKey
}
