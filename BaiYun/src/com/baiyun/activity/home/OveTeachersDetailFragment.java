package com.baiyun.activity.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.ProgressWebView;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.OveDepTeacherPar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author Holyn 注意：调用改fragment的activity必须继承BaseFragmentActivity
 * 
 */
public class OveTeachersDetailFragment extends Fragment {
	public static final String KEY_OveDepTeacherPar = "key_OveDepTeacherPar";

	public View rootView;

	private ProgressWebView webView;

	private OveDepTeacherPar teacherPar = null;
	
	private String urlLast;
	
	private ImageView ivPicture;
	private TextView tvTitle, tvBrief;

	public static OveTeachersDetailFragment newInstance() {
		return new OveTeachersDetailFragment();
	}

	public OveTeachersDetailFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		teacherPar = args.getParcelable(KEY_OveDepTeacherPar);
		urlLast = teacherPar.getContentUrl();
		
		((BaseFragmentActivity) getActivity()).setTopBarTitle(teacherPar.getTitle());
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_ove_teachers_detail, container, false);
		
		ivPicture = (ImageView)rootView.findViewById(R.id.iv_picture);
		String urlLast = teacherPar.getPicUrl();
		if (!TextUtils.isEmpty(urlLast)) {
			String picUrl = HttpURL.HOST+urlLast.substring(1);
			ImageLoader.getInstance().displayImage(picUrl, ivPicture);
		}
		
		tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
		tvTitle.setText(teacherPar.getTitle());
		
		tvBrief = (TextView)rootView.findViewById(R.id.tv_brief);
		tvBrief.setText(teacherPar.getBrief());
		
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
		return rootView;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((BaseFragmentActivity) getActivity()).setLoadingBarGone();
	}

	private void getData() {
		if (urlLast != null && !(urlLast.equalsIgnoreCase(""))) {
			String url = HttpURL.HOST + urlLast;
			webView.loadUrl(url);
		} else {
			Toast.makeText(getActivity(), "图文链接为空", Toast.LENGTH_SHORT).show();
		}
	}
}
