package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.sensorinfo.SensorInfoError;
import com.unity3d.splash.services.core.sensorinfo.SensorInfoListener;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import org.json.JSONObject;

public class SensorInfo
{
  @WebViewExposed
  public static void getAccelerometerData(WebViewCallback paramWebViewCallback)
  {
    JSONObject localJSONObject = SensorInfoListener.getAccelerometerData();
    if (localJSONObject != null)
    {
      paramWebViewCallback.invoke(new Object[] { localJSONObject });
      return;
    }
    paramWebViewCallback.error(SensorInfoError.ACCELEROMETER_DATA_NOT_AVAILABLE, new Object[0]);
  }

  @WebViewExposed
  public static void isAccelerometerActive(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(SensorInfoListener.isAccelerometerListenerActive()) });
  }

  @WebViewExposed
  public static void startAccelerometerUpdates(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(SensorInfoListener.startAccelerometerListener(paramInteger.intValue())) });
  }

  @WebViewExposed
  public static void stopAccelerometerUpdates(WebViewCallback paramWebViewCallback)
  {
    SensorInfoListener.stopAccelerometerListener();
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.SensorInfo
 * JD-Core Version:    0.6.0
 */