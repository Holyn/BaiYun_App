package com.holyn.selectlocalpiclib.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

public class TakePhotoUtil {
	// 存储图片的文件夹名称
	private static final String FILE_NAME = "holyn";
	
	private Context context;
	private String SDState;
	
	private File imgPathParent;// 拍照时图片存储的文件夹
	private String imgPath;// 拍照时图片存储的路径
	private Uri imgUri;// 拍照时图片的URI
	public TakePhotoUtil(Context context) {
		this.context = context;
		// 执行拍照前，应该先判断SD卡是否存在
		SDState = Environment.getExternalStorageState();
	}
	
	public File getImgPathParent() {
		File filePath = null;
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			filePath = getExternalStorageDir(FILE_NAME);
		}else {
			filePath = getCacheDir(context, FILE_NAME);
		}
		return filePath;
	}
	
	public String getImgPath() {
		imgPathParent = getImgPathParent();
		
		//规范化照片名称
		Date date = new Date();
		String dateStr = (new SimpleDateFormat("yyyyMMdd")).format(date);
		String timeStr = (new SimpleDateFormat("hhmmss")).format(date);
		String imgName = dateStr + "_" + timeStr + ".jpg";
		
		String path = getFilePath(imgPathParent, imgName);
		
		File imgFile = new File(path);
		if (!imgFile.exists()) {
			File imgFileParent = imgFile.getParentFile();
			imgFileParent.mkdirs();
		}
		return path;
	}

	public Uri getImgUri() {
		imgPath = getImgPath();
		
		imgUri = Uri.fromFile(new File(imgPath));
		
		return imgUri;
	}
	
	public static boolean isExist(String imgPath) {
		File imgFile = new File(imgPath);
		if (!imgFile.exists()) {
			return false;
		}else {
			return true;
		}
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
	
	/**
	 * 向内部存储存文件,缓存
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
	 * 外部存储
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
