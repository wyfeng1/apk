package com.unity3d.splash.services.core.lifecycle;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.ArrayList;

public class LifecycleListener
  implements Application.ActivityLifecycleCallbacks
{
  private ArrayList _events;

  public LifecycleListener(ArrayList paramArrayList)
  {
    this._events = paramArrayList;
  }

  public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
  {
    if ((this._events.contains("onActivityCreated")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.CREATED, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivityDestroyed(Activity paramActivity)
  {
    if ((this._events.contains("onActivityDestroyed")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.DESTROYED, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivityPaused(Activity paramActivity)
  {
    if ((this._events.contains("onActivityPaused")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.PAUSED, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivityResumed(Activity paramActivity)
  {
    if ((this._events.contains("onActivityResumed")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.RESUMED, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
  {
    if ((this._events.contains("onActivitySaveInstanceState")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.SAVE_INSTANCE_STATE, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivityStarted(Activity paramActivity)
  {
    if ((this._events.contains("onActivityStarted")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.STARTED, new Object[] { paramActivity.getClass().getName() });
  }

  public void onActivityStopped(Activity paramActivity)
  {
    if ((this._events.contains("onActivityStopped")) && (WebViewApp.getCurrentApp() != null))
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.STOPPED, new Object[] { paramActivity.getClass().getName() });
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.lifecycle.LifecycleListener
 * JD-Core Version:    0.6.0
 */