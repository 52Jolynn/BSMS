package com.p6.user.infoBean;

public class PositionInfo implements Info {
	private String posName = "";	//职位名称
	private String posDesc = "";	//职位描述
	
	public boolean setPosName(String posName){this.posName = posName; return true;}
	public boolean setPosDesc(String posDesc){this.posDesc = posDesc; return true;}
	
	public PositionInfo getInfo(){return this;}
	public String getPosName(){return posName;}
	public String getPosDesc(){return posDesc;}
}
