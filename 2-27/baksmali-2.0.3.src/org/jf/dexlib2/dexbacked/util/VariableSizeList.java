/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.AbstractSequentialList;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public abstract class VariableSizeList<T> extends AbstractSequentialList<T>
/*    */ {
/*    */   private final DexBackedDexFile dexFile;
/*    */   private final int offset;
/*    */   private final int size;
/*    */ 
/*    */   public VariableSizeList(DexBackedDexFile dexFile, int offset, int size)
/*    */   {
/* 46 */     this.dexFile = dexFile;
/* 47 */     this.offset = offset;
/* 48 */     this.size = size;
/*    */   }
/*    */ 
/*    */   protected abstract T readNextItem(DexReader paramDexReader, int paramInt);
/*    */ 
/*    */   public VariableSizeListIterator<T> listIterator()
/*    */   {
/* 56 */     return listIterator(0);
/*    */   }
/*    */   public int size() {
/* 59 */     return this.size;
/*    */   }
/*    */ 
/*    */   public VariableSizeListIterator<T> listIterator(int index)
/*    */   {
/* 64 */     VariableSizeListIterator iterator = new VariableSizeListIterator(this.dexFile, this.offset, this.size)
/*    */     {
/*    */       protected T readNextItem(DexReader reader, int index) {
/* 67 */         return VariableSizeList.this.readNextItem(reader, index);
/*    */       }
/*    */     };
/* 70 */     for (int i = 0; i < index; i++) {
/* 71 */       iterator.next();
/*    */     }
/* 73 */     return iterator;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.VariableSizeList
 * JD-Core Version:    0.6.0
 */