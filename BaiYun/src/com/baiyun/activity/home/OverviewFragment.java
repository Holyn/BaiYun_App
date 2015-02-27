package com.baiyun.activity.home;

import android.view.View;
import android.widget.Button;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;

public class OverviewFragment extends BaseFragment{
	
	public static OverviewFragment newInstance() {
		return new OverviewFragment();
	}

	public OverviewFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_intro;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((OverviewActivity)getActivity()).setTopBarTitle("学校概况");
	}

	@Override
	public void createMyView(View rootView) {
		Button btnIntro = (Button)rootView.findViewById(R.id.btn_intro);
		btnIntro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OverviewActivity)getActivity()).setTopBarTitle("学校简介");
				((OverviewActivity)getActivity()).showWebViewFragment(HttpURL.SCHOOL_INTRO);
			}
		});
		
		Button btnOrganization = (Button)rootView.findViewById(R.id.btn_organization);
		btnOrganization.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OverviewActivity)getActivity()).setTopBarTitle("组织架构");
				((OverviewActivity)getActivity()).showWebViewFragment(HttpURL.SCHOOL_ORGANIZATION);
			}
		});
		
		Button btnTeachers = (Button)rootView.findViewById(R.id.btn_teachers);
		btnTeachers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OverviewActivity)getActivity()).showOveTeachersFragment();
			}
		});
		
		Button btnEnvironment = (Button)rootView.findViewById(R.id.btn_environment);
		btnEnvironment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((OverviewActivity)getActivity()).showOveEnvFragment();
			}
		});
	}

}
