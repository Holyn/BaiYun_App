package com.baiyun.activity.recruit;

import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;

public class ApplyFormActivity extends BaseFragmentActivity{
	public static final String FORM_TYPE_INT = "form_type_int";
	public static final int FORM_1 = 1;
	public static final int FORM_2 = 2;
	
	private FragmentManager fragmentManager;
	private ApplyForm1Fragment form1Fragment = null;
	private ApplyForm2Fragment form2Fragment = null;
	
	@Override
	public void init() {
		setTopBarTitle("网上报名");
		setBackPressEnabled(true);
		
		fragmentManager = getSupportFragmentManager();
		
		int formType = getIntent().getIntExtra(FORM_TYPE_INT, 1);
		if (formType == FORM_1) {
			showForm1Fragment();
		}else if (formType == FORM_2) {
			showForm2Fragment();
		}
		
	}

	private void showForm1Fragment(){
		if (form1Fragment == null) {
			form1Fragment = ApplyForm1Fragment.newInstance();
		}
		FragmentUtil.replaceNormal(form1Fragment, fragmentManager, R.id.fl_container_common);
	}
	
	private void showForm2Fragment(){
		if (form2Fragment == null) {
			form2Fragment = ApplyForm2Fragment.newInstance();
		}
		FragmentUtil.replaceNormal(form2Fragment, fragmentManager, R.id.fl_container_common);
	}
}
