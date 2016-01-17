package com.android.morningstar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.io.File;

public class CPUTemperature extends TextView {
   private static final String UPDATE_INTENT = "update_alarm";
   private static String[] files = new String[]{"/sys/device/platform/omap/omap_temp_sensor.0/temperature", "/sys/kernel/debug/tegra_thermal/temp_tj", "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp", "/sys/class/thermal/thermal_zone-/temp", "/sys/class/thermal/thermal_zone1/temp", "/sys/devices/platform/s5p-tmu/curr_temp", "/sys/devices/virtual/thermal/thermal_zone0/temp", "/sys/device/virtual/thermal/thermal_zone1/temp", "/sys/devides/system/cpu/cpufreq/cput_attributes/cur_temp", "/sys/devices/platform/s5p-tmu/temperature"};
   private File file;
   private boolean isCelcius;
   private Context mContext;
   private final BroadcastReceiver mReceiver;
   private PendingIntent pendingIntent;

   public CPUTemperature(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CPUTemperature(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public CPUTemperature(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.file = null;
      this.isCelcius = false;
      this.pendingIntent = null;
      this.mReceiver = new BroadcastReceiver() {
         private boolean screenOn = true;

         public void onReceive(Context var1, Intent var2) {
            if(var2.getAction().equals("android.intent.action.SCREEN_ON")) {
               this.screenOn = true;
            } else if(var2.getAction().equals("android.intent.action.SCREEN_OFF")) {
               this.screenOn = false;
            } else if(var2.getAction().equals("update_alarm") && this.screenOn) {
               CPUTemperature.this.updateTemperature();
            }

         }
      };
      this.mContext = var1;
      this.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if(!CPUTemperature.this.isCelcius) {
               CPUTemperature.this.isCelcius = true;
            } else {
               CPUTemperature.this.isCelcius = false;
            }

         }
      });
      this.getFile();
   }

   private void cancelAlarm() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService(Context.ALARM_SERVICE);
      if(this.pendingIntent != null) {
         var1.cancel(this.pendingIntent);
         this.pendingIntent.cancel();
      }

   }

   private void getFile() {
      this.file = this.getTempFile("default");
   }

   private File getTempFile(String var1) {
      File var4 = null;
      File var5;
      if(var1 != null) {
         label32: {
            var5 = new File(var1);
            if(var5.exists()) {
               var4 = var5;
               if(var5.canRead()) {
                  break label32;
               }
            }

            var4 = null;
         }
      }

      if(var4 != null) {
         var5 = var4;
         if(!var1.equals("default")) {
            return var5;
         }
      }

      String[] var6 = files;
      int var3 = var6.length;
      int var2 = 0;

      while(true) {
         var5 = var4;
         if(var2 >= var3) {
            break;
         }

         File var7 = new File(var6[var2]);
         if(var7.exists()) {
            var5 = var7;
            if(var7.canRead()) {
               break;
            }
         }

         var4 = null;
         ++var2;
      }

      return var5;
   }

   private void initValues() {
      if(200 < 200) {
         this.setText("loading");
      }

   }

   private void setAlarm(int var1) {
      AlarmManager var2 = (AlarmManager)this.mContext.getSystemService(Context.ALARM_SERVICE);
      Intent var3 = new Intent("update_alarm");
      this.pendingIntent = PendingIntent.getBroadcast(this.mContext, 0, var3, 0);
      var2.setRepeating(1, System.currentTimeMillis(), (long)var1, this.pendingIntent);
   }

   private void updateTemperature() {
      // $FF: Couldn't be decompiled
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      IntentFilter var1 = new IntentFilter();
      var1.addAction("update_alarm");
      var1.addAction("android.intent.action.SCREEN_OFF");
      var1.addAction("android.intent.action.SCREEN_ON");
      this.mContext.registerReceiver(this.mReceiver, var1);
      this.setAlarm(1000);
   }

   protected void onDetachedFromWindow() {
      this.mContext.unregisterReceiver(this.mReceiver);
      this.cancelAlarm();
      super.onDetachedFromWindow();
   }
}
