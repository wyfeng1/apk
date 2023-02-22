package com.unity3d.splash.services.core.broadcast;

public enum BroadcastError
{
  static
  {
    BroadcastError localBroadcastError = new BroadcastError("JSON_ERROR", 0);
    JSON_ERROR = localBroadcastError;
    $VALUES = new BroadcastError[] { localBroadcastError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.broadcast.BroadcastError
 * JD-Core Version:    0.6.0
 */