package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.WebViewApp;

public class WebViewHandler
  implements IAdUnitViewHandler
{
  public boolean create(AdUnitActivity paramAdUnitActivity)
  {
    return true;
  }

  public boolean destroy()
  {
    if ((WebViewApp.getCurrentApp() != null) && (WebViewApp.getCurrentApp().getWebView() != null))
      ViewUtilities.removeViewFromParent(WebViewApp.getCurrentApp().getWebView());
    return true;
  }

  public View getView()
  {
    if (WebViewApp.getCurrentApp() != null)
      return WebViewApp.getCurrentApp().getWebView();
    return null;
  }

  public void onCreate(AdUnitActivity paramAdUnitActivity, Bundle paramBundle)
  {
  }

  public void onDestroy(AdUnitActivity paramAdUnitActivity)
  {
    destroy();
  }

  public void onPause(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onResume(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onStart(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onStop(AdUnitActivity paramAdUnitActivity)
  {
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.WebViewHandler
 * JD-Core Version:    0.6.0
 */