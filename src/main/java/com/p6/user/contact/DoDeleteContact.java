package com.p6.user.contact;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Contact;
import com.p6.user.infoBean.ContactInfo;
import com.p6.user.infoBean.Info;

public class DoDeleteContact extends Contact {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			ContactInfo contactInfo = (ContactInfo)infoBean;
			
			String sql = "delete from contactInfo where contactID = '"	+contactInfo.getContactID()+"'";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result =0;
}
