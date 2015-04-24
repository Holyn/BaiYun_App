package com.baiyun.picturesviewer;

import java.util.ArrayList;
import java.util.List;

import com.baiyun.activity.R;
import com.baiyun.http.HttpURL;
import com.baiyun.vo.parcelable.VoPicPar;
import com.nostra13.universalimageloader.core.ImageLoader;

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
import android.widget.TextView;

public class PicturesViewPagerActivity2 extends FragmentActivity {
	public static final String EXTRA_VO_PIC_PAR_LIST = "extra_vo_pic_par_list";
	public static final String EXTRA_CUR_POSITION = "extra_cur_position";
	
	private int curPosition = 0;
	private List<VoPicPar> voPicPars = new ArrayList<VoPicPar>();
	private int voSize = 0;
	
	private ViewPager viewPager;
	private PicturePagerAdapter pagerAdapter;

	private TextView tvContent;
	
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
		voPicPars = getIntent().getParcelableArrayListExtra(EXTRA_VO_PIC_PAR_LIST);
		voSize = voPicPars.size();
		curPosition = getIntent().getIntExtra(EXTRA_CUR_POSITION, 0);
	}

	private void initView() {
		tvContent = (TextView)findViewById(R.id.tv_content);
		tvContent.setVisibility(View.VISIBLE);
		
		viewPager = (ViewPager)findViewById(R.id.viewpager);
		
		//注意：这里不能使用getChildFragmentManager()，否则会报getFragment nullpointerexception 
		pagerAdapter = new PicturePagerAdapter(getSupportFragmentManager(), voPicPars);
		viewPager.setAdapter(pagerAdapter);	
		viewPager.setCurrentItem(curPosition);
		tvContent.setText((curPosition+1)+"/"+voSize+"  "+voPicPars.get(curPosition).getContent());
		
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	private void initGestureDetector(){
		onGestureListener = new GestureDetector.SimpleOnGestureListener(){

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				PicturesViewPagerActivity2.this.finish();
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
			tvContent.setText((position+1)+"/"+voSize+"  "+voPicPars.get(position).getContent());
		}
	}

	private class PicturePagerAdapter extends FragmentStatePagerAdapter {

		private List<VoPicPar> voPicPars;

		public PicturePagerAdapter(FragmentManager fm, List<VoPicPar> voPicPars) {
			super(fm);
			this.voPicPars = voPicPars;
		}

		@Override
		public int getCount() {
			return voPicPars.size();
		}

		@Override
		public Fragment getItem(int position) {
			Bundle args = new Bundle();
			
			VoPicPar picPar = voPicPars.get(position);
			if (picPar.getUrl() != null && !(picPar.getUrl().trim().equalsIgnoreCase(""))) {
				String picUrl = HttpURL.HOST+picPar.getUrl().substring(1);
				args.putString(PicturesViewPagerItemFragment.EXTRA_PICTURE_PATH, picUrl);
			}

			PicturesViewPagerItemFragment itemFragment = PicturesViewPagerItemFragment.newInstance();
			itemFragment.setArguments(args);
			
			return itemFragment;
		}

	}
}
