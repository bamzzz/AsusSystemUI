package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.android.systemui.statusbar.FlingAnimationUtils;
import com.bamzzz.ComotID;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class PanelView extends FrameLayout {
    public static final String TAG = PanelView.class.getSimpleName();
    PanelBar mBar;
    private Interpolator mBounceInterpolator;
    private boolean mClosing;
    private boolean mCollapseAfterPeek;
    private int mEdgeTapAreaWidth;
    private float mExpandedFraction = 0.0F;
    protected float mExpandedHeight = 0.0F;
    private boolean mExpanding;
    private Interpolator mFastOutSlowInInterpolator;
    private FlingAnimationUtils mFlingAnimationUtils;
    private final Runnable mFlingCollapseRunnable = new Runnable() {
        public void run() {
            PanelView.this.fling(0.0F, false);
        }
    };
    private boolean mGestureWaitForTouchSlop;
    private boolean mHasLayoutedSinceDown;
    private ValueAnimator mHeightAnimator;
    protected boolean mHintAnimationRunning;
    private float mHintDistance;
    private float mInitialOffsetOnTouch;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mInstantExpanding;
    private boolean mJustPeeked;
    protected KeyguardBottomAreaView mKeyguardBottomArea;
    private Interpolator mLinearOutSlowInInterpolator;
    private float mOriginalIndicationY;
    private boolean mOverExpandedBeforeFling;
    private boolean mPanelClosedOnDown;
    private ObjectAnimator mPeekAnimator;
    private float mPeekHeight;
    private boolean mPeekPending;
    private Runnable mPeekRunnable = new Runnable() {
        public void run() {
            PanelView.this.mPeekPending = false;
            PanelView.this.runPeekAnimation();
        }
    };
    private boolean mPeekTouching;
    private final Runnable mPostCollapseRunnable = new Runnable() {
        public void run() {
            PanelView.this.collapse(false);
        }
    };
    protected PhoneStatusBar mStatusBar;
    private boolean mTouchAboveFalsingThreshold;
    private boolean mTouchDisabled;
    protected int mTouchSlop;
    private boolean mTouchSlopExceeded;
    protected boolean mTracking;
    private int mTrackingPointer;
    private int mUnlockFalsingThreshold;
    private boolean mUpdateFlingOnLayout;
    private float mUpdateFlingVelocity;
    private VelocityTrackerInterface mVelocityTracker;
    private String mViewName;

    public PanelView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.mFlingAnimationUtils = new FlingAnimationUtils(var1, 0.6F);
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(var1, ComotID.Get("", "id"));
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(var1, ComotID.Get("","id"));
        this.mBounceInterpolator = new BounceInterpolator();
    }

    private void abortAnimations() {
        this.cancelPeek();
        if(this.mHeightAnimator != null) {
            this.mHeightAnimator.cancel();
        }

        this.removeCallbacks(this.mPostCollapseRunnable);
        this.removeCallbacks(this.mFlingCollapseRunnable);
    }

    private ValueAnimator createHeightAnimator(float var1) {
        ValueAnimator var2 = ValueAnimator.ofFloat(new float[]{this.mExpandedHeight, var1});
        var2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator var1) {
                PanelView.this.setExpandedHeightInternal(((Float)var1.getAnimatedValue()).floatValue());
            }
        });
        return var2;
    }

    private int getFalsingThreshold() {
        float var1;
        //if(this.mStatusBar.isScreenOnComingFromTouch()) {
            var1 = 1.5F;
        //} else {
        //    var1 = 1.0F;
        //}

        return (int)((float)this.mUnlockFalsingThreshold * var1);
    }

    private void initVelocityTracker() {
        if(this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }

        //this.mVelocityTracker = VelocityTrackerFactory.obtain(this.getContext());
    }

    private boolean isBelowFalsingThreshold() {
        boolean var1;
        //if(!this.mTouchAboveFalsingThreshold && this.mStatusBar.isFalsingThresholdNeeded()) {
            var1 = true;
        //} else {
        //    var1 = false;
        //}

        return var1;
    }

    private void notifyBarPanelExpansionChanged() {
        PanelBar var3 = this.mBar;
        float var1 = this.mExpandedFraction;
        boolean var2;
        if(this.mExpandedFraction <= 0.0F && !this.mPeekPending && this.mPeekAnimator == null) {
            var2 = false;
        } else {
            var2 = true;
        }

        var3.panelExpansionChanged(this, var1, var2);
    }

    private void notifyExpandingFinished() {
        if(this.mExpanding) {
            this.mExpanding = false;
            this.onExpandingFinished();
        }

    }

    private void notifyExpandingStarted() {
        if(!this.mExpanding) {
            this.mExpanding = true;
            this.onExpandingStarted();
        }

    }

    private boolean onEmptySpaceClick(float var1) {
        boolean var2 = true;
        if(!this.mHintAnimationRunning) {
            //if(var1 < (float)this.mEdgeTapAreaWidth && this.mStatusBar.getBarState() == 1) {
            //    this.onEdgeClicked(false);
            //} else if(var1 > (float)(this.getWidth() - this.mEdgeTapAreaWidth) && this.mStatusBar.getBarState() == 1) {
            //    this.onEdgeClicked(true);
            //} else {
                var2 = this.onMiddleClicked();
            //}
        }

        return var2;
    }

    private boolean onMiddleClicked() {
        boolean var2 = true;
        boolean var1;
        switch(this.mStatusBar.getBarState()) {
            case 0:
                this.post(this.mPostCollapseRunnable);
                var1 = false;
                break;
            case 1:
                var1 = var2;
                if(!this.isDozing()) {
                    this.startUnlockHintAnimation();
                    var1 = var2;
                }
                break;
            case 2:
                //this.mStatusBar.goToKeyguard();
                var1 = var2;
                break;
            default:
                var1 = var2;
        }

        return var1;
    }

    private void runPeekAnimation() {
        this.mPeekHeight = this.getPeekHeight();
        if(this.mHeightAnimator == null) {
            this.mPeekAnimator = ObjectAnimator.ofFloat(this, "expandedHeight", new float[]{this.mPeekHeight}).setDuration(250L);
            this.mPeekAnimator.setInterpolator(this.mLinearOutSlowInInterpolator);
            this.mPeekAnimator.addListener(new AnimatorListenerAdapter() {
                private boolean mCancelled;

                public void onAnimationCancel(Animator var1) {
                    this.mCancelled = true;
                }

                public void onAnimationEnd(Animator var1) {
                    PanelView.this.mPeekAnimator = null;
                    if(PanelView.this.mCollapseAfterPeek && !this.mCancelled) {
                        PanelView.this.postOnAnimation(new Runnable() {
                            public void run() {
                                PanelView.this.collapse(false);
                            }
                        });
                    }

                    PanelView.this.mCollapseAfterPeek = false;
                }
            });
            this.notifyExpandingStarted();
            this.mPeekAnimator.start();
            this.mJustPeeked = true;
        }

    }

    private void schedulePeek() {
        this.mPeekPending = true;
        long var1 = (long)ViewConfiguration.getTapTimeout();
        this.postOnAnimationDelayed(this.mPeekRunnable, var1);
        this.notifyBarPanelExpansionChanged();
    }

    private void startUnlockHintAnimationPhase1(final Runnable var1) {
        ValueAnimator var2 = this.createHeightAnimator(Math.max(0.0F, (float)this.getMaxPanelHeight() - this.mHintDistance));
        var2.setDuration(250L);
        var2.setInterpolator(this.mFastOutSlowInInterpolator);
        var2.addListener(new AnimatorListenerAdapter() {
            private boolean mCancelled;

            public void onAnimationCancel(Animator var1x) {
                this.mCancelled = true;
            }

            public void onAnimationEnd(Animator var1x) {
                if (this.mCancelled) {
                    PanelView.this.mHeightAnimator = null;
                    var1.run();
                } else {
                    PanelView.this.startUnlockHintAnimationPhase2(var1);
                }

            }
        });
        var2.start();
        this.mHeightAnimator = var2;
        this.mOriginalIndicationY = 1.0F; //this.mKeyguardBottomArea.getIndicationView().getY();
        TranslateAnimation var3 = new TranslateAnimation(0.0F, 0.0F, 0.0F, -this.mHintDistance);
        var3.setDuration(250L);
        var3.setFillAfter(false);
        var3.setInterpolator(this.mFastOutSlowInInterpolator);
        var3.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation var1) {
                TranslateAnimation var2 = new TranslateAnimation(0.0F, 0.0F, -PanelView.this.mHintDistance, 0.0F);
                var2.setDuration(450L);
                var2.setFillAfter(false);
                var2.setInterpolator(PanelView.this.mBounceInterpolator);
                //PanelView.this.mKeyguardBottomArea.getIndicationView().startAnimation(var2);
            }

            public void onAnimationRepeat(Animation var1) {
            }

            public void onAnimationStart(Animation var1) {
            }
        });
        //this.mKeyguardBottomArea.getIndicationView().startAnimation(var3);
    }

    private void startUnlockHintAnimationPhase2(final Runnable var1) {
        ValueAnimator var2 = this.createHeightAnimator((float)this.getMaxPanelHeight());
        var2.setDuration(450L);
        var2.setInterpolator(this.mBounceInterpolator);
        var2.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator var1x) {
                PanelView.this.mHeightAnimator = null;
                var1.run();
            }
        });
        var2.start();
        this.mHeightAnimator = var2;
    }

    private void trackMovement(MotionEvent var1) {
        float var2 = var1.getRawX() - var1.getX();
        float var3 = var1.getRawY() - var1.getY();
        var1.offsetLocation(var2, var3);
        if(this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(var1);
        }

        var1.offsetLocation(-var2, -var3);
    }

    public void cancelPeek() {
        if(this.mPeekAnimator != null) {
            this.mPeekAnimator.cancel();
        }

        this.removeCallbacks(this.mPeekRunnable);
        this.mPeekPending = false;
        this.notifyBarPanelExpansionChanged();
    }

    public void collapse(boolean var1) {
        if(!this.mPeekPending && this.mPeekAnimator == null) {
            if(!this.isFullyCollapsed() && !this.mTracking && !this.mClosing) {
                if(this.mHeightAnimator != null) {
                    this.mHeightAnimator.cancel();
                }

                this.mClosing = true;
                this.notifyExpandingStarted();
                if(var1) {
                    this.postDelayed(this.mFlingCollapseRunnable, 120L);
                } else {
                    this.fling(0.0F, false);
                }
            }
        } else {
            this.mCollapseAfterPeek = true;
            if(this.mPeekPending) {
                this.removeCallbacks(this.mPeekRunnable);
                this.postDelayed(this.mPeekRunnable, (long)ViewConfiguration.getDoubleTapTimeout());
            }
        }

    }

    public void dump(FileDescriptor var1, PrintWriter var2, String[] var3) {
        String var10 = this.getClass().getSimpleName();
        float var4 = this.getExpandedHeight();
        int var5 = this.getMaxPanelHeight();
        String var13;
        if(this.mClosing) {
            var13 = "T";
        } else {
            var13 = "f";
        }

        String var14;
        if(this.mTracking) {
            var14 = "T";
        } else {
            var14 = "f";
        }

        String var6;
        if(this.mJustPeeked) {
            var6 = "T";
        } else {
            var6 = "f";
        }

        ObjectAnimator var11 = this.mPeekAnimator;
        String var7;
        if(this.mPeekAnimator != null && this.mPeekAnimator.isStarted()) {
            var7 = " (started)";
        } else {
            var7 = "";
        }

        ValueAnimator var12 = this.mHeightAnimator;
        String var8;
        if(this.mHeightAnimator != null && this.mHeightAnimator.isStarted()) {
            var8 = " (started)";
        } else {
            var8 = "";
        }

        String var9;
        if(this.mTouchDisabled) {
            var9 = "T";
        } else {
            var9 = "f";
        }

        var2.println(String.format("[PanelView(%s): expandedHeight=%f maxPanelHeight=%d closing=%s tracking=%s justPeeked=%s peekAnim=%s%s timeAnim=%s%s touchDisabled=%s]", new Object[]{var10, Float.valueOf(var4), Integer.valueOf(var5), var13, var14, var6, var11, var7, var12, var8, var9}));
    }

    public void expand() {
        if(this.isFullyCollapsed()) {
            this.mBar.startOpeningPanel(this);
            this.notifyExpandingStarted();
            this.fling(0.0F, true);
        }

    }

    protected void fling(float var1, boolean var2) {
        boolean var5 = true;
        this.cancelPeek();
        float var3;
        if(var2) {
            var3 = (float)this.getMaxPanelHeight();
        } else {
            var3 = 0.0F;
        }

        final boolean var4;
        if(var2 && this.fullyExpandedClearAllVisible() && this.mExpandedHeight < (float)(this.getMaxPanelHeight() - this.getClearAllHeight()) && !this.isClearAllVisible()) {
            var4 = true;
        } else {
            var4 = false;
        }

        if(var4) {
            var3 = (float)(this.getMaxPanelHeight() - this.getClearAllHeight());
        }

        if(var3 == this.mExpandedHeight || this.getOverExpansionAmount() > 0.0F && var2) {
            this.notifyExpandingFinished();
        } else {
            if(this.getOverExpansionAmount() <= 0.0F) {
                var5 = false;
            }

            this.mOverExpandedBeforeFling = var5;
            ValueAnimator var6 = this.createHeightAnimator(var3);
            if(var2) {
                var2 = this.isBelowFalsingThreshold();
                if(var2) {
                    var1 = 0.0F;
                }

                this.mFlingAnimationUtils.apply(var6, this.mExpandedHeight, var3, var1, (float)this.getHeight());
                if(var2) {
                    var6.setDuration(350L);
                }
            } else {
                this.mFlingAnimationUtils.applyDismissing(var6, this.mExpandedHeight, var3, var1, (float)this.getHeight());
                if(var1 == 0.0F) {
                    var6.setDuration((long)((float)var6.getDuration() * this.getCannedFlingDurationFactor()));
                }
            }

            var6.addListener(new AnimatorListenerAdapter() {
                private boolean mCancelled;

                public void onAnimationCancel(Animator var1) {
                    this.mCancelled = true;
                }

                public void onAnimationEnd(Animator var1) {
                    PanelView.this.mClosing = false;
                    if(var4 && !this.mCancelled) {
                        PanelView.this.setExpandedHeightInternal((float)PanelView.this.getMaxPanelHeight());
                    }

                    PanelView.this.mHeightAnimator = null;
                    if(!this.mCancelled) {
                        PanelView.this.notifyExpandingFinished();
                    }

                }
            });
            this.mHeightAnimator = var6;
            var6.start();
        }

    }

    protected boolean flingExpands(float var1, float var2) {
        boolean var3 = true;
        if(!this.isBelowFalsingThreshold()) {
            if(Math.abs(var2) < this.mFlingAnimationUtils.getMinVelocityPxPerSecond()) {
                if(this.getExpandedFraction() <= 0.5F) {
                    var3 = false;
                }
            } else if(var1 <= 0.0F) {
                var3 = false;
            }
        }

        return var3;
    }

    protected abstract boolean fullyExpandedClearAllVisible();

    protected abstract float getCannedFlingDurationFactor();

    protected abstract int getClearAllHeight();

    public float getExpandedFraction() {
        return this.mExpandedFraction;
    }

    public float getExpandedHeight() {
        return this.mExpandedHeight;
    }

    protected abstract int getMaxPanelHeight();

    protected abstract float getOverExpansionAmount();

    protected abstract float getOverExpansionPixels();

    protected abstract float getPeekHeight();

    protected abstract boolean hasConflictingGestures();

    public void instantCollapse() {
        this.abortAnimations();
        this.setExpandedFraction(0.0F);
        if(this.mExpanding) {
            this.notifyExpandingFinished();
        }

    }

    public void instantExpand() {
        this.mInstantExpanding = true;
        this.mUpdateFlingOnLayout = false;
        this.abortAnimations();
        this.cancelPeek();
        if(this.mTracking) {
            this.onTrackingStopped(true);
        }

        if(this.mExpanding) {
            this.notifyExpandingFinished();
        }

        this.setVisibility(View.VISIBLE);
        this.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if(PanelView.this.mStatusBar.getStatusBarWindow().getHeight() != PanelView.this.mStatusBar.getStatusBarHeight()) {
                    PanelView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    PanelView.this.setExpandedFraction(1.0F);
                    PanelView.this.mInstantExpanding = false;
                }

            }
        });
        this.requestLayout();
    }

    protected abstract boolean isClearAllVisible();

    public boolean isCollapsing() {
        return this.mClosing;
    }

    protected abstract boolean isDozing();

    public boolean isFullyCollapsed() {
        boolean var1;
        if(this.mExpandedHeight <= 0.0F) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public boolean isFullyExpanded() {
        boolean var1;
        if(this.mExpandedHeight >= (float)this.getMaxPanelHeight()) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    protected boolean isScrolledToBottom() {
        return true;
    }

    public boolean isTracking() {
        return this.mTracking;
    }

    protected abstract boolean isTrackingBlocked();

    protected void loadDimens() {
        Resources var1 = this.getContext().getResources();
        this.mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        this.mHintDistance = var1.getDimension(ComotID.Get("","dimen"));
        this.mEdgeTapAreaWidth = var1.getDimensionPixelSize(ComotID.Get("","dimen"));
        this.mUnlockFalsingThreshold = var1.getDimensionPixelSize(ComotID.Get("","dimen"));
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mViewName = this.getResources().getResourceName(this.getId());
    }

    protected void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.loadDimens();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mBar = null;
    }

    protected abstract void onEdgeClicked(boolean var1);

    protected void onExpandingFinished() {
        this.mClosing = false;
        this.mBar.onExpandingFinished();
    }

    protected void onExpandingStarted() {
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.loadDimens();
    }

    protected abstract void onHeightUpdated(float var1);

    public boolean onInterceptTouchEvent(MotionEvent var1) {
        boolean var7 = true;
        boolean var8 = false;
        if(this.mInstantExpanding) {
            var7 = var8;
        } else {
            int var6 = var1.findPointerIndex(this.mTrackingPointer);
            int var5 = var6;
            if(var6 < 0) {
                var5 = 0;
                this.mTrackingPointer = var1.getPointerId(0);
            }

            float var3 = var1.getX(var5);
            float var4 = var1.getY(var5);
            boolean var9 = this.isScrolledToBottom();
            switch(var1.getActionMasked()) {
                case 0:
                    //this.mStatusBar.userActivity();
                    if((this.mHeightAnimator == null || this.mHintAnimationRunning) && !this.mPeekPending && this.mPeekAnimator == null) {
                        this.mInitialTouchY = var4;
                        this.mInitialTouchX = var3;
                        this.mTouchSlopExceeded = false;
                        this.mJustPeeked = false;
                        if(this.mExpandedHeight != 0.0F) {
                            var7 = false;
                        }

                        this.mPanelClosedOnDown = var7;
                        this.mHasLayoutedSinceDown = false;
                        this.mUpdateFlingOnLayout = false;
                        this.mTouchAboveFalsingThreshold = false;
                        this.initVelocityTracker();
                        this.trackMovement(var1);
                        var7 = var8;
                    } else {
                        if(this.mHeightAnimator != null) {
                            this.mHeightAnimator.cancel();
                        }

                        this.cancelPeek();
                        this.mTouchSlopExceeded = true;
                        var7 = true;
                    }
                    break;
                case 2:
                    float var2 = var4 - this.mInitialTouchY;
                    this.trackMovement(var1);
                    var7 = var8;
                    if(var9) {
                        var7 = var8;
                        if(var2 < (float)(-this.mTouchSlop)) {
                            var7 = var8;
                            if(var2 < -Math.abs(var3 - this.mInitialTouchX)) {
                                if(this.mHeightAnimator != null) {
                                    this.mHeightAnimator.cancel();
                                }

                                this.mInitialOffsetOnTouch = this.mExpandedHeight;
                                this.mInitialTouchY = var4;
                                this.mInitialTouchX = var3;
                                this.mTracking = true;
                                this.mTouchSlopExceeded = true;
                                this.onTrackingStarted();
                                var7 = true;
                            }
                        }
                    }
                    break;
                case 6:
                    var5 = var1.getPointerId(var1.getActionIndex());
                    var7 = var8;
                    if(this.mTrackingPointer == var5) {
                        byte var10;
                        if(var1.getPointerId(0) != var5) {
                            var10 = 0;
                        } else {
                            var10 = 1;
                        }

                        this.mTrackingPointer = var1.getPointerId(var10);
                        this.mInitialTouchX = var1.getX(var10);
                        this.mInitialTouchY = var1.getY(var10);
                        var7 = var8;
                    }
                    break;
                default:
                    var7 = var8;
            }
        }

        return var7;
    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        this.requestPanelHeightUpdate();
        this.mHasLayoutedSinceDown = true;
        if(this.mUpdateFlingOnLayout) {
            this.abortAnimations();
            this.fling(this.mUpdateFlingVelocity, true);
            this.mUpdateFlingOnLayout = false;
        }

    }

    public boolean onTouchEvent(MotionEvent var1) {
        boolean var8;
        if(!this.mInstantExpanding && !this.mTouchDisabled) {
            int var7 = var1.findPointerIndex(this.mTrackingPointer);
            int var6 = var7;
            if(var7 < 0) {
                var6 = 0;
                this.mTrackingPointer = var1.getPointerId(0);
            }

            float var4 = var1.getY(var6);
            float var5 = var1.getX(var6);
            if(var1.getActionMasked() == 0) {
                if(this.mExpandedHeight == 0.0F) {
                    var8 = true;
                } else {
                    var8 = false;
                }

                this.mGestureWaitForTouchSlop = var8;
            }

            boolean var9;
            if(!this.hasConflictingGestures() && !this.mGestureWaitForTouchSlop) {
                var9 = false;
            } else {
                var9 = true;
            }

            float var2;
            float var3;
            switch(var1.getActionMasked()) {
                case 0:
                    this.mInitialTouchY = var4;
                    this.mInitialTouchX = var5;
                    this.mInitialOffsetOnTouch = this.mExpandedHeight;
                    this.mTouchSlopExceeded = false;
                    this.mJustPeeked = false;
                    if(this.mExpandedHeight == 0.0F) {
                        var8 = true;
                    } else {
                        var8 = false;
                    }

                    this.mPanelClosedOnDown = var8;
                    this.mHasLayoutedSinceDown = false;
                    this.mUpdateFlingOnLayout = false;
                    this.mPeekTouching = this.mPanelClosedOnDown;
                    this.mTouchAboveFalsingThreshold = false;
                    if(this.mVelocityTracker == null) {
                        this.initVelocityTracker();
                    }

                    this.trackMovement(var1);
                    if(!var9 || this.mHeightAnimator != null && !this.mHintAnimationRunning || this.mPeekPending || this.mPeekAnimator != null) {
                        if(this.mHeightAnimator != null) {
                            this.mHeightAnimator.cancel();
                        }

                        this.cancelPeek();
                        if((this.mHeightAnimator == null || this.mHintAnimationRunning) && !this.mPeekPending && this.mPeekAnimator == null) {
                            var8 = false;
                        } else {
                            var8 = true;
                        }

                        this.mTouchSlopExceeded = var8;
                        this.onTrackingStarted();
                    }

                    if(this.mExpandedHeight == 0.0F) {
                        this.schedulePeek();
                    }
                    break;
                case 1:
                case 3:
                    this.mTrackingPointer = -1;
                    this.trackMovement(var1);
                    if((!this.mTracking || !this.mTouchSlopExceeded) && Math.abs(var5 - this.mInitialTouchX) <= (float)this.mTouchSlop && Math.abs(var4 - this.mInitialTouchY) <= (float)this.mTouchSlop && var1.getActionMasked() != 3) {
                        this.onTrackingStopped(this.onEmptySpaceClick(this.mInitialTouchX));
                    } else {
                        var3 = 0.0F;
                        var2 = 0.0F;
                        if(this.mVelocityTracker != null) {
                            this.mVelocityTracker.computeCurrentVelocity(1000);
                            var3 = this.mVelocityTracker.getYVelocity();
                            var2 = (float)Math.hypot((double)this.mVelocityTracker.getXVelocity(), (double)this.mVelocityTracker.getYVelocity());
                        }

                        var8 = this.flingExpands(var3, var2);
                        this.onTrackingStopped(var8);
                        //DozeLog.traceFling(var8, this.mTouchAboveFalsingThreshold, this.mStatusBar.isFalsingThresholdNeeded(), this.mStatusBar.isScreenOnComingFromTouch());
                        this.fling(var3, var8);
                        if(var8 && this.mPanelClosedOnDown && !this.mHasLayoutedSinceDown) {
                            var8 = true;
                        } else {
                            var8 = false;
                        }

                        this.mUpdateFlingOnLayout = var8;
                        if(this.mUpdateFlingOnLayout) {
                            this.mUpdateFlingVelocity = var3;
                        }
                    }

                    if(this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }

                    this.mPeekTouching = false;
                    break;
                case 2:
                    var3 = var4 - this.mInitialTouchY;
                    var2 = var3;
                    if(Math.abs(var3) > (float)this.mTouchSlop) {
                        label132: {
                            if(Math.abs(var3) <= Math.abs(var5 - this.mInitialTouchX)) {
                                var2 = var3;
                                if(this.mInitialOffsetOnTouch != 0.0F) {
                                    break label132;
                                }
                            }

                            this.mTouchSlopExceeded = true;
                            var2 = var3;
                            if(var9) {
                                var2 = var3;
                                if(!this.mTracking) {
                                    var2 = var3;
                                    if(!this.mJustPeeked) {
                                        this.mInitialOffsetOnTouch = this.mExpandedHeight;
                                        this.mInitialTouchX = var5;
                                        this.mInitialTouchY = var4;
                                        var2 = 0.0F;
                                    }

                                    if(this.mHeightAnimator != null) {
                                        this.mHeightAnimator.cancel();
                                    }

                                    this.removeCallbacks(this.mPeekRunnable);
                                    this.mPeekPending = false;
                                    this.onTrackingStarted();
                                }
                            }
                        }
                    }

                    var3 = Math.max(0.0F, this.mInitialOffsetOnTouch + var2);
                    if(var3 > this.mPeekHeight) {
                        if(this.mPeekAnimator != null) {
                            this.mPeekAnimator.cancel();
                        }

                        this.mJustPeeked = false;
                    }

                    if(-var2 >= (float)this.getFalsingThreshold()) {
                        this.mTouchAboveFalsingThreshold = true;
                    }

                    if(!this.mJustPeeked && (!var9 || this.mTracking) && !this.isTrackingBlocked()) {
                        this.setExpandedHeightInternal(var3);
                    }

                    this.trackMovement(var1);
                case 4:
                case 5:
                default:
                    break;
                case 6:
                    var7 = var1.getPointerId(var1.getActionIndex());
                    if(this.mTrackingPointer == var7) {
                        byte var10;
                        if(var1.getPointerId(0) != var7) {
                            var10 = 0;
                        } else {
                            var10 = 1;
                        }

                        var3 = var1.getY(var10);
                        var2 = var1.getX(var10);
                        this.mTrackingPointer = var1.getPointerId(var10);
                        this.mInitialOffsetOnTouch = this.mExpandedHeight;
                        this.mInitialTouchY = var3;
                        this.mInitialTouchX = var2;
                    }
            }

            if(var9 && !this.mTracking) {
                var8 = false;
            } else {
                var8 = true;
            }
        } else {
            var8 = false;
        }

        return var8;
    }

    protected void onTrackingStarted() {
        this.mClosing = false;
        this.mTracking = true;
        this.mCollapseAfterPeek = false;
        this.mBar.onTrackingStarted(this);
        this.notifyExpandingStarted();
    }

    protected void onTrackingStopped(boolean var1) {
        this.mTracking = false;
        this.mBar.onTrackingStopped(this, var1);
    }

    protected void requestPanelHeightUpdate() {
        float var1 = (float)this.getMaxPanelHeight();
        if((!this.mTracking || this.isTrackingBlocked()) && this.mHeightAnimator == null && this.mExpandedHeight > 0.0F && var1 != this.mExpandedHeight && !this.mPeekPending && this.mPeekAnimator == null && !this.mPeekTouching) {
            this.setExpandedHeight(var1);
        }

    }

    public abstract void resetViews();

    public void setBar(PanelBar var1) {
        this.mBar = var1;
    }

    public void setExpandedFraction(float var1) {
        this.setExpandedHeight((float)this.getMaxPanelHeight() * var1);
    }

    public void setExpandedHeight(float var1) {
        this.setExpandedHeightInternal(this.getOverExpansionPixels() + var1);
    }

    public void setExpandedHeightInternal(float var1) {
        float var2 = 0.0F;
        float var4 = (float)this.getMaxPanelHeight() - this.getOverExpansionAmount();
        if(this.mHeightAnimator == null) {
            float var3 = Math.max(0.0F, var1 - var4);
            if(this.getOverExpansionPixels() != var3 && this.mTracking) {
                this.setOverExpansion(var3, true);
            }

            this.mExpandedHeight = Math.min(var1, var4) + this.getOverExpansionAmount();
        } else {
            this.mExpandedHeight = var1;
            if(this.mOverExpandedBeforeFling) {
                this.setOverExpansion(Math.max(0.0F, var1 - var4), false);
            }
        }

        this.mExpandedHeight = Math.max(0.0F, this.mExpandedHeight);
        this.onHeightUpdated(this.mExpandedHeight);
        if(var4 == 0.0F) {
            var1 = var2;
        } else {
            var1 = this.mExpandedHeight / var4;
        }

        this.mExpandedFraction = Math.min(1.0F, var1);
        this.notifyBarPanelExpansionChanged();
    }

    protected abstract void setOverExpansion(float var1, boolean var2);

    public void setTouchDisabled(boolean var1) {
        this.mTouchDisabled = var1;
    }

    protected void startUnlockHintAnimation() {
        if(this.mHeightAnimator == null && !this.mTracking) {
            this.cancelPeek();
            this.notifyExpandingStarted();
            this.startUnlockHintAnimationPhase1(new Runnable() {
                public void run() {
                    PanelView.this.notifyExpandingFinished();
                    PanelView.this.mStatusBar.onHintFinished();
                    PanelView.this.mHintAnimationRunning = false;
                }
            });
            this.mHintAnimationRunning = true;
        }

    }
}
