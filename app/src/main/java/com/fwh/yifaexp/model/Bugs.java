package com.fwh.yifaexp.model;

import cn.bmob.v3.BmobObject;

public class Bugs extends BmobObject {
    private String BugInfo;
    private String BugFrom;


    public String getBugInfo() {
		return BugInfo;
	}
	public void setBugInfo(String bugInfo) {
		BugInfo = bugInfo;
	}
	public String getBugFrom() {
		return BugFrom;
	}
	public void setBugFrom(String bugFrom) {
		BugFrom = bugFrom;
	}

}