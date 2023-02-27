/*    */ package com.google.common.base;
/*    */ 
/*    */ final class Platform
/*    */ {
/* 45 */   private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal()
/*    */   {
/*    */     protected char[] initialValue() {
/* 48 */       return new char[1024];
/*    */     }
/* 45 */   };
/*    */ 
/*    */   static long systemNanoTime()
/*    */   {
/* 37 */     return System.nanoTime();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Platform
 * JD-Core Version:    0.6.0
 */