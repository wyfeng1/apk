/*     */ package com.google.common.base;
/*     */ 
/*     */ public final class Ascii
/*     */ {
/*     */   public static String toLowerCase(String string)
/*     */   {
/* 432 */     return toLowerCase(string);
/*     */   }
/*     */ 
/*     */   public static String toLowerCase(CharSequence chars)
/*     */   {
/* 443 */     int length = chars.length();
/* 444 */     StringBuilder builder = new StringBuilder(length);
/* 445 */     for (int i = 0; i < length; i++) {
/* 446 */       builder.append(toLowerCase(chars.charAt(i)));
/*     */     }
/* 448 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   public static char toLowerCase(char c)
/*     */   {
/* 456 */     return isUpperCase(c) ? (char)(c ^ 0x20) : c;
/*     */   }
/*     */ 
/*     */   public static boolean isUpperCase(char c)
/*     */   {
/* 507 */     return (c >= 'A') && (c <= 'Z');
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Ascii
 * JD-Core Version:    0.6.0
 */