package com.android.systemui.pac;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;

import com.serajr.utils.ResourceUtils;

import java.util.Calendar;

public class DaylightHeaderProvider
  implements StatusBarHeaderMachine.IStatusBarHeaderProvider
{
  private static final Calendar CAL_CHRISTMAS = Calendar.getInstance();
  private static final Calendar CAL_NEWYEARSEVE = Calendar.getInstance();
  private static final Calendar CAL_PROKLAMASI_RI = Calendar.getInstance();
  private static final Calendar CAL_RAMADHAN = Calendar.getInstance();
  private static final Calendar CAL_LEBARAN = Calendar.getInstance();
  private static final Calendar CAL_LEBARAN2 = Calendar.getInstance();
  private static final Calendar CAL_LEBARAN_HAJI = Calendar.getInstance();
  private SparseArray<Drawable> mCache;
  private Context mContext;
  
  public DaylightHeaderProvider(Context paramContext)
  {
    this.mContext = paramContext;
    ResourceUtils.init(this.mContext.getPackageName(), this.mContext);
    CAL_CHRISTMAS.set(2, 11);
    CAL_CHRISTMAS.set(5, 25);
    CAL_NEWYEARSEVE.set(2, 11);
    CAL_NEWYEARSEVE.set(5, 31);
    CAL_PROKLAMASI_RI.set(2, 7); // Bulan ditambah 1
    CAL_PROKLAMASI_RI.set(5, 17); // Hari
    CAL_RAMADHAN.set(2, 5);
    CAL_RAMADHAN.set(5, 6); // 6 Jun
    CAL_LEBARAN.set(2, 6);
    CAL_LEBARAN.set(5, 6); // 6 Jul
    CAL_LEBARAN2.set(2, 6);
    CAL_LEBARAN2.set(5, 7); // 7 Jul
    CAL_LEBARAN_HAJI.set(2, 8);
    CAL_LEBARAN_HAJI.set(5, 12); // 12 Sep

    this.mCache = new SparseArray();
  }
  
  private static boolean isItToday(Calendar paramCalendar)
  {
    Calendar localCalendar = Calendar.getInstance();
    return (localCalendar.get(2) == paramCalendar.get(2)) && (localCalendar.get(5) == paramCalendar.get(5));
  }
  
  private Drawable loadOrFetch(int paramInt)
  {
    Drawable localDrawable = (Drawable)this.mCache.get(paramInt);
    if (localDrawable == null)
    {
      localDrawable = this.mContext.getResources().getDrawable(paramInt);
      this.mCache.put(paramInt, localDrawable);
    }
    return localDrawable;
  }
  
  public Drawable getCurrent(Calendar paramCalendar)
  {
    int i = Settings.System.getInt(this.mContext.getContentResolver(), "omni_header_style", 0);
    if (i == 0)
    {
      if (isItToday(CAL_CHRISTMAS)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_christmas"));
      }
      if (isItToday(CAL_NEWYEARSEVE)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_newyearseve"));
      }
      if (isItToday(CAL_PROKLAMASI_RI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_proklamasi_RI"));
      }
      if (isItToday(CAL_RAMADHAN)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_ramadhan"));
      }
      if (isItToday(CAL_LEBARAN)) {
		  return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran"));
	  }
	  if (isItToday(CAL_LEBARAN2)) {
		return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran"));
      }
      if (isItToday(CAL_LEBARAN_HAJI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_haji"));
      }
    }
    if (i == 1)
    {
      if (isItToday(CAL_CHRISTMAS)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_christmas_1"));
      }
      if (isItToday(CAL_NEWYEARSEVE)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_newyearseve_1"));
      }
      if (isItToday(CAL_PROKLAMASI_RI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_proklamasi_RI_1"));
      }
      if (isItToday(CAL_RAMADHAN)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_ramadhan_1"));
      }
      if (isItToday(CAL_LEBARAN)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_1"));
      }
	  if (isItToday(CAL_LEBARAN2)) {
	    return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_1"));
	  }
      if (isItToday(CAL_LEBARAN_HAJI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_haji_1"));
      }
    }
    if (i == 2)
    {
      if (isItToday(CAL_CHRISTMAS)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_christmas_hd"));
      }
      if (isItToday(CAL_NEWYEARSEVE)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_newyearseve_hd"));
      }
      if (isItToday(CAL_PROKLAMASI_RI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_proklamasi_RI_hd"));
      }
      if (isItToday(CAL_RAMADHAN)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_ramadhan_hd"));
      }
      if (isItToday(CAL_LEBARAN)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_hd"));
      }
	  if (isItToday(CAL_LEBARAN2)) {
		return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_hd"));
      }
      if (isItToday(CAL_LEBARAN_HAJI)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_lebaran_haji_hd"));
      }
    }
    int j = paramCalendar.get(11);
    if (i == 0)
    {
      if ((j < 6) || (j >= 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_night"));
      }
      if ((j >= 6) && (j < 9)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunrise"));
      }
      if ((j >= 9) && (j < 11)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_morning"));
      }
      if ((j >= 11) && (j < 13)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_noon"));
      }
      if ((j >= 13) && (j < 19)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_afternoon"));
      }
      if ((j >= 19) && (j < 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunset"));
      }
    }
    if (i == 1)
    {
      if ((j < 6) || (j >= 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_night_1"));
      }
      if ((j >= 6) && (j < 9)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunrise_1"));
      }
      if ((j >= 9) && (j < 11)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_morning_1"));
      }
      if ((j >= 11) && (j < 13)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_noon_1"));
      }
      if ((j >= 13) && (j < 19)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_afternoon_1"));
      }
      if ((j >= 19) && (j < 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunset_1"));
      }
    }
    if (i == 2)
    {
      if ((j < 6) || (j >= 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_night_hd"));
      }
      if ((j >= 6) && (j < 9)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunrise_hd"));
      }
      if ((j >= 9) && (j < 11)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_morning_hd"));
      }
      if ((j >= 11) && (j < 13)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_noon_hd"));
      }
      if ((j >= 13) && (j < 19)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_afternoon_hd"));
      }
      if ((j >= 19) && (j < 21)) {
        return loadOrFetch(ResourceUtils.getDrawableResId("notifhead_sunset_hd"));
      }
    }
    Log.w("DaylightHeaderProvider", "No drawable for status  bar when it is " + j + "!");
    return null;
  }
  
  public String getName()
  {
    return "DaylightHeaderProvider";
  }
}
