package com.fwh.yifaexp.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class UserForYifa extends BmobUser {
	   private Integer iBanlance=0;//账户总额
	   private BmobGeoPoint gpsAdd;//地址
	   private Integer state;//用户状态 0 正常 1封停  2审核中
		 public Integer getState() {
			return state;
		}
	public BmobGeoPoint getGpsAdd() {
		return gpsAdd;
	}

	public void setGpsAdd(BmobGeoPoint gpsAdd) {
		this.gpsAdd = gpsAdd;
	}

	public Integer getIBanlance() {
		return iBanlance;
	}

	public void setIBanlance(Integer iBanlance) {
		this.iBanlance = iBanlance;
	}

	
	
	private String company;//出租车公司
	 private String carNum;//车牌号
	 private String carInfo;//基本信息
	 private String phone;//基本信息
	 private String nickeyName;//昵称 也就是司机注册的名子
	 private String ImageVal;//审核图片
	 private Integer type=1;//客户类型 1用户  2司机
	 private boolean isBusy = false;//是否空闲状态
	 private String gpsAddStr;//当前地理位置
	public String getGpsAddStr() {
		return gpsAddStr;
	}

	public void setGpsAddStr(String gpsAddStr) {
		this.gpsAddStr = gpsAddStr;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickeyName() {
		return nickeyName;
	}

	public void setNickeyName(String nickeyName) {
		this.nickeyName = nickeyName;
	}

	public String getImageVal() {
		return ImageVal;
	}

	public void setImageVal(String imageVal) {
		ImageVal = imageVal;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = 1;
	}
}
