package com.fwh.yifaexp.model;

import java.io.Serializable;


import cn.bmob.v3.BmobObject;

public class CarTypeFind extends BmobObject implements Serializable{
    private String carTitle;//标题 自行车 面包车等
    private double priceStarting;//起步价
    private double priceEnd;//超公里费用 每公里多少元
    private double 	load;//载重
    private String 	type;//长宽高
	public String getCarTitle() {
		return carTitle;
	}
	public void setCarTitle(String carTitle) {
		this.carTitle = carTitle;
	}
	public double getPriceStarting() {
		return priceStarting;
	}
	public void setPriceStarting(double priceStarting) {
		this.priceStarting = priceStarting;
	}
	public double getPriceEnd() {
		return priceEnd;
	}
	public void setPriceEnd(double priceEnd) {
		this.priceEnd = priceEnd;
	}
	public double getLoad() {
		return load;
	}
	public void setLoad(double load) {
		this.load = load;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
  




}