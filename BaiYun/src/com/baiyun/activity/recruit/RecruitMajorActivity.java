package com.baiyun.activity.recruit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.activity.webview.WebViewFragment2;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;
import com.baiyun.vo.parcelable.RecruitTypeMajorPar;

public class RecruitMajorActivity extends BaseFragmentActivity{
	public static final String KEY_RECRUIT_MAJOR_PAR = "key_recruit_major_par";
	
	private FragmentManager fragmentManager;
	private RecruitMajorFragment majorFragment = null;
	private WebViewFragment2 webViewFragment2 = null;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		List<RecruitTypeMajorPar> majorPars = getIntent().getParcelableArrayListExtra(KEY_RECRUIT_MAJOR_PAR);
		
		setTopBarTitle("专业介绍");
		setBackPressEnabled(true);
		
		fragmentManager = getSupportFragmentManager();
		showRecruitMajorFragment(majorPars);
	}

	private void showRecruitMajorFragment(List<RecruitTypeMajorPar> majorPars){
		if (majorFragment == null) {
			majorFragment = RecruitMajorFragment.newInstance();
		}
		Bundle args = new Bundle();
		args.putParcelableArrayList(KEY_RECRUIT_MAJOR_PAR, (ArrayList<? extends Parcelable>) majorPars);
		FragmentUtil.replaceNormal(majorFragment, fragmentManager, R.id.fl_container_common, args);
	}
	
	public void showWebViewFragment2(String urlLast) {
		if (webViewFragment2 == null) {
			webViewFragment2 = WebViewFragment2.newInstance();
		}
		Bundle args = new Bundle();
		args.putString(WebViewFragment2.KEY_URL_LAST, urlLast);
		FragmentUtil.replaceAddToBack(webViewFragment2, fragmentManager, R.id.fl_container_common, args);
	}
	
}
