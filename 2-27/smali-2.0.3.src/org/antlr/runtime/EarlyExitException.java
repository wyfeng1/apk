/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class EarlyExitException extends RecognitionException
/*    */ {
/*    */   public int decisionNumber;
/*    */ 
/*    */   public EarlyExitException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EarlyExitException(int decisionNumber, IntStream input)
/*    */   {
/* 38 */     super(input);
/* 39 */     this.decisionNumber = decisionNumber;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.EarlyExitException
 * JD-Core Version:    0.6.0
 */