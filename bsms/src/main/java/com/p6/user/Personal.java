package com.p6.user;

import com.p6.user.infoBean.Info;

public abstract class Personal extends DoHandler {

	public abstract void process(Info infoBean);
	public abstract boolean isSucced();
}
