package com.baiyun.activity.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.home.DepartmentActivity;
import com.baiyun.activity.home.JobActivity;
import com.baiyun.activity.home.NewsActivity;
import com.baiyun.activity.home.OverviewActivity;
import com.baiyun.activity.home.TrafficActivity;
import com.baiyun.activity.home.VideoActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.httputils.HomeHttpUtils;
import com.baiyun.kefu.KeFuManager;

public class HomeFragment_3 extends BaseFragment{
	private HomeHttpUtils httpUtils;

	private String onlineUrl = null;// 白云在线的跳转链接
	
	public static HomeFragment_3 newInstance() {
		return new HomeFragment_3();
	}

	public HomeFragment_3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HomeHttpUtils(getActivity());
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_home_3;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		getNetData();
	}
	
	private void initView(View rootView){
		((ImageView)rootView.findViewById(R.id.iv_summary)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), OverviewActivity.class));
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_traffic)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), TrafficActivity.class));
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_reports)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), NewsActivity.class));
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_job)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), JobActivity.class));
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_setting)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), DepartmentActivity.class));
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_consult)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new KeFuManager(getActivity()).startChat();
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_online)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onlineUrl != null) {
					if (!(onlineUrl.contains("http://"))) {
						onlineUrl = "http://" + onlineUrl;
					}
					new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("跳转到：" + onlineUrl)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (URLUtil.isNetworkUrl(onlineUrl)) {
										Uri uri = Uri.parse(onlineUrl);
										Intent intent = new Intent(Intent.ACTION_VIEW, uri);
										getActivity().startActivity(intent);
									} else {
										Toast.makeText(getActivity(), "网站链接不正确\n" + onlineUrl, Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", null).create().show();
				} else {
					Toast.makeText(getActivity(), "链接为空" + onlineUrl, Toast.LENGTH_SHORT).show();
				}
			}
		});
		((ImageView)rootView.findViewById(R.id.iv_video)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
			}
		});
	}
	
	private void getNetData() {
		httpUtils.getOnlineUrl(new HomeHttpUtils.OnGetOnlineUrlListener() {

			@Override
			public void onGetOnlineUrl(String url) {
				if (url != null) {
					onlineUrl = url;
				}

			}
		});
	}

}
