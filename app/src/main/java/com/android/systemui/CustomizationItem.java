package com.android.systemui;

import com.android.systemui.helper.ResProvider;
import com.android.systemui.statusbar.policy.AccessibilityContentDescriptions;
import com.android.systemui.statusbar.policy.TelephonyIcons;

public class CustomizationItem {
	public static final int ACCESSIBILITY_DATA_CONNECTION_LTE;
	public static final boolean ALWAYS_SHOW_DATA_TYPE;
	public static final boolean BL_IS_INDOORSAVING;
	public static final int DATA_1X;
	public static final int DATA_3G;
	public static final int DATA_4G;
	public static final int DATA_4G_P;
	public static final int DATA_E;
	public static final int DATA_G;
	public static final int DATA_H;
	public static final int DATA_H_P;
	public static final int DATA_LTE;
	public static final int DEFAULT_DUMMY_NOTIFICATION_TITLE;
	public static final boolean HIDE_SIGNAL_FOR_MOBILE_OFF;
	public static final int[] PHONE_SIGNAL_STRENGTH;
	public static final int QS_DATA_3G;
	public static final int QS_DATA_H;
	public static final int QS_DATA_H_P;
	public static final int QS_DATA_LTE;
	public static final int[][] QS_TELEPHONY_SIGNAL_STRENGTH;
	public static final int ROAMING_ICON;
	public static final boolean SHOW_ALPHA_TAG;
	public static final boolean SHOW_AU_FOR_KDDI;
	public static final boolean SHOW_BLANK_MEDIA_NOTIFICATION;
	public static final int STAT_SYS_BATTERY;
	public static final int STAT_SYS_BATTERY_CHARGE;
	public static final int STAT_SYS_BATTERY_QUICK_CHARGE;
	public static final int STAT_SYS_DOCK_BATTERY;
	public static final int STAT_SYS_DOCK_BATTERY_CHARGE;
	public static final int STAT_SYS_PAD_BATTERY;
	public static final int STAT_SYS_PAD_BATTERY_CHARGE;
	public static final int SUPER_STATUS_BAR;
	public static final int TELEPHONY_SIGNAL_NULL;
	public static final int[][] TELEPHONY_SIGNAL_STRENGTH;
	public static final boolean USE_3G_SIGNAL_ICON;
	public static final boolean USE_4G_SIGNAL_ICON;
	public static final boolean USE_ATT_SIGNAL_ICON;
	public static final boolean USE_BIG_SIGNAL_ICON;
	public static final boolean USE_GOOGLE_SEARCH;
	public static final boolean USE_LTE_SIGNAL_ICON;
	public static final boolean USE_NO_SIM_FOR_EACH_SLOT;
	public static final boolean USE_TWENTY_PERCENT_BATTERY_LEVEL;
	public static final int WIFI_LABEL;

	static {
		int var2 = 2130837926;
		int var3 = 2130838092;
		int var1 = 2130838094;
		HIDE_SIGNAL_FOR_MOBILE_OFF = ResProvider.isHideSignalForMobileOff();
		SHOW_AU_FOR_KDDI = ResProvider.isShowAuForKddi();
		USE_ATT_SIGNAL_ICON = ResProvider.isUseATTSignalIcon();
		USE_LTE_SIGNAL_ICON = ResProvider.isUseLTESignalIcon();
		USE_4G_SIGNAL_ICON = ResProvider.isUse4GSignalIcon();
		USE_BIG_SIGNAL_ICON = ResProvider.isUseBigSignalIcon();
		USE_3G_SIGNAL_ICON = ResProvider.isUse3GSignalIcon();
		SHOW_ALPHA_TAG = ResProvider.isShowAlphaTag();
		USE_TWENTY_PERCENT_BATTERY_LEVEL = ResProvider.isUseTwentyPercentLevel();
		int var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837884;
		} else {
			var0 = 2130838275;
		}

