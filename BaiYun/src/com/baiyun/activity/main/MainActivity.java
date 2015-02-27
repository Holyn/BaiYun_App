package com.baiyun.activity.main;

import com.baiyun.activity.R;
import com.baiyun.base.BaseSlidingFragmentActivity;
import com.baiyun.fragment.sliding.AboutFragment;
import com.baiyun.fragment.sliding.HelpFragment;
import com.baiyun.fragment.sliding.LoginFragment;
import com.baiyun.fragment.sliding.SettingFragment;
import com.baiyun.fragment.sliding.ToolsFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends BaseSlidingFragmentActivity {
	private FragmentManager fragmentManager;
	
	private ContainerFragment containerFragment = null;
	private Fragment curFragment;
	
	private int curPosition = -1;
	private LoginFragment loginFragment = null;
	private ToolsFragment toolsFragment = null;
	private SettingFragment settingFragment = null;
	private HelpFragment helpFragment = null;
	private AboutFragment aboutFragment = null;
	
	@Override
	public void init() {
		setTopBarTitle("广州市白云工商技师学院");
		setTopBarRightBtnEnable(true);
        fragmentManager = getSupportFragmentManager(); 
        
        containerFragment = ContainerFragment.newInstance();
		curFragment = containerFragment;
		fragmentManager.beginTransaction().add(R.id.fl_container_common, curFragment).commit();
        
        
        initTopBarRightClickListener();
        initgetSlideMenuFramentListener();
	}

	private void initTopBarRightClickListener(){
        getTopBarRightImageButton().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showSlideMenuFragment();
			}
		});
    }
    
    private void initgetSlideMenuFramentListener(){
        getSlideMenuFragment().setOnSlideMenuFragmentEventListener(new SlideMenuFragment.OnSlideMenuFragmentEventListener() {
			
			@Override
			public void onSlideMenuFragmentEvent(int menuType) {
				setBackPressEnabled(true);
				switch (menuType) {
				case SlideMenuFragment.MENU_LOGIN:
					setTopBarTitle("用户登录");
					switchFragment(1);
					closeSlideMenuFragmetAndShowContent();
					break;
				case SlideMenuFragment.MENU_TOOLS:
					setTopBarTitle("实用工具");
					switchFragment(2);
					closeSlideMenuFragmetAndShowContent();
					break;
				case SlideMenuFragment.MENU_SETTING:
					setTopBarTitle("设置");
					switchFragment(3);
					closeSlideMenuFragmetAndShowContent();
					break;
				case SlideMenuFragment.MENU_HELP:
					setTopBarTitle("使用帮助");
					switchFragment(4);
					closeSlideMenuFragmetAndShowContent();
					break;
				case SlideMenuFragment.MENU_ABOUT:
					setTopBarTitle("关于我们");
					switchFragment(5);
					closeSlideMenuFragmetAndShowContent();
					break;
				case SlideMenuFragment.MENU_EXIT:
					new AlertDialog.Builder(MainActivity.this)
						.setTitle("温馨提示")
						.setMessage("确定退出应用吗？")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								appExit();
							}
						})
						.setNegativeButton("取消", null)
						.create().show();
					break;
				default:
					break;
				}
			}
		});
    }
    
	public void switchFragment(int position) {
		System.out.println("cur="+curPosition+" pos="+position);
		Fragment nextFragment = null;
		if (position == 0) {
			if (containerFragment == null) {
				containerFragment = ContainerFragment.newInstance();
			}
			nextFragment = containerFragment;
		}else if (position == 1) {
			if (loginFragment == null) {
				loginFragment = LoginFragment.newInstance();
			}
			nextFragment = loginFragment;
		}else if (position == 2) {
			if (toolsFragment == null) {
				toolsFragment = ToolsFragment.newInstance();
			}
			nextFragment = toolsFragment;
		}else if (position == 3) {
			if (settingFragment == null) {
				settingFragment = SettingFragment.newInstance();
			}
			nextFragment = settingFragment;
		}else if (position == 4) {
			if (helpFragment == null) {
				helpFragment = HelpFragment.newInstance();
			}
			nextFragment = helpFragment;
		}else if (position == 5) {
			if (aboutFragment == null) {
				aboutFragment = AboutFragment.newInstance();
			}
			nextFragment = aboutFragment;
		}
		
		if (curPosition != position) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			if (!nextFragment.isAdded()) {    // 先判断是否被add过  
                transaction.hide(curFragment).add(R.id.fl_container_common, nextFragment).commit(); // 隐藏当前的fragment，add下一个到Activity中  
			} else {  
                transaction.hide(curFragment).show(nextFragment).commit(); // 隐藏当前的fragment，显示下一个  
            } 
			/* ToolsFragment可能需要实时更新，所以在hide的同时remove*/
			if (curFragment == toolsFragment) {
				fragmentManager.beginTransaction().remove(curFragment).commit();
			}
			
			curPosition = position;
			curFragment = nextFragment;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (containerFragment.isHidden()) {
			switchFragment(0);
			containerFragment.setChildTitle();
			setBackPressEnabled(false);
		}else {
			super.onBackPressed();
		}
	}

	private void appExit(){//退出app
    	MainActivity.this.finish();
    	System.exit(0);
    }

}
