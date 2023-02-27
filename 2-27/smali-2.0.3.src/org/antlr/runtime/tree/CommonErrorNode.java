/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.MismatchedTokenException;
/*     */ import org.antlr.runtime.MissingTokenException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.UnwantedTokenException;
/*     */ 
/*     */ public class CommonErrorNode extends CommonTree
/*     */ {
/*     */   public IntStream input;
/*     */   public Token start;
/*     */   public Token stop;
/*     */   public RecognitionException trappedException;
/*     */ 
/*     */   public CommonErrorNode(TokenStream input, Token start, Token stop, RecognitionException e)
/*     */   {
/*  43 */     if ((stop == null) || ((stop.getTokenIndex() < start.getTokenIndex()) && (stop.getType() != -1)))
/*     */     {
/*  51 */       stop = start;
/*     */     }
/*  53 */     this.input = input;
/*  54 */     this.start = start;
/*  55 */     this.stop = stop;
/*  56 */     this.trappedException = e;
/*     */   }
/*     */ 
/*     */   public boolean isNil()
/*     */   {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*     */     String badText;
/*     */     String badText;
/*  72 */     if ((this.start instanceof Token)) {
/*  73 */       int i = this.start.getTokenIndex();
/*  74 */       int j = this.stop.getTokenIndex();
/*  75 */       if (this.stop.getType() == -1) {
/*  76 */         j = ((TokenStream)this.input).size();
/*     */       }
/*  78 */       badText = ((TokenStream)this.input).toString(i, j);
/*     */     }
/*     */     else
/*     */     {
/*     */       String badText;
/*  80 */       if ((this.start instanceof Tree)) {
/*  81 */         badText = ((TreeNodeStream)this.input).toString(this.start, this.stop);
/*     */       }
/*     */       else
/*     */       {
/*  86 */         badText = "<unknown>";
/*     */       }
/*     */     }
/*  88 */     return badText;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     if ((this.trappedException instanceof MissingTokenException)) {
/*  94 */       return "<missing type: " + ((MissingTokenException)this.trappedException).getMissingType() + ">";
/*     */     }
/*     */ 
/*  98 */     if ((this.trappedException instanceof UnwantedTokenException)) {
/*  99 */       return "<extraneous: " + ((UnwantedTokenException)this.trappedException).getUnexpectedToken() + ", resync=" + getText() + ">";
/*     */     }
/*     */ 
/* 103 */     if ((this.trappedException instanceof MismatchedTokenException)) {
/* 104 */       return "<mismatched token: " + this.trappedException.token + ", resync=" + getText() + ">";
/*     */     }
/* 106 */     if ((this.trappedException instanceof NoViableAltException)) {
/* 107 */       return "<unexpected: " + this.trappedException.token + ", resync=" + getText() + ">";
/*     */     }
/*     */ 
/* 110 */     return "<error: " + getText() + ">";
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.CommonErrorNode
 * JD-Core Version:    0.6.0
 */