package com.fwh.yifaexp.map;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fwh.commons.city.CityList;
import com.fwh.commons.time.SlideDateTimeListener;
import com.fwh.commons.time.SlideDateTimePicker;
import com.fwh.yifaexp.R;

public class YuYueActivity extends FragmentActivity implements OnClickListener {
	private Button btn;
	private SimpleDateFormat mFormatter = new SimpleDateFormat(
			"MMMM dd yyyy hh:mm aa");
	TextView ztempTV = null;
	TextView tvYuyueStart = null;
	TextView tvYuyueEnd = null;
	TextView tvYuyueCitySelcet = null;
	public static final int TAGSELECTCITY = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yu_yue);
	
		ztempTV = (TextView) findViewById(R.id.ztempTV);
		ztempTV.setOnClickListener(this);
		tvYuyueStart = (TextView) findViewById(R.id.tvYuyueStart);
		tvYuyueStart.setOnClickListener(this);
		tvYuyueEnd = (TextView) findViewById(R.id.tvYuyueEnd);
		tvYuyueEnd.setOnClickListener(this);
		tvYuyueCitySelcet= (TextView) findViewById(R.id.tvYuyueCitySelcet);
		tvYuyueCitySelcet.setOnClickListener(this);
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.ztempTV:
			new SlideDateTimePicker.Builder(getSupportFragmentManager())
			.setListener(listener).setInitialDate(new Date())
			// .setMinDate(minDate)
			// .setMaxDate(maxDate)
			// .setIs24HourTime(true)
			// .setTheme(SlideDateTimePicker.HOLO_DARK)
			.setIndicatorColor(Color.parseColor("#ff8903")).build().show();
			break;
		case R.id.tvYuyueStart:
    		intent.putExtra("tag", POISearchActivity.TAGYUYUESTARTPLACE);
    		startActivityForResult(intent.setClass(getApplicationContext(), POISearchActivity.class), POISearchActivity.TAGYUYUESTARTPLACE);
			break;
		case R.id.tvYuyueEnd:
    		intent.putExtra("tag", POISearchActivity.TAGYUYUEENDPLACE);
    		startActivityForResult(intent.setClass(getApplicationContext(), POISearchActivity.class), POISearchActivity.TAGYUYUEENDPLACE);
			break;
		case R.id.tvYuyueCitySelcet:
			startActivity(new Intent().setClass(getApplicationContext(), CityList.class));
			break;
			
		default:
			break;
		}
	}

	/*
	 * 预约时间选择
	 */
	private SlideDateTimeListener listener = new SlideDateTimeListener() {
		@Override
		public void onDateTimeSet(Date date) {
			Toast.makeText(YuYueActivity.this,
					"您的预约时间为" + mFormatter.format(date), Toast.LENGTH_SHORT)
					.show();
		}

		// Optional cancel listener
		@Override
		public void onDateTimeCancel() {
			Toast.makeText(YuYueActivity.this, "取消选择", Toast.LENGTH_SHORT)
					.show();
		}
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 double latitude=data.getDoubleExtra("weidu",0.0);
		  double latLon=data.getDoubleExtra("jingdu",0.0);
       String miaoshu=data.getStringExtra("miaoshu");
		switch (resultCode) {
		case POISearchActivity.TAGYUYUESTARTPLACE://预约发货地点回调
			 //******************z发送服务器 
			break;
		case POISearchActivity.TAGYUYUEENDPLACE://预约发货结束地点回调
			
			break;
		case YuYueActivity.TAGSELECTCITY://预约发货选择城市
		
			break;
		default:
			break;
		}
	};
}
