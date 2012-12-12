package com.p6.user.project;

import com.p6.user.Project;
import com.p6.user.infoBean.Info;

public class DoProjectNotify extends Project {

	@Override
	public boolean isSucced() {
		// TODO Auto-generated method stub
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		// TODO Auto-generated method stub
		
	}
	private int result=0;
}
