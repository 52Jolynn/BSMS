package com.p6.user.personal;

import com.p6.user.Personal;
import com.p6.user.infoBean.Info;

public class DoViewSelfInfo extends Personal {

	@Override
	public boolean isSucced() {
		// TODO Auto-generated method stub
		return result==0?false:true;
	}

	@Override
	public void process(Info infoBean) {
		// TODO Auto-generated method stub
		
	}
	private int result = 0;
}
