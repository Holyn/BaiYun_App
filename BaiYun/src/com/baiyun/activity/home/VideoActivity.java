package com.baiyun.activity.home;

import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class VideoActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private VideoFragment videoFragment = null;

	@Override
	public void init() {
		setTopBarTitle("视频白云");
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager(); 
		
		showVideoFragment();
	}
	
	private void showVideoFragment(){
		if (videoFragment == null) {
			videoFragment = VideoFragment.newInstance();
		}
		FragmentUtil.replaceNormal(videoFragment, fragmentManager, R.id.fl_container_common);
		
	}

}
