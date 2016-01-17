package com.android.systemui.statusbar.phone;

import android.view.MotionEvent;

public interface VelocityTrackerInterface {
    void addMovement(MotionEvent var1);

    void computeCurrentVelocity(int var1);

    float getXVelocity();

    float getYVelocity();

    void recycle();
}
