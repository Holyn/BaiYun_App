package com.baiyun.activity.home;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class TraGuideFragment extends BaseFragment{
	private VoHttpUtils httpUtils;
	private ProgressWebView webView;
	
	private ImageButton ibSearch;
	
	public static TraGuideFragment newInstance() {
		return new TraGuideFragment();
	}

	public TraGuideFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.webview;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new VoHttpUtils(getActivity());
		
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
		
		ibSearch = ((TrafficActivity)getActivity()).getTopBarRightImageButton();
		ibSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				webView.loadUrl("javascript:opentext()");
			}
		});

		getData();
	}

	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((TrafficActivity) getActivity()).setLoadingBarGone();
	}
	
	private void getData() {
		((TrafficActivity) getActivity()).setLoadingBarVisible();
		
		httpUtils.getVo1(HttpURL.TRAFFIC_GUIDE, new VoHttpUtils.OnGetVo1Listener() {
			
			@Override
			public void onGetVo1(Vo1Par vo1Par) {
				if (getActivity() != null) {
					((TrafficActivity) getActivity()).setLoadingBarGone();
				}
				if (vo1Par != null) {
					String urlLast = vo1Par.getUrl();
					if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
						String url = HttpURL.HOST+urlLast;
						webView.loadUrl(url);
					}else {
						Toast.makeText(getActivity(), "图文链接为空", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}
