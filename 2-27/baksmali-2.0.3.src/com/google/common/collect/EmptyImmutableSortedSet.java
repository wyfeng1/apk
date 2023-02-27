/*     */ package com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ class EmptyImmutableSortedSet<E> extends ImmutableSortedSet<E>
/*     */ {
/*     */   EmptyImmutableSortedSet(Comparator<? super E> comparator)
/*     */   {
/*  38 */     super(comparator);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  43 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean contains(Object target) {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> targets) {
/*  55 */     return targets.isEmpty();
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> iterator() {
/*  59 */     return Iterators.emptyIterator();
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> descendingIterator()
/*     */   {
/*  64 */     return Iterators.emptyIterator();
/*     */   }
/*     */ 
/*     */   boolean isPartialView() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   public ImmutableList<E> asList() {
/*  72 */     return ImmutableList.of();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  76 */     return ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] a) {
/*  80 */     return asList().toArray(a);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object) {
/*  84 */     if ((object instanceof Set)) {
/*  85 */       Set that = (Set)object;
/*  86 */       return that.isEmpty();
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  92 */     return 0;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  96 */     return "[]";
/*     */   }
/*     */ 
/*     */   public E first()
/*     */   {
/* 101 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   public E last()
/*     */   {
/* 106 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive)
/*     */   {
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
/*     */   {
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive)
/*     */   {
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   int indexOf(Object target) {
/* 126 */     return -1;
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> createDescendingSet()
/*     */   {
/* 131 */     return new EmptyImmutableSortedSet(Ordering.from(this.comparator).reverse());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableSortedSet
 * JD-Core Version:    0.6.0
 */