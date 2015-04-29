package com.holyn.selectlocalpiclib.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class MediaScannerUtil {
	private Context context;

	private MediaScannerConnection mediaScanConn = null;

	private MusicSannerClient client = null;

	private String filePath = null;

	private String fileType = null;

	private String[] filePaths = null;
	
	public OnMediaScannnerListener onMediaScannerListener;
	
	public interface OnMediaScannnerListener{
		public void onMediaScanner(Uri uri);
	}

	public void setOnMediaScannerListener(OnMediaScannnerListener onMediaScannerListener) {
		this.onMediaScannerListener = onMediaScannerListener;
	}

	public MediaScannerUtil(Context context) {
		this.context = context;
		// 创建MusicSannerClient
		if (client == null) {

			client = new MusicSannerClient();
		}

		if (mediaScanConn == null) {

			mediaScanConn = new MediaScannerConnection(context, client);
		}
	}

	public class MusicSannerClient implements
			MediaScannerConnection.MediaScannerConnectionClient {
		public void onMediaScannerConnected() {
//			System.out.println("连接媒体扫描器......");
			if (filePath != null) {

				mediaScanConn.scanFile(filePath, fileType);
			}

			if (filePaths != null) {

				for (String file : filePaths) {

					mediaScanConn.scanFile(file, fileType);
				}
			}

			filePath = null;

			fileType = null;

			filePaths = null;
		}

		public void onScanCompleted(String path, Uri uri) {
//			System.out.println("扫描完毕，断开媒体扫描器......");
			onMediaScannerListener.onMediaScanner(uri);
			mediaScanConn.disconnect();
		}

	}

	/**
	 * 扫描文件标签信息
	 * 
	 * @param filePath
	 *            文件路径 eg:/sdcard/MediaPlayer/dahai.mp3
	 * @param fileType
	 *            文件类型 eg: audio/mp3 media/* application/ogg
	 * */
	public void scanFile(String filepath, String fileType) {

		this.filePath = filepath;

		this.fileType = fileType;
		// 连接之后调用MusicSannerClient的onMediaScannerConnected()方法
		mediaScanConn.connect();
	}

	/**
	 * @param filePaths
	 *            文件路径
	 * @param fileType
	 *            文件类型
	 * */
	public void scanFile(String[] filePaths, String fileType) {

		this.filePaths = filePaths;

		this.fileType = fileType;

		mediaScanConn.connect();

	}

	public String getFilePath() {

		return filePath;
	}

	public void setFilePath(String filePath) {

		this.filePath = filePath;
	}

	public String getFileType() {

		return fileType;
	}

	public void setFileType(String fileType) {

		this.fileType = fileType;
	}

	/**
	 * 下面是另外一种调用媒体扫描器的方式，在发送广播让扫描器去扫描文件没问题， 但是在监听扫描器返回来的信息的时候出问题，不知道原因何在
	 */
	public void sendBroadcastToScanner() {
		System.out.println("sendBroadcast uri = "
				+ Uri.parse("file://" + filePath));
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.parse("file://" + filePath)));
	}

	public void registerScannerBroadcast() {
		// 注册MediaScanner的接收器
		IntentFilter filter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter.addDataScheme("file");
		System.out.println("注册MediaScanner的接收器");
		BroadcastReceiver scanReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				System.out.println("onReceive action = " + action);
				if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
					System.out.println("扫描开始......");
				}
				if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
					System.out.println("扫描结束......");
				}

				// 此语句有待验证
				context.unregisterReceiver(this);
			}
		};
		context.registerReceiver(scanReceiver, filter);
	}
}
