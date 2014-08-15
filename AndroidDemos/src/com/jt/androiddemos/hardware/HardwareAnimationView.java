package com.jt.androiddemos.hardware;

import com.jt.androiddemos.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class HardwareAnimationView extends View {
	
	Bitmap mBitmap;
	int mBitmapWidth;
	int mBitmapHeight;

	public HardwareAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public HardwareAnimationView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context) {
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		mBitmapWidth = mBitmap.getWidth();
		mBitmapHeight = mBitmap.getHeight();
		// setLayerType(View.LAYER_TYPE_HARDWARE, null);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	int mLeft = 0;
	int mTop = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.save();
		mLeft = (getWidth() - mBitmapWidth)/2;
		canvas.drawBitmap(mBitmap, mLeft, mTop, null);
		mTop++;
		if (mTop > getHeight()) {
			mTop = 0;
		}
		canvas.restore();
		invalidate();
	}
}
