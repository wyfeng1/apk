/*     */ package org.antlr.runtime.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class FastQueue<T>
/*     */ {
/*  46 */   protected List<T> data = new ArrayList();
/*     */ 
/*  48 */   protected int p = 0;
/*  49 */   protected int range = -1;
/*     */ 
/*     */   public void clear() {
/*  52 */     this.p = 0; this.data.clear();
/*     */   }
/*     */ 
/*     */   public T remove() {
/*  56 */     Object o = elementAt(0);
/*  57 */     this.p += 1;
/*     */ 
/*  59 */     if (this.p == this.data.size())
/*     */     {
/*  61 */       clear();
/*     */     }
/*  63 */     return o;
/*     */   }
/*     */   public void add(T o) {
/*  66 */     this.data.add(o);
/*     */   }
/*  68 */   public int size() { return this.data.size() - this.p;
/*     */   }
/*     */ 
/*     */   public T elementAt(int i)
/*     */   {
/*  80 */     int absIndex = this.p + i;
/*  81 */     if (absIndex >= this.data.size()) {
/*  82 */       throw new NoSuchElementException("queue index " + absIndex + " > last index " + (this.data.size() - 1));
/*     */     }
/*  84 */     if (absIndex < 0) {
/*  85 */       throw new NoSuchElementException("queue index " + absIndex + " < 0");
/*     */     }
/*  87 */     if (absIndex > this.range) this.range = absIndex;
/*  88 */     return this.data.get(absIndex);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     StringBuilder buf = new StringBuilder();
/*  95 */     int n = size();
/*  96 */     for (int i = 0; i < n; i++) {
/*  97 */       buf.append(elementAt(i));
/*  98 */       if (i + 1 >= n) continue; buf.append(" ");
/*     */     }
/* 100 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.misc.FastQueue
 * JD-Core Version:    0.6.0
 */