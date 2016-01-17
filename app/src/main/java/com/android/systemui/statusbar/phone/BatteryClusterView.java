package com.android.systemui.statusbar.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.systemui.CustomizationItem;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.SplitClockView;
import com.asus.systemui.ReflectionMethods;
import com.bamzzz.ComotID;

//import android.os.SystemProperties;

public class BatteryClusterView extends LinearLayout {
	private static final int BATTERY_STATUS_NOT_QUICK_CHARGING;
	private static final int BATTERY_STATUS_QUICK_CHARGING;
	private static final int BATTERY_STATUS_QUICK_CHARGING_V1;
	private static final boolean DEBUG;
	private static Handler mHandler;
	private ImageView mBatteryIconView;
	private TextView mBatteryLevelView;
	private boolean mBlueToothDockCharging = false;
	private int mBlueToothDockLevel = -1;
	private ImageView mBluetoothDockBatteryIconView;
	private TextView mBluetoothDockBatteryLevelView;
	private ImageView mDockBatteryChargerView;
	private ImageView mDockBatteryIconView;
	private TextView mDockBatteryLevelView;
	private int mDockIcon = 0;
	private int mDockLevel = -1;
	private int mDockStatus = 1;
	private StatusBarHeaderView mHeader;
	private int mIcon = 0;
	private boolean mIsBlueToothDockPresent = false;
	private boolean mIsCurrentPenPresent = false;
	private boolean mIsPackPresent = false;
	private boolean mIsPadPresent = false;
	private boolean mIsPenPresent = false;
	private int mLevel = -1;
	private boolean mLevelEnabled = false;
	private ContentObserver mObserver = new ContentObserver(new Handler()) {
		public void onChange(boolean var1) {
			var1 = true;
			BatteryClusterView var2 = BatteryClusterView.this;
			if(System.getInt(BatteryClusterView.this.getContext().getContentResolver(), "show_battery", 0) != 1) {
				var1 = false;
			}

			var2.mLevelEnabled = var1;
			BatteryClusterView.this.updatePercentage();

            int icon = System.getInt(BatteryClusterView.this.getContext().getContentResolver(), "battery_icon_style", 0);
            getIcon(icon);
            show_views();
		}
	};
	private ImageView mPackBatteryChargerView;
	private ImageView mPackBatteryIconView;
	private TextView mPackBatteryLevelView;
	private int mPackIcon = 0;
	private int mPackLevel = -1;
	private int mPackStatus = 1;
	private int mPackType = -1;
	private ImageView mPadBatteryChargerView;
	private ImageView mPadBatteryIconView;
	private TextView mPadBatteryLevelView;
	private int mPadIcon = 0;
	private int mPadLevel = -1;
	private int mPadStatus = 1;
	private ImageView mPenCapacityView;
	private int mPenLevel = -1;
	private int mPlugType = 0;
	private Runnable mRunnable = new Runnable() {
		public void run() {
			BatteryClusterView.this.mPenCapacityView.setVisibility(View.GONE);
		}
	};
	private int mStatus = 1;
	private PhoneStatusBarView mStatusBarView;
	BatteryClusterView.BatteryTracker mTracker = new BatteryClusterView.BatteryTracker(null);

	static {
		boolean var0 = true;
		//if(SystemProperties.getInt("ro.debuggable", 0) != 1) {
			var0 = false;
		//}

		DEBUG = var0;
		BATTERY_STATUS_QUICK_CHARGING_V1 = ReflectionMethods.getBatteryStatusQuickChargingV1();
		BATTERY_STATUS_QUICK_CHARGING = ReflectionMethods.getBatteryStatusQuickCharging();
		BATTERY_STATUS_NOT_QUICK_CHARGING = ReflectionMethods.getBatteryStatusNotQuickCharging();
		mHandler = new Handler();
	}

	public BatteryClusterView(Context var1) {
		super(var1);
	}

