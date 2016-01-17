package com.android.systemui.helper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

public final class ResProvider {
	private static boolean ALWAYS_SHOW_DATA_TYPE = false;
	private static boolean HIDE_SIGNAL_FOR_MOBILE_OFF = false;
	private static boolean SHOW_ALPHA_TAG = false;
	private static boolean SHOW_AU_FOR_KDDI = false;
	private static boolean SHOW_BLANK_MEDIA_NOTIFICATION = true;
	private static boolean USE_3G_SIGNAL_ICON = false;
	private static boolean USE_4G_SIGNAL_ICON = false;
	private static boolean USE_ATT_SIGNAL_ICON = false;
	private static boolean USE_BIG_SIGNAL_ICON = false;
	private static boolean USE_CN_WIFI_LABEL = false;
	private static boolean USE_GOOGLE_SEARCH = true;
	private static boolean USE_INDOOR_SAVING_BL = false;
	private static boolean USE_LTE_SIGNAL_ICON = false;
	private static boolean USE_NO_SIM_FOR_EACH_SLOT = false;
	private static boolean USE_TWENTY_PERCENT_BATTERY_LEVEL = false;
	private static String VENDOR = "";
	private static XmlPullParser mParser;
	private static XmlPullParserFactory mXmlParserFactory;

	private ResProvider() {
		super();
	}

	private static void checkAndSetCustomization(ResProvider.XMLData var0) {
		if(var0.getName().equals("hide_signal_for_mobile_off") && var0.getValue().equals("true")) {
			HIDE_SIGNAL_FOR_MOBILE_OFF = true;
		}

		if(var0.getName().equals("show_au_for_kddi") && var0.getValue().equals("true")) {
			SHOW_AU_FOR_KDDI = true;
		}

		if(var0.getName().equals("use_att_signal_icon") && var0.getValue().equals("true")) {
			USE_ATT_SIGNAL_ICON = true;
		}

		if(var0.getName().equals("use_4g_signal_icon") && var0.getValue().equals("true")) {
			USE_4G_SIGNAL_ICON = true;
		}

		if(var0.getName().equals("use_lte_signal_icon") && var0.getValue().equals("true")) {
			USE_LTE_SIGNAL_ICON = true;
		}

		if(var0.getName().equals("show_alpha_tag") && var0.getValue().equals("true")) {
			SHOW_ALPHA_TAG = true;
		}

		if(var0.getName().equals("use_twenty_percent_battery_level") && var0.getValue().equals("true")) {
			USE_TWENTY_PERCENT_BATTERY_LEVEL = true;
		}

		if(var0.getName().equals("use_cn_wifi_label") && var0.getValue().equals("true")) {
			USE_CN_WIFI_LABEL = true;
		}

		if(var0.getName().equals("use_google_search") && var0.getValue().equals("false")) {
			USE_GOOGLE_SEARCH = false;
		}

		if(var0.getName().equals("always_show_data_type") && var0.getValue().equals("true")) {
			ALWAYS_SHOW_DATA_TYPE = true;
		}

		if(var0.getName().equals("use_no_sim_for_each_slot") && var0.getValue().equals("true")) {
			USE_NO_SIM_FOR_EACH_SLOT = true;
		}

		if(var0.getName().equals("use_big_signal_icon") && var0.getValue().equals("true")) {
			USE_BIG_SIGNAL_ICON = true;
		}

		if(var0.getName().equals("use_3g_signal_icon") && var0.getValue().equals("true")) {
			USE_3G_SIGNAL_ICON = true;
		}

		if(var0.getName().equals("use_indoor_saving_bl") && var0.getValue().equals("true")) {
			USE_INDOOR_SAVING_BL = true;
		}

		if(var0.getName().equals("show_blank_media_notification") && var0.getValue().equals("false")) {
			SHOW_BLANK_MEDIA_NOTIFICATION = false;
		}

	}

	public static void init() {
		setVendorFromList(parseXML("/system/etc/AsusSystemUIRes/vendor.xml"));
		setCustomizationFromList(parseXML("/system/etc/AsusSystemUIRes/customization.xml"));
		setCustomizationFromList(parseXML("/system/etc/AsusSystemUIRes/customization-device.xml"));
	}

	public static final boolean isAlwaysShowDataType() {
		return ALWAYS_SHOW_DATA_TYPE;
	}

	public static final boolean isHideSignalForMobileOff() {
		return HIDE_SIGNAL_FOR_MOBILE_OFF;
	}

	public static final boolean isIndoorSaving_BL() {
		return USE_INDOOR_SAVING_BL;
	}

	public static final boolean isShowAlphaTag() {
		return SHOW_ALPHA_TAG;
	}

	public static final boolean isShowAuForKddi() {
		return SHOW_AU_FOR_KDDI;
	}

	public static final boolean isShowBlankMediaNotification() {
		return SHOW_BLANK_MEDIA_NOTIFICATION;
	}

	public static final boolean isUse3GSignalIcon() {
		return USE_3G_SIGNAL_ICON;
	}

	public static final boolean isUse4GSignalIcon() {
		return USE_4G_SIGNAL_ICON;
	}

	public static final boolean isUseATTSignalIcon() {
		return USE_ATT_SIGNAL_ICON;
	}

	public static final boolean isUseBigSignalIcon() {
		return USE_BIG_SIGNAL_ICON;
	}

	public static final boolean isUseCNWifiLabel() {
		return USE_CN_WIFI_LABEL;
	}

	public static final boolean isUseGoogleSearch() {
		return USE_GOOGLE_SEARCH;
	}

	public static final boolean isUseLTESignalIcon() {
		return USE_LTE_SIGNAL_ICON;
	}

	public static final boolean isUseNoSimForEachSlot() {
		return USE_NO_SIM_FOR_EACH_SLOT;
	}

	public static final boolean isUseTwentyPercentLevel() {
		return USE_TWENTY_PERCENT_BATTERY_LEVEL;
	}

	/*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
	private static ArrayList<XMLData> parseXML(String var0) {
		return null;
	}


	private static void setCustomizationFromList(ArrayList<ResProvider.XMLData> var0) {
		if(var0 != null) {
			for(int var1 = 0; var1 < var0.size(); ++var1) {
				checkAndSetCustomization((ResProvider.XMLData)var0.get(var1));
			}
		}

	}

	private static void setVendorFromList(ArrayList<ResProvider.XMLData> var0) {
		if(var0 != null) {
			for(int var1 = 0; var1 < var0.size(); ++var1) {
				if(((ResProvider.XMLData)var0.get(var1)).getName().equals("vendor")) {
					VENDOR = ((ResProvider.XMLData)var0.get(var1)).getValue();
				}
			}
		}

	}

	private static class XMLData {
		String name;
		String type;
		String value;

		XMLData(String var1, String var2, String var3) {
			super();
			this.type = var1;
			this.name = var2;
			this.value = var3;
		}

		String getName() {
			return this.name;
		}

		String getValue() {
			return this.value;
		}

		public String toString() {
			return "" + this.type + "(" + this.name + "," + this.value + ")";
		}
	}
}
