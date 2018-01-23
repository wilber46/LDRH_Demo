package com.test.android;

import android.util.Log;

public class WLog {
	public static final String TAG = "LDRH_TEST";
	
	public static void d(String tag,String s,boolean writeFile) {
		s = (null == s ? "null" : s);
		Log.d(TAG, String.format("[%s] - %s", tag,s));
		if(writeFile) {
			Utils.writeLog(s);
		}
	}
}
