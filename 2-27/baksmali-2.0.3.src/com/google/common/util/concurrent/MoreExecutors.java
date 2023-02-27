/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public final class MoreExecutors
/*     */ {
/*     */   public static ListeningExecutorService sameThreadExecutor()
/*     */   {
/* 264 */     return new SameThreadExecutorService(null);
/*     */   }
/*     */ 
/*     */   static <T> T invokeAnyImpl(ListeningExecutorService executorService, Collection<? extends Callable<T>> tasks, boolean timed, long nanos)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 538 */     Preconditions.checkNotNull(executorService);
/* 539 */     int ntasks = tasks.size();
/* 540 */     Preconditions.checkArgument(ntasks > 0);
/* 541 */     List futures = Lists.newArrayListWithCapacity(ntasks);
/* 542 */     BlockingQueue futureQueue = Queues.newLinkedBlockingQueue();
/*     */     try
/*     */     {
/* 553 */       ExecutionException ee = null;
/* 554 */       long lastTime = timed ? System.nanoTime() : 0L;
/* 555 */       Iterator it = tasks.iterator();
/*     */ 
/* 557 */       futures.add(submitAndAddQueueListener(executorService, (Callable)it.next(), futureQueue));
/* 558 */       ntasks--;
/* 559 */       int active = 1;
/*     */       while (true)
/*     */       {
/* 562 */         Future f = (Future)futureQueue.poll();
/*     */         long now;
/* 563 */         if (f == null)
/* 564 */           if (ntasks > 0) {
/* 565 */             ntasks--;
/* 566 */             futures.add(submitAndAddQueueListener(executorService, (Callable)it.next(), futureQueue));
/* 567 */             active++; } else {
/* 568 */             if (active == 0)
/*     */               break;
/* 570 */             if (timed) {
/* 571 */               f = (Future)futureQueue.poll(nanos, TimeUnit.NANOSECONDS);
/* 572 */               if (f == null) {
/* 573 */                 throw new TimeoutException();
/*     */               }
/* 575 */               now = System.nanoTime();
/* 576 */               nanos -= now - lastTime;
/* 577 */               lastTime = now;
/*     */             } else {
/* 579 */               f = (Future)futureQueue.take();
/*     */             }
/*     */           }
/* 582 */         if (f != null) {
/* 583 */           active--;
/*     */           try {
/* 585 */             now = f.get();
/*     */ 
/* 599 */             for (Future f : futures)
/* 600 */               f.cancel(true);
/* 601 */             return now;
/*     */           }
/*     */           catch (ExecutionException eex)
/*     */           {
/* 587 */             ee = eex;
/*     */           } catch (RuntimeException rex) {
/* 589 */             ee = new ExecutionException(rex);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 594 */       if (ee == null) {
/* 595 */         ee = new ExecutionException(null);
/*     */       }
/* 597 */       throw ee;
/*     */     } finally {
/* 599 */       for (Future f : futures)
/* 600 */         f.cancel(true); 
/*     */     }
/* 601 */     throw localObject;
/*     */   }
/*     */ 
/*     */   private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService executorService, Callable<T> task, BlockingQueue<Future<T>> queue)
/*     */   {
/* 611 */     ListenableFuture future = executorService.submit(task);
/* 612 */     future.addListener(new Runnable(queue, future) {
/*     */       public void run() {
/* 614 */         this.val$queue.add(this.val$future);
/*     */       }
/*     */     }
/*     */     , sameThreadExecutor());
/*     */ 
/* 617 */     return future;
/*     */   }
/*     */ 
/*     */   private static class SameThreadExecutorService extends AbstractListeningExecutorService
/*     */   {
/* 274 */     private final Lock lock = new ReentrantLock();
/*     */ 
/* 277 */     private final Condition termination = this.lock.newCondition();
/*     */ 
/* 286 */     private int runningTasks = 0;
/* 287 */     private boolean shutdown = false;
/*     */ 
/*     */     public void execute(Runnable command)
/*     */     {
/* 291 */       startTask();
/*     */       try {
/* 293 */         command.run();
/*     */       } finally {
/* 295 */         endTask();
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isShutdown()
/*     */     {
/* 301 */       this.lock.lock();
/*     */       try {
/* 303 */         boolean bool = this.shutdown;
/*     */         return bool; } finally { this.lock.unlock(); } throw localObject;
/*     */     }
/*     */ 
/*     */     public void shutdown()
/*     */     {
/* 311 */       this.lock.lock();
/*     */       try {
/* 313 */         this.shutdown = true;
/*     */       } finally {
/* 315 */         this.lock.unlock();
/*     */       }
/*     */     }
/*     */ 
/*     */     public List<Runnable> shutdownNow()
/*     */     {
/* 322 */       shutdown();
/* 323 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     public boolean isTerminated()
/*     */     {
/* 328 */       this.lock.lock();
/*     */       try {
/* 330 */         int i = (this.shutdown) && (this.runningTasks == 0) ? 1 : 0;
/*     */         return i; } finally { this.lock.unlock(); } throw localObject;
/*     */     }
/*     */ 
/*     */     public boolean awaitTermination(long timeout, TimeUnit unit)
/*     */       throws InterruptedException
/*     */     {
/* 339 */       long nanos = unit.toNanos(timeout);
/* 340 */       this.lock.lock();
/*     */       try
/*     */       {
/*     */         while (true)
/*     */         {
/*     */           int i;
/* 343 */           if (isTerminated()) {
/* 344 */             i = 1;
/*     */             return i;
/*     */           }
/* 345 */           if (nanos <= 0L) {
/* 346 */             i = 0;
/*     */             return i;
/*     */           }
/* 348 */           nanos = this.termination.awaitNanos(nanos);
/*     */         }
/*     */       }
/*     */       finally {
/* 352 */         this.lock.unlock(); } throw localObject;
/*     */     }
/*     */ 
/*     */     private void startTask()
/*     */     {
/* 364 */       this.lock.lock();
/*     */       try {
/* 366 */         if (isShutdown()) {
/* 367 */           throw new RejectedExecutionException("Executor already shutdown");
/*     */         }
/* 369 */         this.runningTasks += 1;
/*     */       } finally {
/* 371 */         this.lock.unlock();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void endTask()
/*     */     {
/* 379 */       this.lock.lock();
/*     */       try {
/* 381 */         this.runningTasks -= 1;
/* 382 */         if (isTerminated())
/* 383 */           this.termination.signalAll();
/*     */       }
/*     */       finally {
/* 386 */         this.lock.unlock();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.MoreExecutors
 * JD-Core Version:    0.6.0
 */