package com.baiyun.fragment.sliding;

import android.view.View;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseSlidingFragmentActivity;

public class AboutFragment extends BaseFragment{
	
	public static AboutFragment newInstance() {
		return new AboutFragment();
	}
	
	public AboutFragment() {
		
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_about;
	}

	@Override
	public void createMyView(View rootView) {
		
	}
	
	
}
