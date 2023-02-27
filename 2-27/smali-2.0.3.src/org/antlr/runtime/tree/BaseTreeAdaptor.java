/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public abstract class BaseTreeAdaptor
/*     */   implements TreeAdaptor
/*     */ {
/*  44 */   protected int uniqueNodeID = 1;
/*     */ 
/*     */   public Object nil()
/*     */   {
/*  48 */     return create(null);
/*     */   }
/*     */ 
/*     */   public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e)
/*     */   {
/*  66 */     CommonErrorNode t = new CommonErrorNode(input, start, stop, e);
/*     */ 
/*  68 */     return t;
/*     */   }
/*     */ 
/*     */   public boolean isNil(Object tree)
/*     */   {
/*  73 */     return ((Tree)tree).isNil();
/*     */   }
/*     */ 
/*     */   public Object dupTree(Object tree)
/*     */   {
/*  78 */     return dupTree(tree, null);
/*     */   }
/*     */ 
/*     */   public Object dupTree(Object t, Object parent)
/*     */   {
/*  86 */     if (t == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     Object newTree = dupNode(t);
/*     */ 
/*  91 */     setChildIndex(newTree, getChildIndex(t));
/*  92 */     setParent(newTree, parent);
/*  93 */     int n = getChildCount(t);
/*  94 */     for (int i = 0; i < n; i++) {
/*  95 */       Object child = getChild(t, i);
/*  96 */       Object newSubTree = dupTree(child, t);
/*  97 */       addChild(newTree, newSubTree);
/*     */     }
/*  99 */     return newTree;
/*     */   }
/*     */ 
/*     */   public void addChild(Object t, Object child)
/*     */   {
/* 111 */     if ((t != null) && (child != null))
/* 112 */       ((Tree)t).addChild((Tree)child);
/*     */   }
/*     */ 
/*     */   public Object becomeRoot(Object newRoot, Object oldRoot)
/*     */   {
/* 145 */     Tree newRootTree = (Tree)newRoot;
/* 146 */     Tree oldRootTree = (Tree)oldRoot;
/* 147 */     if (oldRoot == null) {
/* 148 */       return newRoot;
/*     */     }
/*     */ 
/* 151 */     if (newRootTree.isNil()) {
/* 152 */       int nc = newRootTree.getChildCount();
/* 153 */       if (nc == 1) newRootTree = newRootTree.getChild(0);
/* 154 */       else if (nc > 1)
/*     */       {
/* 156 */         throw new RuntimeException("more than one node as root (TODO: make exception hierarchy)");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 162 */     newRootTree.addChild(oldRootTree);
/* 163 */     return newRootTree;
/*     */   }
/*     */ 
/*     */   public Object rulePostProcessing(Object root)
/*     */   {
/* 170 */     Tree r = (Tree)root;
/* 171 */     if ((r != null) && (r.isNil())) {
/* 172 */       if (r.getChildCount() == 0) {
/* 173 */         r = null;
/*     */       }
/* 175 */       else if (r.getChildCount() == 1) {
/* 176 */         r = r.getChild(0);
/*     */ 
/* 178 */         r.setParent(null);
/* 179 */         r.setChildIndex(-1);
/*     */       }
/*     */     }
/* 182 */     return r;
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, Token fromToken)
/*     */   {
/* 192 */     fromToken = createToken(fromToken);
/*     */ 
/* 194 */     fromToken.setType(tokenType);
/* 195 */     Tree t = (Tree)create(fromToken);
/* 196 */     return t;
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, Token fromToken, String text)
/*     */   {
/* 201 */     if (fromToken == null) return create(tokenType, text);
/* 202 */     fromToken = createToken(fromToken);
/* 203 */     fromToken.setType(tokenType);
/* 204 */     fromToken.setText(text);
/* 205 */     Tree t = (Tree)create(fromToken);
/* 206 */     return t;
/*     */   }
/*     */ 
/*     */   public Object create(int tokenType, String text)
/*     */   {
/* 211 */     Token fromToken = createToken(tokenType, text);
/* 212 */     Tree t = (Tree)create(fromToken);
/* 213 */     return t;
/*     */   }
/*     */ 
/*     */   public int getType(Object t)
/*     */   {
/* 218 */     return ((Tree)t).getType();
/*     */   }
/*     */ 
/*     */   public String getText(Object t)
/*     */   {
/* 228 */     return ((Tree)t).getText();
/*     */   }
/*     */ 
/*     */   public Object getChild(Object t, int i)
/*     */   {
/* 238 */     return ((Tree)t).getChild(i);
/*     */   }
/*     */ 
/*     */   public int getChildCount(Object t)
/*     */   {
/* 253 */     return ((Tree)t).getChildCount();
/*     */   }
/*     */ 
/*     */   public abstract Token createToken(int paramInt, String paramString);
/*     */ 
/*     */   public abstract Token createToken(Token paramToken);
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.BaseTreeAdaptor
 * JD-Core Version:    0.6.0
 */