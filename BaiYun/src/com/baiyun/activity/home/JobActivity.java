package com.baiyun.activity.home;

import com.baiyun.activity.R;
import com.baiyun.custom.ButteryProgressBar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class JobActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{
	private FragmentManager fragmentManager;
	private Fragment curFragment;
	
	private int curPosition = -1;
	private JobSummaryFragment summaryFragment = null;
	private JobRecruitFragment recruitFragment = null;
	private JobCooperationFragment cooperationFragment = null;
	
	private RadioGroup rgTab;
	private ImageButton ibBack;
	public FrameLayout loadingBar;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_job);
		
		fragmentManager = getSupportFragmentManager();
		
		ibBack = (ImageButton)findViewById(R.id.ib_actionbar_back);
		ibBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		//设置条形progressbar
		final ButteryProgressBar progressBar = new ButteryProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,10));
		loadingBar = (FrameLayout)findViewById(R.id.fl_progressbar);
		loadingBar.addView(progressBar);
		
		rgTab = (RadioGroup)findViewById(R.id.rg_tab);
		rgTab.setOnCheckedChangeListener(this);
		
		summaryFragment = JobSummaryFragment.newInstance();
		curFragment = summaryFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_tab_1:
			switchFragment(0);
			break;
		case R.id.rb_tab_2:
			switchFragment(1);
			break;
		case R.id.rb_tab_3:
			switchFragment(2);
			break;
		default:
			break;
		}
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (summaryFragment == null) {
				summaryFragment = JobSummaryFragment.newInstance();
			}
			nextFragment = summaryFragment;
		}else if (position == 1) {
			if (recruitFragment == null) {
				recruitFragment = JobRecruitFragment.newInstance();
			}
			nextFragment = recruitFragment;
		}else if (position == 2) {
			if (cooperationFragment == null) {
				cooperationFragment = JobCooperationFragment.newInstance();
			}
			nextFragment = cooperationFragment;
		}
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_container, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
            } else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			curPosition = position;
			curFragment = nextFragment;
		}
		
	}
	
	public void setLoadingBarVisible() {
		loadingBar.setVisibility(View.VISIBLE);
	}
	
	public void setLoadingBarGone() {
		loadingBar.setVisibility(View.GONE);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
}
