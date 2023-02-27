/*     */ package com.google.common.cache;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ final class LongAdder extends Striped64
/*     */   implements LongAddable, Serializable
/*     */ {
/*     */   final long fn(long v, long x)
/*     */   {
/*  56 */     return v + x;
/*     */   }
/*     */ 
/*     */   public void add(long x)
/*     */   {
/*     */     Striped64.Cell[] as;
/*     */     long b;
/*  71 */     if (((as = this.cells) != null) || (!casBase(b = this.base, b + x))) {
/*  72 */       boolean uncontended = true;
/*     */       Striped64.HashCode hc;
/*  73 */       int h = (hc = (Striped64.HashCode)threadHashCode.get()).code;
/*     */       int n;
/*     */       Striped64.Cell a;
/*     */       long v;
/*  74 */       if ((as == null) || ((n = as.length) < 1) || ((a = as[(n - 1 & h)]) == null) || (!(uncontended = a.cas(v = a.value, v + x))))
/*     */       {
/*  77 */         retryUpdate(x, hc, uncontended);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void increment()
/*     */   {
/*  85 */     add(1L);
/*     */   }
/*     */ 
/*     */   public long sum()
/*     */   {
/* 105 */     long sum = this.base;
/* 106 */     Striped64.Cell[] as = this.cells;
/* 107 */     if (as != null) {
/* 108 */       int n = as.length;
/* 109 */       for (int i = 0; i < n; i++) {
/* 110 */         Striped64.Cell a = as[i];
/* 111 */         if (a != null)
/* 112 */           sum += a.value;
/*     */       }
/*     */     }
/* 115 */     return sum;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 161 */     return Long.toString(sum());
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 170 */     return sum();
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 178 */     return (int)sum();
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 186 */     return (float)sum();
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 194 */     return sum();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.LongAdder
 * JD-Core Version:    0.6.0
 */