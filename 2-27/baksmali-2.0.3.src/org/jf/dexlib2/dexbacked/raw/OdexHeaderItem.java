/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.BaseDexBuffer;
/*    */ 
/*    */ public class OdexHeaderItem
/*    */ {
/* 39 */   public static final byte[][] MAGIC_VALUES = { { 100, 101, 121, 10, 48, 51, 53, 0 }, { 100, 101, 121, 10, 48, 51, 54, 0 } };
/*    */ 
/*    */   public static int getVersion(byte[] magic)
/*    */   {
/* 55 */     if (magic.length < 8) {
/* 56 */       return 0;
/*    */     }
/*    */ 
/* 59 */     boolean matches = true;
/* 60 */     for (int i = 0; i < MAGIC_VALUES.length; i++) {
/* 61 */       byte[] expected = MAGIC_VALUES[i];
/* 62 */       matches = true;
/* 63 */       for (int j = 0; j < 8; j++) {
/* 64 */         if (magic[j] != expected[j]) {
/* 65 */           matches = false;
/* 66 */           break;
/*    */         }
/*    */       }
/* 69 */       if (matches) {
/* 70 */         return i == 0 ? 35 : 36;
/*    */       }
/*    */     }
/* 73 */     return 0;
/*    */   }
/*    */ 
/*    */   public static boolean verifyMagic(byte[] buf)
/*    */   {
/* 78 */     return getVersion(buf) != 0;
/*    */   }
/*    */ 
/*    */   public static int getDexOffset(byte[] buf) {
/* 82 */     BaseDexBuffer bdb = new BaseDexBuffer(buf);
/* 83 */     return bdb.readSmallUint(8);
/*    */   }
/*    */ 
/*    */   public static int getDependenciesOffset(byte[] buf) {
/* 87 */     BaseDexBuffer bdb = new BaseDexBuffer(buf);
/* 88 */     return bdb.readSmallUint(16);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.OdexHeaderItem
 * JD-Core Version:    0.6.0
 */