package com.android.systemui.pac;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;

public class StatusBarHeaderMachine
{
  private PendingIntent mAlarmHourly;
  private boolean mAttached;
  private Context mContext;
  private Handler mHandler = new Handler();

  public StatusBarHeaderMachine(Context paramContext)
  {
    this.mContext = paramContext;
  }

	public interface IStatusBarHeaderMachineObserver {
	}

	public interface IStatusBarHeaderProvider {
	}

	public class SettingsObserver {
	}

}