	public BatteryClusterView(Context var1, AttributeSet var2) {
		super(var1, var2);
		ComotID.init(var1.getPackageName(), var1);
	}

	private final int getDockIcon(int var1) {
		if(var1 != 3 && var1 != 4 && var1 != 5) {
			if(var1 != 2 && var1 != BATTERY_STATUS_QUICK_CHARGING_V1) {
				var1 = ComotID.Get("asus_ep_kb_battery_unknown","drawable");
			} else {
				var1 = CustomizationItem.STAT_SYS_DOCK_BATTERY_CHARGE;
			}
		} else {
			var1 = CustomizationItem.STAT_SYS_DOCK_BATTERY;
		}

		return var1;
	}

	private int getIcon(int var1) {
		if(var1 != BATTERY_STATUS_QUICK_CHARGING && var1 != BATTERY_STATUS_NOT_QUICK_CHARGING) {
			if(var1 != 3 && var1 != 4 && var1 != 5) {
				if(var1 != 2 && var1 != BATTERY_STATUS_QUICK_CHARGING_V1) {
					var1 = ComotID.Get("stat_sys_battery_unknown","drawable");
				} else {
					var1 = CustomizationItem.STAT_SYS_BATTERY_CHARGE;
				}
			} else {
				var1 = CustomizationItem.STAT_SYS_BATTERY;
			}
		} else {
			var1 = CustomizationItem.STAT_SYS_BATTERY_QUICK_CHARGE;
		}

		return var1;
	}

	private final int getPackIcon(int var1) {
		if(var1 != 3 && var1 != 4 && var1 != 5) {
			if(var1 != 2 && var1 != BATTERY_STATUS_QUICK_CHARGING_V1) {
				var1 = ComotID.Get("stat_sys_pack_battery","drawable");
			} else {
				var1 = ComotID.Get("stat_sys_pack_battery_charge","drawable");
			}
		} else {
			var1 = ComotID.Get("asus_ep_accessories_battery_unknown","drawable");
		}

		return var1;
	}

	private final int getPadIcon(int var1) {
		if(var1 != 3 && var1 != 4 && var1 != 5) {
			if(var1 != 2 && var1 != BATTERY_STATUS_QUICK_CHARGING_V1) {
				var1 = ComotID.Get("asus_ep_pad_battery_unknown","drawable");
			} else {
				var1 = CustomizationItem.STAT_SYS_PAD_BATTERY_CHARGE;
			}
		} else {
			var1 = CustomizationItem.STAT_SYS_PAD_BATTERY;
		}

		return var1;
	}

