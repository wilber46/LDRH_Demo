package com.test.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class TestReceiver extends BroadcastReceiver {
	public static final String TAG = "TestReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		WLog.d(TAG, "onReceive" + (null != action ? action : "null"),true);
		if(Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			Intent i = new Intent();
			i.setClass(context, TestKeyService.class);
			context.startService(i);
		}
		else if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
			NetworkInfo info = intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI
                            || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        WLog.d(TAG,getConnectionType(info.getType()) + "连上",true);
                    }
                } else {
                	WLog.d(TAG,getConnectionType(info.getType()) + "断开",true);
                }
            }
		}
	}

	private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }
}
