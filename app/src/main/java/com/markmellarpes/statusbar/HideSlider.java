package com.markmellarpes.statusbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.serajr.utils.ResourceUtils;

public class HideSlider extends ImageView {
   private Context mContext;

   public HideSlider(final Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      ImageView var3 = (ImageView)this.findViewById(ResourceUtils.getIdResId("hide_sliders"));
      var3.setImageResource(ResourceUtils.getDrawableResId("switch_on"));
      if(Boolean.valueOf(var1.getSharedPreferences("BamzzzPrefsFile", 0).getBoolean("SliderVisibility", false)).booleanValue()) {
         var3.setSelected(false);
      } else {
         var3.setSelected(true);
      }

      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            Intent var2;
            Editor var3;
            if(var1x.isSelected()) {
               var1x.setSelected(false);
               var2 = new Intent();
               var2.setAction("com.bamzzz.statusbar.HIDE_SLIDER");
               var1.sendBroadcast(var2);
               var3 = var1.getSharedPreferences("BamzzzPrefsFile", 0).edit();
               var3.putBoolean("SliderVisibility", true);
               var3.commit();
            } else {
               var1x.setSelected(true);
               var2 = new Intent();
               var2.setAction("com.bamzzz.statusbar.UNHIDE_SLIDER");
               var1.sendBroadcast(var2);
               var3 = var1.getSharedPreferences("BamzzzPrefsFile", 0).edit();
               var3.putBoolean("SliderVisibility", false);
               var3.commit();
            }

         }
      });
   }
}
