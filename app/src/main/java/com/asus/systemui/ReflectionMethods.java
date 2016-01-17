package com.asus.systemui;

import android.util.Log;

/**
 * Created by bamzzz on 10 Jan 2016.
 */
/*public class ReflectionMethods {
}*/

public class ReflectionMethods {
	public static int getBatteryStatusNotQuickCharging() {
		byte var1 = 11;

		int var0;
		try {
			Class var2 = Class.forName("android.os.BatteryManager");
			var0 = var2.getDeclaredField("BATTERY_STATUS_NOT_QUICK_CHARGING").getInt(var2);
		} catch (Exception var3) {
			var0 = var1;
		}

		return var0;
	}

	public static int getBatteryStatusQuickCharging() {
		int var0 = 10;

		int var1;
		try {
			Class var2 = Class.forName("android.os.BatteryManager");
			var1 = var2.getDeclaredField("BATTERY_STATUS_QUICK_CHARGING").getInt(var2);
		} catch (Exception var3) {
			return var0;
		}

		var0 = var1;
		return var0;
	}

	public static int getBatteryStatusQuickChargingV1() {
		int var0 = 9;

		int var1;
		try {
			Class var2 = Class.forName("android.os.BatteryManager");
			var1 = var2.getDeclaredField("BATTERY_STATUS_QUICK_CHARGING_V1").getInt(var2);
		} catch (Exception var3) {
			return var0;
		}

		var0 = var1;
		return var0;
	}

	public static int getEthernetActivityIn() {
		int var0 = 1;

		int var1;
		try {
			Class var2 = Class.forName("android.net.EthernetManager");
			var1 = var2.getDeclaredField("DATA_ACTIVITY_IN").getInt(var2);
		} catch (Exception var3) {
			return var0;
		}

		var0 = var1;
		return var0;
	}

	public static int getEthernetActivityInOut() {
		byte var1 = 3;

		int var0;
		try {
			Class var2 = Class.forName("android.net.EthernetManager");
			var0 = var2.getDeclaredField("DATA_ACTIVITY_INOUT").getInt(var2);
		} catch (Exception var3) {
			var0 = var1;
		}

		return var0;
	}

	public static int getEthernetActivityNone() {
		int var0 = 0;

		int var1;
		try {
			Class var2 = Class.forName("android.net.EthernetManager");
			var1 = var2.getDeclaredField("DATA_ACTIVITY_NONE").getInt(var2);
		} catch (Exception var3) {
			return var0;
		}

		var0 = var1;
		return var0;
	}

	public static int getEthernetActivityNotification() {
		byte var1 = 1;

		int var0;
		try {
			Class var2 = Class.forName("android.net.EthernetManager");
			var0 = var2.getDeclaredField("DATA_ACTIVITY_NOTIFICATION").getInt(var2);
		} catch (Exception var3) {
			var0 = var1;
		}

		return var0;
	}

	public static int getEthernetActivityOut() {
		byte var1 = 2;

		int var0;
		try {
			Class var2 = Class.forName("android.net.EthernetManager");
			var0 = var2.getDeclaredField("DATA_ACTIVITY_OUT").getInt(var2);
		} catch (Exception var3) {
			var0 = var1;
		}

		return var0;
	}

	/*public static Messenger getEthernetMessenger(EthernetManager param0) {
		return null;
	}*/

	public static boolean isFlipfontSupport() {
		boolean var0 = false;

		try {
			Class.forName("android.graphics.Typeface").getDeclaredField("isFlipFontUsed");
			Log.v("ReflectionMethods", "========ReflectionMethods.isFlipfontSupport = true ===============");
		} catch (ClassNotFoundException var2) {
			Log.v("ReflectionMethods", "========ReflectionMethods.isFlipfontSupport = false ===============");
			Log.v("ReflectionMethods", "ClassNotFoundException e=" + var2);
			return var0;
		} catch (NoSuchFieldException var3) {
			Log.v("ReflectionMethods", "========ReflectionMethods.isFlipfontSupport = false ===============");
			Log.v("ReflectionMethods", "NoSuchFieldException e=" + var3);
			return var0;
		}

		var0 = true;
		return var0;
	}

	public static boolean isIconMaterialChangeSupport() {
		boolean var0 = false;

		try {
			Class.forName("android.provider.Settings$System").getField("NOTIFICATION_MATERIAL");
			Log.v("ReflectionMethods", "========ReflectionMethods.isChangeIconMaterialSupport = true ===============");
		} catch (ClassNotFoundException var2) {
			Log.v("ReflectionMethods", "========ReflectionMethods.isChangeIconMaterialSupport = false ===============");
			Log.v("ReflectionMethods", "ClassNotFoundException e=" + var2);
			return var0;
		} catch (NoSuchFieldException var3) {
			Log.v("ReflectionMethods", "========ReflectionMethods.isChangeIconMaterialSupport = false ===============");
			Log.v("ReflectionMethods", "NoSuchFieldException e=" + var3);
			return var0;
		}

		var0 = true;
		return var0;
	}

	/*public static boolean isSnapView(UserInfo param0) {
		return false;
	}

	public static boolean isSnapViewSupport(UserInfo var0) {
		boolean var1;
		try {
			Class.forName("android.content.pm.UserInfo").getMethod("isSnapView", (Class[])null).invoke(var0, (Object[])null);
			Log.v("ReflectionMethods", "========ReflectionMethods.isSnapViewSupport = true ===============");
		} catch (Exception var2) {
			Log.v("ReflectionMethods", "========ReflectionMethods.isSnapViewSupport = false ===============");
			var1 = false;
			return var1;
		}

		var1 = true;
		return var1;
	}*/
}
