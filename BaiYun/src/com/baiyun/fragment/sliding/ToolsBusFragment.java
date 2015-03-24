package com.baiyun.fragment.sliding;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class ToolsBusFragment extends BaseFragment{
	private VoHttpUtils httpUtils;
	
	private ListView listView;
	private List<Vo1Par> busList = new ArrayList<Vo1Par>();
	private MyListAdapter adapter;
	
	public static ToolsBusFragment newInstance() {
		return new ToolsBusFragment();
	}
	
	public ToolsBusFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new VoHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.listview_common;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		getNetData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity) getActivity()).setLoadingBarGone();
	}

	private void initView(View rootView){
		listView = (ListView)rootView.findViewById(R.id.listview);
		adapter = new MyListAdapter(getActivity(), busList);
		listView.setAdapter(adapter);
	}
	
	private void getNetData(){
		((MainActivity) getActivity()).setLoadingBarVisible();
		httpUtils.getVo1List(HttpURL.R_TOOLS_BUS, new VoHttpUtils.OnGetVo1ListListener() {
			
			@Override
			public void onGetVo1List(List<Vo1Par> vo1Pars) {
				// TODO Auto-generated method stub
				if (getActivity() != null) {
					((MainActivity) getActivity()).setLoadingBarGone();
				}
				if (vo1Pars != null) {
					busList.addAll(vo1Pars);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class MyListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<Vo1Par> list;

		public MyListAdapter(Context context, List<Vo1Par> list) {
			inflater = LayoutInflater.from(context);
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
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
				convertView = inflater.inflate(R.layout.list_item_tra_search, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvBrief = (TextView)convertView.findViewById(R.id.tv_brief);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Vo1Par vo1 = list.get(position);
			holder.tvTitle.setText(vo1.getTitle());
			holder.tvBrief.setText(vo1.getBrief());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public TextView tvTitle;
			public TextView tvBrief;
		}
		
	}
}
