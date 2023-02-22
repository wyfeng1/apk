package com.unity3d.splash.services.core.request;

import android.os.ConditionVariable;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WebRequestThread
{
  private static int _corePoolSize = 1;
  private static long _keepAliveTime = 1000L;
  private static int _maximumPoolSize = 1;
  private static CancelableThreadPoolExecutor _pool;
  private static LinkedBlockingQueue _queue;
  private static boolean _ready = false;
  private static final Object _readyLock = new Object();

  public static void cancel()
  {
    monitorenter;
    try
    {
      if (_pool != null)
      {
        _pool.cancel();
        Iterator localIterator = _queue.iterator();
        while (localIterator.hasNext())
        {
          Runnable localRunnable = (Runnable)localIterator.next();
          if (!(localRunnable instanceof WebRequestRunnable))
            continue;
          ((WebRequestRunnable)localRunnable).setCancelStatus(true);
        }
        _queue.clear();
        _pool.purge();
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private static void init()
  {
    monitorenter;
    try
    {
      Object localObject1 = new java/util/concurrent/LinkedBlockingQueue;
      ((LinkedBlockingQueue)localObject1).<init>();
      _queue = (LinkedBlockingQueue)localObject1;
      localObject1 = new com/unity3d/splash/services/core/request/CancelableThreadPoolExecutor;
      ((CancelableThreadPoolExecutor)localObject1).<init>(_corePoolSize, _maximumPoolSize, _keepAliveTime, TimeUnit.MILLISECONDS, _queue);
      _pool = (CancelableThreadPoolExecutor)localObject1;
      ((CancelableThreadPoolExecutor)localObject1).prestartAllCoreThreads();
      localObject1 = _queue;
      ??? = new com/unity3d/splash/services/core/request/WebRequestThread$1;
      ((1)???).<init>();
      ((LinkedBlockingQueue)localObject1).add(???);
      boolean bool = _ready;
      if (!bool)
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
          monitorexit;
          return;
        }
      return;
    }
    finally
    {
      monitorexit;
    }
  }

  public static void request(String paramString, WebRequest.RequestType paramRequestType, Map paramMap, Integer paramInteger1, Integer paramInteger2, IWebRequestListener paramIWebRequestListener)
  {
    monitorenter;
    try
    {
      request(paramString, paramRequestType, paramMap, null, paramInteger1, paramInteger2, paramIWebRequestListener);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public static void request(String paramString1, WebRequest.RequestType paramRequestType, Map paramMap, String paramString2, Integer paramInteger1, Integer paramInteger2, IWebRequestListener paramIWebRequestListener)
  {
    monitorenter;
    try
    {
      if (!_ready)
        init();
      if ((paramString1 != null) && (paramString1.length() >= 3))
      {
        LinkedBlockingQueue localLinkedBlockingQueue = _queue;
        WebRequestRunnable localWebRequestRunnable = new com/unity3d/splash/services/core/request/WebRequestRunnable;
        localWebRequestRunnable.<init>(paramString1, paramRequestType.name(), paramString2, paramInteger1.intValue(), paramInteger2.intValue(), paramMap, paramIWebRequestListener);
        localLinkedBlockingQueue.add(localWebRequestRunnable);
        return;
      }
      paramIWebRequestListener.onFailed(paramString1, "Request is NULL or too short");
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramString1;
  }

  // ERROR //
  public static void reset()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 154	com/unity3d/splash/services/core/request/WebRequestThread:cancel	()V
    //   6: getstatic 44	com/unity3d/splash/services/core/request/WebRequestThread:_pool	Lcom/unity3d/splash/services/core/request/CancelableThreadPoolExecutor;
    //   9: ifnull +40 -> 49
    //   12: getstatic 44	com/unity3d/splash/services/core/request/WebRequestThread:_pool	Lcom/unity3d/splash/services/core/request/CancelableThreadPoolExecutor;
    //   15: invokevirtual 157	com/unity3d/splash/services/core/request/CancelableThreadPoolExecutor:shutdown	()V
    //   18: getstatic 44	com/unity3d/splash/services/core/request/WebRequestThread:_pool	Lcom/unity3d/splash/services/core/request/CancelableThreadPoolExecutor;
    //   21: ldc2_w 158
    //   24: getstatic 162	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
    //   27: invokevirtual 166	com/unity3d/splash/services/core/request/CancelableThreadPoolExecutor:awaitTermination	(JLjava/util/concurrent/TimeUnit;)Z
    //   30: pop
    //   31: getstatic 50	com/unity3d/splash/services/core/request/WebRequestThread:_queue	Ljava/util/concurrent/LinkedBlockingQueue;
    //   34: invokevirtual 76	java/util/concurrent/LinkedBlockingQueue:clear	()V
    //   37: aconst_null
    //   38: putstatic 44	com/unity3d/splash/services/core/request/WebRequestThread:_pool	Lcom/unity3d/splash/services/core/request/CancelableThreadPoolExecutor;
    //   41: aconst_null
    //   42: putstatic 50	com/unity3d/splash/services/core/request/WebRequestThread:_queue	Ljava/util/concurrent/LinkedBlockingQueue;
    //   45: iconst_0
    //   46: putstatic 39	com/unity3d/splash/services/core/request/WebRequestThread:_ready	Z
    //   49: ldc 2
    //   51: monitorexit
    //   52: return
    //   53: astore_0
    //   54: ldc 2
    //   56: monitorexit
    //   57: aload_0
    //   58: athrow
    //   59: astore_0
    //   60: goto -29 -> 31
    //
    // Exception table:
    //   from	to	target	type
    //   3	18	53	finally
    //   18	31	53	finally
    //   31	49	53	finally
    //   18	31	59	java/lang/InterruptedException
  }

  public static boolean resolve(String paramString, IResolveHostListener paramIResolveHostListener)
  {
    monitorenter;
    if (paramString != null);
    try
    {
      if (paramString.length() >= 3)
      {
        Thread localThread = new java/lang/Thread;
        2 local2 = new com/unity3d/splash/services/core/request/WebRequestThread$2;
        local2.<init>(paramString, paramIResolveHostListener);
        localThread.<init>(local2);
        localThread.start();
      }
      for (int i = 1; ; i = 0)
      {
        return i;
        paramIResolveHostListener.onFailed(paramString, ResolveHostError.INVALID_HOST, "Host is NULL");
      }
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public static void setConcurrentRequestCount(int paramInt)
  {
    monitorenter;
    try
    {
      _corePoolSize = paramInt;
      _maximumPoolSize = paramInt;
      if (_pool != null)
      {
        _pool.setCorePoolSize(paramInt);
        _pool.setMaximumPoolSize(_maximumPoolSize);
      }
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public static void setKeepAliveTime(long paramLong)
  {
    monitorenter;
    try
    {
      _keepAliveTime = paramLong;
      if (_pool != null)
        _pool.setKeepAliveTime(paramLong, TimeUnit.MILLISECONDS);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public static void setMaximumPoolSize(int paramInt)
  {
    monitorenter;
    try
    {
      _maximumPoolSize = paramInt;
      if (_pool != null)
        _pool.setMaximumPoolSize(paramInt);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.WebRequestThread
 * JD-Core Version:    0.6.0
 */