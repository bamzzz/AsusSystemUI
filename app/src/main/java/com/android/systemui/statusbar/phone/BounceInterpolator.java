package com.android.systemui.statusbar.phone;

import android.view.animation.Interpolator;

public class BounceInterpolator implements Interpolator {
    public BounceInterpolator() {
        super();
    }

    public float getInterpolation(float var1) {
        var1 *= 1.1F;
        if(var1 < 0.36363637F) {
            var1 = 7.5625F * var1 * var1;
        } else if(var1 < 0.72727275F) {
            var1 -= 0.54545456F;
            var1 = 7.5625F * var1 * var1 + 0.75F;
        } else if(var1 < 0.90909094F) {
            var1 -= 0.8181818F;
            var1 = 7.5625F * var1 * var1 + 0.9375F;
        } else {
            var1 = 1.0F;
        }

        return var1;
    }
}
