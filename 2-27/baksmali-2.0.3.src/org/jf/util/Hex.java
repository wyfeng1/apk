/*     */ package org.jf.util;
/*     */ 
/*     */ public final class Hex
/*     */ {
/*     */   public static String u4(int v)
/*     */   {
/*  65 */     char[] result = new char[8];
/*  66 */     for (int i = 0; i < 8; i++) {
/*  67 */       result[(7 - i)] = Character.forDigit(v & 0xF, 16);
/*  68 */       v >>= 4;
/*     */     }
/*     */ 
/*  71 */     return new String(result);
/*     */   }
/*     */ 
/*     */   public static String u3(int v)
/*     */   {
/*  81 */     char[] result = new char[6];
/*  82 */     for (int i = 0; i < 6; i++) {
/*  83 */       result[(5 - i)] = Character.forDigit(v & 0xF, 16);
/*  84 */       v >>= 4;
/*     */     }
/*     */ 
/*  87 */     return new String(result);
/*     */   }
/*     */ 
/*     */   public static String u2(int v)
/*     */   {
/*  97 */     char[] result = new char[4];
/*  98 */     for (int i = 0; i < 4; i++) {
/*  99 */       result[(3 - i)] = Character.forDigit(v & 0xF, 16);
/* 100 */       v >>= 4;
/*     */     }
/*     */ 
/* 103 */     return new String(result);
/*     */   }
/*     */ 
/*     */   public static String u1(int v)
/*     */   {
/* 129 */     char[] result = new char[2];
/* 130 */     for (int i = 0; i < 2; i++) {
/* 131 */       result[(1 - i)] = Character.forDigit(v & 0xF, 16);
/* 132 */       v >>= 4;
/*     */     }
/*     */ 
/* 135 */     return new String(result);
/*     */   }
/*     */ 
/*     */   public static String dump(byte[] arr, int offset, int length, int outOffset, int bpl, int addressLength)
/*     */   {
/* 263 */     int end = offset + length;
/*     */ 
/* 266 */     if (((offset | length | end) < 0) || (end > arr.length)) {
/* 267 */       throw new IndexOutOfBoundsException("arr.length " + arr.length + "; " + offset + "..!" + end);
/*     */     }
/*     */ 
/* 272 */     if (outOffset < 0) {
/* 273 */       throw new IllegalArgumentException("outOffset < 0");
/*     */     }
/*     */ 
/* 276 */     if (length == 0) {
/* 277 */       return "";
/*     */     }
/*     */ 
/* 280 */     StringBuffer sb = new StringBuffer(length * 4 + 6);
/* 281 */     boolean bol = true;
/* 282 */     int col = 0;
/*     */ 
/* 284 */     while (length > 0) {
/* 285 */       if (col == 0)
/*     */       {
/*     */         String astr;
/* 287 */         switch (addressLength) { case 2:
/* 288 */           astr = u1(outOffset); break;
/*     */         case 4:
/* 289 */           astr = u2(outOffset); break;
/*     */         case 6:
/* 290 */           astr = u3(outOffset); break;
/*     */         case 3:
/*     */         case 5:
/*     */         default:
/* 291 */           astr = u4(outOffset);
/*     */         }
/* 293 */         sb.append(astr);
/* 294 */         sb.append(": ");
/* 295 */       } else if ((col & 0x1) == 0) {
/* 296 */         sb.append(' ');
/*     */       }
/* 298 */       sb.append(u1(arr[offset]));
/* 299 */       outOffset++;
/* 300 */       offset++;
/* 301 */       col++;
/* 302 */       if (col == bpl) {
/* 303 */         sb.append('\n');
/* 304 */         col = 0;
/*     */       }
/* 306 */       length--;
/*     */     }
/*     */ 
/* 309 */     if (col != 0) {
/* 310 */       sb.append('\n');
/*     */     }
/*     */ 
/* 313 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.Hex
 * JD-Core Version:    0.6.0
 */