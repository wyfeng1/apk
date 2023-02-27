/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ 
/*     */ public abstract class CacheLoader<K, V>
/*     */ {
/*     */   public abstract V load(K paramK)
/*     */     throws Exception;
/*     */ 
/*     */   public ListenableFuture<V> reload(K key, V oldValue)
/*     */     throws Exception
/*     */   {
/*  92 */     Preconditions.checkNotNull(key);
/*  93 */     Preconditions.checkNotNull(oldValue);
/*  94 */     return Futures.immediateFuture(load(key));
/*     */   }
/*     */ 
/*     */   public static final class InvalidCacheLoadException extends RuntimeException
/*     */   {
/*     */     public InvalidCacheLoadException(String message)
/*     */     {
/* 194 */       super();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.CacheLoader
 * JD-Core Version:    0.6.0
 */