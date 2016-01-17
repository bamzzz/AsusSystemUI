package com.lenox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class Flipper extends LinearLayout {
   public Flipper(Context var1, AttributeSet var2) {
      super(var1, var2);
      BroadcastReceiver var4 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            if(Flipper.this.getVisibility() == View.GONE) {
               Flipper.this.setVisibility(View.VISIBLE);
               Flipper.this.startAnimation(Flipper.this.inFromRightAnimation());
            } else {
               Flipper.this.startAnimation(Flipper.this.outToLeftAnimation());
               (new Handler()).postDelayed(new Runnable() {
                  public void run() {
                     Flipper.this.setVisibility(View.GONE);
                  }
               }, 500L);
            }

         }
      };
      BroadcastReceiver var3 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            if(Flipper.this.getVisibility() == View.GONE) {
               Flipper.this.setVisibility(View.VISIBLE);
               Flipper.this.startAnimation(Flipper.this.inFromLeftAnimation());
            } else {
               Flipper.this.startAnimation(Flipper.this.outToRightAnimation());
               (new Handler()).postDelayed(new Runnable() {
                  public void run() {
                     Flipper.this.setVisibility(View.GONE);
                  }
               }, 500L);
            }

         }
      };
      var1.registerReceiver(var4, new IntentFilter("1"));
      var1.registerReceiver(var3, new IntentFilter("2"));
   }

   private Animation inFromLeftAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, -1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(500L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation inFromRightAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(500L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation outToLeftAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 0.0F, 2, -1.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(500L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation outToRightAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 0.0F, 2, 1.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(500L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }
}
