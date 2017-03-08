package com.fwh.yifaexp.map;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.fwh.utils.FailedlWrite;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.FindCarAdapter;
import com.fwh.yifaexp.map.adapter.SelectCarAdapter;
import com.fwh.yifaexp.model.CarTypeFind;
import com.fwh.yifaexp.model.UserForYifa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SelectCheXingActivity  extends Activity{
	SelectCarAdapter adapter;
	ListView lvMyJourney ;
	List<CarTypeFind> objecta = new ArrayList<CarTypeFind>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		
		BmobQuery<CarTypeFind> bmobQuery = new BmobQuery<CarTypeFind>();
		bmobQuery.order("flag");
		bmobQuery.setLimit(100);    //获取最接近用户地点的10条数据
		bmobQuery.findObjects(this, new FindListener<CarTypeFind>() {
		    @Override
		    public void onSuccess(List<CarTypeFind> object) {
		        // TODO Auto-generated method stub
		      // ToastUtil.show(getApplicationContext(), "查询成功 " );
		    	objecta = object;
		    	adapter.setList(object);
		    }
		    @Override
		    public void onError(int code, String msg) {
		        // TODO Auto-generated method stub
		    	 ToastUtil.show(getApplicationContext(), "车型失败失败 错误码,请反馈至客服" +msg+code);
		    	 FailedlWrite.writeCrashInfoToFile(" 查询车型失败  错误码" +msg+code);
		    }
		});
		
//	
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_map_findcar);
		((TextView) findViewById(R.id.title)).setText("选择车型");
		
		
		lvMyJourney= (ListView) findViewById(R.id.lvFindCar);
		adapter = new SelectCarAdapter(getApplicationContext(),this);
		lvMyJourney.setAdapter(adapter);
		
		lvMyJourney.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Config.getInstance().ExpCheType = objecta.get(arg2).getCarTitle();
				Config.getInstance().startPrice = objecta.get(arg2).getPriceStarting();
				Config.getInstance().meiPrice =  objecta.get(arg2).getPriceEnd();
				finish();
			}
		});
	}
}
