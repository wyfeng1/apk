/*     */ package com.google.common.primitives;
/*     */ 
/*     */ public final class Ints
/*     */ {
/*     */   public static int saturatedCast(long value)
/*     */   {
/* 101 */     if (value > 2147483647L) {
/* 102 */       return 2147483647;
/*     */     }
/* 104 */     if (value < -2147483648L) {
/* 105 */       return -2147483648;
/*     */     }
/* 107 */     return (int)value;
/*     */   }
/*     */ 
/*     */   public static int compare(int a, int b)
/*     */   {
/* 120 */     return a > b ? 1 : a < b ? -1 : 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.primitives.Ints
 * JD-Core Version:    0.6.0
 */