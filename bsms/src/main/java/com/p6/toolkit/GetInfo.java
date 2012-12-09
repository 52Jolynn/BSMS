/**
 * @author Laud and xiaogy
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.toolkit;

import java.sql.*;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.p6.user.infoBean.*;

public class GetInfo {

	/**
	 * 私有构造函数
	 */
	private GetInfo() {

	}

	/**
	 * 得到对应帐户的销售人员ID
	 * @param accountID 帐户ID
	 * @return String 销售人员ID
	 */
	public static int getStaffID(String accountID) {
		int result= 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select staffID from logInfo where accountID=" + accountID;
			conn =ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			result = rs.getInt(1);

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return result;
	}

	/**
	 * 根据帐号得到帐号信息
	 * @param accountID 帐号
	 * @return LogInfo 帐号InfoBean(JavaBean)
	 */
	public static LogInfo getLogInfo(String accountID) {
		LogInfo result = new LogInfo();
		String sql = "select * from logInfo where userID='" + accountID + "\'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			result.setUserID(rs.getString(1));
			result.setUserPassWord(rs.getString(2));
			result.setStaffID(rs.getInt(3));
			result.setStaffTitle(rs.getString(4));
			result.setUserBirthNotifyConfig(rs.getInt(5));

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
		return result;
	}
	/**
	 * 根据销售人员ID得到帐号信息
	 * @param staffID 帐号
	 * @return LogInfo 帐号InfoBean(JavaBean)
	 */
	public static LogInfo getLogInfoByStaffID(int staffID) {
		LogInfo result = new LogInfo();
		String sql = "select * from logInfo where staffID=" + staffID + "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			result.setUserID(rs.getString(1));
			result.setUserPassWord(rs.getString(2));
			result.setStaffID(rs.getInt(3));
			result.setStaffTitle(rs.getString(4));
			result.setUserBirthNotifyConfig(rs.getInt(5));

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
		return result;
	}
	/**
	 * 根据职位得到所有帐号信息
	 * @param position 职位
	 * @return LogInfo 帐号InfoBean(JavaBean)
	 */
	public static Vector<Vector<String>> getLogInfoByPosition(String position) {
		String sql = "select staffName,posName,staffID from loginView where posName='" + position +"'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Vector<Vector<String>> result = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);			
			
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 1; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				result.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
		return result;
	}
	
	
	/**
	 * 得到根据销售人员ID得到生日提醒信息
	 * @param staffID 销售人员ID
	 * @return String 生日提醒信息
	 */
	public static String getBirthNotifyByStaffID(int staffID) {
		String result= "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select userBirthNotifyConfig from logInfo where staffID=" + staffID;
			conn =ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			result = Integer.toString(rs.getInt(1));

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return result;
	}

	/**
	 * 得到销售人员信息
	 * @param staffID 销售人员ID
	 * @return StaffInfo 销售人员InfoBean(JavaBean)
	 */
	public static StaffInfo getStaffInfo(int staffID) {
		StaffInfo result = new StaffInfo();
		String sql = "select * from staffInfo where staffID=" + staffID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			result.setStaffID(rs.getInt(1));
			result.setStaffName(rs.getString(2));
			result.setStaffSex(rs.getString(3));
			result.setStaffBirth(rs.getDate(4));
			result.setPosName(rs.getString(5));
			result.setStaffQuali(rs.getString(6));
			result.setStaffOTel(rs.getString(7));
			result.setStaffMTel(rs.getString(8));
			result.setStaffHTel(rs.getString(9));
			result.setStaffETel(rs.getString(10));
			result.setStaffEMail(rs.getString(11));
			result.setStaffInDate(rs.getDate(12));
			result.setStaffOutDate(rs.getDate(13));
			result.setStaffRemark(rs.getString(14));
			result.setSiftKey(rs.getString(15));

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
		return result;
	}

	/**
	 * 得到指定职位的销售人员信息
	 * @param position	职位的名称 (总经理，部门经理，销售经理，销售助理)
	 * @return Vector<Vector<String>> 销售人员信息
	 */
	public static Vector<Vector<String>> getStaffInfoByPosition(String position) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from staffInfo where posName = '" + position + "'";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}

	/**
	 * 得到指定销售人员的数据库列名
	 * @return Vector<String> 销售人员列名数组
	 */
	public static Vector<String> getStaffTitle() {
		Vector<String> columns = new Vector<String>();

		columns.add("ID");
		columns.add("姓名");
		columns.add("性别");
		columns.add("生日");
		columns.add("职位");
		columns.add("学历");
		columns.add("办公电话");
		columns.add("手机");
		columns.add("家庭电话");
		columns.add("其他电话");
		columns.add("电子邮件");
		columns.add("入职日期");
		columns.add("离职日期");
		columns.add("备注");
		columns.add("简易筛选");

		return columns;
	}

	/**
	 * 得到指定销售人员的所有信息
	 * @return Vector<Vector<String>> 所有销售人员信息二维数组
	 */
	public static Vector<Vector<String>> getStaffInfo() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select staffID, staffName, staffSex, convert(varchar(10), staffBirth, 23) as staffBirth, " +
		"posName, staffQuali, staffOTel, staffMTel, staffHTel, staffETel, staffEMail, " +
		"convert(varchar(10), staffInDate, 23) as staffInDate, convert(varchar(10), staffOutDate, 23) as staffOutDate, " +
		"staffRemark, siftKey from staffInfo";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}


	/**
	 * 得到客户的数据库列名
	 * @return Vector<String> 客户列名数组
	 */
	public static Vector<String> getCustomerTitle() {
		Vector<String> columns = new Vector<String>();

		columns.add("ID");
		columns.add("客户名称");
		columns.add("所属行业");
		columns.add("主管单位");
		columns.add("所在地区");
		columns.add("地址");
		columns.add("邮编");
		columns.add("网站");
		columns.add("适用产品");
		columns.add("备注");
		columns.add("简易筛选");

		return columns;
	}

	/**
	 * 得到所有客户信息
	 * @return Vector<Vector<String>> 所有客户信息的二维数组
	 */
	public static Vector<Vector<String>> getAllCustomer() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from customerInfo";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}

	/**
	 * 得到所有客户信息
	 * @return Vector<Vector<String>> 所有客户信息的二维数组
	 */
	public static Vector<Vector<String>> getCustomerByID(int ID) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from customerInfo where customerID ='"+ID+"'";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}

	/**
	 * 得到联系人的数据库列名
	 * @return Vector<String> 联系人列名数组
	 */
	public static Vector<String> getContactTitle() {
		Vector<String> columns = new Vector<String>();

		columns.add("ID");
		columns.add("联系人");
		columns.add("客户");
		columns.add("客户ID");
		columns.add("性别");
		columns.add("生日");
		columns.add("部门");
		columns.add("职位");
		columns.add("职称");
		columns.add("办公电话");
		columns.add("手机");
		columns.add("家庭电话");
		columns.add("其他电话");
		columns.add("传真");
		columns.add("电子邮件");
		columns.add("性格描述");
		columns.add("爱好描述");
		columns.add("备注");
		columns.add("简易筛选");

		return columns;
	}

	/**
	 * 得到所有联系人信息
	 * @return Vector<Vector<String>> 所有联系人信息的二维数组
	 */
	public static Vector<Vector<String>> getAllContact() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select contactID, contactName, customerName, customerID, contactSex, convert(varchar(10), contactBirth, 23) as contactBirth, " +
		"contactDepart, contactDuty, contactOTel, contactMTel, contactHTel, contactETel, contactFax, contactEMail, " +
		"contactChar, contactHobby, contactRemark, siftKey from contactView";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i)
					vc.add(rs.getString(i));
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭ResultSet失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
					logger.error("关闭Connection失败!" + e.getMessage() + " - " + GetInfo.class.getName());
				}
			}

		}

		return data;
	}

	/**
	 * 得到指定ID的联系人信息
	 * @return Vector<Vector<String>> 得到指定ID的联系人信息的二维数组
	 */
	public static Vector<Vector<String>> getContactByID(int ID) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select contactID, contactName, customerName, customerID, contactSex, convert(varchar(10), contactBirth, 23) as contactBirth, " +//0~5
		"contactDepart, contactDuty, contactOTel, contactMTel, contactHTel, contactETel, contactFax, contactEMail, " +//6~13
		"contactChar, contactHobby, contactRemark, siftKey, contactTitle from contactView where contactID='"+ID+"'";//14 ~18
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i)
					vc.add(rs.getString(i));
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错." + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭ResultSet失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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
					logger.error("关闭Connection失败!" + e.getMessage() + " - " + GetInfo.class.getName());
				}
			}

		}

		return data;
	}

	/**
	 * 得到项目的数据库列名
	 * @return Vector<String> 项目列名数组
	 */
	public static Vector<String> getProjectTitle() {
		Vector<String> columns = new Vector<String>();

		columns.add("ID");
		columns.add("项目名称");
		columns.add("客户");
		columns.add("联系人");
		columns.add("负责人");
		columns.add("联系频度");
		columns.add("项目提醒");
		columns.add("项目状态");
		columns.add("项目内容描述");
		columns.add("创建日期");
		columns.add("签约日期");
		columns.add("终止日期");
		columns.add("经验教训");
		columns.add("简易筛选");


		return columns;
	}

	/**
	 * 得到所有项目信息
	 * @return Vector<Vector<String>> 所有项目信息的二维数组
	 */
	public static Vector<Vector<String>> getAllProject() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select projectID, projectName, customerName, contactID, contactName, staffName, projectInterval, " +
		"projectNotify, projectState, projectContent, convert(varchar(10), projectCreateDate, 23) as projectCreateDate, " +
		"convert(varchar(10), projectSignDate, 23) as projectSignDate, convert(varchar(10), projectEndDate, 23) as projectEndDate," +
		"projectExp, siftKey from projectView";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					if (i == 9) {
						vc.add(PROJECTSTATE[rs.getInt(9) -  1]);
						continue;
					}
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}


	/**
	 * 得到指定ID的项目信息
	 * @return Vector<Vector<String>> 所有项目信息的二维数组
	 */
	public static Vector<Vector<String>> getProjectByID(int ID) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select projectID, projectName, customerName, contactName, staffName, projectInterval, " + //0~5
		"projectNotify, projectState, projectContent, convert(varchar(10), projectCreateDate, 23) as projectCreateDate, " + //6~9
		"convert(varchar(10), projectSignDate, 23) as projectSignDate, convert(varchar(10), projectEndDate, 23) as projectEndDate," + //10~11
		"projectExp, siftKey from projectView where projectID = '"+ID+"'";
		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					if (i == 8) {
						vc.add(PROJECTSTATE[rs.getInt(8) -  1]);
						continue;
					}
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}

	/**
	 * 得到跟踪记录的数据库列名
	 * @return Vector<String> 跟踪记录列名数组
	 */
	public static Vector<String> getTrackRecordTitle() {
		Vector<String> columns = new Vector<String>();

		columns.add("ID");
		columns.add("项目名称");
		columns.add("项目状态");
		columns.add("日期");
		columns.add("联系人");
		columns.add("跟踪方式");
		columns.add("主要内容");
		columns.add("备注");

		return columns;
	}

	/**
	 * 得到所有跟踪记录信息
	 * @return Vector<Vector<String>> 所有跟踪记录信息的二维数组
	 */
	public static Vector<Vector<String>> getAllTrackRecord() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select trackID, projectName, " +
				"projectState, convert(varchar(10), trackDate, 23) as trackDate, " +
				"contactName, trackWay, trackContent, trackRemark from trackRecordView";

		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					if (i == 3) {
						vc.add(PROJECTSTATE[rs.getInt(3) -  1]);
						continue;
					}
					if (i == 6) {
						vc.add(TRACKWAY[rs.getInt(6) - 1]);
						continue;
					}
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}
	
	/**
	 * 根据ID得到跟踪记录信息
	 * @return Vector<Vector<String>> 所有跟踪记录信息的二维数组
	 */
	public static Vector<Vector<String>> getAllTrackRecordByID(int trackID) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select trackID, projectName, projectState, convert(varchar(10), trackDate, 23) as trackDate, " +
				"contactName, trackWay, trackContent, trackRemark, projectID from trackRecordView where " +
				"trackID =" + trackID;

		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					if (i == 3) {
						vc.add(PROJECTSTATE[rs.getInt(3) -  1]);
						continue;
					}
					if (i == 6) {
						vc.add(TRACKWAY[rs.getInt(6) - 1]);
						continue;
					}
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}
	
	/**
	 * 得到客户、销售人员关联系信息表
	 */
	public static Vector<Vector<String>> getCustomerRelateStaff() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select customerID, customerName, customerIndustry, customerMUnit, customerArea, customerAddress, " +
				"customerMailNum, customerWeb, customerProduct, customerRemark, customerSiftKey, staffName from projectView";

		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}
	
	/**
	 * 得到联系人、销售人员关联信息表
	 */
	public static Vector<Vector<String>> getContactRelateStaff() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select contactID, contactName, contactSex, convert(varchar(10), contactBirth, 23) as " +
				"contactBirth, contactDepart, contactDuty, contactTitle, contactOTel, contactMTel, contactHTel, " +
				"contactETel, contactFax, contactEMail, contactChar, contactHobby, contactRemark, contactSiftKey, " +
				"staffName from projectView";

		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}
	
	/**
	 * 得到跟踪记录、销售人员关联表
	 */
	public static Vector<Vector<String>> getTRRelativeStaff() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select trackID, projectName, projectState, convert(varchar(10), trackDate, 23) as trackDate, contactName, " +
				"trackWay, trackContent, trackRemark, staffName from trackRecordView";

		try {
			conn = ConnectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			while (rs.next()) {
				Vector<String> vc = new Vector<String>(columns);
				vc.add(Integer.toString(rs.getInt(1)));
				for (int i = 2; i <= columns; ++i) {
					if (i == 3) {
						vc.add(PROJECTSTATE[rs.getInt(3) -  1]);
						continue;
					}
					if (i == 6) {
						vc.add(TRACKWAY[rs.getInt(6) - 1]);
						continue;
					}
					vc.add(rs.getString(i));
				}
				data.add(vc); //add方法是将data中的值域指向vc,如果vc被修改,data中的值也将改变.
			}

		} catch (SQLException e) {
			logger.error("数据库连接出错!" + e.getMessage() + " - " + GetInfo.class.getName());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("关闭Statement失败!" + e.getMessage() + " - " + GetInfo.class.getName());
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

		return data;
	}

	private static Logger logger = Logger.getLogger(GetInfo.class);

	public static final String[] PROJECTSTATE = {"跟踪", "方案", "竞标", "谈判", "签约", "中止"};
	public static final String[] TRACKWAY = {"电话", "邮件", "面谈", "信函"};
	public static final String[] SEX = {"男", "女"};
	public static final String[] QUALI = {"小学", "中学", "中专", "高中", "大专", "本科", "研究生", "硕士", "博士", "其他"};
	public static final String[] POSITION = {"总经理", "部门经理", "销售经理", "销售助理"};
}
