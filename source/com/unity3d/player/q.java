package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class q
{
  private UnityPlayer a = null;
  private Context b = null;
  private a c;
  private final Semaphore d = new Semaphore(0);
  private final Lock e = new ReentrantLock();
  private p f = null;
  private int g = 2;
  private boolean h = false;
  private boolean i = false;

  q(UnityPlayer paramUnityPlayer)
  {
    this.a = paramUnityPlayer;
  }

  private void d()
  {
    Object localObject = this.f;
    if (localObject != null)
    {
      this.a.removeViewFromPlayer((View)localObject);
      this.i = false;
      this.f.destroyPlayer();
      this.f = null;
      localObject = this.c;
      if (localObject != null)
        ((a)localObject).a();
    }
  }

  public final void a()
  {
    this.e.lock();
    p localp = this.f;
    if (localp != null)
      if (this.g == 0)
      {
        localp.CancelOnPrepare();
      }
      else if (this.i)
      {
        boolean bool = localp.a();
        this.h = bool;
        if (!bool)
          this.f.pause();
      }
    this.e.unlock();
  }

  public final boolean a(Context paramContext, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong1, long paramLong2, a parama)
  {
    this.e.lock();
    this.c = parama;
    this.b = paramContext;
    this.d.drainPermits();
    this.g = 2;
    runOnUiThread(new Runnable(paramString, paramInt1, paramInt2, paramInt3, paramBoolean, paramLong1, paramLong2)
    {
      public final void run()
      {
        if (q.a(q.this) != null)
        {
          f.Log(5, "Video already playing");
          q.a(q.this, 2);
          q.b(q.this).release();
          return;
        }
        q.a(q.this, new p(q.c(q.this), this.a, this.b, this.c, this.d, this.e, this.f, this.g, new p.a()
        {
          public final void a(int paramInt)
          {
            q.d(q.this).lock();
            q.a(q.this, paramInt);
            if ((paramInt == 3) && (q.e(q.this)))
              q.this.runOnUiThread(new Runnable()
              {
                public final void run()
                {
                  q.f(q.this);
                  q.g(q.this).resume();
                }
              });
            if (paramInt != 0)
              q.b(q.this).release();
            q.d(q.this).unlock();
          }
        }));
        if (q.a(q.this) != null)
          q.g(q.this).addView(q.a(q.this));
      }
    });
    paramBoolean = false;
    try
    {
      this.e.unlock();
      this.d.acquire();
      this.e.lock();
      paramInt1 = this.g;
      if (paramInt1 != 2)
        paramBoolean = true;
    }
    catch (java.lang.InterruptedException paramContext)
    {
    }
    runOnUiThread(new Runnable()
    {
      public final void run()
      {
        q.g(q.this).pause();
      }
    });
    if ((paramBoolean) && (this.g != 3))
      paramContext = new Runnable()
      {
        public final void run()
        {
          if (q.a(q.this) != null)
          {
            q.g(q.this).addViewToPlayer(q.a(q.this), true);
            q.h(q.this);
            q.a(q.this).requestFocus();
          }
        }
      };
    else
      paramContext = new Runnable()
      {
        public final void run()
        {
          q.f(q.this);
          q.g(q.this).resume();
        }
      };
    runOnUiThread(paramContext);
    this.e.unlock();
    return paramBoolean;
  }

  public final void b()
  {
    this.e.lock();
    p localp = this.f;
    if ((localp != null) && (this.i) && (!this.h))
      localp.start();
    this.e.unlock();
  }

  public final void c()
  {
    this.e.lock();
    p localp = this.f;
    if (localp != null)
      localp.updateVideoLayout();
    this.e.unlock();
  }

  protected final void runOnUiThread(Runnable paramRunnable)
  {
    Context localContext = this.b;
    if ((localContext instanceof Activity))
    {
      ((Activity)localContext).runOnUiThread(paramRunnable);
      return;
    }
    f.Log(5, "Not running from an Activity; Ignoring execution request...");
  }

  public static abstract interface a
  {
    public abstract void a();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.q
 * JD-Core Version:    0.6.0
 */