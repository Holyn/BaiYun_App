package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.custom.ViewHolder;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeVideoHttpUtils;
import com.baiyun.vo.parcelable.HomeVideoPar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class VideoFragment extends Fragment {
	private View rootView;
	public static final String EXTRA_ID = "extra_id";
	public static final String EXTRA_STYLE = "extra_style";
	private String id;// 视频类型id
	private String style;

	private HomeVideoHttpUtils httpUtils;

	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private List<HomeVideoPar> videoPars = new ArrayList<HomeVideoPar>();
	private MyGridAdapter gridAdapter;

	private int page = 1;

	public static VideoFragment newInstance() {
		return new VideoFragment();
	}

	public VideoFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HomeVideoHttpUtils(getActivity());
		id = getArguments().getString(EXTRA_ID);
		style = getArguments().getString(EXTRA_STYLE);
		videoPars.clear();
		page = 1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_video, container, false);
		initView(rootView);

		((VideoActivity) getActivity()).setLoadingBarVisible();
		getNetData(page);
		return rootView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((VideoActivity) getActivity()).setTopBarTitle(style);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((VideoActivity) getActivity()).setLoadingBarGone();
	}

	private void initView(View rootView) {
		mPullRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.reflesh_gridview);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_END);

		gridView = mPullRefreshGridView.getRefreshableView();
		gridAdapter = new MyGridAdapter(getActivity(), videoPars);
		gridView.setAdapter(gridAdapter);

		mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				mPullRefreshGridView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				page++;
				getNetData(page);
			}

		});

		// 监听griview的点击事件
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HomeVideoPar videoPar = videoPars.get(position);
				Intent intent = new Intent(getActivity(), WebViewActiviry.class);
				intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.VIDEO);
				intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, videoPar.getContentUrl());
				getActivity().startActivity(intent);
			}

		});
	}

	private void getNetData(final int page) {
		httpUtils.getVideos(id, page, new HomeVideoHttpUtils.OnGetVideoListener() {

			@Override
			public void onGetVideo(List<HomeVideoPar> vos) {
				if (page == 1) {
					if (getActivity() != null) {
						((VideoActivity) getActivity()).setLoadingBarGone();
					}
				}
				if (vos != null) {
					videoPars.addAll(vos);
					gridAdapter.notifyDataSetChanged();
					gridView.smoothScrollToPosition(videoPars.size() - vos.size());
				}
				mPullRefreshGridView.onRefreshComplete();
			}
		});
	}

	private class MyGridAdapter extends CommonAdapter<HomeVideoPar> {

		public MyGridAdapter(Context context, List<HomeVideoPar> mDatas) {
			this(context, mDatas, R.layout.grid_item_video);
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
				String picUrl = HttpURL.HOST + urlLast.substring(1);
				helper.setImageByUrl(R.id.iv_video, picUrl);
			}
			helper.setText(R.id.tv_pic_name, item.getPicName());
			helper.setText(R.id.tv_title, item.getTitle());
			helper.setText(R.id.tv_brief, item.getBrief());
		}

	}

}
