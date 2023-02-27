/*    */ package org.jf.dexlib2.dexbacked;
/*    */ 
/*    */ public class DexBackedCatchAllExceptionHandler extends DexBackedExceptionHandler
/*    */ {
/*    */   private final int handlerCodeAddress;
/*    */ 
/*    */   public DexBackedCatchAllExceptionHandler(DexReader reader)
/*    */   {
/* 44 */     this.handlerCodeAddress = reader.readSmallUleb128();
/*    */   }
/*    */   public String getExceptionType() {
/* 47 */     return null; } 
/* 48 */   public int getHandlerCodeAddress() { return this.handlerCodeAddress;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedCatchAllExceptionHandler
 * JD-Core Version:    0.6.0
 */