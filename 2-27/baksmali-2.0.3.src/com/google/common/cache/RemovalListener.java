package com.google.common.cache;

public abstract interface RemovalListener<K, V>
{
  public abstract void onRemoval(RemovalNotification<K, V> paramRemovalNotification);
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.RemovalListener
 * JD-Core Version:    0.6.0
 */