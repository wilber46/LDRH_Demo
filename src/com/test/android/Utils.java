package com.test.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Environment;
import android.text.TextUtils;

public class Utils {
	public static final String TAG = "Utils";
	public static void writeLog(String str) {
		if(TextUtils.isEmpty(str)) {
			return;
		}
		
		String now = Utils.getCurTime();
		
		StringBuilder sb = new StringBuilder().append(now).append("--------").append(str).append("\n");
		WLog.d(TAG, "[writeLog] str = " + sb.toString(),false);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "conn.log"),true);
			fos.write(sb.toString().getBytes());
			fos.flush();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getCurTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:sss");
		String now = sdf.format(Calendar.getInstance().getTime());
		return now;
	}
}
