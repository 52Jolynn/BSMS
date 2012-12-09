package com.p6.user;

import com.p6.user.infoBean.Info;

public abstract class Others extends DoHandler {

	public abstract void process(Info infoBean);
	public abstract boolean isSucced();
}
