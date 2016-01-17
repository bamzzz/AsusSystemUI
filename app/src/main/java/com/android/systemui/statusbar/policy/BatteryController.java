package com.android.systemui.statusbar.policy;

/**
 * Created by bamzzz on 16 Jan 2016.
 */

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

//import cyanogenmod.providers.CMSettings;

public class BatteryController extends BroadcastReceiver {
    private static final String TAG = "BatteryController";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    public static final int STYLE_ICON_PORTRAIT = 0;
    public static final int STYLE_CIRCLE = 2;
    public static final int STYLE_GONE = 4;
    public static final int STYLE_ICON_LANDSCAPE = 5;
    public static final int STYLE_TEXT = 6;

    public static final int PERCENTAGE_MODE_OFF = 0;
    public static final int PERCENTAGE_MODE_INSIDE = 1;
    public static final int PERCENTAGE_MODE_OUTSIDE = 2;

    private static final String STATUS_BAR_BATTERY_STYLE = "status_bar_battery_style";
    private static final String STATUS_BAR_SHOW_BATTERY_PERCENT = "status_bar_show_battery_percent";
    private static final String STATUS_BAR_BATTERY_COLOR = "battery_icon_color";
    private static final String STATUS_BAR_BATTERY_SCALE = "battery_icon_scale";
    private static final String STATUS_BAR_BATTERY_PERCENT_COLOR = "battery_text_color";
    private static final String STATUS_BAR_BATTERY_PERCENT_SIZE = "battery_text_size";
    private static final String STATUS_BAR_BATTERY_WHAT = "battery_style_what";
    private static final String STATUS_BAR_BATTERY_ALIGN = "battery_cluster_position";

    private final ArrayList<BatteryStateChangeCallback> mChangeCallbacks = new ArrayList<>();
    private final PowerManager mPowerManager;

    private int mLevel;
    private boolean mPluggedIn;
    private boolean mCharging;
    private boolean mCharged;
    private boolean mPowerSave;

    private int mStyle;
    private int mPercentMode;

    private int mBatteryColor;
    private int mBatteryScale;
    private int mPercentColor;
    private int mPercentSize;
    private int mBatteryStyle;
    private int mBatteryAlign;

    private int mUserId;
    private SettingsObserver mObserver;


    public BatteryController(Context context) {
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        //filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGING);
        filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGING");
        context.registerReceiver(this, filter);

        updatePowerSave();

        Handler handler = new Handler();

        mObserver = new SettingsObserver(context, handler);
        mObserver.observe();
    }
    
    /*public BatteryController(Context context, Handler handler) {
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        //filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGING);
        filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGING");
        context.registerReceiver(this, filter);

        updatePowerSave();

        mObserver = new SettingsObserver(context, handler);
        mObserver.observe();
    }*/

    public void setUserId(int userId) {
        mUserId = userId;
        mObserver.observe();
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("BatteryController state:");
        pw.print("  mLevel="); pw.println(mLevel);
        pw.print("  mPluggedIn="); pw.println(mPluggedIn);
        pw.print("  mCharging="); pw.println(mCharging);
        pw.print("  mCharged="); pw.println(mCharged);
        pw.print("  mPowerSave="); pw.println(mPowerSave);
    }

    public void addStateChangedCallback(BatteryStateChangeCallback cb) {
        mChangeCallbacks.add(cb);
        cb.onBatteryLevelChanged(mLevel, mPluggedIn, mCharging);
        cb.onBatteryStyleChanged(mStyle, mPercentMode);
        // MOD
        cb.onBatteryAneChanged(mBatteryStyle, mBatteryAlign);
        cb.onBatteryBamzzzChanged(mBatteryColor, mBatteryScale, mPercentColor, mPercentSize);
        // End
    }

