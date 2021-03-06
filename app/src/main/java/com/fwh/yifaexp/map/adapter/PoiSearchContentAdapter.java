package com.fwh.yifaexp.map.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fwh.utils.Constants;
import com.fwh.utils.FileHelper;
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
	private List<String> fileTemp = new ArrayList<String>();//缓存

	public PoiSearchContentAdapter(Context context, List<String> listString ) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Object object=FileHelper.readObject(Constants.FILE_PATH_SEARCHTEMP+Constants.FILE_NAME_SEARCHTEMP);
		this.array = listString;
		if (object!=null){
			fileTemp =(List<String>)object;
			this.array = fileTemp;
			System.out.println("大小   " + fileTemp.size());
			this.notifyDataSetChanged();
		}

		
	}
	public void setList(List<String>listString){
		this.array = listString;
		this.notifyDataSetChanged();

	}
	public List<String> getList(){
		return this.array;
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
		TextView content = mViewHolder.getView(R.id.tv_item_poi_content);
	        final String temp = array.get(position);
	        try {
				String[] tempArr = temp.split(";");
				title.setText(tempArr[0]);
				content.setText(tempArr[1]);
			} catch (Exception e) {
				// TODO: handle exception
			}

		final TextView collection = mViewHolder.getView(R.id.tv_item_poi_collection);
		boolean isHave= false;
		for (int i= 0; i<fileTemp.size();i++) {
			String str= fileTemp.get(i);
			System.out.println(temp +"包含       " + str);
			if (temp.equals(str))
			{
				isHave = true;
				break;
			}
		}

		System.out.println("是否含有    " + isHave);
		if (isHave)
		{
			collection.setBackgroundResource(R.drawable.btn_collection);
		}else{
			collection.setBackgroundResource(R.drawable.btn_nocollection);
			collection.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					fileTemp.add(temp);
					System.out.println("大小   " + fileTemp.size());
					FileHelper.writeObject(fileTemp,Constants.FILE_PATH_SEARCHTEMP,Constants.FILE_NAME_SEARCHTEMP);
					collection.setBackgroundResource(R.drawable.btn_collection);
				}
			});
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
