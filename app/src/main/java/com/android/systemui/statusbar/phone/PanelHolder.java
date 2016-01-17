package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class PanelHolder extends FrameLayout {
    private PanelBar mBar;
    private int mSelectedPanelIndex = -1;

    public PanelHolder(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.setChildrenDrawingOrderEnabled(true);
    }

    protected int getChildDrawingOrder(int var1, int var2) {
        if(this.mSelectedPanelIndex == -1) {
            var1 = var2;
        } else if(var2 == var1 - 1) {
            var1 = this.mSelectedPanelIndex;
        } else {
            var1 = var2;
            if(var2 >= this.mSelectedPanelIndex) {
                var1 = var2 + 1;
            }
        }

        return var1;
    }

    public int getPanelIndex(PanelView var1) {
        int var3 = this.getChildCount();
        int var2 = 0;

        while(true) {
            if(var2 >= var3) {
                var2 = -1;
                break;
            }

            if(var1 == (PanelView)this.getChildAt(var2)) {
                break;
            }

            ++var2;
        }

        return var2;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBar = null;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setChildrenDrawingOrderEnabled(true);
    }

    public boolean onTouchEvent(MotionEvent var1) {
        if(var1.getActionMasked() != 2) {
            EventLog.writeEvent('賈', new Object[]{Integer.valueOf(var1.getActionMasked()), Integer.valueOf((int)var1.getX()), Integer.valueOf((int)var1.getY())});
        }

        return false;
    }

    public void setBar(PanelBar var1) {
        this.mBar = var1;
    }

    public void setSelectedPanel(PanelView var1) {
        this.mSelectedPanelIndex = this.getPanelIndex(var1);
    }
}
