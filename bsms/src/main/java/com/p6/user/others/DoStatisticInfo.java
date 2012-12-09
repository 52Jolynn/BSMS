package com.p6.user.others;

import com.p6.user.Others;
import com.p6.user.infoBean.Info;

public class DoStatisticInfo extends Others {

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
