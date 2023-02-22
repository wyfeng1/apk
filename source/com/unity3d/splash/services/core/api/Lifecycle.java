package com.unity3d.splash.services.core.api;

import android.app.Application;
import com.unity3d.splash.services.core.lifecycle.LifecycleError;
import com.unity3d.splash.services.core.lifecycle.LifecycleListener;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.ArrayList;
import org.json.JSONArray;

public class Lifecycle
{
  private static LifecycleListener _listener;

  public static LifecycleListener getLifecycleListener()
  {
    return _listener;
  }

  @WebViewExposed
  public static void register(JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    if (ClientProperties.getApplication() != null)
    {
      if (getLifecycleListener() == null)
      {
        ArrayList localArrayList = new ArrayList();
        int i = 0;
        while (i < paramJSONArray.length())
          try
          {
            localArrayList.add((String)paramJSONArray.get(i));
            i++;
          }
          catch (org.json.JSONException paramJSONArray)
          {
            paramWebViewCallback.error(LifecycleError.JSON_ERROR, new Object[0]);
            return;
          }
        setLifecycleListener(new LifecycleListener(localArrayList));
        ClientProperties.getApplication().registerActivityLifecycleCallbacks(getLifecycleListener());
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      paramWebViewCallback.error(LifecycleError.LISTENER_NOT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(LifecycleError.APPLICATION_NULL, new Object[0]);
  }

  public static void setLifecycleListener(LifecycleListener paramLifecycleListener)
  {
    _listener = paramLifecycleListener;
  }

  @WebViewExposed
  public static void unregister(WebViewCallback paramWebViewCallback)
  {
    if (ClientProperties.getApplication() != null)
    {
      if (getLifecycleListener() != null)
      {
        ClientProperties.getApplication().unregisterActivityLifecycleCallbacks(getLifecycleListener());
        setLifecycleListener(null);
      }
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(LifecycleError.APPLICATION_NULL, new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Lifecycle
 * JD-Core Version:    0.6.0
 */