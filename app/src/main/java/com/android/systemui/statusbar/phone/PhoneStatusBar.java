package com.android.systemui.statusbar.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

import com.android.systemui.DemoMode;
import com.android.systemui.R;
import com.android.systemui.statusbar.BaseStatusBar;
import com.android.systemui.statusbar.policy.BatteryController;
import com.asus.systemui.util.SystemUiUtil;
import com.bamzzz.ComotID;

import java.util.ArrayList;

/**
 * Created by bamzzz on 06 Jan 2016.
 */
public class PhoneStatusBar extends BaseStatusBar implements DemoMode {
	private static final String BATTERY_SAVER_MODE_COLOR = "battery_saver_mode_color";
	private static final java.lang.String TAG = "TAG";
	private PhoneStatusBar.ThemeChangedCallback mThemeCallback;

	BatteryController mBatteryController;
	//private BatteryMeterView mBatteryView;
	//private BatteryLevelTextView mBatteryTextView;
	// Battery saver bars color
	private int mBatterySaverWarningColor;

	ArrayList<ThemeChangedCallback> mThemeChangedCallbacks;
	private Context mContext;
	private Handler mHandler;
	private HandlerThread mHandlerThread;
	private Object mDozeServiceHost;
	private int mState;
	private int mNaturalBarHeight;
    StatusBarWindowView mStatusBarWindow;
    PhoneStatusBarView mStatusBarView;

    public int getBarState() {
		return this.mState;
	}

	public StatusBarWindowView getStatusBarWindow() {
		return null;
	}

	public int getStatusBarHeight() {
		if(this.mNaturalBarHeight < 0) {
			this.mNaturalBarHeight = this.mContext.getResources().getDimensionPixelSize(SystemUiUtil.getIdentifier(this.mContext, "dimen", "status_bar_height"));
		}

		return this.mNaturalBarHeight;
	}

	public void onHintFinished() {
	}

	public boolean panelsEnabled() {
		return true;
	}

	public boolean isDoubleTapEnabled() {
		return true;
	}

	public boolean interceptTouchEvent(MotionEvent var1) {
		return false;
	}

	public void makeExpandedVisible(boolean b) {
	}

	public boolean hasDoubleTapFeature() {
		return false;
	}

	public void makeExpandedInvisible() {
	}

    @Override
    public void dispatchDemoCommand(String command, Bundle args) {

    }

