package com.fwh.yifaexp.MyUserCenter;

import com.fwh.yifaexp.R;

import cn.smssdk.gui.ContactsPage;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySettingActivity extends Activity {
TextView tvMySettingExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_setting);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		((TextView)findViewById(R.id.title)).setText("设置");
		//常用地址
		RelativeLayout rlSettChangYong = (RelativeLayout)findViewById(R.id.rlSettChangYong);
		rlSettChangYong.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				startActivity(new Intent().setClass(getApplicationContext(), ChangYongActivity.class));
			}
		});
		
		//意见反馈
		RelativeLayout rlSettYiJian = (RelativeLayout)findViewById(R.id.rlSettYiJian);
		rlSettYiJian.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				startActivity(new Intent().setClass(getApplicationContext(), MyFeedBackActivity.class));
			}
		});
		//告诉朋友
		RelativeLayout rlSettPengYou = (RelativeLayout)findViewById(R.id.rlSettPengYou);
		rlSettPengYou.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				ContactsPage contactsPage = new ContactsPage();
				contactsPage.show(MySettingActivity.this);
			}
		});
		//业务合作
		RelativeLayout rlSettGuanYu = (RelativeLayout)findViewById(R.id.rlSettGuanYu);
		rlSettGuanYu.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
//				new AlertDialog.Builder(MySettingActivity.this)
//				.setTitle("关于我们 ")
//				.setMessage("易发快运  \n网站꣺www.baidu.com\n ")
//						.show();
				startActivity(new Intent().setClass(getApplicationContext(), AboutUsActivity.class));

			}
		});

		//技术合作
		RelativeLayout rlSettGuanYuWomen = (RelativeLayout)findViewById(R.id.rlSettGuanYuWomen);
		rlSettGuanYuWomen.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
//				new AlertDialog.Builder(MySettingActivity.this)
//				.setTitle("关于我们 ")
//				.setMessage("易发快运  \n网站꣺www.baidu.com\n ")
//						.show();
				startActivity(new Intent().putExtra("isSoft",true).setClass(getApplicationContext(), AboutUsActivity.class));

			}
		});


		tvMySettingExit = (TextView) findViewById(R.id.tvMySettingExit);
		tvMySettingExit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				moveTaskToBack(true);       
			}
		});
		
		}
	
	
}
