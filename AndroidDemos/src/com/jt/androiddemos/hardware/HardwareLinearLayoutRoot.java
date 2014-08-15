package com.jt.androiddemos.hardware;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class HardwareLinearLayoutRoot extends LinearLayout{
	
	public static final String TAG = "HardwareLinearLayoutRoot";

	public HardwareLinearLayoutRoot(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HardwareLinearLayoutRoot(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		new Throwable(TAG + " onDraw").printStackTrace();
		Log.e(TAG, "onDraw");
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		new Throwable(TAG + " dispatchDraw").printStackTrace();
		Log.e(TAG, "dispatchDraw");
	}

}
