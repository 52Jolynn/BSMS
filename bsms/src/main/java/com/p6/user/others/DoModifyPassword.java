package com.p6.user.others;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.Ecrypt;
import com.p6.user.Others;
import com.p6.user.infoBean.Info;
import com.p6.user.infoBean.LogInfo;

public class DoModifyPassword extends Others {

	/**
	 * 构造函数
	 */
	public DoModifyPassword() {
		
	}
	
	@Override
	public boolean isSucced() {
		return isSucced;
	}

	@Override
	public void process(Info infoBean) {
		if (infoBean instanceof LogInfo) {
			logInfo = (LogInfo)infoBean;
			String username = logInfo.getUserID();
			logger.debug("UserName: " + username);
			String password = Ecrypt.EcryptByMD5(logInfo.getUserPassWord());
			logger.debug("Password: " + password);
			
			try {
				Connection conn = ConnectionPool.getConnection();
				Statement stmt = conn.createStatement();
				logger.debug("update logInfo set userPassWord='" + password + "\' where userID='" + username + "\'");
				int result = stmt.executeUpdate("update logInfo set userPassWord='" + password + "\' where userID='" + username + "\'");
				if (result == 1) {
					isSucced = true;
				}
			} catch (SQLException e) {
				logger.warn("修改密码失败! - " + e.getMessage() + DoModifyPassword.class.getName());
			}
		}
		
	}
	
	private static Logger logger = Logger.getLogger(DoModifyPassword.class);
	private boolean isSucced = false; //操作是否成功
	private LogInfo logInfo = null; //登陆信息Bean

}
