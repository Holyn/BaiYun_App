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
import android.widget.ImageView;
import android.widget.RadioGroup;

public class TrafficActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
	private FragmentManager fragmentManager;
	private Fragment curFragment;

	private int curPosition = -1;
	private TraGuideFragment guideFragment = null;
	private TraSearchFragment searchFragment = null;

	private RadioGroup rgTab;
	private ImageButton ibBack;
	public FrameLayout loadingBar;
	
	private ImageButton ibTopRight = null;
	private ImageView ivLineRight = null;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_traffic);

		fragmentManager = getSupportFragmentManager();

		ibBack = (ImageButton) findViewById(R.id.ib_actionbar_back);
		ibBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		// 设置条形progressbar
		final ButteryProgressBar progressBar = new ButteryProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
		loadingBar = (FrameLayout) findViewById(R.id.fl_progressbar);
		loadingBar.addView(progressBar);

		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
		rgTab.setOnCheckedChangeListener(this);
		
		guideFragment = TraGuideFragment.newInstance();
		curFragment = guideFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container, curFragment).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_tab_1:
			switchFragment(0);
			setTopBarRightBtnEnable(true);
			break;
		case R.id.rb_tab_2:
			switchFragment(1);
			setTopBarRightBtnEnable(false);
			break;
		default:
			break;
		}
	}
	
	public void switchFragment(int position) {
		Fragment nextFragment = null;
		if (position == 0) {
			if (guideFragment == null) {
				guideFragment = TraGuideFragment.newInstance();
			}
			nextFragment = guideFragment;
		}else if (position == 1) {
			if (searchFragment == null) {
				searchFragment = TraSearchFragment.newInstance();
			}
			nextFragment = searchFragment;
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
	
	/**
	 * 设置顶部导航栏右边的菜单是否可见，默认为可见
	 */
	public void setTopBarRightBtnEnable(boolean isEnable) {
		if (ibTopRight == null) {
			ibTopRight = (ImageButton)findViewById(R.id.ib_actionbar_right);
		}
		if (ivLineRight == null) {
			ivLineRight = (ImageView)findViewById(R.id.iv_actionbar_line_right);
		}
		if (isEnable) {
			ibTopRight.setVisibility(View.VISIBLE);
			ivLineRight.setVisibility(View.VISIBLE);
		}else {
			ibTopRight.setVisibility(View.GONE);
			ivLineRight.setVisibility(View.GONE);
		}
	}
	
	public ImageButton getTopBarRightImageButton() {
		if (ibTopRight == null) {
			setTopBarRightBtnEnable(true);
		}
		return ibTopRight;
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
