package com.asus.systemui.qs;

import android.content.Context;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class DoubleTapHelper {
    private DoubleTapHelper.Callback mCallback;
    private Context mContext;
    private GestureDetector mGestureDetector;

    public DoubleTapHelper(Context var1, DoubleTapHelper.Callback var2) {
        super();
        this.mContext = var1;
        this.mCallback = var2;
        this.mGestureDetector = new GestureDetector(this.mContext, new DoubleTapHelper.GestureListener(null));
    }

    public boolean onTouchEvent(MotionEvent var1) {
        return this.mGestureDetector.onTouchEvent(var1);
    }

    public interface Callback {
        void onDoubleTap();
    }

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
            super();
        }

        // $FF: synthetic method
        GestureListener(Object var2) {
            this();
        }

        public boolean onDoubleTap(MotionEvent var1) {
            Log.d("Systemui.doubletap", "onDoubleTap!");
            TelephonyManager var2 = (TelephonyManager)DoubleTapHelper.this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Log.d("Systemui.doubletap", "tm.getCallState(): " + var2.getCallState());
            if(var2 != null && var2.getCallState() == 0) {
                PowerManager var3 = (PowerManager)DoubleTapHelper.this.mContext.getSystemService(Context.POWER_SERVICE);
                DoubleTapHelper.this.mCallback.onDoubleTap();
                //var3.goToSleep(SystemClock.uptimeMillis());
            } else {
                Log.d("Systemui.doubletap", "On phone call!");
            }

            return true;
        }

        public boolean onDown(MotionEvent var1) {
            Log.d("Systemui.doubletap", "onDown!");
            return true;
        }

        public boolean onSingleTapUp(MotionEvent var1) {
            Log.d("Systemui.doubletap", "onSingleTapUp!");
            return true;
        }
    }
}
