package com.holyn.selectlocalpiclib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureCompressUtil {
	
	public static PictureCompressUtil pictureCompressUtil = null;
	
	public static PictureCompressUtil getInstance(){
		if (pictureCompressUtil == null) {
			pictureCompressUtil = new PictureCompressUtil();
		}
		return pictureCompressUtil;
	}
	
	public PictureCompressUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 图片压缩
	 * 
	 * @param path
	 * @param reqWidth
	 * @param reqHeight
	 * @param reqSize 要求的图片大小，单位kb
	 * @return
	 */
	
	public Bitmap compress(String path, int reqWidth, int reqHeight,int reqSize) {
		Bitmap bitmap = null;
		try {
			bitmap = optionsDecode(path, reqWidth, reqHeight);
			bitmap = qualityCompress(bitmap, reqSize);
		} catch (Exception e) {
			bitmap = null;
		}
		return bitmap;
		
	}
	
	
	/**
	 * 比例压缩，根据计算的inSampleSize，得到压缩后图片
	 */
	private Bitmap optionsDecode(String path, int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		return bitmap;
	}
	
	/**
	 * 质量压缩，压缩到要求的size
	 * 
	 * 注：当压缩前的大小与要求压缩的大小相差较大时，此方法执行的时间也较长
	 */
	private static Bitmap qualityCompress(Bitmap bitmap,int reqSize) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		System.out.println("====> 压缩前大小：kb = "+baos.toByteArray().length / 1024);
		while (baos.toByteArray().length / 1024 > reqSize) { // 循环判断如果压缩后图片是否大于1M,大于继续压缩
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 2;// 每次都减少10
			// System.out.println("====> 循环压缩大小：kb = " + baos.toByteArray().length/1024);
		}
		byte[] bytes = baos.toByteArray();
		System.out.println("====> 压缩后大小：kb = "+bytes.length/1024);
		
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中

		Bitmap newBitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//		System.out.println("====> 压缩后大小：isBm = "+isBm.available());

		return newBitmap;
		
	}
	
	
	/**
	 * 计算inSampleSize，用于压缩图片
	 * 
	 * reqWidth：要求图片的最大宽度
	 * reqHeight: 要求图片的最大高度
	 */
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的宽度
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;

		if (reqWidth == 0 || reqHeight == 0) {
			return 1;
		}
		
		if (width > reqWidth && height > reqHeight) {
			// 计算出实际宽度和目标宽度的比率
			int widthRatio = Math.round((float) width / (float) reqWidth);
			int heightRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = Math.max(widthRatio, heightRatio);
			return inSampleSize;
		}
		
		if (width > reqWidth) {
			inSampleSize = Math.round((float) width / (float) reqWidth);
			return inSampleSize;
		}
		
		if (height > reqHeight) {
			inSampleSize = Math.round((float) height / (float) reqHeight);
		}
		
		return inSampleSize;
	}
}
