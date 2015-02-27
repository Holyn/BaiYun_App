package com.baiyun.custom;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog extends ProgressDialog{
	
	public LoadingDialog(Context context,String message) {
		super(context);
		if (message == null) {
			message = "正在加载....";
		}
		this.setMessage(message);
		this.setIndeterminate(true);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}
	
}
