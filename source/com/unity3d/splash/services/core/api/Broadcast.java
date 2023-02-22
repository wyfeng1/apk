package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.broadcast.BroadcastError;
import com.unity3d.splash.services.core.broadcast.BroadcastMonitor;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import org.json.JSONArray;

public class Broadcast
{
  @WebViewExposed
  public static void addBroadcastListener(String paramString1, String paramString2, JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    try
    {
      if (paramJSONArray.length() > 0)
      {
        String[] arrayOfString = new String[paramJSONArray.length()];
        for (int i = 0; i < paramJSONArray.length(); i++)
          arrayOfString[i] = paramJSONArray.getString(i);
        BroadcastMonitor.addBroadcastListener(paramString1, paramString2, arrayOfString);
      }
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    catch (org.json.JSONException paramString1)
    {
      paramWebViewCallback.error(BroadcastError.JSON_ERROR, new Object[0]);
    }
  }

  @WebViewExposed
  public static void addBroadcastListener(String paramString, JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    addBroadcastListener(paramString, null, paramJSONArray, paramWebViewCallback);
  }

  @WebViewExposed
  public static void removeAllBroadcastListeners(WebViewCallback paramWebViewCallback)
  {
    BroadcastMonitor.removeAllBroadcastListeners();
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void removeBroadcastListener(String paramString, WebViewCallback paramWebViewCallback)
  {
    BroadcastMonitor.removeBroadcastListener(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Broadcast
 * JD-Core Version:    0.6.0
 */