package com.unity3d.player;

import android.util.Log;

final class f
{
  protected static boolean a = false;

  protected static void Log(int paramInt, String paramString)
  {
    if (a)
      return;
    if (paramInt == 6)
      Log.e("Unity", paramString);
    if (paramInt == 5)
      Log.w("Unity", paramString);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.f
 * JD-Core Version:    0.6.0
 */