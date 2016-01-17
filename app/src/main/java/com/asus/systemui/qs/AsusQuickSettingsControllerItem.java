package com.asus.systemui.qs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asus.systemui.util.ThemeUtils;
import com.bamzzz.ComotID;

public class AsusQuickSettingsControllerItem extends LinearLayout {
	private String mBgString;
	protected CheckBox mCheckbox;
	private OnCheckedChangeListener mCheckboxListener;
	private int mColSpan;
	protected Context mContext;
	private int mDrawableId;
	protected Resources mR;
	private String mSharfKey;
	protected TextView mText;
	protected int mTextRes;

	public AsusQuickSettingsControllerItem(Context var1) {
		this(var1, (AttributeSet)null);
	}

	public AsusQuickSettingsControllerItem(Context var1, AttributeSet var2) {
		this(var1, var2, 0);
	}

	public AsusQuickSettingsControllerItem(Context var1, AttributeSet var2, int var3) {
		super(var1, var2, var3);
		this.mCheckboxListener = new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton var1, boolean var2) {
				AsusQuickSettingsControllerItem.this.mText.setEnabled(var2);
			}
		};
		this.mContext = var1;
		ComotID.init(this.mContext.getPackageName(), this.mContext);
		this.mR = var1.getResources();
		this.setOrientation(LinearLayout.VERTICAL); //1
		this.setGravity(80);
		this.mColSpan = 1;
	}

	public CheckBox getCheckBox() {
		return this.mCheckbox;
	}

	int getColumnSpan() {
		return this.mColSpan;
	}

	public String getSharfKey() {
		return this.mSharfKey;
	}

	public TextView getText() {
		return this.mText;
	}

	public void onLocaleChanged() {
		this.mText.setText(this.mTextRes);
	}

	public void onThemeChange() {
		this.updateResources();
	}

	public void setCheckBoxImage(Drawable var1) {
		this.mCheckbox.setBackground(var1);
	}

	void setContent(int var1) {
		((LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(var1, this);
	}

	public void setControllText(int var1) {
		this.mText.setText(var1);
	}

	public void setControllText(String var1) {
		this.mText.setText(var1);
	}

	public void setItemTextColor(int var1) {
		this.mText.setTextColor(this.mContext.getResources().getColorStateList(var1));
	}

	public void setup(int var1, int var2, String var3, int var4, String var5) {
		this.setContent(var1);
		this.mCheckbox = (CheckBox)this.findViewById(ComotID.Get("checkbox", "id"));
		this.mText = (TextView)this.findViewById(ComotID.Get("text", "id"));
		this.mDrawableId = var2;
		this.mBgString = var3;
		//
		SettingsObserver settingsObserver = new SettingsObserver(new Handler());
		settingsObserver.observe();
		//
		this.updateResources();
		this.mText.setText(var4);
		this.mTextRes = var4;
		this.mSharfKey = var5;
		this.mCheckbox.setOnCheckedChangeListener(this.mCheckboxListener);
		this.mText.setEnabled(this.mCheckbox.isChecked());
	}

	class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}

		void observe() {
			ContentResolver resolver = mContext.getContentResolver();
			resolver.registerContentObserver(Settings.System.getUriFor("toggle_quick_settings_color"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("quick_settings_color_on"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("quick_settings_color_off"), false, this);
		}

		@Override
		public void onChange(boolean selfChange) {
			updateResources();
		}
	}

	void updateResources() {
		Resources var3 = this.mContext.getResources();
		Drawable var2 = var3.getDrawable(this.mDrawableId);
		ColorStateList var5 = var3.getColorStateList(ComotID.Get("asus_phone_control_onoff_text_color2", "drawable"));
		ThemeUtils var6 = ThemeUtils.getInstance(this.mContext);
		//
		int bamz_cust = Settings.System.getInt(this.mContext.getContentResolver(), "toggle_quick_settings_color", 0);
		if(bamz_cust == 1) {
			int[][] posisi = new int[][]{
					new int[]{android.R.attr.state_enabled}, //1
					new int[]{android.R.attr.stateNotNeeded}, //2
					new int[]{},
			};
			int[] warna = new int[] {
					Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_on", -0xFF3C1A), //1
					Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), //2
					Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), //3
			};

			var5 = new ColorStateList(posisi, warna);
			/*
			int[] ad = var2.getState();
			int[] on = new int[] {android.R.attr.state_checked, android.R.attr.state_selected};
			int[] off = new int[] {android.R.attr.stateNotNeeded};
			if(ad == on){
				var2.setColorFilter(Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_on", -0xFF3C1A), PorterDuff.Mode.MULTIPLY);
			}
			if(ad == off){
				var2.setColorFilter(Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), PorterDuff.Mode.MULTIPLY);
			}
			//var2.setColorFilter(var5.getDefaultColor(), PorterDuff.Mode.MULTIPLY);
			*/
		}
		//
		Drawable var4 = var2;
		ColorStateList var8 = var5;
		if(var6.isChangeNeeded()) {
			Resources var7 = var6.getThemeRes();
			int var1 = var6.getResId(this.mBgString, "drawable");
			if(var6.isValidRes(var1)) {
				var2 = var7.getDrawable(var1);
			}

			var1 = var6.getResId("asus_phone_control_onoff_text_color2", "drawable");
			var4 = var2;
			var8 = var5;
			if(var6.isValidRes(var1)) {
				var8 = var7.getColorStateList(var1);
				//
				if(bamz_cust == 1) {
					int[][] posisi = new int[][]{
							new int[]{android.R.attr.state_enabled}, //1
							new int[]{android.R.attr.stateNotNeeded}, //2
							new int[]{},
					};
					int[] warna = new int[] {
							Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_on", -0xFF3C1A), //1
							Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), //2
							Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), //3
					};

					var8 = new ColorStateList(posisi, warna);
					/*
					int[] ad = var2.getState();
					int[] on = new int[] {android.R.attr.state_checked, android.R.attr.state_selected};
					int[] off = new int[] {android.R.attr.stateNotNeeded};
					if(ad == on){
						var2.setColorFilter(Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_on", -0xFF3C1A), PorterDuff.Mode.MULTIPLY);
					}
					if(ad == off){
						var2.setColorFilter(Settings.System.getInt(this.mContext.getContentResolver(), "quick_settings_color_off", -0x4B4B4C), PorterDuff.Mode.MULTIPLY);
					}
					//var2.setColorFilter(var8.getDefaultColor(), PorterDuff.Mode.MULTIPLY);
					*/
				}
				//
				var4 = var2;
			}
		}
		this.mText.setTextColor(var8);
		this.mCheckbox.setBackground(var4);
	}
}
