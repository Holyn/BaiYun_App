package com.baiyun.activity;

import com.baiyun.sharepreferences.AppSettingSP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StartFragment extends Fragment{
	
	private View rootView;
	private AppSettingSP appSettingSP;
	
	public OnDelayListener onDelayListener;
	public interface OnDelayListener{
		public void onDelay(boolean isFinish);
	}

	public static StartFragment newInstance() {
		return new StartFragment();
	}
	
	public StartFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		if (!(activity instanceof OnDelayListener)) {
			throw new IllegalStateException("StartFragment所在的Activity必须实现OnDelayListener接口");
		}
		
		onDelayListener = (OnDelayListener)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_start, container, false);
		
		appSettingSP = AppSettingSP.getSingleInstance(getActivity());
		if (appSettingSP.getIsFirst()) {
			createShut();
		}
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				onDelayListener.onDelay(true);
			}
		}, 2000);
		
		return rootView;
	}

	/**
	 * 创建桌面快捷方式
	 */
	public void createShut() {
		// 创建添加快捷方式的Intent
		Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		addIntent.putExtra("duplicate", false); 
		String title = getResources().getString(R.string.app_name);
		// 加载快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(getActivity(), R.drawable.ic_launcher);
		// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
		Intent myIntent = new Intent(getActivity(), StartActivity.class);
		// 设置快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 设置快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 设置快捷方式对应的Intent
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// 发送广播添加快捷方式
		getActivity().sendBroadcast(addIntent);
	}
	
	
}
