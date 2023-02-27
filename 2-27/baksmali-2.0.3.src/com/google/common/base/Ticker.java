/*    */ package com.google.common.base;
/*    */ 
/*    */ public abstract class Ticker
/*    */ {
/* 57 */   private static final Ticker SYSTEM_TICKER = new Ticker()
/*    */   {
/*    */     public long read() {
/* 60 */       return Platform.systemNanoTime();
/*    */     }
/* 57 */   };
/*    */ 
/*    */   public abstract long read();
/*    */ 
/*    */   public static Ticker systemTicker()
/*    */   {
/* 54 */     return SYSTEM_TICKER;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Ticker
 * JD-Core Version:    0.6.0
 */