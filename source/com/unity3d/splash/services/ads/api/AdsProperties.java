package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class AdsProperties
{
  @WebViewExposed
  public static void setShowTimeout(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.ads.properties.AdsProperties.setShowTimeout(paramInteger.intValue());
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.api.AdsProperties
 * JD-Core Version:    0.6.0
 */