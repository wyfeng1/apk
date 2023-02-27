/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
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
/*    */   public String toString()
/*    */   {
/* 68 */     return "Ordering.natural()";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.NaturalOrdering
 * JD-Core Version:    0.6.0
 */