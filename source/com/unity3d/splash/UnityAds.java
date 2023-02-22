package com.unity3d.splash;

import android.app.Activity;
import com.unity3d.splash.mediation.IUnityAdsExtendedListener;
import com.unity3d.splash.services.ads.UnityAdsImplementation;

public final class UnityAds
{
  public static final String LAUNCH_SCREEN_PLACEMENT = "unity-launch-screen";
  private static String defaultGameId = "3194466";
  private static IUnityAdsListener launchScreenAdsListener;
  private static boolean shownOnce = false;
  private static boolean skipLaunchScreenAds = false;

  public static void initialize(Activity paramActivity, String paramString, IUnityAdsListener paramIUnityAdsListener)
  {
    UnityAdsImplementation.initialize(paramActivity, paramString, paramIUnityAdsListener, false, false);
  }

  public static boolean isSkipLaunchScreenAds()
  {
    return skipLaunchScreenAds;
  }

  public static void setSkipLaunchScreenAds(boolean paramBoolean)
  {
    skipLaunchScreenAds = paramBoolean;
  }

  public static void showLaunchScreenAds(Activity paramActivity)
  {
    showLaunchScreenAds(paramActivity, defaultGameId, null);
  }

  public static void showLaunchScreenAds(Activity paramActivity, IAdsFinishListener paramIAdsFinishListener)
  {
    showLaunchScreenAds(paramActivity, defaultGameId, paramIAdsFinishListener);
  }

  public static void showLaunchScreenAds(Activity paramActivity, String paramString, IAdsFinishListener paramIAdsFinishListener)
  {
    if (launchScreenAdsListener == null)
    {
      Object localObject = paramIAdsFinishListener;
      if (paramIAdsFinishListener == null)
        localObject = new IAdsFinishListener()
        {
          public final void onUnityAdsFinish(String paramString, UnityAds.FinishState paramFinishState)
          {
          }
        };
      launchScreenAdsListener = new LaunchScreenAdsListener(paramActivity, (IAdsFinishListener)localObject);
    }
    if (!UnityAdsImplementation.isInitialized())
    {
      UnityAdsImplementation.initialize(paramActivity, paramString, launchScreenAdsListener);
      return;
    }
    if (UnityAdsImplementation.isReady("unity-launch-screen"))
      UnityAdsImplementation.show(paramActivity, "unity-launch-screen");
  }

  public static enum FinishState
  {
    static
    {
      FinishState localFinishState = new FinishState("COMPLETED", 2);
      COMPLETED = localFinishState;
      $VALUES = new FinishState[] { ERROR, SKIPPED, localFinishState };
    }
  }

  public static abstract interface IAdsFinishListener
  {
    public abstract void onUnityAdsFinish(String paramString, UnityAds.FinishState paramFinishState);
  }

  static class LaunchScreenAdsListener
    implements IUnityAdsExtendedListener
  {
    private Activity activity;
    private UnityAds.IAdsFinishListener adsFinishListener;

    public LaunchScreenAdsListener(Activity paramActivity, UnityAds.IAdsFinishListener paramIAdsFinishListener)
    {
      this.activity = paramActivity;
      this.adsFinishListener = paramIAdsFinishListener;
    }

    public void onUnityAdsClick(String paramString)
    {
    }

    public void onUnityAdsError(UnityAds.UnityAdsError paramUnityAdsError, String paramString)
    {
      this.adsFinishListener.onUnityAdsFinish(null, UnityAds.FinishState.ERROR);
    }

    public void onUnityAdsFinish(String paramString, UnityAds.FinishState paramFinishState)
    {
      this.adsFinishListener.onUnityAdsFinish(paramString, paramFinishState);
    }

    public void onUnityAdsPlacementStateChanged(String paramString, UnityAds.PlacementState paramPlacementState1, UnityAds.PlacementState paramPlacementState2)
    {
    }

    public void onUnityAdsReady(String paramString)
    {
      if (("unity-launch-screen".equalsIgnoreCase(paramString)) && (!UnityAds.skipLaunchScreenAds) && (!UnityAds.shownOnce))
      {
        UnityAdsImplementation.show(this.activity, paramString);
        UnityAds.access$102(true);
      }
    }

    public void onUnityAdsStart(String paramString)
    {
    }
  }

  public static enum PlacementState
  {
    static
    {
      NOT_AVAILABLE = new PlacementState("NOT_AVAILABLE", 1);
      DISABLED = new PlacementState("DISABLED", 2);
      WAITING = new PlacementState("WAITING", 3);
      PlacementState localPlacementState = new PlacementState("NO_FILL", 4);
      NO_FILL = localPlacementState;
      $VALUES = new PlacementState[] { READY, NOT_AVAILABLE, DISABLED, WAITING, localPlacementState };
    }
  }

  public static enum UnityAdsError
  {
    static
    {
      INITIALIZE_FAILED = new UnityAdsError("INITIALIZE_FAILED", 1);
      INVALID_ARGUMENT = new UnityAdsError("INVALID_ARGUMENT", 2);
      VIDEO_PLAYER_ERROR = new UnityAdsError("VIDEO_PLAYER_ERROR", 3);
      INIT_SANITY_CHECK_FAIL = new UnityAdsError("INIT_SANITY_CHECK_FAIL", 4);
      AD_BLOCKER_DETECTED = new UnityAdsError("AD_BLOCKER_DETECTED", 5);
      FILE_IO_ERROR = new UnityAdsError("FILE_IO_ERROR", 6);
      DEVICE_ID_ERROR = new UnityAdsError("DEVICE_ID_ERROR", 7);
      SHOW_ERROR = new UnityAdsError("SHOW_ERROR", 8);
      UnityAdsError localUnityAdsError = new UnityAdsError("INTERNAL_ERROR", 9);
      INTERNAL_ERROR = localUnityAdsError;
      $VALUES = new UnityAdsError[] { NOT_INITIALIZED, INITIALIZE_FAILED, INVALID_ARGUMENT, VIDEO_PLAYER_ERROR, INIT_SANITY_CHECK_FAIL, AD_BLOCKER_DETECTED, FILE_IO_ERROR, DEVICE_ID_ERROR, SHOW_ERROR, localUnityAdsError };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.UnityAds
 * JD-Core Version:    0.6.0
 */