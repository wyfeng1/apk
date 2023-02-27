/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public abstract class VariableSizeLookaheadIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private final DexReader reader;
/* 45 */   private T cachedItem = null;
/*    */ 
/*    */   protected VariableSizeLookaheadIterator(DexBackedDexFile dexFile, int offset) {
/* 48 */     this.reader = dexFile.readerAt(offset);
/* 49 */     this.cachedItem = readNextItem(this.reader);
/*    */   }
/*    */ 
/*    */   protected abstract T readNextItem(DexReader paramDexReader);
/*    */ 
/*    */   public boolean hasNext()
/*    */   {
/* 61 */     return this.cachedItem != null;
/*    */   }
/*    */ 
/*    */   public T next()
/*    */   {
/* 67 */     if (this.cachedItem == null) {
/* 68 */       throw new NoSuchElementException();
/*    */     }
/* 70 */     Object ret = this.cachedItem;
/* 71 */     this.cachedItem = readNextItem(this.reader);
/* 72 */     return ret;
/*    */   }
/*    */   public void remove() {
/* 75 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
 * JD-Core Version:    0.6.0
 */