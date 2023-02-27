/*    */ package org.jf.smali;
/*    */ 
/*    */ import org.antlr.runtime.CommonToken;
/*    */ 
/*    */ public class InvalidToken extends CommonToken
/*    */ {
/*    */   private final String message;
/*    */ 
/*    */   public InvalidToken(String message, String text)
/*    */   {
/* 43 */     super(103, text);
/* 44 */     this.message = message;
/* 45 */     this.channel = 100;
/*    */   }
/*    */ 
/*    */   public String getMessage() {
/* 49 */     return this.message;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.InvalidToken
 * JD-Core Version:    0.6.0
 */