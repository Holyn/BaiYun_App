package com.baiyun.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewFragment;
import com.baiyun.activity.webview.WebViewFragment2;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;
import com.baiyun.vo.parcelable.OveDepTeacherPar;

public class OverviewActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private OverviewFragment overviewFragment = null;
	private WebViewFragment webViewFragment = null;
	private OveTeachersFragment oveTeachersFragment = null;//delete 2015-5-11
	private WebViewFragment2 webViewFragment2 = null;//delete 2015-5-11
	private OveEnvFragment oveEnvFragment = null;
	
	private OveTeachersDetailFragment teachersDetailFragment = null;//delete 2015-5-11
	
	private OveTeachersGridFragment teachersGridFragment = null;//delete 2015-5-11
	
	@Override
	public void init() {
		setTopBarTitle("学校概况");
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager(); 
		
		showOverviewFragment();
	}

	private void showOverviewFragment(){
		if (overviewFragment == null) {
			overviewFragment = OverviewFragment.newInstance();
		}
		FragmentUtil.replaceNormal(overviewFragment, fragmentManager, R.id.fl_container_common);
	}
	
	public void showWebViewFragment(String httpUrl){
		Bundle args = new Bundle();
		args.putString(WebViewFragment.KEY_HTTP_URL, httpUrl);
		
		if (webViewFragment == null) {
			webViewFragment = WebViewFragment.newInstance();
		}
		FragmentUtil.replaceAddToBack(webViewFragment, fragmentManager, R.id.fl_container_common, args);
	}
	
	public void showOveTeachersFragment(){
		if (oveTeachersFragment == null) {
			oveTeachersFragment = OveTeachersFragment.newInstance();
		}
		FragmentUtil.replaceAddToBack(oveTeachersFragment, fragmentManager, R.id.fl_container_common);
	}
	
	public void showWebViewFragment2(String urlLast, String title){
		Bundle args = new Bundle();
		args.putString(WebViewFragment2.KEY_URL_LAST, urlLast);
		args.putString(WebViewFragment2.KEY_TITLE, title);
		if (webViewFragment2 == null) {
			webViewFragment2 = WebViewFragment2.newInstance();
		}
		FragmentUtil.replaceAddToBack(webViewFragment2, fragmentManager, R.id.fl_container_common, args);
	}
	
	public void showOveEnvFragment(){
		if (oveEnvFragment == null) {
			oveEnvFragment = OveEnvFragment.newInstance();
		}
		FragmentUtil.replaceAddToBack(oveEnvFragment, fragmentManager, R.id.fl_container_common);
	}
	
	public void showOveTeachersDetailFragment(OveDepTeacherPar teacherPar) {
		Bundle args = new Bundle();
		args.putParcelable(OveTeachersDetailFragment.KEY_OveDepTeacherPar, teacherPar);
		if (teachersDetailFragment == null) {
			teachersDetailFragment = OveTeachersDetailFragment.newInstance();
		}
		FragmentUtil.replaceAddToBack(teachersDetailFragment, fragmentManager, R.id.fl_container_common, args);
	}
	
	public void showOveTeachersGridFragment(String id) {
		Bundle args = new Bundle();
		args.putString(OveTeachersGridFragment.EXTRA_ID, id);
		teachersGridFragment = OveTeachersGridFragment.newInstance();
		FragmentUtil.replaceAddToBack(teachersGridFragment, fragmentManager, R.id.fl_container_common, args);
	}
	
}
