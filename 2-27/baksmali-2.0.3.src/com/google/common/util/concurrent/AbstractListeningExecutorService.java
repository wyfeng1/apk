/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ public abstract class AbstractListeningExecutorService
/*     */   implements ListeningExecutorService
/*     */ {
/*     */   public ListenableFuture<?> submit(Runnable task)
/*     */   {
/*  48 */     ListenableFutureTask ftask = ListenableFutureTask.create(task, null);
/*  49 */     execute(ftask);
/*  50 */     return ftask;
/*     */   }
/*     */ 
/*     */   public <T> ListenableFuture<T> submit(Runnable task, T result) {
/*  54 */     ListenableFutureTask ftask = ListenableFutureTask.create(task, result);
/*  55 */     execute(ftask);
/*  56 */     return ftask;
/*     */   }
/*     */ 
/*     */   public <T> ListenableFuture<T> submit(Callable<T> task) {
/*  60 */     ListenableFutureTask ftask = ListenableFutureTask.create(task);
/*  61 */     execute(ftask);
/*  62 */     return ftask;
/*     */   }
/*     */ 
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
/*     */   {
/*     */     try {
/*  68 */       return MoreExecutors.invokeAnyImpl(this, tasks, false, 0L); } catch (TimeoutException cannotHappen) {
/*     */     }
/*  70 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/*  77 */     return MoreExecutors.invokeAnyImpl(this, tasks, true, unit.toNanos(timeout));
/*     */   }
/*     */ 
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
/*     */   {
/*  82 */     if (tasks == null) {
/*  83 */       throw new NullPointerException();
/*     */     }
/*  85 */     List futures = new ArrayList(tasks.size());
/*  86 */     boolean done = false;
/*     */     try {
/*  88 */       for (Callable t : tasks) {
/*  89 */         ListenableFutureTask f = ListenableFutureTask.create(t);
/*  90 */         futures.add(f);
/*  91 */         execute(f);
/*     */       }
/*  93 */       for (Future f : futures)
/*  94 */         if (!f.isDone())
/*     */           try {
/*  96 */             f.get();
/*     */           }
/*     */           catch (CancellationException localCancellationException) {
/*     */           }
/*     */           catch (ExecutionException localExecutionException) {
/*     */           }
/* 102 */       done = true;
/* 103 */       ??? = futures;
/*     */       Iterator i$;
/*     */       Future f;
/*     */       return ???;
/*     */     }
/*     */     finally
/*     */     {
/* 105 */       if (!done)
/* 106 */         for (Future f : futures)
/* 107 */           f.cancel(true); 
/*     */     }
/* 108 */     throw localObject;
/*     */   }
/*     */ 
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
/*     */     throws InterruptedException
/*     */   {
/* 116 */     if ((tasks == null) || (unit == null)) {
/* 117 */       throw new NullPointerException();
/*     */     }
/* 119 */     long nanos = unit.toNanos(timeout);
/* 120 */     List futures = new ArrayList(tasks.size());
/* 121 */     boolean done = false;
/*     */     try {
/* 123 */       for (Callable t : tasks) {
/* 124 */         futures.add(ListenableFutureTask.create(t));
/*     */       }
/*     */ 
/* 127 */       long lastTime = System.nanoTime();
/*     */ 
/* 131 */       Iterator it = futures.iterator();
/*     */       List localList1;
/* 132 */       while (it.hasNext()) {
/* 133 */         execute((Runnable)(Runnable)it.next());
/* 134 */         long now = System.nanoTime();
/* 135 */         nanos -= now - lastTime;
/* 136 */         lastTime = now;
/* 137 */         if (nanos <= 0L) {
/* 138 */           localList1 = futures;
/*     */           Iterator i$;
/*     */           Future f;
/*     */           return localList1;
/*     */         }
/*     */       }
/* 142 */       for (Future f : futures) {
/* 143 */         if (!f.isDone())
/*     */         {
/*     */           Iterator i$;
/* 144 */           if (nanos <= 0L) {
/* 145 */             localList1 = futures;
/*     */             Future f;
/*     */             return localList1;
/*     */           }
/*     */           try
/*     */           {
/* 148 */             f.get(nanos, TimeUnit.NANOSECONDS);
/*     */           } catch (CancellationException localCancellationException1) {
/*     */           } catch (ExecutionException localExecutionException1) {
/*     */           } catch (TimeoutException toe) {
/* 152 */             i$ = futures;
/*     */ 
/* 162 */             if (!done)
/* 163 */               for (Future f : futures)
/* 164 */                 f.cancel(true);
/* 165 */             return i$;
/*     */           }
/* 154 */           long now = System.nanoTime();
/* 155 */           nanos -= now - lastTime;
/* 156 */           lastTime = now;
/*     */         }
/*     */       }
/* 159 */       done = true;
/* 160 */       ??? = futures;
/*     */       Iterator i$;
/*     */       Future f;
/*     */       return ???;
/*     */     }
/*     */     finally
/*     */     {
/* 162 */       if (!done)
/* 163 */         for (Future f : futures)
/* 164 */           f.cancel(true); 
/*     */     }
/* 165 */     throw localObject;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.AbstractListeningExecutorService
 * JD-Core Version:    0.6.0
 */