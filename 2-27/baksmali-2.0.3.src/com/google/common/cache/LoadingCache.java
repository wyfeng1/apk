package com.google.common.cache;

import com.google.common.base.Function;

public abstract interface LoadingCache<K, V> extends Function<K, V>, Cache<K, V>
{
  public abstract V getUnchecked(K paramK);
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.LoadingCache
 * JD-Core Version:    0.6.0
 */