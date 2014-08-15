package com.jt.androiddemos.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToastLog {

	public static void showToast(Context context, String text, int duration) {
		Toast.makeText(context, text, duration).show();
	}
	
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
