package com.fwh.yifaexp.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;
import android.R.integer;

public class ExpCompany  extends BmobObject {
	private float price;//价格
	private String mobileNums;//电话号码
	private BmobGeoPoint position;//当前位置
	private boolean isDao;//是否支持货到付款 
	private String imgUrl;//图片链Í
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getMobileNums() {
		return mobileNums;
	}
	public void setMobileNums(String mobileNums) {
		this.mobileNums = mobileNums;
	}
	public BmobGeoPoint getPosition() {
		return position;
	}
	public void setPosition(BmobGeoPoint position) {
		this.position = position;
	}
	public boolean isDao() {
		return isDao;
	}
	public void setDao(boolean isDao) {
		this.isDao = isDao;
	}
}
