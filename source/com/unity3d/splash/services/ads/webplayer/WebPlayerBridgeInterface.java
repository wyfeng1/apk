package com.unity3d.splash.services.ads.webplayer;

import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;

public class WebPlayerBridgeInterface
{
  private final String viewId;

  public WebPlayerBridgeInterface(String paramString)
  {
    this.viewId = paramString;
  }

  @JavascriptInterface
  public void handleEvent(String paramString)
  {
    if (WebViewApp.getCurrentApp() != null)
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.WEBPLAYER_EVENT, new Object[] { paramString, this.viewId });
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.webplayer.WebPlayerBridgeInterface
 * JD-Core Version:    0.6.0
 */