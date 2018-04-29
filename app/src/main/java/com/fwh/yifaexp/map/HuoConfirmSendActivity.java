package com.fwh.yifaexp.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
import com.fwh.yifaexp.MyUserCenter.MapHuoWuActivity;
import com.fwh.yifaexp.MyUserCenter.MyJourneyActivity;
import com.fwh.yifaexp.model.GoodsForYifa;
import com.fwh.yifaexp.model.UserForYifa;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

public class HuoConfirmSendActivity extends Activity {
	private AMap aMap;
	private MapView mapView;
	private TextView tv_huo_confirm;
	GoodsForYifa  good = new GoodsForYifa();
	
//	@ViewInject(R.id.tvHuoConfirmMileage)
	TextView tvHuoConfirmMileage;//总里程
//	@ViewInject(R.id.tvHuoConfirmQiBu)
	TextView tvHuoConfirmQiBu;//起步价
//	@ViewInject(R.id.tvHuoConfirmChaoLiCheng)
	TextView tvHuoConfirmChaoLiCheng;//超里程费用
//	@ViewInject(R.id.tvHuoConfirmZaixian)
	TextView tvHuoConfirmZaixian;//在线支付
	
	
	boolean  bZaiXian = false;//在线支付  默认 货到付款
	TextView textView;
	float dis=100;//距离
	double chaogonglifei = 10;//超公里费用
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huo_confirm_send);

		//ViewUtils.inject(this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initView();
		initMap();
		
		
		payByAli();
	}
	private void initView() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.title)).setText("确认发货");
		// 返回按钮
		findViewById(R.id.imgvPoiTitleBack).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
		});
		tvHuoConfirmMileage =(TextView) findViewById(R.id.tvHuoConfirmMileage);
		tvHuoConfirmQiBu  =(TextView) findViewById(R.id.tvHuoConfirmQiBu);
		tvHuoConfirmChaoLiCheng =(TextView) findViewById(R.id.tvHuoConfirmChaoLiCheng);
		tvHuoConfirmZaixian =(TextView) findViewById(R.id.tvHuoConfirmZaixian);


		tv_huo_confirm = (TextView) findViewById(R.id.tv_huo_confirm);
		tv_huo_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Config.getInstance().isJingZhun) //判断为精准发货
					commitGoodsInfo(Config.getInstance().isJingZhunDriver);
				else//智能匹配司机发货
				getDrivres();
			}
		});
		
		//tvHuoConfirmMileage 总里程
		dis = MyMapUtil.circulateDistance(new LatLng(Config.getInstance().gpsAddStart.getLatitude(), Config.getInstance().gpsAddStart.getLongitude())
		, new LatLng(Config.getInstance().gpsAddFinish.getLatitude(), Config.getInstance().gpsAddFinish.getLongitude()));
		if((int)(dis/1000) == 0){
			tvHuoConfirmMileage.setText("不到1"+"公里");
		}else{
			tvHuoConfirmMileage.setText(""+(int)(dis/1000)+"公里");
		}

			tvHuoConfirmQiBu.setText(Config.getInstance().startPrice+"");
			chaogonglifei = (((int)(dis/1000)-5)*Config.getInstance().meiPrice);

			//tvHuoConfirmChaoLiCheng.setText(chaogonglifei+"");
		initJIsuanfei();
	}
	///**
	//计算费用公式  2018-04-11
	/// */
	private double allPrice=0.0;
	private void initJIsuanfei(){
		//
		//
		//dis
		if(Config.getInstance().ExpWay==Constants.EXP_WAY_BY_TONGCHENG){
//			if (dis/1000>=5){// 超出五公里后2元每方  5公里以上最低不能少于五元
//				allPrice+=(Config.getInstance().ExpHuoTiji*2*(dis/1000));
//				if(allPrice<=5){
//					allPrice = 5;
//				}
//			}else{//五公里以内最低2元,十元每方,
//				allPrice+=(Config.getInstance().ExpHuoTiji*Config.getInstance().meiPrice);//*((dis/1000))
//				if (	allPrice<=2){
//					allPrice=2;
//				}
//			}

			if (dis/1000>=5){// 超出五公里后2元每方  5公里以上最低不能少于五元
				allPrice+=(Config.getInstance().ExpHuoTiji*Config.getInstance().meiPrice);
				allPrice+=(Config.getInstance().ExpHuoTiji*2*((dis/1000)-5));

			}else{//五公里以内最低2元,十五元每方,
				allPrice+=(Config.getInstance().ExpHuoTiji*Config.getInstance().meiPrice);//*((dis/1000))
				if (	allPrice<=2){
					allPrice=2;
				}
			}
		}else{
			allPrice+=(Config.getInstance().ExpHuoTiji*50);

		}

		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0");
		tvHuoConfirmChaoLiCheng.setText(df.format(allPrice)+"元");
		//
	}
	private void initMap() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		//aMap.set
		printPointWithMap();
	}
	private void printPointWithMap() {
		// MyMapUtil.drawMarkerAndWinWithLon(aMap, 30.679879, 104.064855,
		// "主人,我到这里啦", "外星地点", false).showInfoWindow();
		//System.out.println(good.getUserDriver().getGpsAdd() + "----------");
		double lat = Config.getInstance().gpsAddFinish.getLatitude();
		double lng = Config.getInstance().gpsAddFinish.getLongitude();
		Marker mar = aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.position(new LatLng(lat, lng))
				.title(Config.getInstance().gpsAddFinishStr)
				.snippet("")
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_yuyue_type_end)));
		mar.showInfoWindow();
		MyMapUtil.changeCamera(aMap, CameraUpdateFactory
				.newCameraPosition(new CameraPosition(new LatLng(lat, lng), 18,
						0, 30)));
		
		double lat2 = Config.getInstance().gpsAddStart.getLatitude();
		double lng2 = Config.getInstance().gpsAddStart.getLongitude();
		Marker mar2 = aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.position(new LatLng(lat2, lng2))
				.title(Config.getInstance().gpsAddStartStr)
				.snippet("")
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_yuyue_type_start)));
		mar2.showInfoWindow();
