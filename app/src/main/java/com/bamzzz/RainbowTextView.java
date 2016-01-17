package com.bamzzz;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by bamzzz on 08 Jan 2016.
 *
 * Source from https://github.com/chiuki
 *
 * Usage:
 * <com.bamzzz.RainbowTextView
 xmlns:android="http://schemas.android.com/apk/res/android"
 xmlns:tools="http://schemas.android.com/tools"
 android:id="@+id/text"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_gravity="center"
 android:gravity="center"
 android:text="@string/rainbow"
 android:textColor="@android:color/black"
 android:textSize="@dimen/text_size_huge"
 android:textStyle="bold"
 tools:context=".RainbowTextActivity"/>
 */

public class RainbowTextView extends TextView {
	public RainbowTextView(Context context) {
		super(context);
	}

	public RainbowTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int[] rainbow = getRainbowColors();
		Shader shader = new LinearGradient(0, 0, 0, w, rainbow,
				null, Shader.TileMode.MIRROR);

		Matrix matrix = new Matrix();
		matrix.setRotate(90);
		shader.setLocalMatrix(matrix);

		getPaint().setShader(shader);
	}

	private int[] getRainbowColors() {
		return new int[] {
				-0x10000,
				-0x5B00,
				-0x100,
				-0xFF8000,
				-0xFFFF01,
				-0xB4FF7E,
				-0x117D12
		};
	}

}
