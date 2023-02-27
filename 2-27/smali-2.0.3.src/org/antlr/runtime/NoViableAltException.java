/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class NoViableAltException extends RecognitionException
/*    */ {
/*    */   public String grammarDecisionDescription;
/*    */   public int decisionNumber;
/*    */   public int stateNumber;
/*    */ 
/*    */   public NoViableAltException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public NoViableAltException(String grammarDecisionDescription, int decisionNumber, int stateNumber, IntStream input)
/*    */   {
/* 43 */     super(input);
/* 44 */     this.grammarDecisionDescription = grammarDecisionDescription;
/* 45 */     this.decisionNumber = decisionNumber;
/* 46 */     this.stateNumber = stateNumber;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 51 */     if ((this.input instanceof CharStream)) {
/* 52 */       return "NoViableAltException('" + (char)getUnexpectedType() + "'@[" + this.grammarDecisionDescription + "])";
/*    */     }
/*    */ 
/* 55 */     return "NoViableAltException(" + getUnexpectedType() + "@[" + this.grammarDecisionDescription + "])";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.NoViableAltException
 * JD-Core Version:    0.6.0
 */