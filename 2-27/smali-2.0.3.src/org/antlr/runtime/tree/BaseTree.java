/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class BaseTree
/*     */   implements Tree
/*     */ {
/*     */   protected List<Object> children;
/*     */ 
/*     */   public BaseTree()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BaseTree(Tree node)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Tree getChild(int i)
/*     */   {
/*  54 */     if ((this.children == null) || (i >= this.children.size())) {
/*  55 */       return null;
/*     */     }
/*  57 */     return (Tree)this.children.get(i);
/*     */   }
/*     */ 
/*     */   public int getChildCount()
/*     */   {
/*  79 */     if (this.children == null) {
/*  80 */       return 0;
/*     */     }
/*  82 */     return this.children.size();
/*     */   }
/*     */ 
/*     */   public void addChild(Tree t)
/*     */   {
/*  95 */     if (t == null) {
/*  96 */       return;
/*     */     }
/*  98 */     BaseTree childTree = (BaseTree)t;
/*  99 */     if (childTree.isNil()) {
/* 100 */       if ((this.children != null) && (this.children == childTree.children)) {
/* 101 */         throw new RuntimeException("attempt to add child list to itself");
/*     */       }
/*     */ 
/* 104 */       if (childTree.children != null)
/* 105 */         if (this.children != null) {
/* 106 */           int n = childTree.children.size();
/* 107 */           for (int i = 0; i < n; i++) {
/* 108 */             Tree c = (Tree)childTree.children.get(i);
/* 109 */             this.children.add(c);
/*     */ 
/* 111 */             c.setParent(this);
/* 112 */             c.setChildIndex(this.children.size() - 1);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 118 */           this.children = childTree.children;
/* 119 */           freshenParentAndChildIndexes();
/*     */         }
/*     */     }
/*     */     else
/*     */     {
/* 124 */       if (this.children == null) {
/* 125 */         this.children = createChildrenList();
/*     */       }
/* 127 */       this.children.add(t);
/* 128 */       childTree.setParent(this);
/* 129 */       childTree.setChildIndex(this.children.size() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected List<Object> createChildrenList()
/*     */   {
/* 257 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public boolean isNil()
/*     */   {
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public void freshenParentAndChildIndexes()
/*     */   {
/* 268 */     freshenParentAndChildIndexes(0);
/*     */   }
/*     */ 
/*     */   public void freshenParentAndChildIndexes(int offset) {
/* 272 */     int n = getChildCount();
/* 273 */     for (int c = offset; c < n; c++) {
/* 274 */       Tree child = getChild(c);
/* 275 */       child.setChildIndex(c);
/* 276 */       child.setParent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getChildIndex()
/*     */   {
/* 315 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setChildIndex(int index)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Tree getParent()
/*     */   {
/* 324 */     return null;
/*     */   }
/*     */ 
/*     */   public void setParent(Tree t)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getLine()
/*     */   {
/* 390 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine()
/*     */   {
/* 395 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.BaseTree
 * JD-Core Version:    0.6.0
 */