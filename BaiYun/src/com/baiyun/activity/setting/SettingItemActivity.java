package com.baiyun.activity.setting;

import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.fragment.sliding.AboutFragment;
import com.baiyun.fragment.sliding.HelpFragment;
import com.baiyun.util.ui.FragmentUtil;

public class SettingItemActivity extends BaseFragmentActivity{
	public static final String EXTRA_ITEM_TYPE = "extra_item_type";
	
	public static final int CHANGE_PWD = 1;
	public static final int HELP = 2;
	public static final int FEEDBACK = 3;
	public static final int ABOUT = 4;
	
	private int itemType = 1;
	
	private FragmentManager fragmentManager;
	private ChangePwdFragment changePwdFragment = null;
	private HelpFragment helpFragment = null;
	private FeedbackFragment feedbackFragment = null;
	private AboutFragment aboutFragment = null;
	
	@Override
	public void init() {
		setBackPressEnabled(true);
		
		initData();
		initView();
	}
	
	private void initData(){
		fragmentManager = getSupportFragmentManager();
		itemType = getIntent().getIntExtra(EXTRA_ITEM_TYPE, CHANGE_PWD);
	}
	
	private void initView(){
		switch (itemType) {
		case CHANGE_PWD:
			setTopBarTitle("修改密码");
			showChangePwdFragment();
			break;
		case HELP:
			setTopBarTitle("使用帮助");
			showHelpFragment();
			break;
		case FEEDBACK:
			setTopBarTitle("意见反馈");
			showFeedbackFragment();
			break;
		case ABOUT:
			setTopBarTitle("关于我们");
			showAboutFragment();
			break;
		default:
			break;
		}
	}
	
	private void showChangePwdFragment(){
		if (changePwdFragment == null) {
			changePwdFragment = ChangePwdFragment.newInstance();
		}
		FragmentUtil.replaceNormal(changePwdFragment, fragmentManager, R.id.fl_container_common);
	}
	
	private void showHelpFragment(){
		if (helpFragment == null) {
			helpFragment = HelpFragment.newInstance();
		}
		FragmentUtil.replaceNormal(helpFragment, fragmentManager, R.id.fl_container_common);
	}
	
	private void showFeedbackFragment(){
		if (feedbackFragment == null) {
			feedbackFragment = FeedbackFragment.newInstance();
		}
		FragmentUtil.replaceNormal(feedbackFragment, fragmentManager, R.id.fl_container_common);
	}

	private void showAboutFragment(){
		if (aboutFragment == null) {
			aboutFragment = AboutFragment.newInstance();
		}
		FragmentUtil.replaceNormal(aboutFragment, fragmentManager, R.id.fl_container_common);
	}
}
