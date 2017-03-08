package com.fwh.yifaexp.map;

import cn.bmob.v3.datatype.BmobGeoPoint;

import com.fwh.utils.Constants;
import com.fwh.yifaexp.model.UserForYifa;

public class Config {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */  
    private static Config instance = null;  
  
    /* 私有构造方法，防止被实例化 */  
    private Config() {  
    }  
  
    /* 1:懒汉式，静态工程方法，创建实例 */  
    public static Config getInstance() {  
        if (instance == null) {  
            instance = new Config();  
        }  
        return instance;  
    }  
	public  int ExpWay = Constants.EXP_WAY_BY_TONGCHENG;//发货方式
	public  String currentCity = "潍坊市";//发货方式
	public  String ExpWayStr = "同城";//发货方式
	public  String ExpHuoType = "";//货物类型
	public  String ExpCheType = "";//所需车型
	public  double  ExpHuoWeight = 0.0;//货物重量
	public  String  ExpHuoInfoType = "默认";//重货 泡货 "重货"
	public  String EXPOrderTime="";//预约时间
	public  String XianShiDa="当日达";//限时达
	public  BmobGeoPoint gpsAddStart;//地理位置
	public  BmobGeoPoint gpsAddFinish;//地理位置
	public  String gpsAddStartStr;//地理位置
	public  String gpsAddFinishStr;//地理位置
	public  String  ExpHuoZSInfo = "默认";//整车散货
	public  String  EXPCompany = "默认";//物流公司
	public  double  startPrice =10;//起步价
	public  double  meiPrice =10;//每公里价格
	public  int  ExpHuoZSTtpe;//整车散货
	public BmobGeoPoint MyPoint;
	public boolean isJingZhun = false;//是否精准发车 
	public UserForYifa isJingZhunDriver = null;//精准发车司机
}
