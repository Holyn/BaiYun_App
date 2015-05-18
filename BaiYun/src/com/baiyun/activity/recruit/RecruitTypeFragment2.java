package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.activity.webview.WebViewActiviry;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.custom.ListViewForScrollView;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.vo.parcelable.RecruitTypePar;

public class RecruitTypeFragment2 extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	
	private List<List<RecruitTypePar>> typeListList = new ArrayList<List<RecruitTypePar>>();
	private ListView listListView;
	private ListListAdapter listListAdapter;
	
	public static RecruitTypeFragment2 newInstance() {
		return new RecruitTypeFragment2();
	}
	
	public RecruitTypeFragment2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new RecruitHttpUtils(getActivity());
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.listview_common;
	}

	@Override
	public void createMyView(View rootView) {
		listListView = (ListView)rootView.findViewById(R.id.listview);
		listListAdapter = new ListListAdapter(getActivity(), typeListList);
		listListView.setAdapter(listListAdapter);
		
		getData();
	}
	
	private void getData() {
		((MainActivity) getActivity()).setLoadingBarVisible();
		httpUtils.getTypeList(new RecruitHttpUtils.OnGetTypeListListener() {
			
			@Override
			public void OnGetTypeList(List<RecruitTypePar> typePars) {
				if (getActivity() != null) {
					((MainActivity) getActivity()).setLoadingBarGone();
				}
				if (typePars != null) {
					separateList(typePars);
				}
			}
		});
	}
	
	// 分解网络请求回来的List<RecruitTypePar> recruitTypePars
	private void separateList(List<RecruitTypePar> recruitTypePars){
		Iterator<RecruitTypePar> iterator = recruitTypePars.iterator();
		while (iterator.hasNext()) {
			RecruitTypePar recruitTypePar = (RecruitTypePar) iterator.next();
			String menuSubId = recruitTypePar.getMenuSubId();
			
			List<RecruitTypePar> typeListAdd = null;
			for (int i = 0; i < typeListList.size(); i++) {
				List<RecruitTypePar> typeList = typeListList.get(i);
				String menuSubIdAdd = typeList.get(0).getMenuSubId();
				if (menuSubId == menuSubIdAdd) {//该id的类型已add
					typeListAdd = typeList;
				}
			}
			if (typeListAdd == null) {
				typeListAdd = new ArrayList<RecruitTypePar>();
				typeListAdd.add(recruitTypePar);
				typeListList.add(typeListAdd);
			}else {
				typeListAdd.add(recruitTypePar);
			}
		}
		listListAdapter.notifyDataSetChanged();
		listListView.scrollTo(0, 0);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity) getActivity()).setLoadingBarGone();
	}
	
	private class ListListAdapter extends CommonAdapter<List<RecruitTypePar>>{
		
		public ListListAdapter(Context context, List<List<RecruitTypePar>> mDatas) {
			this(context, mDatas, R.layout.list_item_recruit_type_1);
		}

		public ListListAdapter(Context context, List<List<RecruitTypePar>> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(com.baiyun.custom.ViewHolder helper, List<RecruitTypePar> item) {
			// TODO Auto-generated method stub
			helper.setText(R.id.tv_title, item.get(0).getSubMenuName());
			ListViewForScrollView listViewForScrollView = helper.getView(R.id.listViewFSV);
			ListAdapter listAdapter = new ListAdapter(getActivity(), item);
			listViewForScrollView.setAdapter(listAdapter);
		}
		
	}
	
	private class ListAdapter extends CommonAdapter<RecruitTypePar>{
		
		public ListAdapter(Context context, List<RecruitTypePar> mDatas) {
			this(context, mDatas, R.layout.list_item_recruit_type);
		}

		public ListAdapter(Context context, List<RecruitTypePar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(com.baiyun.custom.ViewHolder helper, final RecruitTypePar item) {
			// TODO Auto-generated method stub
			helper.setText(R.id.tv_title, item.getThreeMenuName());
			(helper.getView(R.id.btn_plan)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    Intent intent = new Intent(getActivity(), WebViewActiviry.class);
				    intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.RECRUIT_PLAN);
				    intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, item.getPlanUrl());
				    getActivity().startActivity(intent);
				}
			});
			(helper.getView(R.id.btn_introduce)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    Intent intent = new Intent(getActivity(), RecruitMajorActivity.class);
				    intent.putParcelableArrayListExtra(RecruitMajorActivity.KEY_RECRUIT_MAJOR_PAR, (ArrayList<? extends Parcelable>) item.getgCourseContentViewList());
				    getActivity().startActivity(intent);
				}
			});
		}
		
	}
}
