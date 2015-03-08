package com.baiyun.activity.life;

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
import com.baiyun.httputils.HomeJobHttpUtils;
import com.baiyun.httputils.SchoolLifeHttpUtils;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.HomeRecruitPar;
import com.baiyun.vo.parcelable.LifeAssociationNewsPar;
import com.baiyun.vo.parcelable.Vo2Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LAssociationNewsFragment extends BaseFragment{
	public static final String KEY_ID = "key_id";
	
	private String idStr;
	
	private PullToRefreshListView refreshListView;
	private SchoolLifeHttpUtils httpUtils;
	
	private List<LifeAssociationNewsPar> associationNewsPars = new ArrayList<LifeAssociationNewsPar>();
	private ListAdapter listAdapter;
	private int page = 1;
	
	public static LAssociationNewsFragment newInstance() {
		return new LAssociationNewsFragment();
	}

	public LAssociationNewsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		idStr = getArguments().getString(KEY_ID);
//		System.out.println("====> idStr = "+idStr);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_listview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new SchoolLifeHttpUtils(getActivity());
		
		initListView(rootView);
		
		((LAssociationActivity) getActivity()).setLoadingBarVisible();
		getNetData(page);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((LAssociationActivity) getActivity()).setLoadingBarGone();
	}
	
	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new ListAdapter(getActivity(), associationNewsPars);
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
				getNetData(page);
			}
		});
		refreshListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    if(id == -1) {
			        // 点击的是headerView或者footerView
			        return;
			    }
			    int realPosition=(int)id;
			    
				LifeAssociationNewsPar news = associationNewsPars.get(realPosition);
				((LAssociationActivity)getActivity()).showWebViewFragment2(news.getContentUrl(),"动态新闻");
			}
		});
	}
	
	private void getNetData(final int page){
		httpUtils.getAssociationNews(idStr, page, new SchoolLifeHttpUtils.onGetAssociationNewsListener() {
			
			@Override
			public void onGetAssociationNews(List<LifeAssociationNewsPar> newsPars) {
				if (page == 1) {
					if (getActivity() != null) {
						((LAssociationActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (newsPars != null) {
					LAssociationNewsFragment.this.associationNewsPars.addAll(newsPars);
					listAdapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
		
	}
	
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<LifeAssociationNewsPar> list;

		public ListAdapter(Context context, List<LifeAssociationNewsPar> list) {
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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.list_item_life_news, parent, false);
				holder.ivHeader = (ImageView) convertView.findViewById(R.id.iv_header);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			LifeAssociationNewsPar news = associationNewsPars.get(position);
			if (news.getPicUrl() != null && !(news.getPicUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST + news.getPicUrl().substring(1);
				ImageLoader.getInstance().displayImage(picUrl, holder.ivHeader);
			}
			holder.tvTitle.setText(news.getContentTitle());
			holder.tvContent.setText(news.getContentBrief());
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
