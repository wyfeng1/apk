/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
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
/*     */   public final FluentIterable<E> filter(Predicate<? super E> predicate)
/*     */   {
/* 157 */     return from(Iterables.filter(this.iterable, predicate));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.FluentIterable
 * JD-Core Version:    0.6.0
 */