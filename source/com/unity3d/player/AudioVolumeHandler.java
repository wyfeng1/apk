package com.unity3d.player;

import android.content.Context;

public class AudioVolumeHandler
  implements b.b
{
  private b a;

  AudioVolumeHandler(Context paramContext)
  {
    paramContext = new b(paramContext);
    this.a = paramContext;
    paramContext.a(this);
  }

  public final void a()
  {
    this.a.a();
    this.a = null;
  }

  public final native void onAudioVolumeChanged(int paramInt);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.AudioVolumeHandler
 * JD-Core Version:    0.6.0
 */