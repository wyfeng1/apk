/*     */ package org.jf.util;
/*     */ 
/*     */ public final class Utf8Utils
/*     */ {
/*  69 */   private static final ThreadLocal<char[]> localBuffer = new ThreadLocal()
/*     */   {
/*     */     protected char[] initialValue()
/*     */     {
/*  73 */       return new char[256];
/*     */     }
/*  69 */   };
/*     */ 
/*     */   public static String utf8BytesWithUtf16LengthToString(byte[] bytes, int start, int utf16Length, int[] readLength)
/*     */   {
/* 193 */     char[] chars = (char[])localBuffer.get();
/* 194 */     if ((chars == null) || (chars.length < utf16Length)) {
/* 195 */       chars = new char[utf16Length];
/* 196 */       localBuffer.set(chars);
/*     */     }
/* 198 */     int outAt = 0;
/*     */ 
/* 200 */     int at = 0;
/* 201 */     for (at = start; utf16Length > 0; utf16Length--) {
/* 202 */       int v0 = bytes[at] & 0xFF;
/*     */       char out;
/* 204 */       switch (v0 >> 4) { case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/* 208 */         if (v0 == 0)
/*     */         {
/* 210 */           return throwBadUtf8(v0, at);
/*     */         }
/* 212 */         out = (char)v0;
/* 213 */         at++;
/* 214 */         break;
/*     */       case 12:
/*     */       case 13:
/* 218 */         int v1 = bytes[(at + 1)] & 0xFF;
/* 219 */         if ((v1 & 0xC0) != 128) {
/* 220 */           return throwBadUtf8(v1, at + 1);
/*     */         }
/* 222 */         int value = (v0 & 0x1F) << 6 | v1 & 0x3F;
/* 223 */         if ((value != 0) && (value < 128))
/*     */         {
/* 228 */           return throwBadUtf8(v1, at + 1);
/*     */         }
/* 230 */         out = (char)value;
/* 231 */         at += 2;
/* 232 */         break;
/*     */       case 14:
/* 236 */         int v1 = bytes[(at + 1)] & 0xFF;
/* 237 */         if ((v1 & 0xC0) != 128) {
/* 238 */           return throwBadUtf8(v1, at + 1);
/*     */         }
/* 240 */         int v2 = bytes[(at + 2)] & 0xFF;
/* 241 */         if ((v2 & 0xC0) != 128) {
/* 242 */           return throwBadUtf8(v2, at + 2);
/*     */         }
/* 244 */         int value = (v0 & 0xF) << 12 | (v1 & 0x3F) << 6 | v2 & 0x3F;
/*     */ 
/* 246 */         if (value < 2048)
/*     */         {
/* 251 */           return throwBadUtf8(v2, at + 2);
/*     */         }
/* 253 */         out = (char)value;
/* 254 */         at += 3;
/* 255 */         break;
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       default:
/* 259 */         return throwBadUtf8(v0, at);
/*     */       }
/*     */ 
/* 262 */       chars[outAt] = out;
/* 263 */       outAt++;
/*     */     }
/*     */ 
/* 266 */     if ((readLength != null) && (readLength.length > 0)) {
/* 267 */       readLength[0] = (at - start);
/* 268 */       readLength[0] = (at - start);
/*     */     }
/* 270 */     return new String(chars, 0, outAt);
/*     */   }
/*     */ 
/*     */   private static String throwBadUtf8(int value, int offset)
/*     */   {
/* 283 */     throw new IllegalArgumentException("bad utf-8 byte " + Hex.u1(value) + " at offset " + Hex.u4(offset));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.Utf8Utils
 * JD-Core Version:    0.6.0
 */