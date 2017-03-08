package com.fwh.yifaexp.map.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fwh.yifaexp.R;
import com.fwh.yifaexp.model.GoodsForYifa;

public class MyJourneyAdapter extends BaseAdapter {
	private Context context= null;
	private LayoutInflater layoutInflater = null;
	private List<String> array = new ArrayList<String>();

	List<GoodsForYifa> list = new ArrayList<GoodsForYifa>();

	public MyJourneyAdapter(Context context ) {
		//System.out.println("111111111");
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//this.list = listString;
		//System.out.println("3333333");
		
	}
	public void setList(List<GoodsForYifa>  listString){
		this.list = listString;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  if (convertView == null) { 
	             convertView = LayoutInflater.from(context) 
	                     .inflate(R.layout.list_item_my_journey, null, false); 
	         } 
		  ViewHolder mViewHolder = new ViewHolder(convertView); 
	          mViewHolder = ViewHolder.get(convertView); 
	          //System.out.println("---"+position);
	      	TextView content = mViewHolder
	    			.getView(R.id.tvMyJourneyTime);
	    	          content.setText(list.get(position).getCreatedAt());
	    	      	TextView tvMyJourneyState = mViewHolder
	    	    			.getView(R.id.tvMyJourneyState);
	    	      	//System.out.println("list.get(position).getiGoodState()"+list.get(position).getObjectId());
	    	      	switch (list.get(position).getiGoodState()) {//货物当前状态 0废弃 1接单中 2配送中 3已抵达  
	    			case 0:
	    				tvMyJourneyState.setText("已取消");
	    				break;
	    			case 1:
	    				tvMyJourneyState.setText("接单中");
	    				break;
	    			case 2:
	    				tvMyJourneyState.setText("派送中");
	    				break;
	    			default:tvMyJourneyState.setText("已抵达");
	    				break;
	    			}
	    	      	TextView tvMyJourneyStartPlace = mViewHolder
	    	    			.getView(R.id.tvMyJourneyStartPlace);
	    	      	tvMyJourneyStartPlace.setText(list.get(position).getGpsAddStartStr());
	    	      		TextView tvMyJourneyEndPlace = mViewHolder
	    	    			.getView(R.id.tvMyJourneyEndPlace);
	    	      		tvMyJourneyEndPlace.setText(list.get(position).getGpsAddFinishStr());
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
