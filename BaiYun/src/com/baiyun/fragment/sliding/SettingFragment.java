package com.baiyun.fragment.sliding;

import java.io.File;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyun.activity.R;
import com.baiyun.activity.setting.SettingItemActivity;
import com.baiyun.baidu_push.BaiduPushManager;
import com.baiyun.base.BaseFragment;
import com.baiyun.cache.CachePath;
import com.baiyun.custom.DialogFactory;
import com.baiyun.custom.SlipButton;
import com.baiyun.httputils.SlideMenuHttpUtils;
import com.baiyun.sharepreferences.AppSettingSP;
import com.baiyun.util.SystemUtil;
import com.baiyun.vo.parcelable.VersionPar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class SettingFragment extends BaseFragment{
	private SlipButton sbChangePush;
	private TextView tvChangePwd;
	private TextView tvVersionUpdate;
	private TextView tvHelp;
	private TextView tvFeedback;
	private TextView tvAbout;
	
	public static SettingFragment newInstance() {
		return new SettingFragment();
	}
	
	public SettingFragment() {
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_setting;
	}

	@Override
	public void createMyView(View rootView) {
		initView(rootView);
		initListener();
	}
	
	private void initView(View rootView){
		sbChangePush = (SlipButton)rootView.findViewById(R.id.slip_utton);
		if (AppSettingSP.getSingleInstance(getActivity()).isBaiduPushEnable()) {
			sbChangePush.setCheck(true);
		}else {
			sbChangePush.setCheck(false);
		}
		
		tvChangePwd = (TextView)rootView.findViewById(R.id.tv_change_pwd);
		tvVersionUpdate = (TextView)rootView.findViewById(R.id.tv_version_update);
		tvHelp = (TextView)rootView.findViewById(R.id.tv_help);
		tvFeedback = (TextView)rootView.findViewById(R.id.tv_feedback);
		tvAbout = (TextView)rootView.findViewById(R.id.tv_about);
	}
	
	private void initListener(){
		sbChangePush.SetOnChangedListener(new SlipButton.onChangeListener() {
			
			@Override
			public void OnChanged(boolean CheckState) {
				AppSettingSP.getSingleInstance(getActivity()).setIsBaiduPushEnable(CheckState);
				if (!CheckState) {
					BaiduPushManager.stopWork(getActivity());
				}
			}
		});
		
		tvChangePwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.CHANGE_PWD);
				startActivity(intent);
			}
		});
		
		tvVersionUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkVersion();
			}
		});
		
		tvHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.HELP);
				startActivity(intent);
			}
		});
		
		tvFeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.FEEDBACK);
				startActivity(intent);
			}
		});
		
		tvAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SettingItemActivity.class);
				intent.putExtra(SettingItemActivity.EXTRA_ITEM_TYPE, SettingItemActivity.ABOUT);
				startActivity(intent);
			}
		});
	}
	
	private void checkVersion(){
		showLoadingDialog();
		try {
			String curVersionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
			final Float curVersionNameFloat = Float.parseFloat(curVersionName);
			
			new SlideMenuHttpUtils(getActivity()).getVersion(curVersionName, new SlideMenuHttpUtils.OnGetVersionListener() {
				
				@Override
				public void onGetVersion(VersionPar versionPar) {
					// TODO Auto-generated method stub
					if (versionPar != null) {
						String serVersionName = versionPar.getLatestVersion();
						Float serVersionNameFloat = Float.parseFloat(serVersionName);
						if (curVersionNameFloat < serVersionNameFloat) {//有新的版本
							DialogFactory.showVersionNotice(getActivity(), versionPar);
						}else {
							Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
						}
					}
					
					closeLoadingDialog();
				}
			});
			
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
