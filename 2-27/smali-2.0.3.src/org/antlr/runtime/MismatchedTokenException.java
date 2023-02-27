/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedTokenException extends RecognitionException
/*    */ {
/* 32 */   public int expecting = 0;
/*    */ 
/*    */   public MismatchedTokenException() {
/*    */   }
/*    */ 
/*    */   public MismatchedTokenException(int expecting, IntStream input) {
/* 38 */     super(input);
/* 39 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 44 */     return "MismatchedTokenException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.MismatchedTokenException
 * JD-Core Version:    0.6.0
 */