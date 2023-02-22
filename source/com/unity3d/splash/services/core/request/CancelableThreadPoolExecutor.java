package com.unity3d.splash.services.core.request;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CancelableThreadPoolExecutor extends ThreadPoolExecutor
{
  private final List _activeRunnable = new LinkedList();

  public CancelableThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, LinkedBlockingQueue paramLinkedBlockingQueue)
  {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramLinkedBlockingQueue);
  }

  protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
  {
    monitorenter;
    try
    {
      super.afterExecute(paramRunnable, paramThrowable);
      this._activeRunnable.remove(paramRunnable);
      monitorexit;
      return;
    }
    finally
    {
      paramRunnable = finally;
      monitorexit;
    }
    throw paramRunnable;
  }

  protected void beforeExecute(Thread paramThread, Runnable paramRunnable)
  {
    monitorenter;
    try
    {
      super.beforeExecute(paramThread, paramRunnable);
      this._activeRunnable.add(paramRunnable);
      monitorexit;
      return;
    }
    finally
    {
      paramThread = finally;
      monitorexit;
    }
    throw paramThread;
  }

  public void cancel()
  {
    monitorenter;
    try
    {
      Iterator localIterator = this._activeRunnable.iterator();
      while (localIterator.hasNext())
      {
        Runnable localRunnable = (Runnable)localIterator.next();
        if (!(localRunnable instanceof WebRequestRunnable))
          continue;
        ((WebRequestRunnable)localRunnable).setCancelStatus(true);
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.CancelableThreadPoolExecutor
 * JD-Core Version:    0.6.0
 */