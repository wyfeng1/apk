package com.unity3d.splash.services.core.request;

import java.util.Map;

public abstract interface IWebRequestProgressListener
{
  public abstract void onRequestProgress(String paramString, long paramLong1, long paramLong2);

  public abstract void onRequestStart(String paramString, long paramLong, int paramInt, Map paramMap);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.IWebRequestProgressListener
 * JD-Core Version:    0.6.0
 */