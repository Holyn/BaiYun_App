package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.custom.CommonAdapter;
import com.baiyun.custom.ViewHolder;
import com.baiyun.vo.parcelable.RecruitTypeMajorPar;

public class RecruitMajorFragment extends BaseFragment {
	private ListView listView;
	private ListViewAdapter adapter;
	private List<RecruitTypeMajorPar> majorPars;
	
	public static RecruitMajorFragment newInstance() {
		return new RecruitMajorFragment();
	}

	public RecruitMajorFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		majorPars = getArguments().getParcelableArrayList(RecruitMajorActivity.KEY_RECRUIT_MAJOR_PAR);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.listview_common;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
	}
	
	private void initView(View rootView){
		listView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new ListViewAdapter(getActivity(), majorPars);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((RecruitMajorActivity)getActivity()).showWebViewFragment2(majorPars.get(position).getIntroUrl());
			}
		});
	}
	

	private class ListViewAdapter extends CommonAdapter<RecruitTypeMajorPar> {

		public ListViewAdapter(Context context, List<RecruitTypeMajorPar> mDatas) {
			this(context, mDatas, R.layout.list_item_life_guide);
			// TODO Auto-generated constructor stub
		}
		
		public ListViewAdapter(Context context, List<RecruitTypeMajorPar> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder helper, RecruitTypeMajorPar item) {
			// TODO Auto-generated method stub
			helper.setText(R.id.tv_title, item.getProfessionName());
		}}

}
