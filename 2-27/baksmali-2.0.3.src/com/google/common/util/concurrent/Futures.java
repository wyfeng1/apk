/*      */ package com.google.common.util.concurrent;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.collect.Ordering;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ public final class Futures
/*      */ {
/*  945 */   private static final AsyncFunction<ListenableFuture<Object>, Object> DEREFERENCER = new AsyncFunction() { } ;
/*      */ 
/* 1408 */   private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function()
/*      */   {
/*      */     public Boolean apply(Constructor<?> input) {
/* 1411 */       return Boolean.valueOf(Arrays.asList(input.getParameterTypes()).contains(String.class));
/*      */     }
/*      */   }).reverse();
/*      */ 
/*      */   public static <V> ListenableFuture<V> immediateFuture(V value)
/*      */   {
/*  240 */     return new ImmediateSuccessfulFuture(value);
/*      */   }
/*      */ 
/*      */   private static class ImmediateSuccessfulFuture<V> extends Futures.ImmediateFuture<V>
/*      */   {
/*      */     private final V value;
/*      */ 
/*      */     ImmediateSuccessfulFuture(V value)
/*      */     {
/*  137 */       super();
/*  138 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public V get()
/*      */     {
/*  143 */       return this.value;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static abstract class ImmediateFuture<V>
/*      */     implements ListenableFuture<V>
/*      */   {
/*   91 */     private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());
/*      */ 
/*      */     public void addListener(Runnable listener, Executor executor)
/*      */     {
/*   96 */       Preconditions.checkNotNull(listener, "Runnable was null.");
/*   97 */       Preconditions.checkNotNull(executor, "Executor was null.");
/*      */       try {
/*   99 */         executor.execute(listener);
/*      */       }
/*      */       catch (RuntimeException e)
/*      */       {
/*  103 */         log.log(Level.SEVERE, "RuntimeException while executing runnable " + listener + " with executor " + executor, e);
/*      */       }
/*      */     }
/*      */ 
/*      */     public boolean cancel(boolean mayInterruptIfRunning)
/*      */     {
/*  110 */       return false;
/*      */     }
/*      */ 
/*      */     public abstract V get() throws ExecutionException;
/*      */ 
/*      */     public V get(long timeout, TimeUnit unit) throws ExecutionException
/*      */     {
/*  118 */       Preconditions.checkNotNull(unit);
/*  119 */       return get();
/*      */     }
/*      */ 
/*      */     public boolean isCancelled()
/*      */     {
/*  124 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isDone()
/*      */     {
/*  129 */       return true;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.Futures
 * JD-Core Version:    0.6.0
 */