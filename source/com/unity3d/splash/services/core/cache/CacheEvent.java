package com.unity3d.splash.services.core.cache;

public enum CacheEvent
{
  static
  {
    DOWNLOAD_PROGRESS = new CacheEvent("DOWNLOAD_PROGRESS", 1);
    DOWNLOAD_END = new CacheEvent("DOWNLOAD_END", 2);
    DOWNLOAD_STOPPED = new CacheEvent("DOWNLOAD_STOPPED", 3);
    CacheEvent localCacheEvent = new CacheEvent("DOWNLOAD_ERROR", 4);
    DOWNLOAD_ERROR = localCacheEvent;
    $VALUES = new CacheEvent[] { DOWNLOAD_STARTED, DOWNLOAD_PROGRESS, DOWNLOAD_END, DOWNLOAD_STOPPED, localCacheEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheEvent
 * JD-Core Version:    0.6.0
 */