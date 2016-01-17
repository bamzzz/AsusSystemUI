package com.android.systemui.statusbar;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public class FlingAnimationUtils {
    private FlingAnimationUtils.AnimatorProperties mAnimatorProperties = new FlingAnimationUtils.AnimatorProperties(null);
    private Interpolator mFastOutLinearIn;
    private Interpolator mFastOutSlowIn;
    private float mHighVelocityPxPerSecond;
    private Interpolator mLinearOutSlowIn;
    private float mMaxLengthSeconds;
    private float mMinVelocityPxPerSecond;

    public FlingAnimationUtils(Context var1, float var2) {
        super();
        this.mMaxLengthSeconds = var2;
        this.mLinearOutSlowIn = new PathInterpolator(0.0F, 0.0F, 0.35F, 1.0F);
        this.mFastOutSlowIn = AnimationUtils.loadInterpolator(var1, 17563661);
        this.mFastOutLinearIn = AnimationUtils.loadInterpolator(var1, 17563663);
        this.mMinVelocityPxPerSecond = 250.0F * var1.getResources().getDisplayMetrics().density;
        this.mHighVelocityPxPerSecond = 3000.0F * var1.getResources().getDisplayMetrics().density;
    }

    private float calculateLinearOutFasterInY2(float var1) {
        var1 = Math.max(0.0F, Math.min(1.0F, (var1 - this.mMinVelocityPxPerSecond) / (this.mHighVelocityPxPerSecond - this.mMinVelocityPxPerSecond)));
        return (1.0F - var1) * 0.4F + 0.5F * var1;
    }

    private FlingAnimationUtils.AnimatorProperties getDismissingProperties(float var1, float var2, float var3, float var4) {
        var4 = (float)((double)this.mMaxLengthSeconds * Math.pow((double)(Math.abs(var2 - var1) / var4), 0.5D));
        var2 = Math.abs(var2 - var1);
        var3 = Math.abs(var3);
        var1 = this.calculateLinearOutFasterInY2(var3);
        float var5 = var1 / 0.5F;
        PathInterpolator var6 = new PathInterpolator(0.0F, 0.0F, 0.5F, var1);
        var1 = var5 * var2 / var3;
        if(var1 <= var4) {
            this.mAnimatorProperties.interpolator = var6;
        } else if(var3 >= this.mMinVelocityPxPerSecond) {
            var1 = var4;
            FlingAnimationUtils.InterpolatorInterpolator var7 = new FlingAnimationUtils.InterpolatorInterpolator(new FlingAnimationUtils.VelocityInterpolator(var4, var3, var2, null), var6, this.mLinearOutSlowIn);
            this.mAnimatorProperties.interpolator = var7;
        } else {
            var1 = var4;
            this.mAnimatorProperties.interpolator = this.mFastOutLinearIn;
        }

        this.mAnimatorProperties.duration = (long)(1000.0F * var1);
        return this.mAnimatorProperties;
    }

    private FlingAnimationUtils.AnimatorProperties getProperties(float var1, float var2, float var3, float var4) {
        var4 = (float)((double)this.mMaxLengthSeconds * Math.sqrt((double)(Math.abs(var2 - var1) / var4)));
        var2 = Math.abs(var2 - var1);
        var3 = Math.abs(var3);
        var1 = 2.857143F * var2 / var3;
        if(var1 <= var4) {
            this.mAnimatorProperties.interpolator = this.mLinearOutSlowIn;
        } else if(var3 >= this.mMinVelocityPxPerSecond) {
            var1 = var4;
            FlingAnimationUtils.InterpolatorInterpolator var5 = new FlingAnimationUtils.InterpolatorInterpolator(new FlingAnimationUtils.VelocityInterpolator(var4, var3, var2, null), this.mLinearOutSlowIn, this.mLinearOutSlowIn);
            this.mAnimatorProperties.interpolator = var5;
        } else {
            var1 = var4;
            this.mAnimatorProperties.interpolator = this.mFastOutSlowIn;
        }

        this.mAnimatorProperties.duration = (long)(1000.0F * var1);
        return this.mAnimatorProperties;
    }

    public void apply(Animator var1, float var2, float var3, float var4) {
        this.apply(var1, var2, var3, var4, Math.abs(var3 - var2));
    }

    public void apply(Animator var1, float var2, float var3, float var4, float var5) {
        FlingAnimationUtils.AnimatorProperties var6 = this.getProperties(var2, var3, var4, var5);
        var1.setDuration(var6.duration);
        var1.setInterpolator(var6.interpolator);
    }

    public void applyDismissing(Animator var1, float var2, float var3, float var4, float var5) {
        FlingAnimationUtils.AnimatorProperties var6 = this.getDismissingProperties(var2, var3, var4, var5);
        var1.setDuration(var6.duration);
        var1.setInterpolator(var6.interpolator);
    }

    public float getMinVelocityPxPerSecond() {
        return this.mMinVelocityPxPerSecond;
    }

    private static class AnimatorProperties {
        long duration;
        Interpolator interpolator;

        private AnimatorProperties() {
            super();
        }

        // $FF: synthetic method
        AnimatorProperties(Object var1) {
            this();
        }
    }

    private static final class InterpolatorInterpolator implements Interpolator {
        private Interpolator mCrossfader;
        private Interpolator mInterpolator1;
        private Interpolator mInterpolator2;

        InterpolatorInterpolator(Interpolator var1, Interpolator var2, Interpolator var3) {
            super();
            this.mInterpolator1 = var1;
            this.mInterpolator2 = var2;
            this.mCrossfader = var3;
        }

        public float getInterpolation(float var1) {
            float var2 = this.mCrossfader.getInterpolation(var1);
            return (1.0F - var2) * this.mInterpolator1.getInterpolation(var1) + this.mInterpolator2.getInterpolation(var1) * var2;
        }
    }

    private static final class VelocityInterpolator implements Interpolator {
        private float mDiff;
        private float mDurationSeconds;
        private float mVelocity;

        private VelocityInterpolator(float var1, float var2, float var3) {
            super();
            this.mDurationSeconds = var1;
            this.mVelocity = var2;
            this.mDiff = var3;
        }

        // $FF: synthetic method
        VelocityInterpolator(float var1, float var2, float var3, Object var4) {
            this(var1, var2, var3);
        }

        public float getInterpolation(float var1) {
            float var2 = this.mDurationSeconds;
            return this.mVelocity * var1 * var2 / this.mDiff;
        }
    }
}
