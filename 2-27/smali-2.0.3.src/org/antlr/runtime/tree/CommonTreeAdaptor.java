/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.Token;
/*     */ 
/*     */ public class CommonTreeAdaptor extends BaseTreeAdaptor
/*     */ {
/*     */   public Object dupNode(Object t)
/*     */   {
/*  52 */     if (t == null) return null;
/*  53 */     return ((Tree)t).dupNode();
/*     */   }
/*     */ 
/*     */   public Object create(Token payload)
/*     */   {
/*  58 */     return new CommonTree(payload);
/*     */   }
/*     */ 
/*     */   public Token createToken(int tokenType, String text)
/*     */   {
/*  71 */     return new CommonToken(tokenType, text);
/*     */   }
/*     */ 
/*     */   public Token createToken(Token fromToken)
/*     */   {
/*  90 */     return new CommonToken(fromToken);
/*     */   }
/*     */ 
/*     */   public void setTokenBoundaries(Object t, Token startToken, Token stopToken)
/*     */   {
/* 100 */     if (t == null) return;
/* 101 */     int start = 0;
/* 102 */     int stop = 0;
/* 103 */     if (startToken != null) start = startToken.getTokenIndex();
/* 104 */     if (stopToken != null) stop = stopToken.getTokenIndex();
/* 105 */     ((Tree)t).setTokenStartIndex(start);
/* 106 */     ((Tree)t).setTokenStopIndex(stop);
/*     */   }
/*     */ 
/*     */   public String getText(Object t)
/*     */   {
/* 123 */     if (t == null) return null;
/* 124 */     return ((Tree)t).getText();
/*     */   }
/*     */ 
/*     */   public int getType(Object t)
/*     */   {
/* 129 */     if (t == null) return 0;
/* 130 */     return ((Tree)t).getType();
/*     */   }
/*     */ 
/*     */   public Token getToken(Object t)
/*     */   {
/* 139 */     if ((t instanceof CommonTree)) {
/* 140 */       return ((CommonTree)t).getToken();
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getChild(Object t, int i)
/*     */   {
/* 147 */     if (t == null) return null;
/* 148 */     return ((Tree)t).getChild(i);
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object t)
/*     */   {
/* 153 */     if (t == null) return 0;
/* 154 */     return ((Tree)t).getChildCount();
/*     */   }
/*     */ 
/*     */   public Object getParent(Object t)
/*     */   {
/* 159 */     if (t == null) return null;
/* 160 */     return ((Tree)t).getParent();
/*     */   }
/*     */ 
/*     */   public void setParent(Object t, Object parent)
/*     */   {
/* 165 */     if (t != null) ((Tree)t).setParent((Tree)parent);
/*     */   }
/*     */ 
/*     */   public int getChildIndex(Object t)
/*     */   {
/* 170 */     if (t == null) return 0;
/* 171 */     return ((Tree)t).getChildIndex();
/*     */   }
/*     */ 
/*     */   public void setChildIndex(Object t, int index)
/*     */   {
/* 176 */     if (t != null) ((Tree)t).setChildIndex(index);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.CommonTreeAdaptor
 * JD-Core Version:    0.6.0
 */