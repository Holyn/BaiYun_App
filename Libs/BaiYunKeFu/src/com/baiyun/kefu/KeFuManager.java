package com.baiyun.kefu;

import com.appkefu.lib.interfaces.KFAPIs;
import com.appkefu.lib.interfaces.KFCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class KeFuManager {
	private Context context;
	
	public KeFuManager(Context context) {
		this.context = context;
	}
	
	public static void login(Context context) {
		//登录方式
		KFAPIs.visitorLogin(context);
	}
	
	//1.咨询人工客服
	public void startChat()
	{
		//登录方式
		KFAPIs.visitorLogin(context);
		
		Bitmap kefuAvatarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.kefu);  
		Bitmap userAvatarBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.user);
		//
		KFAPIs.startChat(context, 
				"chen13590604841", 				//1. 客服工作组名称(请务必保证大小写一致)，请在管理后台分配
				"客服小秘书",				//2. 会话界面标题，可自定义
				null,			    	//3. 附加信息，在成功对接客服之后，会自动将此信息发送给客服;
										//   如果不想发送此信息，可以将此信息设置为""或者null
				false,					//4. 是否显示自定义菜单,如果设置为显示,请务必首先在管理后台设置自定义菜单,
										//	请务必至少分配三个且只分配三个自定义菜单,多于三个的暂时将不予显示 
										//	显示:true, 不显示:false
				5,						//5. 默认显示消息数量
				kefuAvatarBitmap,		//6. 修改默认客服头像，如果不想修改默认头像，设置此参数为null
				userAvatarBitmap,		//7. 修改默认用户头像, 如果不想修改默认头像，设置此参数为null
				true,					//8. 默认机器人应答
				new KFCallBack() {		//9. 会话页面右上角回调函数
					@Override
					public void OnTopDetailClicked() {
						// TODO Auto-generated method stub
//						Log.d("KFMainActivity", "右上角回调接口调用");
//						Toast.makeText(context, "右上角回调接口调用", Toast.LENGTH_SHORT).show();
						//测试右上角回调接口调用
						showTagList();
					}
				});
				
	}
	
	//显示标签列表
	private void showTagList()
	{
		Intent intent = new Intent(context, TagListActivity.class);
		context.startActivity(intent);
	}
}
