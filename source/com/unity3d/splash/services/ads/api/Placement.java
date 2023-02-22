package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class Placement
{
  @WebViewExposed
  public static void setDefaultBannerPlacement(String paramString, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.ads.placement.Placement.setDefaultBannerPlacement(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setDefaultPlacement(String paramString, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.ads.placement.Placement.setDefaultPlacement(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setPlacementState(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.ads.placement.Placement.setPlacementState(paramString1, paramString2);
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.api.Placement
 * JD-Core Version:    0.6.0
 */