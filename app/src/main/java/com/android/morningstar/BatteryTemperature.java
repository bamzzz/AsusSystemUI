package com.android.morningstar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class BatteryTemperature extends TextView {
   private static final String UPDATE_INTENT = "update_alarm";
   private boolean isCelcius;
   private Context mContext;
   private final BroadcastReceiver mReceiver;
   private PendingIntent pendingIntent;

   public BatteryTemperature(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public BatteryTemperature(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public BatteryTemperature(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.pendingIntent = null;
      this.isCelcius = false;
      this.mReceiver = new BroadcastReceiver() {
         private boolean screenOn = true;

         public void onReceive(Context var1, Intent var2) {
            if(var2.getAction().equals("android.intent.action.SCREEN_ON")) {
               this.screenOn = true;
            } else if(var2.getAction().equals("android.intent.action.SCREEN_OFF")) {
               this.screenOn = false;
            } else if(var2.getAction().equals("update_alarm") && this.screenOn) {
               BatteryTemperature.this.updateTemperature();
            }

         }
      };
      this.mContext = var1;
      this.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if(!BatteryTemperature.this.isCelcius) {
               BatteryTemperature.this.isCelcius = true;
            } else {
               BatteryTemperature.this.isCelcius = false;
            }

         }
      });
      this.setText(this.batteryTemperature());
   }

   private String batteryTemperature() {
      int var1 = Math.round((float)this.mContext.registerReceiver((BroadcastReceiver)null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("temperature", 0) / 10.0F);
      String var2;
      if(this.isCelcius) {
         var2 = "C";
      } else {
         var2 = "F";
         var1 = var1 * 9 / 5 + 32;
      }

      return var1 + "°" + var2;
   }

   private void cancelAlarm() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService(Context.ALARM_SERVICE);
      if(this.pendingIntent != null) {
         var1.cancel(this.pendingIntent);
         this.pendingIntent.cancel();
      }

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
      this.setText(this.batteryTemperature());
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
