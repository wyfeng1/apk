package com.google.common.collect;

import java.util.Iterator;

public abstract interface PeekingIterator<E> extends Iterator<E>
{
  public abstract E peek();

  public abstract E next();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.PeekingIterator
 * JD-Core Version:    0.6.0
 */