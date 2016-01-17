package com.android.systemui.statusbar.phone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.systemui.pac.StatusBarHeaderMachine;
import com.android.systemui.statusbar.policy.WeatherController;
import com.android.systemui.statusbar.policy.WeatherControllerImpl;
import com.asus.systemui.util.ThemeUtils;
import com.bamzzz.ComotID;

public class StatusBarHeaderView extends RelativeLayout implements View.OnClickListener, WeatherController.Callback, StatusBarHeaderMachine.IStatusBarHeaderMachineObserver {
	private ActivityStarter mActivityStarter;
	private TextClock mAmpm;
	private ImageView mBackgroundImage;
	private PhoneStatusBar.ThemeChangedCallback mCallback;
	private Button mClearButton;
	private final Rect mClipBounds = new Rect();
	private int mCollapsedHeight;
	private Drawable mCurrentBackground;
	private TextView mDate;
	private ImageView mEditPageView;
	private boolean mExpanded;
	private boolean mIsSecureKeyguardLock;
	private float mLastHeight;
	private ImageView mSettingsButton;
	private TextClock mTime;
	private ImageView mUserInfo;
	private Context mContext;

	private ViewGroup mWeatherContainer;
	private TextView mWeatherLine1, mWeatherLine2;
	private WeatherController mWeatherController;
	private boolean mShowWeather;
	private boolean mListening;
	private SettingsObserver mSettingsObserver;


	public StatusBarHeaderView(Context paramContext, AttributeSet var2) {
		super(paramContext, var2);
		this.mContext = paramContext;
		ComotID.init(this.mContext.getPackageName(), this.mContext);
	}

