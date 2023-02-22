package com.unity3d.player;

public abstract interface IPermissionRequestCallbacks
{
  public abstract void onPermissionDenied(String paramString);

  public abstract void onPermissionDeniedAndDontAskAgain(String paramString);

  public abstract void onPermissionGranted(String paramString);
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.IPermissionRequestCallbacks
 * JD-Core Version:    0.6.0
 */