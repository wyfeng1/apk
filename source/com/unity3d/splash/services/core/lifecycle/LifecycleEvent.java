package com.unity3d.splash.services.core.lifecycle;

public enum LifecycleEvent
{
  static
  {
    RESUMED = new LifecycleEvent("RESUMED", 2);
    PAUSED = new LifecycleEvent("PAUSED", 3);
    STOPPED = new LifecycleEvent("STOPPED", 4);
    SAVE_INSTANCE_STATE = new LifecycleEvent("SAVE_INSTANCE_STATE", 5);
    LifecycleEvent localLifecycleEvent = new LifecycleEvent("DESTROYED", 6);
    DESTROYED = localLifecycleEvent;
    $VALUES = new LifecycleEvent[] { CREATED, STARTED, RESUMED, PAUSED, STOPPED, SAVE_INSTANCE_STATE, localLifecycleEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.lifecycle.LifecycleEvent
 * JD-Core Version:    0.6.0
 */