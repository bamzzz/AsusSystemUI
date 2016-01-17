package com.markmellarpes.statusbar;

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

public class MultiPanelFlipper extends ViewFlipper {
   ViewFlipper VF;
   private Context mContext;

   public MultiPanelFlipper(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      this.VF = (ViewFlipper)this.findViewById(ResourceUtils.getIdResId("vp_multi_panel_bamz"));
      BroadcastReceiver var3 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelFlipper.this.VF.setDisplayedChild(0);
            MultiPanelFlipper.this.VF.setInAnimation(MultiPanelFlipper.this.inFromLeftAnimation());
            MultiPanelFlipper.this.VF.setOutAnimation(MultiPanelFlipper.this.outToRightAnimation());
         }
      };
      BroadcastReceiver var4 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelFlipper.this.VF.setDisplayedChild(1);
            MultiPanelFlipper.this.VF.setInAnimation(MultiPanelFlipper.this.inFromLeftAnimation());
            MultiPanelFlipper.this.VF.setOutAnimation(MultiPanelFlipper.this.outToRightAnimation());
         }
      };
      BroadcastReceiver var5 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelFlipper.this.VF.setDisplayedChild(2);
            MultiPanelFlipper.this.VF.setInAnimation(MultiPanelFlipper.this.inFromLeftAnimation());
            MultiPanelFlipper.this.VF.setOutAnimation(MultiPanelFlipper.this.outToRightAnimation());
         }
      };
      var1.registerReceiver(var3, new IntentFilter("com.bamzzz.statusbar.FLIP_TO_NOTIF"));
      var1.registerReceiver(var4, new IntentFilter("com.bamzzz.statusbar.FLIP_TO_TOGGLES"));
      var1.registerReceiver(var5, new IntentFilter("com.bamzzz.statusbar.FLIP_TO_SLIDERS"));
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
