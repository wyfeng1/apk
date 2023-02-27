/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ final class EmptyImmutableList extends ImmutableList<Object>
/*     */ {
/*  37 */   static final EmptyImmutableList INSTANCE = new EmptyImmutableList();
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
/*     */   boolean isPartialView() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(Object target) {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> targets) {
/*  59 */     return targets.isEmpty();
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<Object> iterator() {
/*  63 */     return listIterator();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  67 */     return ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] a) {
/*  71 */     if (a.length > 0) {
/*  72 */       a[0] = null;
/*     */     }
/*  74 */     return a;
/*     */   }
/*     */ 
/*     */   public Object get(int index)
/*     */   {
/*  80 */     Preconditions.checkElementIndex(index, 0);
/*  81 */     throw new AssertionError("unreachable");
/*     */   }
/*     */ 
/*     */   public int indexOf(Object target) {
/*  85 */     return -1;
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object target) {
/*  89 */     return -1;
/*     */   }
/*     */ 
/*     */   public ImmutableList<Object> subList(int fromIndex, int toIndex) {
/*  93 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, 0);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   public ImmutableList<Object> reverse() {
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public UnmodifiableListIterator<Object> listIterator() {
/* 102 */     return Iterators.EMPTY_LIST_ITERATOR;
/*     */   }
/*     */ 
/*     */   public UnmodifiableListIterator<Object> listIterator(int start) {
/* 106 */     Preconditions.checkPositionIndex(start, 0);
/* 107 */     return Iterators.EMPTY_LIST_ITERATOR;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object) {
/* 111 */     if ((object instanceof List)) {
/* 112 */       List that = (List)object;
/* 113 */       return that.isEmpty();
/*     */     }
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 119 */     return 1;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 123 */     return "[]";
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableList
 * JD-Core Version:    0.6.0
 */