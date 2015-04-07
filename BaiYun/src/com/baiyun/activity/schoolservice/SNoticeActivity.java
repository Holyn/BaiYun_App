package com.baiyun.activity.schoolservice;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewFragment2;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class SNoticeActivity extends BaseFragmentActivity{
	private FragmentManager fragmentManager;
	
	private SNoticeFragment noticeFragment = null;
	private WebViewFragment2 webViewFragment2 = null;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		showNoticeFragment();
	}
	
	private void showNoticeFragment(){
		if (noticeFragment == null) {
			noticeFragment = SNoticeFragment.newInstance();
		}
		FragmentUtil.replaceNormal(noticeFragment, fragmentManager, R.id.fl_container_common);
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

}
