package com.baiyun.base;

import com.baiyun.activity.R;
import com.baiyun.custom.LoadingDialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment{
	public View rootView;
	public LoadingDialog loadingDialog = null;
	
	public abstract int getLayoutId();
	
	public abstract void createMyView(View rootView);

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
            return rootView;
		}else {
			rootView = inflater.inflate(getLayoutId(), container, false);
			createMyView(rootView);
		}
		return rootView;
	}
	
	protected TextView getActionBarTitleView(){
		if (((Activity)getActivity()) instanceof ActionBarActivity) {
			TextView tvTitle = (TextView)((Activity)getActivity()).findViewById(R.id.tv_actionbar_title);
			return tvTitle;
		}else {
			return null;
		}
	}
	
	protected void setActionBarTitle(String title) {
		if (((Activity)getActivity()) instanceof ActionBarActivity) {
			TextView tvTitle = (TextView)((Activity)getActivity()).findViewById(R.id.tv_actionbar_title);
			tvTitle.setText(title);
		}
	}
	
	protected void showLoadingDialog() {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(getActivity(), null);
		}
		loadingDialog.show();
	}
	
	protected void closeLoadingDialog() {
		if (loadingDialog != null) {
			if (loadingDialog.isShowing()) {
				loadingDialog.cancel();
				loadingDialog = null;
			}
		}
	}
	
}
