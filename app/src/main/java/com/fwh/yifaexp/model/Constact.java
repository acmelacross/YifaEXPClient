package com.fwh.yifaexp.model;

import android.R.integer;

public class Constact {
	public static final int EXP_WAY_BY_KUAIDI = 50;
	public static final int EXP_WAY_BY_TONGCHENG = 51;
	public static final int EXP_WAY_BY_WULIU = 52;
	public static final String EXP_WAY_STR_KUAIDI = "快递";
	public static final String EXP_WAY_STR_TONGCHENG  = "同城";
	public static final String EXP_WAY_STR_WULIU = "物流";
	public static final int EXP_GoodStateCancel = 0;//货物当前状态 0废弃 1接单中 2配送中 3已抵达  
	public static final int EXP_GoodState_Jie = 1;
	public static final int EXP_GoodState_Song = 2;
	public static final int EXP_GoodState_Da = 3;
//	public static final int EXP_GoodPriceState_Wei = 0;//金额详情 未付款
//	public static final int EXP_GoodPriceState_Fu = 1;//金额详情 未付款
	public static final String EXP_GoodZSZhengStr  = "专车";
	public static final String EXP_GoodZSSanStr = "拼车";//散货
	public static final int EXP_GoodZSZheng = 1;//整车
	public static final int EXP_GoodZSSan = 2;//拼车
//	public static final String EXP_WAY_BY_XIAODAN = "小单";
	public static final int EXP_GoodPayStateDefault = 0;//未选择 默认  
	public static final int EXP_GoodPayStateInt = 1;//货到付款  
	public static final int EXP_GoodPayStateDestination= 2;//在线支付  
}
