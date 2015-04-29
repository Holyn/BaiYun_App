package com.holyn.selectlocalpiclib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

public class QueryLocalPictureHandler extends Handler{
	public static final int TYPE_GET_LOCAL_ALBUMS = 1;
	
	private Context context;
	
	public QueryLocalPictureHandler(Context context) {
		this.context = context;
	}
	
	public void excute() {
		new Thread(new GetLocalAlbumVosRunnable()).start();
	}
	
	protected void finishGetLocalAlbumVos(List<LocalAlbumVo> localAlbumVos){
		
	}
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		switch (msg.what) {
		case TYPE_GET_LOCAL_ALBUMS:
			List<LocalAlbumVo> localAlbumVos = (List<LocalAlbumVo>)msg.obj;
			finishGetLocalAlbumVos(localAlbumVos);
			break;

		default:
			break;
		}
		
	}
	
	private class GetLocalAlbumVosRunnable implements Runnable{

		@Override
		public void run() {
			List<LocalAlbumVo> albumVos = queryAllLocalImages(context);
			Message msg = new Message();
			msg.what = TYPE_GET_LOCAL_ALBUMS;
			msg.obj = albumVos;
			
			QueryLocalPictureHandler.this.sendMessage(msg);
			
		}
		
	}
	
	/**
	 * 查找本地的图片存储数据库，按时间的先后排序
	 */
	public List<LocalAlbumVo> queryAllLocalImages(Context context) {

		List<LocalAlbumVo> localAlbumVos = new ArrayList<LocalAlbumVo>();
		
		//localAlbumVos的第一个item保存当前手机所有相册内最新的前100图片
		LocalAlbumVo zheNewPictures = new LocalAlbumVo();
		zheNewPictures.setLocalImageVos(new ArrayList<LocalImageVo>());
		zheNewPictures.setId(-1111);
		zheNewPictures.setName("最新图片");
		localAlbumVos.add(zheNewPictures);
		
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;// 查询本地图片数据库对应的uri

		/**
		 * 临时的辅助类
		 * Integer 为图片list所在的文件夹的id：MediaStore.Images.Media.BUCKET_ID
		 */
		HashMap<Integer, List<LocalImageVo>> imgListMap = new HashMap<Integer, List<LocalImageVo>>();

		// imgListMap.containsKey(key);

		ContentResolver mContentResolver = context.getContentResolver();
		// 只查询jpeg和png的图片
		Cursor  cursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
				+ MediaStore.Images.Media.MIME_TYPE + "=?", new String[] { "image/jpeg", "image/png" }, 
				MediaStore.Images.Media.DATE_ADDED + " DESC");
		
		cursor.moveToFirst();
		
//		System.out.println("====> cursor.getCount() = "+cursor.getCount());
		
		int index = 0;
		do {
			
			LocalImageVo imageVo = new LocalImageVo();
			int id = cursor.getInt(0);//对应数据库横向第0个属性为id
			String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
			String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));//图片大小，单位byte
			int bucketId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));//文件夹的id
			String bucketName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));//文件夹名称
			
			imageVo.setId(id);
			imageVo.setName(name);
			imageVo.setPath(data);
			
			//把以byte为单位的图片大小改为K
//			double doubleSize = (double) size / 1024;
//			BigDecimal bd = new BigDecimal(doubleSize);
//			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//			doubleSize = Double.valueOf(bd.toString());
//			imageVo.setSize(doubleSize);
			
			imageVo.setSize(size);
			imageVo.setBucketId(bucketId);
			imageVo.setBucketName(bucketName);
			
			//把最新的前100张图片归纳到一个新的相册：“最新图片”
			if (index < 100) {
//				System.out.println("====>（index < 100） = "+imageVo.getBucketId());
				LocalImageVo zheNew100ImageVo = new LocalImageVo();
				zheNew100ImageVo.setId(id);
				zheNew100ImageVo.setName(name);
				zheNew100ImageVo.setPath(data);
				
				//把以byte为单位的图片大小改为K
//				double doubleSize = (double) size / 1024;
//				BigDecimal bd = new BigDecimal(doubleSize);
//				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//				doubleSize = Double.valueOf(bd.toString());
//				imageVo.setSize(doubleSize);
				
				zheNew100ImageVo.setSize(size);
				zheNew100ImageVo.setBucketId(bucketId);
				zheNew100ImageVo.setBucketName(bucketName);
				
//				for (int i = 0; i < localAlbumVos.get(0).getLocalImageVos().size(); i++) {
//					if (localAlbumVos.get(0).getLocalImageVos().get(i).getId() == id) {
//						System.out.println("====> 艹---图片的id竟然会相等....");
//					}
//				}
				
				localAlbumVos.get(0).getLocalImageVos().add(zheNew100ImageVo);
				if (index == 0) {
					localAlbumVos.get(0).setCoverPath(data);
				}

			}
			
			if (imgListMap.containsKey(bucketId)) {
//				System.out.println("====>是imgListMap.containsKey(bucketId) = "+imageVo.getBucketId());
//				List<LocalImageVo> tempAddImageVos = ;
				imgListMap.get(bucketId).add(imageVo);
//				imgListMap.put(bucketId, tempAddImageVos);//不知道add之后还要不要put,不要
			}else {
//				System.out.println("====>非imgListMap.containsKey(bucketId) = "+imageVo.getBucketId());
				List<LocalImageVo> firstAddImageVos = new ArrayList<LocalImageVo>();
				firstAddImageVos.add(imageVo);
				imgListMap.put(bucketId, firstAddImageVos);
				
				LocalAlbumVo localAlbumVo = new LocalAlbumVo();
				localAlbumVo.setId(bucketId);
				localAlbumVo.setName(bucketName);
				localAlbumVos.add(localAlbumVo);
			}
			
			index ++;
		} while (cursor.moveToNext());
		
		
        //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)  
        if(Build.VERSION.SDK_INT < 14)  
        {  
			if (!cursor.isClosed()) {
				cursor.close();
			}
        } 
		
		int albumSize = localAlbumVos.size();//相册列表长度
		for (int i = 1; i < albumSize; i++) {//从1开始，因为0属于自定义的相册
			int albumId = localAlbumVos.get(i).getId();
			if (imgListMap.containsKey(albumId)) {
				List<LocalImageVo> tempImageVos = imgListMap.get(albumId);
				localAlbumVos.get(i).setCoverPath(tempImageVos.get(0).getPath());
				localAlbumVos.get(i).setLocalImageVos(tempImageVos);
			}
		}
		
		imgListMap.clear();

		return localAlbumVos;

	}
}
