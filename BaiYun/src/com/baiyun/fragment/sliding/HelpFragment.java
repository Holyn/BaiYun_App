package com.baiyun.fragment.sliding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyun.activity.R;
import com.baiyun.activity.main.MainActivity;
import com.baiyun.base.BaseFragment;
import com.baiyun.base.BaseSlidingFragmentActivity;

public class HelpFragment extends BaseFragment{
	
	public static HelpFragment newInstance() {
		return new HelpFragment();
	}
	
	public HelpFragment() {
		
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_help;
	}

	@Override
	public void createMyView(View rootView) {
		
	}
	
	
}
