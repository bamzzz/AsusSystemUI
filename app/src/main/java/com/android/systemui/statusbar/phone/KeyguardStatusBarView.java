package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.systemui.BatteryMeterView;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.BatteryController;

/**
 * Created by bamzzz on 14 Jan 2016.
 */
public class KeyguardStatusBarView extends RelativeLayout {
    private final Context mContext;

    public KeyguardStatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LinearLayout batteryContainer = (LinearLayout) findViewById(R.id.battery_container);
    }

    public void setBatteryController(BatteryController batteryController) {

        ((BatteryMeterView) findViewById(R.id.battery)).setBatteryController(batteryController);
    }

}
