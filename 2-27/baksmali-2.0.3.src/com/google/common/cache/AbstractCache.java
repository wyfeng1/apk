/*     */ package com.google.common.cache;
/*     */ 
/*     */ public abstract class AbstractCache<K, V>
/*     */   implements Cache<K, V>
/*     */ {
/*     */   public static final class SimpleStatsCounter
/*     */     implements AbstractCache.StatsCounter
/*     */   {
/* 206 */     private final LongAddable hitCount = LongAddables.create();
/* 207 */     private final LongAddable missCount = LongAddables.create();
/* 208 */     private final LongAddable loadSuccessCount = LongAddables.create();
/* 209 */     private final LongAddable loadExceptionCount = LongAddables.create();
/* 210 */     private final LongAddable totalLoadTime = LongAddables.create();
/* 211 */     private final LongAddable evictionCount = LongAddables.create();
/*     */ 
/*     */     public void recordHits(int count)
/*     */     {
/* 223 */       this.hitCount.add(count);
/*     */     }
/*     */ 
/*     */     public void recordMisses(int count)
/*     */     {
/* 231 */       this.missCount.add(count);
/*     */     }
/*     */ 
/*     */     public void recordLoadSuccess(long loadTime)
/*     */     {
/* 236 */       this.loadSuccessCount.increment();
/* 237 */       this.totalLoadTime.add(loadTime);
/*     */     }
/*     */ 
/*     */     public void recordLoadException(long loadTime)
/*     */     {
/* 242 */       this.loadExceptionCount.increment();
/* 243 */       this.totalLoadTime.add(loadTime);
/*     */     }
/*     */ 
/*     */     public void recordEviction()
/*     */     {
/* 248 */       this.evictionCount.increment();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface StatsCounter
/*     */   {
/*     */     public abstract void recordHits(int paramInt);
/*     */ 
/*     */     public abstract void recordMisses(int paramInt);
/*     */ 
/*     */     public abstract void recordLoadSuccess(long paramLong);
/*     */ 
/*     */     public abstract void recordLoadException(long paramLong);
/*     */ 
/*     */     public abstract void recordEviction();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.AbstractCache
 * JD-Core Version:    0.6.0
 */