package com.unity3d.splash.services.core.request;

public enum WebRequestEvent
{
  static
  {
    WebRequestEvent localWebRequestEvent = new WebRequestEvent("FAILED", 1);
    FAILED = localWebRequestEvent;
    $VALUES = new WebRequestEvent[] { COMPLETE, localWebRequestEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.WebRequestEvent
 * JD-Core Version:    0.6.0
 */