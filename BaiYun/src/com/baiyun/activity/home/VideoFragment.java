package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.baiyun.httputils.HomeVideoHttpUtils;
import com.baiyun.vo.parcelable.HomeVideoPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VideoFragment extends BaseFragment{
	private PullToRefreshListView refreshListView;
	private HomeVideoHttpUtils httpUtils;
	
	private List<HomeVideoPar> videoList = new ArrayList<HomeVideoPar>();
	private VideoListAdapter listAdapter;
	private int page = 1;
	
	public static VideoFragment newInstance() {
		return new VideoFragment();
	}
	
	public VideoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeVideoHttpUtils(getActivity());
		initListView(rootView);
		
		((VideoActivity)getActivity()).setLoadingBarVisible();
		getData(page);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((VideoActivity)getActivity()).setLoadingBarGone();
	}

	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new VideoListAdapter(getActivity(), videoList);
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
		refreshListView.getRefreshableView().setOnItemClickListener(new VideoListOnItemClickListener());
	}
	
	private void getData(final int page){
		
//		httpUtils.getVideos("", page, new HomeVideoHttpUtils.OnGetVideoListener() {
//			
//			@Override
//			public void onGetVideo(List<HomeVideoPar> videoPars) {
//				if (page == 1) {
//					if (getActivity() != null) {
//						((VideoActivity)getActivity()).setLoadingBarGone();
//					}
//				}
//				if (videoPars != null) {
//					videoList.addAll(videoPars);
//					listAdapter.notifyDataSetChanged();
//				}
//				refreshListView.onRefreshComplete();
//			}
//		});
		
	}
	/**
	 * list的点击事件
	 */
	private class VideoListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    if(id == -1) {
		        // 点击的是headerView或者footerView
		        return;
		    }
		    int realPosition=(int)id;
			HomeVideoPar videoPar = videoList.get(realPosition);
			Intent intent = new Intent(getActivity(), WebViewActiviry.class);
			intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.VIDEO);
			intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, videoPar.getContentUrl());
			getActivity().startActivity(intent);
			
		}
		
	}
	
	private class VideoListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<HomeVideoPar> list;

		public VideoListAdapter(Context context, List<HomeVideoPar> list) {
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
				convertView = inflater.inflate(R.layout.list_item_video, null);
				holder.ivHeader = (ImageView)convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tvCreater = (TextView)convertView.findViewById(R.id.tv_creater);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
				holder.tvViewtimes = (TextView)convertView.findViewById(R.id.tv_viewtimes);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			HomeVideoPar video = list.get(position);
			if (video.getPicUrl() != null && !(video.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+video.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(video.getTitle());
			holder.tvCreater.setText(video.getCreater());
			holder.tvTime.setText(video.getContentCreateTime());
			holder.tvViewtimes.setText("播放："+video.getViewTimes());
			
			return convertView;
		}
		
		public final class ViewHolder {
			public ImageView ivHeader;
			public TextView tvTitle;
			public TextView tvCreater;
			public TextView tvTime;
			public TextView tvViewtimes;
		}
		
	}
}
