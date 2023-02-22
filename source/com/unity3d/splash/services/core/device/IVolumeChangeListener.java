package com.unity3d.splash.services.core.device;

public abstract interface IVolumeChangeListener
{
  public abstract int getStreamType();

  public abstract void onVolumeChanged(int paramInt);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.IVolumeChangeListener
 * JD-Core Version:    0.6.0
 */