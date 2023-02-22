package com.unity3d.splash.mediation;

import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds.PlacementState;

public abstract interface IUnityAdsExtendedListener extends IUnityAdsListener
{
  public abstract void onUnityAdsClick(String paramString);

  public abstract void onUnityAdsPlacementStateChanged(String paramString, UnityAds.PlacementState paramPlacementState1, UnityAds.PlacementState paramPlacementState2);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.mediation.IUnityAdsExtendedListener
 * JD-Core Version:    0.6.0
 */