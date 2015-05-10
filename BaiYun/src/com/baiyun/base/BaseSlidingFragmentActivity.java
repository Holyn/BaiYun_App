package com.baiyun.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyun.activity.R;
import com.baiyun.activity.main.SlideMenuFragment;
import com.baiyun.custom.ButteryProgressBar;
import com.baiyun.util.ScreenUtil;
import com.holyn.slidingmenu.lib.SlidingMenu;
import com.holyn.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class BaseSlidingFragmentActivity extends SlidingFragmentActivity{
	
	protected SlidingMenu slidingMenu;
	private SlideMenuFragment slideMenuFragment;
	
	private FrameLayout flTopBar = null;
	private TextView tvTitle = null;
	private ImageButton ibBack = null, ibTopRight = null;
	private ImageView ivLineLeft = null, ivLineRight = null;
	
	private Button btnMenu2 = null;//定制多一个菜单
	private boolean isBtnMenu2Enable = false;
	
	public FrameLayout loadingBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common);
		
		initRightSlidingMenu();
		
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
	 * 读取Title
	 */
	public String getTopBarTitle(){
		if (tvTitle == null) {
			tvTitle = (TextView)findViewById(R.id.tv_actionbar_title);
		}
		String title = tvTitle.getText().toString();
		return title;
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
		isBtnMenu2Enable = isEnable;
		if (btnMenu2 == null) {
			btnMenu2 = (Button)findViewById(R.id.btn_menu_2);
		}
		if (isEnable) {
			btnMenu2.setVisibility(View.VISIBLE);
		}else {
			btnMenu2.setVisibility(View.GONE);
		}
	}
	
	public boolean isBtnMenu2Enable() {
		return isBtnMenu2Enable;
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
	
    private void initRightSlidingMenu() {
		setBehindContentView(R.layout.sliding_menu_layout);
		FragmentTransaction leftFragementTransaction = getSupportFragmentManager().beginTransaction();
		slideMenuFragment = SlideMenuFragment.newInstance();
		leftFragementTransaction.replace(R.id.fl_sliding_menu, slideMenuFragment);
		leftFragementTransaction.commit();
		// customize the SlidingMenu
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.RIGHT);// 设置是左滑
		slidingMenu.setBehindOffset(ScreenUtil.getScreenWidth(this)>>1);// 设置菜单宽度
		slidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);//设置手势模式
		slidingMenu.setShadowDrawable(R.drawable.yejk_shadow);// 设置左菜单阴影图片
		slidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		slidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
    }
    
    public void showSlideMenuFragment() {
		slidingMenu.showMenu();
	}
    
    public void closeSlideMenuFragmetAndShowContent() {
		slidingMenu.showContent();
	}
    
	public SlidingMenu getSlidingMenu() {
		return super.getSlidingMenu();
	}

	public SlideMenuFragment getSlideMenuFragment() {
		return slideMenuFragment;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	
}
