package com.fwh.yifaexp.model;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;


public class Address extends BmobObject implements Serializable{
    private String addressName;
    private String addressProvince;
    private double lat;
    private double lng;
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getAddressProvince() {
		return addressProvince;
	}
	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public Address(String addressName, String addressProvince, LatLng latlng) {
		super();
		this.addressName = addressName;
		this.addressProvince = addressProvince;
	}



}