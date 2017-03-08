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
import android.widget.ImageView;
import android.widget.TextView;

import com.fwh.utils.MyMapUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.Config;
import com.fwh.yifaexp.model.ExpCompany;
import com.fwh.yifaexp.model.UserForYifa;

public class ExpCompanyAdapter extends BaseAdapter {
	private Context context= null;
	//private LayoutInflater layoutInflater = null;
	private Activity thisAc;
	List<ExpCompany> list = new ArrayList<ExpCompany>();

	public ExpCompanyAdapter(Context context ,Activity thisAc) {
		//System.out.println("111111111");
		this.context = context;
		this.thisAc = thisAc;
//		layoutInflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//this.list = listString;
		//System.out.println("3333333");
		
	}
	public void setList(List<ExpCompany>  listString){
		this.list = listString;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		  if (convertView == null) { 
	             convertView = LayoutInflater.from(context) 
	                     .inflate(R.layout.list_item_wuliu_company, null, false); 
	         } 
		  ViewHolder mViewHolder = new ViewHolder(convertView); 
	          mViewHolder = ViewHolder.get(convertView); 
	          
	    	      	TextView tv_listitem_juli = mViewHolder
	    	    			.getView(R.id.tv_listitem_juli);
	    	      	tv_listitem_juli.setText("12.3km");
	    	      	//电话
	    	      		TextView tv_listitem_mobile = mViewHolder
	    	    			.getView(R.id.tv_listitem_mobile);
	    	      		tv_listitem_mobile.setText(list.get(position).getMobileNums());
	    	      		//价格
	    	      		TextView tv_listitem_price = mViewHolder
		    	    			.getView(R.id.tv_listitem_price);
	    	      		tv_listitem_price.setText(list.get(position).getPrice()+"元/公里");
	    	      		//目前离当前距离
	    	      		TextView tv_listitem_daofu = mViewHolder
		    	    			.getView(R.id.tv_listitem_daofu);
	    	      		if (list.get(position).isDao()) {
	    	      			tv_listitem_daofu.setBackgroundResource(R.drawable.ic_order_huodaofukuan);
						}else{
							tv_listitem_daofu.setBackgroundResource(R.drawable.ic_order_buhuodaofukuan);
						}
	    	      		
	    	      		
	    	      		//ImageView tv_listitem_img = mViewHolder.getView(R.id.tv_listitem_img);
	    	      		
	    	      		
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
