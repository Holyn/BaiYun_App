package com.baiyun.custom;

import com.baiyun.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SlipButton extends View implements OnTouchListener {
	private Bitmap onBitmap;
	private Bitmap offBitmap;
	private Bitmap slipBitmap;
	private Rect onRect;
	private Rect offRect;
	private float nowX;
	private float downX;
	private boolean nowChoose = false;
	private boolean isChecked;
	private boolean onSlip = false;
	private boolean isChange = false;
	private onChangeListener changeListener;

	public SlipButton(Context context) {
		super(context);
		init();
	}

	public SlipButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlipButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		onBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_left_1);
		offBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_right_1);
		slipBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.split_1);
		onRect = new Rect(0, 0, slipBitmap.getWidth(), slipBitmap.getHeight());
		offRect = new Rect(offBitmap.getWidth() - slipBitmap.getWidth(), 0,
				offBitmap.getWidth(), slipBitmap.getHeight());
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x;
		if (nowX < (onBitmap.getWidth() / 2)) {
			x = nowX - slipBitmap.getWidth() / 2;
			canvas.drawBitmap(offBitmap, matrix, paint);
		} else {
			x = onBitmap.getWidth() - slipBitmap.getWidth() / 2;
			canvas.drawBitmap(onBitmap, matrix, paint);
		}
		if (onSlip) {
			if (nowX >= onBitmap.getWidth()) {
				x = onBitmap.getWidth() - slipBitmap.getWidth() / 2;
			} else if (nowX < 0) {
				x = 0;
			} else {
				x = nowX - slipBitmap.getWidth() / 2;
			}
		} else {
			if (nowChoose) {
				x = offRect.left;
				canvas.drawBitmap(onBitmap, matrix, paint);
			} else {
				x = onRect.left;
			}
		}
		if (isChecked) {
			canvas.drawBitmap(onBitmap, matrix, paint);
			x = offRect.left;
			isChecked = !isChecked;
		}
		if (x < 0) {
			x = 0;
		} else if (x > onBitmap.getWidth() - slipBitmap.getWidth())
			x = onBitmap.getWidth() - slipBitmap.getWidth();
		canvas.drawBitmap(slipBitmap, x, 0, paint);

		super.onDraw(canvas);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (isClickable()) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				nowX = event.getX();
				break;
			case MotionEvent.ACTION_DOWN:
				if (event.getX() > onBitmap.getWidth()
						|| event.getY() > onBitmap.getHeight())
					return false;
				onSlip = true;
				downX = event.getX();
				nowX = downX;
				break;
			case MotionEvent.ACTION_UP:
				onSlip = false;
				boolean LastChoose = nowChoose;
				if (event.getX() >= (onBitmap.getWidth() / 2)) {
					nowX = onBitmap.getWidth() - slipBitmap.getWidth() / 2;
					nowChoose = true;
				} else {
					nowX = nowX - slipBitmap.getWidth() / 2;
					nowChoose = false;
				}
				if (isChange && LastChoose != nowChoose) {
					changeListener.OnChanged(nowChoose);
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				onSlip = false;
				boolean choose = nowChoose;
				if (nowX >= (onBitmap.getWidth() / 2)) {
					nowX = onBitmap.getWidth() - slipBitmap.getWidth() / 2;
					nowChoose = true;
				} else {
					nowX = nowX - slipBitmap.getWidth() / 2;
					nowChoose = false;
				}
				if (isChange && choose != nowChoose) {
					changeListener.OnChanged(nowChoose);
				}
				break;

			default:
				break;
			}
			invalidate();
		}
		return true;
	}

	public void SetOnChangedListener(onChangeListener l) {
		isChange = true;
		changeListener = l;
	}

	public interface onChangeListener {
		abstract void OnChanged(boolean CheckState);
	}

	public void setCheck(boolean isChecked) {
		this.isChecked = isChecked;
		nowChoose = isChecked;
	}

}
