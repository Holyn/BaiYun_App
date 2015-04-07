package com.baiyun.activity.schoolservice;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.custom.ButteryProgressBar;
import com.baiyun.util.ui.FragmentUtil;

public class SLostFoundActivity extends FragmentActivity{
	private FragmentManager fragmentManager;
	
	private TextView tvTitle = null;
	private ImageButton ibBack = null;
	private Button btnPublish;
	
	public FrameLayout loadingBar;
	
	private SLostFoundFragment lostFoundFragment = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_s_lost_found);
		
		fragmentManager = getSupportFragmentManager();
		
		//设置条形progressbar
		final ButteryProgressBar progressBar = new ButteryProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				10));
		loadingBar = (FrameLayout)findViewById(R.id.fl_progressbar);
		loadingBar.addView(progressBar);
		
		ibBack = (ImageButton)findViewById(R.id.ib_actionbar_back);
		ibBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		btnPublish = (Button)findViewById(R.id.btn_publish);
		btnPublish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		showLostFoundFragment();
	}

	private void showLostFoundFragment(){
		if (lostFoundFragment == null) {
			lostFoundFragment = SLostFoundFragment.newInstance();
		}
		FragmentUtil.replaceNormal(lostFoundFragment, fragmentManager, R.id.fl_container_common);
	}
	
	/**
	 * 设置TopBarTitle
	 */
	public void setTopBarTitle(String title){
		if (tvTitle == null) {
			tvTitle = (TextView)findViewById(R.id.tv_actionbar_title);
		}
		tvTitle.setText(title);
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
