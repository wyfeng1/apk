/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ public final class SettableFuture<V> extends AbstractFuture<V>
/*    */ {
/*    */   public static <V> SettableFuture<V> create()
/*    */   {
/* 34 */     return new SettableFuture();
/*    */   }
/*    */ 
/*    */   public boolean set(V value)
/*    */   {
/* 53 */     return super.set(value);
/*    */   }
/*    */ 
/*    */   public boolean setException(Throwable throwable)
/*    */   {
/* 68 */     return super.setException(throwable);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.SettableFuture
 * JD-Core Version:    0.6.0
 */