    public void removeStateChangedCallback(BatteryStateChangeCallback cb) {
        mChangeCallbacks.remove(cb);
    }

    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            mLevel = (int)(100f
                    * intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    / intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100));
            mPluggedIn = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0;

            final int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            mCharged = status == BatteryManager.BATTERY_STATUS_FULL;
            mCharging = mCharged || status == BatteryManager.BATTERY_STATUS_CHARGING;

            fireBatteryLevelChanged();
        } else if (action.equals(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)) {
            updatePowerSave();
        } else if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGING")) {
            setPowerSave(intent.getBooleanExtra("mode", false));
        } /*else if (action.equals(PowerManager.ACTION_POWER_SAVE_MODE_CHANGING)) {
            setPowerSave(intent.getBooleanExtra(PowerManager.EXTRA_POWER_SAVE_MODE, false));
        }*/
    }

    public boolean isPowerSave() {
        return mPowerSave;
    }

    private void updatePowerSave() {
        setPowerSave(mPowerManager.isPowerSaveMode());
    }

    private void setPowerSave(boolean powerSave) {
        if (powerSave == mPowerSave) return;
        mPowerSave = powerSave;
        if (DEBUG) Log.d(TAG, "Power save is " + (mPowerSave ? "on" : "off"));
        firePowerSaveChanged();
    }

    private void fireBatteryLevelChanged() {
        final int N = mChangeCallbacks.size();
        for (int i = 0; i < N; i++) {
            mChangeCallbacks.get(i).onBatteryLevelChanged(mLevel, mPluggedIn, mCharging);
        }
    }

    private void firePowerSaveChanged() {
        final int N = mChangeCallbacks.size();
        for (int i = 0; i < N; i++) {
            mChangeCallbacks.get(i).onPowerSaveChanged();
        }
    }

    private void fireSettingsChanged() {
        final int N = mChangeCallbacks.size();
        for (int i = 0; i < N; i++) {
            mChangeCallbacks.get(i).onBatteryStyleChanged(mStyle, mPercentMode);
        }
    }

    // MOD
    private void aneSettingsChanged() {
        final int N = mChangeCallbacks.size();
        for (int i = 0; i < N; i++) {
            mChangeCallbacks.get(i).onBatteryAneChanged(mBatteryStyle, mBatteryAlign);
        }
    }

    private void bamzzzSettingsChanged() {
        final int N = mChangeCallbacks.size();
        for (int i = 0; i < N; i++) {
            mChangeCallbacks.get(i).onBatteryBamzzzChanged(mBatteryColor, mBatteryScale, mPercentColor, mPercentSize);
        }
    }
    // End

    public interface BatteryStateChangeCallback {
        void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging);
        void onPowerSaveChanged();
        void onBatteryStyleChanged(int style, int percentMode);
        // MOD
        void onBatteryAneChanged(int batteryStyle, int batteryAlign);
        void onBatteryBamzzzChanged(int batteryColor, int batteryScale, int percentColor, int percentSize);
        // End
    }

    private final class SettingsObserver extends ContentObserver {
        private ContentResolver mResolver;
        private boolean mRegistered;

        private final Uri STYLE_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_STYLE);
        private final Uri PERCENT_URI =
                Settings.System.getUriFor(STATUS_BAR_SHOW_BATTERY_PERCENT);
        // MOD //
        private final Uri BATTERY_COLOR_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_COLOR);
        private final Uri BATTERY_SCALE_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_SCALE);
        private final Uri PERCENT_COLOR_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_PERCENT_COLOR);
        private final Uri PERCENT_SIZE_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_PERCENT_SIZE);
        private final Uri BATTERY_WHAT_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_WHAT);
        private final Uri BATTERY_ALIGN_URI =
                Settings.System.getUriFor(STATUS_BAR_BATTERY_ALIGN);
        // End

        public SettingsObserver(Context context, Handler handler) {
            super(handler);
            mResolver = context.getContentResolver();
        }

        public void observe() {
            if (mRegistered) {
                mResolver.unregisterContentObserver(this);
            }
            mResolver.registerContentObserver(STYLE_URI, false, this);
            mResolver.registerContentObserver(PERCENT_URI, false, this);
            // MOD
            mResolver.registerContentObserver(BATTERY_COLOR_URI, false, this);
            mResolver.registerContentObserver(BATTERY_SCALE_URI, false, this);
            mResolver.registerContentObserver(PERCENT_COLOR_URI, false, this);
            mResolver.registerContentObserver(PERCENT_SIZE_URI, false, this);
            mResolver.registerContentObserver(BATTERY_WHAT_URI, false, this);
            mResolver.registerContentObserver(BATTERY_ALIGN_URI, false, this);
            // End
            mRegistered = true;

            update();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            update();
        }

        private void update() {
            mStyle = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_STYLE, 0);
            mPercentMode = Settings.System.getInt(mResolver,
                    STATUS_BAR_SHOW_BATTERY_PERCENT, 0);
            // MOD
            mBatteryColor = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_COLOR, 0xffff);
            mBatteryScale = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_SCALE, 4);
            mPercentColor = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_PERCENT_COLOR, 0xffff);
            mPercentSize = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_PERCENT_SIZE, 12);
            mBatteryStyle = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_WHAT, 1);
            mBatteryAlign = Settings.System.getInt(mResolver,
                    STATUS_BAR_BATTERY_ALIGN, 1);
            aneSettingsChanged();
            bamzzzSettingsChanged();
            // End
            fireSettingsChanged();
        }
    };
}