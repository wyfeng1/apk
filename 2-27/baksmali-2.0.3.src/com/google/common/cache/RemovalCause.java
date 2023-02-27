/*    */ package com.google.common.cache;
/*    */ 
/*    */ public enum RemovalCause
/*    */ {
/* 40 */   EXPLICIT, 
/*    */ 
/* 53 */   REPLACED, 
/*    */ 
/* 65 */   COLLECTED, 
/*    */ 
/* 76 */   EXPIRED, 
/*    */ 
/* 87 */   SIZE;
/*    */ 
/*    */   abstract boolean wasEvicted();
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.RemovalCause
 * JD-Core Version:    0.6.0
 */