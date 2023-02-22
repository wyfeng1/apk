package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class Connectivity
{
  @WebViewExposed
  public static void setConnectionMonitoring(Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    ConnectivityMonitor.setConnectionMonitoring(paramBoolean.booleanValue());
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Connectivity
 * JD-Core Version:    0.6.0
 */