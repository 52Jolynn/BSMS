package com.p6.user.trackRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.TrackRecord;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.TrackRecordInfo;

public class DoAddTrackRecord extends TrackRecord {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			TrackRecordInfo trackRecordInfo = (TrackRecordInfo)infoBean;
			
			String sql = "insert into TrackRecord (projectID,projectState,trackDate,contactID,trackWay,trackContent,trackRemark)"+
					" values('" +
					trackRecordInfo.getProjectID()+"','"+
					trackRecordInfo.getProjectState()+"','"+
					ParseStringAndDate.date2String(trackRecordInfo.getTrackDate(),"-")+"','"+
					trackRecordInfo.getContactID()+"','"+
					trackRecordInfo.getTrackWay()+"','"+
					trackRecordInfo.getTrackContent()+"','"+
					trackRecordInfo.getTrackRemark()+"')";

			System.out.println(sql);
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//trackID,projectID,projectName,projectState,	trackDate,contactID,contactName,trackWay,trackContent,trackRemark 
	}
	private int result=0;
}
