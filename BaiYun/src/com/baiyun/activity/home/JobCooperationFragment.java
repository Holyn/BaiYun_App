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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeJobHttpUtils;
import com.baiyun.vo.parcelable.HomeCooNewsPar;
import com.baiyun.vo.parcelable.HomeCooSumPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JobCooperationFragment extends BaseFragment implements View.OnClickListener{
	private HomeJobHttpUtils httpUtils;
	
	private LinearLayout llSummary;
	private TextView tvSummary;
	private HomeCooSumPar cooSumPar = null;
	
	private PullToRefreshListView refreshListView;
	private List<HomeCooNewsPar> newsList = new ArrayList<HomeCooNewsPar>();
	private ListAdapter listAdapter;
	private int page = 1;
	
	public static JobCooperationFragment newInstance() {
		return new JobCooperationFragment();
	}

	public JobCooperationFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_cooperation;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeJobHttpUtils(getActivity());
		
		initView(rootView);
		
		getSummary();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((JobActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		llSummary = (LinearLayout)rootView.findViewById(R.id.ll_summary);
		tvSummary = (TextView)rootView.findViewById(R.id.tv_summary);
		
		initListView(rootView);
		
		getNews(page);
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
				getNews(page);
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
			    HomeCooNewsPar news = newsList.get(realPosition);
			    
			    goDetail(news.getContentUrl());
			}
		});
	}
	
	private void getSummary(){
		((JobActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getCooSum(new HomeJobHttpUtils.OnGetCooSumListener() {
			
			@Override
			public void OnGetCooSum(HomeCooSumPar cooSumPar) {
				if (getActivity() != null) {
					((JobActivity)getActivity()).setLoadingBarGone();
				}
				if (cooSumPar != null) {
					JobCooperationFragment.this.cooSumPar = cooSumPar;
					tvSummary.setText(cooSumPar.getBrief());
					llSummary.setOnClickListener(JobCooperationFragment.this);
				}
			}
		});
	}
	
	private void getNews(int page){
		httpUtils.getCooNews(page, new HomeJobHttpUtils.OnGetCooNewsListener() {
			
			@Override
			public void OnGetCooNews(List<HomeCooNewsPar> newsPars) {
				if (newsPars != null) {
					newsList.addAll(newsPars);
					listAdapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_summary:
			if (cooSumPar != null) {
				goDetail(cooSumPar.getUrl());
			}
			break;

		default:
			break;
		}
		
	}
	
	private void goDetail(String url){
	    Intent intent = new Intent(getActivity(), WebViewActiviry.class);
	    intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.COOPERATION);
	    intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, url);
	    getActivity().startActivity(intent);
	}

	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<HomeCooNewsPar> list;

		public ListAdapter(Context context, List<HomeCooNewsPar> list) {
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
				convertView = inflater.inflate(R.layout.list_item_coo_news, null);
				holder.ivHeader = (ImageView)convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			HomeCooNewsPar news = list.get(position);
			if (news.getPicUrl() != null && !(news.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+news.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(news.getTitle());
			holder.tvTime.setText(news.getContentCreateTime());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public ImageView ivHeader;
			public TextView tvTitle;
			public TextView tvTime;
		}
		
	}

}
