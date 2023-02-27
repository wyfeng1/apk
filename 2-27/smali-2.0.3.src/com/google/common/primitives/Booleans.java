/*    */ package com.google.common.primitives;
/*    */ 
/*    */ public final class Booleans
/*    */ {
/*    */   public static int compare(boolean a, boolean b)
/*    */   {
/* 73 */     return a ? 1 : a == b ? 0 : -1;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.primitives.Booleans
 * JD-Core Version:    0.6.0
 */