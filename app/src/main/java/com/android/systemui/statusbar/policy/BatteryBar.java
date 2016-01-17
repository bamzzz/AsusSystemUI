package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.BatteryManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BatteryBar extends RelativeLayout implements Animatable {

    private static final String TAG = BatteryBar.class.getSimpleName();

    // Total animation duration
    private static final int ANIM_DURATION = 1000; // 5 seconds
	private static final String STATUSBAR_BATTERY_BAR = "battery_bar";
	private static final String STATUSBAR_BATTERY_BAR_COLOR = "battery_bar_color";
	private static final String STATUSBAR_BATTERY_BAR_ANIMATE = "battery_bar_animate";
	private static final String STATUSBAR_BATTERY_BAR_TOGGLE_STATIC_COLOR = "battery_bar_toggle_static_color";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_15 = "battery_bar_static_color_15";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_30 = "battery_bar_static_color_30";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_45 = "battery_bar_static_color_45";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_60 = "battery_bar_static_color_60";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_75 = "battery_bar_static_color_75";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_90 = "battery_bar_static_color_90";
	private static final String STATUSBAR_BATTERY_BAR_STATIC_COLOR_100 = "battery_bar_static_color_100";
	private static final String STATUSBAR_BATTERY_BAR_RANDOM_COLOR = "battery_bar_random_color";
    private static final String STATUSBAR_BATTERY_BAR_RANDOM_DELAY = "battery_bar_random_delay";
    private static final String STATUSBAR_BATTERY_BAR_RANDOM_PERIOD = "battery_bar_random_period";


	private boolean mAttached = false;
    private int mBatteryLevel = 0;
    private int mChargingLevel = -1;
    private boolean mBatteryCharging = false;
    private boolean shouldAnimateCharging = true;
    private boolean isAnimating = false;

    private Handler mHandler = new Handler();

    LinearLayout mBatteryBarLayout;
    View mBatteryBar;

    LinearLayout mChargerLayout;
    View mCharger;

    public static final int STYLE_REGULAR = 0;
    public static final int STYLE_SYMMETRIC = 1;

    boolean vertical = false;
	private Context mContext;
    private TimerTask second;
    private Handler handler;

    class SettingsObserver extends ContentObserver {

        public SettingsObserver(Handler handler) {
            super(handler);
        }

        void observer() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR), false, this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_COLOR), false,
                    this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_ANIMATE),
                    false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_TOGGLE_STATIC_COLOR),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_15),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_30),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_45),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_60),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_75),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_90),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_STATIC_COLOR_100),
					false, this);
			resolver.registerContentObserver(
					Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_RANDOM_COLOR),
					false, this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_RANDOM_DELAY),
                    false, this);
            resolver.registerContentObserver(
                    Settings.System.getUriFor(STATUSBAR_BATTERY_BAR_RANDOM_PERIOD),
                    false, this);
		}

        @Override
        public void onChange(boolean selfChange) {
            updateSettings();
        }
    }

    public BatteryBar(Context context) {
        this(context, null);
    }

    public BatteryBar(Context context, boolean isCharging, int currentCharge) {
        this(context, null);

        mBatteryLevel = currentCharge;
        mBatteryCharging = isCharging;
    }

    public BatteryBar(Context context, boolean isCharging, int currentCharge, boolean isVertical) {
        this(context, null);

        mBatteryLevel = currentCharge;
        mBatteryCharging = isCharging;
        vertical = isVertical;
    }

    public BatteryBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		this.mContext = context;
        this.handler = new Handler();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;

            mBatteryBarLayout = new LinearLayout(mContext);
            addView(mBatteryBarLayout, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            mBatteryBar = new View(mContext);
            mBatteryBarLayout.addView(mBatteryBar, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            float dp = 4f;
            int pixels = (int) (metrics.density * dp + 0.5f);

            // charger
            mChargerLayout = new LinearLayout(mContext);

            if (vertical)
                addView(mChargerLayout, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        pixels));
            else
                addView(mChargerLayout, new RelativeLayout.LayoutParams(pixels,
                        LayoutParams.MATCH_PARENT));

            mCharger = new View(mContext);
            mChargerLayout.setVisibility(View.GONE);
            mChargerLayout.addView(mCharger, new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            getContext().registerReceiver(mIntentReceiver, filter, null, getHandler());

            SettingsObserver observer = new SettingsObserver(mHandler);
            observer.observer();
            updateSettings();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            mAttached = false;
            getContext().unregisterReceiver(mIntentReceiver);
        }
    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                mBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                mBatteryCharging = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0) == BatteryManager.BATTERY_STATUS_CHARGING;
                if (mBatteryCharging && mBatteryLevel < 100) {
                    start();
                } else {
                    stop();
                }
                setProgress(mBatteryLevel);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                stop();
            } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                if (mBatteryCharging && mBatteryLevel < 100) {
                    start();
                }
            }
        }
    };

    private void updateSettings() {
        ContentResolver resolver = getContext().getContentResolver();

        int color = Settings.System.getInt(resolver, STATUSBAR_BATTERY_BAR_COLOR, 0xFF33B5E5);

        shouldAnimateCharging = Settings.System.getInt(resolver,
                STATUSBAR_BATTERY_BAR_ANIMATE, 0) == 1;

        if (mBatteryCharging && mBatteryLevel < 100 && shouldAnimateCharging) {
            start();
        } else {
            stop();
        }
        setProgress(mBatteryLevel);

		// MOD
        long delay = Settings.System.getInt(resolver, STATUSBAR_BATTERY_BAR_RANDOM_DELAY, 1000);
        long period = Settings.System.getInt(resolver, STATUSBAR_BATTERY_BAR_RANDOM_PERIOD, 1000);
		int colorstatic = Settings.System.getInt(resolver, STATUSBAR_BATTERY_BAR_TOGGLE_STATIC_COLOR, 0);
        int acak_acak = Settings.System.getInt(resolver, STATUSBAR_BATTERY_BAR_RANDOM_COLOR, 0);

		/*if(colorstatic == 1){
			int batere = mBatteryLevel;
			if (batere <= 15) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_15, 0xFFFF0000);
			}
			if (batere > 15 && batere <= 30) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_30, 0xFFAA0000);
			}
			if (batere > 30 && batere <= 45) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_45, 0xFFFF00DD);
			}
			if (batere > 45 && batere <= 60) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_60, 0xFFFFCCAA);
			}
			if (batere > 60 && batere <= 75) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_75, 0xFFFF00AA);
			}
			if (batere > 75 && batere <= 90) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_90, 0xFFFFAA00);
			}
			if (batere > 90 && batere <= 100) {
				color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_100, 0xFFFFFF00);
			}
			mBatteryBar.setBackgroundColor(color);
			mCharger.setBackgroundColor(color);
		} else {
            mBatteryBar.setBackgroundColor(color);
            mCharger.setBackgroundColor(color);
        }*/
        switch(acak_acak){
            case 0:
                mBatteryBar.setBackgroundColor(color);
                mCharger.setBackgroundColor(color);
                break;
            case 1:
                /*Timer timer = new Timer();
				MyTimer mt = new MyTimer();
				timer.schedule(mt, 1000, 1000);*/
                this.second = new TimerTask() {
                    public void run() {
                        Runnable moveOn_Mblo = new Runnable() {
                            public void run() {
                                Random rand = new Random();
                                // generate the random integers for r, g and b value
                                int r = rand.nextInt(255);
                                int g = rand.nextInt(255);
                                int b = rand.nextInt(255);
                                int randomColor = Color.rgb(r, g, b);
                                mBatteryBar.setBackgroundColor(randomColor);
                                mCharger.setBackgroundColor(randomColor);
                                //mBatteryBar.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                                //mCharger.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                                //Clock.this.updateClock();
                            }
                        };
                        BatteryBar.this.handler.post(moveOn_Mblo);
                    }
                };
                // schedule(TimerTask task,long delay,long period)
                //(new Timer()).schedule(this.second, 0L, 1001L);
                (new Timer()).schedule(this.second, delay, period);
                break;
        }
        invalidate();
		//mBatteryBar.setBackgroundColor(color);
		//mCharger.setBackgroundColor(color);
	}

	class MyTimer extends TimerTask {
		public void run() {
			//This runs in a background thread.
			//We cannot call the UI from this thread, so we must call the main UI thread and pass a runnable
			//runOnUiThread(new Runnable() {
				//public void run() {
					Random rand = new Random();
					//The random generator creates values between [0,256) for use as RGB values used below to create a random color
					//We call the RelativeLayout object and we change the color.  The first parameter in argb() is the alpha.
					//mealLayout.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					mBatteryBar.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
					mCharger.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
				}
			//});
		//}
	}


	private int ambilWarnaStatis(){
		int batere = mBatteryLevel;
		int color = 0;
		if (batere <= 15) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_15, 0xFF33B5E5);
		} else if (batere > 15 || batere <= 30) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_30, 0xFF33B5E5);
		} else if (batere > 30 || batere <= 45) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_45, 0xFF33B5E5);
		} else if (batere > 45 || batere <= 60) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_60, 0xFF33B5E5);
		} else if (batere > 60 || batere <= 75) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_75, 0xFF33B5E5);
		} else if (batere > 75 || batere <= 90) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_90, 0xFF33B5E5);
		} else if (batere > 90 || batere <= 100) {
			color = Settings.System.getInt(getContext().getContentResolver(), STATUSBAR_BATTERY_BAR_STATIC_COLOR_100, 0xFF33B5E5);
		}
		return color;
	}

    private void setProgress(int n) {
        if (vertical) {
            int w = (int) (((getHeight() / 100.0) * n) + 0.5);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBatteryBarLayout
                    .getLayoutParams();
            params.height = w;
            mBatteryBarLayout.setLayoutParams(params);

        } else {
            int w = (int) (((getWidth() / 100.0) * n) + 0.5);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBatteryBarLayout
                    .getLayoutParams();
            params.width = w;
            mBatteryBarLayout.setLayoutParams(params);
        }

    }

    @Override
    public void start() {
        if (!shouldAnimateCharging)
            return;

        if (vertical) {
            TranslateAnimation a = new TranslateAnimation(getX(), getX(), getHeight(),
                    mBatteryBarLayout.getHeight());
            a.setInterpolator(new AccelerateInterpolator());
            a.setDuration(ANIM_DURATION);
            a.setRepeatCount(Animation.INFINITE);
            mChargerLayout.startAnimation(a);
            mChargerLayout.setVisibility(View.VISIBLE);
        } else {
            TranslateAnimation a = new TranslateAnimation(getWidth(), mBatteryBarLayout.getWidth(),
                    getTop(), getTop());
            a.setInterpolator(new AccelerateInterpolator());
            a.setDuration(ANIM_DURATION);
            a.setRepeatCount(Animation.INFINITE);
            mChargerLayout.startAnimation(a);
            mChargerLayout.setVisibility(View.VISIBLE);
        }
        isAnimating = true;
    }

    @Override
    public void stop() {
        mChargerLayout.clearAnimation();
        mChargerLayout.setVisibility(View.GONE);
        isAnimating = false;
    }

    @Override
    public boolean isRunning() {
        return isAnimating;
    }

}