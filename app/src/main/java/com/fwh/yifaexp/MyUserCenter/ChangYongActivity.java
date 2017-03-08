package com.fwh.yifaexp.MyUserCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fwh.utils.Constants;
import com.fwh.utils.FileHelper;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.PoiSearchContentAdapter;
import com.fwh.yifaexp.map.adapter.PoiSearchContentAdapterDelete;
import com.fwh.yifaexp.model.Address;

public class ChangYongActivity extends Activity {

	private ArrayList<String> array = new ArrayList<String>();
	PoiSearchContentAdapterDelete adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chang_yong);
		initView();
	}
	private void initView(){
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		 ((TextView)findViewById(R.id.title)).setText("编辑常用地址");
		ListView lvChangYong = (ListView) this.findViewById(R.id.lvChangYong);
		 adapter = new PoiSearchContentAdapterDelete(ChangYongActivity.this,
				 new ArrayList<String>());;
		// getData();
		lvChangYong.setAdapter(adapter);
		lvChangYong.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
//				POISearchActivity2.index = position;
//				startActivity(new Intent().setClass(getApplicationContext(), POISearchActivity2.class));
			}
		});
		 
	}
//	private ArrayList<String> getData(){
//		Object o = FileHelper.readObject(Constants.SDPath +"address/address");
//		if (o!=null) {
//			HashMap<String, Address> addressMap = (HashMap<String, Address>)o;
//			Set set = addressMap.keySet();
//			for(Iterator iter= set.iterator();iter.hasNext();){
//			     String key = (String)iter.next();
//			     Address value = (Address)addressMap.get(key);
//			     System.out.println(key + "    "+addressMap.get(key).getAddressName());
//			     array.add(addressMap.get(key).getAddressName());
//			     adapter.notifyDataSetChanged();
//			}
//		}else{
//			array = new ArrayList<String>();
//			for (int i = 0; i < 5; i++) {
//				array.add("点击此处输入常用地址"+(i+1));
//			}
//		}
//		return array;
//
//	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("resume");
		//
		//getData();
		//adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData());
	}

}
