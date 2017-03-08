package com.fwh.yifaexp.MyUserCenter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.fwh.utils.Constants;
import com.fwh.utils.FailedlWrite;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.MyJourneyAdapter;
import com.fwh.yifaexp.model.GoodsForYifa;
import com.fwh.yifaexp.model.UserForYifa;

public class MyJourneyActivity extends Activity {
	ListView lvMyJourney = null;
	List<GoodsForYifa> list = new ArrayList<GoodsForYifa>();
	MyJourneyAdapter adapter ;;
	public static GoodsForYifa goodTemp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_my_journey);
		initView();
		initData();
	}
private void initView() {
	// TODO Auto-generated method stub
	((TextView)findViewById(R.id.title)).setText("发货记录");
	lvMyJourney = (ListView)findViewById(R.id.lvMyJourney);
	adapter= new MyJourneyAdapter(getApplicationContext());
	lvMyJourney.setAdapter(adapter);
	
	lvMyJourney.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			if (list.get(position).getiGoodState() != Constants.EXP_GoodStateCancel){
				goodTemp = list.get(position);
				System.out.println(goodTemp.getObjectId()+"*-*-*-*-");
				startActivity(new Intent().setClass(getApplicationContext(), MapHuoWuActivity.class));
			}else
				ToastUtil.show(getApplicationContext(), "改单已取消,不能查看详细内容");
			
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

private void initData(){
	UserForYifa user = BmobUser.getCurrentUser(this, UserForYifa.class);
	BmobQuery<GoodsForYifa> query = new BmobQuery<GoodsForYifa>();
	query.addWhereEqualTo("user", user);    // 查询当前用户的所有帖子
	query.order("-updatedAt");
	query.include("userDriver");
	//query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
	query.findObjects(this, new FindListener<GoodsForYifa>() {
	    @Override
	    public void onSuccess(List<GoodsForYifa> object) {
	        // TODO Auto-generated method stub
	       // System.out.println(object.size()+"aaaaaaaaaaaaaa"+object.get(0).getUserDriver());
	        list =object;
	        adapter.setList(list);
	       // adapter.notifyDataSetChanged();
	        adapter.notifyDataSetChanged();
	    }

	    @Override
	    public void onError(int code, String msg) {
	        // TODO Auto-generated method stub
	    	// System.out.println("bbbbbbbbbbbbbbbbb");
	    	FailedlWrite.writeCrashInfoToFile("客户端查询订单失败"+code+msg);
	    	
	    }
	});
}
}
