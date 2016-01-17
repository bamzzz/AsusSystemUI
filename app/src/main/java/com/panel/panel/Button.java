package com.panel.panel;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.serajr.utils.ResourceUtils;

public class Button extends ImageView {
   private Context mContext;

   public Button(final Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
      ImageView var3 = (ImageView)this.findViewById(ResourceUtils.getIdResId("switch_panel_button"));
      var3.setImageResource(ResourceUtils.getDrawableResId("ic_notify_switch"));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            Intent var2;
            if(var1x.isSelected()) {
               var1x.setSelected(false);
               var2 = new Intent();
               var2.setAction("com.bamzzz.panel.FLIP_TO_SLIDER");
               var1.sendBroadcast(var2);
            } else {
               var1x.setSelected(true);
               var2 = new Intent();
               var2.setAction("com.bamzzz.panel.FLIP_TO_PANEL");
               var1.sendBroadcast(var2);
            }

         }
      });
   }
}
