package com.fwh.yifaexp.map;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.ContactsPage;
import cn.smssdk.gui.RegisterPage;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;

import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.fwh.utils.AppUtils;
import com.fwh.utils.Constants;
import com.fwh.utils.FailedlWrite;
import com.fwh.utils.MyMapUtil;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.CheckPermissionsActivity;
import com.fwh.yifaexp.MainActivity;
import com.fwh.yifaexp.MyUserCenter.MyJourneyActivity;
import com.fwh.yifaexp.MyUserCenter.MySettingActivity;
import com.fwh.yifaexp.MyUserCenter.ZhouBianWuLiuActivity;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.model.Bugs;
import com.fwh.yifaexp.model.UserForYifa;

/**
 * 首页Activity
 */
public class IndexActivity extends CheckPermissionsActivity implements LocationSource,
		AMapLocationListener, OnClickListener, AMap.OnInfoWindowClickListener,
		AMap.OnMarkerClickListener, AMap.InfoWindowAdapter {
	static final int SELECT_ADDRESS_WITH_MAP = 1;// 选择地图Activity回传状态值
	static final int SELECT_ADDRESS_RULE_END_PLACE = 2;// POI选择地图重点
	static final int SELECT_EXP_WAY_KUAIDI = 21;// 选择快递运输
	static final int SELECT_EXP_WAY_TONGCHENG = 22;// /选择同城运输
	static final int SELECT_EXP_WAY_WULIU = 23;// /选择物流运输
	private AMap aMap;
	private MapView mapView;
	private LocationSource.OnLocationChangedListener mListener;

	private SlidingPaneLayout paneLayout = null;
	private ImageView imgv_user_menu = null;
	private View menu, content;
	private ImageView imgVChangeVoiceText = null;
	private TextView tvMapIndexcontent = null;
	private TextView tvMapIndexConfirm = null;// 底部选择目的地确认按钮
	private TextView tvMapIndexDistance = null;// 底部选择目的地距离金钱计算
	boolean isVoice = false;// 当前状态是否为录音
	private RelativeLayout rlMapIndexTop;
	private RelativeLayout rlMapIndexBottom;
	private RelativeLayout rlMapIndexChangeEXPWay;
	private boolean isHasEndPlace = false;
	LatLng myLa = null;// 记录我目前的定位
	private boolean locationFirst = true;// 定位打印小蓝点只执行一次
	AMapLocationClient mlocationClient;
	AMapLocationClientOption mLocationOption;
	UserForYifa user;
	private Dialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_index_activity);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		initView();
	}

	/**
	 * 初始化VIEW
	 */
	private void initView() {
		// TODO∏ Auto-generated method stub
		// 侧滑控件
		paneLayout = (SlidingPaneLayout) findViewById(R.id.slidepanel);
		paneLayout.setPanelSlideListener(new PanelSlideListener() {
			@Override
			public void onPanelSlide(View arg0, float arg1) {
				// System.out.println("onPanelSlide");
			}

			@Override
			public void onPanelOpened(View arg0) {
			}

			@Override
			public void onPanelClosed(View arg0) {
			}
		});
		findViewById(R.id.imgv_user_menu).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuFaHuoJiLu).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuJiFen).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuSheZhi).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuWoDe).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuXiaoXi).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuWuLiuFee).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenuZhouBian).setOnClickListener(this);
		findViewById(R.id.tvMapIndexMenufenxiangyoujiang).setOnClickListener(
				this);
		
		
		imgVChangeVoiceText = (ImageView) findViewById(R.id.imgvMapIndexChangeTextVoice);
		imgVChangeVoiceText.setOnClickListener(this);
		tvMapIndexcontent = (TextView) findViewById(R.id.tvMapIndexcontent);
		tvMapIndexcontent.setOnClickListener(this);
		// findViewById(R.id.tvMapIndexYuYue).setOnClickListener(this);
		rlMapIndexTop = (RelativeLayout) findViewById(R.id.rlMapIndexTop);
		rlMapIndexBottom = (RelativeLayout) findViewById(R.id.rlMapIndexBottom);
		rlMapIndexChangeEXPWay = (RelativeLayout) findViewById(R.id.rlMapIndexChangeEXPWay);
		findViewById(R.id.tvMapIndexConfirm).setOnClickListener(this);
		tvMapIndexDistance = (TextView) findViewById(R.id.tvMapIndexDistance);

		final BmobRealTimeData rtd = new BmobRealTimeData();
		// rtd.subTableUpdate("GoodsForYifa");
		rtd.start(this, new ValueEventListener() {
			@Override
			public void onDataChange(JSONObject data) {
				// TODO Auto-generated method stub
				System.out.println("bmob" + "(" + data.optString("action")
						+ ")" + "数据：" + data);
			}

			@Override
			public void onConnectCompleted() {
				// TODO Auto-generated method stub
				System.out.println("bmob" + "连接成功:" + rtd.isConnected());

			}
		});

	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		// myLocationStyle.myLocationIcon(BitmapDescriptorFactory
		// .fromResource(R.drawable.ic_launcher));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int,int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击marker事件监听器
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

		//aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

		// aMap.setMyLocationType()
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));

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
		deactivate();
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



	/**
	 * 定位成功后回调函数 地图上显示marker，
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		System.out.println("onLocationChangedAMapLocation"+aLocation);
		if (mListener != null && aLocation != null) {
			mListener.onLocationChanged(aLocation);// 显示系统小蓝点

			// 执行一次 定位打印小蓝点
//			if (locationFirst) {
//				locationFirst = false;
			aMap.clear();
				Marker marker = aMap.addMarker(new MarkerOptions()
						.anchor(0.5f, 0.5f)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_yuyue_type_start))
						.position(
								new LatLng(aLocation.getLatitude(), aLocation
										.getLongitude()))
						.title("点 我 更 换 发 货 地 ")
						.snippet(
								"当前所在地: " + aLocation.getStreet()
										+ aLocation.getPoiName() + " ")
						.draggable(true));
				marker.showInfoWindow();
				myLa = new LatLng(aLocation.getLatitude(),
						aLocation.getLongitude());
				Config.getInstance().MyPoint = new BmobGeoPoint(aLocation.getLongitude(),
						aLocation.getLatitude());
				Config.getInstance().gpsAddStart = new BmobGeoPoint(aLocation.getLongitude(),
						aLocation.getLatitude());
				Config.getInstance().gpsAddStartStr = aLocation.getStreet()
						+ aLocation.getPoiName();
				Config.getInstance().currentCity = aLocation.getCity();
				// timer3(aLocation);//调用急计时器//服务器提交定位
				if (UserForYifa.getCurrentUser(getApplicationContext(),
						UserForYifa.class) != null) {
					timer10putLocation(aLocation);
				}
			//}

		}
	}

	// 定时器
	public void timer3(final AMapLocation aLocation) { // 设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// 发送服务器
				Bugs p2 = new Bugs();

				p2.save(getApplicationContext(), new SaveListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						// ToastUtil.show(\,
						// info)("添加数据成功，返回objectId为："+p2.getObjectId());
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						ToastUtil.show(getApplicationContext(), "创建数据失败：" + msg
								+ "错误号码" + code + "请反馈");
					}
				});
			}
		}, 1000, 600000);
	}

	// 定时器
	public void timer10putLocation(final AMapLocation aLocation) { // 设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				 user = UserForYifa.getCurrentUser(
						getApplicationContext(), UserForYifa.class);
				if (user != null) {
					// 允许用户使用应用
					// 发送服务器定位信息

					UserForYifa newUser = new UserForYifa();
					newUser.setGpsAdd(new BmobGeoPoint(
							aLocation.getLongitude(), aLocation.getLatitude()));
					newUser.update(getApplicationContext(), user.getObjectId(),
							new UpdateListener() {
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									System.out.println("上传地理位置成功");
								}

								@Override
								public void onFailure(int code, String msg) {
									// TODO Auto-generated method stub
									// toast("更新用户信息失败:" + msg);
									System.out.println("上传地理位置失败"
											+ code + msg);
									FailedlWrite
											.writeCrashInfoToFile("上传地理位置失败"
													+ code + msg);
								}
							});
				} else {
					// 缓存用户对象为空时， 可打开用户注册界面…
					System.out.println("null   ");
				}

			}
		}, 1, 600000*10);
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			//初始化定位
			mlocationClient = new AMapLocationClient(this);
			//初始化定位参数
			mLocationOption = new AMapLocationClientOption();
			mLocationOption.setInterval(1000*60*5);
			//设置定位回调监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
			mLocationOption.setNeedAddress(true);
			//设置定位参数
			mlocationClient.setLocationOption(mLocationOption);

			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.startLocation();//启动定位
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		double latitude = data.getDoubleExtra("weidu", 0.0);
		double latLon = data.getDoubleExtra("jingdu", 0.0);
		String miaoshu = data.getStringExtra("miaoshu");
		switch (resultCode) {
		case SELECT_ADDRESS_WITH_MAP:
			aMap.clear();
			System.out.println(latitude + "通过点击地图选择位置-*-*-*-*" + latLon
					+ miaoshu);
			Marker markerStart = aMap.addMarker(new MarkerOptions()
					.anchor(0.5f, 0.5f)
					.position(new LatLng(latitude, latLon))
					.title(miaoshu)
					.snippet(miaoshu)
					.draggable(true)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_yuyue_type_start)));
			markerStart.showInfoWindow();
			MyMapUtil.changeCamera(aMap, CameraUpdateFactory
					.newCameraPosition(new CameraPosition(new LatLng(latitude,
							latLon), 18, 0, 30)));
			myLa = new LatLng(latitude, latLon);// 更改启动位置
			break;
		case SELECT_ADDRESS_RULE_END_PLACE:// 正常情况下选泽重点
			isHasEndPlace = true;
			String info = data.getStringExtra("info");
			System.out.println(info + miaoshu + aMap + "通过兴趣点搜索-*-*-*-*");
			// MyMapUtil.drawMarkerAndWinWithLon(aMap, latitude, latLon,
			// miaoshu, info, false);
			aMap.clear();
			Marker markerEnd = aMap.addMarker(new MarkerOptions()
					.anchor(0.5f, 0.5f)
					.position(new LatLng(latitude, latLon))
					.title(miaoshu)
					.snippet(info)
					.draggable(true)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_yuyue_type_end)));
			markerEnd.showInfoWindow();
			MyMapUtil.changeCamera(aMap, CameraUpdateFactory
					.newCameraPosition(new CameraPosition(new LatLng(latitude,
							latLon), 18, 0, 30)));
			setBottomRel(isHasEndPlace);
			// System.out.println("------- " + myLa);
			if (myLa != null) {
				float distance = MyMapUtil.circulateDistance(myLa, new LatLng(
						latitude, latLon));
				tvMapIndexDistance.setText("温馨提示:全程" + distance / 1000 * 1.22
						+ "公里,大约花费" + distance / 100000 * 5 + "元");
				aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
						.position(myLa).title("点我更换发货地").snippet("当前:")
						.draggable(true));
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgv_user_menu:// 侧滑菜单
			if(user!=null)
				paneLayout.openPane();
			else{
				getRegDialog();
				finish();
			}
				
			break;
		case R.id.tvMapIndexMenuFaHuoJiLu: // 我的发货记录监听
			startActivity(new Intent().setClass(getApplicationContext(),
					MyJourneyActivity.class));
			break;
		case R.id.tvMapIndexMenuZhouBian: // 周边配送站
			startActivity(new Intent().setClass(getApplicationContext(),
					ZhouBianWuLiuActivity.class));
			break;
		case R.id.tvMapIndexMenuWuLiuFee: // 物流费用查询
			startActivity(new Intent().setClass(getApplicationContext(),
					PayInfoActivity.class));
			break;
		case R.id.tvMapIndexMenuJiFen: // 联系客服
			// ToastUtil.show(getApplicationContext(), "该功能暂未开放");
			final String number = Constants.KEFUTEL;
			// 用intent启动拨打电话
			Builder builder = new Builder(IndexActivity.this);
			builder.setMessage("确认拨打吗？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
									+ number)));
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.dismiss();
						}
					});
			builder.create().show();

		

			break;
		case R.id.tvMapIndexMenuSheZhi: // 我的设置监听
			startActivity(new Intent().setClass(getApplicationContext(),
					MySettingActivity.class));
			break;
		case R.id.tvMapIndexMenuWoDe: // 我的积分监听
			ToastUtil.show(getApplicationContext(), "该功能暂未开放");
			break;
		case R.id.tvMapIndexMenufenxiangyoujiang: // 点击分享有奖
			// // 打开通信录好友列表页面
			ToastUtil.show(getApplicationContext(), "该功能暂未开放");
//			ContactsPage contactsPage = new ContactsPage();
//			contactsPage.show(this);
			break;
		case R.id.tvMapIndexMenuXiaoXi: // 我的小心监听
			break;
		case R.id.imgvMapIndexChangeTextVoice:
			isVoice = !isVoice;
			if (isVoice) {// 语音状态
				imgVChangeVoiceText
						.setBackgroundResource(R.drawable.map_index_channel_text_normal);
				tvMapIndexcontent.setText("马上叫车");
				tvMapIndexcontent.setTextColor(Color.parseColor("#ffffff"));
				tvMapIndexcontent
						.setBackgroundResource(R.drawable.map_index_input_voice);
			} else// 文字状态
			{
				imgVChangeVoiceText
						.setBackgroundResource(R.drawable.map_index_channel_voice_normal);
				tvMapIndexcontent.setText("");
				tvMapIndexcontent.setHint("请输入目的地");
				tvMapIndexcontent.setTextColor(Color.parseColor("#ff8903"));
				tvMapIndexcontent
						.setBackgroundResource(R.drawable.map_index_input_text);
			}
			break;
		case R.id.tvMapIndexcontent: // 点击文本输入框监听
			if (isVoice) {// 语音状态
				startActivity(new Intent().setClass(getApplicationContext(),
						VoiceActivity.class));
			} else {// 进入POI搜索
				if(user==null){
					getRegDialog();
					break;
					}
				Intent intent = new Intent();
				intent.putExtra("tag", POISearchActivity.TAGSELECTENDPLACE);
				POISearchActivity.isStart=false;

				startActivity(intent.setClass(getApplicationContext(),
						POISearchActivity.class));
			}
			break;
		case R.id.tvMapIndexYuYue: // 预约监听
			startActivity(new Intent().setClass(getApplicationContext(),
					YuYueActivity.class));
			break;
		case R.id.tvMapIndexConfirm: // 确认提交
			isHasEndPlace = false;
			setBottomRel(isHasEndPlace);
			break;
		
		default:
			break;
		}
	}

	/**
	 * 监听自定义infowindow窗口的infowindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker) {
		View infoWindow = getLayoutInflater().inflate(
				R.layout.custom_info_window, null);
		LayoutParams lpm = new LayoutParams(300, 144); 

		// infoWindow.setLayoutParams(lpm);
		render(marker, infoWindow);

		// infoWindow.layout(20, 30, 40, 50);
		// infoWindow.layout(l, t, r, b)
		return infoWindow;
	}

	/**
	 * 自定义infowinfow窗口
	 */
	public void render(Marker marker, View view) {
		// ((ImageView) view.findViewById(R.id.badge))
		// .setImageResource(R.drawable.arrow);

		String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.title));
		if (title != null) {
			// SpannableString titleText = new SpannableString(title);
			// titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0,
			// titleText.length(), 0);
			// titleUi.setTextSize(15);
			titleUi.setText("点我更换发货地");

		} else {
			titleUi.setText("");
		}
		String snippet = marker.getSnippet();
		TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		if (snippet != null) {
			// SpannableString snippetText = new SpannableString(snippet);
			// snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
			// snippetText.length(), 0);
			// snippetUi.setTextSize(20);
			// snippetUi.setText(snippetText);
			snippetUi.setText(snippet);
		} else {
			snippetUi.setText("");
		}
	}

	/**
	 * 监听点击infowindow窗口事件回调
	 */
	@Override
	public void onInfoWindowClick(Marker marker) {

		POISearchActivity.isStart = true;
		startActivityForResult((new Intent().setClass(getApplicationContext(),
				POISearchActivity.class)), SELECT_ADDRESS_WITH_MAP);
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		// com.fwh.utils.ToastUtil.show(this, "你点击了infoWindow窗口onMarkerClick" +
		// marker.getTitle());
		return false;
	}



	/**
	 * 设置底部Relative显示隐藏
	 */
	private void setBottomRel(boolean b) {
		if (!b) {
			// TODO Auto-generated method stub
			rlMapIndexTop.setVisibility(View.VISIBLE);
			rlMapIndexBottom.setVisibility(View.GONE);
			rlMapIndexChangeEXPWay.setVisibility(View.VISIBLE);
		} else {
			// TODO Auto-generated method stub
			rlMapIndexTop.setVisibility(View.GONE);
			rlMapIndexBottom.setVisibility(View.VISIBLE);
			rlMapIndexChangeEXPWay.setVisibility(View.GONE);
		}

	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	打开注册
	*/
	private void getRegDialog(){
		// 打开注册页面
					RegisterPage registerPage = new RegisterPage();
					registerPage.setRegisterCallback(new EventHandler() {
						public void afterEvent(int event, int result, Object data) {
							// 解析注册结果
							if (result == SMSSDK.RESULT_COMPLETE) {
								@SuppressWarnings("unchecked")
								HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
								String country = (String) phoneMap.get("country");
								String phone = (String) phoneMap.get("phone");
								// 提交用户信息
								registerUser(country, phone);
							}
						}
					});
					registerPage.show(this);
	}
	
	
	public boolean handleMessage(Message msg) {
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
			// 短信注册成功后，返回MainActivity,然后提示新好友
			if (result == SMSSDK.RESULT_COMPLETE) {
				//Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
			} else {
				((Throwable) data).printStackTrace();
			}
		} else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT){
			if (result == SMSSDK.RESULT_COMPLETE) {
				refreshViewCount(data);
			} else {
				((Throwable) data).printStackTrace();
			}
		}
		return false;
	}
	// 更新，新好友个数
	private void refreshViewCount(Object data){
		int newFriendsCount = 0;
		try {
			newFriendsCount = Integer.parseInt(String.valueOf(data));
		} catch (Throwable t) {
			newFriendsCount = 0;
		}
//		if(newFriendsCount > 0){
//			tvNum.setVisibility(View.VISIBLE);
//			tvNum.setText(String.valueOf(newFriendsCount));
//		}else{
//			tvNum.setVisibility(View.GONE);
//		}
//		if (pd != null && pd.isShowing()) {
//			pd.dismiss();
//		}
	}
	// 弹出加载框
	private void showDialog(){
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		pd = CommonDialog.ProgressDialog(this);
		pd.show();
	}
	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "易发快运安卓用户_" + uid;
		String avatar = MainActivity.AVATARS[id % 12];
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
		System.out.println("MOB短信验证成功");
		
		signUser(phone);
		
		
	}
	/**
	 * 注册用户
	 */
	private void signUser(final String phone){
		UserForYifa user = new UserForYifa();
		user.setUsername(phone);
		user.setPassword("123456");
		user.setIBanlance(0);
		user.signUp(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		        //ToastUtil.show(getApplicationContext(), "注册成功:");
		        startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		    	//ToastUtil.show(getApplicationContext(), code+"注册失败:原因 "+msg+code);
		    	System.out.println("code" + code + (code==202));
		    	if (code == 202) {
		    		//登陆
		    		UserForYifa bu2 = new UserForYifa();
		    		bu2.setUsername(phone);
		    		bu2.setPassword("123456");
		    		bu2.login(getApplicationContext(), new SaveListener() {
		    		    @Override
		    		    public void onSuccess() {
		    		        // TODO Auto-generated method stub
		    		    	startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
		    		    }
		    		    @Override
		    		    public void onFailure(int code, String msg) {
		    		        // TODO Auto-generated method stub
		    		    	System.out.println();
		    		    	ToastUtil.show(getApplicationContext(), "登录失败:原因 "+msg+code);
		    		    }
		    		});
		    		
				}
		    	
		    }
		});
		
	}
	 Handler handler = new Handler(){
		 @Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			//System.out.println("啊啊啊啊啊啊啊啊啊");
			startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
			finish();
		}
	 };


}
