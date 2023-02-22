package com.unity3d.splash.services.core.log;

public class DeviceLogLevel
{
  private static final String LOG_TAG = "UnityAds";
  private String _receivingMethodName = null;

  public DeviceLogLevel(String paramString)
  {
    this._receivingMethodName = paramString;
  }

  public String getLogTag()
  {
    return "UnityAds";
  }

  public String getReceivingMethodName()
  {
    return this._receivingMethodName;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.log.DeviceLogLevel
 * JD-Core Version:    0.6.0
 */