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
import com.baiyun.httputils.HomeJobHttpUtils;
import com.baiyun.vo.parcelable.HomeRecruitPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class JobRecruitFragment extends BaseFragment{
	private PullToRefreshListView refreshListView;
	private HomeJobHttpUtils httpUtils;
	
	private List<HomeRecruitPar> newsList = new ArrayList<HomeRecruitPar>();
	private ListAdapter listAdapter;
	private int page = 1;
	
	public static JobRecruitFragment newInstance() {
		return new JobRecruitFragment();
	}

	public JobRecruitFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeJobHttpUtils(getActivity());
		
		initListView(rootView);
		
		((JobActivity)getActivity()).setLoadingBarVisible();
		getData(page);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((JobActivity)getActivity()).setLoadingBarGone();
	}

	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new ListAdapter(getActivity(), newsList);
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
		refreshListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    if(id == -1) {
			        // 点击的是headerView或者footerView
			        return;
			    }
			    int realPosition=(int)id;
			    HomeRecruitPar news = newsList.get(realPosition);
			    
			    Intent intent = new Intent(getActivity(), WebViewActiviry.class);
			    intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.JOB_RECRUIT);
			    intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, news.getContentUrl());
			    getActivity().startActivity(intent);
			    
			}
		});
	}
	
	private void getData(final int page){
		httpUtils.getRecList(page, new HomeJobHttpUtils.OnGetRecListListener() {
			
			@Override
			public void OnGetRecList(List<HomeRecruitPar> recruitPars) {
				if (page == 1) {
					if (getActivity() != null) {
						((JobActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (recruitPars != null) {
					newsList.addAll(recruitPars);
					listAdapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
	}
	
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<HomeRecruitPar> list;

		public ListAdapter(Context context, List<HomeRecruitPar> list) {
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
				convertView = inflater.inflate(R.layout.list_item_job_recruit, null);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			HomeRecruitPar news = list.get(position);
			holder.tvTitle.setText(news.getTitle());
			holder.tvContent.setText(news.getBrief());
			holder.tvTime.setText(news.getContentCreateTime());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public TextView tvTitle;
			public TextView tvContent;
			public TextView tvTime;
		}
		
	}
}
