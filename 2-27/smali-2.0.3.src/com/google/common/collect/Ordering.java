/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public abstract class Ordering<T>
/*     */   implements Comparator<T>
/*     */ {
/*     */   public static <C extends Comparable> Ordering<C> natural()
/*     */   {
/*  89 */     return NaturalOrdering.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static <T> Ordering<T> from(Comparator<T> comparator)
/*     */   {
/* 106 */     return (comparator instanceof Ordering) ? (Ordering)comparator : new ComparatorOrdering(comparator);
/*     */   }
/*     */ 
/*     */   public static Ordering<Object> usingToString()
/*     */   {
/* 217 */     return UsingToStringOrdering.INSTANCE;
/*     */   }
/*     */ 
/*     */   public <S extends T> Ordering<S> reverse()
/*     */   {
/* 313 */     return new ReverseOrdering(this);
/*     */   }
/*     */ 
/*     */   public abstract int compare(T paramT1, T paramT2);
/*     */ 
/*     */   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable)
/*     */   {
/* 810 */     Object[] elements = (Object[])Iterables.toArray(iterable);
/* 811 */     for (Object e : elements) {
/* 812 */       Preconditions.checkNotNull(e);
/*     */     }
/* 814 */     Arrays.sort(elements, this);
/* 815 */     return ImmutableList.asImmutableList(elements);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Ordering
 * JD-Core Version:    0.6.0
 */