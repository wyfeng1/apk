/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class Parser extends BaseRecognizer
/*    */ {
/*    */   public TokenStream input;
/*    */ 
/*    */   public Parser(TokenStream input, RecognizerSharedState state)
/*    */   {
/* 42 */     super(state);
/* 43 */     this.input = input;
/*    */   }
/*    */ 
/*    */   protected Object getCurrentInputSymbol(IntStream input)
/*    */   {
/* 56 */     return ((TokenStream)input).LT(1);
/*    */   }
/*    */ 
/*    */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*    */   {
/* 66 */     String tokenText;
/*    */     String tokenText;
/* 66 */     if (expectedTokenType == -1) tokenText = "<missing EOF>"; else
/* 67 */       tokenText = "<missing " + getTokenNames()[expectedTokenType] + ">";
/* 68 */     CommonToken t = new CommonToken(expectedTokenType, tokenText);
/* 69 */     Token current = ((TokenStream)input).LT(1);
/* 70 */     if (current.getType() == -1) {
/* 71 */       current = ((TokenStream)input).LT(-1);
/*    */     }
/* 73 */     t.line = current.getLine();
/* 74 */     t.charPositionInLine = current.getCharPositionInLine();
/* 75 */     t.channel = 0;
/* 76 */     t.input = current.getInputStream();
/* 77 */     return t;
/*    */   }
/*    */ 
/*    */   public String getSourceName()
/*    */   {
/* 93 */     return this.input.getSourceName();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.Parser
 * JD-Core Version:    0.6.0
 */