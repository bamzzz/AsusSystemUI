/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.util.Log
 *  android.util.Slog
 *  com.asus.systemui.util.ThemeUtils
 */
package com.asus.systemui.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
//import android.util.Slog;

/*
 * Exception performing whole class analysis ignored.
 */
public class ThemeUtils {
	public static final String[] QB_BOOST_DRAWABLE_ARRAY = new String[]{"asus_phone_quickbox_clean_memory_green", "asus_phone_quickbox_clean_memory_yellow"};
	public static final String[] QB_BOOST_RING_GREEN_DRAWABLE_ARRAY = new String[]{"asus_quicksetting_boost_green_color_start", "asus_quicksetting_boost_green_color_end", "asus_quicksetting_boost_green_color_end"};
	public static final String[] QB_BOOST_RING_YELLOW_DRAWABLE_ARRAY = new String[]{"asus_quicksetting_boost_yellow_color_start", "asus_quicksetting_boost_yellow_color_middle", "asus_quicksetting_boost_yellow_color_end"};
	public static final String[] QB_BOOST_TEXT_COLOR_ARRAY;
	public static final String[] QS_MULTISIM_DRAWABLE_ARRAY;
	public static final String[] QS_SPLENDID_DRAWABLE_ARRAY;
	public static final String[] QS_VIBRATE_DRAWABLE_ARRAY;
	private static ThemeUtils mInstance;
	private boolean isThemeChangeNeeded = false;
	private Context mContext;
	private String mCurrentPkgName = "com.asus.res.defaulttheme";
	private Resources mCurrentThemeRes = null;
	private PackageManager pm;

	static {
		QS_VIBRATE_DRAWABLE_ARRAY = new String[]{"asus_phone_control_onoff_mute", "asus_phone_control_onoff_vibrate", "asus_phone_control_onoff_volume"};
		QS_SPLENDID_DRAWABLE_ARRAY = new String[]{"asus_phone_control_onoff_balance", "asus_phone_control_onoff_reading", "asus_phone_control_onoff_vivid", "asus_phone_control_onoff_customized"};
		QS_MULTISIM_DRAWABLE_ARRAY = new String[]{"asus_phone_control_onoff_sim1", "asus_phone_control_onoff_sim2", "asus_phone_control_onoff_sim_prompt"};
		QB_BOOST_TEXT_COLOR_ARRAY = new String[]{"asus_phone_memory_text_color_green", "asus_phone_memory_text_color_yellow"};
		mInstance = null;
	}

	private ThemeUtils(Context context) {
		this.mContext = context;
		this.pm = this.mContext.getPackageManager();
		this.updateThemeInfo();
	}

	/*
	 * Enabled unnecessary exception pruning
	 */
	public static ThemeUtils getInstance(Context context) {
		if (mInstance != null) return mInstance;
		synchronized (ThemeUtils.class) {
			if (mInstance != null) return mInstance;
			mInstance = new ThemeUtils(context);
			return mInstance;
		}
	}

	public static Resources getPackageResource(Context context, String string) {
		PackageManager packageManager = context.getPackageManager();
		try {
			return packageManager.getResourcesForApplication(string);
		}
		catch (PackageManager.NameNotFoundException var3_4) {
			var3_4.printStackTrace();
			return null;
		}
	}

	public static String getPkgNameFromPREF(Context context) {
		String string = context.getSharedPreferences("THEME_PREF", 0).getString("THEME_PKG_NAME", "");
		if (!string.equals("")) return string;
		return "com.asus.res.defaulttheme";
	}

	public static void savePkgNameToPREF(Context context, String string) {
		context.getSharedPreferences("THEME_PREF", 0).edit().putString("THEME_PKG_NAME", string).commit();
	}

	public String getCurrentPkgName() {
		return this.mCurrentPkgName;
	}

	public int getResId(String string, String string2) {
		try {
			return this.mCurrentThemeRes.getIdentifier(string, string2, this.mCurrentPkgName);
		}
		catch (Resources.NotFoundException var3_4) {
			var3_4.printStackTrace();
			return 0;
		}
	}

	public Resources getThemeRes() {
		return this.mCurrentThemeRes;
	}

	public boolean isChangeNeeded() {
		if (!this.isThemeChangeNeeded) return false;
		if (this.mCurrentThemeRes == null) return false;
		return true;
	}

	public boolean isValidRes(int n) {
		if (n == 0) return false;
		return true;
	}

	public void setThemeChanged(boolean bl) {
		this.isThemeChangeNeeded = bl;
	}

	public void updateThemeInfo() {
		String string = ThemeUtils.getPkgNameFromPREF((Context)this.mContext);
		if (!string.equals("com.asus.res.defaulttheme")) {
			try {
				this.mCurrentThemeRes = this.pm.getResourcesForApplication(string);
				this.isThemeChangeNeeded = true;
				this.mCurrentPkgName = string;
			}
			catch (PackageManager.NameNotFoundException var5_2) {
				ThemeUtils.savePkgNameToPREF((Context)this.mContext, (String)"com.asus.res.defaulttheme");
				Log.v((String)"SystemUI.ThemeUtils", (String)("Resources not found in " + this.mCurrentPkgName));
			}
		} else {
			this.mCurrentThemeRes = null;
			this.isThemeChangeNeeded = false;
			this.mCurrentPkgName = "com.asus.res.defaulttheme";
		}
		//Slog.d((String)"ThemeChanged", (String)"UpdateThemeInfo---");
		//Slog.d((String)"ThemeChanged", (String)("isThemeChangeNeeded: " + this.isThemeChangeNeeded));
		//Slog.d((String)"ThemeChanged", (String)("mCurrentPkgName: " + this.mCurrentPkgName));
	}
}
