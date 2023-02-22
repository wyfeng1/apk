package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds.FinishState;
import com.unity3d.splash.UnityAds.PlacementState;
import com.unity3d.splash.UnityAds.UnityAdsError;
import com.unity3d.splash.mediation.IUnityAdsExtendedListener;
import com.unity3d.splash.services.ads.properties.AdsProperties;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.Iterator;
import java.util.Set;

public class Listener
{
  @WebViewExposed
  public static void sendClickEvent(String paramString, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
        {
          IUnityAdsListener localIUnityAdsListener = (IUnityAdsListener)localIterator.next();
          if (!(localIUnityAdsListener instanceof IUnityAdsExtendedListener))
            continue;
          ((IUnityAdsExtendedListener)localIUnityAdsListener).onUnityAdsClick(this.val$placementId);
        }
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void sendErrorEvent(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString1, paramString2)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
        {
          IUnityAdsListener localIUnityAdsListener = (IUnityAdsListener)localIterator.next();
          if (!(localIUnityAdsListener instanceof IUnityAdsExtendedListener))
            continue;
          localIUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.valueOf(this.val$error), this.val$message);
        }
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void sendFinishEvent(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString1, paramString2)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
          ((IUnityAdsListener)localIterator.next()).onUnityAdsFinish(this.val$placementId, UnityAds.FinishState.valueOf(this.val$result));
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void sendPlacementStateChangedEvent(String paramString1, String paramString2, String paramString3, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString1, paramString2, paramString3)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
        {
          IUnityAdsListener localIUnityAdsListener = (IUnityAdsListener)localIterator.next();
          if (!(localIUnityAdsListener instanceof IUnityAdsExtendedListener))
            continue;
          ((IUnityAdsExtendedListener)localIUnityAdsListener).onUnityAdsPlacementStateChanged(this.val$placementId, UnityAds.PlacementState.valueOf(this.val$oldState), UnityAds.PlacementState.valueOf(this.val$newState));
        }
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void sendReadyEvent(String paramString, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
          ((IUnityAdsListener)localIterator.next()).onUnityAdsReady(this.val$placementId);
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void sendStartEvent(String paramString, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString)
    {
      public final void run()
      {
        Iterator localIterator = AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
          ((IUnityAdsListener)localIterator.next()).onUnityAdsStart(this.val$placementId);
      }
    });
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.api.Listener
 * JD-Core Version:    0.6.0
 */