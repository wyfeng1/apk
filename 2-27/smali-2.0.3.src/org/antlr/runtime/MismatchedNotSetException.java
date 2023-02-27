/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedNotSetException extends MismatchedSetException
/*    */ {
/*    */   public String toString()
/*    */   {
/* 40 */     return "MismatchedNotSetException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.MismatchedNotSetException
 * JD-Core Version:    0.6.0
 */