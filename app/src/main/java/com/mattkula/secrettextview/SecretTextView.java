
/*

https://github.com/matthewrkula/SecretTextView

How To Use

Use it just like a normal TextView for the most part.

    <com.mattkula.secrettextview.SecretTextView
            android:id="@+id/textview"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:text="This is a demo of Matt Kula's Secret TextView"
            android:textSize="30sp"
            android:textColor="@android:color/black"
            />

To fade in or fade out the text:

secretTextView.show();    // fade in
secretTextView.hide();    // fade out
secretTextView.toggle();  // fade in or out depending on current state

To change the duration of the fading (default is 2.5 seconds):

secretTextView.setDuration(3000);     // set fade duration to 3 seconds

To set visibility without fading in or out:

secretTextView.setIsVisible(true);
secretTextView.setIsVisible(false);

*/

package com.mattkula.secrettextview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by matt on 5/27/14.
 */
public class SecretTextView extends TextView {
	private String mTextString;
	private SpannableString mSpannableString;

	private double[] mAlphas;
	private MutableForegroundColorSpan[] mSpans;
	private boolean mIsVisible;
	private boolean mIsTextResetting = false;
	private int mDuration = 2500;

	ValueAnimator animator;
	ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
		@Override
		public void onAnimationUpdate(ValueAnimator valueAnimator) {
			Float percent = (Float)valueAnimator.getAnimatedValue();
			resetSpannableString(mIsVisible ? percent : 2.0f - percent);
		}
	};

	public SecretTextView(Context context) {
		super(context);
		init();
	}

	public SecretTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		this.mIsVisible = false;
		animator = ValueAnimator.ofFloat(0.0f, 2.0f);
		animator.addUpdateListener(listener);
		animator.setDuration(mDuration);
	}

	public void toggle(){
		if (mIsVisible) {
			hide();
		} else {
			show();
		}
	}

	public void show(){
		mIsVisible = true;
		animator.start();
	}

	public void hide(){
		mIsVisible = false;
		animator.start();
	}

	public void setIsVisible(boolean isVisible){
		mIsVisible = isVisible;
		resetSpannableString(isVisible == true ? 2.0f : 0.0f);
	}

	public boolean getIsVisible(){
		return mIsVisible;
	}

	private void resetSpannableString(double percent){
		mIsTextResetting = true;

		int color = getCurrentTextColor();
		for (int i = 0; i < this.mTextString.length(); i++) {
			MutableForegroundColorSpan span = mSpans[i];
			span.setColor(Color.argb(clamp(mAlphas[i] + percent), Color.red(color), Color.green(color), Color.blue(color)));
		}

		setText(mSpannableString);

		mIsTextResetting = false;
	}

	private void resetAlphas(int length){
		mAlphas = new double[length];
		for(int i=0; i < mAlphas.length; i++){
			mAlphas[i] = Math.random()-1;
		}
	}

	private void resetIfNeeded(){
		if (!mIsTextResetting){
			mTextString = getText().toString();
			mSpannableString = new SpannableString(this.mTextString);
			mSpans = new MutableForegroundColorSpan[this.mTextString.length()];
			for (int i = 0; i < this.mTextString.length(); i++) {
				MutableForegroundColorSpan span = new MutableForegroundColorSpan();
				mSpannableString.setSpan(span, i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				mSpans[i] = span;
			}
			resetAlphas(mTextString.length());
			resetSpannableString(mIsVisible ? 2.0f : 0);
		}
	}

	public void setText(String text) {
		super.setText(text);
		resetIfNeeded();
	}

	@Override
	public void setText(CharSequence text, TextView.BufferType type) {
		super.setText(text, type);
		resetIfNeeded();
	}

	private int clamp(double f){
		return (int)(255*Math.min(Math.max(f, 0), 1));
	}

	public void setDuration(int duration){
		this.mDuration = duration;
		animator.setDuration(duration);
	}
}