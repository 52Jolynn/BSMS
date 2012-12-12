package com.p6.user;

import com.p6.user.infoBean.Info;

public class Handler {
	public Handler() {
		
	}
	
	public void setDoHandler(DoHandler handler) {
		this.handler = handler;
	}
	
	public void process(Info infoBean) {
		handler.process(infoBean);
	}
	public boolean isSucced() {
		return handler.isSucced();
	}
	
	private DoHandler handler = null;
}