		STAT_SYS_BATTERY = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837885;
		} else {
			var0 = 2130838284;
		}

		STAT_SYS_BATTERY_CHARGE = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837886;
		} else {
			var0 = 2130838301;
		}

		STAT_SYS_BATTERY_QUICK_CHARGE = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837889;
		} else {
			var0 = 2130838339;
		}

		STAT_SYS_PAD_BATTERY = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837890;
		} else {
			var0 = 2130838340;
		}

		STAT_SYS_PAD_BATTERY_CHARGE = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837887;
		} else {
			var0 = 2130838319;
		}

		STAT_SYS_DOCK_BATTERY = var0;
		if(USE_TWENTY_PERCENT_BATTERY_LEVEL) {
			var0 = 2130837888;
		} else {
			var0 = 2130838320;
		}

		STAT_SYS_DOCK_BATTERY_CHARGE = var0;
		ALWAYS_SHOW_DATA_TYPE = ResProvider.isAlwaysShowDataType();
		USE_NO_SIM_FOR_EACH_SLOT = ResProvider.isUseNoSimForEachSlot();
		if(ResProvider.isUseCNWifiLabel()) {
			var0 = 2131296729;
		} else {
			var0 = 2131296611;
		}

		WIFI_LABEL = var0;
		USE_GOOGLE_SEARCH = ResProvider.isUseGoogleSearch();
		if(USE_GOOGLE_SEARCH) {
			var0 = 2131296732;
		} else {
			var0 = 2131296731;
		}

		DEFAULT_DUMMY_NOTIFICATION_TITLE = var0;
		if(USE_ATT_SIGNAL_ICON) {
			var0 = 2130838274;
		} else {
			var0 = 2130838354;
		}

		TELEPHONY_SIGNAL_NULL = var0;
		int[][] var4;
		if(USE_ATT_SIGNAL_ICON) {
			var4 = TelephonyIcons.TELEPHONY_ATT_SIGNAL_STRENGTH;
		} else {
			var4 = TelephonyIcons.TELEPHONY_SIGNAL_STRENGTH;
		}

		TELEPHONY_SIGNAL_STRENGTH = var4;
		if(USE_ATT_SIGNAL_ICON) {
			var4 = TelephonyIcons.QS_TELEPHONY_ATT_SIGNAL_STRENGTH;
		} else {
			var4 = TelephonyIcons.QS_TELEPHONY_SIGNAL_STRENGTH;
		}

		QS_TELEPHONY_SIGNAL_STRENGTH = var4;
		int[] var5;
		if(USE_ATT_SIGNAL_ICON) {
			var5 = AccessibilityContentDescriptions.PHONE_ATT_SIGNAL_STRENGTH;
		} else {
			var5 = AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH;
		}

		PHONE_SIGNAL_STRENGTH = var5;
		if(USE_4G_SIGNAL_ICON) {
			var0 = TelephonyIcons.TELEPHONY_SIGNAL_STRENGTH_4G_IN[0];
		} else if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837920;
		} else {
			var0 = 2130838309;
		}

		DATA_3G = var0;
		if(USE_4G_SIGNAL_ICON) {
			var0 = 2130838094;
		} else {
			var0 = 2130838092;
		}

		QS_DATA_3G = var0;
		if(USE_4G_SIGNAL_ICON) {
			var0 = TelephonyIcons.TELEPHONY_SIGNAL_STRENGTH_4G_IN[0];
		} else if(USE_3G_SIGNAL_ICON) {
			var0 = DATA_3G;
		} else if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837924;
		} else {
			var0 = 2130838314;
		}

		DATA_H = var0;
		if(USE_4G_SIGNAL_ICON) {
			var0 = 2130838094;
		} else if(USE_3G_SIGNAL_ICON) {
			var0 = 2130838092;
		} else {
			var0 = 2130838103;
		}

		QS_DATA_H = var0;
		if(USE_4G_SIGNAL_ICON) {
			var0 = TelephonyIcons.TELEPHONY_SIGNAL_STRENGTH_4G_IN[0];
		} else if(USE_3G_SIGNAL_ICON) {
			var0 = DATA_3G;
		} else if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837925;
		} else {
			var0 = 2130838315;
		}

		DATA_H_P = var0;
		if(USE_4G_SIGNAL_ICON) {
			var0 = 2130838094;
		} else {
			var0 = var3;
			if(!USE_3G_SIGNAL_ICON) {
				var0 = 2130838103;
			}
		}

		QS_DATA_H_P = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837921;
		} else {
			var0 = 2130838310;
		}

		DATA_4G = var0;
		if(USE_LTE_SIGNAL_ICON) {
			var0 = TelephonyIcons.TELEPHONY_SIGNAL_STRENGTH_LTE_IN[0];
		} else if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837926;
		} else {
			var0 = DATA_4G;
		}

		DATA_LTE = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837923;
		} else {
			var0 = 2130838313;
		}

		DATA_G = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837922;
		} else {
			var0 = 2130838312;
		}

		DATA_E = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837919;
		} else {
			var0 = 2130838308;
		}

		DATA_1X = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = 2130837927;
		} else {
			var0 = 2130838317;
		}

		ROAMING_ICON = var0;
		if(USE_BIG_SIGNAL_ICON) {
			var0 = var2;
		} else {
			var0 = 2130838311;
		}

		DATA_4G_P = var0;
		var0 = var1;
		if(USE_LTE_SIGNAL_ICON) {
			var0 = 2130838105;
		}

		QS_DATA_LTE = var0;
		if(USE_LTE_SIGNAL_ICON) {
			var0 = 2131296504;
		} else {
			var0 = 2131296503;
		}

		ACCESSIBILITY_DATA_CONNECTION_LTE = var0;
		if(SHOW_ALPHA_TAG) {
			var0 = 2130968605;
		} else {
			var0 = 2130968696;
		}

		SUPER_STATUS_BAR = var0;
		BL_IS_INDOORSAVING = ResProvider.isIndoorSaving_BL();
		SHOW_BLANK_MEDIA_NOTIFICATION = ResProvider.isShowBlankMediaNotification();
	}
}
