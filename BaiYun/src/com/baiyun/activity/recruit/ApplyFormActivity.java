package com.baiyun.activity.recruit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baiyun.activity.R;
import com.baiyun.base.BaseFragmentActivity;
import com.baiyun.util.ui.FragmentUtil;
import com.baiyun.vo.parcelable.ApplyPar;

public class ApplyFormActivity extends BaseFragmentActivity{
	public static final String FORM_TYPE_INT = "form_type_int";
	public static final int FORM_1 = 1;
	public static final int FORM_2 = 2;
	
	public static final String VALUE_APPLYPAR = "value_applypar";//ApplyPar传值
	
	private FragmentManager fragmentManager;
	private ApplyForm1Fragment form1Fragment = null;
	private ApplyForm2Fragment form2Fragment = null;
	
	@Override
	public void init() {
		setTopBarTitle("网上报名");
		setBackPressEnabled(true);
		
		fragmentManager = getSupportFragmentManager();
		
		ApplyPar applyPar = getIntent().getParcelableExtra(VALUE_APPLYPAR);
		int formType = getIntent().getIntExtra(FORM_TYPE_INT, 1);
		if (formType == FORM_1) {
			showForm1Fragment(applyPar);
		}else if (formType == FORM_2) {
			showForm2Fragment(applyPar);
		}
		
	}

	private void showForm1Fragment(ApplyPar applyPar){
		if (form1Fragment == null) {
			form1Fragment = ApplyForm1Fragment.newInstance();
		}
		Bundle args = new Bundle();
		args.putParcelable(VALUE_APPLYPAR, applyPar);
		FragmentUtil.replaceNormal(form1Fragment, fragmentManager, R.id.fl_container_common, args);
	}
	
	private void showForm2Fragment(ApplyPar applyPar){
		if (form2Fragment == null) {
			form2Fragment = ApplyForm2Fragment.newInstance();
		}
		Bundle args = new Bundle();
		args.putParcelable(VALUE_APPLYPAR, applyPar);
		FragmentUtil.replaceNormal(form2Fragment, fragmentManager, R.id.fl_container_common, args);
	}
}
