package com.p6.user.infoBean;

public class CustomerInfo implements Info {
	private int customerID = 0;				//客户ID
	private String customerName = "";		//客户名称
	private String customerIndustry = "";	//所属行业
	private String customerMUnit = "";		//主管单位
	private String customerArea = "";		//所在地区
	private String customerAddress = "";		//地址
	private String customerMailNum = "";		//邮编
	private String customerWeb = "";			//网站
	private String customerProduct = "";		//适用产品
	private String customerRemark = "";		//备注
	private String siftKey = "";				//简易筛选Key
	
	public boolean setCustomerID(int customerID)
		{this.customerID = customerID; return true;}
	public boolean setCustomerName(String customerName)
		{this.customerName = customerName; return true;}
	public boolean setCustomerIndustry(String customerIndustry)
		{this.customerIndustry = customerIndustry; return true;}
	public boolean setCustomerMUnit(String customerMUnit)
		{this.customerMUnit = customerMUnit; return true;}
	public boolean setCustomerArea(String customerArea)
		{this.customerArea = customerArea; return true;}
	public boolean setCustomerAddress(String customerAddress)
		{this.customerAddress = customerAddress; return true;}
	public boolean setCustomerMailNum(String customerMailNum)
		{this.customerMailNum = customerMailNum; return true;}
	public boolean setCustomerWeb(String customerWeb)
		{this.customerWeb = customerWeb; return true;}
	public boolean setCustomerProduct(String customerProduct)
		{this.customerProduct = customerProduct; return true;}
	public boolean setCustomerRemark(String customerRemark)
		{this.customerRemark = customerRemark; return true;}
	public boolean setSiftKey(String siftKey)
		{this.siftKey = siftKey; return true;}
	
	public CustomerInfo getInfo(){return this;}
	public int 	  getCustomerID(){return customerID;}				//客户ID
	public String getCustomerName(){return customerName;}			//客户名称
	public String getCustomerIndustry(){return customerIndustry;}	//所属行业
	public String getCustomerMUnit(){return customerMUnit;}		//主管单位
	public String getCustomerArea(){return customerArea;}			//所在地区
	public String getCustomerAddress(){return customerAddress;}	//地址
	public String getCustomerMailNum(){return customerMailNum;}	//邮编
	public String getCustomerWeb(){return customerWeb;}			//网站
	public String getCustomerProduct(){return customerProduct;}	//适用产品
	public String getCustomerRemark(){return customerRemark;}		//备注
	public String getSiftKey(){return siftKey;}					//简易筛选Key

}
