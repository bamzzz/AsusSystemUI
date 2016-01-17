package com.android.systemui.statusbar.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.asus.systemui.qs.DoubleTapHelper;
import com.bamzzz.ComotID;

public class PhoneStatusBarView extends PanelBar implements DoubleTapHelper.Callback {
	private static final boolean DEBUG;
	PhoneStatusBar mBar;
	private final PhoneStatusBarTransitions mBarTransitions;
	private DoubleTapHelper mDoubleTapHelper;
	PanelView mLastFullyOpenedPanel = null;
	PanelView mNotificationPanel;
	private ScrimController mScrimController;

	static {
		DEBUG = true; //PhoneStatusBar.DEBUG;
	}

	private Context mContext;

	public PhoneStatusBarView(Context var1, AttributeSet var2) {
		super(var1, var2);
		this.getContext().getResources();
		this.mBarTransitions = new PhoneStatusBarTransitions(this);
	}

	public void addPanel(PanelView var1) {
		super.addPanel(var1);
		if(var1.getId() == ComotID.Get("eeq", "id")) {
			this.mNotificationPanel = var1;
		}

	}

	public BarTransitions getBarTransitions() {
        return this.mBarTransitions;
    }

	public void onAllPanelsCollapsed() {
		super.onAllPanelsCollapsed();
		this.postOnAnimation(new Runnable() {
			public void run() {
				PhoneStatusBarView.this.mBar.makeExpandedInvisible();
			}
		});
		this.mLastFullyOpenedPanel = null;
	}

	@SuppressLint("MissingSuperCall")
	public void onAttachedToWindow() {
		if(this.mBar.hasDoubleTapFeature()) {
			this.mDoubleTapHelper = new DoubleTapHelper(this.mContext, this);
		}

	}

	public void onDoubleTap() {
		this.mNotificationPanel.cancelPeek();
	}

	public void onExpandingFinished() {
		super.onExpandingFinished();
		this.mScrimController.onExpandingFinished();
	}

	@SuppressLint("MissingSuperCall")
	public void onFinishInflate() {
		this.mBarTransitions.init();
	}

	public boolean onInterceptTouchEvent(MotionEvent var1) {
		boolean var3 = true;
		boolean var2;
		if(this.mBar.isDoubleTapEnabled()) {
			var2 = var3;
		} else {
			var2 = var3;
			if(!this.mBar.interceptTouchEvent(var1)) {
				var2 = var3;
				if(!super.onInterceptTouchEvent(var1)) {
					var2 = false;
				}
			}
		}

		return var2;
	}

	public void onPanelFullyOpened(PanelView var1) {
		super.onPanelFullyOpened(var1);
		if(var1 != this.mLastFullyOpenedPanel) {
			var1.sendAccessibilityEvent(32);
		}

		this.mLastFullyOpenedPanel = var1;
	}

	public void onPanelPeeked() {
		super.onPanelPeeked();
		this.mBar.makeExpandedVisible(false);
	}

	public boolean onRequestSendAccessibilityEvent(View var1, AccessibilityEvent var2) {
		boolean var3;
		if(super.onRequestSendAccessibilityEvent(var1, var2)) {
			AccessibilityEvent var4 = AccessibilityEvent.obtain();
			this.onInitializeAccessibilityEvent(var4);
			this.dispatchPopulateAccessibilityEvent(var4);
			var2.appendRecord(var4);
			var3 = true;
		} else {
			var3 = false;
		}

		return var3;
	}

	public boolean onTouchEvent(MotionEvent var1) {
		boolean var6 = false;
		if(this.mBar.isDoubleTapEnabled()) {
			this.mDoubleTapHelper.onTouchEvent(var1);
		}

		boolean var7 = this.mBar.interceptTouchEvent(var1);
		if(var1.getActionMasked() != 2) {
			int var5 = var1.getActionMasked();
			int var3 = (int)var1.getX();
			int var4 = (int)var1.getY();
			byte var2;
			if(var7) {
				var2 = 1;
			} else {
				var2 = 0;
			}

			EventLog.writeEvent('貪', new Object[]{Integer.valueOf(var5), Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var2)});
		}

		if(var7 || super.onTouchEvent(var1)) {
			var6 = true;
		}

		return var6;
	}

	public void onTrackingStarted(PanelView var1) {
		super.onTrackingStarted(var1);
		//this.mBar.onTrackingStarted();
		//this.mScrimController.onTrackingStarted();
	}

	public void onTrackingStopped(PanelView var1, boolean var2) {
		super.onTrackingStopped(var1, var2);
		//this.mBar.onTrackingStopped(var2);
	}

	public void panelExpansionChanged(PanelView var1, float var2, boolean var3) {
		super.panelExpansionChanged(var1, var2, var3);
		//this.mScrimController.setPanelExpansion(var2);
	}

	public boolean panelsEnabled() {
		return this.mBar.panelsEnabled();
	}

	public PanelView selectPanelForTouch(MotionEvent var1) {
		PanelView var2;
		if(this.mNotificationPanel.getExpandedHeight() > 0.0F) {
			var2 = null;
		} else {
			var2 = this.mNotificationPanel;
		}

		return var2;
	}

	public void setBar(PhoneStatusBar var1) {
		this.mBar = var1;
	}

	public void setScrimController(ScrimController var1) {
		this.mScrimController = var1;
	}
}
