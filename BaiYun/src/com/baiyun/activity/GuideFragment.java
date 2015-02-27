package com.baiyun.activity;

import java.util.ArrayList;

import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BasePagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GuideFragment extends Fragment{
	private View rootView;
	
	private ViewPager viewPager;
	private BasePagerAdapter adapter;
	private ArrayList<View> views = new ArrayList<View>();

	private RadioGroup radioGroup;
	private RadioButton rb01, rb02, rb03;
	private Button btnEnter;
	private int  currentview = 0;
	
	public static GuideFragment newInstance() {
		return new GuideFragment();
	}

	public GuideFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_guide, container, false);
		initView();
		return rootView;
	}
	
	private void initView(){
		initViewPager();
		
		radioGroup = (RadioGroup)rootView.findViewById(R.id.rg_guide);
		rb01 = (RadioButton)rootView.findViewById(R.id.rb_guide_01);
		rb02 = (RadioButton)rootView.findViewById(R.id.rb_guide_02);
		rb03 = (RadioButton)rootView.findViewById(R.id.rb_guide_03);

		btnEnter = (Button)rootView.findViewById(R.id.btn_guide_enter);
		btnEnter.setVisibility(View.GONE);
		
		btnEnter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
				((StartActivity)getActivity()).finish();
			}
		});
	}
	
	private void initViewPager(){
		viewPager = (ViewPager)rootView.findViewById(R.id.vp_guide);
		for (int i = 0; i < 3; i++) {
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_guide, (ViewGroup)null);
			ImageView imageView = (ImageView) view.findViewById(R.id.iv_vp_guide);
			if (i == 0) {
				imageView.setImageResource(R.drawable.iv_guide_1);
			} else if (i == 1) {
				imageView.setImageResource(R.drawable.iv_guide_2);
			} else if (i == 2) {
				imageView.setImageResource(R.drawable.iv_guide_3);
			}
			views.add(view);
		}

		adapter = new BasePagerAdapter(views);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {
		private int currentPageScrollStatus;//记录page滑动状态，如果滑动了state就是1
		private boolean isLastPageSlideLeft = false;//是否是滑动到最后一个页面并且继续左滑

		public void onPageSelected(int arg0) {
			currentview = arg0;
			switch (arg0) {
			case 0:
				radioGroup.setVisibility(View.VISIBLE);
				rb01.setChecked(true);
				rb02.setChecked(false);
				rb03.setChecked(false);
				btnEnter.setVisibility(View.GONE);
				break;
			case 1:
				radioGroup.setVisibility(View.VISIBLE);
				rb01.setChecked(false);
				rb02.setChecked(true);
				rb03.setChecked(false);
				btnEnter.setVisibility(View.GONE);
				break;
			case 2:
				radioGroup.setVisibility(View.VISIBLE);
				rb01.setChecked(false);
				rb02.setChecked(false);
				rb03.setChecked(true);
				btnEnter.setVisibility(View.VISIBLE);
				break;
			}
		}

		/*
		 *arg0 :当前页面，及你点击滑动的页面
		 *arg1:当前页面偏移的百分比
		 *arg2:当前页面偏移的像素位置   
		 */
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			//当滑动到最后一个页面时，继续向左滑就跳转到登录页面
			if (currentview == 2) {
				if (arg1 == 0 && currentPageScrollStatus == 1) {
					isLastPageSlideLeft = true;
				}else {
					isLastPageSlideLeft = false;
				}
			}
		}
		/*
		 * arg0 ==1的时辰默示正在滑动，
		 * arg0==2的时辰默示滑动完毕了，
		 * arg0==0的时辰默示什么都没做。
		 * 1表示手有滑动，手滑动后如果页面也有滑动，那么1就会变成2，页面滑动停止后2再变成1
		 * 有时候手势滑动的但是页面并没有滑动，就像滑动到最后一页继续左滑的时候，那么arg0的值就由1直接变0
		 * 
		 */
		public void onPageScrollStateChanged(int arg0) {
			if (arg0 == 0 && isLastPageSlideLeft) {//滑动停止的时候跳转到登录页面
				getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
				((StartActivity)getActivity()).finish();
			}
			currentPageScrollStatus = arg0;
		}
	}
}
