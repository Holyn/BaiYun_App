package com.baiyun.activity;

import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.sharepreferences.AppSettingSP;

import android.content.Intent;
import android.view.WindowManager;
/**
 * @author Holyn
 * @since 2015-1-18
 *
 */
public class StartActivity extends BaseFragmentActivity implements StartFragment.OnDelayListener{
	private AppSettingSP appSettingSP;
	
	@Override
	public void init() {
		showFullScreen();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		setTopBarEnable(false);//关闭头部的导航栏
		
//		showStartFragment();
		
		appSettingSP = AppSettingSP.getSingleInstance(this);
		
		if (appSettingSP.getIsFirst()) {
			showGuideFragment();
			appSettingSP.setIsFirst(false);
		}else {
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
			this.finish();
		}
		
	}

	@Override
	public void onDelay(boolean isFinish) {
		if (isFinish) {
			if (appSettingSP.getIsFirst()) {
				showGuideFragment();
				appSettingSP.setIsFirst(false);
			}else {
				Intent intent = new Intent(StartActivity.this, MainActivity.class);
				startActivity(intent);
				this.finish();
			}
		}
	}
	
	private void showStartFragment(){
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_container_common, StartFragment.newInstance()).commit();
	}
	
	private void showGuideFragment(){
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_container_common, GuideFragment.newInstance()).commit();
	}

	public void showFullScreen(){
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void closeFullScreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

}
