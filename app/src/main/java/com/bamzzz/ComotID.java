package com.bamzzz;

import android.content.Context;

/**
 * Created by bamzzz on 05 Jan 2016.
 * Usage:
 * getResourceId("myIcon", "drawable", getPackageName());
 * getResourceId("myAppName", "string", getPackageName());
 */

public class ComotID {

	private static Context CONTEXT = null;
	private static String PACKAGE_NAME = "";

	public static int Get(String pVariableName, String pResourcename)
	{
		try {
			return CONTEXT.getResources().getIdentifier(pVariableName, pResourcename, PACKAGE_NAME);
			//return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
			//return Resources.getSystem().getIdentifier(pVariableName, pResourcename, pPackageName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("No resource ID found for: "
					+ pResourcename + " / " + pVariableName, e);
			//return -1;
		}
	}

	public static int[] GetStyle(String pVariableName)
	{
		int[] arrayOfInt = new int[1];
		arrayOfInt[0] = CONTEXT.getResources().getIdentifier(pVariableName, "styleable", PACKAGE_NAME);
		return arrayOfInt;
	}

	public static void init(String pPackageName, Context paramContext)
	{
		PACKAGE_NAME = pPackageName;
		CONTEXT = paramContext;
	}

}
