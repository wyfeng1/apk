/*    */ package org.jf.dexlib2.dexbacked;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.BaseTryBlock;
/*    */ import org.jf.dexlib2.dexbacked.util.VariableSizeList;
/*    */ 
/*    */ public class DexBackedTryBlock extends BaseTryBlock<DexBackedExceptionHandler>
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int tryItemOffset;
/*    */   private final int handlersStartOffset;
/*    */ 
/*    */   public DexBackedTryBlock(DexBackedDexFile dexFile, int tryItemOffset, int handlersStartOffset)
/*    */   {
/* 49 */     this.dexFile = dexFile;
/* 50 */     this.tryItemOffset = tryItemOffset;
/* 51 */     this.handlersStartOffset = handlersStartOffset;
/*    */   }
/*    */ 
/*    */   public int getStartCodeAddress() {
/* 55 */     return this.dexFile.readSmallUint(this.tryItemOffset + 0);
/*    */   }
/*    */ 
/*    */   public int getCodeUnitCount() {
/* 59 */     return this.dexFile.readUshort(this.tryItemOffset + 4);
/*    */   }
/*    */ 
/*    */   public List<? extends DexBackedExceptionHandler> getExceptionHandlers()
/*    */   {
/* 65 */     DexReader reader = this.dexFile.readerAt(this.handlersStartOffset + this.dexFile.readUshort(this.tryItemOffset + 6));
/*    */ 
/* 67 */     int encodedSize = reader.readSleb128();
/*    */ 
/* 69 */     if (encodedSize > 0)
/*    */     {
/* 71 */       return new VariableSizeList(this.dexFile, reader.getOffset(), encodedSize)
/*    */       {
/*    */         protected DexBackedTypedExceptionHandler readNextItem(DexReader reader, int index)
/*    */         {
/* 75 */           return new DexBackedTypedExceptionHandler(reader);
/*    */         }
/*    */       };
/*    */     }
/* 80 */     int sizeWithCatchAll = -1 * encodedSize + 1;
/* 81 */     return new VariableSizeList(this.dexFile, reader.getOffset(), sizeWithCatchAll, sizeWithCatchAll)
/*    */     {
/*    */       protected DexBackedExceptionHandler readNextItem(DexReader dexReader, int index)
/*    */       {
/* 85 */         if (index == this.val$sizeWithCatchAll - 1) {
/* 86 */           return new DexBackedCatchAllExceptionHandler(dexReader);
/*    */         }
/* 88 */         return new DexBackedTypedExceptionHandler(dexReader);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedTryBlock
 * JD-Core Version:    0.6.0
 */