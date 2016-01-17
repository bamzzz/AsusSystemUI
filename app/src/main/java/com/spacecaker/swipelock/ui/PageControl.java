package com.spacecaker.swipelock.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;

public class PageControl extends LinearLayout {
   private Drawable activeDrawable;
   private Drawable inactiveDrawable;
   private ArrayList indicators;
   private Context mContext;
   private int mCurrentPage = 0;
   private int mIndicatorSize = 7;
   private PageControl.OnPageControlClickListener mOnPageControlClickListener = null;
   private int mPageCount = 0;

   public PageControl(Context var1) {
      super(var1);
      this.mContext = var1;
      this.initPageControl();
   }

   public PageControl(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   private void initPageControl() {
      Log.i("uk.co.jasonfry.android.tools.ui.PageControl", "Initialising PageControl");
      this.indicators = new ArrayList();
      this.activeDrawable = new ShapeDrawable();
      this.inactiveDrawable = new ShapeDrawable();
      this.activeDrawable.setBounds(0, 0, this.mIndicatorSize, this.mIndicatorSize);
      this.inactiveDrawable.setBounds(0, 0, this.mIndicatorSize, this.mIndicatorSize);
      OvalShape var1 = new OvalShape();
      var1.resize((float)this.mIndicatorSize, (float)this.mIndicatorSize);
      OvalShape var2 = new OvalShape();
      var2.resize((float)this.mIndicatorSize, (float)this.mIndicatorSize);
      int[] var3 = new int[2];
      TypedArray var4 = this.mContext.getTheme().obtainStyledAttributes(var3);
      ((ShapeDrawable)this.activeDrawable).getPaint().setColor(var4.getColor(0, -12303292));
      ((ShapeDrawable)this.inactiveDrawable).getPaint().setColor(var4.getColor(1, -3355444));
      ((ShapeDrawable)this.activeDrawable).setShape(var1);
      ((ShapeDrawable)this.inactiveDrawable).setShape(var2);
      this.mIndicatorSize = (int)((float)this.mIndicatorSize * this.getResources().getDisplayMetrics().density);
      this.setOnTouchListener(new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            boolean var3;
            if(PageControl.this.mOnPageControlClickListener != null) {
               switch(var2.getAction()) {
               case 1:
                  if(PageControl.this.getOrientation() == 0) {
                     if(var2.getX() < (float)(PageControl.this.getWidth() / 2)) {
                        if(PageControl.this.mCurrentPage > 0) {
                           PageControl.this.mOnPageControlClickListener.goBackwards();
                        }
                     } else if(PageControl.this.mCurrentPage < PageControl.this.mPageCount - 1) {
                        PageControl.this.mOnPageControlClickListener.goForwards();
                     }
                  } else if(var2.getY() < (float)(PageControl.this.getHeight() / 2)) {
                     if(PageControl.this.mCurrentPage > 0) {
                        PageControl.this.mOnPageControlClickListener.goBackwards();
                     }
                  } else if(PageControl.this.mCurrentPage < PageControl.this.mPageCount - 1) {
                     PageControl.this.mOnPageControlClickListener.goForwards();
                  }

                  var3 = false;
                  return var3;
               }
            }

            var3 = true;
            return var3;
         }
      });
   }

   public Drawable getActiveDrawable() {
      return this.activeDrawable;
   }

   public int getCurrentPage() {
      return this.mCurrentPage;
   }

   public Drawable getInactiveDrawable() {
      return this.inactiveDrawable;
   }

   public int getIndicatorSize() {
      return this.mIndicatorSize;
   }

   public PageControl.OnPageControlClickListener getOnPageControlClickListener() {
      return this.mOnPageControlClickListener;
   }

   public int getPageCount() {
      return this.mPageCount;
   }

   protected void onFinishInflate() {
      this.initPageControl();
   }

   public void setActiveDrawable(Drawable var1) {
      this.activeDrawable = var1;
      ((ImageView)this.indicators.get(this.mCurrentPage)).setBackgroundDrawable(this.activeDrawable);
   }

   public void setCurrentPage(int var1) {
      if(var1 < this.mPageCount) {
         ((ImageView)this.indicators.get(this.mCurrentPage)).setBackgroundDrawable(this.inactiveDrawable);
         ((ImageView)this.indicators.get(var1)).setBackgroundDrawable(this.activeDrawable);
         this.mCurrentPage = var1;
      }

   }

   public void setInactiveDrawable(Drawable var1) {
      this.inactiveDrawable = var1;

      for(int var2 = 0; var2 < this.mPageCount; ++var2) {
         ((ImageView)this.indicators.get(var2)).setBackgroundDrawable(this.inactiveDrawable);
      }

      ((ImageView)this.indicators.get(this.mCurrentPage)).setBackgroundDrawable(this.activeDrawable);
   }

   public void setIndicatorSize(int var1) {
      this.mIndicatorSize = var1;

      for(var1 = 0; var1 < this.mPageCount; ++var1) {
         ((ImageView)this.indicators.get(var1)).setLayoutParams(new LayoutParams(this.mIndicatorSize, this.mIndicatorSize));
      }

   }

   public void setOnPageControlClickListener(PageControl.OnPageControlClickListener var1) {
      this.mOnPageControlClickListener = var1;
   }

   public void setPageCount(int var1) {
      this.mPageCount = var1;

      for(int var2 = 0; var2 < var1; ++var2) {
         ImageView var4 = new ImageView(this.mContext);
         LayoutParams var3 = new LayoutParams(this.mIndicatorSize, this.mIndicatorSize);
         var3.setMargins(this.mIndicatorSize / 2, this.mIndicatorSize, this.mIndicatorSize / 2, this.mIndicatorSize);
         var4.setLayoutParams(var3);
         var4.setBackgroundDrawable(this.inactiveDrawable);
         this.indicators.add(var4);
         this.addView(var4);
      }

   }

   public interface OnPageControlClickListener {
      void goBackwards();

      void goForwards();
   }
}