	private void updatePercentage() {
		if(this.mLevelEnabled && this.mLevel >= 0) {
			this.mBatteryLevelView.setVisibility(View.VISIBLE);
			this.mBatteryLevelView.setText(this.mLevel + "%");
		} else {
			this.mBatteryLevelView.setVisibility(View.GONE);
		}

		if(this.mIsPadPresent && this.mLevelEnabled && this.mPadLevel >= 0) {
			this.mPadBatteryLevelView.setVisibility(View.VISIBLE);
			this.mPadBatteryLevelView.setText(this.mPadLevel + "%");
		} else {
			this.mPadBatteryLevelView.setVisibility(View.GONE);
		}

		if(this.mDockStatus != 1 && this.mLevelEnabled && this.mDockLevel >= 0) {
			this.mDockBatteryLevelView.setVisibility(View.VISIBLE);
			this.mDockBatteryLevelView.setText(this.mDockLevel + "%");
		} else {
			this.mDockBatteryLevelView.setVisibility(View.GONE);
		}

		if(this.mIsBlueToothDockPresent && this.mLevelEnabled && this.mBlueToothDockLevel >= 0) {
			this.mBluetoothDockBatteryLevelView.setVisibility(View.VISIBLE);
			this.mBluetoothDockBatteryLevelView.setText(this.mBlueToothDockLevel + "%");
		} else {
			this.mBluetoothDockBatteryLevelView.setVisibility(View.GONE);
		}

		if(this.mIsPackPresent && this.mLevelEnabled && this.mPackLevel >= 0) {
			this.mPackBatteryLevelView.setVisibility(View.VISIBLE);
			this.mPackBatteryLevelView.setText(this.mPackLevel + "%");
		} else {
			this.mPackBatteryLevelView.setVisibility(View.GONE);
		}

	}

	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		IntentFilter var1 = new IntentFilter();
		var1.addAction("android.intent.action.BATTERY_CHANGED");
		var1.addAction("android.intent.action.CONFIGURATION_CHANGED");
		var1.addAction("android.asus.action.BT_DOCK_BATTERY_CHANGED");
		var1.addAction("android.intent.action.USER_SWITCHED");
		this.getContext().registerReceiver(this.mTracker, var1);
		this.mObserver.onChange(false);
		this.getContext().getContentResolver().registerContentObserver(System.getUriFor("show_battery"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("enable_battery_icon"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("battery_icon_color"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("battery_text_color"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("battery_text_size"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("battery_icon_style"), false, this.mObserver);
        this.getContext().getContentResolver().registerContentObserver(System.getUriFor("battery_icon_scale"), false, this.mObserver);
	}

	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		this.getContext().unregisterReceiver(this.mTracker);
		this.getContext().getContentResolver().unregisterContentObserver(this.mObserver);
		mHandler.removeCallbacks(this.mRunnable);
		if(this.mBatteryIconView.getDrawable() != null) {
			this.mBatteryIconView.getDrawable().setCallback((Callback)null);
			this.mBatteryIconView.setImageDrawable((Drawable)null);
		}

		if(this.mPadBatteryIconView.getDrawable() != null) {
			this.mPadBatteryIconView.getDrawable().setCallback((Callback)null);
			this.mPadBatteryIconView.setImageDrawable((Drawable)null);
		}

		if(this.mDockBatteryIconView.getDrawable() != null) {
			this.mDockBatteryIconView.getDrawable().setCallback((Callback)null);
			this.mDockBatteryIconView.setImageDrawable((Drawable)null);
		}

		if(this.mPackBatteryIconView.getDrawable() != null) {
			this.mPackBatteryIconView.getDrawable().setCallback((Callback)null);
			this.mPackBatteryIconView.setImageDrawable((Drawable)null);
		}

		if(this.mPadBatteryChargerView.getDrawable() != null) {
			this.mPadBatteryChargerView.getDrawable().setCallback((Callback)null);
			this.mPadBatteryChargerView.setImageDrawable((Drawable)null);
		}

		if(this.mDockBatteryIconView.getDrawable() != null) {
			this.mDockBatteryIconView.getDrawable().setCallback((Callback)null);
			this.mDockBatteryIconView.setImageDrawable((Drawable)null);
		}

		if(this.mPackBatteryChargerView.getDrawable() != null) {
			this.mPackBatteryChargerView.getDrawable().setCallback((Callback)null);
			this.mPackBatteryChargerView.setImageDrawable((Drawable)null);
		}

	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		this.mBatteryIconView = (ImageView)this.findViewById(ComotID.Get("battery","id"));
		this.mBatteryLevelView = (TextView)this.findViewById(ComotID.Get("battery_level","id"));
		this.mPadBatteryIconView = (ImageView)this.findViewById(ComotID.Get("pad_battery","id"));
		this.mPadBatteryLevelView = (TextView)this.findViewById(ComotID.Get("pad_battery_level","id"));
		this.mPadBatteryChargerView = (ImageView)this.findViewById(ComotID.Get("pad_charger","id"));
		this.mDockBatteryIconView = (ImageView)this.findViewById(ComotID.Get("dock_battery","id"));
		this.mDockBatteryLevelView = (TextView)this.findViewById(ComotID.Get("dock_battery_level","id"));
		this.mDockBatteryChargerView = (ImageView)this.findViewById(ComotID.Get("dock_charger","id"));
		this.mPackBatteryIconView = (ImageView)this.findViewById(ComotID.Get("pack_battery","id"));
		this.mPackBatteryLevelView = (TextView)this.findViewById(ComotID.Get("pack_battery_level","id"));
		this.mPackBatteryChargerView = (ImageView)this.findViewById(ComotID.Get("pack_charger","id"));
		this.mPenCapacityView = (ImageView)this.findViewById(ComotID.Get("pen_capacity","id"));
		this.mBluetoothDockBatteryIconView = (ImageView)this.findViewById(ComotID.Get("bluetooth_dock_battery","id"));
		this.mBluetoothDockBatteryLevelView = (TextView)this.findViewById(ComotID.Get("bluetooth_dock_battery_level","id"));
	}

	public void setStatusBarHeaderView(StatusBarHeaderView var1) {
		this.mHeader = var1;
	}

	public void setStatusBarView(PhoneStatusBarView var1) {
		this.mStatusBarView = var1;
	}

	public void show_views() {
		this.mBatteryIconView.setImageDrawable((Drawable)null);
		this.mBatteryIconView.setImageResource(this.mIcon);
		this.mBatteryIconView.setImageLevel(this.mLevel);
		if(this.mIsPadPresent) {
			this.mPadBatteryIconView.setImageDrawable((Drawable)null);
			this.mPadBatteryIconView.setVisibility(View.VISIBLE);
			this.mPadBatteryIconView.setImageResource(this.mPadIcon);
			this.mPadBatteryIconView.setImageLevel(this.mPadLevel);
			this.mPadBatteryChargerView.setImageDrawable((Drawable)null);
			this.mPadBatteryChargerView.setVisibility(View.VISIBLE);
			if((this.mPlugType & 16) != 0) {
				this.mPadBatteryChargerView.setImageResource(ComotID.Get("asus_ep_battery_kb_charger_charging","drawable"));
			} else {
				this.mPadBatteryChargerView.setImageResource(ComotID.Get("asus_ep_battery_kb_charger_idle","drawable"));
			}
		} else {
			this.mPadBatteryIconView.setVisibility(View.GONE);
			this.mPadBatteryChargerView.setVisibility(View.GONE);
		}

		if(this.mDockStatus != 1) {
			this.mDockBatteryIconView.setImageDrawable((Drawable)null);
			this.mDockBatteryIconView.setVisibility(View.VISIBLE);
			this.mDockBatteryIconView.setImageResource(this.mDockIcon);
			this.mDockBatteryIconView.setImageLevel(this.mDockLevel);
			this.mDockBatteryChargerView.setImageDrawable((Drawable)null);
			this.mDockBatteryChargerView.setVisibility(View.VISIBLE);
			if((this.mPlugType & 8) != 0) {
				this.mDockBatteryChargerView.setImageResource(ComotID.Get("","drawable"));
			} else {
				this.mDockBatteryChargerView.setImageResource(ComotID.Get("","drawable"));
			}
		} else {
			this.mDockBatteryIconView.setVisibility(View.GONE);
			this.mDockBatteryChargerView.setVisibility(View.GONE);
		}

		if(this.mIsCurrentPenPresent) {
			if(this.mIsPenPresent != this.mIsCurrentPenPresent) {
				mHandler.removeCallbacks(this.mRunnable);
			}

			this.mPenCapacityView.setImageDrawable((Drawable)null);
			this.mPenCapacityView.setImageResource(ComotID.Get("stat_sys_pen_battery","drawable"));
			this.mPenCapacityView.setImageLevel(this.mPenLevel);
			this.mPenCapacityView.setVisibility(View.VISIBLE);
		} else if(this.mIsPenPresent != this.mIsCurrentPenPresent) {
			mHandler.postDelayed(this.mRunnable, 3000L);
		}

		this.mIsPenPresent = this.mIsCurrentPenPresent;
		if(this.mIsBlueToothDockPresent) {
			this.mBluetoothDockBatteryIconView.setImageDrawable((Drawable)null);
			this.mBluetoothDockBatteryIconView.setVisibility(View.VISIBLE);
			if(this.mBlueToothDockCharging) {
				this.mBluetoothDockBatteryIconView.setImageResource(ComotID.Get("stat_sys_bluetooth_battery","drawable"));
			} else {
				this.mBluetoothDockBatteryIconView.setImageResource(ComotID.Get("stat_sys_bluetooth_battery_charge","drawable"));
			}

			this.mBluetoothDockBatteryIconView.setImageLevel(this.mBlueToothDockLevel);
		} else {
			this.mBluetoothDockBatteryIconView.setVisibility(View.GONE);
		}

		if(this.mIsPackPresent) {
			this.mPackBatteryIconView.setImageDrawable((Drawable)null);
			this.mPackBatteryIconView.setVisibility(View.VISIBLE);
			this.mPackBatteryIconView.setImageResource(this.mPackIcon);
			this.mPackBatteryIconView.setImageLevel(this.mPackLevel);
			if(this.mPackType != 1) {
				this.mPackBatteryChargerView.setVisibility(View.GONE);
			} else {
				this.mPackBatteryChargerView.setImageDrawable((Drawable)null);
				this.mPackBatteryChargerView.setVisibility(View.VISIBLE);
				if((this.mPlugType & 32) != 0) {
					this.mPackBatteryChargerView.setImageResource(ComotID.Get("","drawable"));
				} else {
					this.mPackBatteryChargerView.setImageResource(ComotID.Get("","drawable"));
				}
			}
		} else {
			this.mPackBatteryIconView.setVisibility(View.GONE);
			this.mPackBatteryChargerView.setVisibility(View.GONE);
		}

		this.updatePercentage();
	}

	private class BatteryTracker extends BroadcastReceiver {
		private BatteryTracker() {
			super();
		}

		// $FF: synthetic method
		BatteryTracker(Object var2) {
			this();
		}

		public void onReceive(Context var1, Intent var2) {
			String var3 = var2.getAction();
			if(var3.equals("android.intent.action.BATTERY_CHANGED")) {
				if(BatteryClusterView.this.mStatusBarView != null) {
					((Clock)BatteryClusterView.this.mStatusBarView.findViewById(ComotID.Get("","id"))).updateClock();
				}

				if(BatteryClusterView.this.mHeader != null) {
					((SplitClockView)BatteryClusterView.this.mHeader.findViewById(ComotID.Get("","id"))).updatePatterns();
				}

				BatteryClusterView.this.mLevel = var2.getIntExtra("level", 0);
				BatteryClusterView.this.mStatus = var2.getIntExtra("status", 1);
				BatteryClusterView.this.mPlugType = var2.getIntExtra("plugged", 0);
				BatteryClusterView.this.mIcon = BatteryClusterView.this.getIcon(BatteryClusterView.this.mStatus);
				BatteryClusterView.this.mIsPadPresent = var2.getBooleanExtra("pad_present", false);
				if(BatteryClusterView.this.mIsPadPresent) {
					BatteryClusterView.this.mPadLevel = var2.getIntExtra("pad_level", 0);
					BatteryClusterView.this.mPadStatus = var2.getIntExtra("pad_status", 1);
					BatteryClusterView.this.mPadIcon = BatteryClusterView.this.getPadIcon(BatteryClusterView.this.mPadStatus);
				}

				BatteryClusterView.this.mDockStatus = var2.getIntExtra("dock_status", 1);
				if(BatteryClusterView.this.mDockStatus != 1) {
					BatteryClusterView.this.mDockLevel = var2.getIntExtra("dock_level", 0);
					BatteryClusterView.this.mDockIcon = BatteryClusterView.this.getDockIcon(BatteryClusterView.this.mDockStatus);
				}

				BatteryClusterView.this.mIsCurrentPenPresent = var2.getBooleanExtra("pen_battery_present", false);
				if(BatteryClusterView.this.mIsCurrentPenPresent) {
					BatteryClusterView.this.mPenLevel = var2.getIntExtra("pen_battery_capacity", 0);
					if(BatteryClusterView.this.mPenLevel < 0) {
						BatteryClusterView.this.mPenLevel = 0;
					} else if(BatteryClusterView.this.mPenLevel > 3) {
						BatteryClusterView.this.mPenLevel = 3;
					}
				}

				BatteryClusterView.this.mIsPackPresent = var2.getBooleanExtra("pack_battery_present", false);
				if(BatteryClusterView.this.mIsPackPresent) {
					BatteryClusterView.this.mPackLevel = var2.getIntExtra("pack_battery_level", 0);
					BatteryClusterView.this.mPackStatus = var2.getIntExtra("pack_battery_status", 1);
					BatteryClusterView.this.mPackType = var2.getIntExtra("pack_battery_pack_type", 0);
					BatteryClusterView.this.mPackIcon = BatteryClusterView.this.getPackIcon(BatteryClusterView.this.mPackStatus);
				}

				if(BatteryClusterView.DEBUG) {
					Log.d("BatteryClusterView", "mLevel: " + BatteryClusterView.this.mLevel);
					Log.d("BatteryClusterView", "mStatus: " + BatteryClusterView.this.mStatus);
					Log.d("BatteryClusterView", "mPlugType: " + BatteryClusterView.this.mPlugType);
					if(BatteryClusterView.this.mIsPadPresent) {
						Log.d("BatteryClusterView", "mPadLevel: " + BatteryClusterView.this.mPadLevel);
						Log.d("BatteryClusterView", "mPadStatus: " + BatteryClusterView.this.mPadStatus);
					}

					if(BatteryClusterView.this.mDockStatus != 1) {
						Log.d("BatteryClusterView", "mDockLevel: " + BatteryClusterView.this.mDockLevel);
					}

					if(BatteryClusterView.this.mIsCurrentPenPresent) {
						Log.d("BatteryClusterView", "mPenLevel: " + BatteryClusterView.this.mPenLevel);
					}

					if(BatteryClusterView.this.mIsPackPresent) {
						Log.d("BatteryClusterView", "mPackLevel: " + BatteryClusterView.this.mPadLevel);
						Log.d("BatteryClusterView", "mPackStatus: " + BatteryClusterView.this.mPadStatus);
						Log.d("BatteryClusterView", "mPackType: " + BatteryClusterView.this.mPackType);
					}

					Log.d("BatteryClusterView", "====================================");
				}

				BatteryClusterView.this.show_views();
			} else if(var3.equals("android.asus.action.BT_DOCK_BATTERY_CHANGED")) {
				BatteryClusterView.this.mIsBlueToothDockPresent = var2.getBooleanExtra("bluetooth_dock_battery_present", false);
				BatteryClusterView.this.mBlueToothDockLevel = var2.getIntExtra("bluetooth_dock_battery_level", 0);
				BatteryClusterView.this.mBlueToothDockCharging = var2.getBooleanExtra("bluetooth_dock_battery_charging", false);
				BatteryClusterView.this.show_views();
			} else if(var3.equals("android.intent.action.CONFIGURATION_CHANGED")) {
				FontSizeUtils.updateFontSize(BatteryClusterView.this.mBatteryLevelView, 2131165248);
				FontSizeUtils.updateFontSize(BatteryClusterView.this.mPadBatteryLevelView, 2131165248);
				FontSizeUtils.updateFontSize(BatteryClusterView.this.mDockBatteryLevelView, 2131165248);
			} else if(var3.equals("android.intent.action.USER_SWITCHED")) {
				BatteryClusterView.this.mObserver.onChange(false);
			}

		}
	}
}
