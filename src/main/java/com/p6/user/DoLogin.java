package com.p6.user;

import java.sql.*;

import org.apache.log4j.Logger;

import com.p6.toolkit.ConnectionPool;
import com.p6.toolkit.Ecrypt;
import com.p6.toolkit.GetInfo;
import com.p6.user.infoBean.*;

public class DoLogin extends DoHandler {

	@Override
	public boolean isSucced() {
		return isSucced;
	}

	@Override
	public void process(Info infoBean) {
		if (infoBean instanceof LogInfo) {
			logInfo = (LogInfo)infoBean;
			String username = logInfo.getUserID();
			String password = Ecrypt.EcryptByMD5(logInfo.getUserPassWord());
			Connection conn = ConnectionPool.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select userPassWord, staffID, staffTitle, userBirthNotifyConfig from logInfo where userID='" + username + "\'");

				while (rs.next()) {
					if (rs.getString(1).equals(password)) {
						isSucced = true;
					}
					logInfo.setStaffID(rs.getInt(2));
					logInfo.setStaffTitle(rs.getString(3));
					logInfo.setUserBirthNotifyConfig(rs.getInt(4));
				}
			} catch (SQLException e) {
				logger.warn("数据库连接失败, 请检查网络!" + e.getMessage() + "-" + DoLogin.class.getName());
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						logger.error("关闭ResultSet失败! - " + GetInfo.class.getName());
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
					}
				}
			}
		}
	}

	private static Logger logger = Logger.getLogger(DoLogin.class);
	private boolean isSucced = false;
	private LogInfo logInfo = null;
}
