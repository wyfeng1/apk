/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.antlr.runtime.misc.FastQueue;
/*     */ 
/*     */ public class TreeIterator
/*     */   implements Iterator<Object>
/*     */ {
/*     */   protected TreeAdaptor adaptor;
/*     */   protected Object root;
/*     */   protected Object tree;
/*  44 */   protected boolean firstTime = true;
/*     */   public Object up;
/*     */   public Object down;
/*     */   public Object eof;
/*     */   protected FastQueue<Object> nodes;
/*     */ 
/*     */   public TreeIterator(TreeAdaptor adaptor, Object tree)
/*     */   {
/*  61 */     this.adaptor = adaptor;
/*  62 */     this.tree = tree;
/*  63 */     this.root = tree;
/*  64 */     this.nodes = new FastQueue();
/*  65 */     this.down = adaptor.create(2, "DOWN");
/*  66 */     this.up = adaptor.create(3, "UP");
/*  67 */     this.eof = adaptor.create(-1, "EOF");
/*     */   }
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*  78 */     if (this.firstTime) return this.root != null;
/*  79 */     if ((this.nodes != null) && (this.nodes.size() > 0)) return true;
/*  80 */     if (this.tree == null) return false;
/*  81 */     if (this.adaptor.getChildCount(this.tree) > 0) return true;
/*  82 */     return this.adaptor.getParent(this.tree) != null;
/*     */   }
/*     */ 
/*     */   public Object next()
/*     */   {
/*  87 */     if (this.firstTime) {
/*  88 */       this.firstTime = false;
/*  89 */       if (this.adaptor.getChildCount(this.tree) == 0) {
/*  90 */         this.nodes.add(this.eof);
/*  91 */         return this.tree;
/*     */       }
/*  93 */       return this.tree;
/*     */     }
/*     */ 
/*  96 */     if ((this.nodes != null) && (this.nodes.size() > 0)) return this.nodes.remove();
/*     */ 
/*  99 */     if (this.tree == null) return this.eof;
/*     */ 
/* 102 */     if (this.adaptor.getChildCount(this.tree) > 0) {
/* 103 */       this.tree = this.adaptor.getChild(this.tree, 0);
/* 104 */       this.nodes.add(this.tree);
/* 105 */       return this.down;
/*     */     }
/*     */ 
/* 108 */     Object parent = this.adaptor.getParent(this.tree);
/*     */ 
/* 110 */     while ((parent != null) && (this.adaptor.getChildIndex(this.tree) + 1 >= this.adaptor.getChildCount(parent)))
/*     */     {
/* 113 */       this.nodes.add(this.up);
/* 114 */       this.tree = parent;
/* 115 */       parent = this.adaptor.getParent(this.tree);
/*     */     }
/*     */ 
/* 118 */     if (parent == null) {
/* 119 */       this.tree = null;
/* 120 */       this.nodes.add(this.eof);
/* 121 */       return this.nodes.remove();
/*     */     }
/*     */ 
/* 126 */     int nextSiblingIndex = this.adaptor.getChildIndex(this.tree) + 1;
/* 127 */     this.tree = this.adaptor.getChild(parent, nextSiblingIndex);
/* 128 */     this.nodes.add(this.tree);
/* 129 */     return this.nodes.remove();
/*     */   }
/*     */ 
/*     */   public void remove() {
/* 133 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.TreeIterator
 * JD-Core Version:    0.6.0
 */