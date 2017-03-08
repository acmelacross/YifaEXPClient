package com.fwh.yifaexp.MyUserCenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.fwh.utils.FailedlWrite;
import com.fwh.utils.MyMapUtil;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.Config;
import com.fwh.yifaexp.model.UserForYifa;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class ZhouBianWuLiuActivity extends Activity {
	private AMap aMap;
	private MapView mapView;
	List<UserForYifa> nearByUsers=null;//附近的司机集合
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhou_bian_wu_liu);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initMap();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.title)).setText("周边配送站");
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	private void initMap() {
		if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
	}
	   /**
     * amap添加一些事件监听器
     */
    private void setUpMap() {
        	//	aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        //		aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        //aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
    	//printPointWithMap();
    	getCars();
    }
    private void getCars(){
    	BmobQuery<UserForYifa> bmobQuery = new BmobQuery<UserForYifa>();
    	bmobQuery.addWhereNear("gpsAdd", Config.getInstance().MyPoint);
    	bmobQuery.setLimit(20);    //获取最接近用户地点的10条数据
    	bmobQuery.addWhereEqualTo("type", 2);
    	bmobQuery.findObjects(this, new FindListener<UserForYifa>() {
    	    @Override
    	    public void onSuccess(List<UserForYifa> object) {
    	        // TODO Auto-generated method stub
    	    	nearByUsers = object;
    	    	if (nearByUsers.size() >0) 
    	    		printPointWithMap();
    	    	else
    	    		ToastUtil.show(getApplicationContext(), "附近找不到司机");
    	    }
    	    @Override
    	    public void onError(int code, String msg) {
    	        // TODO Auto-generated method stub
    	    	ToastUtil.show(getApplicationContext(), "获取数据失败,请联系管理员 错误码"+code+msg);
    	    	FailedlWrite.writeCrashInfoToFile("获取数据失败,请联系管理员 错误码"+code+msg);
    	    }
    	});
    }
    
    private void printPointWithMap(){
    
    	//MyMapUtil.drawMarkerAndWinWithLon(aMap, 30.679879, 104.064855, "主人,我到这里啦", "外星地点", false).showInfoWindow();
    	for (int i = 0; i < nearByUsers.size(); i++) {
    		double lat = nearByUsers.get(i).getGpsAdd().getLatitude();
    		double lon = nearByUsers.get(i).getGpsAdd().getLongitude();
    		 aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
    				.position(new LatLng(lat, lon)).title("车牌号 "+nearByUsers.get(i).getCarNum())
    				.snippet("电话" +nearByUsers.get(i).getUsername()).draggable(true).icon(BitmapDescriptorFactory
    						.fromResource(R.drawable.map_huo_where)));
		}
    	
    	//marker.showInfoWindow();
    	
         MyMapUtil.changeCamera(aMap,
					CameraUpdateFactory.newCameraPosition(new CameraPosition(
							new LatLng(Config.getInstance().MyPoint.getLatitude(),Config.getInstance().MyPoint.getLongitude()), 18, 0, 30)));
    }

}
