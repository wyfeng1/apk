/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ public class ExecutionError extends Error
/*    */ {
/*    */   protected ExecutionError()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ExecutionError(Error cause)
/*    */   {
/* 60 */     super(cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.ExecutionError
 * JD-Core Version:    0.6.0
 */