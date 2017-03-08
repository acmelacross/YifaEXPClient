package com.fwh.utils;

import android.os.Environment;

import com.amap.api.maps.model.LatLng;


public class Constants {
	public static String SDPath = Environment.getExternalStorageDirectory().getPath()+"/yifaclient/";

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


	public static final int ERROR = 1001;// 网络异常
	public static final int ROUTE_START_SEARCH = 2000;
	public static final int ROUTE_END_SEARCH = 2001;
	public static final int ROUTE_BUS_RESULT = 2002;// 路径规划中公交模式
	public static final int ROUTE_DRIVING_RESULT = 2003;// 路径规划中驾车模式
	public static final int ROUTE_WALK_RESULT = 2004;// 路径规划中步行模式
	public static final int ROUTE_NO_RESULT = 2005;// 路径规划没有搜索到结果

	public static final int GEOCODER_RESULT = 3000;// 地理编码或者逆地理编码成功
	public static final int GEOCODER_NO_RESULT = 3001;// 地理编码或者逆地理编码没有数据

	public static final int POISEARCH = 4000;// poi搜索到结果
	public static final int POISEARCH_NO_RESULT = 4001;// poi没有搜索到结果
	public static final int POISEARCH_NEXT = 5000;// poi搜索下一页

	public static final int BUSLINE_LINE_RESULT = 6001;// 公交线路查询
	public static final int BUSLINE_id_RESULT = 6002;// 公交id查询
	public static final int BUSLINE_NO_RESULT = 6003;// 异常情况

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
	public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
	public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
	public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
	public static final String KEFUTEL ="13305364454";// 郑州市经纬度
	private static String APP_FILE_PATH=Environment.getExternalStorageDirectory().getPath()+"/yifaclient/";
	public static String FILE_PATH_SEARCHTEMP=APP_FILE_PATH+"temp/";
	public static String FILE_NAME_SEARCHTEMP="search_temp";
}
