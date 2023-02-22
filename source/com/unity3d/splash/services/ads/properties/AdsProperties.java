package com.unity3d.splash.services.ads.properties;

import com.unity3d.splash.IUnityAdsListener;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class AdsProperties
{
  private static Set _listeners = Collections.synchronizedSet(new LinkedHashSet());
  private static int _showTimeout = 5000;

  public static void addListener(IUnityAdsListener paramIUnityAdsListener)
  {
    if ((paramIUnityAdsListener != null) && (!_listeners.contains(paramIUnityAdsListener)))
      _listeners.add(paramIUnityAdsListener);
  }

  public static Set getListeners()
  {
    return _listeners;
  }

  public static int getShowTimeout()
  {
    return _showTimeout;
  }

  public static void removeListener(IUnityAdsListener paramIUnityAdsListener)
  {
    _listeners.remove(paramIUnityAdsListener);
  }

  public static void setShowTimeout(int paramInt)
  {
    _showTimeout = paramInt;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.properties.AdsProperties
 * JD-Core Version:    0.6.0
 */