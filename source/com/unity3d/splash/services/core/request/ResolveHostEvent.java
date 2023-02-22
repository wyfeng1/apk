package com.unity3d.splash.services.core.request;

public enum ResolveHostEvent
{
  static
  {
    ResolveHostEvent localResolveHostEvent = new ResolveHostEvent("FAILED", 1);
    FAILED = localResolveHostEvent;
    $VALUES = new ResolveHostEvent[] { COMPLETE, localResolveHostEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.ResolveHostEvent
 * JD-Core Version:    0.6.0
 */