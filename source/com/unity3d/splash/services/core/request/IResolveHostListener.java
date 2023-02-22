package com.unity3d.splash.services.core.request;

public abstract interface IResolveHostListener
{
  public abstract void onFailed(String paramString1, ResolveHostError paramResolveHostError, String paramString2);

  public abstract void onResolve(String paramString1, String paramString2);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.IResolveHostListener
 * JD-Core Version:    0.6.0
 */