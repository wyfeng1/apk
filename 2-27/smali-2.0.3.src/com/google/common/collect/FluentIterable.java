/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public abstract class FluentIterable<E>
/*     */   implements Iterable<E>
/*     */ {
/*     */   private final Iterable<E> iterable;
/*     */ 
/*     */   protected FluentIterable()
/*     */   {
/*  77 */     this.iterable = this;
/*     */   }
/*     */ 
/*     */   FluentIterable(Iterable<E> iterable) {
/*  81 */     this.iterable = ((Iterable)Preconditions.checkNotNull(iterable));
/*     */   }
/*     */ 
/*     */   public static <E> FluentIterable<E> from(Iterable<E> iterable)
/*     */   {
/*  89 */     return (iterable instanceof FluentIterable) ? (FluentIterable)iterable : new FluentIterable(iterable, iterable)
/*     */     {
/*     */       public Iterator<E> iterator()
/*     */       {
/*  93 */         return this.val$iterable.iterator();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 117 */     return Iterables.toString(this.iterable);
/*     */   }
/*     */ 
/*     */   public final <T> FluentIterable<T> transform(Function<? super E, T> function)
/*     */   {
/* 205 */     return from(Iterables.transform(this.iterable, function));
/*     */   }
/*     */ 
/*     */   public final FluentIterable<E> limit(int size)
/*     */   {
/* 311 */     return from(Iterables.limit(this.iterable, size));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.FluentIterable
 * JD-Core Version:    0.6.0
 */