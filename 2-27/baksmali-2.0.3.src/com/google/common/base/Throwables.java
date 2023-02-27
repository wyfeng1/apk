/*     */ package com.google.common.base;
/*     */ 
/*     */ public final class Throwables
/*     */ {
/*     */   public static <X extends Throwable> void propagateIfInstanceOf(Throwable throwable, Class<X> declaredType)
/*     */     throws Throwable
/*     */   {
/*  63 */     if ((throwable != null) && (declaredType.isInstance(throwable)))
/*  64 */       throw ((Throwable)declaredType.cast(throwable));
/*     */   }
/*     */ 
/*     */   public static void propagateIfPossible(Throwable throwable)
/*     */   {
/*  83 */     propagateIfInstanceOf(throwable, Error.class);
/*  84 */     propagateIfInstanceOf(throwable, RuntimeException.class);
/*     */   }
/*     */ 
/*     */   public static <X extends Throwable> void propagateIfPossible(Throwable throwable, Class<X> declaredType)
/*     */     throws Throwable
/*     */   {
/* 108 */     propagateIfInstanceOf(throwable, declaredType);
/* 109 */     propagateIfPossible(throwable);
/*     */   }
/*     */ 
/*     */   public static RuntimeException propagate(Throwable throwable)
/*     */   {
/* 159 */     propagateIfPossible((Throwable)Preconditions.checkNotNull(throwable));
/* 160 */     throw new RuntimeException(throwable);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Throwables
 * JD-Core Version:    0.6.0
 */