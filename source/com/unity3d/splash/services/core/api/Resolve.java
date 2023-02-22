package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.request.IResolveHostListener;
import com.unity3d.splash.services.core.request.ResolveHostError;
import com.unity3d.splash.services.core.request.ResolveHostEvent;
import com.unity3d.splash.services.core.request.WebRequestThread;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class Resolve
{
  @WebViewExposed
  public static void resolve(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    if (WebRequestThread.resolve(paramString2, new IResolveHostListener(paramString1)
    {
      public final void onFailed(String paramString1, ResolveHostError paramResolveHostError, String paramString2)
      {
        if (WebViewApp.getCurrentApp() != null)
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.RESOLVE, ResolveHostEvent.FAILED, new Object[] { this.val$id, paramString1, paramResolveHostError.name(), paramString2 });
      }

      public final void onResolve(String paramString1, String paramString2)
      {
        if (WebViewApp.getCurrentApp() != null)
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.RESOLVE, ResolveHostEvent.COMPLETE, new Object[] { this.val$id, paramString1, paramString2 });
      }
    }))
    {
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
    }
    paramWebViewCallback.error(ResolveHostError.INVALID_HOST, new Object[] { paramString1 });
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Resolve
 * JD-Core Version:    0.6.0
 */