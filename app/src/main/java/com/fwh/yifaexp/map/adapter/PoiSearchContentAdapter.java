package com.fwh.yifaexp.map.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fwh.yifaexp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoiSearchContentAdapter extends BaseAdapter {
	private Context context= null;
	private LayoutInflater layoutInflater = null;
	private List<String> array = new ArrayList<String>();

	public PoiSearchContentAdapter(Context context, List<String> listString ) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.array = listString;
		
	}
	public void setList(List<String>listString){
		this.array = listString;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  if (convertView == null) { 
	             convertView = LayoutInflater.from(context) 
	                     .inflate(R.layout.list_item_poi_content, null, false); 
	         } 
		  ViewHolder mViewHolder = new ViewHolder(convertView); 
	          mViewHolder = ViewHolder.get(convertView); 
	        TextView title = mViewHolder.getView(R.id.tv_item_poi_title); 
	        String temp = array.get(position);
	        try {
				String[] tempArr = temp.split(";");
				title.setText(tempArr[0]);
				TextView content = mViewHolder
						.getView(R.id.tv_item_poi_content);
				content.setText(tempArr[1]);
			} catch (Exception e) {
				// TODO: handle exception
			}
		return convertView;
	}

	@Override
	public int getCount() {
		//System.out.println("aFoodpoition" +aFoodpoition + "--"+ a[aFoodpoition][0]);
		return array.size();
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
