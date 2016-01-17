/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.util.Log
 *  com.android.internal.util.MemInfoReader
 *  com.asus.systemui.util.AsusQuicksettingUtil
 */
package com.asus.systemui.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

/*
 * Exception performing whole class analysis ignored.
 */
public class AsusQuicksettingUtil {
	public static final boolean IS_SMALL_RAM = AsusQuicksettingUtil.isSmallRam();
	private static AsusQuicksettingUtil mInstance = null;
	private Context mContext;
	private PackageManager pm;

	static {
	}

	private AsusQuicksettingUtil(Context context) {
		this.mContext = context;
		this.pm = this.mContext.getPackageManager();
	}

	/*
	 * Enabled unnecessary exception pruning
	 */
	public static AsusQuicksettingUtil getInstance(Context context) {
		if (mInstance != null) return mInstance;
		synchronized (AsusQuicksettingUtil.class) {
			if (mInstance != null) return mInstance;
			mInstance = new AsusQuicksettingUtil(context);
			return mInstance;
		}
	}

	private static Resources getResFromPackage(Context context, String string) {
		PackageManager packageManager = context.getPackageManager();
		Resources resources = null;
		try {
			Resources resources2;
			resources = resources2 = packageManager.getResourcesForApplication(string);
			if (resources == null) return resources;
			return resources;
		}
		catch (PackageManager.NameNotFoundException var4_5) {
			Log.w((String)"Quicksettings.UtilgetResFromPackage()", (String)("cannot find package " + string), (Throwable)var4_5);
		}
		return resources;
	}

	public static String getStringFromPackage(Context context, String string, String string2) {
		Resources resources = AsusQuicksettingUtil.getResFromPackage((Context)context, (String)string);
		if (resources == null) return null;
		int n = resources.getIdentifier(string2, "string", string);
		if (n == 0) return null;
		return resources.getString(n);
	}

	public static boolean isSmallRam() {
		//MemInfoReader memInfoReader = new MemInfoReader();
		//memInfoReader.readMemInfo();
		//if (memInfoReader.getTotalSize() > 0x40000000) return false;
		return true;
	}

	public String getActivityName(String string, String string2) {
		try {
			ComponentName componentName = new ComponentName(string, string2);
			return this.pm.getActivityInfo(componentName, 0).loadLabel(this.pm).toString();
		}
		catch (PackageManager.NameNotFoundException var4_5) {
			Log.e((String)"Quicksettings.Util", (String)"Couldn't get activity name");
			return "notfound";
		}
	}

	public String getAppName(String string) {
		try {
			return this.pm.getPackageInfo((String)string, (int)0).applicationInfo.loadLabel(this.pm).toString();
		}
		catch (PackageManager.NameNotFoundException var2_3) {
			Log.e((String)"Quicksettings.Util", (String)"Couldn't get app name");
			return "notfound";
		}
	}

	public String getAppVersion(String string) {
		try {
			return this.pm.getPackageInfo((String)string, (int)0).versionName;
		}
		catch (PackageManager.NameNotFoundException var2_3) {
			Log.e((String)"Quicksettings.Util", (String)"Couldn't get app version");
			return "notfound";
		}
	}

	public boolean isAppExist(String string) {
		if (string == null) return false;
		if ("".equals(string)) {
			return false;
		}
		try {
			this.mContext.getPackageManager().getApplicationInfo(string, 8192);
			return true;
		}
		catch (PackageManager.NameNotFoundException var2_2) {
			return false;
		}
	}
}
