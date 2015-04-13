package com.baiyun.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseSlidingFragmentActivity;

public class ContainerFragment extends BaseFragment{
	private ImageView line_1,line_2,line_3,line_4;
	private RadioButton rb_1,rb_2,rb_3,rb_4;
	
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
//	private HomeFragment homeFragment = null;
//	private HomeFragment_2 homeFragment_2 = null;
	private HomeFragment_3 homeFragment_3 = null;
	private RecruitFragment recruitFragment = null;
	private SchoolLifeFragment schoolLifeFragment = null;
	private SchoolServiceFragment schoolServiceFragment = null;
	
	public static ContainerFragment newInstance() {
		return new ContainerFragment();
	}

	public ContainerFragment() {
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_main_container;
	}

	@Override
	public void createMyView(View rootView) {
		fragmentManager = getChildFragmentManager();
		
		line_1 = (ImageView)rootView.findViewById(R.id.rb_tab_line_1);
		line_2 = (ImageView)rootView.findViewById(R.id.rb_tab_line_2);
		line_3 = (ImageView)rootView.findViewById(R.id.rb_tab_line_3);
		line_4 = (ImageView)rootView.findViewById(R.id.rb_tab_line_4);
		
		rb_1 = (RadioButton)rootView.findViewById(R.id.rb_tab_home);
		rb_2 = (RadioButton)rootView.findViewById(R.id.rb_tab_recruit);
		rb_3 = (RadioButton)rootView.findViewById(R.id.rb_tab_life);
		rb_4 = (RadioButton)rootView.findViewById(R.id.rb_tab_service);
		
		homeFragment_3 = HomeFragment_3.newInstance();
		curFragment = homeFragment_3;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
		
		initRadioButtonListener();//设置监听底部的RadioButton
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		((BaseSlidingFragmentActivity)getActivity()).setBackPressEnabled(false);
	}
	
	/*
	 * 监听底部RadioButton的点击事件
	 */
	private void initRadioButtonListener(){
		rb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					((MainActivity)getActivity()).setTopBarTitle("广州市白云工商技师学院");
					line_1.setVisibility(View.VISIBLE);
					line_2.setVisibility(View.GONE);
					line_3.setVisibility(View.GONE);
					line_4.setVisibility(View.GONE);
					
					rb_1.setChecked(true);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(false);
					switchFragment(0);
				}
			}
		});
		
		rb_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					((MainActivity)getActivity()).setTopBarTitle("招生服务");
					line_1.setVisibility(View.GONE);
					line_2.setVisibility(View.VISIBLE);
					line_3.setVisibility(View.GONE);
					line_4.setVisibility(View.GONE);
					
					rb_1.setChecked(false);
					rb_2.setChecked(true);
					rb_3.setChecked(false);
					rb_4.setChecked(false);
					switchFragment(1);
				}
			}
		});
		
		/* 底部家园互动RadioButton监听 */
		rb_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					((MainActivity)getActivity()).setTopBarTitle("校园生活");
					line_1.setVisibility(View.GONE);
					line_2.setVisibility(View.GONE);
					line_3.setVisibility(View.VISIBLE);
					line_4.setVisibility(View.GONE);
					
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(true);
					rb_4.setChecked(false);
					switchFragment(2);
				}
			}
		});
		
		/* 底部发现RadioButton监听 */
		rb_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					((MainActivity)getActivity()).setTopBarTitle("校内服务");
					line_1.setVisibility(View.GONE);
					line_2.setVisibility(View.GONE);
					line_3.setVisibility(View.GONE);
					line_4.setVisibility(View.VISIBLE);
					
					rb_1.setChecked(false);
					rb_2.setChecked(false);
					rb_3.setChecked(false);
					rb_4.setChecked(true);
					switchFragment(3);
				}
			}
		});
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (homeFragment_3 == null) {
				homeFragment_3 = HomeFragment_3.newInstance();
			}
			nextFragment = homeFragment_3;
		}else if (position == 1) {
			if (recruitFragment == null) {
				recruitFragment = RecruitFragment.newInstance();
			}
			nextFragment = recruitFragment;
		}else if (position == 2) {
			if (schoolLifeFragment == null) {
				schoolLifeFragment = SchoolLifeFragment.newInstance();
			}
			nextFragment = schoolLifeFragment;
		}else if (position == 3) {
			if (schoolServiceFragment == null) {
				schoolServiceFragment = SchoolServiceFragment.newInstance();
			}
			nextFragment = schoolServiceFragment;
		}
		
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_container, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
            } else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			
			/**
			 * remove了对应的Fragment之后，那么下次切换到这个Fragment就会重新OnResum，不可见是OnPause
			 */
			if (curFragment == recruitFragment) {
				fragmentManager.beginTransaction().remove(curFragment).commit();
				recruitFragment = null;
			}
			
			curPosition = position;
			curFragment = nextFragment;
		}
	}
	
	/**
	 * 设置ContainerFragment嵌套的Fragment的title，供给依附的Activity中调用
	 */
	public void setChildTitle() {
		if (curPosition == 1) {
			((MainActivity)getActivity()).setTopBarTitle("招生服务");
		}else if (curPosition == 2) {
			((MainActivity)getActivity()).setTopBarTitle("校园生活");
		}else if (curPosition == 3) {
			((MainActivity)getActivity()).setTopBarTitle("校内服务");
		}else {
			((MainActivity)getActivity()).setTopBarTitle("广州市白云工商技师学院");
		}
	}
}
