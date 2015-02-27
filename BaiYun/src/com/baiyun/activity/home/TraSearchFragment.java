package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeJobHttpUtils;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.HomeRecruitPar;
import com.baiyun.vo.parcelable.Vo2Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class TraSearchFragment extends BaseFragment{
	private PullToRefreshListView refreshListView;
	private VoHttpUtils httpUtils;
	
	private List<Vo2Par> vo2Pars = new ArrayList<Vo2Par>();
	private ListAdapter listAdapter;
	private int page = 1;
	
	public static TraSearchFragment newInstance() {
		return new TraSearchFragment();
	}

	public TraSearchFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new VoHttpUtils(getActivity());
		
		initListView(rootView);
		
		((TrafficActivity) getActivity()).setLoadingBarVisible();
		getData(page);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((TrafficActivity) getActivity()).setLoadingBarGone();
	}
	
	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new ListAdapter(getActivity(), vo2Pars);
		refreshListView.getRefreshableView().setAdapter(listAdapter);
		refreshListView.setMode(Mode.PULL_FROM_END);
		refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getData(page);
			}
		});
	}
	
	private void getData(final int page){
		httpUtils.getVo2(HttpURL.TRAFFIC_SEARCH, page, new VoHttpUtils.OnGetVo2Listener() {
			
			@Override
			public void onGetVo2(List<Vo2Par> vo2Pars) {
				if (page == 1) {
					if (getActivity() != null) {
						((TrafficActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (vo2Pars != null) {
					TraSearchFragment.this.vo2Pars.addAll(vo2Pars);
					listAdapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
	}
	
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<Vo2Par> list;

		public ListAdapter(Context context, List<Vo2Par> list) {
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
			
			Vo2Par vo2 = list.get(position);
			holder.tvTitle.setText(vo2.getTitle());
			holder.tvBrief.setText(vo2.getBrief());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public TextView tvTitle;
			public TextView tvBrief;
		}
		
	}

}
