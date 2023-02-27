/*     */ package com.google.common.base;
/*     */ 
/*     */ public final class Strings
/*     */ {
/*     */   public static String repeat(String string, int count)
/*     */   {
/* 154 */     Preconditions.checkNotNull(string);
/*     */ 
/* 156 */     if (count <= 1) {
/* 157 */       Preconditions.checkArgument(count >= 0, "invalid count: %s", new Object[] { Integer.valueOf(count) });
/* 158 */       return count == 0 ? "" : string;
/*     */     }
/*     */ 
/* 162 */     int len = string.length();
/* 163 */     long longSize = len * count;
/* 164 */     int size = (int)longSize;
/* 165 */     if (size != longSize) {
/* 166 */       throw new ArrayIndexOutOfBoundsException("Required array size too large: " + String.valueOf(longSize));
/*     */     }
/*     */ 
/* 170 */     char[] array = new char[size];
/* 171 */     string.getChars(0, len, array, 0);
/*     */ 
/* 173 */     for (int n = len; n < size - n; n <<= 1) {
/* 174 */       System.arraycopy(array, 0, array, n, n);
/*     */     }
/* 176 */     System.arraycopy(array, 0, array, n, size - n);
/* 177 */     return new String(array);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Strings
 * JD-Core Version:    0.6.0
 */