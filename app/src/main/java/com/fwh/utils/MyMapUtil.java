package com.fwh.utils;

import java.util.List;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;

public class MyMapUtil {
	public static float circulateDistance(LatLng firstL, LatLng lastL) {
		// TODO Auto-generated method stub
		//firstLFloat a = AMapUtils.calculateLineDistance(new LatLng(39.9106560000,116.3851130000), new LatLng(31.0539610000,121.5555540000));
		Float a = AMapUtils.calculateLineDistance(firstL, lastL);
		System.out.println("输出 距离   --" + a);
		return a ;
	}
	/**
	 * 在地图通过PoiItem画marker 
	 * @param aMap
	 * @param poiItems
	 * @param isClean
	 */
	public static void drawMarkerWithPoiItem(AMap aMap, List<PoiItem> poiItems, boolean isClean){
//		if (isClean)
//			aMap.clear();// 清理之前的图标
//		PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
//		poiOverlay.removeFromMap();
//		poiOverlay.addToMap();
	}
	
	/**
	 * 在地图通过经纬度画marker 
	 * @param aMap
	 * @param aLocation
	 * @param isClean
	 */
	public static Marker drawMarkerAndWinWithLon(AMap aMap, double wei, double jing, String title, String title2, boolean isClean) {
		// TODO Auto-generated method stub
		Marker marker =	aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(new LatLng(wei, jing)).title(title)
				.snippet(title2).draggable(true));
		marker.showInfoWindow();
		return marker;
	}
	/**
	 * moveCamera来改变可视区域
	 */
	public static void   changeCamera(AMap aMap,CameraUpdate update) {
		
			aMap.moveCamera(update);
	}
}
