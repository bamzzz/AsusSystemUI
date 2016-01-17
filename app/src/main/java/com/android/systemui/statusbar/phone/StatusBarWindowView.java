package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.phone.PhoneStatusBar;

public class StatusBarWindowView extends FrameLayout {
    public static final boolean DEBUG;
    private View mBrightnessMirror;

    private Paint mPaint = new Paint();
    PhoneStatusBar mService;

    static {
        DEBUG = true;
    }

    public StatusBarWindowView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.setMotionEventSplittingEnabled(false);

    }



    public boolean dispatchKeyEvent(KeyEvent var1) {
        boolean var4 = true;
        boolean var2;
        if(var1.getAction() == 0) {
            var2 = true;
        } else {
            var2 = false;
        }
        boolean var3;
        var3 = var4;
        return var3;
    }

    public boolean dispatchTouchEvent(MotionEvent var1) {
        boolean var2;
        if(this.mBrightnessMirror != null && this.mBrightnessMirror.getVisibility() == 0 && var1.getActionMasked() == 5) {
            var2 = false;
        } else {
            var2 = super.dispatchTouchEvent(var1);
        }

        return var2;
    }

    protected boolean fitSystemWindows(Rect var1) {
        boolean var4 = true;
        boolean var3 = true;
        boolean var2;
        if(this.getFitsSystemWindows()) {
            var2 = var3;
            if(var1.left == this.getPaddingLeft()) {
                var2 = var3;
                if(var1.top == this.getPaddingTop()) {
                    var2 = var3;
                    if(var1.right == this.getPaddingRight()) {
                        if(var1.bottom != this.getPaddingBottom()) {
                            var2 = var3;
                        } else {
                            var2 = false;
                        }
                    }
                }
            }

            if(var2) {
                this.setPadding(var1.left, var1.top, var1.right, 0);
            }

            var1.left = 0;
            var1.top = 0;
            var1.right = 0;
        } else {
            var2 = var4;
            if(this.getPaddingLeft() == 0) {
                var2 = var4;
                if(this.getPaddingRight() == 0) {
                    var2 = var4;
                    if(this.getPaddingTop() == 0) {
                        if(this.getPaddingBottom() != 0) {
                            var2 = var4;
                        } else {
                            var2 = false;
                        }
                    }
                }
            }

            if(var2) {
                this.setPadding(0, 0, 0, 0);
            }
        }

        return false;
    }


    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

            boolean var1;
            if(!DEBUG) {
                var1 = true;
            } else {
                var1 = false;
            }

            this.setWillNotDraw(var1);


    }

    public void onDraw(Canvas var1) {
        super.onDraw(var1);

    }

    protected void onFinishInflate() {
        //this.mLockscreenWallpaperView = (LockscreenWallpaperView)this.findViewById(2131624110);
    }

    public boolean onInterceptTouchEvent(MotionEvent var1) {
        boolean var3 = false;
        Log.d("StatusBarWindowView", "isShortcutTouch:");
        return var3;
    }

    public boolean onTouchEvent(MotionEvent var1) {
        boolean var4 = false;
        boolean var3 = var4;


        var4 = var3;
        if(!var3) {
            var4 = super.onTouchEvent(var1);
        }


        return var4;
    }
}
