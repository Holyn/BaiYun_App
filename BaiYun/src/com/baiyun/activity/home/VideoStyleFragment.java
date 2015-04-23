package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.custom.ViewHolder;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeVideoHttpUtils;
import com.baiyun.vo.parcelable.HomeVideoPar;
import com.baiyun.vo.parcelable.HomeVideoStylePar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class VideoStyleFragment extends BaseFragment{
	private PullToRefreshListView refreshListView;
	private HomeVideoHttpUtils httpUtils;
	
	private List<HomeVideoStylePar> videoStylePars = new ArrayList<HomeVideoStylePar>();
	private MyListAdapter listAdapter;
	private int page = 1;
	
	public static VideoStyleFragment newInstance() {
		return new VideoStyleFragment();
	}
	
	public VideoStyleFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_video_style;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeVideoHttpUtils(getActivity());
		initListView(rootView);
		
		((VideoActivity)getActivity()).setLoadingBarVisible();
		getNetData(page);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((VideoActivity)getActivity()).setLoadingBarGone();
	}

	private void initListView(View rootView){
		refreshListView = (PullToRefreshListView)rootView.findViewById(R.id.refresh_listview);
		listAdapter = new MyListAdapter(getActivity(), videoStylePars);
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
	}
	
	private void getNetData(final int page){
		httpUtils.getVideoStyle(page, new HomeVideoHttpUtils.OnGetVideoStyleListener() {
			
			@Override
			public void onGetVideoStyle(List<HomeVideoStylePar> vos) {
				if (page == 1) {
					if (getActivity() != null) {
						((VideoActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (vos != null) {
					videoStylePars.addAll(vos);
					listAdapter.notifyDataSetChanged();
				}
				refreshListView.onRefreshComplete();
			}
		});
		
	}
	
	private class MyListAdapter extends CommonAdapter<HomeVideoStylePar>{
		
		public MyListAdapter(Context context, List<HomeVideoStylePar> mDatas) {
			this(context, mDatas, R.layout.list_item_video_style);
		}

		public MyListAdapter(Context context, List<HomeVideoStylePar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder helper, HomeVideoStylePar item) {
			helper.setText(R.id.tv_title, item.getName());
			TextView tvMore = helper.getView(R.id.tv_more);
			tvMore.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			List<HomeVideoPar> videoPars = item.getgAppContentPicViewList();
			MyGridAdapter gridAdapter = new MyGridAdapter(getActivity(), videoPars);
			GridView gridView = helper.getView(R.id.gv_video);
			gridView.setAdapter(gridAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		
	}
	
	private class MyGridAdapter extends CommonAdapter<HomeVideoPar>{
		
		public MyGridAdapter(Context context, List<HomeVideoPar> mDatas) {
			this(context, mDatas, R.layout.grid_item_video_style);
		}

		public MyGridAdapter(Context context, List<HomeVideoPar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder helper, HomeVideoPar item) {
			// TODO Auto-generated method stub
			String urlLast = item.getPicUrl();
			if (!TextUtils.isEmpty(urlLast)) {
				String picUrl = HttpURL.HOST+urlLast.substring(1);
				helper.setImageByUrl(R.id.iv_video, picUrl);
			}
			helper.setText(R.id.tv_pic_name, item.getPicName());
			helper.setText(R.id.tv_title, item.getTitle());
			helper.setText(R.id.tv_brief, item.getBrief());
		}
		
	}

}
