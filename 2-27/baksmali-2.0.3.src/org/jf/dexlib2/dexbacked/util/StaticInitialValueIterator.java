/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class StaticInitialValueIterator
/*    */ {
/* 43 */   public static final StaticInitialValueIterator EMPTY = new StaticInitialValueIterator() {
/* 44 */     public EncodedValue getNextOrNull() { return null;
/*    */     }
/* 43 */   };
/*    */ 
/*    */   public abstract EncodedValue getNextOrNull();
/*    */ 
/*    */   public static StaticInitialValueIterator newOrEmpty(DexBackedDexFile dexFile, int offset)
/*    */   {
/* 53 */     if (offset == 0) {
/* 54 */       return EMPTY;
/*    */     }
/* 56 */     return new StaticInitialValueIteratorImpl(dexFile, offset);
/*    */   }
/*    */ 
/*    */   private static class StaticInitialValueIteratorImpl extends StaticInitialValueIterator
/*    */   {
/*    */     private final DexReader reader;
/*    */     private final int size;
/* 62 */     private int index = 0;
/*    */ 
/*    */     public StaticInitialValueIteratorImpl(DexBackedDexFile dexFile, int offset) {
/* 65 */       this.reader = dexFile.readerAt(offset);
/* 66 */       this.size = this.reader.readSmallUleb128();
/*    */     }
/*    */ 
/*    */     public EncodedValue getNextOrNull()
/*    */     {
/* 71 */       if (this.index < this.size) {
/* 72 */         this.index += 1;
/* 73 */         return DexBackedEncodedValue.readFrom(this.reader);
/*    */       }
/* 75 */       return null;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.StaticInitialValueIterator
 * JD-Core Version:    0.6.0
 */