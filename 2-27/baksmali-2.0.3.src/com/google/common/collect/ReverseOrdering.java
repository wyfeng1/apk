/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ final class ReverseOrdering<T> extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Ordering<? super T> forwardOrder;
/*    */ 
/*    */   ReverseOrdering(Ordering<? super T> forwardOrder)
/*    */   {
/* 34 */     this.forwardOrder = ((Ordering)Preconditions.checkNotNull(forwardOrder));
/*    */   }
/*    */ 
/*    */   public int compare(T a, T b) {
/* 38 */     return this.forwardOrder.compare(b, a);
/*    */   }
/*    */ 
/*    */   public <S extends T> Ordering<S> reverse()
/*    */   {
/* 43 */     return this.forwardOrder;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 81 */     return -this.forwardOrder.hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 85 */     if (object == this) {
/* 86 */       return true;
/*    */     }
/* 88 */     if ((object instanceof ReverseOrdering)) {
/* 89 */       ReverseOrdering that = (ReverseOrdering)object;
/* 90 */       return this.forwardOrder.equals(that.forwardOrder);
/*    */     }
/* 92 */     return false;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 96 */     return this.forwardOrder + ".reverse()";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ReverseOrdering
 * JD-Core Version:    0.6.0
 */