/**
 * @author Laud
 * @version 1.0.0
 * copyright powersix
 */
package com.p6.user.others;

import java.util.Vector;

import org.apache.log4j.Logger;
import com.p6.toolkit.GetInfo;
import com.p6.user.Others;
import com.p6.user.infoBean.*;

public class DoSearch extends Others {
	/**
	 * 私有构造函数
	 */
	@SuppressWarnings("unused")
	private DoSearch() {
	}
	
	/**
	 * 构造函数
	 * @param infoBean infoBean被查询的信息
	 */
	public DoSearch(Info infoBean) {
		if (infoBean instanceof StaffInfo) {
			logger.info("查询销售人员资料 - " + DoSearch.class.getName());
			staffInfo = (StaffInfo)infoBean;
		}
		else if (infoBean instanceof CustomerInfo) {
			logger.info("查询客户资料 - " + DoSearch.class.getName());
			customerInfo = (CustomerInfo)infoBean;
		}
		else if (infoBean instanceof ContactInfo) {
			logger.info("查询联系人资料 - " + DoSearch.class.getName());
			contactInfo = (ContactInfo)infoBean;
		}
		else if (infoBean instanceof ProjectInfo) {
			logger.info("查询项目资料 - " + DoSearch.class.getName());
			projectInfo = (ProjectInfo)infoBean;
		}
		else if (infoBean instanceof TrackRecordInfo) {
			logger.info("查询跟踪记录资料 - " + DoSearch.class.getName());
			trackRecordInfo = (TrackRecordInfo)infoBean;
		}
		else {
			logger.warn("传入的InfoBean类型参数不正确! - " + DoSearch.class.getName());
		}
	}
	 
	@Override
	public boolean isSucced() {
		return isSucced;
	}

	/**
	 * @param infoBean 发出查询请求的销售信息Bean
	 */
	@Override
	public void process(Info infoBean) {
		if (infoBean instanceof StaffInfo) {
			staff = (StaffInfo)infoBean;
			title = staff.getPosName();
			if (title.equals("总经理")) {
				TMSearch();
			}
			else if (title.equals("部门经理")) {
				DMSearch();
			}
			else if (title.equals("销售经理")) {
				SMSearch();
			}
			else if (title.equals("销售助理")) {
				SASearch();
			}
		}

	}
	
	/**
	 * 总经理搜索,只能查询部门经理资料
	 */
	private void TMSearch() {
		logger.info("总经理执行查询\n");
		logger.debug("staffInfo=null: " + (staffInfo == null));
		logger.info("总经理查询销售人员 - " + DoSearch.class.getName());
		content = GetInfo.getStaffInfo();
		header = GetInfo.getStaffTitle();
		isSucced = true;
	}
	
	/**
	 * 部门经理搜索,查询所有信息
	 */
	private void DMSearch() {
		logger.info("部门经理执行查询\n");
		logger.debug("staffInfo=null: " + (staffInfo == null));
		logger.debug("customerInfo=null: " + (customerInfo == null));
		logger.debug("contactInfo=null: " + (contactInfo == null));
		logger.debug("projectInfo=null: " + (projectInfo == null));
		logger.debug("trackRecordInfo=null: " + (trackRecordInfo == null));
		if (staffInfo != null) {
			logger.info("部门经理查询销售人员 - " + DoSearch.class.getName());
			content = GetInfo.getStaffInfo();
			header = GetInfo.getStaffTitle();
			isSucced = true;
		}
		else if (customerInfo != null) {
			logger.info("部门经理查询客户资料 - " + DoSearch.class.getName());
			content = GetInfo.getCustomerRelateStaff();
			header = GetInfo.getCustomerTitle();
			isSucced = true;
		}
		else if (contactInfo != null) {
			logger.info("部门经理查询联系人资料 - " + DoSearch.class.getName());
			content = GetInfo.getContactRelateStaff();
			header = GetInfo.getContactTitle();
			isSucced = true;
		}
		else if (projectInfo != null) {
			logger.info("部门经理查询项目资料 - " + DoSearch.class.getName());
			content = GetInfo.getAllProject();
			header = GetInfo.getProjectTitle();
			isSucced = true;
		}
		else if (trackRecordInfo != null) {
			logger.info("部门经理查询跟踪记录 - " + DoSearch.class.getName());
			content = GetInfo.getTRRelativeStaff();
			header = GetInfo.getTrackRecordTitle();
			isSucced = true;
		}
	}
	
	/**
	 * 销售经理搜索,查询除销售人员的所有自己负责项目的客户，联系人，项目，跟踪记录信息
	 */
	private void SMSearch() {
		logger.info("销售经理执行查询\n");
		logger.debug("customerInfo=null: " + (customerInfo == null));
		logger.debug("contactInfo=null: " + (contactInfo == null));
		logger.debug("projectInfo=null: " + (projectInfo == null));
		logger.debug("trackRecordInfo=null: " + (trackRecordInfo == null));
		if (customerInfo != null) {
			content = GetInfo.getCustomerRelateStaff();
			header = GetInfo.getCustomerTitle();
		}
		else if (contactInfo != null) {
			content = GetInfo.getContactRelateStaff();
			header = GetInfo.getContactTitle();
			isSucced = true;
		}
		else if (projectInfo != null) {
			content = GetInfo.getAllProject();
			header = GetInfo.getProjectTitle();
			isSucced = true;
		}
		else if (trackRecordInfo != null) {
			content = GetInfo.getTRRelativeStaff();
			header = GetInfo.getTrackRecordTitle();
			isSucced = true;
		}
	}
	
	/**
	 * 销售助理搜索，查询自己负责的客户，联系人信息
	 */
	private void SASearch() {
		logger.info("销售助理执行查询\n");
		logger.debug("customerInfo=null: " + (customerInfo == null));
		logger.debug("contactInfo=null: " + (contactInfo == null));
		if (customerInfo != null) {
			content = GetInfo.getCustomerRelateStaff();
			header = GetInfo.getCustomerTitle();
			isSucced = true;
		}
		else if (contactInfo != null) {
			content = GetInfo.getContactRelateStaff();
			header = GetInfo.getContactTitle();
			isSucced = true;
		}
	}
	
	/**
	 * 得到搜索结果用于设置table的header
	 */
	public Vector<String> getHeader() {
		return this.header;
	}
	
	/**
	 * 得到搜索结果用于设置table的content
	 */
	public Vector<Vector<String>> getContent() {
		return this.content;
	}
	
	private boolean isSucced = false;
	private String title = null; //发出查询请求的销售人员的角色
	private StaffInfo staff = null; //发出查询请求的销售人员信息Bean
	private StaffInfo staffInfo = null; //待查询的销售人员信息Bean
	private CustomerInfo customerInfo = null; //待查询的客户信息Bean
	private ContactInfo contactInfo = null; //待查询的联系人信息Bean
	private ProjectInfo projectInfo = null; //待查询的项目信息Bean
	private TrackRecordInfo trackRecordInfo = null; //待查询的跟踪记录信息Bean
	private Vector<Vector<String>> content = new Vector<Vector<String>>();
	private Vector<String> header = new Vector<String>();
	
	private static Logger logger = Logger.getLogger(DoSearch.class);
}
