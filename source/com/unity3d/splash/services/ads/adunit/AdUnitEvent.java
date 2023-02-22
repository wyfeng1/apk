package com.unity3d.splash.services.ads.adunit;

public enum AdUnitEvent
{
  static
  {
    ON_CREATE = new AdUnitEvent("ON_CREATE", 1);
    ON_RESUME = new AdUnitEvent("ON_RESUME", 2);
    ON_DESTROY = new AdUnitEvent("ON_DESTROY", 3);
    ON_PAUSE = new AdUnitEvent("ON_PAUSE", 4);
    KEY_DOWN = new AdUnitEvent("KEY_DOWN", 5);
    ON_RESTORE = new AdUnitEvent("ON_RESTORE", 6);
    ON_STOP = new AdUnitEvent("ON_STOP", 7);
    ON_FOCUS_GAINED = new AdUnitEvent("ON_FOCUS_GAINED", 8);
    AdUnitEvent localAdUnitEvent = new AdUnitEvent("ON_FOCUS_LOST", 9);
    ON_FOCUS_LOST = localAdUnitEvent;
    $VALUES = new AdUnitEvent[] { ON_START, ON_CREATE, ON_RESUME, ON_DESTROY, ON_PAUSE, KEY_DOWN, ON_RESTORE, ON_STOP, ON_FOCUS_GAINED, localAdUnitEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitEvent
 * JD-Core Version:    0.6.0
 */