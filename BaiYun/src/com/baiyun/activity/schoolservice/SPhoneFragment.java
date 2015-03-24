package com.baiyun.activity.schoolservice;

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
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class SPhoneFragment extends BaseFragment{
	private VoHttpUtils httpUtils;
	
	private ListView listView;
	private ListViewAdapter adapter;
	private List<Vo1Par> phoneList = new ArrayList<Vo1Par>();
	
	public static SPhoneFragment newInstance() {
		return new SPhoneFragment();
	}
	
	public SPhoneFragment() {
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
	
	private void initView(View rootView){
		listView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new ListViewAdapter(getActivity(), phoneList);
		listView.setAdapter(adapter);
		
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				
//			}
//		});
	}
	
	private void getNetData(){
		httpUtils.getVo1List(HttpURL.S_PHONE, new VoHttpUtils.OnGetVo1ListListener() {
			
			@Override
			public void onGetVo1List(List<Vo1Par> vo1Pars) {
				// TODO Auto-generated method stub
				if (vo1Pars != null) {
					phoneList.addAll(vo1Pars);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private class ListViewAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<Vo1Par> vo1Pars;

		public ListViewAdapter(Context context, List<Vo1Par> vo1Pars) {
			inflater = LayoutInflater.from(context);
			this.vo1Pars = vo1Pars;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return vo1Pars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return vo1Pars.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.list_item_s_phone, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Vo1Par vo1Par = vo1Pars.get(position);
			holder.tvTitle.setText(vo1Par.getTitle()+":"+vo1Par.getBrief());
			return convertView;
		}

		public final class ViewHolder {
			public TextView tvTitle;
		}

	}

}
