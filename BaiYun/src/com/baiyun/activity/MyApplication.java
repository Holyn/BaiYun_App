package com.baiyun.activity;

/**
 * App的一些数据缓存，Application缓存在内存不足的时候会被杀毒软件清理
 * 数据传递，数据共享 等,数据缓存等操作
 * @author Holyn
 * @modified
 */

import com.baidu.frontia.FrontiaApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

public class MyApplication extends Application {
	private String cookie = null;
	
	private int curRecruitFragmentPosition = -1;//记住当前打开的ContainerFragment里面对应的Fragment位置

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
		FrontiaApplication.initFrontiaApplication(this);//百度推送声明
	}
	
	public int getCurRecruitFragmentPosition() {
		return curRecruitFragmentPosition;
	}
	public void setCurRecruitFragmentPosition(int position) {
		this.curRecruitFragmentPosition = position;
	}

	public static void initImageLoader(Context context) {

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.iv_photo_empty)
				.showImageForEmptyUri(R.drawable.iv_photo_empty)
				.showImageOnFail(R.drawable.iv_photo_empty).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
