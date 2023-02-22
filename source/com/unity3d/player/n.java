package com.unity3d.player;

import android.os.Build;

final class n
  implements Thread.UncaughtExceptionHandler
{
  private volatile Thread.UncaughtExceptionHandler a;

  final boolean a()
  {
    monitorenter;
    try
    {
      Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
      if (localUncaughtExceptionHandler == this);
      for (int i = 0; ; i = 1)
      {
        return i;
        this.a = localUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public final void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    monitorenter;
    try
    {
      Error localError = new java/lang/Error;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append(String.format("FATAL EXCEPTION [%s]\n", new Object[] { paramThread.getName() }));
      localStringBuilder.append(String.format("Unity version     : %s\n", new Object[] { "2021.3.11f1c2" }));
      localStringBuilder.append(String.format("Device model      : %s %s\n", new Object[] { Build.MANUFACTURER, Build.MODEL }));
      localStringBuilder.append(String.format("Device fingerprint: %s\n", new Object[] { Build.FINGERPRINT }));
      localStringBuilder.append(String.format("Build Type        : %s\n", new Object[] { "Release" }));
      localStringBuilder.append(String.format("Scripting Backend : %s\n", new Object[] { "IL2CPP" }));
      localStringBuilder.append(String.format("ABI               : %s\n", new Object[] { Build.CPU_ABI }));
      localStringBuilder.append(String.format("Strip Engine Code : %s\n", new Object[] { Boolean.valueOf(true) }));
      localError.<init>(localStringBuilder.toString());
      localError.setStackTrace(new StackTraceElement[0]);
      localError.initCause(paramThrowable);
      this.a.uncaughtException(paramThread, localError);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      try
      {
        this.a.uncaughtException(paramThread, paramThrowable);
        monitorexit;
        return;
      }
      finally
      {
        paramThread = finally;
        monitorexit;
      }
    }
    throw paramThread;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.n
 * JD-Core Version:    0.6.0
 */