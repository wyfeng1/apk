/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.AbstractSet;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public abstract class VariableSizeSet<T> extends AbstractSet<T>
/*    */ {
/*    */   private final DexBackedDexFile dexFile;
/*    */   private final int offset;
/*    */   private final int size;
/*    */ 
/*    */   public VariableSizeSet(DexBackedDexFile dexFile, int offset, int size)
/*    */   {
/* 46 */     this.dexFile = dexFile;
/* 47 */     this.offset = offset;
/* 48 */     this.size = size;
/*    */   }
/*    */ 
/*    */   protected abstract T readNextItem(DexReader paramDexReader, int paramInt);
/*    */ 
/*    */   public VariableSizeIterator<T> iterator()
/*    */   {
/* 56 */     return new VariableSizeIterator(this.dexFile, this.offset, this.size)
/*    */     {
/*    */       protected T readNextItem(DexReader reader, int index) {
/* 59 */         return VariableSizeSet.this.readNextItem(reader, index);
/*    */       } } ;
/*    */   }
/*    */ 
/*    */   public int size() {
/* 64 */     return this.size;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.VariableSizeSet
 * JD-Core Version:    0.6.0
 */