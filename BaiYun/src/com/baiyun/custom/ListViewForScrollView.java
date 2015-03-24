package com.baiyun.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 这个方法和方法1有一个同样的毛病，就是默认显示的首项是ListView，需要手动把ScrollView滚动至最顶端。
 * sv = (ScrollView) findViewById(R.id.act_solution_4_sv);
 * sv.smoothScrollTo(0, 0);
 * @author Administrator
 *
 */
public class ListViewForScrollView extends ListView {
	public ListViewForScrollView(Context context) {
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
