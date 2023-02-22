package com.unity3d.splash.services.ads;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds.FinishState;
import com.unity3d.splash.UnityAds.PlacementState;
import com.unity3d.splash.UnityAds.UnityAdsError;
import com.unity3d.splash.services.IUnityServicesListener;
import com.unity3d.splash.services.UnityServices;
import com.unity3d.splash.services.UnityServices.UnityServicesError;
import com.unity3d.splash.services.ads.adunit.AdUnitOpen;
import com.unity3d.splash.services.ads.load.LoadModule;
import com.unity3d.splash.services.ads.placement.Placement;
import com.unity3d.splash.services.ads.properties.AdsProperties;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public final class UnityAdsImplementation
{
  public static void addListener(IUnityAdsListener paramIUnityAdsListener)
  {
    AdsProperties.addListener(paramIUnityAdsListener);
  }

  public static boolean getDebugMode()
  {
    return UnityServices.getDebugMode();
  }

  public static String getDefaultPlacement()
  {
    return Placement.getDefaultPlacement();
  }

  @Deprecated
  public static IUnityAdsListener getListener()
  {
    Iterator localIterator = AdsProperties.getListeners().iterator();
    if (localIterator.hasNext())
      return (IUnityAdsListener)localIterator.next();
    return null;
  }

  public static UnityAds.PlacementState getPlacementState()
  {
    if ((isSupported()) && (isInitialized()))
      return Placement.getPlacementState();
    return UnityAds.PlacementState.NOT_AVAILABLE;
  }

  public static UnityAds.PlacementState getPlacementState(String paramString)
  {
    if ((isSupported()) && (isInitialized()) && (paramString != null))
      return Placement.getPlacementState(paramString);
    return UnityAds.PlacementState.NOT_AVAILABLE;
  }

  public static String getVersion()
  {
    return UnityServices.getVersion();
  }

  private static void handleShowError(String paramString1, UnityAds.UnityAdsError paramUnityAdsError, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder("Unity Ads show failed: ");
    localStringBuilder.append(paramString2);
    paramString2 = localStringBuilder.toString();
    DeviceLog.error(paramString2);
    Utilities.runOnUiThread(new Runnable(paramUnityAdsError, paramString2, paramString1)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
        {
          IUnityAdsListener localIUnityAdsListener = (IUnityAdsListener)localIterator.next();
          localIUnityAdsListener.onUnityAdsError(this.val$error, this.val$errorMessage);
          String str = this.val$placementId;
          if (str != null)
          {
            localIUnityAdsListener.onUnityAdsFinish(str, UnityAds.FinishState.ERROR);
            continue;
          }
          localIUnityAdsListener.onUnityAdsFinish("", UnityAds.FinishState.ERROR);
        }
      }
    });
  }

  public static void initialize(Activity paramActivity, String paramString, IUnityAdsListener paramIUnityAdsListener)
  {
    initialize(paramActivity, paramString, paramIUnityAdsListener, false);
  }

  public static void initialize(Activity paramActivity, String paramString, IUnityAdsListener paramIUnityAdsListener, boolean paramBoolean)
  {
    initialize(paramActivity, paramString, paramIUnityAdsListener, paramBoolean, false);
  }

  public static void initialize(Activity paramActivity, String paramString, IUnityAdsListener paramIUnityAdsListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    DeviceLog.entered();
    addListener(paramIUnityAdsListener);
    UnityServices.initialize(paramActivity, paramString, new IUnityServicesListener(paramIUnityAdsListener)
    {
      public final void onUnityServicesError(UnityServices.UnityServicesError paramUnityServicesError, String paramString)
      {
        if (paramUnityServicesError == UnityServices.UnityServicesError.INIT_SANITY_CHECK_FAIL)
        {
          this.val$listener.onUnityAdsError(UnityAds.UnityAdsError.INIT_SANITY_CHECK_FAIL, paramString);
          return;
        }
        if (paramUnityServicesError == UnityServices.UnityServicesError.INVALID_ARGUMENT)
          this.val$listener.onUnityAdsError(UnityAds.UnityAdsError.INVALID_ARGUMENT, paramString);
      }
    }
    , paramBoolean1, paramBoolean2);
  }

  public static boolean isInitialized()
  {
    return UnityServices.isInitialized();
  }

  public static boolean isReady()
  {
    return (isSupported()) && (isInitialized()) && (Placement.isReady());
  }

  public static boolean isReady(String paramString)
  {
    return (isSupported()) && (isInitialized()) && (paramString != null) && (Placement.isReady(paramString));
  }

  public static boolean isSupported()
  {
    return UnityServices.isSupported();
  }

  public static void load(String paramString)
  {
    LoadModule.getInstance().load(paramString);
  }

  public static void removeListener(IUnityAdsListener paramIUnityAdsListener)
  {
    AdsProperties.removeListener(paramIUnityAdsListener);
  }

  public static void setDebugMode(boolean paramBoolean)
  {
    UnityServices.setDebugMode(paramBoolean);
  }

  @Deprecated
  public static void setListener(IUnityAdsListener paramIUnityAdsListener)
  {
    AdsProperties.addListener(paramIUnityAdsListener);
  }

  public static void show(Activity paramActivity)
  {
    if (Placement.getDefaultPlacement() != null)
    {
      show(paramActivity, Placement.getDefaultPlacement());
      return;
    }
    handleShowError("", UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads default placement is not initialized");
  }

  public static void show(Activity paramActivity, String paramString)
  {
    if (paramActivity == null)
    {
      handleShowError(paramString, UnityAds.UnityAdsError.INVALID_ARGUMENT, "Activity must not be null");
      return;
    }
    if (isReady(paramString))
    {
      localObject = new StringBuilder("Unity Ads opening new ad unit for placement ");
      ((StringBuilder)localObject).append(paramString);
      DeviceLog.info(((StringBuilder)localObject).toString());
      ClientProperties.setActivity(paramActivity);
      new Thread(new Runnable(paramActivity, paramString)
      {
        public final void run()
        {
          Display localDisplay = ((WindowManager)this.val$activity.getSystemService("window")).getDefaultDisplay();
          JSONObject localJSONObject1 = new JSONObject();
          try
          {
            localJSONObject1.put("requestedOrientation", this.val$activity.getRequestedOrientation());
            JSONObject localJSONObject2 = new org/json/JSONObject;
            localJSONObject2.<init>();
            localJSONObject2.put("rotation", localDisplay.getRotation());
            Point localPoint = new android/graphics/Point;
            localPoint.<init>();
            localDisplay.getSize(localPoint);
            localJSONObject2.put("width", localPoint.x);
            localJSONObject2.put("height", localPoint.y);
            localJSONObject1.put("display", localJSONObject2);
          }
          catch (JSONException localJSONException)
          {
            DeviceLog.exception("JSON error while constructing show options", localJSONException);
          }
          try
          {
            if (!AdUnitOpen.open(this.val$placementId, localJSONObject1))
              UnityAdsImplementation.access$000(this.val$placementId, UnityAds.UnityAdsError.INTERNAL_ERROR, "Webapp timeout, shutting down Unity Ads");
            return;
          }
          catch (NoSuchMethodException localNoSuchMethodException)
          {
            DeviceLog.exception("Could not get callback method", localNoSuchMethodException);
            UnityAdsImplementation.access$000(this.val$placementId, UnityAds.UnityAdsError.SHOW_ERROR, "Could not get com.unity3d.ads.properties.showCallback method");
          }
        }
      }).start();
      return;
    }
    if (!isSupported())
    {
      handleShowError(paramString, UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads is not supported for this device");
      return;
    }
    if (!isInitialized())
    {
      handleShowError(paramString, UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads is not initialized");
      return;
    }
    Object localObject = UnityAds.UnityAdsError.SHOW_ERROR;
    paramActivity = new StringBuilder("Placement \"");
    paramActivity.append(paramString);
    paramActivity.append("\" is not ready");
    handleShowError(paramString, (UnityAds.UnityAdsError)localObject, paramActivity.toString());
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.UnityAdsImplementation
 * JD-Core Version:    0.6.0
 */