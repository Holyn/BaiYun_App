package com.holyn.selectlocalpiclib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

public class QueryLocalPictureUtil {
	/**
	 * 根据指定的uri，查询图片在数据库中的信息
	 */
	public static LocalImageVo queryLocalImageVo(Context context, Uri uri) {
		ContentResolver mContentResolver = context.getContentResolver();
		Cursor cursor = mContentResolver.query(uri, null, null, null, null);

		LocalImageVo imageVo = new LocalImageVo();

		if (cursor.moveToFirst()) {

			int id = cursor.getInt(0);// 对应数据库横向第0个属性为id
			String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
			String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));// 图片大小，单位byte
			int bucketId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));// 文件夹的id
			String bucketName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));// 文件夹名称

			if (size == 0) {
				try {
					File file = new File(data);
					FileInputStream fis = new FileInputStream(file);
					size = fis.available();
					fis.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			imageVo.setId(id);
			imageVo.setName(name);
			imageVo.setPath(data);

			// 把以byte为单位的图片大小改为K
			// double doubleSize = (double) size / 1024;
			// BigDecimal bd = new BigDecimal(doubleSize);
			// bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			// doubleSize = Double.valueOf(bd.toString());
			// imageVo.setSize(doubleSize);

			imageVo.setSize(size);
			imageVo.setBucketId(bucketId);
			imageVo.setBucketName(bucketName);

			// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
			if (Build.VERSION.SDK_INT < 14) {
				if (!cursor.isClosed()) {
					cursor.close();
				}
			}

		}
		return imageVo;
	}

	/**
	 * 根据图片的id查询出图片的缩略图id
	 */
	public static String queryLocalThumbnails(Context context, String id) {
//		Bitmap bitmap = null;
		String path = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;

		ContentResolver mContentResolver = context.getContentResolver();
		String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA };
		Cursor cursor = mContentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, Thumbnails.IMAGE_ID + " = ?", new String[] { id }, null);
		System.out.println("====> cursor = " + cursor);

		if (cursor != null) {
			System.out.println("====> cursor.moveToFirst() = " + cursor.moveToFirst());
			if (cursor.moveToFirst()) {
				long thumbnailsId = cursor.getLong(cursor.getColumnIndex(Thumbnails._ID));
				path = cursor.getString(cursor.getColumnIndex(Thumbnails.DATA));
				System.out.println("====> thumnail path = " + path);

//				bitmap = Thumbnails.getThumbnail(mContentResolver, thumbnailsId, Thumbnails.MICRO_KIND, options);

			}
		} else {
			System.out.println("====> cursor = null");
		}

		// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
		if (Build.VERSION.SDK_INT < 14) {
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}

//		System.out.println("====> bitmap = " + bitmap);

		return path;

	}
}
