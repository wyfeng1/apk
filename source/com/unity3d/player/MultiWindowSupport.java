package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MultiWindowSupport
{
  private static final String RESIZABLE_WINDOW = "unity.allow-resizable-window";
  private static boolean s_LastMultiWindowMode = false;

  public static boolean getAllowResizableWindow(Activity paramActivity)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramActivity.getPackageManager().getApplicationInfo(paramActivity.getPackageName(), 128);
      if (isInMultiWindowMode(paramActivity))
      {
        boolean bool = localApplicationInfo.metaData.getBoolean("unity.allow-resizable-window");
        if (bool)
          return true;
      }
      label38: return false;
    }
    catch (java.lang.Exception paramActivity)
    {
      break label38;
    }
  }

  static boolean isInMultiWindowMode(Activity paramActivity)
  {
    if (PlatformSupport.NOUGAT_SUPPORT)
      return paramActivity.isInMultiWindowMode();
    return false;
  }

  public static boolean isMultiWindowModeChangedToTrue(Activity paramActivity)
  {
    return (!s_LastMultiWindowMode) && (isInMultiWindowMode(paramActivity));
  }

  public static void saveMultiWindowMode(Activity paramActivity)
  {
    s_LastMultiWindowMode = isInMultiWindowMode(paramActivity);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.MultiWindowSupport
 * JD-Core Version:    0.6.0
 */