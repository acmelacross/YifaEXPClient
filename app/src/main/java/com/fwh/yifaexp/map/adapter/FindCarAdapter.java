package com.fwh.yifaexp.map.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.fwh.utils.MyMapUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.Config;
import com.fwh.yifaexp.model.UserForYifa;

public class FindCarAdapter extends BaseAdapter {
	private Context context= null;
	//private LayoutInflater layoutInflater = null;
	private Activity thisAc;
	List<UserForYifa> list = new ArrayList<UserForYifa>();

	public FindCarAdapter(Context context ,Activity thisAc) {
		//System.out.println("111111111");
		this.context = context;
		this.thisAc = thisAc;
//		layoutInflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//this.list = listString;
		//System.out.println("3333333");
		
	}
	public void setList(List<UserForYifa>  listString){
		this.list = listString;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		  if (convertView == null) { 
	             convertView = LayoutInflater.from(context) 
	                     .inflate(R.layout.list_item_findcar, null, false); 
	         } 
		  ViewHolder mViewHolder = new ViewHolder(convertView); 
	          mViewHolder = ViewHolder.get(convertView); 
	    	      	TextView category_name = mViewHolder
	    	    			.getView(R.id.category_name);
	    	      	category_name.setText(list.get(position).getCarNum());
	    	      	//火车详细信息
	    	      		TextView tvFindCarInfo = mViewHolder
	    	    			.getView(R.id.tvFindCarInfo);
	    	      		tvFindCarInfo.setText(list.get(position).getCarInfo());
	    	      		//火车所在地址
	    	      		TextView tvFindCarAddress = mViewHolder
		    	    			.getView(R.id.tvFindCarAddress);
	    	      		tvFindCarAddress.setText(list.get(position).getGpsAddStr());
	    	      		//目前离当前距离
	    	      		TextView tvFindCarDistance = mViewHolder
		    	    			.getView(R.id.tvFindCarDistance);
	    	      		
	    	      		LatLng start = new LatLng(Config.getInstance().MyPoint.getLatitude(), Config.getInstance().MyPoint.getLongitude());
	    	      		LatLng end = new LatLng(list.get(position).getGpsAdd().getLatitude(), list.get(position).getGpsAdd().getLongitude());
	    	      		int mile = (int) MyMapUtil.circulateDistance(start, end);
	    	      		tvFindCarDistance.setText("距离约"+mile/1000+"公里");
	    	      		//拨打电话
	    	      		TextView tvFindCarCall = mViewHolder
    	    			.getView(R.id.tvFindCarCall);
	    	      		tvFindCarCall.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
					                //用intent启动拨打电话  
					                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+list.get(position).getUsername()));  
					                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					                context.startActivity(intent);  
							}
						});
	    	      		//指定货车发货
	    	      		TextView tvFindCarTui = mViewHolder
    	    			.getView(R.id.tvFindCarTui);
	    	      		tvFindCarTui.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Config.getInstance().isJingZhun = true;
								Config.getInstance().isJingZhunDriver= list.get(position);
								thisAc.finish();
							}
						});
	    		return convertView;
	}

	@Override
	public int getCount() {
		//System.out.println("aFoodpoition" +aFoodpoition + "--"+ a[aFoodpoition][0]);
		//return array.size();
		return list.size() ;
	}

	@Override
	public Object getItem(int position) {
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		System.out.println("getViewgetViewgetViewgetViewgetView");
//		//ViewHolder viewHolder = null;
//		if (convertView == null) {
//			convertView = layoutInflater.inflate(R.layout.list_item_poi_content, null);
//			
//		} else {
//			//viewHolder = (ViewHolder) convertView.getTag();
//		}
//		System.out.println("getViewgetViewgetViewgetViewgetView");
//		return convertView;
//	}



}
