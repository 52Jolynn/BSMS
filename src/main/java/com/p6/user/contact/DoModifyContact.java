package com.p6.user.contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Contact;
import com.p6.user.infoBean.ContactInfo;
import com.p6.user.infoBean.Info;

public class DoModifyContact extends Contact {

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
			

			String sql = "update contactInfo set " +
				"customerID ='"		+contactInfo.getCustomerID()+"',"+
				"contactName ='" 	+contactInfo.getContactName()+"',"+
				"contactSex ='"		+contactInfo.getContactSex()+"',"+
				"contactBirth ='"	+ParseStringAndDate.date2String(contactInfo.getContactBirth(),"-")+"',"+
				"contactDepart ='"	+contactInfo.getContactDepart()+"',"+
				"contactDuty ='" 	+contactInfo.getContactDuty()+"',"+
				"contactTitle ='"	+contactInfo.getContactTitle()+"',"+
				"contactOTel ='"	+contactInfo.getContactOTel()+"',"+
				"contactMTel ='"	+contactInfo.getContactMTel()+"',"+
				"contactHTel ='"	+contactInfo.getContactHTel()+"',"+
				"contactETel ='"	+contactInfo.getContactETel()+"',"+
				"contactFax ='"		+contactInfo.getContactFax()+"',"+
				"contactEMail ='"	+contactInfo.getContactEMail()+"',"+
				"contactChar ='"	+contactInfo.getContactChar()+"',"+
				"contactHobby ='"	+contactInfo.getContactHobby()+"',"+
				"contactRemark ='"	+contactInfo.getContactRemark()+"',"+
				"siftKey ='"		+contactInfo.getSiftKey()+"'"+
				"where contactID = '"	+contactInfo.getContactID()+"'";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result=0;
}
