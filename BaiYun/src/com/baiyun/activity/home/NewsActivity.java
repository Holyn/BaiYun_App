package com.baiyun.activity.home;

import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class NewsActivity extends BaseFragmentActivity {
	private FragmentManager fragmentManager;

	private NewsFragment newsFragment = null;

	@Override
	public void init() {
		setTopBarTitle("技师微报");
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		showNewsFragment();
	}

	private void showNewsFragment() {
		if (newsFragment == null) {
			newsFragment = NewsFragment.newInstance();
		}
		FragmentUtil.replaceNormal(newsFragment, fragmentManager, R.id.fl_container_common);
	}

}
