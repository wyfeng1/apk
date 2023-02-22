package com.unity3d.splash.services.core.cache;

public enum CacheDirectoryType
{
  static
  {
    CacheDirectoryType localCacheDirectoryType = new CacheDirectoryType("INTERNAL", 1);
    INTERNAL = localCacheDirectoryType;
    $VALUES = new CacheDirectoryType[] { EXTERNAL, localCacheDirectoryType };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheDirectoryType
 * JD-Core Version:    0.6.0
 */