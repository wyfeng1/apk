/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ final class NaturalOrdering extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 32 */   static final NaturalOrdering INSTANCE = new NaturalOrdering();
/*    */ 
/*    */   public int compare(Comparable left, Comparable right) {
/* 35 */     Preconditions.checkNotNull(left);
/* 36 */     Preconditions.checkNotNull(right);
/* 37 */     if (left == right) {
/* 38 */       return 0;
/*    */     }
/*    */ 
/* 41 */     return left.compareTo(right);
/*    */   }
/*    */ 
/*    */   public <S extends Comparable> Ordering<S> reverse() {
/* 45 */     return ReverseNaturalOrdering.INSTANCE;
/*    */   }
/*    */ 
/*    */   public <E extends Comparable> List<E> sortedCopy(Iterable<E> iterable)
/*    */   {
/* 57 */     List list = Lists.newArrayList(iterable);
/* 58 */     Collections.sort(list);
/* 59 */     return list;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 68 */     return "Ordering.natural()";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.NaturalOrdering
 * JD-Core Version:    0.6.0
 */