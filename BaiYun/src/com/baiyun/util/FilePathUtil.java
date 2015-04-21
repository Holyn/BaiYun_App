package com.baiyun.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 安卓手机文件缓存路径管理
 * 
 * @author Holyn
 * @create 2014-8-20
 * @modified
 * 
 */
public class FilePathUtil {
	/**
	 * 所有的Android设备都有两块文件存储区域：内部和外部存储。
	 * 内部存贮一般指设备自带的非易失性存储器，外部存储指可拆卸的存储介质，比如微型的SD卡。
	 * 一些设备把永久的存储区域分为"internal"和"external"的分区，所以即便没有可拆卸的存储介质，
	 * 这些设备永远都有两种存储区域，并且不管外部存储区到底是可拆卸的还是内置的，APIs的行为是一致的
	 * http://www.myexception.cn/android/1300531.html
	 */
	
	/**
	 * 1)、向内部存储存文件，存储
	 * getFilesDir()返回一个File，表示的是app的应用文件在内部存储的绝对路径。
	 * dirName: 文件夹（目录）名称,路径data/data/com..../files/dirName
	 *
	 */
	public static File getFileDir(Context context, String dirName) {
		File file = null;
		if (dirName == null) {
			file = context.getFilesDir();
		} else {
			file = new File(context.getFilesDir(), dirName);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 2)、向内部存储存文件,缓存
	 * getCacheDir()返回一个File，表示的是app的缓存文件在内部存储的绝对路径
	 * dirName: 文件夹（目录）名称,路径data/data/com..../cache/dirName
	 *
	 */
	public static File getCacheDir(Context context, String dirName) {
		File file = null;
		if (dirName == null) {
			file = context.getCacheDir();
		} else {
			file = new File(context.getCacheDir(), dirName);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 外部1，跟着app的卸载而清除
	 *通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
	 *通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
	 *如果使用上面的方法，当你的应用在被用户卸载后，SDCard/Android/data/你的应用的包名/ 这个目录下的所有文件都会被删除，不会留下垃圾信息。
	 *而且上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项
	 *
	 * DIRECTORY_ALARMS //警报的铃声
	 * DIRECTORY_DCIM //相机拍摄的图片和视频保存的位置
	 * DIRECTORY_DOWNLOADS //下载文件保存的位置
	 * DIRECTORY_MOVIES //电影保存的位置， 比如 通过google play下载的电影
	 * DIRECTORY_MUSIC //音乐保存的位置
	 * DIRECTORY_NOTIFICATIONS //通知音保存的位置
	 * DIRECTORY_PICTURES //下载的图片保存的位置
	 * DIRECTORY_PODCASTS //用于保存podcast(博客)的音频文件
	 * DIRECTORY_RINGTONES //保存铃声的位置
	 */
	public static File getExternalFileDir(Context context, String type, String dirName) {
		File file = null;
		if (dirName == null) {
			file = context.getExternalFilesDir(type);
		} else {
			file = new File(context.getExternalFilesDir(type), dirName);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 外部2，跟着app的卸载而清除
	 * 
	 */
	public static File getExternalCacheDir(Context context, String dirName) {
		File file = null;
		if (dirName == null) {
			file = context.getExternalCacheDir();
		} else {
			file = new File(context.getExternalCacheDir(), dirName);
		}

		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 外部3，不会跟着app的卸载而清除
	 * 
	 * 以前的Android(4.1之前的版本)中，SDcard跟路径通过“/sdcard”或者“/mnt/sdcard”来表示，
	 * 而在Jelly Bean系统中修改为了“/storage/sdcard0”，以后可能还会有多个SDcard的情况。
	 * 目前为了保持和之前代码的兼容，sdcard路径做了link映射。
	 * 为了使您的代码更加健壮并且能够兼容以后的Android版本和新的设备，
	 * 请通过Environment.getExternalStorageDirectory().getPath()来获取sdcard路径，如果您需要往sdcard中保存特定类型的内容，
	 * 可以考虑使用Environment.getExternalStoragePublicDirectory(String type)函数，该函数可以返回特定类型的目录，
	 * 目前支持如下类型：
	 * DIRECTORY_ALARMS //警报的铃声
	 * DIRECTORY_DCIM //相机拍摄的图片和视频保存的位置
	 * DIRECTORY_DOWNLOADS //下载文件保存的位置
	 * DIRECTORY_MOVIES //电影保存的位置， 比如 通过google play下载的电影
	 * DIRECTORY_MUSIC //音乐保存的位置
	 * DIRECTORY_NOTIFICATIONS //通知音保存的位置
	 * DIRECTORY_PICTURES //下载的图片保存的位置
	 * DIRECTORY_PODCASTS //用于保存podcast(博客)的音频文件
	 * DIRECTORY_RINGTONES //保存铃声的位置
	 * 
	 * Environment.getDownloadCacheDirectory().getPath():/cache 
	 * Environment.getDataDirectory().getPath():/data 
	 * Environment.getExternalStorageDirectory().getPath():/mnt/sdcard 
	 * Environment.getExternalStoragePublicDirectory().getPath():/mnt/sdcard/Pictures 
	 * Environment.getRootDirectory().getPath():/system 
	 */
	public static File getExternalStorageDir(String dirName) {
		File file = null;
		if (dirName == null) {
			file = Environment.getExternalStorageDirectory();
		} else {
			file = new File(Environment.getExternalStorageDirectory(), dirName);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
}
