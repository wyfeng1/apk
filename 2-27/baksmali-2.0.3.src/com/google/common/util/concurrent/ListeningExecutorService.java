package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract interface ListeningExecutorService extends ExecutorService
{
  public abstract <T> ListenableFuture<T> submit(Callable<T> paramCallable);
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.ListeningExecutorService
 * JD-Core Version:    0.6.0
 */