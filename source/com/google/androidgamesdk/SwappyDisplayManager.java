package com.google.androidgamesdk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Display.Mode;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SwappyDisplayManager
  implements DisplayManager.DisplayListener
{
  private final boolean DEBUG = false;
  private final String LOG_TAG = "SwappyDisplayManager";
  private final long ONE_MS_IN_NS = 1000000L;
  private final long ONE_S_IN_NS = 1000000000L;
  private Activity mActivity;
  private long mCookie;
  private Display.Mode mCurrentMode;
  private a mLooper;
  private WindowManager mWindowManager;

  public SwappyDisplayManager(long paramLong, Activity paramActivity)
  {
    try
    {
      Object localObject1 = paramActivity.getPackageManager().getActivityInfo(paramActivity.getIntent().getComponent(), 128);
      if (((ActivityInfo)localObject1).metaData != null)
      {
        localObject1 = ((ActivityInfo)localObject1).metaData.getString("android.app.lib_name");
        if (localObject1 != null)
          System.loadLibrary((String)localObject1);
      }
    }
    finally
    {
      Log.e("SwappyDisplayManager", localObject2.getMessage());
    }
    this.mCookie = paramLong;
    this.mActivity = paramActivity;
    paramActivity = (WindowManager)paramActivity.getSystemService(WindowManager.class);
    this.mWindowManager = paramActivity;
    paramActivity = paramActivity.getDefaultDisplay();
    this.mCurrentMode = paramActivity.getMode();
    updateSupportedRefreshRates(paramActivity);
    paramActivity = (DisplayManager)this.mActivity.getSystemService(DisplayManager.class);
    monitorenter;
    try
    {
      a locala = new com/google/androidgamesdk/SwappyDisplayManager$a;
      locala.<init>(this, 0);
      this.mLooper = locala;
      locala.start();
      paramActivity.registerDisplayListener(this, this.mLooper.a);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramActivity;
  }

  private boolean modeMatchesCurrentResolution(Display.Mode paramMode)
  {
    return (paramMode.getPhysicalHeight() == this.mCurrentMode.getPhysicalHeight()) && (paramMode.getPhysicalWidth() == this.mCurrentMode.getPhysicalWidth());
  }

  private native void nOnRefreshPeriodChanged(long paramLong1, long paramLong2, long paramLong3, long paramLong4);

  private native void nSetSupportedRefreshPeriods(long paramLong, long[] paramArrayOfLong, int[] paramArrayOfInt);

  private void updateSupportedRefreshRates(Display paramDisplay)
  {
    Display.Mode[] arrayOfMode = paramDisplay.getSupportedModes();
    int i = 0;
    int j = 0;
    for (int k = 0; j < arrayOfMode.length; k = m)
    {
      m = k;
      if (modeMatchesCurrentResolution(arrayOfMode[j]))
        m = k + 1;
      j++;
    }
    long[] arrayOfLong = new long[k];
    paramDisplay = new int[k];
    int m = 0;
    k = i;
    while (k < arrayOfMode.length)
    {
      j = m;
      if (modeMatchesCurrentResolution(arrayOfMode[k]))
      {
        arrayOfLong[m] = ()(1.0E+009F / arrayOfMode[k].getRefreshRate());
        paramDisplay[m] = arrayOfMode[k].getModeId();
        j = m + 1;
      }
      k++;
      m = j;
    }
    nSetSupportedRefreshPeriods(this.mCookie, arrayOfLong, paramDisplay);
  }

  public void onDisplayAdded(int paramInt)
  {
  }

  public void onDisplayChanged(int paramInt)
  {
    monitorenter;
    try
    {
      Display localDisplay = this.mWindowManager.getDefaultDisplay();
      float f = localDisplay.getRefreshRate();
      Display.Mode localMode = localDisplay.getMode();
      int i = localMode.getPhysicalWidth();
      paramInt = this.mCurrentMode.getPhysicalWidth();
      int j = 1;
      if (i != paramInt)
        paramInt = 1;
      else
        paramInt = 0;
      if (localMode.getPhysicalHeight() != this.mCurrentMode.getPhysicalHeight())
        i = 1;
      else
        i = 0;
      if (f == this.mCurrentMode.getRefreshRate())
        j = 0;
      this.mCurrentMode = localMode;
      if ((paramInt | i) != 0)
        updateSupportedRefreshRates(localDisplay);
      if (j != 0)
      {
        long l1 = localDisplay.getAppVsyncOffsetNanos();
        long l2 = this.mWindowManager.getDefaultDisplay().getPresentationDeadlineNanos();
        long l3 = ()(1.0E+009F / f);
        nOnRefreshPeriodChanged(this.mCookie, l3, l1, l3 - (l2 - 1000000L));
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void onDisplayRemoved(int paramInt)
  {
  }

  public void setPreferredDisplayModeId(int paramInt)
  {
    this.mActivity.runOnUiThread(new Runnable(paramInt)
    {
      public final void run()
      {
        Window localWindow = SwappyDisplayManager.this.mActivity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localLayoutParams.preferredDisplayModeId = this.a;
        localWindow.setAttributes(localLayoutParams);
      }
    });
  }

  public void terminate()
  {
    this.mLooper.a.getLooper().quit();
  }

  private final class a extends Thread
  {
    public Handler a;
    private Lock c;
    private Condition d;

    private a()
    {
      this$1 = new ReentrantLock();
      this.c = SwappyDisplayManager.this;
      this.d = SwappyDisplayManager.this.newCondition();
    }

    public final void run()
    {
      Log.i("SwappyDisplayManager", "Starting looper thread");
      this.c.lock();
      Looper.prepare();
      this.a = new Handler();
      this.d.signal();
      this.c.unlock();
      Looper.loop();
      Log.i("SwappyDisplayManager", "Terminating looper thread");
    }

    public final void start()
    {
      this.c.lock();
      super.start();
      try
      {
        this.d.await();
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      this.c.unlock();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.google.androidgamesdk.SwappyDisplayManager
 * JD-Core Version:    0.6.0
 */