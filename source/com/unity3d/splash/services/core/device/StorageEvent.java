package com.unity3d.splash.services.core.device;

public enum StorageEvent
{
  static
  {
    DELETE = new StorageEvent("DELETE", 1);
    CLEAR = new StorageEvent("CLEAR", 2);
    WRITE = new StorageEvent("WRITE", 3);
    READ = new StorageEvent("READ", 4);
    StorageEvent localStorageEvent = new StorageEvent("INIT", 5);
    INIT = localStorageEvent;
    $VALUES = new StorageEvent[] { SET, DELETE, CLEAR, WRITE, READ, localStorageEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.StorageEvent
 * JD-Core Version:    0.6.0
 */