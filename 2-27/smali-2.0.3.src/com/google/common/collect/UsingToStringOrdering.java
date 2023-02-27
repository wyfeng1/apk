/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ final class UsingToStringOrdering extends Ordering<Object>
/*    */   implements Serializable
/*    */ {
/* 30 */   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
/*    */ 
/*    */   public int compare(Object left, Object right) {
/* 33 */     return left.toString().compareTo(right.toString());
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 42 */     return "Ordering.usingToString()";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.UsingToStringOrdering
 * JD-Core Version:    0.6.0
 */