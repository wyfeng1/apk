/*    */ package org.jf.util;
/*    */ 
/*    */ public abstract class NibbleUtils
/*    */ {
/*    */   public static int extractHighSignedNibble(int value)
/*    */   {
/* 42 */     return value << 24 >> 28;
/*    */   }
/*    */ 
/*    */   public static int extractHighUnsignedNibble(int value)
/*    */   {
/* 62 */     return (value & 0xF0) >>> 4;
/*    */   }
/*    */ 
/*    */   public static int extractLowUnsignedNibble(int value)
/*    */   {
/* 72 */     return value & 0xF;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.NibbleUtils
 * JD-Core Version:    0.6.0
 */