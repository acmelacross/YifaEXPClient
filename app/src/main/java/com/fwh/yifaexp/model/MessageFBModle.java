package com.fwh.yifaexp.model;

import cn.bmob.v3.BmobObject;

public class MessageFBModle extends BmobObject
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String message;
  private String userInfo = "";

  public String getMessage()
  {
    return this.message;
  }

  public String getUserInfo()
  {
    return this.userInfo;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }

  public void setUserInfo(String paramString)
  {
    this.userInfo = paramString;
  }
}