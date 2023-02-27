/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.base.Preconditions;
/*     */ 
/*     */ public final class CacheStats
/*     */ {
/*     */   private final long hitCount;
/*     */   private final long missCount;
/*     */   private final long loadSuccessCount;
/*     */   private final long loadExceptionCount;
/*     */   private final long totalLoadTime;
/*     */   private final long evictionCount;
/*     */ 
/*     */   public CacheStats(long hitCount, long missCount, long loadSuccessCount, long loadExceptionCount, long totalLoadTime, long evictionCount)
/*     */   {
/*  79 */     Preconditions.checkArgument(hitCount >= 0L);
/*  80 */     Preconditions.checkArgument(missCount >= 0L);
/*  81 */     Preconditions.checkArgument(loadSuccessCount >= 0L);
/*  82 */     Preconditions.checkArgument(loadExceptionCount >= 0L);
/*  83 */     Preconditions.checkArgument(totalLoadTime >= 0L);
/*  84 */     Preconditions.checkArgument(evictionCount >= 0L);
/*     */ 
/*  86 */     this.hitCount = hitCount;
/*  87 */     this.missCount = missCount;
/*  88 */     this.loadSuccessCount = loadSuccessCount;
/*  89 */     this.loadExceptionCount = loadExceptionCount;
/*  90 */     this.totalLoadTime = totalLoadTime;
/*  91 */     this.evictionCount = evictionCount;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 247 */     return Objects.hashCode(new Object[] { Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount) });
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 253 */     if ((object instanceof CacheStats)) {
/* 254 */       CacheStats other = (CacheStats)object;
/* 255 */       return (this.hitCount == other.hitCount) && (this.missCount == other.missCount) && (this.loadSuccessCount == other.loadSuccessCount) && (this.loadExceptionCount == other.loadExceptionCount) && (this.totalLoadTime == other.totalLoadTime) && (this.evictionCount == other.evictionCount);
/*     */     }
/*     */ 
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 267 */     return Objects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.CacheStats
 * JD-Core Version:    0.6.0
 */