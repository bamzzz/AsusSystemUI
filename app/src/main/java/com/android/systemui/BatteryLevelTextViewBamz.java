package com.android.systemui;

/**
 * Created by bamzzz on 16 Jan 2016.
 */

/*
 * Copyright (C) 2014 The CyanogenMod Project
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

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.systemui.statusbar.policy.BatteryController;
import com.bamzzz.ComotID;

import java.text.NumberFormat;

public class BatteryLevelTextViewBamz extends TextView implements
        BatteryController.BatteryStateChangeCallback{
    private BatteryController mBatteryController;
    private boolean mBatteryCharging;
    private boolean mForceShow;
    private boolean mAttached;
    private int mRequestedVisibility;
    private int mStyle;
    private int mPercentMode;

    private int mBatteryColor;
    private int mBatteryScale;
    private int mPercentColor;
    private int mPercentSize;
    private int mBatteryStyle;
    private int mBatteryAlign;

    public BatteryLevelTextViewBamz(Context context, AttributeSet attrs) {
        super(context, attrs);
        // MOD
        ComotID.init(context.getPackageName(), context);
        // ENd
        mRequestedVisibility = getVisibility();
    }

    public void setForceShown(boolean forceShow) {
        mForceShow = forceShow;
        updateVisibility();
    }

    public void setBatteryController(BatteryController batteryController) {
        mBatteryController = batteryController;
        if (mAttached) {
            mBatteryController.addStateChangedCallback(this);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        mRequestedVisibility = visibility;
        updateVisibility();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Respect font size setting.
        /*setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.battery_level_text_size));*/
        setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(ComotID.Get("battery_level_text_size","dimen")));
    }

    @Override
    public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
        String percentage = NumberFormat.getPercentInstance().format((double) level / 100.0);
        setText(percentage);
        if (mBatteryCharging != charging) {
            mBatteryCharging = charging;
            updateVisibility();
        }
    }

    @Override
    public void onPowerSaveChanged() {
        // Not used
    }

    @Override
    public void onBatteryStyleChanged(int style, int percentMode) {
        mStyle = style;
        mPercentMode = percentMode;
        updateVisibility();
    }

    // MOD
    @Override
    public void onBatteryAneChanged(int batteryStyle, int batteryAlign) {
        mBatteryStyle = batteryStyle;
        mBatteryAlign = batteryAlign;
        updateVisibility();
    }

    @Override
    public void onBatteryBamzzzChanged(int batteryColor, int batteryScale, int percentColor, int percentSize) {
        mBatteryColor = batteryColor;
        mBatteryScale = batteryScale;
        mPercentColor = percentColor;
        mPercentSize = percentSize;
        updateVisibility();
    }
    // End

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mBatteryController != null) {
            mBatteryController.addStateChangedCallback(this);
        }

        mAttached = true;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttached = false;

        if (mBatteryController != null) {
            mBatteryController.removeStateChangedCallback(this);
        }
    }

    private void updateVisibility() {
        if(mBatteryStyle != 1 && mBatteryAlign == 2){
            boolean showNextPercent = mPercentMode == BatteryController.PERCENTAGE_MODE_OUTSIDE
                    || (mBatteryCharging && mPercentMode == BatteryController.PERCENTAGE_MODE_INSIDE);
            if (mStyle == BatteryController.STYLE_GONE) {
                showNextPercent = false;
            } else if (mStyle == BatteryController.STYLE_TEXT) {
                showNextPercent = true;
            }

            if (showNextPercent || mForceShow) {
                super.setVisibility(mRequestedVisibility);
            } else {
                super.setVisibility(GONE);
            }

            setTextColor(mPercentColor);
            setTextSize(mPercentSize);

        } else {
            super.setVisibility(GONE);
        }
        /*boolean showNextPercent = mPercentMode == BatteryController.PERCENTAGE_MODE_OUTSIDE
                || (mBatteryCharging && mPercentMode == BatteryController.PERCENTAGE_MODE_INSIDE);
        if (mStyle == BatteryController.STYLE_GONE) {
            showNextPercent = false;
        } else if (mStyle == BatteryController.STYLE_TEXT) {
            showNextPercent = true;
        }

        if (showNextPercent || mForceShow) {
            super.setVisibility(mRequestedVisibility);
        } else {
            super.setVisibility(GONE);
        }*/
    }
}