package com.baiyun.util;

/**
 * 检查网络连接状况的工具类
 * @author Holyn
 * @create 2014-8-8 17:52:30
 * @modified
 */

import com.baiyun.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

public class NetWorkUtil {
	private static String  TAG = "NetWorkUtil";
	
	/** @Fields TYPE_NET_WORK_DISABLED : 网络不可用 */
	public static final int TYPE_DISABLED_NET = 0x00;
	/** @Fields TYPE_OTHER_NET : mobile 网络 */
	public static final int TYPE_MOBILE_NET = 0x01;
	/** @Fields TYPE_OTHER_NET : wifi 网络 */
	public static final int TYPE_WIFI_NET = 0x02;

	public static boolean isNetAvailable(final Context context) {
		if (checkNetWorkType(context) == TYPE_DISABLED_NET) {
			((Activity)context).runOnUiThread(new Runnable() {//在主线程里面Toast
				
				@Override
				public void run() {
					Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
//					if (context.getClass().getName().equalsIgnoreCase("com.krbb.activity.LoginActivity")) {
//						setNetwork(context);
//					}
				}
			});
			return false;
		}else {
			return true;
		}
	}
	
	public static int checkNetWorkType(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return TYPE_DISABLED_NET;
			}
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo == null) {
				// 注意一：
				// NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
				// 但是有些电信机器，仍可以正常联网，
				// 所以当成net网络处理依然尝试连接网络。
				// （然后在socket中捕捉异常，进行二次判断与用户提示）。
//				Log.i(TAG, "=====================>无网络");
				return TYPE_DISABLED_NET;
			} else {
				if (!networkInfo.isAvailable()) {
					return TYPE_DISABLED_NET;
				}
				// NetworkInfo不为null开始判断是网络类型
				int netType = networkInfo.getType();
				if (netType == ConnectivityManager.TYPE_WIFI) {
//					Log.i(TAG, "=====================>wifi网络");
					return TYPE_WIFI_NET;
				} else if (netType == ConnectivityManager.TYPE_MOBILE) {
//					Log.i(TAG, "=====================>wifi网络");
					return TYPE_MOBILE_NET;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return TYPE_DISABLED_NET;
		}
		return TYPE_DISABLED_NET;
	}
	/**
	 * 跳转到手机设置网络
	 */
	public static void setNetwork(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(R.string.netstate);
		builder.setMessage(R.string.setnetwork);
		builder.setPositiveButton(R.string.ok_set, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				try {
					context.startActivity(new Intent(Settings.ACTION_SETTINGS));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		builder.setNegativeButton(R.string.cancel_set, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create();
		builder.show();
	}
}
