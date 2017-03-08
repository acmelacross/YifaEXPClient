package com.fwh.yifaexp.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import cn.bmob.v3.datatype.BmobGeoPoint;


import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;

import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.PoiSearchContentAdapter;

public class SelectAddWithMapActivity extends Activity implements OnPoiSearchListener,AMap.OnInfoWindowClickListener,AMap.OnMapClickListener {
	private AMap aMap;
	private MapView mapView;
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;
	private PoiResult poiResult; // poi返回的结果
	//private PoiOverlay poiOverlay;// poi图层
	private List<PoiItem> poiItems;// poi数据
	private boolean doSearchOnce = false;//判断调用一次POI搜索
	private ListView listvSlelctAdd  =null;
	private LatLonPoint lp = new LatLonPoint(39.908127, 116.375257);// 默认西单广场
	private PoiSearchContentAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_add_with_map);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		initView();
	}
	private void init() {
		if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
	}
	   /**
     * amap添加一些事件监听器
     */
    private void setUpMap() {
        		aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器

    }
	private void initView() {
		// TODO Auto-generated method stub
		listvSlelctAdd = (ListView)findViewById(R.id.listvSlelctAdd);
		adapter = new PoiSearchContentAdapter(getApplicationContext(),new ArrayList<String>());
		listvSlelctAdd.setAdapter(adapter);
		listvSlelctAdd.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				resultIndex(arg2);
			}
		});
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
	}


	
	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker,final LatLng latLng) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation(latLng);
		startPoint.offset(0, -50);//跳动位移
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * latLng.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * latLng.latitude + (1 - t)
						* startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));
				//aMap.invalidate();// 刷新地图
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
			}
		});

	}
	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		//System.out.println("doSearchQuery");
		query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(15);// 设置每页最多返回多少条poiitem
		if (lp != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(lp, 2000, true));//

			poiSearch.searchPOIAsyn();// 异步搜索
		}
	}

	/**
	 * POI搜索回调方法
	 */
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		
		// TODO Auto-generated method stub
		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					if (poiItems != null && poiItems.size() > 0) {
						aMap.clear();// 清理之前的图标
//						poiOverlay = new PoiOverlay(aMap, poiItems);
//						poiOverlay.removeFromMap();
//						poiOverlay.addToMap();
						

						//更新列表适配器
						List<String> listString = new ArrayList<String>();
						for (int i = 0; i < poiItems.size(); i++) {
							listString.add(poiItems.get(i).getCityName()+";"+poiItems.get(i).toString());
							//System.out.println("listString" + poiItems.get(i).toString());
						}
						
						adapter.setList(listString);
						adapter.notifyDataSetChanged();
					} else if (suggestionCities != null
							&& suggestionCities.size() > 0) {
						//showSuggestCity(suggestionCities);
					} else {
						ToastUtil.show(SelectAddWithMapActivity.this,
								R.string.no_result);
					}
				}
			} else {
				ToastUtil
						.show(SelectAddWithMapActivity.this, "请将地图放大一些");
			}
		} else if (rCode == 27) {
			ToastUtil
					.show(SelectAddWithMapActivity.this, R.string.error_network);
		} else if (rCode == 32) {
			ToastUtil.show(SelectAddWithMapActivity.this, R.string.error_key);
		} else {
			ToastUtil.show(SelectAddWithMapActivity.this,getString(R.string.error_other) + rCode);
		}
	}

	@Override
	public void onPoiItemSearched(PoiItem poiItem, int i) {

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
	
	@Override
	public void onMapClick(LatLng latLng) {
		// TODO Auto-generated method stub
		//System.out.println("onMapClick ");
		lp.setLatitude(latLng.latitude);
		lp.setLongitude(latLng.longitude);
		doSearchQuery();
	}
	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		//System.out.println("onInfoWindowClick");
	}
	 /**记录选中地点返回到主页
	 * @param position
	 */
	public void resultIndex(int position) {
		Config.getInstance().gpsAddStart = new BmobGeoPoint(poiItems.get(position).getLatLonPoint().getLongitude(), poiItems.get(position).getLatLonPoint().getLatitude());
		Config.getInstance().gpsAddStartStr=poiItems.get(position).getCityName().toString()+poiItems.get(position).toString();
		Intent intent=new Intent();
	        intent.putExtra("weidu", poiItems.get(position).getLatLonPoint().getLatitude());
	        intent.putExtra("jingdu", poiItems.get(position).getLatLonPoint().getLongitude());
	        intent.putExtra("miaoshu", poiItems.get(position).getCityName().toString()+poiItems.get(position).toString());
	        setResult(IndexActivity.SELECT_ADDRESS_WITH_MAP, intent);
	        finish();
	    }




}
