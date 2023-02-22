package com.unity3d.player;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings.System;

final class b
{
  private final Context a;
  private final AudioManager b;
  private a c;

  public b(Context paramContext)
  {
    this.a = paramContext;
    this.b = ((AudioManager)paramContext.getSystemService("audio"));
  }

  public final void a()
  {
    if (this.c != null)
    {
      this.a.getContentResolver().unregisterContentObserver(this.c);
      this.c = null;
    }
  }

  public final void a(b paramb)
  {
    this.c = new a(new Handler(), this.b, 3, paramb);
    this.a.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.c);
  }

  private final class a extends ContentObserver
  {
    private final b.b b;
    private final AudioManager c;
    private final int d;
    private int e;

    public a(Handler paramAudioManager, AudioManager paramInt, int paramb, b.b arg5)
    {
      super();
      this.c = paramInt;
      this.d = 3;
      Object localObject;
      this.b = localObject;
      this.e = paramInt.getStreamVolume(3);
    }

    public final boolean deliverSelfNotifications()
    {
      return super.deliverSelfNotifications();
    }

    public final void onChange(boolean paramBoolean, Uri paramUri)
    {
      paramUri = this.c;
      if ((paramUri != null) && (this.b != null))
      {
        int i = paramUri.getStreamVolume(this.d);
        if (i != this.e)
        {
          this.e = i;
          this.b.onAudioVolumeChanged(i);
        }
      }
    }
  }

  public static abstract interface b
  {
    public abstract void onAudioVolumeChanged(int paramInt);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.b
 * JD-Core Version:    0.6.0
 */