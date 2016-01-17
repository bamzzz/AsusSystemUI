package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.Settings.System;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.android.systemui.DemoMode;
import com.android.systemui.R;
import com.bamzzz.ComotID;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

//import libcore.icu.LocaleData;

public class Clock extends TextView implements DemoMode {
	private static String sHourFormatValue;
	private static boolean sIs24Hour = false;
	private static Locale sIs24HourLocale;
	private static final Object sLocaleLock = new Object();
	private final int mAmPmStyle;
	private boolean mAttached;
	private Calendar mCalendar;
	private SimpleDateFormat mClockFormat;
	private String mClockFormatString;
	private boolean mDemoMode;
	private final BroadcastReceiver mIntentReceiver;
	private Locale mLocale;
	private ContentObserver mObserver;

	public Clock(Context var1) {
		this(var1, (AttributeSet)null);
	}

	public Clock(Context var1, AttributeSet var2) {
		this(var1, var2, 0);
	}

	public Clock(Context var1, AttributeSet var2, int var3) {
		super(var1, var2, var3);
		this.mObserver = new ContentObserver(new Handler()) {
			public void onChange(boolean var1) {
				Clock.sHourFormatValue = Settings.System.getString(Clock.this.getContext().getContentResolver(), "time_12_24");
				Clock.this.updateClock();
			}
		};
		this.mIntentReceiver = new BroadcastReceiver() {
			public void onReceive(Context var1, Intent var2) {
				String var3 = var2.getAction();
				if(var3.equals("android.intent.action.TIMEZONE_CHANGED")) {
					var3 = var2.getStringExtra("time-zone");
					Clock.this.mCalendar = Calendar.getInstance(TimeZone.getTimeZone(var3));
					if(Clock.this.mClockFormat != null) {
						Clock.this.mClockFormat.setTimeZone(Clock.this.mCalendar.getTimeZone());
					}
				} else if(var3.equals("android.intent.action.CONFIGURATION_CHANGED")) {
					Locale var4 = Clock.this.getResources().getConfiguration().locale;
					if(!var4.equals(Clock.this.mLocale)) {
						Clock.this.mLocale = var4;
						Clock.this.mClockFormatString = "";
					}
				} else if(var3.equals("android.intent.action.TIME_TICK")) {
					Log.d("StatusBar.Clock", "Receive ACTION_TIME_TICK");
				}

				Clock.this.updateClock();
			}
		};
		TypedArray var6 = var1.getTheme().obtainStyledAttributes(var2, ComotID.GetStyle("Clock"), 0, 0);

		try {
			this.mAmPmStyle = var6.getInt(0, 0);
		} finally {
			var6.recycle();
		}

	}

	private final CharSequence getSmallTime() {
		Context var7 = this.getContext();
		boolean var6 = is24HourFormat(var7);
		//LocaleData var10 = LocaleData.get(var7.getResources().getConfiguration().locale);
        String var10 = null;
		String var11;
		if(var6) {
			var11 = var10; //.timeFormat24;
		} else {
			var11 = var10; //.timeFormat12;
		}

		int var1;
		int var2;
		SimpleDateFormat var12;
		if(!var11.equals(this.mClockFormatString)) {
			String var8 = var11;
			if(this.mAmPmStyle != 0) {
				byte var4 = -1;
				boolean var3 = false;
				var1 = 0;

				while(true) {
					var2 = var4;
					if(var1 >= var11.length()) {
						break;
					}

					char var5 = var11.charAt(var1);
					boolean var9 = var3;
					if(var5 == 39) {
						if(!var3) {
							var9 = true;
						} else {
							var9 = false;
						}
					}

					if(!var9 && var5 == 97) {
						var2 = var1;
						break;
					}

					++var1;
					var3 = var9;
				}

				var8 = var11;
				if(var2 >= 0) {
					for(var1 = var2; var1 > 0 && Character.isWhitespace(var11.charAt(var1 - 1)); --var1) {
						;
					}

					var8 = var11.substring(0, var1) + '\uef00' + var11.substring(var1, var2) + "a" + '\uef01' + var11.substring(var2 + 1);
				}
			}

			var12 = new SimpleDateFormat(var8);
			this.mClockFormat = var12;
			this.mClockFormatString = var8;
		} else {
			var12 = this.mClockFormat;
		}

		Object var13 = var12.format(this.mCalendar.getTime());
		if(this.mAmPmStyle != 0) {
			var2 = ((String)var13).indexOf('\uef00');
			var1 = ((String)var13).indexOf('\uef01');
			if(var2 >= 0 && var1 > var2) {
				var13 = new SpannableStringBuilder((CharSequence)var13);
				if(this.mAmPmStyle == 2) {
					((SpannableStringBuilder)var13).delete(var2, var1 + 1);
				} else {
					if(this.mAmPmStyle == 1) {
						((SpannableStringBuilder)var13).setSpan(new RelativeSizeSpan(0.7F), var2, var1, 34);
					}

					((SpannableStringBuilder)var13).delete(var1, var1 + 1);
					((SpannableStringBuilder)var13).delete(var2, var2 + 1);
				}
			}
		}

		return (CharSequence)var13;
	}

	public static boolean is24HourFormat(Context param0) {
		// $FF: Couldn't be decompiled
        return true;
	}

	public void dispatchDemoCommand(String var1, Bundle var2) {
		if(!this.mDemoMode && var1.equals("enter")) {
			this.mDemoMode = true;
		} else if(this.mDemoMode && var1.equals("exit")) {
			this.mDemoMode = false;
			this.updateClock();
		} else if(this.mDemoMode && var1.equals("clock")) {
			var1 = var2.getString("millis");
			String var5 = var2.getString("hhmm");
			if(var1 != null) {
				this.mCalendar.setTimeInMillis(Long.parseLong(var1));
			} else if(var5 != null && var5.length() == 4) {
				int var4 = Integer.parseInt(var5.substring(0, 2));
				int var3 = Integer.parseInt(var5.substring(2));
				this.mCalendar.set(10, var4);
				this.mCalendar.set(12, var3);
			}

			this.setText(this.getSmallTime());
		}

	}

	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(!this.mAttached) {
			this.mAttached = true;
			IntentFilter var1 = new IntentFilter();
			var1.addAction("android.intent.action.TIME_TICK");
			var1.addAction("android.intent.action.TIME_SET");
			var1.addAction("android.intent.action.TIMEZONE_CHANGED");
			var1.addAction("android.intent.action.CONFIGURATION_CHANGED");
			var1.addAction("android.intent.action.USER_SWITCHED");
			var1.addAction("android.intent.action.SCREEN_ON");
			this.getContext().registerReceiver(this.mIntentReceiver, var1, (String) null, this.getHandler());
			this.mObserver.onChange(false);
			this.getContext().getContentResolver().registerContentObserver(System.getUriFor("time_12_24"), false, this.mObserver);
		}

		this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
		this.updateClock();
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if(this.mAttached) {
			this.getContext().unregisterReceiver(this.mIntentReceiver);
			this.getContext().getContentResolver().unregisterContentObserver(this.mObserver);
			this.mAttached = false;
		}

	}

	public final void updateClock() {
		if(!this.mDemoMode && this.mCalendar != null) {
			this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
			this.setText(this.getSmallTime());
		}

	}
}
