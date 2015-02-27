package com.baiyun.base;
/**
 * ViewPager的简单适配器
 * @author Holyn
 * @modified
 */
import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class BaseAdAdapter extends PagerAdapter{
	private ArrayList<View> views;
	
	public BaseAdAdapter(ArrayList<View> views) {
		this.views = views;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
//		((ViewPager)container).removeView(views.get(position));
		
		((ViewPager)container).removeView(views.get(position % views.size()));//循环滑动  
	}
	
	//@Override
	//public CharSequence getPageTitle(int position) {
		//return titles.get(position);
	//}
	
	@Override
	public Object instantiateItem(View container, int position) {
//		((ViewPager)container).addView(views.get(position));
//		return views.get(position);
		
        ((ViewPager)container).addView(views.get(position % views.size()), 0);  //循环滑动  
        return views.get(position % views.size());  
	}
	
	@Override
	public void finishUpdate(View container) {
		
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		
	}
	
	
}