	//Weather
	class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}

		//@Override
		protected void observe() {
			//super.observe();

			ContentResolver resolver = mContext.getContentResolver();
			resolver.registerContentObserver(Settings.System.getUriFor("status_bar_show_weather"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("status_bar_weather_text_color"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("status_bar_weather_text_size"), false, this);
			resolver.registerContentObserver(Settings.System.getUriFor("status_bar_weather_text_style"), false, this);
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
			mShowWeather = Settings.System.getInt(resolver, "status_bar_show_weather", 1) == 1;
			//Toast.makeText(mContext,"Updating...", Toast.LENGTH_SHORT);
			updateVisibilities();
			requestLayout();
			setImageViewDrawable();
		}

		@Override
		public void onChange(boolean selfChange) {
			updateVisibilities();
			requestLayout();
			setImageViewDrawable();
		}
	}
	// End

	private void doUpdateStatusBarCustomHeader(Drawable var1, boolean var2) {
		if(var1 != null) {
			if(var1 != this.mCurrentBackground) {
				this.mBackgroundImage.setVisibility(View.VISIBLE);
				this.setNotificationPanelHeaderBackground(var1, var2);
				this.mCurrentBackground = var1;
			}
		} else {
			this.mCurrentBackground = null;
			this.mBackgroundImage.setVisibility(View.GONE);
		}

	}

	private void loadDimens() {
		this.mCollapsedHeight = this.getResources().getDimensionPixelSize(ComotID.Get("asus_status_bar_header_height", "dimen"));
	}

	private void setClipping(float var1) {
		this.mClipBounds.set(this.getPaddingLeft(), 0, this.getWidth() - this.getPaddingRight(), (int)var1);
		this.setClipBounds(this.mClipBounds);
		this.invalidateOutline();
	}

	private void setImageViewDrawable() {
		Resources var10 = this.mContext.getResources();
		/*Drawable var6 = var10.getDrawable(2130837873);
		Drawable var7 = var10.getDrawable(2130837872);
		Drawable var8 = var10.getDrawable(2130838031);
		Drawable var9 = var10.getDrawable(2130837733);
		int var4 = var10.getColor(2131230812);
		int var1 = var10.getColor(2131230822);*/
		Drawable var6 = var10.getDrawable(ComotID.Get("asus_quick_settings_notify_setting", "drawable"));
		Drawable var7 = var10.getDrawable(ComotID.Get("asus_quick_settings_notify_editpage", "drawable"));
		Drawable var8 = var10.getDrawable(ComotID.Get("ic_notify_clear", "drawable"));
		Drawable var9 = var10.getDrawable(ComotID.Get("asus_header_bg", "drawable"));
		int var4 = var10.getColor(ComotID.Get("asus_notification_clear_all_text_color", "color"));
		int var1 = var10.getColor(ComotID.Get("asus_panel_header_time_text_color", "color"));
		ThemeUtils var14 = ThemeUtils.getInstance(this.mContext);
		Drawable var12 = var9;
		Drawable var11 = var8;
		int var3 = var1;
		int var2 = var4;
		Drawable var16 = var7;
		Drawable var13 = var6;
		if(var14.isChangeNeeded()) {
			Resources var15 = var14.getThemeRes();
			var2 = var14.getResId("asus_quick_settings_notify_setting", "drawable");
			if(var14.isValidRes(var2)) {
				var6 = var15.getDrawable(var2);
			}

			var2 = var14.getResId("asus_quick_settings_notify_editpage", "drawable");
			if(var14.isValidRes(var2)) {
				var7 = var15.getDrawable(var2);
			}

			var2 = var14.getResId("ic_notify_clear", "drawable");
			if(var14.isValidRes(var2)) {
				var8 = var15.getDrawable(var2);
			}

			var2 = var14.getResId("asus_header_bg", "drawable");
			if(var14.isValidRes(var2)) {
				var9 = var15.getDrawable(var2);
			}

			var2 = var14.getResId("asus_notification_clear_all_text_color", "color");
			if(var14.isValidRes(var2)) {
				var1 = var15.getColor(var2);
			}

			int var5 = var14.getResId("asus_panel_header_time_text_color", "color");
			var12 = var9;
			var11 = var8;
			var3 = var1;
			var2 = var4;
			var16 = var7;
			var13 = var6;
			if(var14.isValidRes(var5)) {
				var2 = var15.getColor(var5);
				var13 = var6;
				var16 = var7;
				var3 = var1;
				var11 = var8;
				var12 = var9;
			}
		}

		this.mSettingsButton.setImageDrawable(var13);
		this.mEditPageView.setImageDrawable(var16);
		this.mClearButton.setBackground(var11);
		this.setBackground(var12);
		this.mClearButton.setTextColor(var3);
		this.mTime.setTextColor(var2);
		this.mDate.setTextColor(var2);
		this.mAmpm.setTextColor(var2);
		//Weather MOD
		int warna = Settings.System.getInt(mContext.getContentResolver(), "status_bar_weather_text_color", -2);
		float ukuran = Settings.System.getInt(mContext.getContentResolver(), "status_bar_weather_text_size", 12);
		this.mWeatherLine1.setTextColor(warna);
		this.mWeatherLine2.setTextColor(warna);
		this.mWeatherLine1.setTextSize(ukuran);
		this.mWeatherLine2.setTextSize(ukuran);
		this.mWeatherLine1.setTypeface(ambilFontStyle());
		this.mWeatherLine2.setTypeface(ambilFontStyle());
		// ENd
		var1 = this.mContext.getResources().getDimensionPixelSize(ComotID.Get("notification_side_padding", "dimen"));
		this.mContext.getResources().getDimensionPixelSize(ComotID.Get("asus_quicksetting_panel_header_padding_bottom", "dimen"));
		this.setPaddingRelative(var1, 0, var1, 0);
	}

	public Typeface ambilFontStyle(){
		int style = Settings.System.getInt(mContext.getContentResolver(), "status_bar_weather_text_style", 4);
		Typeface st;
		switch(style) {
			case 0:
				st = Typeface.create("sans-serif", Typeface.BOLD);
				break;
			case 1:
				st = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
				break;
			case 2:
				st = Typeface.create("sans-serif-light", Typeface.NORMAL);
				break;
			case 3:
				st = Typeface.create("sans-serif-light", Typeface.ITALIC);
				break;
			default:
				st = Typeface.create("sans-serif", Typeface.NORMAL);
		}
		return st;
	}

	private void setNotificationPanelHeaderBackground(Drawable var1, boolean var2) {
		if(this.mBackgroundImage.getDrawable() != null && !var2) {
			TransitionDrawable var3 = new TransitionDrawable(new Drawable[]{this.mBackgroundImage.getDrawable(), var1});
			var3.setCrossFadeEnabled(true);
			this.mBackgroundImage.setImageDrawable(var3);
			var3.startTransition(1000);
		} else {
			this.mBackgroundImage.setImageDrawable(var1);
		}

	}

	private void startSettingsActivity() {
		this.mActivityStarter.startActivity(new Intent("android.settings.SETTINGS"), true);
	}

	private void updateHeights() {
		int var1 = this.mCollapsedHeight;
		LayoutParams var2 = (LayoutParams) this.getLayoutParams();
		if(var2.height != var1) {
			var2.height = var1;
			this.setLayoutParams(var2);
		}

	}

	@SuppressWarnings("ResourceType")
	private void updateVisibilities() {
		byte var2 = 4;
		ImageView var3 = this.mEditPageView;
		byte var1;
		if(this.mExpanded && !this.mIsSecureKeyguardLock) {
			var1 = 0;
		} else {
			var1 = 4;
		}

		var3.setVisibility(var1);
		var3 = this.mSettingsButton;
		if(!this.mIsSecureKeyguardLock) {
			var1 = 0;
		} else {
			var1 = 4;
		}

		var3.setVisibility(var1);
		Button var4 = this.mClearButton;
		if(this.mExpanded) {
			var1 = var2;
		} else {
			var1 = 0;
		}

		var4.setVisibility(var1);
		//Weather
		this.mWeatherContainer.setVisibility(this.mExpanded && this.mShowWeather ? View.VISIBLE : View.GONE);
		/*if(this.mShowWeather){
			Toast.makeText(this.mContext, "Weather Visible", Toast.LENGTH_SHORT).show();
		} if(!this.mShowWeather){
			Toast.makeText(this.mContext, "Weather Gone", Toast.LENGTH_SHORT).show();
		}*/
		// End
	}

	public void disableHeader() {
		this.post(new Runnable() {
			public void run() {
				StatusBarHeaderView.this.mCurrentBackground = null;
				StatusBarHeaderView.this.mBackgroundImage.setVisibility(8);
			}
		});
	}

	///Weather
	public void setWeatherController(WeatherController weatherController) {
		mWeatherController = weatherController;
	}
	//End

	public int getCollapsedHeight() {
		return this.mCollapsedHeight;
	}

	public PhoneStatusBar.ThemeChangedCallback getThemeCallback() {
		return this.mCallback;
	}

	public void onClick(View v) {
		if(v == this.mSettingsButton) {
			this.startSettingsActivity();
		} else if(v == this.mEditPageView) {
			this.mActivityStarter.startActivity(new Intent("android.settings.action.ASUS_QUICKSETTING_LIST_SETTING_PAGE"), true);
		} else if (v == this.mWeatherContainer) {
			startForecastActivity(); //Weather
		}
	}

	//Weather
	/*private void startClockActivity() {
		mActivityStarter.startActivity(new Intent(AlarmClock.ACTION_SHOW_ALARMS),
				true *//* dismissShade *//*);
	}

	private void startDateActivity() {
		Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
		builder.appendPath("time");
		ContentUris.appendId(builder, System.currentTimeMillis());
		Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
		mActivityStarter.startActivity(intent, true *//* dismissShade *//*);
	}*/

	private void startForecastActivity() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(WeatherControllerImpl.COMPONENT_WEATHER_FORECAST);
		mActivityStarter.startActivity(intent, true /* dismissShade */);
	}
	//End

	protected void onFinishInflate() {
		super.onFinishInflate();
		this.mSettingsButton = (ImageView)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mSettingsButton.setOnClickListener(this);
		this.mEditPageView = (ImageView)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mEditPageView.setOnClickListener(this);
		this.mClearButton = (Button)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mDate = (TextView)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mTime = (TextClock)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id")).findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mAmpm = (TextClock)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id")).findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mUserInfo = (ImageView)this.findViewById(ComotID.Get("stat_sys_network_traffic_up", "id"));
		this.mBackgroundImage = (ImageView)this.findViewById(ComotID.Get("background_image", "id"));
		this.loadDimens();
		//Weather
		this.mWeatherContainer = (LinearLayout) findViewById(ComotID.Get("weather_container", "id"));
		this.mWeatherContainer.setOnClickListener(this);
		this.mWeatherLine1 = (TextView) findViewById(ComotID.Get("weather_line_1", "id"));
		this.mWeatherLine2 = (TextView) findViewById(ComotID.Get("weather_line_2", "id"));
		this.mSettingsObserver = new SettingsObserver(new Handler());
		//End
		this.setImageViewDrawable();
		this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			public void onLayoutChange(View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
				if(var4 - var2 != var8 - var6) {
					StatusBarHeaderView.this.setClipping((float)StatusBarHeaderView.this.getHeight());
				}

			}
		});
		this.setOutlineProvider(new ViewOutlineProvider() {
			public void getOutline(View var1, Outline var2) {
				var2.setRect(StatusBarHeaderView.this.mClipBounds);
			}
		});
		this.mCallback = new StatusBarHeaderView.MyThemeChangedCallback(null);
	}

	public void release() {
		this.mCallback = null;
		this.addOnLayoutChangeListener((OnLayoutChangeListener)null);
		this.setOutlineProvider((ViewOutlineProvider) null);
		this.mSettingsButton.setOnClickListener((OnClickListener) null);
		this.mSettingsButton.setImageDrawable((Drawable) null);
		this.mEditPageView.setOnClickListener((OnClickListener) null);
		this.mEditPageView.setImageDrawable((Drawable) null);
		this.mClearButton.getBackground().setCallback((Callback) null);
		this.mClearButton.setBackground((Drawable) null);
		//this.mWeatherContainer.setOnClickListener((OnClickListener) null);
	}

	public void setActivityStarter(ActivityStarter var1) {
		this.mActivityStarter = var1;
	}

	//Weather
	public void setListening(boolean listening) {
		if (listening == mListening) {
			return;
		}
		mListening = listening;
		updateListeners();
	}
	//End

	public void setExpanded(boolean var1, boolean var2) {
		boolean var3;
		if(var1 == this.mExpanded && this.mIsSecureKeyguardLock == var2) {
			var3 = false;
		} else {
			var3 = true;
		}

		this.mExpanded = var1;
		this.mIsSecureKeyguardLock = var2;
		if(var3) {
			this.updateEverything();
		}

	}

	public void updateEverything() {
		final float var1 = (float)this.getHeight();
		this.setClipping(var1);
		this.post(new Runnable() {
			public void run() {
				android.widget.RelativeLayout.LayoutParams var1x = (android.widget.RelativeLayout.LayoutParams)StatusBarHeaderView.this.mBackgroundImage.getLayoutParams();
				var1x.height = (int)var1;
				StatusBarHeaderView.this.mBackgroundImage.setLayoutParams(var1x);
			}
		});
		this.updateVisibilities();
		this.updateHeights();
		this.requestLayout();
	}

	public void updateHeader(final Drawable var1, final boolean var2) {
		this.post(new Runnable() {
			public void run() {
				StatusBarHeaderView.this.doUpdateStatusBarCustomHeader(var1, var2);
			}
		});
	}

	// Weather
	private void updateListeners() {
		if (mListening) {
			mSettingsObserver.observe();
			mWeatherController.addCallback(this);
		} else {
			mWeatherController.removeCallback(this);
			mSettingsObserver.unobserve();
		}
	}

	@Override
	public void onWeatherChanged(WeatherController.WeatherInfo info) {
		if (info.temp == null || info.condition == null) {
			mWeatherLine1.setText(null);
		} else {
			mWeatherLine1.setText(mContext.getString(
					//R.string.status_bar_expanded_header_weather_format,
					ComotID.Get("status_bar_expanded_header_weather_format", "string"),
					info.temp,
					info.condition));
			//Toast.makeText(this.mContext,info.temp + info.condition, Toast.LENGTH_SHORT);
		}
		mWeatherLine2.setText(info.city);
	}
	// End

	private class MyThemeChangedCallback implements PhoneStatusBar.ThemeChangedCallback {
		private MyThemeChangedCallback() {
			super();
		}

		// $FF: synthetic method
		MyThemeChangedCallback(Object var2) {
			this();
		}

		public void onThemeChanged() {
			StatusBarHeaderView.this.setImageViewDrawable();
		}
	}
}
