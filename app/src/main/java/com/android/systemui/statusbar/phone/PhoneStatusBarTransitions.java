package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.bamzzz.ComotID;

public final class PhoneStatusBarTransitions extends BarTransitions {
    //private View mBattery;
    private View mBattery;
    private View mClock;
    private Animator mCurrentAnimation;
    private final float mIconAlphaWhenOpaque;
    private View mLeftSide;
    private View mSignalCluster;
    private View mStatusIcons;
    private final PhoneStatusBarView mView;

    public PhoneStatusBarTransitions(PhoneStatusBarView view) {
        super(false, view, 2130838392);
        this.mView = view;
        this.mIconAlphaWhenOpaque = this.mView.getContext().getResources().getFraction(
                ComotID.Get("status_bar_icon_drawing_alpha", "dimen"), 1, 1);
    }

    private void applyMode(int mode, boolean animate) {
        if(this.mLeftSide != null) {
            float newAlpha = this.getNonBatteryClockAlphaFor(mode);
            float newAlphaBC = this.getBatteryClockAlpha(mode);
            if(this.mCurrentAnimation != null) {
                this.mCurrentAnimation.cancel();
            }

            if(animate) {
                AnimatorSet var5 = new AnimatorSet();
                var5.playTogether(new Animator[]{
                        this.animateTransitionTo(this.mLeftSide, newAlpha),
                        this.animateTransitionTo(this.mStatusIcons, newAlpha),
                        this.animateTransitionTo(this.mSignalCluster, newAlpha),
                        this.animateTransitionTo(this.mBattery, newAlphaBC),
                        this.animateTransitionTo(this.mClock, newAlphaBC)});
                if(mode == 3) {
                    var5.setDuration(750L);
                }

                var5.start();
                this.mCurrentAnimation = var5;
            } else {
                this.mLeftSide.setAlpha(newAlpha);
                this.mStatusIcons.setAlpha(newAlpha);
                this.mSignalCluster.setAlpha(newAlpha);
                this.mBattery.setAlpha(newAlphaBC);
                this.mClock.setAlpha(newAlphaBC);
            }
        }

    }

    private float getBatteryClockAlpha(int var1) {
        float var2;
        if(var1 == 3) {
            var2 = 0.5F;
        } else {
            var2 = this.getNonBatteryClockAlphaFor(var1);
        }

        return var2;
    }

    private float getNonBatteryClockAlphaFor(int var1) {
        float var2;
        if(var1 == 3) {
            var2 = 0.0F;
        } else if(!this.isOpaque(var1)) {
            var2 = 1.0F;
        } else {
            var2 = this.mIconAlphaWhenOpaque;
        }

        return var2;
    }

    private boolean isOpaque(int var1) {
        boolean var2 = true;
        if(var1 == 1 || var1 == 2 || var1 == 4) {
            var2 = false;
        }

        return var2;
    }

    public ObjectAnimator animateTransitionTo(View var1, float var2) {
        return ObjectAnimator.ofFloat(var1, "alpha", new float[]{var1.getAlpha(), var2});
    }

    public void init() {
        this.mLeftSide = this.mView.findViewById(ComotID.Get("", "id"));
        this.mStatusIcons = this.mView.findViewById(ComotID.Get("", "id"));
        this.mSignalCluster = this.mView.findViewById(ComotID.Get("sigal", "id"));
        //this.mBattery = this.mView.findViewById(ComotID.Get("", "id"));
        mBattery = this.mView.findViewById(ComotID.Get("battery", "id"));
        this.mClock = this.mView.findViewById(ComotID.Get("clock", "id"));
        this.applyModeBackground(-1, this.getMode(), false);
        this.applyMode(this.getMode(), false);
    }

    protected void onTransition(int var1, int var2, boolean var3) {
        super.onTransition(var1, var2, var3);
        this.applyMode(var2, var3);
    }
}
