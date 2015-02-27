package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {
	// 计算OnGlobalLayoutListener的onGlobalLayout的次数
	public static int ON_GLOBAL_LAYOUT_COUNT = 1;

	public PullToRefreshRecyclerView(Context context) {
		super(context);
	}

	public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshRecyclerView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
		RecyclerView recyclerView;
		recyclerView = new RecyclerView(context, attrs);
		recyclerView.setId(R.id.recyclerview);
		return recyclerView;
	}

	@Override
	protected boolean isReadyForPullStart() {
		if (mRefreshableView.getChildCount() <= 0)
			return true;
		int firstVisiblePosition = mRefreshableView
				.getChildPosition(mRefreshableView.getChildAt(0));
		if (firstVisiblePosition == 0){
			return mRefreshableView.getChildAt(0).getTop() == 0;
		}
		else
			return false;

	}

	@Override
	protected boolean isReadyForPullEnd() {
		int childCount = mRefreshableView.getChildCount();
		if (childCount == 0) {
			return false;
		}
		int lastVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView
				.getChildAt(mRefreshableView.getChildCount() - 1));
		if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount() - 1) {
			return mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1).getBottom() <= mRefreshableView
					.getBottom();
		}
		return false;
	}

	/*
	 * 监听头部自动下拉的事件，方便首次进入页面时刷新
	 */
	public OnHeaderShowListener onHeaderShowListener;

	public void setOnHeaderShowListener(OnHeaderShowListener onHeaderShowListener) {
		this.onHeaderShowListener = onHeaderShowListener;
		addOnGlobalLayoutListener();
	}

	public interface OnHeaderShowListener {
		public void onHeaderShow();
	}

	private int onGlobalLayoutCount = 1;

	public void addOnGlobalLayoutListener() {
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				if (onGlobalLayoutCount == 2) {
					onHeaderShowListener.onHeaderShow();
				} else if (onGlobalLayoutCount > 2) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
					onGlobalLayoutCount = 0;
				}
				onGlobalLayoutCount++;
			}
		});
	}
}
