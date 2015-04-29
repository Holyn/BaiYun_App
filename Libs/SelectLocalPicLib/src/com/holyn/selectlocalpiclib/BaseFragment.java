package com.holyn.selectlocalpiclib;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public abstract class BaseFragment extends Fragment {
	protected InputMethodManager inputMethodManager;	// 软盘输入管理器
	protected FragmentManager fragmentManager;			// fragment管理器
	protected View rootLayout;							// 根布局view
	protected HideCallBack hideCallBack;				// 隐藏Fragment时回调

	public interface HideCallBack {
		public void onHide();
	}

	public BaseFragment() {
		super();
	}

	/**
	 * 实体
	 * 
	 * @param fragmentManager
	 */
	public BaseFragment(FragmentManager fragmentManager) {
		super();
		this.fragmentManager = fragmentManager;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootLayout != null) {
			ViewGroup parent = (ViewGroup) rootLayout.getParent();
			if (parent != null) {
				parent.removeView(rootLayout);		// 避免rootLayout已有父容器异常
			}
			reinit();
		} else {
			rootLayout = inflater.inflate(getResourceLayoutId(), container,
					false);
			init();
		}
		return rootLayout;
	}

	/**
	 * 重新加载Fragment时，子类可以重写本方法，在这里做一些重置操作
	 */
	protected void reinit() {

	}

	/**
	 * 获取fragment的视图资源ID
	 * 
	 * @return
	 */
	protected abstract int getResourceLayoutId();

	/**
	 * onCreateView方法执行完毕，在这里执行初始化操作
	 */
	protected abstract void init();

	/**
	 * 设置隐藏Fragment回调接口
	 * 
	 * @param hideCallBack
	 */
	public void setHideCallBack(HideCallBack hideCallBack) {
		this.hideCallBack = hideCallBack;
	}

	/**
	 * 点击根布局空白区域时，退出当前Fragment
	 */
	protected void rootLayoutOnClickListener() {
		rootLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hide();
			}
		});
	}

	/**
	 * 根据指定的动画显示Fragment
	 * 
	 * @param contentId
	 * @param enter
	 * @param exit
	 * @param popEnter
	 * @param popExit
	 */
	public void show(int contentId, int enter, int exit, int popEnter,
			int popExit) {
		fragmentManager.popBackStack();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
		fragmentTransaction.replace(contentId, this);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * 隐藏AreaFragment
	 */
	public void hide() {
		fragmentManager.popBackStack();
		if (hideCallBack != null) {
			hideCallBack.onHide();
		}
	}

}
