package com.unity3d.splash.services.ads.placement;

import com.unity3d.splash.UnityAds.PlacementState;
import java.util.HashMap;

public class Placement
{
  private static String _defaultBannerPlacement;
  private static String _defaultPlacement;
  private static HashMap _placementReadyMap;

  private static UnityAds.PlacementState currentState(String paramString)
  {
    HashMap localHashMap = _placementReadyMap;
    if ((localHashMap != null) && (localHashMap.containsKey(paramString)))
      return (UnityAds.PlacementState)_placementReadyMap.get(paramString);
    return UnityAds.PlacementState.NOT_AVAILABLE;
  }

  public static String getDefaultBannerPlacement()
  {
    return _defaultBannerPlacement;
  }

  public static String getDefaultPlacement()
  {
    return _defaultPlacement;
  }

  public static UnityAds.PlacementState getPlacementState()
  {
    String str = _defaultPlacement;
    if (str == null)
      return UnityAds.PlacementState.NOT_AVAILABLE;
    return getPlacementState(str);
  }

  public static UnityAds.PlacementState getPlacementState(String paramString)
  {
    return currentState(paramString);
  }

  public static boolean isReady()
  {
    return getPlacementState() == UnityAds.PlacementState.READY;
  }

  public static boolean isReady(String paramString)
  {
    return getPlacementState(paramString) == UnityAds.PlacementState.READY;
  }

  public static void reset()
  {
    _placementReadyMap = null;
    _defaultPlacement = null;
  }

  public static void setDefaultBannerPlacement(String paramString)
  {
    _defaultBannerPlacement = paramString;
  }

  public static void setDefaultPlacement(String paramString)
  {
    _defaultPlacement = paramString;
  }

  public static void setPlacementState(String paramString1, String paramString2)
  {
    if (_placementReadyMap == null)
      _placementReadyMap = new HashMap();
    _placementReadyMap.put(paramString1, UnityAds.PlacementState.valueOf(paramString2));
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.placement.Placement
 * JD-Core Version:    0.6.0
 */