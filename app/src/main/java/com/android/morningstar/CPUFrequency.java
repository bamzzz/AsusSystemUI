package com.android.morningstar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.widget.TextView;
import java.io.File;

public class CPUFrequency extends TextView {
   private static final String UPDATE_INTENT = "update_alarm";
   private static String[] files = new String[]{"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq"};
   private File freqFile;
   private Context mContext;
   private final BroadcastReceiver mReceiver;
   private PendingIntent pendingIntent;

   public CPUFrequency(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CPUFrequency(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public CPUFrequency(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.freqFile = null;
      this.pendingIntent = null;
      this.mReceiver = new BroadcastReceiver() {
         private boolean screenOn = true;

         public void onReceive(Context var1, Intent var2) {
            if(var2.getAction().equals("android.intent.action.SCREEN_ON")) {
               this.screenOn = true;
            } else if(var2.getAction().equals("android.intent.action.SCREEN_OFF")) {
               this.screenOn = false;
            } else if(var2.getAction().equals("update_alarm") && this.screenOn) {
               CPUFrequency.this.updateFrequency();
            }

         }
      };
      this.mContext = var1;
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
      this.freqFile = this.getFreqFile("default");
   }

   private File getFreqFile(String var1) {
      File var4;
      File var6;
      label25: {
         var4 = new File(var1);
         if(var4.exists()) {
            var6 = var4;
            if(var4.canRead()) {
               break label25;
            }
         }

         var6 = null;
      }

      String[] var5 = files;
      int var3 = var5.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         var4 = new File(var5[var2]);
         if(var4.exists()) {
            var6 = var4;
            if(var4.canRead()) {
               break;
            }
         }

         var6 = null;
      }

      return var6;
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

   private void updateFrequency() {
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
