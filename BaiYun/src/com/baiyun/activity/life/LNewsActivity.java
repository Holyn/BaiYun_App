package com.baiyun.activity.life;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewFragment2;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class LNewsActivity extends BaseFragmentActivity{
	public final static String NEWS_ID_VALUE = "news_id_value";
	public final static String NEWS_ID_24 = "24";//学工动态（id=24）
	public final static String NEWS_ID_26 = "26";//体育艺术（id=26）
	public final static String NEWS_ID_27 = "27";//科技创新（id=27）
	
	private FragmentManager fragmentManager;
	
	private LNewsFragment lNewsFragment = null;
	private WebViewFragment2 webViewFragment2 = null;
	
	@Override
	public void init() {
		setBackPressEnabled(true);
		fragmentManager = getSupportFragmentManager();
		
		String newsId = getIntent().getStringExtra(NEWS_ID_VALUE);
		showLNewsFragment(newsId);
	}
	
	private void showLNewsFragment(String newsId) {
		Bundle args = new Bundle();
		args.putString(NEWS_ID_VALUE, newsId);
		if (lNewsFragment == null) {
			lNewsFragment = LNewsFragment.newInstance();
		}
		FragmentUtil.replaceNormal(lNewsFragment, fragmentManager, R.id.fl_container_common,args);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
