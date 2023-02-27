/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public abstract class VariableSizeIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private final DexReader reader;
/*    */   protected final int size;
/*    */   private int index;
/*    */ 
/*    */   protected VariableSizeIterator(DexBackedDexFile dexFile, int offset, int size)
/*    */   {
/* 48 */     this.reader = dexFile.readerAt(offset);
/* 49 */     this.size = size;
/*    */   }
/*    */ 
/*    */   protected VariableSizeIterator(DexReader reader, int size) {
/* 53 */     this.reader = reader;
/* 54 */     this.size = size;
/*    */   }
/*    */ 
/*    */   protected abstract T readNextItem(DexReader paramDexReader, int paramInt);
/*    */ 
/*    */   public boolean hasNext()
/*    */   {
/* 72 */     return this.index < this.size;
/*    */   }
/*    */ 
/*    */   public T next()
/*    */   {
/* 77 */     if (this.index >= this.size) {
/* 78 */       throw new NoSuchElementException();
/*    */     }
/* 80 */     return readNextItem(this.reader, this.index++);
/*    */   }
/*    */   public void remove() {
/* 83 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.VariableSizeIterator
 * JD-Core Version:    0.6.0
 */