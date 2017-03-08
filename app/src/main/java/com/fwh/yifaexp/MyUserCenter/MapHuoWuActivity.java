package com.fwh.yifaexp.MyUserCenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.fwh.utils.Constants;
import com.fwh.utils.FailedlWrite;
import com.fwh.utils.MyMapUtil;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.model.GoodsForYifa;

public class MapHuoWuActivity extends Activity {
	private AMap aMap;
	private MapView mapView;
	private GoodsForYifa good = MyJourneyActivity.goodTemp;
	public static final String URL = "YOUR_URL";
	TextView tv_mapHuoCancel;//取消订单
	LinearLayout ll_mapHuoPayWay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_huo_wu);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		// 设置要使用的支付方式
		//initPingJiaJia();
		initMap();
		initView();

	}

//	private void initPingJiaJia() {
//		// TODO Auto-generated method stub
//		// 打开微信支付按钮
//		PayActivity.SHOW_CHANNEL_WECHAT = true;
//		// 打开银联支付按钮
//		PayActivity.SHOW_CHANNEL_UPMP = true;
//		// 打开百度支付按钮
//		PayActivity.SHOW_CHANNEL_BFB = true;
//		// 打开支付宝按钮
//		PayActivity.SHOW_CHANNEL_ALIPAY = true;
//	}

	private void initView() {
		good = MyJourneyActivity.goodTemp;
		//System.out.println("good   " +good);
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.title)).setText("货去哪儿了");
		((TextView)findViewById(R.id.tvMapPositionCheNums)).setText(good.getUserDriver().getCarNum());
		TextView tvIsPay =(TextView)findViewById(R.id.tvIsPay);
		((TextView) findViewById(R.id.tvMapHuoStart)).setText(good.getGpsAddStartStr());
		((TextView) findViewById(R.id.tvMapHuoEnd)).setText(good.getGpsAddFinishStr());
		
		 
		//货物状态为已付款 则取消显示
		ll_mapHuoPayWay = (LinearLayout)findViewById(R.id.ll_mapHuoPayWay);
		System.out.println("good.getExpHuoPay()  " +good.getExpHuoPay());
		if (good.getExpHuoPay() == Constants.EXP_GoodPayStateDefault)
			tvIsPay.setText("货到付款");
			//ll_mapHuoPayWay.setVisibility(View.GONE);
		else if (good.getExpHuoPay() ==Constants.EXP_GoodPayStateDestination) //1货到付款 2 在线支付
			tvIsPay.setText("货到付款");
		else if (good.getExpHuoPay() ==Constants.EXP_GoodPayStateInt) //1货到付款 2 在线支付
			tvIsPay.setText("在线支付");
			
		// 返回按钮
		findViewById(R.id.imgvPoiTitleBack).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		// 初始化 付款按钮 在线支付
		TextView tv_mapHuoPay = (TextView)findViewById(R.id.tv_mapHuoPayInternet);
		tv_mapHuoPay.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						pay();//good
					}
				});
		// 初始化 付款按钮 货到付款
		TextView tv_mapHuoPayDao = (TextView)findViewById(R.id.tv_mapHuoPayDao);
		tv_mapHuoPayDao.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						updatePayState(Constants.EXP_GoodPayStateDestination);
						
					}
				});
		if (good.getPriceStaet() !=Constants.EXP_GoodPayStateDefault) {
			tv_mapHuoPay.setVisibility(View.GONE);
		}
		// 初始化 取消订单
		tv_mapHuoCancel	 = (TextView)findViewById(R.id.tv_mapHuoCancel);
		if (good.getiGoodState() != 0) 
			tv_mapHuoCancel.setBackgroundColor(Color.parseColor("#ff8903"));
		else
			tv_mapHuoCancel.setBackgroundColor(Color.parseColor("#ffd0d0d0"));
				tv_mapHuoCancel.setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								//pay();//good
								//pay();//good
								if(good.getiGoodState() != Constants.EXP_GoodStateCancel){
									new AlertDialog.Builder(MapHuoWuActivity.this).setTitle("确认取消订单吗？") 
									.setMessage("恶意刷单将会被处罚哦")
						            .setIcon(android.R.drawable.ic_dialog_info) 
						            .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
						                @Override 
						                public void onClick(DialogInterface dialog, int which) { 
						                // 点击“确认”后的操作 
						                	GoodsForYifa temp = new GoodsForYifa();
											temp.setiGoodState(Constants.EXP_GoodStateCancel);
											temp.update(MapHuoWuActivity.this, good.getObjectId(), new UpdateListener() {
											    @Override
											    public void onSuccess() {
											        // TODO Auto-generated method stub
											    	good.setiGoodState(Constants.EXP_GoodStateCancel);
											    	ToastUtil.show(MapHuoWuActivity.this, "订单取消成功");
											    	tv_mapHuoCancel.setBackgroundColor(Color.parseColor("#ffd0d0d0"));
											    }
											    @Override
											    public void onFailure(int code, String msg) {
											        // TODO Auto-generated method stub
											    	ToastUtil.show(MapHuoWuActivity.this, "订单取消失败,错误信息已记录,请联系客服  "+code+msg);
											    	FailedlWrite.writeCrashInfoToFile("订单取消失败,错误信息  "+code+msg);
											    }
											});
						                } 
						            }) 
						            .setNegativeButton("返回", new DialogInterface.OnClickListener() { 
						                @Override 
						                public void onClick(DialogInterface dialog, int which) { 
						                // 点击“返回”后的操作,这里不设置没有任何操作 
						                } 
						            }).show(); 
								}
								else
									ToastUtil.show(MapHuoWuActivity.this, "订单已经取消");
							}
						});
				findViewById(R.id.ivMapPositionCall).setOnClickListener(new View.OnClickListener() {//打电话按钮
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//System.out.println(good.getUser().getUsername()+good.getUser().getPhone());
		                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+good.getUserDriver().getUsername()));  
		                startActivity(intent);  
					}
				});
		
	}

	private void initMap() {
		//System.out.println("good2   " +good);
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		((TextView) findViewById(R.id.tvMapHuoWhereInfo)).setText(good
				.getString());
	}

	/**
	 * amap添加一些事件监听器
	 */
	private void setUpMap() {
		// aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
		// aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
		// aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
		printPointWithMap();
	}

	private void printPointWithMap() {
		// MyMapUtil.drawMarkerAndWinWithLon(aMap, 30.679879, 104.064855,
		// "主人,我到这里啦", "外星地点", false).showInfoWindow();
		//System.out.println( good+ "----------");
		double lat = good.getUserDriver().getGpsAdd().getLatitude();
		double lng = good.getUserDriver().getGpsAdd().getLongitude();
		Marker mar = aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.position(new LatLng(lat, lng))
				.title("司机到这里了")
				.snippet("")
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.map_huo_where)));
		mar.showInfoWindow();
		MyMapUtil.changeCamera(aMap, CameraUpdateFactory
				.newCameraPosition(new CameraPosition(new LatLng(lat, lng), 18,
						0, 30)));
	}

	/**
	 * 发起支付
	 */
	private void pay() {
		// 发起支付
		// 构建账单json对象
		String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date());

		// 计算总金额（以分为单位）
		int amount = 0;
		JSONArray billList = new JSONArray();
		// for (Good good : mList) {
		// amount += good.getPrice() * good.getCount() * 100;
		billList.put(good.getString());
		// }
		// 自定义的额外信息 选填
		JSONObject extras = new JSONObject();
		try {
			extras.put("extra1", "extra1");
			extras.put("extra2", "extra2");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject bill = new JSONObject();
		JSONObject displayItem = new JSONObject();
		try {
			displayItem.put("name", "商品");
			displayItem.put("contents", billList);
			JSONArray display = new JSONArray();
			display.put(displayItem);
			bill.put("order_no", orderNo);
			bill.put("amount", amount);
			bill.put("display", display);
			// bill.put("extras", extras);// 该字段选填
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		PayActivity.CallPayActivity(MapHuoWuActivity.this, bill.toString(),
//				"www.baidu.com");
	}
	private void updatePayState(int state)
	{
		GoodsForYifa goodUp = new GoodsForYifa();
		goodUp.setExpHuoPay(state);
		goodUp.update(this, good.getObjectId(), new UpdateListener() {

		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		    	ToastUtil.show(MapHuoWuActivity.this, "选择成功");
		    }

		    @Override
		    public void onFailure(int code, String msg) {
		        // TODO Auto-generated method stub
		    	ToastUtil.show(MapHuoWuActivity.this, "选择失败,请联系管理员 + 错误码"+ code + msg);
		    	FailedlWrite.writeCrashInfoToFile( "订单支付状态更新失败"+code + msg);
		    }
		});
	}
}
