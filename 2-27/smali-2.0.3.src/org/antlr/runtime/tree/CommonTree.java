/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.Token;
/*     */ 
/*     */ public class CommonTree extends BaseTree
/*     */ {
/*     */   public Token token;
/*  45 */   protected int startIndex = -1; protected int stopIndex = -1;
/*     */   public CommonTree parent;
/*  51 */   public int childIndex = -1;
/*     */ 
/*     */   public CommonTree() {
/*     */   }
/*     */   public CommonTree(CommonTree node) {
/*  56 */     super(node);
/*  57 */     this.token = node.token;
/*  58 */     this.startIndex = node.startIndex;
/*  59 */     this.stopIndex = node.stopIndex;
/*     */   }
/*     */ 
/*     */   public CommonTree(Token t) {
/*  63 */     this.token = t;
/*     */   }
/*     */ 
/*     */   public Token getToken() {
/*  67 */     return this.token;
/*     */   }
/*     */ 
/*     */   public Tree dupNode()
/*     */   {
/*  72 */     return new CommonTree(this);
/*     */   }
/*     */ 
/*     */   public boolean isNil()
/*     */   {
/*  77 */     return this.token == null;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  82 */     if (this.token == null) {
/*  83 */       return 0;
/*     */     }
/*  85 */     return this.token.getType();
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*  90 */     if (this.token == null) {
/*  91 */       return null;
/*     */     }
/*  93 */     return this.token.getText();
/*     */   }
/*     */ 
/*     */   public int getLine()
/*     */   {
/*  98 */     if ((this.token == null) || (this.token.getLine() == 0)) {
/*  99 */       if (getChildCount() > 0) {
/* 100 */         return getChild(0).getLine();
/*     */       }
/* 102 */       return 0;
/*     */     }
/* 104 */     return this.token.getLine();
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine()
/*     */   {
/* 109 */     if ((this.token == null) || (this.token.getCharPositionInLine() == -1)) {
/* 110 */       if (getChildCount() > 0) {
/* 111 */         return getChild(0).getCharPositionInLine();
/*     */       }
/* 113 */       return 0;
/*     */     }
/* 115 */     return this.token.getCharPositionInLine();
/*     */   }
/*     */ 
/*     */   public int getTokenStartIndex()
/*     */   {
/* 120 */     if ((this.startIndex == -1) && (this.token != null)) {
/* 121 */       return this.token.getTokenIndex();
/*     */     }
/* 123 */     return this.startIndex;
/*     */   }
/*     */ 
/*     */   public void setTokenStartIndex(int index)
/*     */   {
/* 128 */     this.startIndex = index;
/*     */   }
/*     */ 
/*     */   public void setTokenStopIndex(int index)
/*     */   {
/* 141 */     this.stopIndex = index;
/*     */   }
/*     */ 
/*     */   public int getChildIndex()
/*     */   {
/* 169 */     return this.childIndex;
/*     */   }
/*     */ 
/*     */   public Tree getParent()
/*     */   {
/* 174 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(Tree t)
/*     */   {
/* 179 */     this.parent = ((CommonTree)t);
/*     */   }
/*     */ 
/*     */   public void setChildIndex(int index)
/*     */   {
/* 184 */     this.childIndex = index;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 189 */     if (isNil()) {
/* 190 */       return "nil";
/*     */     }
/* 192 */     if (getType() == 0) {
/* 193 */       return "<errornode>";
/*     */     }
/* 195 */     if (this.token == null) {
/* 196 */       return null;
/*     */     }
/* 198 */     return this.token.getText();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.CommonTree
 * JD-Core Version:    0.6.0
 */