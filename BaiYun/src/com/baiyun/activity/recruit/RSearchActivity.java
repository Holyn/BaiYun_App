package com.baiyun.activity.recruit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class RSearchActivity extends BaseFragmentActivity{

	private FragmentManager fragmentManager;
	private RSearchFragment searchFragment = null;
	
	@Override
	public void init() {
		setTopBarTitle("报名查询");
		setBackPressEnabled(true);
		
		fragmentManager = getSupportFragmentManager();
		
		showRSearchFragment();
	}
	
	private void showRSearchFragment(){
		if (searchFragment == null) {
			searchFragment = RSearchFragment.newInstance();
		}
		Bundle args = new Bundle();
		FragmentUtil.replaceNormal(searchFragment, fragmentManager, R.id.fl_container_common, args);
	}

}
