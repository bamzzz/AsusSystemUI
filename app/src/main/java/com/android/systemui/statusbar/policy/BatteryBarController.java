package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.BatteryManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BatteryBarController extends LinearLayout {

    private static final String TAG = "BatteryBarController";
	private static final String STATUSBAR_BATTERY_BAR = "battery_bar";
	private static final String STATUSBAR_BATTERY_BAR_STYLE = "battery_bar_style";
	private static final String STATUSBAR_BATTERY_BAR_THICKNESS = "battery_bar_thickness";

	BatteryBar mainBar;
    BatteryBar alternateStyleBar;

    public static final int STYLE_REGULAR = 0;
    public static final int STYLE_SYMMETRIC = 1;

    int mStyle = STYLE_REGULAR;
    int mLocation = 0;

    protected final static int CURRENT_LOC = 1;
    int mLocationToLookFor = 0;

    private int mBatteryLevel = 0;
    private boolean mBatteryCharging = false;

    boolean isAttached = false;
    boolean isVertical = false;
	private Context mContext;

	class SettingsObserver extends ContentObserver {

        public SettingsObserver(Handler handler) {
            super(handler);
        }

        void observer() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR), false, this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STYLE), false,
                    this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_THICKNESS),
                    false, this);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateSettings();
        }
    }

    public BatteryBarController(Context context, AttributeSet attrs) {
        super(context, attrs);
		this.mContext = context;
        if (attrs != null) {
            String ns = "http://schemas.android.com/apk/res/com.android.systemui";
            mLocationToLookFor = attrs.getAttributeIntValue(ns, "viewLocation", 0);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isAttached) {
            isVertical = (getLayoutParams().height == LayoutParams.MATCH_PARENT);

            isAttached = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
            getContext().registerReceiver(mIntentReceiver, filter);

            SettingsObserver observer = new SettingsObserver(new Handler());
            observer.observer();
            updateSettings();
        }
    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                mBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                mBatteryCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0) == BatteryManager.BATTERY_STATUS_CHARGING;
                setLastBatteryLevel(context, mBatteryLevel);
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        if (isAttached) {
            isAttached = false;
            removeBars();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isAttached) {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSettings();
                }
            }, 500);

        }
    }

    public void addBars() {
        // set heights
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float dp = (float) Settings.System.getInt(getContext().getContentResolver(),
                STATUSBAR_BATTERY_BAR_THICKNESS, 1);
        int pixels = (int) ((metrics.density * dp) + 0.5);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) getLayoutParams();

        if (isVertical)
            params.width = pixels;
        else
            params.height = pixels;
        setLayoutParams(params);

        if (isVertical)
            params.width = pixels;
        else
            params.height = pixels;
        setLayoutParams(params);
        mBatteryLevel = getLastBatteryLevel(getContext());
        if (mStyle == STYLE_REGULAR) {
            addView(new BatteryBar(mContext, mBatteryCharging, mBatteryLevel, isVertical),
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT, 1));
        } else if (mStyle == STYLE_SYMMETRIC) {
            BatteryBar bar1 = new BatteryBar(mContext, mBatteryCharging, mBatteryLevel, isVertical);
            BatteryBar bar2 = new BatteryBar(mContext, mBatteryCharging, mBatteryLevel, isVertical);

            if (isVertical) {
                bar2.setRotation(180);
                addView(bar2, (new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1)));
                addView(bar1, (new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1)));
            } else {
                bar1.setRotation(180);
                addView(bar1, (new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1)));
                addView(bar2, (new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1)));
            }

        }
    }

	private int getLastBatteryLevel(Context context) {
		return read(context).getInt("last_battery_level", 50);
	}

	private void setLastBatteryLevel(Context context, int var1) {
		edit(context).putInt("last_battery_level", var1).commit();
	}

	public static SharedPreferences read(Context context) {
		return context.getSharedPreferences("status_bar", 0);
	}

	public static SharedPreferences.Editor edit(Context context) {
		return context.getSharedPreferences("status_bar", 0).edit();
	}

	public void removeBars() {
        removeAllViews();
    }

    public void updateSettings() {
        mStyle = Settings.System.getInt(getContext().getContentResolver(),
                STATUSBAR_BATTERY_BAR_STYLE, 0);
        mLocation = Settings.System.getInt(getContext().getContentResolver(),
                STATUSBAR_BATTERY_BAR, 0);

        if (isLocationValid(mLocation)) {
            removeBars();
            addBars();
            setVisibility(View.VISIBLE);
        } else {
            removeBars();
            setVisibility(View.GONE);
        }
    }

    protected boolean isLocationValid(int location) {
        return mLocationToLookFor == location;
    }
}