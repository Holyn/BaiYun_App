package com.baiyun.baidu_push;

import android.app.Notification;
import android.content.Context;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baiyun.activity.R;
import com.baiyun.sharepreferences.AppSettingSP;

public class BaiduPushManager {
	
	public static void startWork(Context context) {
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
		// 这里把apikey存放于manifest文件中，只是一种存放方式，
		// 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,"api_key")
		
		if (AppSettingSP.getSingleInstance(context).isBaiduPushEnable()) {
			PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, BaiduPushUtils.getMetaValue(context, "api_key"));
			setNotificationBuilder(context);
		}
		
	}
	
	public static void stopWork(Context context) {
		PushManager.stopWork(context);
	}

	public static void setNotificationBuilder(Context context) {
		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(context, 
				R.layout.notification_custom_builder,
				R.id.notification_icon, R.id.notification_title, R.id.notification_text);
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(context.getApplicationInfo().icon);
		cBuilder.setLayoutDrawable(R.drawable.ic_launcher);
		PushManager.setNotificationBuilder(context, 1, cBuilder);
	}
}
