/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public abstract class Equivalence<T>
/*     */ {
/*     */   public final boolean equivalent(T a, T b)
/*     */   {
/*  65 */     if (a == b) {
/*  66 */       return true;
/*     */     }
/*  68 */     if ((a == null) || (b == null)) {
/*  69 */       return false;
/*     */     }
/*  71 */     return doEquivalent(a, b);
/*     */   }
/*     */ 
/*     */   protected abstract boolean doEquivalent(T paramT1, T paramT2);
/*     */ 
/*     */   public final int hash(T t)
/*     */   {
/* 101 */     if (t == null) {
/* 102 */       return 0;
/*     */     }
/* 104 */     return doHash(t);
/*     */   }
/*     */ 
/*     */   protected abstract int doHash(T paramT);
/*     */ 
/*     */   public static Equivalence<Object> equals()
/*     */   {
/* 307 */     return Equals.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static Equivalence<Object> identity()
/*     */   {
/* 319 */     return Identity.INSTANCE;
/*     */   }
/*     */ 
/*     */   static final class Identity extends Equivalence<Object>
/*     */     implements Serializable
/*     */   {
/* 343 */     static final Identity INSTANCE = new Identity();
/*     */ 
/*     */     protected boolean doEquivalent(Object a, Object b) {
/* 346 */       return false;
/*     */     }
/*     */ 
/*     */     protected int doHash(Object o) {
/* 350 */       return System.identityHashCode(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class Equals extends Equivalence<Object>
/*     */     implements Serializable
/*     */   {
/* 325 */     static final Equals INSTANCE = new Equals();
/*     */ 
/*     */     protected boolean doEquivalent(Object a, Object b) {
/* 328 */       return a.equals(b);
/*     */     }
/*     */     public int doHash(Object o) {
/* 331 */       return o.hashCode();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Equivalence
 * JD-Core Version:    0.6.0
 */