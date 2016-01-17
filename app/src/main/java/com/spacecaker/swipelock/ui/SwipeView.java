package com.spacecaker.swipelock.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import com.spacecaker.swipelock.ui.PageControl;

public class SwipeView extends HorizontalScrollView {
   private static int DEFAULT_SWIPE_THRESHOLD = 60;
   private int SCREEN_WIDTH;
   protected boolean mCallScrollToPageInOnLayout = false;
   private Context mContext;
   private int mCurrentPage = 0;
   private boolean mJustInterceptedAndIgnored = false;
   private LinearLayout mLinearLayout;
   private boolean mMostlyScrollingInX = false;
   private boolean mMostlyScrollingInY = false;
   private int mMotionStartX;
   private int mMotionStartY;
   private SwipeView.OnPageChangedListener mOnPageChangedListener = null;
   private OnTouchListener mOnTouchListener;
   private PageControl mPageControl = null;
   private int mPageWidth = 0;
   private SwipeView.SwipeOnTouchListener mSwipeOnTouchListener;

   public SwipeView(Context var1) {
      super(var1);
      this.mContext = var1;
      this.initSwipeView();
   }

   public SwipeView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      this.initSwipeView();
   }

   public SwipeView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mContext = var1;
      this.initSwipeView();
   }

   private void detectMostlyScrollingDirection(MotionEvent var1) {
      if(!this.mMostlyScrollingInX && !this.mMostlyScrollingInY) {
         float var2 = Math.abs((float)this.mMotionStartX - var1.getX());
         float var3 = Math.abs((float)this.mMotionStartY - var1.getY());
         if(var3 > var2 + 5.0F) {
            this.mMostlyScrollingInY = true;
         } else if(var2 > var3 + 5.0F) {
            this.mMostlyScrollingInX = true;
         }
      }

   }

   private void initSwipeView() {
      Log.i("uk.co.jasonfry.android.tools.ui.SwipeView", "Initialising SwipeView");
      this.mLinearLayout = new LinearLayout(this.mContext);
      this.mLinearLayout.setOrientation(0);
      super.addView(this.mLinearLayout, -1, new LayoutParams(-1, -1));
      this.setSmoothScrollingEnabled(true);
      this.setHorizontalFadingEdgeEnabled(false);
      this.setHorizontalScrollBarEnabled(false);
      this.SCREEN_WIDTH = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
      this.mPageWidth = this.SCREEN_WIDTH;
      this.mCurrentPage = 0;
      this.mSwipeOnTouchListener = new SwipeView.SwipeOnTouchListener((SwipeView.SwipeOnTouchListener)null);
      super.setOnTouchListener(this.mSwipeOnTouchListener);
   }

   private void scrollToPage(int var1, boolean var2) {
      boolean var5 = false;
      int var4 = this.mCurrentPage;
      int var3;
      if(var1 >= this.getPageCount() && this.getPageCount() > 0) {
         var3 = var1 - 1;
      } else {
         var3 = var1;
         if(var1 < 0) {
            var3 = 0;
         }
      }

      if(var2) {
         this.smoothScrollTo(this.mPageWidth * var3, 0);
      } else {
         this.scrollTo(this.mPageWidth * var3, 0);
      }

      this.mCurrentPage = var3;
      if(this.mOnPageChangedListener != null && var4 != var3) {
         this.mOnPageChangedListener.onPageChanged(var4, var3);
      }

      if(this.mPageControl != null && var4 != var3) {
         this.mPageControl.setCurrentPage(var3);
      }

      if(this.mCallScrollToPageInOnLayout) {
         var2 = var5;
      } else {
         var2 = true;
      }

      this.mCallScrollToPageInOnLayout = var2;
   }

   public void addView(View var1) {
      this.addView(var1, -1);
   }

   public void addView(View var1, int var2) {
      Object var3;
      if(var1.getLayoutParams() == null) {
         var3 = new LayoutParams(this.mPageWidth, -1);
      } else {
         var3 = var1.getLayoutParams();
         ((android.view.ViewGroup.LayoutParams)var3).width = this.mPageWidth;
      }

      this.addView(var1, var2, (android.view.ViewGroup.LayoutParams)var3);
   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      this.requestLayout();
      this.invalidate();
      this.mLinearLayout.addView(var1, var2, var3);
   }

   public void addView(View var1, android.view.ViewGroup.LayoutParams var2) {
      var2.width = this.mPageWidth;
      this.addView(var1, -1, var2);
   }

   public int calculatePageSize(MarginLayoutParams var1) {
      return this.setPageWidth(var1.leftMargin + var1.width + var1.rightMargin);
   }

   public LinearLayout getChildContainer() {
      return this.mLinearLayout;
   }

   public int getCurrentPage() {
      return this.mCurrentPage;
   }

   public SwipeView.OnPageChangedListener getOnPageChangedListener() {
      return this.mOnPageChangedListener;
   }

   public PageControl getPageControl() {
      return this.mPageControl;
   }

   public int getPageCount() {
      return this.mLinearLayout.getChildCount();
   }

   public int getPageWidth() {
      return this.mPageWidth;
   }

   public int getSwipeThreshold() {
      return DEFAULT_SWIPE_THRESHOLD;
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      boolean var2 = super.onInterceptTouchEvent(var1);
      if(var1.getAction() == 0) {
         this.mMotionStartX = (int)var1.getX();
         this.mMotionStartY = (int)var1.getY();
         if(!this.mJustInterceptedAndIgnored) {
            this.mMostlyScrollingInX = false;
            this.mMostlyScrollingInY = false;
         }
      } else if(var1.getAction() == 2) {
         this.detectMostlyScrollingDirection(var1);
      }

      if(this.mMostlyScrollingInY) {
         var2 = false;
      } else if(this.mMostlyScrollingInX) {
         this.mJustInterceptedAndIgnored = true;
         var2 = true;
      }

      return var2;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if(this.mCallScrollToPageInOnLayout) {
         this.scrollToPage(this.mCurrentPage);
         this.mCallScrollToPageInOnLayout = false;
      }

   }

   protected boolean onRequestFocusInDescendants(int var1, Rect var2) {
      return false;
   }

   public boolean onTrackballEvent(MotionEvent var1) {
      return true;
   }

   public void requestChildFocus(View var1, View var2) {
      this.requestFocus();
   }

   public void scrollToPage(int var1) {
      this.scrollToPage(var1, false);
   }

   public void setOnPageChangedListener(SwipeView.OnPageChangedListener var1) {
      this.mOnPageChangedListener = var1;
   }

   public void setOnTouchListener(OnTouchListener var1) {
      this.mOnTouchListener = var1;
   }

   public void setPageControl(PageControl var1) {
      this.mPageControl = var1;
      var1.setPageCount(this.getPageCount());
      var1.setCurrentPage(this.mCurrentPage);
      var1.setOnPageControlClickListener(new PageControl.OnPageControlClickListener() {
         public void goBackwards() {
            SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage - 1);
         }

         public void goForwards() {
            SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage + 1);
         }
      });
   }

   public int setPageWidth(int var1) {
      this.mPageWidth = var1;
      return (this.SCREEN_WIDTH - this.mPageWidth) / 2;
   }

   public void setSwipeThreshold(int var1) {
      DEFAULT_SWIPE_THRESHOLD = var1;
   }

   public void smoothScrollToPage(int var1) {
      this.scrollToPage(var1, true);
   }

   public interface OnPageChangedListener {
      void onPageChanged(int var1, int var2);
   }

   private class SwipeOnTouchListener implements OnTouchListener {
      private int mDistanceX;
      private boolean mFirstMotionEvent;
      private int mPreviousDirection;
      private boolean mSendingDummyMotionEvent;

      private SwipeOnTouchListener() {
         this.mSendingDummyMotionEvent = false;
         this.mFirstMotionEvent = true;
      }

      // $FF: synthetic method
      SwipeOnTouchListener(SwipeView.SwipeOnTouchListener var2) {
         this();
      }

      private boolean actionDown(MotionEvent var1) {
         SwipeView.this.mMotionStartX = (int)var1.getX();
         SwipeView.this.mMotionStartY = (int)var1.getY();
         this.mFirstMotionEvent = false;
         return false;
      }

      private boolean actionMove(MotionEvent var1) {
         int var3 = SwipeView.this.mMotionStartX - (int)var1.getX();
         byte var2;
         if(var3 < 0) {
            if(this.mDistanceX + 4 <= var3) {
               var2 = 1;
            } else {
               var2 = -1;
            }
         } else if(this.mDistanceX - 4 <= var3) {
            var2 = 1;
         } else {
            var2 = -1;
         }

         if(var2 != this.mPreviousDirection && !this.mFirstMotionEvent) {
            SwipeView.this.mMotionStartX = (int)var1.getX();
            this.mDistanceX = SwipeView.this.mMotionStartX - (int)var1.getX();
         } else {
            this.mDistanceX = var3;
         }

         this.mPreviousDirection = var2;
         boolean var4;
         if(SwipeView.this.mJustInterceptedAndIgnored) {
            this.mSendingDummyMotionEvent = true;
            SwipeView.this.dispatchTouchEvent(MotionEvent.obtain(var1.getDownTime(), var1.getEventTime(), 0, (float)SwipeView.this.mMotionStartX, (float)SwipeView.this.mMotionStartY, var1.getPressure(), var1.getSize(), var1.getMetaState(), var1.getXPrecision(), var1.getYPrecision(), var1.getDeviceId(), var1.getEdgeFlags()));
            SwipeView.this.mJustInterceptedAndIgnored = false;
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      private boolean actionUp(MotionEvent var1) {
         float var3 = (float)SwipeView.this.getScrollX();
         float var2 = (float)(SwipeView.this.mLinearLayout.getMeasuredWidth() / SwipeView.this.mPageWidth);
         var3 /= (float)SwipeView.this.mPageWidth;
         if(this.mPreviousDirection == 1) {
            if(this.mDistanceX > SwipeView.DEFAULT_SWIPE_THRESHOLD) {
               if((float)SwipeView.this.mCurrentPage < var2 - 1.0F) {
                  var2 = (float)((int)(var3 + 1.0F) * SwipeView.this.mPageWidth);
               } else {
                  var2 = (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth);
               }
            } else if((float)Math.round(var3) == var2 - 1.0F) {
               var2 = (float)((int)(var3 + 1.0F) * SwipeView.this.mPageWidth);
            } else {
               var2 = (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth);
            }
         } else if(this.mDistanceX < -SwipeView.DEFAULT_SWIPE_THRESHOLD) {
            var2 = (float)((int)var3 * SwipeView.this.mPageWidth);
         } else if(Math.round(var3) == 0) {
            var2 = (float)((int)var3 * SwipeView.this.mPageWidth);
         } else {
            var2 = (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth);
         }

         SwipeView.this.smoothScrollToPage((int)var2 / SwipeView.this.mPageWidth);
         this.mFirstMotionEvent = true;
         this.mDistanceX = 0;
         SwipeView.this.mMostlyScrollingInX = false;
         SwipeView.this.mMostlyScrollingInY = false;
         return true;
      }

      public boolean onTouch(View var1, MotionEvent var2) {
         boolean var4 = true;
         boolean var3;
         if((SwipeView.this.mOnTouchListener != null && !SwipeView.this.mJustInterceptedAndIgnored || SwipeView.this.mOnTouchListener != null && this.mSendingDummyMotionEvent) && SwipeView.this.mOnTouchListener.onTouch(var1, var2)) {
            var3 = var4;
            if(var2.getAction() == 1) {
               this.actionUp(var2);
               var3 = var4;
            }
         } else if(this.mSendingDummyMotionEvent) {
            this.mSendingDummyMotionEvent = false;
            var3 = false;
         } else {
            switch(var2.getAction()) {
            case 0:
               var3 = this.actionDown(var2);
               break;
            case 1:
               var3 = this.actionUp(var2);
               break;
            case 2:
               var3 = this.actionMove(var2);
               break;
            default:
               var3 = false;
            }
         }

         return var3;
      }
   }
}
