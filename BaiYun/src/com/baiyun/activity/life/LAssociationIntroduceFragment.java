package com.baiyun.activity.life;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragment;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class LAssociationIntroduceFragment extends BaseFragment{
	public static final String KEY_URL = "key_url";
	
	private String urlLast;//不完整的url的后面部分
	private ProgressWebView webView;
	
	public static LAssociationIntroduceFragment newInstance() {
		return new LAssociationIntroduceFragment();
	}

	public LAssociationIntroduceFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		urlLast = getArguments().getString(KEY_URL);
//		System.out.println("====> urlLast = "+urlLast);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.webview;
	}

	@Override
	public void createMyView(View rootView) {
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

		if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
			String url = HttpURL.HOST+urlLast;
			webView.loadUrl(url);
		}else {
			Toast.makeText(getActivity(), "图文链接为空", Toast.LENGTH_SHORT).show();
		}
	}
	
}
