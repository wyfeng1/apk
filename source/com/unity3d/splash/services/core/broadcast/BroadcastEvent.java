package com.unity3d.splash.services.core.broadcast;

public enum BroadcastEvent
{
  static
  {
    BroadcastEvent localBroadcastEvent = new BroadcastEvent("ACTION", 0);
    ACTION = localBroadcastEvent;
    $VALUES = new BroadcastEvent[] { localBroadcastEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.broadcast.BroadcastEvent
 * JD-Core Version:    0.6.0
 */