package com.baiyun.activity.webview;

import com.baiyun.activity.R;
import com.baiyun.http.HttpURL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * itent到此WebViewActiviry，
 * 需要putExtra两个参数：KEY_WEB_VIEW_TYPE与KEY_CONTENT_URL
 *
 */
public class WebViewActiviry extends FragmentActivity{
	public static final String KEY_WEB_VIEW_TYPE = "key_web_view_type";
	public static final String KEY_CONTENT_URL = "key_content_url";
	
	public static final int NEWS_DETAIL = 1;//标题为技师微报
	public static final int VIDEO = 2;//标题为：视频白云
	public static final int JOB_RECRUIT = 3;//标题为:招聘信息
	public static final int COOPERATION = 4;//标题为:校企合作
	public static final int RECRUIT_PLAN = 5;//标题为:招生计划
	public static final int RECRUIT_INTRO = 6;//标题为:专业介绍
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null;
	private ImageButton ibBack = null, ibTopRight = null;
	private ImageView ivLineLeft = null, ivLineRight = null;
	
	private ProgressWebView webView = null;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_webview);
		webView = (ProgressWebView)findViewById(R.id.webview);
		
		setBackPressEnabled(true);
		
		Intent intent = getIntent();
		int webViewType = intent.getIntExtra(KEY_WEB_VIEW_TYPE, 0);
		String contentUrl = intent.getStringExtra(KEY_CONTENT_URL);
		
		if (webViewType == NEWS_DETAIL) {
			setTopBarTitle("技师微报");
		}else if (webViewType == VIDEO) {
			setTopBarTitle("视频白云");
			webView.getSettings().setPluginState(PluginState.ON);
			
//			webView.getSettings().setPluginsEnabled(true);
			webView.getSettings().setAllowFileAccess(true);
			webView.getSettings().setDefaultTextEncodingName("GBK");
		}else if (webViewType == JOB_RECRUIT) {
			setTopBarTitle("招聘信息");
		}else if (webViewType == COOPERATION) {
			setTopBarTitle("校企合作");
		}else if (webViewType == RECRUIT_PLAN) {
			setTopBarTitle("招生计划");
		}else if (webViewType == RECRUIT_INTRO) {
			setTopBarTitle("专业介绍");
		}
		
		if (contentUrl == null) {
			Toast.makeText(this, "图文链接为空", Toast.LENGTH_SHORT).show();
		}else {
			if (contentUrl.equalsIgnoreCase("")) {
			}else {
				contentUrl = HttpURL.HOST+contentUrl;//构造完整Html5路径
//				System.out.println("====> WebView-URL: "+contentUrl);
				
				
				webView.getSettings().setJavaScriptEnabled(true);
				webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
				webView.getSettings().setSupportZoom(true); // 可以缩放
				webView.getSettings().setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
				webView.getSettings().setUseWideViewPort(true);  //为图片添加放大缩小功能
				
				webView.setDownloadListener(new DownloadListener() {
		            @Override
		            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
		                if (url != null && url.startsWith("http://"))
		                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		            }
		        });
				webView.loadUrl(contentUrl);
			}
		}
	}

	/**
	 * 默认TopBar是显示的
	 */
	public void setTopBarEnable(boolean isEnable) {
		if (flTopBar == null) {
			flTopBar = (FrameLayout)findViewById(R.id.fl_actionbar);
		}
		if (isEnable) {
			flTopBar.setVisibility(View.VISIBLE);
		}else {
			flTopBar.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 设置TopBarTitle
	 */
	public void setTopBarTitle(String title){
		if (tvTitle == null) {
			tvTitle = (TextView)findViewById(R.id.tv_actionbar_title);
		}
		tvTitle.setText(title);
	}
	
	/**
	 * 默认时回退按钮是不可见的
	 */
	public void setBackPressEnabled(boolean isEnable) {
		if (ibBack == null) {
			ibBack = (ImageButton)findViewById(R.id.ib_actionbar_back);
		}
		if (ivLineLeft == null) {
			ivLineLeft = (ImageView)findViewById(R.id.iv_actionbar_line_left);
		}
		if (isEnable) {
			ibBack.setVisibility(View.VISIBLE);
			ivLineLeft.setVisibility(View.VISIBLE);
			ibBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}else {
			ibBack.setVisibility(View.GONE);
			ivLineLeft.setVisibility(View.GONE);
		}
	}
	/**
	 * 默认顶部导航栏右边的菜单是不可见的
	 */
	public void setTopBarRightBtnEnable(boolean isEnable) {
		if (ibTopRight == null) {
			ibTopRight = (ImageButton)findViewById(R.id.ib_actionbar_right);
		}
		if (ivLineRight == null) {
			ivLineRight = (ImageView)findViewById(R.id.iv_actionbar_line_right);
		}
		if (isEnable) {
			ibTopRight.setVisibility(View.VISIBLE);
			ivLineRight.setVisibility(View.VISIBLE);
		}else {
			ibTopRight.setVisibility(View.GONE);
			ivLineRight.setVisibility(View.GONE);
		}
	}
	
	public ImageButton getTopBarRightImageButton() {
		if (ibTopRight == null) {
			setTopBarRightBtnEnable(true);
		}
		return ibTopRight;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		webView.reload();
		super.onPause();
	}
}
