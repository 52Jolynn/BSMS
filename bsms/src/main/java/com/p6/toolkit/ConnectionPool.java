/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.beans.PropertyVetoException;
import java.sql.*;

import org.apache.log4j.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	/**
	 * 私有构造函数
	 */
	private ConnectionPool() {
	}
	
	private static void init() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(driver);
			cpds.setJdbcUrl(url);
			cpds.setUser(user);
			cpds.setPassword(password);
			cpds.setMaxPoolSize(10);
			cpds.setMinPoolSize(5);
			cpds.setMaxStatements(100);
		} catch (PropertyVetoException e) {
			logger.warn("数据库驱动程序:" + driver + "出现异常! - " + ConnectionPool.class.getName());
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		if (cpds == null) {
			logger.debug("ComboPooledDataSource变量cpds为空,重新建立新的连接池. - " + ConnectionPool.class.getName());
			init();
		}
			
		try {
			conn = cpds.getConnection();
		} catch (SQLException e) {
			logger.warn("数据库连接失败,请检查数据库连接! - " + ConnectionPool.class.getName());
		}
		
		return conn;
	}
	
	public static void release() {
		if (cpds != null) {
			cpds.close();
		}
	}
	
	private static ComboPooledDataSource cpds = null;
	private static final Config config = new Config("config.xml");
	private static final String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private static final String url = "jdbc:microsoft:sqlserver://" + config.getDatabaseURL() + ":" + config.getPort() + ";DatabaseName=" + config.getDatabaseName();
	private static final String user = config.getUsername();
	private static final String password = config.getPassword();
	private static Logger logger = Logger.getLogger(ConnectionPool.class);
}
