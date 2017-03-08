package com.fwh.yifaexp.map.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fwh.yifaexp.R;
import com.fwh.yifaexp.model.CarTypeFind;

public class SelectCarAdapter extends BaseAdapter {
	private Context context= null;
	private Activity thisAc;
	List<CarTypeFind> list = new ArrayList<CarTypeFind>();

	public SelectCarAdapter(Context context ,Activity thisAc) {
		this.context = context;
		this.thisAc = thisAc;

	}
	public void setList(List<CarTypeFind>  listString){
		this.list = listString;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		  if (convertView == null) { 
	             convertView = LayoutInflater.from(context) 
	                     .inflate(R.layout.list_item_select_chexing, null, false); 
	         } 
		  ViewHolder mViewHolder = new ViewHolder(convertView); 
	          mViewHolder = ViewHolder.get(convertView); 
	    TextView tv_select_che_title =mViewHolder
    			.getView(R.id.tv_select_che_title);
	    TextView tv_select_che_zaizhong =mViewHolder
    			.getView(R.id.tv_select_che_zaizhong);
	    TextView tv_select_changkuan =mViewHolder
    			.getView(R.id.tv_select_changkuan);
	    TextView tv_select_chaolichengshu =mViewHolder
    			.getView(R.id.tv_select_chaolichengshu);
	    String titleStr = list.get(position).getCarTitle() +" /起步价: " + list.get(position).getPriceStarting()+"元(5公里)";
	    SpannableString spanText = new SpannableString(titleStr);
	    spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#00cc00")), 0, titleStr.indexOf("/起步价"),
	    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    spanText.setSpan(new RelativeSizeSpan(1.2f), 0, titleStr.indexOf("/起步价"),
	    		Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    spanText.setSpan(new ForegroundColorSpan(Color.RED),  titleStr.indexOf(":")+1, titleStr.indexOf("元"),
	    	    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	    tv_select_che_title.setText(spanText);
	    if (list.get(position).getLoad()>1) {
	    	  tv_select_che_zaizhong.setText("载重 : "+list.get(position).getLoad()+"吨");
		}else{
			 tv_select_che_zaizhong.setText("载重 : "+list.get(position).getLoad()*1000+"公斤");
		}
	  
	    tv_select_changkuan.setText("长*宽*高 : "+list.get(position).getType()+"m");
	    tv_select_chaolichengshu.setText("超公里费 : " +list.get(position).getPriceEnd()+"元/公里");
	    
	    
	   ImageView iv_select_che_icon =mViewHolder
   			.getView(R.id.iv_select_che_icon);
	  switch (position) {
	case 0:
		 iv_select_che_icon.setBackgroundResource(R.drawable.scooter);
		break;
	case 1:
		 iv_select_che_icon.setBackgroundResource(R.drawable.ic_truck_sanlunche);
		break;
	case 2:
		 iv_select_che_icon.setBackgroundResource(R.drawable.ic_truck_mianbao);
		break;
	case 3:
		 iv_select_che_icon.setBackgroundResource(R.drawable.ic_truck_xiaoxing);
		break;
	case 4:
		 iv_select_che_icon.setBackgroundResource(R.drawable.ic_truck_zhongxinghuoche);
		break;
	case 5:
		 iv_select_che_icon.setBackgroundResource(R.drawable.ic_truck_dahuo);
		break;

	default:
		break;
	}
	   
	   ImageView iv_select_car_select =mViewHolder
	   			.getView(R.id.iv_select_car_select);
	   
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
