package com.baiyun.cache;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.baiyun.util.FilePathUtil;

import android.content.Context;
import android.os.Environment;
/**
 * app缓存路径
 * @author Holyn
 * @create 2014-9-30
 * @modified
 */
public class CachePath {
	/** 外部存储的根目录下的文件夹名称 */
	public static final String OUT_CACHE_FILE_DIR = "baiyun";
	
	/* 1）
	 * 保存用户头像的文件夹名称，内部缓存
	 * FilePathUtil.getFileDir(this, "header");
	 * /data/data/com.krbb.activity/files/header
	 */
	private static final String HEADER = "header";
	
	/* 2）
	 * 保存app缓存的文件夹名称，内部缓存
	 * FilePathUtil.getCacheDir(this, "pictures");
	 * /data/data/com.krbb.activity/cache/pictures
	 */
	private static final String PICTURES = "pictures";
	
	/* 3）
	 * 保存App自动加载的图片的文件夹名称，外部存储，私有
	 * FilePathUtil.getExternalFileDir(this, Environment.DIRECTORY_PICTURES, null)：
	 * /mnt/sdcard/Android/data/com.krbb.activity/files/Pictures/
	 * 
	 */
	
	/* 4）
	 * 保存App的文件夹名称，外部存储，私有
	 *  FilePathUtil.getExternalCacheDir(this,null);
	 * /mnt/sdcard/Android/data/com.krbb.activity/cache/
	 * 
	 */
	
	/* 5）
	 * 保存用户手动下载的图片的文件夹名称，外部存储，公有
	 * FilePathUtil.getExternalStorageDir("yehb/pictures");
	 *  /mnt/sdcard/yehb/pictures
	 */
	private static final String OUT_CACHE_PICTURES = OUT_CACHE_FILE_DIR+"/pictures";
	/* 6）
	 * 保存用户手动下载的文件的文件夹名称，外部存储，公有
	 * FilePathUtil.getExternalStorageDir("yehb/files");
	 *  /mnt/sdcard/yehb/files
	 */
	private static final String OUT_CACHE_FILES = OUT_CACHE_FILE_DIR+"/files";//保存用户下载的文件的文件夹名称，外部存储，公有
	
	/* 7）
	 * 保存程序的崩溃信息，外部存储，公有
	 * FilePathUtil.getExternalStorageDir("yehb/crash");
	 *  /mnt/sdcard/yehb/files
	 */
	private static final String OUT_CACHE_CRASH= OUT_CACHE_FILE_DIR+"/crash";//保存程序的崩溃信息的（.log）文件夹名称，外部存储，公有
	
	//保存头像，自动，内部存储，不公开，使用 1）
	public static File getHeaderDir(Context context) {
		return FilePathUtil.getFileDir(context, HEADER);
		
	}
	
	//缓存例如浏览相册时的图片，自动，内部存储，不公开，使用 2）
	public static File getImgCacheDir(Context context) {
		return FilePathUtil.getCacheDir(context, PICTURES);
		
	}
	
	//缓存下载的apk文件，app卸载的时候，apk跟着清除 ，使用3）
	public static File getApkCacheDir(Context context) {
		return FilePathUtil.getExternalFileDir(context, Environment.DIRECTORY_DOWNLOADS, "apk");
	}
	
	//缓存程序所需的文件，自动，外部存储，私有，可公开，但跟随app卸载而清除，使用 4）
	public static File getFileCacheDir(Context context) {
		return FilePathUtil.getExternalCacheDir(context,null);
		
	}
	
	//保存下载的图片，手动，外部存储，公开，不跟随app卸载而清除，使用 5）
	public static File getImgDownloadDir() {
		return FilePathUtil.getExternalStorageDir(OUT_CACHE_PICTURES);
		
	}
	
	//保存下载的文件，手动，外部存储，公开，不跟随app卸载而清除，使用 6）
	public static File getFileDownloadDir() {
		return FilePathUtil.getExternalStorageDir(OUT_CACHE_FILES);
		
	}
	
	//缓存下载的apk文件，手动，外部存储，公开，不跟随app卸载而清除，使用 6）
	public static File getApkCacheDir() {
		return FilePathUtil.getExternalStorageDir(OUT_CACHE_FILES);
	}
	
	//保存app崩溃的信息，手动，外部存储，公开，不跟随app卸载而清除，使用 6）
	public static File getCrashFileDir() {
		return FilePathUtil.getExternalStorageDir(OUT_CACHE_CRASH);
		
	}
	
	/**
	 * 获取文件名的路径
	 * dirPath: 文件夹（目录）
	 * fileName: 文件名,路径..../fileName
	 * fileName的格式可以是xxx.txt等，即可带后缀，保存图片的时候可不带后缀
	 *
	 */
	public static String getFilePath(File dirPath, String fileName) {
		try {
			String filePath = dirPath.getAbsolutePath() + File.separator + URLEncoder.encode(fileName.replace("*", ""), "UTF-8");
			return filePath;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

		return null;
		
	}
}
