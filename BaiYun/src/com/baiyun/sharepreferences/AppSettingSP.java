package com.baiyun.sharepreferences;

import com.baiyun.constants.Constants;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 使用SharePreference存储一些app的设置信息,xml的文件名为APP_SETTING = "appSetting"
 * 
 * @author Holyn
 * @create 2015-1-18
 * @modified
 */
public class AppSettingSP {
	public static AppSettingSP singleInstance;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public AppSettingSP(Context context) {
		sp = context.getSharedPreferences(Constants.APP_SETTING, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static synchronized AppSettingSP getSingleInstance(Context context) {
		if (singleInstance == null) {
			singleInstance = new AppSettingSP(context);
		}
		return singleInstance;
	}

	// 是否在后台运行标记
	public void setIsStart(boolean isStart) {
		editor.putBoolean("isStart", isStart);
		editor.commit();
	}

	public boolean getIsStart() {
		return sp.getBoolean("isStart", false);
	}

	// 是否第一次运行本应用
	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getIsFirst() {
		return sp.getBoolean("isFirst", true);
	}

	// 是否继续自动检查更新
	public void setIsAutoCheckUpdate(boolean isAutoCheckUpdate) {
		editor.putBoolean("isAutoCheckUpdate", isAutoCheckUpdate);
		editor.commit();
	}

	public Boolean isAutoCheckUpdate() {
		return sp.getBoolean("isAutoCheckUpdate", true);
	}

	// 是否启动百度推送
	public void setIsBaiduPushEnable(boolean isBaiduPushEnable) {
		editor.putBoolean("isBaiduPushEnable", isBaiduPushEnable);
		editor.commit();
	}

	public Boolean isBaiduPushEnable() {
		return sp.getBoolean("isBaiduPushEnable", true);
	}
}
