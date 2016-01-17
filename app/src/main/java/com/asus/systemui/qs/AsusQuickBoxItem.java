package com.asus.systemui.qs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

import com.asus.systemui.util.AsusQuicksettingUtil;
import com.asus.systemui.util.ThemeUtils;
import com.bamzzz.ComotID;

public class AsusQuickBoxItem extends AsusQuickSettingsControllerItem {
	private String mActivityName = "notfound";
	private String mPkgName;
	private boolean pkgIsExist;
	private boolean useActivityName = false;

	public AsusQuickBoxItem(Context var1) {
		super(var1);
		ComotID.init(this.mContext.getPackageName(), this.mContext);
	}

	public void onLocaleChanged() {
		if(this.pkgIsExist) {
			if(this.useActivityName) {
				this.mText.setText(AsusQuicksettingUtil.getInstance(this.mContext).getActivityName(this.mPkgName, this.mActivityName));
			} else {
				this.mText.setText(AsusQuicksettingUtil.getInstance(this.mContext).getAppName(this.mPkgName));
			}
		} else {
			this.mText.setText(this.mTextRes);
		}

	}

	public void onThemeChange() {
		this.updateResources();
	}

	public void setup(int var1, int var2, String var3, int var4, String var5, String var6) {
		super.setup(var1, var2, var3, var4, var5);
		this.mPkgName = var6;
		if(this.useActivityName) {
			var3 = AsusQuicksettingUtil.getInstance(this.mContext).getActivityName(var6, this.mActivityName);
		} else {
			var3 = AsusQuicksettingUtil.getInstance(this.mContext).getAppName(var6);
		}

		if(!var3.equals("notfound")) {
			this.pkgIsExist = true;
			this.mText.setText(var3);
		} else {
			this.useActivityName = false;
		}
        //
		SettingsObserver settingsObserver = new SettingsObserver(new Handler());
		settingsObserver.observe();
		//
		this.updateResources();
	}

	class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}

		void observe() {
			ContentResolver resolver = mContext.getContentResolver();
			resolver.registerContentObserver(Settings.System.getUriFor("toggle_quick_box_color"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("quick_box_color_pressed"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("quick_box_color"), false, this);
		}

		@Override
		public void onChange(boolean selfChange) {
			updateResources();
		}
	}

	void updateResources() {
		super.updateResources();
		ColorStateList var3 = this.mContext.getResources().getColorStateList(ComotID.Get("asus_phone_quickbox_text_color", "drawable"));
		//
		int bamz_cust = Settings.System.getInt(this.mContext.getContentResolver(), "toggle_quick_box_color", 0);
		if(bamz_cust == 1) {
			int[][] posisi = new int[][]{
					new int[]{android.R.attr.state_pressed},
					new int[]{},
			};
			int[] warna = new int[] {
					Settings.System.getInt(this.mContext.getContentResolver(), "quick_box_color_pressed", -0xEA3B67),
					Settings.System.getInt(this.mContext.getContentResolver(), "quick_box_color", -0xEB456F),
			};

			var3 = new ColorStateList(posisi, warna);
		}
		//
	    ThemeUtils var4 = ThemeUtils.getInstance(this.mContext);
		ColorStateList var2 = var3;
		if(var4.isChangeNeeded()) {
			Resources var5 = var4.getThemeRes();
			int var1 = var4.getResId("asus_phone_quickbox_text_color", "drawable");
			var2 = var3;
			if(var4.isValidRes(var1)) {
				var2 = var5.getColorStateList(var1);
				//
				if(bamz_cust == 1) {
					int[][] posisi = new int[][]{
							new int[]{android.R.attr.state_pressed},
							new int[]{},
					};
					int[] warna = new int[] {
							Settings.System.getInt(this.mContext.getContentResolver(), "quick_box_color_pressed", -0xEA3B67),
							Settings.System.getInt(this.mContext.getContentResolver(), "quick_box_color", -0xEB456F),
					};

					var2 = new ColorStateList(posisi, warna);
				}
				//
			}
		}

		this.mText.setTextColor(var2);
	}
}
