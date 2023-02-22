package com.unity3d.splash;

public abstract interface IUnityAdsListener
{
  public abstract void onUnityAdsError(UnityAds.UnityAdsError paramUnityAdsError, String paramString);

  public abstract void onUnityAdsFinish(String paramString, UnityAds.FinishState paramFinishState);

  public abstract void onUnityAdsReady(String paramString);

  public abstract void onUnityAdsStart(String paramString);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.IUnityAdsListener
 * JD-Core Version:    0.6.0
 */