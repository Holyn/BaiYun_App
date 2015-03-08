package com.baiyun.activity.life;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.vo.parcelable.LifeGuidePar;

public class LGuideFragment extends BaseFragment {
	private SchoolLifeHttpUtils httpUtils;
	
	private ListView listView;
	private ListViewAdapter adapter;
	private List<LifeGuidePar> guidePars = new ArrayList<LifeGuidePar>();
	
	public static LGuideFragment newInstance() {
		return new LGuideFragment();
	}

	public LGuideFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new SchoolLifeHttpUtils(getActivity());
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((LGuideActivity)getActivity()).setTopBarTitle("服务指南");
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((LGuideActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		listView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new ListViewAdapter(getActivity(), guidePars);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((LGuideActivity)getActivity()).showWebViewFragment2(guidePars.get(position).getContentUrl(), "服务指南");
				
			}
		});
	}
	
	private void getNetData(){
		((LGuideActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getGuide(new SchoolLifeHttpUtils.onGetGuideListener() {
			
			@Override
			public void onGetGuide(List<LifeGuidePar> pars) {
				if (getActivity() != null) {
					((LGuideActivity)getActivity()).setLoadingBarGone();
				}
				if (pars != null) {
					guidePars.addAll(pars);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class ListViewAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<LifeGuidePar> guidePars;

		public ListViewAdapter(Context context, List<LifeGuidePar> guidePars) {
			inflater = LayoutInflater.from(context);
			this.guidePars = guidePars;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return guidePars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return guidePars.get(position);
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
				convertView = inflater.inflate(R.layout.list_item_life_guide, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			LifeGuidePar guidePar = guidePars.get(position);
			holder.tvTitle.setText(guidePar.getTitle());
			return convertView;
		}

		public final class ViewHolder {
			public TextView tvTitle;
		}

	}

}
