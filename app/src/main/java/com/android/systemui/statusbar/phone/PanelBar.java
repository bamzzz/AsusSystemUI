package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Iterator;

public class PanelBar extends FrameLayout {
    public static final String TAG = PanelBar.class.getSimpleName();
    float mPanelExpandedFractionSum;
    PanelHolder mPanelHolder;
    ArrayList<PanelView> mPanels = new ArrayList();
    private int mState = 0;
    PanelView mTouchingPanel;
    private boolean mTracking;
    private Context mContext;

    public PanelBar(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public void addPanel(PanelView var1) {
        this.mPanels.add(var1);
        var1.setBar(this);
    }

    public void collapseAllPanels(boolean var1) {
        boolean var2 = false;
        Iterator var4 = this.mPanels.iterator();

        while(true) {
            while(var4.hasNext()) {
                PanelView var3 = (PanelView)var4.next();
                if(var1 && !var3.isFullyCollapsed()) {
                    var3.collapse(true);
                    var2 = true;
                } else {
                    var3.resetViews();
                    var3.setExpandedFraction(0.0F);
                    var3.setVisibility(View.GONE);
                    var3.cancelPeek();
                }
            }

            if(!var2 && this.mState != 0) {
                this.go(0);
                this.onAllPanelsCollapsed();
            }

            return;
        }
    }

    public void go(int var1) {
        this.mState = var1;
    }

    public void onAllPanelsCollapsed() {
    }

    public void onExpandingFinished() {
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void onPanelFullyOpened(PanelView var1) {
    }

    public void onPanelPeeked() {
    }

    public boolean onTouchEvent(MotionEvent var1) {
        boolean var3 = true;
        boolean var2 = true;
        if(!this.panelsEnabled()) {
            if(var1.getAction() == 0) {
                Log.v(TAG, String.format("onTouch: all panels disabled, ignoring touch at (%d,%d)", new Object[]{Integer.valueOf((int)var1.getX()), Integer.valueOf((int)var1.getY())}));
            }

            var2 = false;
        } else {
            if(var1.getAction() == 0) {
                Intent var4 = new Intent("com.android.systemui.PANEL_TOUCH_DOWN");
                this.mContext.sendBroadcast(var4);
                PanelView var5 = this.selectPanelForTouch(var1);
                if(var5 == null) {
                    Log.v(TAG, String.format("onTouch: no panel for touch at (%d,%d)", new Object[]{Integer.valueOf((int)var1.getX()), Integer.valueOf((int)var1.getY())}));
                    this.mTouchingPanel = null;
                    return var2;
                }

                if(!var5.isEnabled()) {
                    Log.v(TAG, String.format("onTouch: panel (%s) is disabled, ignoring touch at (%d,%d)", new Object[]{var5, Integer.valueOf((int)var1.getX()), Integer.valueOf((int)var1.getY())}));
                    this.mTouchingPanel = null;
                    return var2;
                }

                this.startOpeningPanel(var5);
            }

            var2 = var3;
            if(this.mTouchingPanel != null) {
                var2 = this.mTouchingPanel.onTouchEvent(var1);
            }
        }

        return var2;
    }

    public void onTrackingStarted(PanelView var1) {
        this.mTracking = true;
    }

    public void onTrackingStopped(PanelView var1, boolean var2) {
        this.mTracking = false;
    }

    public void panelExpansionChanged(PanelView var1, float var2, boolean var3) {
        boolean var6 = true;
        PanelView var9 = null;
        this.mPanelExpandedFractionSum = 0.0F;
        Iterator var10 = this.mPanels.iterator();

        while(var10.hasNext()) {
            PanelView var11 = (PanelView)var10.next();
            boolean var7;
            if(var11.getExpandedHeight() > 0.0F) {
                var7 = true;
            } else {
                var7 = false;
            }

            //byte var8;
            if(var7) {
                var11.setVisibility(View.VISIBLE);
            } else {
                var11.setVisibility(View.GONE);
            }

            //var11.setVisibility(var8);
            if(var3) {
                if(this.mState == 0) {
                    this.go(1);
                    this.onPanelPeeked();
                }

                boolean var12 = false;
                float var4 = var11.getExpandedFraction();
                float var5 = this.mPanelExpandedFractionSum;
                if(var7) {
                    var2 = var4;
                } else {
                    var2 = 0.0F;
                }

                this.mPanelExpandedFractionSum = var2 + var5;
                var6 = var12;
                if(var1 == var11) {
                    var6 = var12;
                    if(var4 == 1.0F) {
                        var9 = var1;
                        var6 = var12;
                    }
                }
            }
        }

        this.mPanelExpandedFractionSum /= (float)this.mPanels.size();
        if(var9 != null && !this.mTracking) {
            this.go(2);
            this.onPanelFullyOpened(var9);
        } else if(var6 && !this.mTracking && this.mState != 0) {
            this.go(0);
            this.onAllPanelsCollapsed();
        }

    }

    public boolean panelsEnabled() {
        return true;
    }

    public PanelView selectPanelForTouch(MotionEvent var1) {
        int var2 = this.mPanels.size();
        return (PanelView)this.mPanels.get((int)((float)var2 * var1.getX() / (float)this.getMeasuredWidth()));
    }

    public void setPanelHolder(PanelHolder var1) {
        if(var1 == null) {
            Log.e(TAG, "setPanelHolder: null PanelHolder", new Throwable());
        } else {
            var1.setBar(this);
            this.mPanelHolder = var1;
            int var3 = var1.getChildCount();

            for(int var2 = 0; var2 < var3; ++var2) {
                View var4 = var1.getChildAt(var2);
                if(var4 != null && var4 instanceof PanelView) {
                    this.addPanel((PanelView)var4);
                }
            }
        }

    }

    public void startOpeningPanel(PanelView var1) {
        this.mTouchingPanel = var1;
        this.mPanelHolder.setSelectedPanel(this.mTouchingPanel);
        Iterator var3 = this.mPanels.iterator();

        while(var3.hasNext()) {
            PanelView var2 = (PanelView)var3.next();
            if(var2 != var1) {
                var2.collapse(false);
            }
        }

    }
}
