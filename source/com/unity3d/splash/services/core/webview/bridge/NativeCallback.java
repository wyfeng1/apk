package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class NativeCallback
{
  private static AtomicInteger _callbackCount = new AtomicInteger(0);
  private Method _callback;
  private String _id;

  public NativeCallback(Method paramMethod)
  {
    this._callback = paramMethod;
    paramMethod = new StringBuilder();
    paramMethod.append(this._callback.getName().toUpperCase(Locale.US));
    paramMethod.append("_");
    paramMethod.append(_callbackCount.getAndIncrement());
    this._id = paramMethod.toString();
  }

  public String getId()
  {
    return this._id;
  }

  public void invoke(String paramString, Object[] paramArrayOfObject)
  {
    try
    {
      CallbackStatus localCallbackStatus = CallbackStatus.valueOf(paramString);
      if (paramArrayOfObject == null)
      {
        paramString = new Object[1];
        paramString[0] = localCallbackStatus;
      }
      else
      {
        paramString = new ArrayList(Arrays.asList(paramArrayOfObject));
        paramString.add(0, localCallbackStatus);
        paramString = paramString.toArray();
      }
      this._callback.invoke(null, paramString);
      WebViewApp.getCurrentApp().removeCallback(this);
      return;
    }
    catch (java.lang.Exception paramString)
    {
      DeviceLog.error("Illegal status");
      WebViewApp.getCurrentApp().removeCallback(this);
    }
    throw paramString;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.NativeCallback
 * JD-Core Version:    0.6.0
 */