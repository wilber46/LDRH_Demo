package com.test.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Apis {
	public static final String TAG = "bbw";
	private TelephonyManager mTelephonyMgr = null;
	private BatteryManager mBatteryMgr = null;
	
	public Apis(Context context) {
		mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		mBatteryMgr = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
	}
	
	/**
     * Returns a constant indicating the state of the default SIM card.
     *
     * @see #SIM_STATE_UNKNOWN
     * @see #SIM_STATE_ABSENT
     * @see #SIM_STATE_PIN_REQUIRED
     * @see #SIM_STATE_PUK_REQUIRED
     * @see #SIM_STATE_NETWORK_LOCKED
     * @see #SIM_STATE_READY
     * @see #SIM_STATE_NOT_READY
     * @see #SIM_STATE_PERM_DISABLED
     * @see #SIM_STATE_CARD_IO_ERROR
     */
	public int getSimState(Context context) {
		if(null != mTelephonyMgr) {
			return mTelephonyMgr.getSimState();
		}
		return TelephonyManager.SIM_STATE_UNKNOWN;
	}
	
	public void listenGsmSignalStrength(Context context,PhoneStateListener listener) {
		if(null != mTelephonyMgr) {
			mTelephonyMgr.listen(listener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		}
	}
	
	public void unListenGsmSignalStrength() {
		if(null != mTelephonyMgr) {
			mTelephonyMgr.listen(null, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		}
	}
	
	public int getBatteryInfo(Context context) {
		if(null == mBatteryMgr) {
			return -1;
		}
		
		int currentNow = mBatteryMgr.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
		int chargeCounter = mBatteryMgr.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
		int curAverage = mBatteryMgr.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
		Log.d(TAG, "getBatteryInfo currentNow = " + currentNow + ",chargeCounter = " + chargeCounter + ",curAverage = " + curAverage);
		return currentNow;
	}
	
	public void registerBatteryInfo(Context context) {
		context.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, android.content.Intent intent) {
			String action = intent.getAction();
			if(Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				int current = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				Log.d(TAG, "ACTION_BATTERY_CHANGED current = " + current + ",scale = " + scale);
			}
		};
	};
	
	/**
	 * 隐藏号码
	 * @param context
	 * @param on
	 */
	public void enableHideNumber(final Context context, boolean on) {
		Intent intent = new Intent("yunovo.intent.action.ENABLE_HIDE_NUMBER");
		intent.putExtra("enable", on ? 1 : 0);
		context.sendBroadcast(intent);
	}
}
