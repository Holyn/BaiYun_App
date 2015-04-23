package com.baiyun.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class VideoActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private VideoStyleFragment videoStyleFragment = null;
	private VideoFragment videoFragment = null;

	@Override
	public void init() {
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager(); 
		
		showVideoFragment();
	}
	
	private void showVideoFragment(){
		if (videoStyleFragment == null) {
			videoStyleFragment = VideoStyleFragment.newInstance();
		}
		FragmentUtil.replaceNormal(videoStyleFragment, fragmentManager, R.id.fl_container_common);
	}
	
	public void showVideoFragment(String id, String style){
		Bundle args = new Bundle();
		args.putString(VideoFragment.EXTRA_ID, id);
		args.putString(VideoFragment.EXTRA_STYLE, style);
		if (videoFragment == null) {
			videoFragment = VideoFragment.newInstance();
		}
		FragmentUtil.replaceAddToBack(videoFragment, fragmentManager, R.id.fl_container_common, args);
	}

}
