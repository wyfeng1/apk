/*     */ package com.google.common.base;
/*     */ 
/*     */ public final class Predicates
/*     */ {
/* 330 */   private static final Joiner COMMA_JOINER = Joiner.on(",");
/*     */ 
/*     */   public static <T> Predicate<T> notNull()
/*     */   {
/*  85 */     return ObjectPredicate.NOT_NULL.withNarrowedType();
/*     */   }
/*     */ 
/*     */   static abstract enum ObjectPredicate
/*     */     implements Predicate<Object>
/*     */   {
/* 276 */     ALWAYS_TRUE, 
/*     */ 
/* 281 */     ALWAYS_FALSE, 
/*     */ 
/* 286 */     IS_NULL, 
/*     */ 
/* 291 */     NOT_NULL;
/*     */ 
/*     */     <T> Predicate<T> withNarrowedType()
/*     */     {
/* 299 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Predicates
 * JD-Core Version:    0.6.0
 */