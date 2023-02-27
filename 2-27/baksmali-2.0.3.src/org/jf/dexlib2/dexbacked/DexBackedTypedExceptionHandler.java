/*    */ package org.jf.dexlib2.dexbacked;
/*    */ 
/*    */ public class DexBackedTypedExceptionHandler extends DexBackedExceptionHandler
/*    */ {
/*    */   private final DexBackedDexFile dexFile;
/*    */   private final int typeId;
/*    */   private final int handlerCodeAddress;
/*    */ 
/*    */   public DexBackedTypedExceptionHandler(DexReader reader)
/*    */   {
/* 42 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 43 */     this.typeId = reader.readSmallUleb128();
/* 44 */     this.handlerCodeAddress = reader.readSmallUleb128();
/*    */   }
/*    */   public String getExceptionType() {
/* 47 */     return this.dexFile.getType(this.typeId); } 
/* 48 */   public int getHandlerCodeAddress() { return this.handlerCodeAddress;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedTypedExceptionHandler
 * JD-Core Version:    0.6.0
 */