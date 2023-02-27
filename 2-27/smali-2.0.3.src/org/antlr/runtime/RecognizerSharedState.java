/*     */ package org.antlr.runtime;
/*     */ 
/*     */ public class RecognizerSharedState
/*     */ {
/*  44 */   public BitSet[] following = new BitSet[100];
/*  45 */   public int _fsp = -1;
/*     */ 
/*  51 */   public boolean errorRecovery = false;
/*     */ 
/*  59 */   public int lastErrorIndex = -1;
/*     */ 
/*  64 */   public boolean failed = false;
/*     */ 
/*  67 */   public int syntaxErrors = 0;
/*     */ 
/*  72 */   public int backtracking = 0;
/*     */ 
/* 102 */   public int tokenStartCharIndex = -1;
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.RecognizerSharedState
 * JD-Core Version:    0.6.0
 */