//		MyMapUtil.changeCamera(aMap, CameraUpdateFactory
//				.newCameraPosition(new CameraPosition(new LatLng(lat2, lng2), 18,
//						0, 30)));

		aMap.moveCamera(CameraUpdateFactory.zoomTo(5));
	}
	
	/**
	 * 获取附近司机
	 */
	private void getDrivres(){
		UserForYifa user = BmobUser.getCurrentUser(this, UserForYifa.class);
		if(user != null){
		    // 允许用户使用应用
			System.out.println("you   "  + user.getUsername());
			BmobQuery<UserForYifa> bmobQuery = new BmobQuery<UserForYifa>();
			bmobQuery.addWhereNear("gpsAdd", Config.getInstance().gpsAddStart);
			bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
			bmobQuery.findObjects(this, new FindListener<UserForYifa>() {
			    @Override
			    public void onSuccess(List<UserForYifa> object) {
			        // TODO Auto-generated method stub
			        System.out.println("查询成功：共" + object.size() + "条数据。"+object);
			        for (int i = 0; i < object.size(); i++) {
						if (object.get(i).getType() == 2&&(!object.get(i).isBusy())) {
							System.out.println("object.get(i)" + object.get(i));
							commitGoodsInfo(object.get(i));
							break;
						}
						if(i == (object.size()-1))//到了最后还没有司机
						{
							ToastUtil.show(getApplicationContext(), "附近木有空闲司机,请稍后再试");
						}
					}
			        
			    }
			    @Override
			    public void onError(int code, String msg) {
			        // TODO Auto-generated method stub
			    	  System.out.println("查询失败：" + msg);
			    	  	ToastUtil.show(getApplicationContext(), "未能匹配到司机,请稍后再试"+code+msg);
			    	  	FailedlWrite.writeCrashInfoToFile("未能匹配到司机,请稍后再试"+code+msg);
			    }
			});
			
		}else{
		    //缓存用户对象为空时， 可打开用户注册界面…
			System.out.println("null   ");
		}
	}
	/**
	 * 提交货物信息
	 */
	private void commitGoodsInfo(UserForYifa driver){
	
		UserForYifa user = BmobUser.getCurrentUser(this, UserForYifa.class);
		good.setPrice(allPrice);
		good.setiExpWay(Config.getInstance().ExpWay);
		good.setsExpWayStr(Config.getInstance().ExpWayStr);
		good.setsExpUserTime();
		System.out.println("goodtime " +good.getsExpUserTime() );
		good.setUserDriver(driver);
		good.setsExpOrderTime(Config.getInstance().EXPOrderTime);
		good.setGpsAddFinish(Config.getInstance().gpsAddFinish);
		good.setGpsAddStart(Config.getInstance().gpsAddStart);
		good.setGpsAddFinishStr(Config.getInstance().gpsAddFinishStr);
		good.setGpsAddStartStr(Config.getInstance().gpsAddStartStr);
		good.setiGoodState(Constants.EXP_GoodState_Jie);
		good.setPriceStaet(Constants.EXP_GoodPayStateDefault);
		good.setXianShiDa(Config.getInstance().XianShiDa);
		if (Config.getInstance().ExpWay !=Constants.EXP_WAY_BY_KUAIDI) {
			good.setdExpHuoWeight(Config.getInstance().ExpHuoWeight);
			good.setsExpCheType(Config.getInstance().ExpCheType);
			good.setsExpHuoInfoType(Config.getInstance().ExpHuoInfoType);
			good.setsExpHuoType(Config.getInstance().ExpHuoType);
		}
		good.setdExpHuoTiji(Config.getInstance().ExpHuoTiji);

		good.setUser(user);
		MyJourneyActivity.goodTemp = good;
		good.save(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		        // TODO Auto-generated method stub
		       // Log.i("life","发货成功");
		    	System.out.println("good" + good.getString()+good.getsExpUserTime());
		    	startActivity(new Intent().setClass(getApplicationContext(), MapHuoWuActivity.class));
		    	Config.getInstance().isJingZhun=false;
		    	finish();
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		        // TODO Auto-generated method stub
		       // Log.i("life","评论失败");
		    }
		});
	}
	
	// 调用支付宝支付
		void payByAli() {
			// BmobPay.init(this, APPID);

//			BP.pay(this, "", "", 0.1, false, new PListener() {
//
//				// 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
//				@Override
//				public void unknow() {
//
//				}
//
//				// 支付成功,如果金额较大请手动查询确认
//				@Override
//				public void succeed() {
//
//				}
//
//				// 无论成功与否,返回订单号
//				@Override
//				public void orderId(String orderId) {
//					// 此处应该保存订单号,比如保存进数据库等,以便以后查询
//
//				}
//
//				// 支付失败,原因可能是用户中断支付操作,也可能是网络原因
//				@Override
//				public void fail(int code, String reason) {
//
//				}
//			});
		}
	
	/**
	 * 设置在线支付 货到付款
	 */
	//@OnClick(R.id.tvHuoConfirmZaixian)

	
	void installBmobPayPlugin(String fileName) {
		try {
			InputStream is = getAssets().open(fileName);
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + fileName + ".apk");
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.parse("file://" + file),
					"application/vnd.android.package-archive");
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
