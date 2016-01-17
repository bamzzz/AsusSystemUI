package com.spacecaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SpaceLayOut extends LinearLayout {
   private boolean LAYOUT = false;
   private String UPDATE = "com.androidminang.UPDATE";
   private Context mContext;
   private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals(SpaceLayOut.this.UPDATE)) {
            SpaceLayOut.this.LAYOUT = var2.getBooleanExtra("LEOT_YANG_MANA_NIH", true);
            SpaceLayOut.this.update(SpaceLayOut.this.LAYOUT);
         }

      }
   };
   private boolean mUpdating = false;

   public SpaceLayOut(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   private void hide(View var1) {
      var1.setVisibility(View.GONE);
   }

   private void show(View var1) {
      var1.setVisibility(View.VISIBLE);
   }

   private void update(boolean var1) {
      View var3 = this.getChildAt(0);
      View var2 = this.getChildAt(1);
      if(var1) {
         this.show(var3);
         this.hide(var2);
      } else {
         this.show(var2);
         this.hide(var3);
      }

   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.setUpdates(true);
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.setUpdates(false);
   }

   void setUpdates(boolean var1) {
      if(var1 != this.mUpdating) {
         this.mUpdating = var1;
         if(var1) {
            IntentFilter var2 = new IntentFilter();
            var2.addAction(this.UPDATE);
            this.mContext.registerReceiver(this.mIntentReceiver, var2, (String)null, (Handler)null);
            this.update(this.LAYOUT);
         } else {
            this.mContext.unregisterReceiver(this.mIntentReceiver);
         }
      }

   }
}
