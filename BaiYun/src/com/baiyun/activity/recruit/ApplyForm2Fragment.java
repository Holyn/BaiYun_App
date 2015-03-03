package com.baiyun.activity.recruit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.vo.parcelable.ApplyPar;

public class ApplyForm2Fragment  extends BaseFragment{
	private ApplyPar applyPar;
	
	private TextView tvTitle;
	
	public static ApplyForm2Fragment newInstance() {
		return new ApplyForm2Fragment();
	}
	
	public ApplyForm2Fragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		applyPar = getArguments().getParcelable(ApplyFormActivity.VALUE_APPLYPAR);
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_form_2;
	}

	@Override
	public void createMyView(View rootView) {
		tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
		tvTitle.setText(applyPar.getName());
	}

}
