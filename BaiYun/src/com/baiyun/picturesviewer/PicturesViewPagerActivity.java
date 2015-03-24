package com.baiyun.picturesviewer;

import java.util.ArrayList;
import java.util.List;

import com.baiyun.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PicturesViewPagerActivity extends FragmentActivity {
	public static final String EXTRA_IMAGE_LIST = "extra_image_list";
	public static final String EXTRA_CUR_POSITION = "extra_cur_position";
	public static final String EXTRA_IS_LOCAL = "extra_is_local";
	
	private List<String> imageList = new ArrayList<String>();
	private int curPosition = 0;
//	private boolean isLocal = true;
	
	private ViewPager viewPager;
	private PicturePagerAdapter pagerAdapter;

	private RadioGroup rgPoint;
	
	private GestureDetector gestureDetector;//声明手势全局变量 
	private GestureDetector.OnGestureListener onGestureListener;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pictures_view_pager);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		initData();
		initView();
		initGestureDetector();
	}
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		super.onPause();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}
	private void initData() {
		imageList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_LIST);
		curPosition = getIntent().getIntExtra(EXTRA_CUR_POSITION, 0);
//		isLocal = getIntent().getBooleanExtra(EXTRA_IS_LOCAL, true);
	}

	private void initView() {
		rgPoint = (RadioGroup) findViewById(R.id.rg_point);
		
		int size = imageList.size();
		if (size > 1) {
			rgPoint.setVisibility(View.VISIBLE);
			for (int i = 0; i < size; i++) {
				View rbPointRootView = LayoutInflater.from(this).inflate(R.layout.radio_button_point, rgPoint, false);
				RadioButton rbPoint = (RadioButton)rbPointRootView.findViewById(R.id.rb_point);
				rbPoint.setId(i);
				rgPoint.addView(rbPointRootView);
			}
			rgPoint.check(curPosition);
		}else {
			rgPoint.setVisibility(View.GONE);
		}
		
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		
		//注意：这里不能使用getChildFragmentManager()，否则会报getFragment nullpointerexception 
		pagerAdapter = new PicturePagerAdapter(getSupportFragmentManager(), imageList);
		viewPager.setAdapter(pagerAdapter);	
		viewPager.setCurrentItem(curPosition);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	private void initGestureDetector(){
		onGestureListener = new GestureDetector.SimpleOnGestureListener(){

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				PicturesViewPagerActivity.this.finish();
				return super.onSingleTapConfirmed(e);
			}
			
		};
		gestureDetector = new GestureDetector(this, onGestureListener);//初始化该变量 
		
	}
	
	
	/*
	 * 监听ViewPager的页面切换事件
	 */
	private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{

		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);
			rgPoint.check(((RadioButton)rgPoint.getChildAt(position)).getId());
		}
	}

	private class PicturePagerAdapter extends FragmentStatePagerAdapter {

		private List<String> imageList;

		public PicturePagerAdapter(FragmentManager fm, List<String> imageList) {
			super(fm);
			this.imageList = imageList;
		}

		@Override
		public int getCount() {
			return imageList.size();
		}

		@Override
		public Fragment getItem(int position) {

			PicturesViewPagerItemFragment itemFragment = PicturesViewPagerItemFragment.newInstance();
			Bundle args = new Bundle();
			args.putString(PicturesViewPagerItemFragment.EXTRA_PICTURE_PATH, imageList.get(position));
//			args.putBoolean(EXTRA_IS_LOCAL, isLocal);
			itemFragment.setArguments(args);
			
			return itemFragment;
		}

	}
}
