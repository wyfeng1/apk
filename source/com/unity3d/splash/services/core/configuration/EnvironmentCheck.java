package com.unity3d.splash.services.core.configuration;

import android.os.Build.VERSION;
import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.lang.reflect.Method;

public class EnvironmentCheck
{
  private static boolean hasJavascriptInterface(Method paramMethod)
  {
    if (Build.VERSION.SDK_INT < 17)
      return true;
    paramMethod = paramMethod.getAnnotations();
    if (paramMethod != null)
    {
      int i = paramMethod.length;
      for (int j = 0; j < i; j++)
        if ((paramMethod[j] instanceof JavascriptInterface))
          return true;
    }
    return false;
  }

  public static boolean isEnvironmentOk()
  {
    return (testProGuard()) && (testCacheDirectory());
  }

  public static boolean testCacheDirectory()
  {
    if (SdkProperties.getCacheDirectory() != null)
    {
      DeviceLog.debug("Unity Ads cache directory check OK");
      return true;
    }
    DeviceLog.error("Unity Ads cache directory check fail: no working cache directory available");
    return false;
  }

  public static boolean testProGuard()
  {
    try
    {
      localObject = Class.forName("com.unity3d.splash.services.core.webview.bridge.WebViewBridgeInterface");
      Method localMethod = ((Class)localObject).getMethod("handleInvocation", new Class[] { String.class });
      localObject = ((Class)localObject).getMethod("handleCallback", new Class[] { String.class, String.class, String.class });
      if ((hasJavascriptInterface(localMethod)) && (hasJavascriptInterface((Method)localObject)))
      {
        DeviceLog.debug("Unity Ads ProGuard check OK");
        return true;
      }
      DeviceLog.error("Unity Ads ProGuard check fail: missing @JavascriptInterface annotations in Unity Ads web bridge");
      return false;
    }
    catch (Exception localException)
    {
      localObject = new StringBuilder("Unknown exception during Unity Ads ProGuard check: ");
      ((StringBuilder)localObject).append(localException.getMessage());
      DeviceLog.exception(((StringBuilder)localObject).toString(), localException);
      return true;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localObject = "Unity Ads ProGuard check fail: Unity Ads web bridge methods not found";
      DeviceLog.exception((String)localObject, localNoSuchMethodException);
      return false;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (true)
        Object localObject = "Unity Ads ProGuard check fail: Unity Ads web bridge class not found";
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.EnvironmentCheck
 * JD-Core Version:    0.6.0
 */