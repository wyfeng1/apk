package com.unity3d.player;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.System;

final class m
{
  private Context a;
  private b b;

  public m(Context paramContext)
  {
    this.a = paramContext;
  }

  public final void a()
  {
    if (this.b != null)
    {
      this.a.getContentResolver().unregisterContentObserver(this.b);
      this.b = null;
    }
  }

  public final void a(a parama, String paramString)
  {
    this.b = new b(new Handler(Looper.getMainLooper()), parama);
    this.a.getContentResolver().registerContentObserver(Settings.System.getUriFor(paramString), true, this.b);
  }

  public static abstract interface a
  {
    public abstract void b();
  }

  private final class b extends ContentObserver
  {
    private m.a b;

    public b(Handler parama, m.a arg3)
    {
      super();
      Object localObject;
      this.b = localObject;
    }

    public final boolean deliverSelfNotifications()
    {
      return super.deliverSelfNotifications();
    }

    public final void onChange(boolean paramBoolean)
    {
      m.a locala = this.b;
      if (locala != null)
        locala.b();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.m
 * JD-Core Version:    0.6.0
 */