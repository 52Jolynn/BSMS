package com.p6.user.contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.DateChooser.ParseStringAndDate;
import com.p6.user.Contact;
import com.p6.user.infoBean.ContactInfo;
import com.p6.user.infoBean.Info;

public class DoAddContact extends Contact {

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
			
			String sql = "insert into contactInfo(customerID,contactName," +
			"contactSex,contactBirth,contactDepart,contactDuty," +
			"contactTitle,contactOTel,contactMTel,contactHTel," +
			"contactETel,contactFax,contactEMail,contactChar," +
			"contactHobby,contactRemark,siftKey)"+
			" values('" +
			contactInfo.getCustomerID()+"','"+
			contactInfo.getContactName()+"','"+
			contactInfo.getContactSex()+"','"+
			ParseStringAndDate.date2String(contactInfo.getContactBirth(),"-")+"','"+	//
			contactInfo.getContactDepart()+"','"+
			contactInfo.getContactDuty()+"','"+
			contactInfo.getContactTitle()+"','"+
			contactInfo.getContactOTel()+"','"+
			contactInfo.getContactMTel()+"','"+
			contactInfo.getContactHTel()+"','"+
			contactInfo.getContactETel()+"','"+
			contactInfo.getContactFax()+"','"+
			contactInfo.getContactEMail()+"','"+
			contactInfo.getContactChar()+"','"+
			contactInfo.getContactHobby()+"','"+
			contactInfo.getContactRemark()+"','"+
			contactInfo.getSiftKey()+"')";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private int result=0; 
}
