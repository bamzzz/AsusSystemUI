package com.serajr.utils;

import android.content.Context;
import android.content.res.Resources;

public class ResourceUtils
{
  private static Context CONTEXT = null;
  private static String PACKAGE_NAME = "";
  
  public static int getAnimResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "anim", PACKAGE_NAME);
  }
  
  public static int getArrayResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "array", PACKAGE_NAME);
  }
  
  public static int getBoolResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "bool", PACKAGE_NAME);
  }
  
  public static int getColorResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "color", PACKAGE_NAME);
  }
  
  public static Context getContext()
  {
    return CONTEXT;
  }
  
  public static int getDimenResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "dimen", PACKAGE_NAME);
  }
  
  public static int getDrawableResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "drawable", PACKAGE_NAME);
  }
  
  public static int getIdResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "id", PACKAGE_NAME);
  }
  
  public static int getIntegerResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "integer", PACKAGE_NAME);
  }
  
  public static int getLayoutResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "layout", PACKAGE_NAME);
  }
  
  public static String getPackageName()
  {
    return PACKAGE_NAME;
  }
  
  public static int getStringResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "string", PACKAGE_NAME);
  }
  
  public static int[] getStyleableArrayResId(String paramString)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = CONTEXT.getResources().getIdentifier(paramString, "styleable", PACKAGE_NAME);
    return arrayOfInt;
  }
  
  public static int getStyleableResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "styleable", PACKAGE_NAME);
  }
  
  public static int getXmlResId(String paramString)
  {
    return CONTEXT.getResources().getIdentifier(paramString, "xml", PACKAGE_NAME);
  }
  
  public static void init(String paramString, Context paramContext)
  {
    PACKAGE_NAME = paramString;
    CONTEXT = paramContext;
  }
}
