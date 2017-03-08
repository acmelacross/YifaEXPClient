package com.fwh.yifaexp.map;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.fwh.utils.FailedlWrite;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.ExpCompanyAdapter;
import com.fwh.yifaexp.map.adapter.FindCarAdapter;
import com.fwh.yifaexp.map.adapter.SelectCarAdapter;
import com.fwh.yifaexp.model.CarTypeFind;
import com.fwh.yifaexp.model.ExpCompany;
import com.fwh.yifaexp.model.UserForYifa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SelectWuLiuActivity  extends Activity{
	ExpCompanyAdapter adapter;
	ListView lvMyJourney ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

		BmobQuery<ExpCompany> bmobQuery = new BmobQuery<ExpCompany>();
		//bmobQuery.order("flag");
		bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(this, new FindListener<ExpCompany>() {
		    @Override
		    public void onSuccess(List<ExpCompany> object) {
		        // TODO Auto-generated method stub
		       ToastUtil.show(getApplicationContext(), "查询成功 " +object);
		    	adapter.setList(object);
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	 ToastUtil.show(getApplicationContext(), "车型失败失败 错误码,请反馈至客服" +msg+code);
		    	 FailedlWrite.writeCrashInfoToFile(" 查询车型失败  错误码" +msg+code);
		    }
		});
		
	
		
//		ExpCompany e = new ExpCompany();
//		e.setMobileNums("133123456");//36.7150914880,119.2127367384
//		e.setPosition(new BmobGeoPoint(119.2127367384, 36.7150914880));
//		e.setPrice(15);
//		e.setDao(true);
//		e.save(this, new SaveListener() {
//			@Override
//			public void onSuccess() {
//				// TODO Auto-generated method stub
//				System.out.println("onSuccess");
//			}
//			
//			@Override
//			public void onFailure(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_map_findcar);
		((TextView) findViewById(R.id.title)).setText("选择物流公司");
		
		
		lvMyJourney= (ListView) findViewById(R.id.lvFindCar);
		adapter = new ExpCompanyAdapter(getApplicationContext(),this);
		lvMyJourney.setAdapter(adapter);
		lvMyJourney.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Config.getInstance().EXPCompany = "物流公司"+arg2;
				finish();
			}
		});
		
	}
}
