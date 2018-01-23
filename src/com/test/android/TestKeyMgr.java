package com.test.android;

import com.yunovo.common.server.IYOcKeyListener;
import com.yunovo.common.server.IYOcManager;
import com.yunovo.common.server.YOcManager;

import android.content.Context;
import android.os.RemoteException;
//import android.os.ServiceManager;
import android.util.Log;
import android.view.KeyEvent;

public class TestKeyMgr {
	public static final String TAG = "TestKeyMgr";
	private IYOcManager mYOcMgr = null;
	private MyKeyListener listener = null;
	
	public TestKeyMgr(Context context) {
//		mYOcMgr = IYOcManager.Stub.asInterface(ServiceManager.getService(YOcManager.SERVICE_NAME));
		mYOcMgr = IYOcManager.Stub.asInterface(SysHideUtil.getServiceMgr(YOcManager.SERVICE_NAME));
		listener = new MyKeyListener();
		
	}
	
	public void listen() {
		if(null != mYOcMgr) {
			try {
				mYOcMgr.addKeyListener(listener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void unListen() {
		if(null != mYOcMgr) {
			try {
				mYOcMgr.removeKeyListener(listener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MyKeyListener extends IYOcKeyListener.Stub {
		@Override
		public boolean onKey(KeyEvent event) throws RemoteException {
			Log.d(TAG, "current key is " + event);
			
			final boolean down = event.getAction() == KeyEvent.ACTION_DOWN;
			final int keyCode = event.getKeyCode();
			final int repeatCount = event.getRepeatCount();
			
			if(keyCode == KeyEvent.KEYCODE_INFO) {
				// 摘机键 earpiece up
				String speakerOut =  SysHideUtil.getProperty("yunovo.speaker.out", "");
				Log.d(TAG, "key info,speakerOut status = " + speakerOut);
				if(speakerOut.equals("true")) {
					Log.d(TAG, "real status = " + speakerOut);
				} else if(speakerOut.equals("false")) {
					Log.d(TAG, "real status = " + speakerOut);
				}
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					Log.d(TAG, "earpiece up");
					
				} else if(event.getAction() == KeyEvent.ACTION_UP){
					Log.d(TAG, "earpiece down");
				}
			}
			
			if(down && repeatCount == 0) {
				if(
						keyCode == KeyEvent.KEYCODE_0 || 
						keyCode == KeyEvent.KEYCODE_1 ||
						keyCode == KeyEvent.KEYCODE_2 || 
						keyCode == KeyEvent.KEYCODE_3 ||
						keyCode == KeyEvent.KEYCODE_4 || 
						keyCode == KeyEvent.KEYCODE_5 ||
						keyCode == KeyEvent.KEYCODE_6 || 
						keyCode == KeyEvent.KEYCODE_7 ||
						keyCode == KeyEvent.KEYCODE_8 || 
						keyCode == KeyEvent.KEYCODE_9 ||
						keyCode == KeyEvent.KEYCODE_STAR || 	// *
						keyCode == KeyEvent.KEYCODE_POUND ||	// #
						keyCode == KeyEvent.KEYCODE_CALL || 
						keyCode == KeyEvent.KEYCODE_MENU ||
						keyCode == KeyEvent.KEYCODE_BACK || 
						keyCode == KeyEvent.KEYCODE_DPAD_DOWN ||
						keyCode == KeyEvent.KEYCODE_DPAD_UP ||
						keyCode == KeyEvent.KEYCODE_DPAD_LEFT ||
						keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ||
						keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
						keyCode == KeyEvent.KEYCODE_F1 ||	// 重拨
						keyCode == KeyEvent.KEYCODE_F2 ||	// 免提
						keyCode == KeyEvent.KEYCODE_MUTE
						) {
					Log.d(TAG, "board key is " + keyCode);
				}
			}
			
			// 监听按键状态一律返回false
			return false;
		}
	}
}
