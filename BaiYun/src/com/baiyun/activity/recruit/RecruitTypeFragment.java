package com.baiyun.activity.recruit;

import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.RecruitHttpUtils;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.RecruitTypePar;

public class RecruitTypeFragment extends BaseFragment{
	private RecruitHttpUtils httpUtils;
	private List<RecruitTypePar> recruitTypePars;
	
	private List<RecruitTypePar> list_1;//menuSubId=16，高中生(职中、中专、中技)报读专区
	private List<RecruitTypePar> list_2;//menuSubId=17，初中生报读专区
	private List<RecruitTypePar> list_3;//menuSubId=18，职能培训报读专区
	
	public static RecruitTypeFragment newInstance() {
		return new RecruitTypeFragment();
	}
	
	public RecruitTypeFragment() {
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
		return R.layout.fragment_recruit_type;
	}

	@Override
	public void createMyView(View rootView) {
		
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
					recruitTypePars = typePars;
				}
			}
		});
	}
	
	// 分解网络请求回来的List<RecruitTypePar> recruitTypePars
	private void separateList(List<RecruitTypePar> recruitTypePars){
		Iterator<RecruitTypePar> iterator = recruitTypePars.iterator();
		while (iterator.hasNext()) {
			RecruitTypePar recruitTypePar = (RecruitTypePar) iterator.next();
			if (recruitTypePar.getMenuSubId().equalsIgnoreCase("16")) {
				list_1.add(recruitTypePar);
			}else if (recruitTypePar.getMenuSubId().equalsIgnoreCase("17")) {
				
			}else if (recruitTypePar.getMenuSubId().equalsIgnoreCase("18")) {
				
			}
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity) getActivity()).setLoadingBarGone();
	}
}
