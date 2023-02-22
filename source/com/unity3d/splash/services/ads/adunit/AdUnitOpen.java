package com.unity3d.splash.services.ads.adunit;

import android.os.ConditionVariable;
import com.unity3d.splash.services.ads.properties.AdsProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.bridge.CallbackStatus;
import java.lang.reflect.Method;
import org.json.JSONObject;

public class AdUnitOpen
{
  private static ConditionVariable _waitShowStatus;

  public static boolean open(String paramString, JSONObject paramJSONObject)
  {
    monitorenter;
    try
    {
      Method localMethod = AdUnitOpen.class.getMethod("showCallback", new Class[] { CallbackStatus.class });
      ConditionVariable localConditionVariable = new android/os/ConditionVariable;
      localConditionVariable.<init>();
      _waitShowStatus = localConditionVariable;
      WebViewApp.getCurrentApp().invokeMethod("webview", "show", localMethod, new Object[] { paramString, paramJSONObject });
      boolean bool = _waitShowStatus.block(AdsProperties.getShowTimeout());
      _waitShowStatus = null;
      monitorexit;
      return bool;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public static void showCallback(CallbackStatus paramCallbackStatus)
  {
    if ((_waitShowStatus != null) && (paramCallbackStatus.equals(CallbackStatus.OK)))
      _waitShowStatus.open();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitOpen
 * JD-Core Version:    0.6.0
 */