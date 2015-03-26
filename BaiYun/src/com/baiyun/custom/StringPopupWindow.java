package com.baiyun.custom;

import java.util.ArrayList;
import java.util.List;

import com.baiyun.activity.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class StringPopupWindow extends PopupWindow{
	private View contentView;
	private TextView tvTitle;
	private TextView tvCancel;
	
	private ListView listView;
	private List<String> stringList;
	private MyListViewAdapter adapter;
	
	public OnItemClickListener onItemClickListener;
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	public interface OnItemClickListener{
		public void OnItemClick(String string, int position);
	}
	
	public StringPopupWindow(Context context,List<String> sList,String title) {
		super(context);
		this.stringList = sList;
		
		contentView = LayoutInflater.from(context).inflate(R.layout.popupwindow_string_list, null);
		
		tvTitle = (TextView)contentView.findViewById(R.id.tv_title);
		tvTitle.setText(title);
		
		tvCancel = (TextView)contentView.findViewById(R.id.tv_cancel);
		tvCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StringPopupWindow.this.dismiss();
			}
		});
		
		listView = (ListView)contentView.findViewById(R.id.listview);
		if (stringList == null) {
			stringList = new ArrayList<String>();
		}
		adapter = new MyListViewAdapter(context, stringList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				onItemClickListener.OnItemClick(stringList.get(arg2), arg2);
				StringPopupWindow.this.dismiss();
			}
			
		});
		
        // 设置SelectPicPopupWindow的View  
        this.setContentView(contentView);  
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.MATCH_PARENT);  
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.MATCH_PARENT);  
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimationStringPopupwindow); 
	}
	
	public void notifyListChange(List<String> sList){
//		for (int i = 0; i < sList.size(); i++) {
//			System.out.println("====> "+sList.get(i));
//		}
		stringList.clear();
		stringList.addAll(sList);
		adapter.notifyDataSetChanged();
	}
	
    public void showFullScreen(View rootView) { 
        if (!this.isShowing()) {  
            this.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);;  
        } else {  
            this.dismiss();  
        }  
    }
    
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}
	
	private class MyListViewAdapter extends BaseAdapter{
		private Context context;
		private List<String> ss;
		
		public MyListViewAdapter(Context context,List<String> sList) {
			this.context = context;
			this.ss = sList;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ss.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ss.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.popupwindow_string_item, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.tvTitle.setText(ss.get(position));
			
			return convertView;
		}
		
		public final class ViewHolder{
			TextView tvTitle;
		}
	}
}
