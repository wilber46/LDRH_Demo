package com.test.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.DisconnectCause;
import android.text.TextUtils;
import android.util.Log;

public class TestKeyService extends Service {
	public static final String TAG = "TestKeyService";
	private TestKeyMgr mKeyMgr;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mKeyMgr = new TestKeyMgr(getApplicationContext());
		mKeyMgr.listen();
//		HandlerThread thread = new HandlerThread("netrequest");
//		thread.start();
//		mHandler = new MyHandler(thread.getLooper());
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_CALL_ERROR);
		registerReceiver(mReceiver,intentFilter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
//		if(null != mKeyMgr) {
//			mKeyMgr.unListen();
//		}
		
		if(null != mReceiver) {
			unregisterReceiver(mReceiver);
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		if(null != mHandler) {
//			mHandler.removeCallbacks(runnable);
//			mHandler.post(runnable);
//		}
		return START_NOT_STICKY;
	}
	
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(ACTION_CALL_ERROR.equals(action)) {
				int code = intent.getIntExtra(EXTRA_CODE, DisconnectCause.NOT_VALID);
				String msg = intent.getStringExtra(EXTRA_MESSAGE);
				msg = (TextUtils.isEmpty(msg) ? "" : msg);
				Log.d(TAG, "[mReceiver#onReceive] code = " + code + ",msg = " + msg);
				if(code != DisconnectCause.NOT_VALID && code != DisconnectCause.NOT_DISCONNECTED) {
					Log.d(TAG, "[mReceiver#onReceive] call disconnected !!");
				}
			}
		}
	};
	
	
	/* ------------------------- net test --------------------- */
	private class MyHandler extends Handler {
		public MyHandler(Looper looper) {
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			requestNetInfo();
			if(null != mHandler) {
				mHandler.postDelayed(this, 5000);
			}
		}
	};
	
	public void requestNetInfo(){
		WLog.d(TAG, "requestNetInfo",false);
		BufferedReader br = null;
		try {
			URL url = new URL(URL_BD);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			WLog.d(TAG,sb.length() > 20 ? sb.substring(0, 19) : sb.toString(),true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			WLog.d(TAG,e.getMessage(),true);
		} catch (IOException e) {
			e.printStackTrace();
			WLog.d(TAG,e.getMessage(),true);
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	private MyHandler mHandler = null;
	public static final String URL_BD = "http://www.hao123.com";
	public static final String ACTION_CALL_ERROR = "yunovo.intent.action.CALL_ERROR";
	public static final String EXTRA_CODE = "code";
	public static final String EXTRA_MESSAGE = "message";
}