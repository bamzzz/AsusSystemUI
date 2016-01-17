package com.asus.systemui.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;
//import android.view.WindowManagerGlobal;

public class SystemUiUtil {
   static String TAG = "SystemUiUtil";

   public static int getIdentifier(Context var0, String var1, String var2) {
      return var0.getResources().getIdentifier(var2, var1, "android");
   }

   public static boolean getShowNav() {
      boolean var0 = false;

      /*boolean var1;
      try {
         var1 = WindowManagerGlobal.getWindowManagerService().hasNavigationBar();
	  } catch (RemoteException var3) {
         Log.w(TAG, "getShowNav()- no window manager", var3);
         return var0;
      }

      var0 = var1;*/
      return var0;
   }

   public static boolean isPhoneSize(Context var0) {
      DisplayMetrics var4 = new DisplayMetrics();
      WindowManager var5 = (WindowManager)var0.getSystemService(Context.WINDOW_SERVICE);
      var5.getDefaultDisplay().getMetrics(var4);
      int var2 = var4.densityDpi;
      Point var6 = new Point();
      var5.getDefaultDisplay().getRealSize(var6);
      int var1;
      if(var6.x > var6.y) {
         var1 = var6.y;
      } else {
         var1 = var6.x;
      }

      boolean var3;
      if(var1 * 160 / var2 < 600) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
