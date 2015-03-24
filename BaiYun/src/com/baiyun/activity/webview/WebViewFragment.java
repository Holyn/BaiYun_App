package com.baiyun.activity.webview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.picturesviewer.PicturesViewPagerActivity;
import com.baiyun.vo.parcelable.Vo1Par;

/**
 * @author Holyn 注意：调用改fragment的activity必须继承BaseFragmentActivity
 * 
 */
public class WebViewFragment extends Fragment {
	public static final String KEY_HTTP_URL = "key_http_url";

	public View rootView;

	private VoHttpUtils httpUtils;
	private WebView webView;

	private String httpUrl;

	public static WebViewFragment newInstance() {
		return new WebViewFragment();
	}

	public WebViewFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		httpUrl = args.getString(KEY_HTTP_URL);
	}

	@SuppressLint("SetJavaScriptEnabled") @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		rootView = inflater.inflate(R.layout.webview, container, false);
		httpUtils = new VoHttpUtils(getActivity());

//		webView = (ProgressWebView) rootView.findViewById(R.id.webview);
		
		webView = new WebView(getActivity());

		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
//		webView.getSettings().setSupportZoom(true); // 可以缩放
		// webView.getSettings().setDefaultZoom(ZoomDensity.FAR);// 默认缩放模式
//		webView.getSettings().setUseWideViewPort(true); // 为图片添加放大缩小功能
//
//		webView.setDownloadListener(new DownloadListener() {
//			@Override
//			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//				if (url != null && url.startsWith("http://"))
//					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//			}
//		});

		getData();
		return webView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((BaseFragmentActivity) getActivity()).setLoadingBarGone();
	}

	@SuppressLint("JavascriptInterface") 
	private void getData() {
		((BaseFragmentActivity) getActivity()).setLoadingBarVisible();

		httpUtils.getVo1(httpUrl, new VoHttpUtils.OnGetVo1Listener() {

			@Override
			public void onGetVo1(Vo1Par vo1Par) {
				if (getActivity() != null) {
					((BaseFragmentActivity) getActivity()).setLoadingBarGone();
				}
				if (vo1Par != null) {
					String urlLast = vo1Par.getUrl();
					if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
						String url = HttpURL.HOST + urlLast;
						webView.loadUrl(url);
						
						// 添加js交互接口类，并起别名 imagelistner
						webView.addJavascriptInterface(new JavascriptInterface(getActivity()), "imagelistner");
						webView.setWebViewClient(new MyWebViewClient());
					} else {
						Toast.makeText(getActivity(), "图文链接为空", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		webView.loadUrl("javascript:(function(){" +
		"var objs = document.getElementsByTagName(\"img\"); " + 
				"for(var i=0;i<objs.length;i++)  " + 
		"{"
				+ "    objs[i].onclick=function()  " + 
		"    {  " 
				+ "        window.imagelistner.openImage(this.src);  " + 
		"    }  " + 
		"}" + 
		"})()");
	}

	// js通信接口
	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String img) {
			System.out.println(img);
			//
			ArrayList<String> imagePathList = new ArrayList<String>();
			imagePathList.add(img);
			Intent intent = new Intent(context, PicturesViewPagerActivity.class);
			intent.putStringArrayListExtra(PicturesViewPagerActivity.EXTRA_IMAGE_LIST, imagePathList);
			context.startActivity(intent);
		}
	}

	@SuppressLint("SetJavaScriptEnabled") public class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
//			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			addImageClickListner();
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	}
}
