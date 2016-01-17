package com.panel.panel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;
import com.serajr.utils.ResourceUtils;

public class Panel extends ViewFlipper {
   ViewFlipper VF;
   private Context mContext;

   public Panel(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      this.VF = (ViewFlipper)this.findViewById(ResourceUtils.getIdResId("switch_panel_content"));
      BroadcastReceiver var3 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            Panel.this.VF.setDisplayedChild(2);
            Panel.this.VF.setInAnimation(Panel.this.inFromRightAnimation());
            Panel.this.VF.setOutAnimation(Panel.this.outToLeftAnimation());
         }
      };
      BroadcastReceiver var4 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            Panel.this.VF.setDisplayedChild(1);
            Panel.this.VF.setInAnimation(Panel.this.inFromLeftAnimation());
            Panel.this.VF.setOutAnimation(Panel.this.outToRightAnimation());
         }
      };
      var1.registerReceiver(var3, new IntentFilter("com.bamzzz.panel.FLIP_TO_SLIDER"));
      var1.registerReceiver(var4, new IntentFilter("com.bamzzz.panel.FLIP_TO_PANEL"));
   }

   private Animation inFromLeftAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, -1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(300L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation inFromRightAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(300L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation outToLeftAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 0.0F, 2, -1.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(300L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }

   private Animation outToRightAnimation() {
      TranslateAnimation var1 = new TranslateAnimation(2, 0.0F, 2, 1.0F, 2, 0.0F, 2, 0.0F);
      var1.setDuration(300L);
      var1.setInterpolator(new AccelerateInterpolator());
      return var1;
   }
}
