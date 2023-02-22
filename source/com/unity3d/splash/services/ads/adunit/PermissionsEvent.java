package com.unity3d.splash.services.ads.adunit;

public enum PermissionsEvent
{
  static
  {
    PermissionsEvent localPermissionsEvent = new PermissionsEvent("PERMISSIONS_ERROR", 1);
    PERMISSIONS_ERROR = localPermissionsEvent;
    $VALUES = new PermissionsEvent[] { PERMISSIONS_RESULT, localPermissionsEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.PermissionsEvent
 * JD-Core Version:    0.6.0
 */