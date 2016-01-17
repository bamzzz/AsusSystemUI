package com.android.systemui.statusbar.phone;

import android.animation.TimeInterpolator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.asus.systemui.util.SystemUiUtil;

public class BarTransitions {
    public static final boolean HIGH_END = false; //ActivityManager.isHighEndGfx();
    private final BarTransitions.BarBackgroundDrawable mBarBackground;
    private int mMode;
    private final String mTag;
    private final View mView;

    public BarTransitions(boolean var1, View var2, int var3) {
        super();
        this.mTag = "BarTransitions." + var2.getClass().getSimpleName();
        this.mView = var2;
        this.mBarBackground = new BarTransitions.BarBackgroundDrawable(var1, this.mView, var3);
        if(HIGH_END) {
            this.mView.setBackground(this.mBarBackground);
        }

    }

    public static String modeToString(int var0) {
        String var1;
        if(var0 == 0) {
            var1 = "MODE_OPAQUE";
        } else if(var0 == 1) {
            var1 = "MODE_SEMI_TRANSPARENT";
        } else if(var0 == 2) {
            var1 = "MODE_TRANSLUCENT";
        } else if(var0 == 3) {
            var1 = "MODE_LIGHTS_OUT";
        } else if(var0 == 4) {
            var1 = "MODE_TRANSPARENT";
        } else if(var0 == 5) {
            var1 = "MODE_WARNING";
        } else if(var0 == 6) {
            var1 = "MODE_SNAPVIEW_OBVIOUS";
        } else if(var0 == 7) {
            var1 = "MODE_SNAPVIEW_SUBTLE";
        } else if(var0 == 8) {
            var1 = "MODE_RECORD_WARNING";
        } else {
            var1 = "UNKNOWN_MODE (value:" + var0 + ")";
        }

        return var1;
    }

    protected void applyModeBackground(int var1, int var2, boolean var3) {
        this.mBarBackground.applyModeBackground(var1, var2, var3);
    }

    public void finishAnimations() {
        this.mBarBackground.finishAnimation();
    }

    public int getMode() {
        return this.mMode;
    }

    protected void onTransition(int var1, int var2, boolean var3) {
        if(HIGH_END) {
            this.applyModeBackground(var1, var2, var3);
        }

    }

    public void setSnapViewColor(int var1) {
        this.mBarBackground.setSnapViewColor(var1);
    }

    public void transitionTo(int var1, boolean var2) {
        int var3 = var1;
        if(!HIGH_END) {
            label18: {
                if(var1 != 1 && var1 != 2) {
                    var3 = var1;
                    if(var1 != 4) {
                        break label18;
                    }
                }

                var3 = 0;
            }
        }

        if(this.mMode != var3) {
            var1 = this.mMode;
            this.mMode = var3;
            this.onTransition(var1, this.mMode, var2);
        }

    }

    public void updateBarBackground() {
        this.mBarBackground.invalidateSelf();
    }

    protected static class BarBackgroundDrawable extends Drawable {
        private boolean mAnimating;
        private int mColor;
        private int mColorStart;
        private long mEndTime;
        private final Drawable mGradient;
        private int mGradientAlpha;
        private int mGradientAlphaStart;
        private final TimeInterpolator mInterpolator;
        private boolean mIsNav;
        private int mMode = -1;
        private final int mOpaque;
        private int mPrivate;
        private final int mRecordWarning;
        private final int mSemiTransparent;
        private long mStartTime;
        private final int mTranslucent;
        private final int mTransparent;
        private View mView;
        private final int mWarning;

        public BarBackgroundDrawable(boolean var1, View var2, int var3) {
            super();
            Resources var4 = var2.getContext().getResources();
            this.mOpaque = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mSemiTransparent = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mTranslucent = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mTransparent = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mWarning = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mPrivate = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mRecordWarning = var4.getColor(SystemUiUtil.getIdentifier(var2.getContext(), "color", "battery_saver_mode_color"));
            this.mGradient = var4.getDrawable(var3);
            this.mInterpolator = new LinearInterpolator();
            this.mView = var2;
            this.mIsNav = var1;
        }

