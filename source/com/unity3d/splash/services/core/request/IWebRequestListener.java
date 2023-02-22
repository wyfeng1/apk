package com.unity3d.splash.services.core.request;

import java.util.Map;

public abstract interface IWebRequestListener
{
  public abstract void onComplete(String paramString1, String paramString2, int paramInt, Map paramMap);

  public abstract void onFailed(String paramString1, String paramString2);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.IWebRequestListener
 * JD-Core Version:    0.6.0
 */