    class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}

		//@Override
		protected void observe() {
			//super.observe();

			ContentResolver resolver = mContext.getContentResolver();
			resolver.registerContentObserver(Settings.System.getUriFor(BATTERY_SAVER_MODE_COLOR), false, this);
			update();
		}

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			super.onChange(selfChange, uri);
			if (uri.equals(Settings.System.getUriFor(
					BATTERY_SAVER_MODE_COLOR))) {
				mBatterySaverWarningColor = Settings.System.getInt(
						mContext.getContentResolver(),
						BATTERY_SAVER_MODE_COLOR, 1);
				if (mBatterySaverWarningColor != 0) {
					mBatterySaverWarningColor = mContext.getResources()
							.getColor(R.color.battery_saver_mode_color);
				}
			}
			update();
		}

		//@Override
		protected void unobserve() {
			//super.unobserve();
			ContentResolver resolver = mContext.getContentResolver();
			resolver.unregisterContentObserver(this);
		}

		//@Override
		public void update() {
			ContentResolver resolver = mContext.getContentResolver();
			int mode = Settings.System.getInt(mContext.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}
	}

	protected void makeStatusBarView() {
		final Context context = mContext;

		Resources res = context.getResources();

        mStatusBarWindow = (StatusBarWindowView) View.inflate(context,
                R.layout.super_status_bar, null);

        mStatusBarView = (PhoneStatusBarView) mStatusBarWindow.findViewById(R.id.status_bar);
        mStatusBarView.setBar(this);
		/*mStatusBarWindowContent = (FrameLayout) View.inflate(context,
				R.layout.super_status_bar, null);
		mStatusBarWindow.setService(this);
		mStatusBarWindowContent.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				checkUserAutohide(v, event);
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (mExpandedVisible) {
						animateCollapsePanels();
					}
				}
				return mStatusBarWindowContent.onTouchEvent(event);
			}
		});
		mStatusBarView = (PhoneStatusBarView) mStatusBarWindowContent.findViewById(R.id.status_bar);
		mStatusBarView.setBar(this);*/

		mBatterySaverWarningColor = Settings.System.getInt(
				mContext.getContentResolver(),
				BATTERY_SAVER_MODE_COLOR, 1);
		if (mBatterySaverWarningColor != 0) {
			mBatterySaverWarningColor = mContext.getResources()
					.getColor(R.color.battery_saver_mode_color);
		}

		/*mHandlerThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
		mHandlerThread.start();*/
		//mBatteryController = new BatteryController(mContext, mHandler);
		mBatteryController = new BatteryController(mContext);
		mBatteryController.addStateChangedCallback(new BatteryController.BatteryStateChangeCallback() {
			@Override
			public void onPowerSaveChanged() {
				/*mHandler.post(mCheckBarModes);
				if (mDozeServiceHost != null) {
					mDozeServiceHost.firePowerSaveChanged(mBatteryController.isPowerSave());
				}*/
			}

			@Override
			public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
				// noop
			}

			@Override
			public void onBatteryStyleChanged(int style, int percentMode) {
				// noop
			}
//
			@Override
			public void onBatteryAneChanged(int batteryStyle, int batteryAlign) {
				// noop
			}

			@Override
			public void onBatteryBamzzzChanged(int batteryColor, int batteryScale, int percentColor, int percentSize) {
				// noop
			}

		});

		//mHeader.setBatteryController(mBatteryController);
		//BatteryMeterView batteryMeterView = ((BatteryMeterView) mStatusBarView.findViewById(R.id.battery_cm));
		//batteryMeterView.setBatteryController(mBatteryController);
		//batteryMeterView.setAnimationsEnabled(false);
		//((BatteryLevelTextView) mStatusBarView.findViewById(R.id.battery_level_text))
		//		.setBatteryController(mBatteryController);
		//mKeyguardStatusBar.setBatteryController(mBatteryController);
        //LinearLayout batteryContainer = (LinearLayout) mStatusBarView.findViewById(R.id.battery_container);
        //mBatteryViewManager = new BatteryViewManager(mContext, batteryContainer, mStatusBarView.getBarTransitions(), null);
        //mBatteryViewManager.setBatteryController(mBatteryController);
        //mKeyguardStatusBar.setBatteryController(mBatteryController);

	}

	/*private final Runnable mCheckBarModes = new Runnable() {
		@Override
		public void run() {
			checkBarModes();
		}
	};

	private void checkBarMode(int mode, int windowState, BarTransitions transitions,
							  boolean noAnimation) {
		final boolean powerSave = mBatteryController.isPowerSave();
		final boolean anim = !noAnimation && mDeviceInteractive
				&& windowState != WINDOW_STATE_HIDDEN && !powerSave;
		if (powerSave && getBarState() == StatusBarState.SHADE) {
			mode = MODE_WARNING;
		}
		if (mode == MODE_WARNING) {
			transitions.setWarningColor(mBatterySaverWarningColor);
		}
		transitions.transitionTo(mode, anim);
	}
*/

	private void updateQSView() {};

	void setStatusbarBattery() {
		mStatusBarView.findViewById(ComotID.Get("battery", "id")).setVisibility(View.GONE);
		mStatusBarView.findViewById(ComotID.Get("battery_bamzzz","id")).setVisibility(View.GONE);
		mStatusBarView.findViewById(ComotID.Get("battery_cm1","id")).setVisibility(View.GONE);
		mStatusBarView.findViewById(ComotID.Get("battery_cm2","id")).setVisibility(View.GONE);
		int BatteryPosition = Settings.System.getInt(mContext.getContentResolver(), "battery_cluster_position", 1);
		int BatteryStyle = Settings.System.getInt(mContext.getContentResolver(), "battery_cluster_style", 1);
		int bat_id = 1;
		if (BatteryStyle == 1) {
			if(BatteryPosition != 1 && BatteryPosition == 2) {
				bat_id = ComotID.Get("battery","id");
			} else {
				bat_id = ComotID.Get("battery_bamzzz","id");
			}
		}
		if (BatteryStyle == 2) {
			if(BatteryPosition != 1 && BatteryPosition == 2) {
				bat_id = ComotID.Get("battery_cm1","id");
			} else {
				bat_id = ComotID.Get("battery_cm2","id");
			}
		}

		this.mStatusBarView.findViewById(ComotID.Get("bat_id","id")).setVisibility(View.VISIBLE);
	}

	public void addThemeChangedCallback(PhoneStatusBar.ThemeChangedCallback var1) {
		this.mThemeChangedCallbacks.add(var1);
	}

	public void removeThemeChangedCallback(PhoneStatusBar.ThemeChangedCallback var1) {
		this.mThemeChangedCallbacks.remove(var1);
	}

	private class MyThemeChangedCallback implements PhoneStatusBar.ThemeChangedCallback {
		private MyThemeChangedCallback() {
			super();
		}

		// $FF: synthetic method
		MyThemeChangedCallback(Object var2) {
			this();
		}

		public void onThemeChanged() {
			PhoneStatusBar.this.updateQSView();
		}
	}

	public interface ThemeChangedCallback {
		void onThemeChanged();
	}
}
