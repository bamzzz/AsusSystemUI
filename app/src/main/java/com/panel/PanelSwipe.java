package com.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

class PanelSwipe extends FrameLayout implements AnimationListener {
   private static final int ANIM_DURATION = 400;
   private static final int LEFT = 1;
   private static final int MAJOR_MOVE = 60;
   private static final int RIGHT = 2;
   private TranslateAnimation inLeft;
   private TranslateAnimation inRight;
   private View[] mChildren = new View[0];
   private int mCurrentView = 0;
   private GestureDetector mGestureDetector;
   private PanelSwipe.Listener mListener;
   private int mPreviousMove;
   private int mWidth;
   private TranslateAnimation outLeft;
   private TranslateAnimation outRight;

   public PanelSwipe(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mGestureDetector = new GestureDetector(var1, new SimpleOnGestureListener() {
         public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
            boolean var5;
            if(Math.abs((int)(var2.getX() - var1.getX())) > 10 && Math.abs(var3) > Math.abs(var4)) {
               if(var3 > 0.0F) {
                  PanelSwipe.this.moveRight();
               } else {
                  PanelSwipe.this.moveLeft();
               }

               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         }
      });
   }

   private void updateCurrentView() {
      for(int var1 = this.mChildren.length - 1; var1 >= 0; --var1) {
         View var3 = this.mChildren[var1];
         byte var2;
         if(var1 == this.mCurrentView) {
            var2 = 0;
         } else {
            var2 = 8;
         }

         var3.setVisibility(var2);
      }

   }

   int getCurrentIndex() {
      return this.mCurrentView;
   }

   void moveLeft() {
      if(this.mCurrentView < this.mChildren.length - 1 && this.mPreviousMove != 1) {
         this.mChildren[this.mCurrentView + 1].setVisibility(0);
         this.mChildren[this.mCurrentView + 1].startAnimation(this.inLeft);
         this.mChildren[this.mCurrentView].startAnimation(this.outLeft);
         this.mChildren[this.mCurrentView].setVisibility(8);
         ++this.mCurrentView;
         this.mPreviousMove = 1;
      }

   }

   void moveRight() {
      if(this.mCurrentView > 0 && this.mPreviousMove != 2) {
         this.mChildren[this.mCurrentView - 1].setVisibility(0);
         this.mChildren[this.mCurrentView - 1].startAnimation(this.inRight);
         this.mChildren[this.mCurrentView].startAnimation(this.outRight);
         this.mChildren[this.mCurrentView].setVisibility(8);
         --this.mCurrentView;
         this.mPreviousMove = 2;
      }

   }

   public void onAnimationEnd(Animation var1) {
      if(this.mListener != null) {
         this.mListener.onChange();
      }

   }

   public void onAnimationRepeat(Animation var1) {
   }

   public void onAnimationStart(Animation var1) {
   }

   protected void onFinishInflate() {
      int var2 = this.getChildCount();
      this.mChildren = new View[var2];

      for(int var1 = 0; var1 < var2; ++var1) {
         this.mChildren[var1] = this.getChildAt(var1);
      }

      this.updateCurrentView();
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      return this.mGestureDetector.onTouchEvent(var1);
   }

   public void onSizeChanged(int var1, int var2, int var3, int var4) {
      this.mWidth = var1;
      this.inLeft = new TranslateAnimation((float)this.mWidth, 0.0F, 0.0F, 0.0F);
      this.inLeft.setAnimationListener(this);
      this.outLeft = new TranslateAnimation(0.0F, (float)(-this.mWidth), 0.0F, 0.0F);
      this.inRight = new TranslateAnimation((float)(-this.mWidth), 0.0F, 0.0F, 0.0F);
      this.inRight.setAnimationListener(this);
      this.outRight = new TranslateAnimation(0.0F, (float)this.mWidth, 0.0F, 0.0F);
      this.inLeft.setDuration(402L);
      this.outLeft.setDuration(402L);
      this.inRight.setDuration(402L);
      this.outRight.setDuration(402L);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      this.mGestureDetector.onTouchEvent(var1);
      return true;
   }

   void setCurrentIndex(int var1) {
      boolean var2;
      if(this.mCurrentView != var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.mCurrentView = var1;
      this.updateCurrentView();
      if(var2 && this.mListener != null) {
         this.mListener.onChange();
      }

   }

   public void setListener(PanelSwipe.Listener var1) {
      this.mListener = var1;
   }

   public interface Listener {
      void onChange();
   }
}
