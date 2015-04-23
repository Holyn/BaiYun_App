package com.baiyun.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SystemUtil {
	// 关闭软键盘
	public static void hideSoftInput(Context context) {
		View v = ((ActionBarActivity) context).getCurrentFocus();
		if (v != null) {
			InputMethodManager im = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(v.getApplicationWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/*
	 * 引导安装apk，apkFilePath路径下必须存在已经下载完整的apk文件
	 */
	public static void installApkIntent(Context context, String apkFilePath) {
		Uri uri = Uri.fromFile(new File(apkFilePath));
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(installIntent);
	}
}
