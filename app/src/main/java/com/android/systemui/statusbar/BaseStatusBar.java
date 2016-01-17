package com.android.systemui.statusbar;

import android.content.Context;
import android.view.WindowManager;

import com.android.systemui.pac.gestureanywhere.GestureAnywhereView;

/**
 * Created by bamzzz on 11 Jan 2016.
 */
public class BaseStatusBar {

    protected GestureAnywhereView mGestureAnywhereView;
    protected WindowManager mWindowManager;
    private Context mContext;

    /*@ChaosLab(name="GestureAnywhere", classification= ChaosLab.Classification.NEW_METHOD)
    protected void addGestureAnywhereView() {
        mGestureAnywhereView = (GestureAnywhereView)View.inflate(
                mContext, R.layout.gesture_anywhere_overlay, null);
        mWindowManager.addView(mGestureAnywhereView, getGestureAnywhereViewLayoutParams(Gravity.LEFT));
        mGestureAnywhereView.setStatusBar(this);
    }

    @ChaosLab(name="GestureAnywhere", classification= ChaosLab.Classification.NEW_METHOD)
    protected void removeGestureAnywhereView() {
        if (mGestureAnywhereView != null)
            mWindowManager.removeView(mGestureAnywhereView);
    }

    @ChaosLab(name="GestureAnywhere", classification= ChaosLab.Classification.NEW_METHOD)
    protected WindowManager.LayoutParams getGestureAnywhereViewLayoutParams(int gravity) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_STATUS_BAR_SUB_PANEL,
                0
                        | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                PixelFormat.TRANSLUCENT);
        lp.privateFlags |= WindowManager.LayoutParams.PRIVATE_FLAG_NO_MOVE_ANIMATION;
        lp.gravity = Gravity.TOP | gravity;
        lp.setTitle("GestureAnywhereView");

        return lp;
    }*/
    
}
