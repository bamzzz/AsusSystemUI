package com.android.systemui;

import android.view.View;
import android.widget.TextView;

/**
 * Created by bamzzz on 10 Jan 2016.
 */
public class FontSizeUtils {
	public static void updateFontSize(View var0, int var1, int var2) {
		updateFontSize((TextView)var0.findViewById(var1), var2);
	}

	public static void updateFontSize(TextView var0, int var1) {
		if(var0 != null) {
			var0.setTextSize(0, (float)var0.getResources().getDimensionPixelSize(var1));
		}

	}
}
