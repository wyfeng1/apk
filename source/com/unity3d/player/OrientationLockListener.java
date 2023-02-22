package com.unity3d.player;

import android.content.Context;
import android.provider.Settings.System;

public class OrientationLockListener
  implements m.a
{
  private m a;
  private Context b;

  OrientationLockListener(Context paramContext)
  {
    this.b = paramContext;
    this.a = new m(paramContext);
    nativeUpdateOrientationLockState(Settings.System.getInt(this.b.getContentResolver(), "accelerometer_rotation", 0));
    this.a.a(this, "accelerometer_rotation");
  }

  public final void a()
  {
    this.a.a();
    this.a = null;
  }

  public final void b()
  {
    nativeUpdateOrientationLockState(Settings.System.getInt(this.b.getContentResolver(), "accelerometer_rotation", 0));
  }

  public final native void nativeUpdateOrientationLockState(int paramInt);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.OrientationLockListener
 * JD-Core Version:    0.6.0
 */