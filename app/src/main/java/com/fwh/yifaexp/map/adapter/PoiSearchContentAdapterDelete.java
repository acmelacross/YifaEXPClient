package com.fwh.yifaexp.map.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fwh.utils.Constants;
import com.fwh.utils.FileHelper;
import com.fwh.yifaexp.R;

import java.util.ArrayList;
import java.util.List;

public class PoiSearchContentAdapterDelete extends BaseAdapter {
	private Context context= null;
	private LayoutInflater layoutInflater = null;
	private List<String> array = new ArrayList<String>();
	private List<String> fileTemp = new ArrayList<String>();//缓存

	public PoiSearchContentAdapterDelete(Context context, List<String> listString ) {
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
	                     .inflate(R.layout.list_item_poi_content_delete, null, false);
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
		collection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new AlertDialog.Builder(context).setTitle("确认要删除吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 点击“确认”后的操作
								fileTemp.remove(temp);
								//fileTemp.add(temp);
								System.out.println("大小   " + fileTemp.size());
								FileHelper.writeObject(fileTemp,Constants.FILE_PATH_SEARCHTEMP,Constants.FILE_NAME_SEARCHTEMP);
								PoiSearchContentAdapterDelete.this.notifyDataSetChanged();
							}
						})
						.setNegativeButton("返回", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 点击“返回”后的操作,这里不设置没有任何操作
							}
						}).show();


			}
		});




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
