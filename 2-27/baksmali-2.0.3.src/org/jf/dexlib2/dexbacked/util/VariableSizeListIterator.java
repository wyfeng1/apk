/*     */ package org.jf.dexlib2.dexbacked.util;
/*     */ 
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ 
/*     */ public abstract class VariableSizeListIterator<T>
/*     */   implements ListIterator<T>
/*     */ {
/*     */   private DexReader reader;
/*     */   protected final int size;
/*     */   private final int startOffset;
/*     */   private int index;
/*     */ 
/*     */   protected VariableSizeListIterator(DexBackedDexFile dexFile, int offset, int size)
/*     */   {
/*  49 */     this.reader = dexFile.readerAt(offset);
/*  50 */     this.startOffset = offset;
/*  51 */     this.size = size;
/*     */   }
/*     */ 
/*     */   protected abstract T readNextItem(DexReader paramDexReader, int paramInt);
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*  69 */     return this.index < this.size;
/*     */   }
/*     */ 
/*     */   public T next()
/*     */   {
/*  74 */     if (this.index >= this.size) {
/*  75 */       throw new NoSuchElementException();
/*     */     }
/*  77 */     return readNextItem(this.reader, this.index++);
/*     */   }
/*     */ 
/*     */   public boolean hasPrevious()
/*     */   {
/*  82 */     return this.index > 0;
/*     */   }
/*     */ 
/*     */   public T previous()
/*     */   {
/*  87 */     int targetIndex = this.index - 1;
/*  88 */     this.reader.setOffset(this.startOffset);
/*  89 */     this.index = 0;
/*  90 */     while (this.index < targetIndex) {
/*  91 */       readNextItem(this.reader, this.index++);
/*     */     }
/*  93 */     return readNextItem(this.reader, this.index++);
/*     */   }
/*     */ 
/*     */   public int nextIndex()
/*     */   {
/*  98 */     return this.index;
/*     */   }
/*     */ 
/*     */   public int previousIndex()
/*     */   {
/* 103 */     return this.index - 1;
/*     */   }
/*     */   public void remove() {
/* 106 */     throw new UnsupportedOperationException(); } 
/* 107 */   public void set(T t) { throw new UnsupportedOperationException(); } 
/* 108 */   public void add(T t) { throw new UnsupportedOperationException();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
 * JD-Core Version:    0.6.0
 */