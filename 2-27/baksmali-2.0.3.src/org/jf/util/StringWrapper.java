/*     */ package org.jf.util;
/*     */ 
/*     */ public class StringWrapper
/*     */ {
/*     */   public static String[] wrapString(String str, int maxWidth, String[] output)
/*     */   {
/*  55 */     if (output == null) {
/*  56 */       output = new String[(int)(str.length() / maxWidth * 1.5D + 1.0D)];
/*     */     }
/*     */ 
/*  59 */     int lineStart = 0;
/*  60 */     int arrayIndex = 0;
/*     */ 
/*  62 */     for (int i = 0; i < str.length(); i++) {
/*  63 */       char c = str.charAt(i);
/*     */ 
/*  65 */       if (c == '\n') {
/*  66 */         output = addString(output, str.substring(lineStart, i), arrayIndex++);
/*  67 */         lineStart = i + 1;
/*  68 */       } else if (i - lineStart == maxWidth) {
/*  69 */         output = addString(output, str.substring(lineStart, i), arrayIndex++);
/*  70 */         lineStart = i;
/*     */       }
/*     */     }
/*  73 */     if ((lineStart != i) || (i == 0)) {
/*  74 */       output = addString(output, str.substring(lineStart), arrayIndex++, output.length + 1);
/*     */     }
/*     */ 
/*  77 */     if (arrayIndex < output.length) {
/*  78 */       output[arrayIndex] = null;
/*     */     }
/*  80 */     return output;
/*     */   }
/*     */ 
/*     */   private static String[] addString(String[] arr, String str, int index) {
/*  84 */     if (index >= arr.length) {
/*  85 */       arr = enlargeArray(arr, (int)Math.ceil((arr.length + 1) * 1.5D));
/*     */     }
/*     */ 
/*  88 */     arr[index] = str;
/*  89 */     return arr;
/*     */   }
/*     */ 
/*     */   private static String[] addString(String[] arr, String str, int index, int newLength) {
/*  93 */     if (index >= arr.length) {
/*  94 */       arr = enlargeArray(arr, newLength);
/*     */     }
/*     */ 
/*  97 */     arr[index] = str;
/*  98 */     return arr;
/*     */   }
/*     */ 
/*     */   private static String[] enlargeArray(String[] arr, int newLength) {
/* 102 */     String[] newArr = new String[newLength];
/* 103 */     System.arraycopy(arr, 0, newArr, 0, arr.length);
/* 104 */     return newArr;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.StringWrapper
 * JD-Core Version:    0.6.0
 */