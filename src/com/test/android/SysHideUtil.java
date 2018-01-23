package com.test.android;

import java.lang.reflect.Method;

import android.os.IBinder;

public class SysHideUtil {
	public static String getProperty(String key, String defaultValue) {
		String value = defaultValue;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			value = (String) (get.invoke(c, key, ""));
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return defaultValue;
	}

	public static void setProperty(String key, String value) {
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method set = c.getMethod("set", String.class, String.class);
			set.invoke(c, key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IBinder getServiceMgr(String serviceName) {
		IBinder binder = null;
		try {
			Class clazz = Class.forName("android.os.ServiceManager");  
			Method method = clazz.getMethod("getService", String.class);  
			binder = (IBinder) method.invoke(null, serviceName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	    return binder;
	}
}
