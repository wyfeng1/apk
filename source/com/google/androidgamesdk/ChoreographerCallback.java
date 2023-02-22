package com.google.androidgamesdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;

public class ChoreographerCallback
  implements Choreographer.FrameCallback
{
  private static final String LOG_TAG = "ChoreographerCallback";
  private long mCookie;
  private a mLooper;

  public ChoreographerCallback(long paramLong)
  {
    this.mCookie = paramLong;
    a locala = new a(0);
    this.mLooper = locala;
    locala.start();
  }

  public void doFrame(long paramLong)
  {
    nOnChoreographer(this.mCookie, paramLong);
  }

  public native void nOnChoreographer(long paramLong1, long paramLong2);

  public void postFrameCallback()
  {
    this.mLooper.a.post(new Runnable()
    {
      public final void run()
      {
        Choreographer.getInstance().postFrameCallback(ChoreographerCallback.this);
      }
    });
  }

  public void postFrameCallbackDelayed(long paramLong)
  {
    Choreographer.getInstance().postFrameCallbackDelayed(this, paramLong);
  }

  public void terminate()
  {
    this.mLooper.a.getLooper().quit();
  }

  private final class a extends Thread
  {
    public Handler a;

    private a()
    {
    }

    public final void run()
    {
      Log.i("ChoreographerCallback", "Starting looper thread");
      Looper.prepare();
      this.a = new Handler();
      Looper.loop();
      Log.i("ChoreographerCallback", "Terminating looper thread");
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.google.androidgamesdk.ChoreographerCallback
 * JD-Core Version:    0.6.0
 */