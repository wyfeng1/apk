/*     */ package org.antlr.runtime.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class LookaheadStream<T> extends FastQueue<T>
/*     */ {
/*  42 */   protected int currentElementIndex = 0;
/*     */   protected T prevElement;
/*  52 */   public T eof = null;
/*     */   protected int lastMarker;
/*  58 */   protected int markDepth = 0;
/*     */ 
/*     */   public abstract T nextElement();
/*     */ 
/*     */   public abstract boolean isEOF(T paramT);
/*     */ 
/*     */   public T remove()
/*     */   {
/*  83 */     Object o = elementAt(0);
/*  84 */     this.p += 1;
/*     */ 
/*  86 */     if ((this.p == this.data.size()) && (this.markDepth == 0)) {
/*  87 */       this.prevElement = o;
/*     */ 
/*  89 */       clear();
/*     */     }
/*  91 */     return o;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  96 */     syncAhead(1);
/*  97 */     remove();
/*  98 */     this.currentElementIndex += 1;
/*     */   }
/*     */ 
/*     */   protected void syncAhead(int need)
/*     */   {
/* 106 */     int n = this.p + need - 1 - this.data.size() + 1;
/* 107 */     if (n > 0) fill(n);
/*     */   }
/*     */ 
/*     */   public void fill(int n)
/*     */   {
/* 112 */     for (int i = 1; i <= n; i++) {
/* 113 */       Object o = nextElement();
/* 114 */       if (isEOF(o)) this.eof = o;
/* 115 */       this.data.add(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 121 */     throw new UnsupportedOperationException("streams are of unknown size");
/*     */   }
/*     */   public T LT(int k) {
/* 124 */     if (k == 0) {
/* 125 */       return null;
/*     */     }
/* 127 */     if (k < 0) return LB(-k);
/*     */ 
/* 129 */     syncAhead(k);
/* 130 */     if (this.p + k - 1 > this.data.size()) return this.eof;
/* 131 */     return elementAt(k - 1);
/*     */   }
/*     */   public int index() {
/* 134 */     return this.currentElementIndex;
/*     */   }
/*     */   public int mark() {
/* 137 */     this.markDepth += 1;
/* 138 */     this.lastMarker = this.p;
/* 139 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void rewind(int marker)
/*     */   {
/* 147 */     this.markDepth -= 1;
/* 148 */     int delta = this.p - marker;
/* 149 */     this.currentElementIndex -= delta;
/* 150 */     this.p = marker;
/*     */   }
/*     */ 
/*     */   public void rewind()
/*     */   {
/* 155 */     int delta = this.p - this.lastMarker;
/* 156 */     this.currentElementIndex -= delta;
/* 157 */     this.p = this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void seek(int index)
/*     */   {
/* 175 */     if (index < 0) {
/* 176 */       throw new IllegalArgumentException("can't seek before the beginning of the input");
/*     */     }
/*     */ 
/* 179 */     int delta = this.currentElementIndex - index;
/* 180 */     if (this.p - delta < 0) {
/* 181 */       throw new UnsupportedOperationException("can't seek before the beginning of this stream's buffer");
/*     */     }
/*     */ 
/* 184 */     this.p -= delta;
/* 185 */     this.currentElementIndex = index;
/*     */   }
/*     */ 
/*     */   protected T LB(int k) {
/* 189 */     assert (k > 0);
/*     */ 
/* 191 */     int index = this.p - k;
/* 192 */     if (index == -1) {
/* 193 */       return this.prevElement;
/*     */     }
/*     */ 
/* 198 */     if (index >= 0) {
/* 199 */       return this.data.get(index);
/*     */     }
/*     */ 
/* 202 */     if (index < -1) {
/* 203 */       throw new UnsupportedOperationException("can't look more than one token before the beginning of this stream's buffer");
/*     */     }
/*     */ 
/* 206 */     throw new UnsupportedOperationException("can't look past the end of this stream's buffer using LB(int)");
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.misc.LookaheadStream
 * JD-Core Version:    0.6.0
 */