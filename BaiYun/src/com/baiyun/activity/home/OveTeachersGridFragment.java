package com.baiyun.activity.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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

import com.baiyun.activity.R;
import com.baiyun.activity.life.LModelActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.custom.ViewHolder;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeHttpUtils;
import com.baiyun.vo.parcelable.OveDepTeacherPar;
import com.baiyun.vo.parcelable.Vo2Par;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class OveTeachersGridFragment extends BaseFragment{
	public View rootView;
	public static final String EXTRA_ID = "extra_id";
	private HomeHttpUtils httpUtils;
	private String id;//系部id
	
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView gridView;
	private List<OveDepTeacherPar> teacherPars = new ArrayList<OveDepTeacherPar>();
	private MyGridAdapter gridAdapter;
	
	private int page = 1;
	
	public static OveTeachersGridFragment newInstance() {
		return new OveTeachersGridFragment();
	}

	public OveTeachersGridFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HomeHttpUtils(getActivity());
		id = getArguments().getString(EXTRA_ID);
		teacherPars.clear();
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.pull_to_refresh_gridview;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		
		((OverviewActivity)getActivity()).setLoadingBarVisible();
		getNetData(page);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((OverviewActivity)getActivity()).setLoadingBarGone();
	}
	
	private void initView(View rootView){
		mPullRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.reflesh_gridview);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_END);
		
		gridView = mPullRefreshGridView.getRefreshableView();
		gridAdapter = new MyGridAdapter(getActivity(), teacherPars);
		gridView.setAdapter(gridAdapter);
		
		mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				mPullRefreshGridView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				page++;
				getNetData(page);
			}
			
		});
		
		//监听griview的点击事件
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OveDepTeacherPar teacherPar = teacherPars.get(position);
				((OverviewActivity)getActivity()).showOveTeachersDetailFragment(teacherPar);
			}
			
		});
	}
	
	private void getNetData(final int page){
		httpUtils.getOveDepTeacherPars(id, page, new HomeHttpUtils.OnGetOveDepTeacherParsListener() {
			
			@Override
			public void onGetOveDepTeacherPars(List<OveDepTeacherPar> vos) {
				if (page == 1) {
					if (getActivity() != null) {
						((OverviewActivity)getActivity()).setLoadingBarGone();
					}
				}
				if (vos != null) {
					teacherPars.addAll(vos);
					gridAdapter.notifyDataSetChanged();
					gridView.smoothScrollToPosition(teacherPars.size() - vos.size());
				}
				mPullRefreshGridView.onRefreshComplete();
			}
		});
	}
	
	private class MyGridAdapter extends CommonAdapter<OveDepTeacherPar>{
		
		public MyGridAdapter(Context context, List<OveDepTeacherPar> mDatas) {
			this(context, mDatas, R.layout.grid_item_ove_teachers);
		}

		public MyGridAdapter(Context context, List<OveDepTeacherPar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder helper, OveDepTeacherPar item) {
			// TODO Auto-generated method stub
			String picUrlLast = item.getPicUrl();
			if (!(TextUtils.isEmpty(picUrlLast))) {
				String picUrl = HttpURL.HOST+item.getPicUrl().substring(1);
				helper.setImageByUrl(R.id.iv_picture, picUrl);
			}
			helper.setText(R.id.tv_name, item.getTitle());
		}
		
	}

}
