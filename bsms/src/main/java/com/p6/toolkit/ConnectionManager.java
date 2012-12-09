/**
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.sql.*;

public class ConnectionManager {
	@SuppressWarnings("unused")
	/**
	 * 私有构造函数
	 */
	private ConnectionManager() {
		
	}
	
	/**
	 * 构造函数
	 * @param url 数据库地址
	 * @param user 数据库用户
	 * @param password 用户密码
	 */
	public ConnectionManager(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	/**
	 * 构造函数
	 * @param driver 驱动程序
	 * @param url 数据库地址
	 * @param user　数据库用户
	 * @param password 用户密码
	 */
	public ConnectionManager(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	/**
	 * 获取数据库连接
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (driver == null) {
			driver = DEFAULT_DRIVER;
		}
		if (url == null) {
			url = DEFAULT_URL;
		}

		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	private final String DEFAULT_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private final String DEFAULT_URL = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=master";
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	private Connection conn = null;
}
