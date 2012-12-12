package com.p6.user.customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Customer;
import com.p6.user.infoBean.CustomerInfo;
import com.p6.user.infoBean.Info;

public class DoModifyCustomer extends Customer {

	@Override
	public boolean isSucced() {
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		try {
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();
			CustomerInfo customerInfo = (CustomerInfo)infoBean;
			
			String sql = "update customerInfo set " +
				"customerName ='"		+customerInfo.getCustomerName()+"',"+
				"customerIndustry ='" 	+customerInfo.getCustomerIndustry()+"',"+
				"customerMUnit ='"		+customerInfo.getCustomerMUnit()+"',"+
				"customerArea ='"		+customerInfo.getCustomerArea()+"',"+
				"customerAddress ='"	+customerInfo.getCustomerAddress()+"',"+
				"customerMailNum ='" 	+customerInfo.getCustomerMailNum()+"',"+
				"customerWeb ='"		+customerInfo.getCustomerWeb()+"',"+
				"customerProduct ='"	+customerInfo.getCustomerProduct()+"',"+
				"customerRemark ='"		+customerInfo.getCustomerRemark()+"',"+
				"siftKey ='"			+customerInfo.getSiftKey()+"'"+
				"where customerID = '"	+customerInfo.getCustomerID()+"'";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int result=0;

}
