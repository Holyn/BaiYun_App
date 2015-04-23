package com.baiyun.custom;

import java.io.File;

import com.baiyun.activity.R;
import com.baiyun.cache.CachePath;
import com.baiyun.util.SystemUtil;
import com.baiyun.vo.parcelable.VersionPar;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class DialogFactory{
	/**
	 * 提示有更新，点击"马上更新"就显示apk下载进度
	 */
	public static void showVersionNotice(final Context context, final VersionPar versionPar) {
		new AlertDialog.Builder(context)
		.setIcon(R.drawable.ic_launcher)
		.setTitle("版本更新提示")
		.setMessage(versionPar.getContent())
		.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DialogFactory.showDownLoadProgress(context, versionPar);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.create().show();
	}
	
	/**
	 * 显示apk的下载进度
	 */
	public static void showDownLoadProgress(final Context context, VersionPar versionPar) {
        // 创建ProgressDialog对象  
		final ProgressDialog progressDialog = new ProgressDialog(context);                 
        // 设置进度条风格，风格为长形  
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
        // 设置ProgressDialog 标题  
        progressDialog.setTitle("温馨提示");                
        // 设置ProgressDialog 提示信息  
        progressDialog.setMessage("正在下载新的安装包...");                
        // 设置ProgressDialog 进度条进度  
        progressDialog.setProgress(100);              
        // 设置ProgressDialog 的进度条是否不明确  
        progressDialog.setIndeterminate(false);               
        // 设置ProgressDialog 是否可以按退回按键取消  
        progressDialog.setCancelable(true);               
        progressDialog.setIcon(R.drawable.ic_launcher);
        
        // 让ProgressDialog显示  
        progressDialog.show(); 
        
        String apkName = "BaiYun_"+versionPar.getLatestVersion()+".apk";
        final String apkFilePath = CachePath.getFilePath(CachePath.getApkCacheDir(), apkName);
        
        new HttpUtils().download(versionPar.getLatestUrl(), apkFilePath, new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> responseInfo) {
				// TODO Auto-generated method stub
				//引导安装apk
				SystemUtil.installApkIntent(context, apkFilePath);
				progressDialog.dismiss();
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				super.onLoading(total, current, isUploading);
				int proValue = (int)(current*100/total);
				progressDialog.setProgress(proValue);
			}
			
		});
	}
}