        public void applyModeBackground(int var1, int var2, boolean var3) {
            if(this.mMode != var2) {
                this.mMode = var2;
                this.mAnimating = var3;
                if(var3) {
                    long var4 = SystemClock.elapsedRealtime();
                    this.mStartTime = var4;
                    this.mEndTime = 200L + var4;
                    this.mGradientAlphaStart = this.mGradientAlpha;
                    this.mColorStart = this.mColor;
                }

                this.invalidateSelf();
            }

        }

        public void draw(Canvas var1) {
            int var3;
            if(this.mMode == 8) {
                var3 = this.mRecordWarning;
            } else if(this.mMode == 5) {
                var3 = this.mWarning;
            } else if(this.mMode == 2) {
                var3 = this.mTranslucent;
            } else if(this.mMode == 1) {
                var3 = this.mSemiTransparent;
            } else if(this.mMode == 4) {
                var3 = this.mTransparent;
            } else if(this.mMode != 6 && this.mMode != 7) {
                var3 = this.mOpaque;
            } else {
                var3 = this.mPrivate;
            }

            if(!this.mAnimating) {
                this.mColor = var3;
                this.mGradientAlpha = 0;
            } else {
                long var4 = SystemClock.elapsedRealtime();
                if(var4 >= this.mEndTime) {
                    this.mAnimating = false;
                    this.mColor = var3;
                    this.mGradientAlpha = 0;
                } else {
                    float var2 = (float)(var4 - this.mStartTime) / (float)(this.mEndTime - this.mStartTime);
                    var2 = Math.max(0.0F, Math.min(this.mInterpolator.getInterpolation(var2), 1.0F));
                    this.mGradientAlpha = (int)((float)0 * var2 + (float)this.mGradientAlphaStart * (1.0F - var2));
                    this.mColor = Color.argb((int)((float)Color.alpha(var3) * var2 + (float)Color.alpha(this.mColorStart) * (1.0F - var2)), (int)((float)Color.red(var3) * var2 + (float)Color.red(this.mColorStart) * (1.0F - var2)), (int)((float)Color.green(var3) * var2 + (float)Color.green(this.mColorStart) * (1.0F - var2)), (int)((float)Color.blue(var3) * var2 + (float)Color.blue(this.mColorStart) * (1.0F - var2)));
                }
            }

            this.mGradientAlpha = 255;
            if(this.mGradientAlpha > 0) {
                this.mGradient.setAlpha(this.mGradientAlpha);
                this.mGradient.draw(var1);
            }

            if(Color.alpha(this.mColor) > 0) {
                if(this.mMode == 7) {
                    Paint var6 = new Paint();
                    var6.setColor(this.mColor);
                    var6.setStrokeWidth(5.0F);
                    if(!this.mIsNav) {
                        var1.drawLine(0.0F, 0.0F, (float)this.mView.getWidth(), 0.0F, var6);
                    } else {
                        var1.drawLine(0.0F, (float)this.mView.getHeight(), (float)this.mView.getWidth(), (float)this.mView.getHeight(), var6);
                    }
                } else {
                    var1.drawColor(this.mColor);
                }
            }

            if(this.mAnimating) {
                this.invalidateSelf();
            }

        }

        public void finishAnimation() {
            if(this.mAnimating) {
                this.mAnimating = false;
                this.invalidateSelf();
            }

        }

        public int getOpacity() {
            return -3;
        }

        protected void onBoundsChange(Rect var1) {
            super.onBoundsChange(var1);
            this.mGradient.setBounds(var1);
        }

        public void setAlpha(int var1) {
        }

        public void setColorFilter(ColorFilter var1) {
        }

        public void setSnapViewColor(int var1) {
            this.mPrivate = -16777216 + var1;
        }
    }

}
