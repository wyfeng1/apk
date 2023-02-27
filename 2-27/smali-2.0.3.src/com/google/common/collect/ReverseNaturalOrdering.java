/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ final class ReverseNaturalOrdering extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 31 */   static final ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
/*    */ 
/*    */   public int compare(Comparable left, Comparable right) {
/* 34 */     Preconditions.checkNotNull(left);
/* 35 */     if (left == right) {
/* 36 */       return 0;
/*    */     }
/*    */ 
/* 39 */     return right.compareTo(left);
/*    */   }
/*    */ 
/*    */   public <S extends Comparable> Ordering<S> reverse() {
/* 43 */     return Ordering.natural();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 86 */     return "Ordering.natural().reverse()";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ReverseNaturalOrdering
 * JD-Core Version:    0.6.0
 */