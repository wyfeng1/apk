package com.unity3d.splash.services.core.cache;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CacheThread extends Thread
{
  public static final int MSG_DOWNLOAD = 1;
  private static int _connectTimeout = 0;
  private static CacheThreadHandler _handler;
  private static int _progressInterval = 0;
  private static int _readTimeout = 0;
  private static boolean _ready = false;
  private static final Object _readyLock = new Object();

  static
  {
    _connectTimeout = 30000;
    _readTimeout = 30000;
    _progressInterval = 0;
  }

  public static void cancel()
  {
    if (!_ready)
      return;
    _handler.removeMessages(1);
    _handler.setCancelStatus(true);
  }

  public static void download(String paramString1, String paramString2, HashMap paramHashMap, boolean paramBoolean)
  {
    monitorenter;
    try
    {
      if (!_ready)
        init();
      Bundle localBundle = new android/os/Bundle;
      localBundle.<init>();
      localBundle.putString("source", paramString1);
      localBundle.putString("target", paramString2);
      localBundle.putInt("connectTimeout", _connectTimeout);
      localBundle.putInt("readTimeout", _readTimeout);
      localBundle.putInt("progressInterval", _progressInterval);
      localBundle.putBoolean("append", paramBoolean);
      if (paramHashMap != null)
      {
        Iterator localIterator = paramHashMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          paramString2 = (String)localIterator.next();
          paramString1 = new String[((List)paramHashMap.get(paramString2)).size()];
          localBundle.putStringArray(paramString2, (String[])((List)paramHashMap.get(paramString2)).toArray(paramString1));
        }
      }
      paramString1 = new android/os/Message;
      paramString1.<init>();
      paramString1.what = 1;
      paramString1.setData(localBundle);
      _handler.setCancelStatus(false);
      _handler.sendMessage(paramString1);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramString1;
  }

  public static int getConnectTimeout()
  {
    return _connectTimeout;
  }

  public static int getProgressInterval()
  {
    return _progressInterval;
  }

  public static int getReadTimeout()
  {
    return _readTimeout;
  }

  private static void init()
  {
    CacheThread localCacheThread = new CacheThread();
    localCacheThread.setName("UnityAdsCacheThread");
    localCacheThread.start();
    while (!_ready)
      try
      {
        synchronized (_readyLock)
        {
          _readyLock.wait();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        DeviceLog.debug("Couldn't synchronize thread");
      }
  }

  public static boolean isActive()
  {
    if (!_ready)
      return false;
    return _handler.isActive();
  }

  public static void setConnectTimeout(int paramInt)
  {
    _connectTimeout = paramInt;
  }

  public static void setProgressInterval(int paramInt)
  {
    _progressInterval = paramInt;
  }

  public static void setReadTimeout(int paramInt)
  {
    _readTimeout = paramInt;
  }

  public void run()
  {
    Looper.prepare();
    _handler = new CacheThreadHandler();
    _ready = true;
    synchronized (_readyLock)
    {
      _readyLock.notify();
      Looper.loop();
      return;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheThread
 * JD-Core Version:    0.6.0
 */