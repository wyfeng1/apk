package com.google.common.collect;

import java.util.Comparator;

abstract interface SortedIterable<T> extends Iterable<T>
{
  public abstract Comparator<? super T> comparator();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.SortedIterable
 * JD-Core Version:    0.6.0
 */