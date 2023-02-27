/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ public final class Uninterruptibles
/*     */ {
/*     */   public static <V> V getUninterruptibly(Future<V> future)
/*     */     throws ExecutionException
/*     */   {
/* 131 */     boolean interrupted = false;
/*     */     try
/*     */     {
/* 135 */       Object localObject1 = future.get();
/*     */       return localObject1;
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/*     */       while (true)
/* 137 */         interrupted = true;
/*     */     }
/*     */     finally
/*     */     {
/* 141 */       if (interrupted)
/* 142 */         Thread.currentThread().interrupt(); 
/* 142 */     }throw localObject2;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.Uninterruptibles
 * JD-Core Version:    0.6.0
 */