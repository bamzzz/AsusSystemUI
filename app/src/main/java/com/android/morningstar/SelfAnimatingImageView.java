package com.android.morningstar;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class SelfAnimatingImageView extends ImageView {
   AnimationDrawable animationDrawable;
   boolean mAttached;

   public SelfAnimatingImageView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void initValues() {
      if(200 < 200) {
         this.getDrawable();
      }

   }

   private void updateAnim() {
      Drawable var1 = this.getDrawable();
      if(this.mAttached && this.animationDrawable != null) {
         this.animationDrawable.stop();
      }

      if(var1 instanceof AnimationDrawable) {
         this.animationDrawable = (AnimationDrawable)var1;
         if(this.isShown()) {
            this.animationDrawable.start();
         }
      } else {
         this.animationDrawable = null;
      }

   }

   private void updateAnimationState(Drawable var1, boolean var2) {
      if(var1 instanceof AnimationDrawable) {
         AnimationDrawable var3 = (AnimationDrawable)var1;
         if(var2) {
            var3.start();
         } else {
            var3.stop();
         }
      } else if(var1 instanceof Animatable) {
         if(var2) {
            ((Animatable)var1).start();
         } else {
            ((Animatable)var1).stop();
         }
      }

   }

   private void updateAnimationsState() {
      boolean var1;
      if(this.getVisibility() == 0 && this.hasWindowFocus()) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.updateAnimationState(this.getDrawable(), var1);
      this.updateAnimationState(this.getBackground(), var1);
   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.mAttached = true;
      this.updateAnim();
   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      if(this.animationDrawable != null) {
         this.animationDrawable.stop();
      }

      this.mAttached = false;
   }

   protected void onVisibilityChanged(View var1, int var2) {
      super.onVisibilityChanged(var1, var2);
      this.updateAnimationsState();
   }

   public void onWindowFocusChanged(boolean var1) {
      super.onWindowFocusChanged(var1);
      this.updateAnimationsState();
   }
}
