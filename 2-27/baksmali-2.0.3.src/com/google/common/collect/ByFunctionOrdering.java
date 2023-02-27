/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ final class ByFunctionOrdering<F, T> extends Ordering<F>
/*    */   implements Serializable
/*    */ {
/*    */   final Function<F, ? extends T> function;
/*    */   final Ordering<T> ordering;
/*    */ 
/*    */   ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering)
/*    */   {
/* 41 */     this.function = ((Function)Preconditions.checkNotNull(function));
/* 42 */     this.ordering = ((Ordering)Preconditions.checkNotNull(ordering));
/*    */   }
/*    */ 
/*    */   public int compare(F left, F right) {
/* 46 */     return this.ordering.compare(this.function.apply(left), this.function.apply(right));
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 50 */     if (object == this) {
/* 51 */       return true;
/*    */     }
/* 53 */     if ((object instanceof ByFunctionOrdering)) {
/* 54 */       ByFunctionOrdering that = (ByFunctionOrdering)object;
/* 55 */       return (this.function.equals(that.function)) && (this.ordering.equals(that.ordering));
/*    */     }
/*    */ 
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 62 */     return Objects.hashCode(new Object[] { this.function, this.ordering });
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 66 */     return this.ordering + ".onResultOf(" + this.function + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ByFunctionOrdering
 * JD-Core Version:    0.6.0
 */