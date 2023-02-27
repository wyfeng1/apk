/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*     */ 
/*     */ public abstract class AbstractFuture<V>
/*     */   implements ListenableFuture<V>
/*     */ {
/*  68 */   private final Sync<V> sync = new Sync();
/*     */ 
/*  71 */   private final ExecutionList executionList = new ExecutionList();
/*     */ 
/*     */   public V get(long timeout, TimeUnit unit)
/*     */     throws InterruptedException, TimeoutException, ExecutionException
/*     */   {
/*  96 */     return this.sync.get(unit.toNanos(timeout));
/*     */   }
/*     */ 
/*     */   public V get()
/*     */     throws InterruptedException, ExecutionException
/*     */   {
/* 116 */     return this.sync.get();
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/* 121 */     return this.sync.isDone();
/*     */   }
/*     */ 
/*     */   public boolean isCancelled()
/*     */   {
/* 126 */     return this.sync.isCancelled();
/*     */   }
/*     */ 
/*     */   public boolean cancel(boolean mayInterruptIfRunning)
/*     */   {
/* 131 */     if (!this.sync.cancel(mayInterruptIfRunning)) {
/* 132 */       return false;
/*     */     }
/* 134 */     this.executionList.execute();
/* 135 */     if (mayInterruptIfRunning) {
/* 136 */       interruptTask();
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   protected void interruptTask()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addListener(Runnable listener, Executor exec)
/*     */   {
/* 170 */     this.executionList.add(listener, exec);
/*     */   }
/*     */ 
/*     */   protected boolean set(V value)
/*     */   {
/* 183 */     boolean result = this.sync.set(value);
/* 184 */     if (result) {
/* 185 */       this.executionList.execute();
/*     */     }
/* 187 */     return result;
/*     */   }
/*     */ 
/*     */   protected boolean setException(Throwable throwable)
/*     */   {
/* 201 */     boolean result = this.sync.setException((Throwable)Preconditions.checkNotNull(throwable));
/* 202 */     if (result) {
/* 203 */       this.executionList.execute();
/*     */     }
/*     */ 
/* 208 */     if ((throwable instanceof Error)) {
/* 209 */       throw ((Error)throwable);
/*     */     }
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */   static final CancellationException cancellationExceptionWithCause(String message, Throwable cause)
/*     */   {
/* 399 */     CancellationException exception = new CancellationException(message);
/* 400 */     exception.initCause(cause);
/* 401 */     return exception;
/*     */   }
/*     */ 
/*     */   static final class Sync<V> extends AbstractQueuedSynchronizer
/*     */   {
/*     */     private V value;
/*     */     private Throwable exception;
/*     */ 
/*     */     protected int tryAcquireShared(int ignored)
/*     */     {
/* 250 */       if (isDone()) {
/* 251 */         return 1;
/*     */       }
/* 253 */       return -1;
/*     */     }
/*     */ 
/*     */     protected boolean tryReleaseShared(int finalState)
/*     */     {
/* 262 */       setState(finalState);
/* 263 */       return true;
/*     */     }
/*     */ 
/*     */     V get(long nanos)
/*     */       throws TimeoutException, CancellationException, ExecutionException, InterruptedException
/*     */     {
/* 275 */       if (!tryAcquireSharedNanos(-1, nanos)) {
/* 276 */         throw new TimeoutException("Timeout waiting for task.");
/*     */       }
/*     */ 
/* 279 */       return getValue();
/*     */     }
/*     */ 
/*     */     V get()
/*     */       throws CancellationException, ExecutionException, InterruptedException
/*     */     {
/* 292 */       acquireSharedInterruptibly(-1);
/* 293 */       return getValue();
/*     */     }
/*     */ 
/*     */     private V getValue()
/*     */       throws CancellationException, ExecutionException
/*     */     {
/* 302 */       int state = getState();
/* 303 */       switch (state) {
/*     */       case 2:
/* 305 */         if (this.exception != null) {
/* 306 */           throw new ExecutionException(this.exception);
/*     */         }
/* 308 */         return this.value;
/*     */       case 4:
/*     */       case 8:
/* 313 */         throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
/*     */       }
/*     */ 
/* 317 */       throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
/*     */     }
/*     */ 
/*     */     boolean isDone()
/*     */     {
/* 327 */       return (getState() & 0xE) != 0;
/*     */     }
/*     */ 
/*     */     boolean isCancelled()
/*     */     {
/* 334 */       return (getState() & 0xC) != 0;
/*     */     }
/*     */ 
/*     */     boolean set(V v)
/*     */     {
/* 348 */       return complete(v, null, 2);
/*     */     }
/*     */ 
/*     */     boolean setException(Throwable t)
/*     */     {
/* 355 */       return complete(null, t, 2);
/*     */     }
/*     */ 
/*     */     boolean cancel(boolean interrupt)
/*     */     {
/* 362 */       return complete(null, null, interrupt ? 8 : 4);
/*     */     }
/*     */ 
/*     */     private boolean complete(V v, Throwable t, int finalState)
/*     */     {
/* 379 */       boolean doCompletion = compareAndSetState(0, 1);
/* 380 */       if (doCompletion)
/*     */       {
/* 383 */         this.value = v;
/*     */ 
/* 385 */         this.exception = ((finalState & 0xC) != 0 ? new CancellationException("Future.cancel() was called.") : t);
/*     */ 
/* 387 */         releaseShared(finalState);
/* 388 */       } else if (getState() == 1)
/*     */       {
/* 391 */         acquireShared(-1);
/*     */       }
/* 393 */       return doCompletion;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.AbstractFuture
 * JD-Core Version:    0.6.0
 */