package com.baiyun.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.custom.ButteryProgressBar;

public abstract class BaseFragmentActivity extends FragmentActivity{
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null;
	private ImageButton ibBack = null, ibTopRight = null;
	private ImageView ivLineLeft = null, ivLineRight = null;
	
	private Button btnMenu2 = null;//定制多一个菜单
	
	public FrameLayout loadingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common);
		
		//设置条形progressbar
		final ButteryProgressBar progressBar = new ButteryProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				10));
		loadingBar = (FrameLayout)findViewById(R.id.fl_progressbar);
		loadingBar.addView(progressBar);
		
		init();
	}
	
	/**
	 * 此方法在onCreate里面执行，唔需重写onCreate方法
	 * 已经setContentView(R.layout.activity_common);
	 */
	public abstract void init();
	
	/**
	 * 默认TopBar是显示的
	 */
	public void setTopBarEnable(boolean isEnable) {
		if (flTopBar == null) {
			flTopBar = (FrameLayout)findViewById(R.id.fl_actionbar);
		}
		if (isEnable) {
			flTopBar.setVisibility(View.VISIBLE);
		}else {
			flTopBar.setVisibility(View.GONE);
		}
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
	
	/**
	 * 默认时回退按钮是不可见的
	 */
	public void setBackPressEnabled(boolean isEnable) {
		if (ibBack == null) {
			ibBack = (ImageButton)findViewById(R.id.ib_actionbar_back);
		}
		if (ivLineLeft == null) {
			ivLineLeft = (ImageView)findViewById(R.id.iv_actionbar_line_left);
		}
		if (isEnable) {
			ibBack.setVisibility(View.VISIBLE);
			ivLineLeft.setVisibility(View.VISIBLE);
			ibBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}else {
			ibBack.setVisibility(View.GONE);
			ivLineLeft.setVisibility(View.GONE);
		}
	}
	/**
	 * 默认顶部导航栏右边的菜单是不可见的
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
	
	/**
	 * 默认顶部导航栏右边的第二个菜单是不可见的
	 */
	public void setBtnMenu2Enable(boolean isEnable){
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		if (isEnable) {
			btnMenu2.setVisibility(View.VISIBLE);
		}else {
			btnMenu2.setVisibility(View.GONE);
		}
	}
	
	public void setBtnMenu2Name(String menuName) {
		setBtnMenu2Enable(true);
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		if (!TextUtils.isEmpty(menuName)) {
			btnMenu2.setText(menuName);
		}
	}
	
	public ImageButton getTopBarRightImageButton() {
		if (ibTopRight == null) {
			setTopBarRightBtnEnable(true);
		}
		return ibTopRight;
	}
	
	public Button getBtnMenu2() {
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		return btnMenu2;
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
