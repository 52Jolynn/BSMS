package com.p6.user.trackRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.TrackRecord;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.TrackRecordInfo;

public class DoModifyTrackRecord extends TrackRecord {

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
			
			String sql = "update TrackRecord set " +
				"projectID ='" 		+ trackRecordInfo.getProjectID()+"',"+
				"projectState ='"	+ trackRecordInfo.getProjectState()+"',"+
				"trackDate ='"		+ ParseStringAndDate.date2String(trackRecordInfo.getTrackDate(), "-")+"',"+
				"contactID ='"		+ trackRecordInfo.getContactID()+"',"+
				"trackWay ='" 		+ trackRecordInfo.getTrackWay()+"',"+
				"trackContent ='"		+ trackRecordInfo.getTrackContent()+"',"+
				"trackRemark ='"		+ trackRecordInfo.getTrackRemark()+"' "+
				"where trackID = '"	+ trackRecordInfo.getTrackID()+"'";
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
