package com.unity3d.player;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class UnityPermissions
{
  private static final String SKIP_DIALOG_METADATA_NAME = "unityplayer.SkipPermissionsDialog";

  private static boolean checkInfoForMetadata(PackageItemInfo paramPackageItemInfo)
  {
    try
    {
      boolean bool = paramPackageItemInfo.metaData.getBoolean("unityplayer.SkipPermissionsDialog");
      return bool;
    }
    catch (java.lang.Exception paramPackageItemInfo)
    {
    }
    return false;
  }

  public static boolean hasUserAuthorizedPermission(Activity paramActivity, String paramString)
  {
    return paramActivity.checkCallingOrSelfPermission(paramString) == 0;
  }

  public static void requestUserPermissions(Activity paramActivity, String[] paramArrayOfString, IPermissionRequestCallbacks paramIPermissionRequestCallbacks)
  {
    if (!PlatformSupport.MARSHMALLOW_SUPPORT)
      return;
    if ((paramActivity != null) && (paramArrayOfString != null))
    {
      FragmentManager localFragmentManager = paramActivity.getFragmentManager();
      if (localFragmentManager.findFragmentByTag("96489") == null)
      {
        paramActivity = new g(paramActivity, paramIPermissionRequestCallbacks);
        paramIPermissionRequestCallbacks = new Bundle();
        paramIPermissionRequestCallbacks.putStringArray("PermissionNames", paramArrayOfString);
        paramActivity.setArguments(paramIPermissionRequestCallbacks);
        paramArrayOfString = localFragmentManager.beginTransaction();
        paramArrayOfString.add(0, paramActivity, "96489");
        paramArrayOfString.commit();
      }
    }
  }

  public static boolean skipPermissionsDialog(Activity paramActivity)
  {
    if (!PlatformSupport.MARSHMALLOW_SUPPORT)
      return false;
    try
    {
      PackageManager localPackageManager = paramActivity.getPackageManager();
      ActivityInfo localActivityInfo = localPackageManager.getActivityInfo(paramActivity.getComponentName(), 128);
      paramActivity = localPackageManager.getApplicationInfo(paramActivity.getPackageName(), 128);
      boolean bool;
      if (!checkInfoForMetadata(localActivityInfo))
        bool = checkInfoForMetadata(paramActivity);
      return bool;
    }
    catch (java.lang.Exception paramActivity)
    {
      label55: break label55;
    }
  }

  public static class ModalWaitForPermissionResponse
    implements IPermissionRequestCallbacks
  {
    private boolean haveResponse = false;

    public void onPermissionDenied(String paramString)
    {
      monitorenter;
      try
      {
        this.haveResponse = true;
        notify();
        monitorexit;
        return;
      }
      finally
      {
        paramString = finally;
        monitorexit;
      }
      throw paramString;
    }

    public void onPermissionDeniedAndDontAskAgain(String paramString)
    {
      monitorenter;
      try
      {
        this.haveResponse = true;
        notify();
        monitorexit;
        return;
      }
      finally
      {
        paramString = finally;
        monitorexit;
      }
      throw paramString;
    }

    public void onPermissionGranted(String paramString)
    {
      monitorenter;
      try
      {
        this.haveResponse = true;
        notify();
        monitorexit;
        return;
      }
      finally
      {
        paramString = finally;
        monitorexit;
      }
      throw paramString;
    }

    // ERROR //
    public void waitForResponse()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 17	com/unity3d/player/UnityPermissions$ModalWaitForPermissionResponse:haveResponse	Z
      //   6: istore_1
      //   7: iload_1
      //   8: ifeq +6 -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: invokevirtual 31	java/lang/Object:wait	()V
      //   18: aload_0
      //   19: monitorexit
      //   20: return
      //   21: astore_2
      //   22: aload_0
      //   23: monitorexit
      //   24: aload_2
      //   25: athrow
      //   26: astore_2
      //   27: aload_0
      //   28: monitorexit
      //   29: return
      //
      // Exception table:
      //   from	to	target	type
      //   2	7	21	finally
      //   14	18	21	finally
      //   2	7	26	java/lang/InterruptedException
      //   14	18	26	java/lang/InterruptedException
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.UnityPermissions
 * JD-Core Version:    0.6.0
 */