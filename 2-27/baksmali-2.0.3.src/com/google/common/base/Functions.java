/*    */ package com.google.common.base;
/*    */ 
/*    */ public final class Functions
/*    */ {
/*    */   public static Function<Object, String> toStringFunction()
/*    */   {
/* 56 */     return ToStringFunction.INSTANCE;
/*    */   }
/*    */ 
/*    */   private static enum ToStringFunction implements Function<Object, String>
/*    */   {
/* 61 */     INSTANCE;
/*    */ 
/*    */     public String apply(Object o)
/*    */     {
/* 65 */       Preconditions.checkNotNull(o);
/* 66 */       return o.toString();
/*    */     }
/*    */ 
/*    */     public String toString() {
/* 70 */       return "toString";
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Functions
 * JD-Core Version:    0.6.0
 */