package com.markmellarpes.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.serajr.utils.ResourceUtils;

public class MultiPanelButtons extends LinearLayout {
   private Context mContext;
   String message;
   TextView notif;
   TextView slider;
   TextView toggle;
   View view1;
   View view2;

   public MultiPanelButtons(final Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      this.notif = new TextView(var1);
      this.toggle = new TextView(var1);
      this.slider = new TextView(var1);
      this.notif.setBackgroundResource(ResourceUtils.getDrawableResId("toggle"));
      this.toggle.setBackgroundResource(ResourceUtils.getDrawableResId("toggle"));
      this.slider.setBackgroundResource(ResourceUtils.getDrawableResId("toggle"));
      float var3 = this.getContext().getResources().getDisplayMetrics().density;
      int var4 = (int)(8.0F * var3 + 0.5F);
      this.notif.setGravity(17);
      this.toggle.setGravity(17);
      this.slider.setGravity(17);
      this.notif.setText("Home");
      this.toggle.setText("Profile");
      this.slider.setText("Info");
      this.view1 = new View(var1);
      this.view2 = new View(var1);
      LayoutParams var7 = new LayoutParams(1, -1);
      var7.topMargin = var4;
      var7.bottomMargin = var4;
      this.view1.setLayoutParams(var7);
      this.view2.setLayoutParams(var7);
      this.view1.setBackgroundColor(Color.parseColor("#1fffffff"));
      this.view2.setBackgroundColor(Color.parseColor("#1fffffff"));
      var7 = new LayoutParams(0, -1);
      var7.weight = 1.0F;
      var7.gravity = Gravity.CENTER; //17;
      this.notif.setLayoutParams(var7);
      this.toggle.setLayoutParams(var7);
      this.slider.setLayoutParams(var7);
      this.addView(this.notif);
      this.addView(this.view1);
      this.addView(this.toggle);
      this.addView(this.view2);
      this.addView(this.slider);
      this.setLayoutParams(new LayoutParams(-1, (int)(30.0F * var3 + 0.5F)));
      final SharedPreferences var5 = var1.getSharedPreferences("BamzzzPrefsFile", 0);
      String var8 = var5.getString("type", "phablet");
      if("tablet".equals(var8)) {
         if(!Boolean.valueOf(var5.getBoolean("SliderVisibility", false)).booleanValue()) {
            this.unhideSlider();
         } else {
            this.hideSlider();
         }

         this.hide();
      }

      if("normal".equals(var8)) {
         if(!Boolean.valueOf(var5.getBoolean("SliderVisibility", false)).booleanValue()) {
            this.unhideSlider();
         } else {
            this.hideSlider();
         }

         this.hide();
      } else if("phablet".equals(var8)) {
         this.unhide();
         if(!Boolean.valueOf(var5.getBoolean("SliderVisibility", false)).booleanValue()) {
            this.unhideSlider();
         } else {
            this.hideSlider();
         }
      }

      this.notif.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            var1x.setSelected(true);
            MultiPanelButtons.this.toggle.setSelected(false);
            MultiPanelButtons.this.slider.setSelected(false);
            Intent var2 = new Intent();
            var2.setAction("com.bamzzz.statusbar.FLIP_TO_NOTIF");
            var1.sendBroadcast(var2);
         }
      });
      this.toggle.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            var1x.setSelected(true);
            MultiPanelButtons.this.notif.setSelected(false);
            MultiPanelButtons.this.slider.setSelected(false);
            Intent var2 = new Intent();
            var2.setAction("com.bamzzz.statusbar.FLIP_TO_TOGGLES");
            var1.sendBroadcast(var2);
         }
      });
      this.slider.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            var1x.setSelected(true);
            MultiPanelButtons.this.notif.setSelected(false);
            MultiPanelButtons.this.toggle.setSelected(false);
            Intent var2 = new Intent();
            var2.setAction("com.bamzzz.statusbar.FLIP_TO_SLIDERS");
            var1.sendBroadcast(var2);
         }
      });
      BroadcastReceiver var9 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelButtons.this.hideSlider();
         }
      };
      BroadcastReceiver var6 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelButtons.this.unhideSlider();
         }
      };
      BroadcastReceiver var10 = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            MultiPanelButtons.this.message = var2.getStringExtra("layoutType");
            if("tablet".equals(MultiPanelButtons.this.message)) {
               MultiPanelButtons.this.hide();
            }

            if("normal".equals(MultiPanelButtons.this.message)) {
               MultiPanelButtons.this.hide();
            } else if("phablet".equals(MultiPanelButtons.this.message)) {
               if(!Boolean.valueOf(var5.getBoolean("SliderVisibility", false)).booleanValue()) {
                  MultiPanelButtons.this.unhideSlider();
               } else {
                  MultiPanelButtons.this.hideSlider();
               }

               MultiPanelButtons.this.unhide();
            }

         }
      };
      var1.registerReceiver(var9, new IntentFilter("com.bamzzz.statusbar.HIDE_SLIDER"));
      var1.registerReceiver(var6, new IntentFilter("com.bamzzz.statusbar.UNHIDE_SLIDER"));
      var1.registerReceiver(var10, new IntentFilter("com.bamzzz.statusbar.CHANGE_LAYOUT"));
   }

   public void hide() {
      this.setVisibility(View.GONE);
   }

   public void hideSlider() {
      LayoutParams var1 = new LayoutParams(0, -1);
      var1.weight = 1.5F;
      var1.gravity = Gravity.CENTER; //17;
      this.notif.setLayoutParams(var1);
      this.toggle.setLayoutParams(var1);
      this.slider.setVisibility(View.GONE);
      this.view2.setVisibility(View.GONE);
   }

   public void unhide() {
      this.setVisibility(View.VISIBLE);
   }

   public void unhideSlider() {
      LayoutParams var1 = new LayoutParams(0, -1);
      var1.weight = 1.0F;
      var1.gravity = Gravity.CENTER; //17;
      this.notif.setLayoutParams(var1);
      this.toggle.setLayoutParams(var1);
      this.slider.setVisibility(View.VISIBLE);
      this.view2.setVisibility(View.VISIBLE);
   }
}
