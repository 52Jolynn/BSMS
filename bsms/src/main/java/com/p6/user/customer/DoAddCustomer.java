package com.p6.user.customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.p6.toolkit.ConnectionPool;
import com.p6.user.Customer;
import com.p6.user.infoBean.CustomerInfo;
import com.p6.user.infoBean.Info;

public class DoAddCustomer extends Customer {

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
			
			String sql = "insert into customerInfo(" +
					"customerName,customerIndustry," +
					"customerMUnit,customerArea,customerAddress," +
					"customerMailNum,customerWeb,customerProduct," +
					"customerRemark,siftKey)"+ 
					"values('" +
					customerInfo.getCustomerName()+"','"+
					customerInfo.getCustomerIndustry()+"','"+
					customerInfo.getCustomerMUnit()+"','"+
					customerInfo.getCustomerArea()+"','"+
					customerInfo.getCustomerAddress()+"','"+
					customerInfo.getCustomerMailNum()+"','"+
					customerInfo.getCustomerWeb()+"','"+
					customerInfo.getCustomerProduct()+"','"+
					customerInfo.getCustomerRemark()+"','"+
					customerInfo.getSiftKey()+"')";
			
			result = stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	private int result=0;
}
