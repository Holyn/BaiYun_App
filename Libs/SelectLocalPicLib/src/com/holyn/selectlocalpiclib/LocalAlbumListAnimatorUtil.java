package com.holyn.selectlocalpiclib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;


public class LocalAlbumListAnimatorUtil {
	
	private boolean isShowing = false;
	private Context context;
	private ViewGroup layout;

	public LocalAlbumListAnimatorUtil(Context context, ListView listView) {
		this.context = context;
		this.layout = listView;
	}

	public boolean isShow() {
		return isShowing;
	}

	public void showAlbumList() {
		layout.setVisibility(View.VISIBLE);
		isShowing = true;
		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.bottom_in_2);
		anim.setInterpolator(new LinearInterpolator());
		layout.startAnimation(anim);
	}

	public void hideAlbumList() {
		layout.setVisibility(View.GONE);
		isShowing = false;

		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.bottom_out_2);
		anim.setInterpolator(new LinearInterpolator());

		layout.startAnimation(anim);
	}
	
}
