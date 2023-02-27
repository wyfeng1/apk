/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ final class ComparatorOrdering<T> extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Comparator<T> comparator;
/*    */ 
/*    */   ComparatorOrdering(Comparator<T> comparator)
/*    */   {
/* 37 */     this.comparator = ((Comparator)Preconditions.checkNotNull(comparator));
/*    */   }
/*    */ 
/*    */   public int compare(T a, T b) {
/* 41 */     return this.comparator.compare(a, b);
/*    */   }
/*    */ 
/*    */   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable)
/*    */   {
/* 59 */     Object[] elements = (Object[])Iterables.toArray(iterable);
/* 60 */     for (Object e : elements) {
/* 61 */       Preconditions.checkNotNull(e);
/*    */     }
/* 63 */     Arrays.sort(elements, this.comparator);
/* 64 */     return ImmutableList.asImmutableList(elements);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 68 */     if (object == this) {
/* 69 */       return true;
/*    */     }
/* 71 */     if ((object instanceof ComparatorOrdering)) {
/* 72 */       ComparatorOrdering that = (ComparatorOrdering)object;
/* 73 */       return this.comparator.equals(that.comparator);
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 79 */     return this.comparator.hashCode();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 83 */     return this.comparator.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ComparatorOrdering
 * JD-Core Version:    0.6.0
 */