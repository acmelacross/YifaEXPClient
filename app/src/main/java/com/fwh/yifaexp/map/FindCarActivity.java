package com.fwh.yifaexp.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.fwh.utils.FailedlWrite;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.CheckPermissionsActivity;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.FindCarAdapter;
import com.fwh.yifaexp.model.GoodsForYifa;
import com.fwh.yifaexp.model.UserForYifa;

public class FindCarActivity extends CheckPermissionsActivity {
	ListView lvMyJourney = null;
	List<GoodsForYifa> list = new ArrayList<GoodsForYifa>();
	FindCarAdapter adapter;;
	public static GoodsForYifa goodTemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_map_findcar);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.title)).setText("精准找车");
		lvMyJourney = (ListView) findViewById(R.id.lvFindCar);
		adapter = new FindCarAdapter(getApplicationContext(),this);
		lvMyJourney.setAdapter(adapter);

//		lvMyJourney.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
//				if (list.get(position).getiGoodState() != Constact.EXP_GoodStateCancel) {
//					goodTemp = list.get(position);
//					// System.out.println(goodTemp.getObjectId()+"*-*-*-*-");
//					// startActivity(new
//					// Intent().setClass(getApplicationContext(),
//					// MapHuoWuActivity.class));
//				} else
//					ToastUtil.show(getApplicationContext(), "改单已取消,不能查看详细内容");
//
//			}
//		});
		// 返回按钮
		findViewById(R.id.imgvPoiTitleBack).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	private void initData() {
		UserForYifa user = BmobUser.getCurrentUser(this, UserForYifa.class);
		BmobQuery<UserForYifa> bmobQuery = new BmobQuery<UserForYifa>();
		bmobQuery.addWhereNear("gpsAdd", user.getGpsAdd());
		bmobQuery.addWhereEqualTo("type", 2);
		bmobQuery.addWhereNotEqualTo("isBusy", true);
		bmobQuery.setLimit(10);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(this, new FindListener<UserForYifa>() {
		    @Override
		    public void onSuccess(List<UserForYifa> object) {
		        // TODO Auto-generated method stub
		      // ToastUtil.show(getApplicationContext(), "查询成功 " );
		       adapter.setList(object);
		       adapter.notifyDataSetChanged();
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	 ToastUtil.show(getApplicationContext(), "查询失败 错误码,请反馈至客服" +msg+code);
		    	 FailedlWrite.writeCrashInfoToFile("精准找车附近司机失败 错误码" +msg+code);
		    }
		});
	}
}
