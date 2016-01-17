package com.spacecaker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.serajr.utils.ResourceUtils;

public class ButtonBurst extends Button {
   private Context mContext;
   private boolean mUpdating = false;

   public ButtonBurst(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      this.update();
   }

   private void update() {
      this.setBackgroundColor(16777215);
      Drawable var1 = this.mContext.getResources().getDrawable(ResourceUtils.getDrawableResId("asus_ic_closeall"));
      Drawable var2 = this.mContext.getResources().getDrawable(ResourceUtils.getDrawableResId("ic_qs_certificate_info"));
      if(!this.mUpdating) {
         this.setBackgroundDrawable(var1);
      } else {
         this.setBackgroundDrawable(var2);
      }

      this.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if(ButtonBurst.this.mUpdating) {
               ButtonBurst.this.sendBroadCast(true);
            } else {
               ButtonBurst.this.sendBroadCast(false);
            }

         }
      });
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.update();
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
   }

   void sendBroadCast(boolean var1) {
      Intent var2 = new Intent();
      var2.setAction("com.androidminang.UPDATE");
      var2.putExtra("LEOT_YANG_MANA_NIH", var1);
      this.mContext.sendBroadcast(var2);
      if(var1) {
         var1 = false;
      } else {
         var1 = true;
      }

      this.mUpdating = var1;
      this.update();
   }
}
