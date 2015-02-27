package com.baiyun.activity.home;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.ZoomDensity;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.HomeJobHttpUtils;

public class JobSummaryFragment extends BaseFragment{
	private HomeJobHttpUtils httpUtils;
	
	private ProgressWebView webView;
	
	public static JobSummaryFragment newInstance() {
		return new JobSummaryFragment();
	}
	
	public JobSummaryFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.webview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new HomeJobHttpUtils(getActivity());
		
		webView = (ProgressWebView) rootView.findViewById(R.id.webview);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
		webView.getSettings().setSupportZoom(true); // 可以缩放
		webView.getSettings().setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
		webView.getSettings().setUseWideViewPort(true); // 为图片添加放大缩小功能

		webView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});

		getData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((JobActivity) getActivity()).setLoadingBarGone();
	}
	
	private void getData() {
		((JobActivity) getActivity()).setLoadingBarVisible();
		
		httpUtils.getSumUrl(new HomeJobHttpUtils.OnGetSumUrlListener() {
			
			@Override
			public void OnGetSumUrl(String url) {
				if (getActivity() != null) {
					((JobActivity) getActivity()).setLoadingBarGone();
				}
				if (url != null) {
					webView.loadUrl(url);
				}
			}
		});
	}
}
