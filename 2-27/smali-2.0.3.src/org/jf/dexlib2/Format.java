/*    */ package org.jf.dexlib2;
/*    */ 
/*    */ public enum Format
/*    */ {
/* 35 */   Format10t(2), 
/* 36 */   Format10x(2), 
/* 37 */   Format11n(2), 
/* 38 */   Format11x(2), 
/* 39 */   Format12x(2), 
/* 40 */   Format20bc(4), 
/* 41 */   Format20t(4), 
/* 42 */   Format21c(4), 
/* 43 */   Format21ih(4), 
/* 44 */   Format21lh(4), 
/* 45 */   Format21s(4), 
/* 46 */   Format21t(4), 
/* 47 */   Format22b(4), 
/* 48 */   Format22c(4), 
/* 49 */   Format22cs(4), 
/* 50 */   Format22s(4), 
/* 51 */   Format22t(4), 
/* 52 */   Format22x(4), 
/* 53 */   Format23x(4), 
/* 54 */   Format30t(6), 
/* 55 */   Format31c(6), 
/* 56 */   Format31i(6), 
/* 57 */   Format31t(6), 
/* 58 */   Format32x(6), 
/* 59 */   Format35c(6), 
/* 60 */   Format35mi(6), 
/* 61 */   Format35ms(6), 
/* 62 */   Format3rc(6), 
/* 63 */   Format3rmi(6), 
/* 64 */   Format3rms(6), 
/* 65 */   Format51l(10), 
/* 66 */   ArrayPayload(-1, true), 
/* 67 */   PackedSwitchPayload(-1, true), 
/* 68 */   SparseSwitchPayload(-1, true), 
/* 69 */   UnresolvedOdexInstruction(-1);
/*    */ 
/*    */   public final int size;
/*    */   public final boolean isPayloadFormat;
/*    */ 
/* 75 */   private Format(int size) { this(size, false); }
/*    */ 
/*    */   private Format(int size, boolean isPayloadFormat)
/*    */   {
/* 79 */     this.size = size;
/* 80 */     this.isPayloadFormat = isPayloadFormat;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.Format
 * JD-Core Version:    0.6.0
 */