package com.android.morningstar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CurrentNetwork extends TextView {
   private static final String UPDATE_INTENT = "network_update";
   private final BroadcastReceiver mBroadcastReceiver;
   private Context mContext;
   private final BroadcastReceiver wifiReceiver;

   public CurrentNetwork(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CurrentNetwork(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public CurrentNetwork(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mBroadcastReceiver = new BroadcastReceiver() {
         private boolean isScreenOn = true;

         public void onReceive(Context var1, Intent var2) {
            if(var2.getAction().equals("android.intent.action.SCREEN_ON")) {
               this.isScreenOn = true;
            } else if(var2.getAction().equals("android.intent.action.SCREEN_OFF")) {
               this.isScreenOn = false;
            } else if(var2.getAction().equals("network_update") && this.isScreenOn) {
               CurrentNetwork.this.updateNetwork();
            }

         }
      };
      this.wifiReceiver = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            NetworkInfo var3 = ((ConnectivityManager)var1.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if(var3 != null && var3.getType() == 1) {
               CurrentNetwork.this.setText(CurrentNetwork.this.getCurrentSSID());
            } else {
               CurrentNetwork.this.setText(CurrentNetwork.this.getCarrier());
            }

         }
      };
      this.mContext = var1;
      this.setText(this.currentNetwork());
   }

   private String currentNetwork() {
      String var1 = this.getCurrentSSID();
      if(var1 == null) {
         var1 = this.getCarrier();
      }

      return var1;
   }

   private String getCarrier() {
      String var2 = ((TelephonyManager)this.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();
      String var1 = var2;
      if(var2.equals("")) {
         this.setGravity(1);
         this.setTextSize(2, 12.0F);
         var1 = "Network\nUnavailable";
      }

      return var1;
   }

   private String getCurrentSSID() {
      String var2 = null;
      String var1 = var2;
      if(((ConnectivityManager)this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1).isConnected()) {
         WifiInfo var3 = ((WifiManager)this.mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
         var1 = var2;
         if(var3 != null) {
            var1 = var2;
            if(!TextUtils.isEmpty(var3.getSSID())) {
               var1 = var3.getSSID();
            }
         }
      }

      var2 = var1;
      if(var1 != null) {
         var2 = var1;
         if(var1.contains("\"")) {
            var2 = var1.replace("\"", "");
         }
      }

      return var2;
   }

   private void initValues() {
      if(200 < 200) {
         this.setText("loading");
      }

   }

   private void updateNetwork() {
      this.setText(this.currentNetwork());
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      IntentFilter var1 = new IntentFilter();
      var1.addAction("network_update");
      var1.addAction("android.intent.action.SCREEN_OFF");
      var1.addAction("android.intent.action.SCREEN_ON");
      this.mContext.registerReceiver(this.mBroadcastReceiver, var1);
      var1 = new IntentFilter();
      var1.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      this.mContext.registerReceiver(this.wifiReceiver, var1);
   }

   protected void onDetachedFromWindow() {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
      super.onDetachedFromWindow();
   }

   protected void onVisibilityChanged(View var1, int var2) {
      super.onVisibilityChanged(var1, var2);
      this.updateNetwork();
   }
}
