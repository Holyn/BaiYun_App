package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.http.HttpURL;
import com.baiyun.httputils.VoHttpUtils;
import com.baiyun.vo.parcelable.Vo1Par;

public class TuitionFragment2 extends BaseFragment{
	
	private VoHttpUtils httpUtils;
	private ProgressWebView webView;
	
	private RadioGroup radioGroup;
	
	private String httpUrl = HttpURL.RECRUIT_INFOR_20;
	private List<Vo1Par> tuitionGuideList = new ArrayList<Vo1Par>();
	
	public static TuitionFragment2 newInstance() {
		return new TuitionFragment2();
	}
	
	public TuitionFragment2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_tuition_2;
	}

	@Override
	public void createMyView(View rootView) {
		httpUtils = new VoHttpUtils(getActivity());

		radioGroup = (RadioGroup)rootView.findViewById(R.id.rg_tuition);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_tuition_1:
					showTuitionGuide(0);
					break;
				case R.id.rb_tuition_2:
					showTuitionGuide(1);
					break;
				case R.id.rb_tuition_3:
					showTuitionGuide(2);
					break;
				default:
					break;
				}
			}
		});
		
		webView = (ProgressWebView) rootView.findViewById(R.id.webview);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
		webView.getSettings().setSupportZoom(true); // 可以缩放
//		webView.getSettings().setDefaultZoom(ZoomDensity.FAR);// 默认缩放模式
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
		((MainActivity) getActivity()).setLoadingBarGone();
	}

	private void getData() {
		((MainActivity) getActivity()).setLoadingBarVisible();
		httpUtils.getVo1List(httpUrl, new VoHttpUtils.OnGetVo1ListListener() {
			
			@Override
			public void onGetVo1List(List<Vo1Par> vo1Pars) {
				if (getActivity() != null) {
					((MainActivity) getActivity()).setLoadingBarGone();
				}
				if (vo1Pars != null) {
					tuitionGuideList.addAll(vo1Pars);
					radioGroup.check(R.id.rb_tuition_1);
				}
			}
		});
	}
	
	private void showTuitionGuide(int position){
		String urlLast = tuitionGuideList.get(position).getUrl();
		if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
			String url = HttpURL.HOST + urlLast;
			webView.loadUrl(url);
		} else {
			Toast.makeText(getActivity(), "图文链接为空", Toast.LENGTH_SHORT).show();
		}
	}
	
}
