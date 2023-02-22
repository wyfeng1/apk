package com.unity3d.player;

import android.os.Build.VERSION;

public class PlatformSupport
{
  static final boolean MARSHMALLOW_SUPPORT;
  static final boolean NOUGAT_SUPPORT;

  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = true;
    boolean bool2;
    if (i >= 23)
      bool2 = true;
    else
      bool2 = false;
    MARSHMALLOW_SUPPORT = bool2;
    if (Build.VERSION.SDK_INT >= 24)
      bool2 = bool1;
    else
      bool2 = false;
    NOUGAT_SUPPORT = bool2;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.PlatformSupport
 * JD-Core Version:    0.6.0
 */