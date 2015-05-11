package com.baiyun.activity.home;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.HomeHttpUtils;
import com.baiyun.vo.parcelable.OvePicPar;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OverviewFragment extends BaseFragment {
	private HomeHttpUtils httpUtils;

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
		((OverviewActivity) getActivity()).setTopBarTitle("学校概况");
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeHttpUtils(getActivity());
		final ImageView ivPicture = (ImageView) rootView.findViewById(R.id.iv_picture);
		httpUtils.getOvePicPar(new HomeHttpUtils.OnGetOvePicParListener() {

			@Override
			public void onGetOvePicPar(OvePicPar ovePicPar) {
				// TODO Auto-generated method stub
				if (ovePicPar != null) {
					String urlLast = ovePicPar.getUrl();
					if (!TextUtils.isEmpty(urlLast)) {
						String picUrl = HttpURL.HOST + urlLast.substring(1);
						ImageLoader.getInstance().displayImage(picUrl, ivPicture);
					}
				}
			}
		});

		Button btnIntro = (Button) rootView.findViewById(R.id.btn_intro);
		btnIntro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((OverviewActivity) getActivity()).setTopBarTitle("学校简介");
				((OverviewActivity) getActivity()).showWebViewFragment(HttpURL.SCHOOL_INTRO);
			}
		});

		Button btnOrganization = (Button) rootView.findViewById(R.id.btn_organization);
		btnOrganization.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((OverviewActivity) getActivity()).setTopBarTitle("组织架构");
				((OverviewActivity) getActivity()).showWebViewFragment(HttpURL.SCHOOL_ORGANIZATION);
			}
		});

		Button btnTeachers = (Button) rootView.findViewById(R.id.btn_teachers);
		btnTeachers.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((OverviewActivity) getActivity()).setTopBarTitle("师资队伍");
				((OverviewActivity) getActivity()).showWebViewFragment(HttpURL.SCHOOL_TEACHERS_SUM);
				
//				((OverviewActivity) getActivity()).showOveTeachersFragment();//delete 2015-5-11
			}
		});

		Button btnEnvironment = (Button) rootView.findViewById(R.id.btn_environment);
		btnEnvironment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((OverviewActivity) getActivity()).showOveEnvFragment();
			}
		});
	}

}
