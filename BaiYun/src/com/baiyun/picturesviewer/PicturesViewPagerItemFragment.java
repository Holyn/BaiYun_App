package com.baiyun.picturesviewer;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

import com.baiyun.activity.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * 控制图片的异步加载，放大缩小操作等
 * @author Holyn
 * @create 2015-3-15
 * @modified
 */
public class PicturesViewPagerItemFragment extends Fragment{
	public static final String EXTRA_PICTURE_PATH = "extra_picture_path";//预览图片开始的位置
	private String picturePath;	
//	private boolean isLocal = true;
	
	private ImageView ivPhoto;
	private ProgressBar pbLoading;
	
	private PhotoViewAttacher mAttacher;

	public static PicturesViewPagerItemFragment newInstance() {
		return new PicturesViewPagerItemFragment();
	}
	
	public PicturesViewPagerItemFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		picturePath = args.getString(EXTRA_PICTURE_PATH);
//		isLocal = args.getBoolean(PicturesViewPagerActivity.EXTRA_IS_LOCAL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pictures_view_pager_item, container, false);
		ivPhoto = (ImageView)rootView.findViewById(R.id.iv_photo);
		mAttacher = new PhotoViewAttacher(ivPhoto);
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {//图片的点击事件
			
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				
			}
		});
		
		pbLoading = (ProgressBar)rootView.findViewById(R.id.pb_loading);
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		System.out.println("====> picturePath = "+picturePath);
		
		ImageLoader.getInstance().displayImage(picturePath, ivPhoto, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				pbLoading.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				pbLoading.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				pbLoading.setVisibility(View.GONE);
				mAttacher.update();
			}
		});
	}
}
