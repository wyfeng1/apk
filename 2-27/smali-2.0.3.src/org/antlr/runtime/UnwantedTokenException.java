/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class UnwantedTokenException extends MismatchedTokenException
/*    */ {
/*    */   public UnwantedTokenException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public UnwantedTokenException(int expecting, IntStream input)
/*    */   {
/* 36 */     super(expecting, input);
/*    */   }
/*    */ 
/*    */   public Token getUnexpectedToken() {
/* 40 */     return this.token;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 45 */     String exp = ", expected " + this.expecting;
/* 46 */     if (this.expecting == 0) {
/* 47 */       exp = "";
/*    */     }
/* 49 */     if (this.token == null) {
/* 50 */       return "UnwantedTokenException(found=" + null + exp + ")";
/*    */     }
/* 52 */     return "UnwantedTokenException(found=" + this.token.getText() + exp + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.UnwantedTokenException
 * JD-Core Version:    0.6.0
 */