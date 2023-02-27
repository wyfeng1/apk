/*    */ package org.jf.smali;
/*    */ 
/*    */ import org.antlr.runtime.CommonToken;
/*    */ import org.antlr.runtime.IntStream;
/*    */ import org.antlr.runtime.RecognitionException;
/*    */ import org.antlr.runtime.Token;
/*    */ import org.antlr.runtime.tree.CommonTree;
/*    */ 
/*    */ public class SemanticException extends RecognitionException
/*    */ {
/*    */   private String errorMessage;
/*    */ 
/*    */   SemanticException(IntStream input, String errorMessage, Object[] messageArguments)
/*    */   {
/* 42 */     super(input);
/* 43 */     this.errorMessage = String.format(errorMessage, messageArguments);
/*    */   }
/*    */ 
/*    */   SemanticException(IntStream input, Exception ex) {
/* 47 */     super(input);
/* 48 */     this.errorMessage = ex.getMessage();
/*    */   }
/*    */ 
/*    */   SemanticException(IntStream input, CommonTree tree, String errorMessage, Object[] messageArguments)
/*    */   {
/* 53 */     this.input = input;
/* 54 */     this.token = tree.getToken();
/* 55 */     this.index = tree.getTokenStartIndex();
/* 56 */     this.line = this.token.getLine();
/* 57 */     this.charPositionInLine = this.token.getCharPositionInLine();
/* 58 */     this.errorMessage = String.format(errorMessage, messageArguments);
/*    */   }
/*    */ 
/*    */   SemanticException(IntStream input, Token token, String errorMessage, Object[] messageArguments)
/*    */   {
/* 63 */     this.input = input;
/* 64 */     this.token = token;
/* 65 */     this.index = ((CommonToken)token).getStartIndex();
/* 66 */     this.line = token.getLine();
/* 67 */     this.charPositionInLine = token.getCharPositionInLine();
/* 68 */     this.errorMessage = String.format(errorMessage, messageArguments);
/*    */   }
/*    */ 
/*    */   public String getMessage() {
/* 72 */     return this.errorMessage;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.SemanticException
 * JD-Core Version:    0.6.0
 */