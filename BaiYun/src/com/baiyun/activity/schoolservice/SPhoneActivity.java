package com.baiyun.activity.schoolservice;

import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class SPhoneActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private SPhoneFragment phoneFragment = null;
	@Override
	public void init() {
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		showLModelFragment();
	}
	
	private void showLModelFragment(){
		if (phoneFragment == null) {
			phoneFragment = SPhoneFragment.newInstance();
		}
		FragmentUtil.replaceNormal(phoneFragment, fragmentManager, R.id.fl_container_common);
	}

}
