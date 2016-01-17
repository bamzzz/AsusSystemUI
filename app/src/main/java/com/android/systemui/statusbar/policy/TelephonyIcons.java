package com.android.systemui.statusbar.policy;

/**
 * Created by bamzzz on 10 Jan 2016.
 */
public class TelephonyIcons {
	public static final int[][] DATA_SIGNAL_STRENGTH;
	public static final int[][] QS_TELEPHONY_ATT_SIGNAL_STRENGTH;
	public static final int[][] QS_TELEPHONY_SIGNAL_STRENGTH;
	public static final int[][] TELEPHONY_ATT_SIGNAL_STRENGTH = new int[][]{{2130838268, 2130838269, 2130838270, 2130838271, 2130838272, 2130838273}, {2130838268, 2130838263, 2130838264, 2130838265, 2130838266, 2130838267}};
	public static final int[][] TELEPHONY_NUM;
	public static final int[][] TELEPHONY_SIGNAL_STRENGTH = new int[][]{{2130838345, 2130838346, 2130838347, 2130838348, 2130838349}, {2130838345, 2130838321, 2130838322, 2130838323, 2130838324}};
	public static final int[] TELEPHONY_SIGNAL_STRENGTH_4G_IN;
	public static final int[] TELEPHONY_SIGNAL_STRENGTH_4G_OUT;
	public static final int[] TELEPHONY_SIGNAL_STRENGTH_LTE_IN;
	public static final int[] TELEPHONY_SIGNAL_STRENGTH_LTE_OUT;

	static {
		int[] var0 = new int[]{2130838097, 2130838098, 2130838099, 2130838100, 2130838101};
		QS_TELEPHONY_SIGNAL_STRENGTH = new int[][]{{2130838087, 2130838088, 2130838090, 2130838091, 2130838093}, var0};
		QS_TELEPHONY_ATT_SIGNAL_STRENGTH = new int[][]{{2130838041, 2130838042, 2130838043, 2130838044, 2130838045, 2130838046}, {2130838041, 2130838047, 2130838048, 2130838049, 2130838050, 2130838051}};
		TELEPHONY_SIGNAL_STRENGTH_LTE_IN = new int[]{2130838237, 2130838238, 2130838238, 2130838239, 2130838239, 2130838240};
		TELEPHONY_SIGNAL_STRENGTH_LTE_OUT = new int[]{2130838237, 2130838241, 2130838241, 2130838242, 2130838242, 2130838243};
		TELEPHONY_SIGNAL_STRENGTH_4G_IN = new int[]{2130838244, 2130838245, 2130838245, 2130838246, 2130838246, 2130838247};
		TELEPHONY_SIGNAL_STRENGTH_4G_OUT = new int[]{2130838244, 2130838248, 2130838248, 2130838249, 2130838249, 2130838250};
		TELEPHONY_NUM = new int[][]{{2130838326, 2130838325}, {2130838328, 2130838327}};
		DATA_SIGNAL_STRENGTH = TELEPHONY_SIGNAL_STRENGTH;
	}
}
