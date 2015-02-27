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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeNewsHttpUtils;
import com.baiyun.vo.parcelable.HomeNewsPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsFragment extends BaseFragment{
	private PullToRefreshListView refreshListView;
	private HomeNewsHttpUtils httpUtils;
	
	private List<HomeNewsPar> newsList = new ArrayList<HomeNewsPar>();
	private NewsListAdapter listAdapter;
	private int page = 1;
	
	public static NewsFragment newInstance() {
		return new NewsFragment();
	}

	public NewsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeNewsHttpUtils(getActivity());
		
		initListView(rootView);
		getData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((NewsActivity)getActivity()).setLoadingBarGone();
	}

	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new NewsListAdapter(getActivity(), newsList);
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
				httpUtils.getPageHomeNews(page, new HomeNewsHttpUtils.OnGetPageHomeNewsListener() {
					
					@Override
					public void onGerPageHomeNews(List<HomeNewsPar> homeNewsPars) {
						refreshListView.onRefreshComplete();
						if (homeNewsPars != null) {
							newsList.addAll(homeNewsPars);
							listAdapter.notifyDataSetChanged();
						}
					}
				});
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
			    HomeNewsPar news = newsList.get(realPosition);
			    
			    Intent intent = new Intent(getActivity(), WebViewActiviry.class);
			    intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.NEWS_DETAIL);
			    intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, news.getContentUrl());
			    getActivity().startActivity(intent);
			    
			}
		});
	}
	
	private void getData(){
		((NewsActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getPageHomeNews(page, new HomeNewsHttpUtils.OnGetPageHomeNewsListener() {
			
			@Override
			public void onGerPageHomeNews(List<HomeNewsPar> homeNewsPars) {
				if (getActivity() != null) {
					((NewsActivity)getActivity()).setLoadingBarGone();
				}
				if (homeNewsPars != null) {
					newsList.addAll(homeNewsPars);
					listAdapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private class NewsListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<HomeNewsPar> list;

		public NewsListAdapter(Context context, List<HomeNewsPar> list) {
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
				convertView = inflater.inflate(R.layout.list_item_schools_news, null);
				holder.ivHeader = (ImageView)convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView)convertView.findViewById(R.id.tv_content);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			HomeNewsPar news = list.get(position);
			if (news.getPicUrl() != null && !(news.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+news.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(news.getTitle());
			holder.tvContent.setText(news.getBrief());
			holder.tvTime.setText(news.getContentCreateTime());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public ImageView ivHeader;
			public TextView tvTitle;
			public TextView tvContent;
			public TextView tvTime;
		}
		
	}

}
