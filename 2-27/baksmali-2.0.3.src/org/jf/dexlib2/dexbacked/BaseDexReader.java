/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.Utf8Utils;
/*     */ 
/*     */ public class BaseDexReader<T extends BaseDexBuffer>
/*     */ {
/*     */   public final T dexBuf;
/*     */   private int offset;
/*     */ 
/*     */   public BaseDexReader(T dexBuf, int offset)
/*     */   {
/*  44 */     this.dexBuf = dexBuf;
/*  45 */     this.offset = offset;
/*     */   }
/*     */   public int getOffset() {
/*  48 */     return this.offset; } 
/*  49 */   public void setOffset(int offset) { this.offset = offset; }
/*     */ 
/*     */   public int readSleb128()
/*     */   {
/*  53 */     int end = this.offset;
/*     */ 
/*  56 */     byte[] buf = this.dexBuf.buf;
/*     */ 
/*  58 */     int result = buf[(end++)] & 0xFF;
/*  59 */     if (result <= 127) {
/*  60 */       result = result << 25 >> 25;
/*     */     } else {
/*  62 */       int currentByteValue = buf[(end++)] & 0xFF;
/*  63 */       result = result & 0x7F | (currentByteValue & 0x7F) << 7;
/*  64 */       if (currentByteValue <= 127) {
/*  65 */         result = result << 18 >> 18;
/*     */       } else {
/*  67 */         currentByteValue = buf[(end++)] & 0xFF;
/*  68 */         result |= (currentByteValue & 0x7F) << 14;
/*  69 */         if (currentByteValue <= 127) {
/*  70 */           result = result << 11 >> 11;
/*     */         } else {
/*  72 */           currentByteValue = buf[(end++)] & 0xFF;
/*  73 */           result |= (currentByteValue & 0x7F) << 21;
/*  74 */           if (currentByteValue <= 127) {
/*  75 */             result = result << 4 >> 4;
/*     */           } else {
/*  77 */             currentByteValue = buf[(end++)] & 0xFF;
/*  78 */             if (currentByteValue > 127) {
/*  79 */               throw new ExceptionWithContext("Invalid sleb128 integer encountered at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */             }
/*     */ 
/*  82 */             result |= currentByteValue << 28;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  88 */     this.offset = end;
/*  89 */     return result;
/*     */   }
/*     */ 
/*     */   public int readSmallUleb128() {
/*  93 */     return readUleb128(false);
/*     */   }
/*     */ 
/*     */   private int readUleb128(boolean allowLarge) {
/*  97 */     int end = this.offset;
/*     */ 
/* 100 */     byte[] buf = this.dexBuf.buf;
/*     */ 
/* 102 */     int result = buf[(end++)] & 0xFF;
/* 103 */     if (result > 127) {
/* 104 */       int currentByteValue = buf[(end++)] & 0xFF;
/* 105 */       result = result & 0x7F | (currentByteValue & 0x7F) << 7;
/* 106 */       if (currentByteValue > 127) {
/* 107 */         currentByteValue = buf[(end++)] & 0xFF;
/* 108 */         result |= (currentByteValue & 0x7F) << 14;
/* 109 */         if (currentByteValue > 127) {
/* 110 */           currentByteValue = buf[(end++)] & 0xFF;
/* 111 */           result |= (currentByteValue & 0x7F) << 21;
/* 112 */           if (currentByteValue > 127) {
/* 113 */             currentByteValue = buf[(end++)];
/*     */ 
/* 116 */             if (currentByteValue < 0) {
/* 117 */               throw new ExceptionWithContext("Invalid uleb128 integer encountered at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */             }
/* 119 */             if (((currentByteValue & 0xF) > 7) && 
/* 120 */               (!allowLarge))
/*     */             {
/* 123 */               throw new ExceptionWithContext("Encountered valid uleb128 that is out of range at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */             }
/*     */ 
/* 127 */             result |= currentByteValue << 28;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 133 */     this.offset = end;
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */   public int readLargeUleb128()
/*     */   {
/* 144 */     return readUleb128(true);
/*     */   }
/*     */ 
/*     */   public int readBigUleb128()
/*     */   {
/* 154 */     int end = this.offset;
/*     */ 
/* 157 */     byte[] buf = this.dexBuf.buf;
/*     */ 
/* 159 */     int result = buf[(end++)] & 0xFF;
/* 160 */     if (result > 127) {
/* 161 */       int currentByteValue = buf[(end++)] & 0xFF;
/* 162 */       result = result & 0x7F | (currentByteValue & 0x7F) << 7;
/* 163 */       if (currentByteValue > 127) {
/* 164 */         currentByteValue = buf[(end++)] & 0xFF;
/* 165 */         result |= (currentByteValue & 0x7F) << 14;
/* 166 */         if (currentByteValue > 127) {
/* 167 */           currentByteValue = buf[(end++)] & 0xFF;
/* 168 */           result |= (currentByteValue & 0x7F) << 21;
/* 169 */           if (currentByteValue > 127) {
/* 170 */             currentByteValue = buf[(end++)];
/*     */ 
/* 173 */             if (currentByteValue < 0) {
/* 174 */               throw new ExceptionWithContext("Invalid uleb128 integer encountered at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */             }
/*     */ 
/* 177 */             result |= currentByteValue << 28;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 183 */     this.offset = end;
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   public void skipUleb128() {
/* 188 */     int end = this.offset;
/*     */ 
/* 190 */     byte[] buf = this.dexBuf.buf;
/*     */ 
/* 192 */     byte currentByteValue = buf[(end++)];
/* 193 */     if (currentByteValue < 0) {
/* 194 */       currentByteValue = buf[(end++)];
/* 195 */       if (currentByteValue < 0) {
/* 196 */         currentByteValue = buf[(end++)];
/* 197 */         if (currentByteValue < 0) {
/* 198 */           currentByteValue = buf[(end++)];
/* 199 */           if (currentByteValue < 0) {
/* 200 */             currentByteValue = buf[(end++)];
/* 201 */             if (currentByteValue < 0) {
/* 202 */               throw new ExceptionWithContext("Invalid uleb128 integer encountered at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 210 */     this.offset = end;
/*     */   }
/*     */ 
/*     */   public int readSmallUint() {
/* 214 */     int o = this.offset;
/* 215 */     int result = this.dexBuf.readSmallUint(o);
/* 216 */     this.offset = (o + 4);
/* 217 */     return result;
/*     */   }
/*     */ 
/*     */   public int peekUshort()
/*     */   {
/* 228 */     return this.dexBuf.readUshort(this.offset);
/*     */   }
/*     */ 
/*     */   public int readUshort() {
/* 232 */     int o = this.offset;
/* 233 */     int result = this.dexBuf.readUshort(this.offset);
/* 234 */     this.offset = (o + 2);
/* 235 */     return result;
/*     */   }
/*     */ 
/*     */   public int peekUbyte() {
/* 239 */     return this.dexBuf.readUbyte(this.offset);
/*     */   }
/*     */ 
/*     */   public int readUbyte() {
/* 243 */     int o = this.offset;
/* 244 */     int result = this.dexBuf.readUbyte(this.offset);
/* 245 */     this.offset = (o + 1);
/* 246 */     return result;
/*     */   }
/*     */ 
/*     */   public int readInt()
/*     */   {
/* 257 */     int o = this.offset;
/* 258 */     int result = this.dexBuf.readInt(this.offset);
/* 259 */     this.offset = (o + 4);
/* 260 */     return result;
/*     */   }
/*     */ 
/*     */   public int readByte()
/*     */   {
/* 271 */     int o = this.offset;
/* 272 */     int result = this.dexBuf.readByte(this.offset);
/* 273 */     this.offset = (o + 1);
/* 274 */     return result;
/*     */   }
/*     */   public void skipByte() {
/* 277 */     this.offset += 1; } 
/* 278 */   public void moveRelative(int i) { this.offset += i;
/*     */   }
/*     */ 
/*     */   public int readSizedInt(int bytes)
/*     */   {
/* 289 */     int o = this.offset;
/* 290 */     byte[] buf = this.dexBuf.buf;
/*     */     int result;
/* 293 */     switch (bytes) {
/*     */     case 4:
/* 295 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | buf[(o + 3)] << 24;
/*     */ 
/* 299 */       break;
/*     */     case 3:
/* 301 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | buf[(o + 2)] << 16;
/*     */ 
/* 304 */       break;
/*     */     case 2:
/* 306 */       result = buf[o] & 0xFF | buf[(o + 1)] << 8;
/*     */ 
/* 308 */       break;
/*     */     case 1:
/* 310 */       result = buf[o];
/* 311 */       break;
/*     */     default:
/* 313 */       throw new ExceptionWithContext("Invalid size %d for sized int at offset 0x%x", new Object[] { Integer.valueOf(bytes), Integer.valueOf(this.offset) });
/*     */     }
/* 315 */     this.offset = (o + bytes);
/* 316 */     return result;
/*     */   }
/*     */ 
/*     */   public int readSizedSmallUint(int bytes) {
/* 320 */     int o = this.offset;
/* 321 */     byte[] buf = this.dexBuf.buf;
/*     */ 
/* 323 */     int result = 0;
/* 324 */     switch (bytes) {
/*     */     case 4:
/* 326 */       int b = buf[(o + 3)];
/* 327 */       if (b < 0) {
/* 328 */         throw new ExceptionWithContext("Encountered valid sized uint that is out of range at offset 0x%x", new Object[] { Integer.valueOf(this.offset) });
/*     */       }
/*     */ 
/* 331 */       result = b << 24;
/*     */     case 3:
/* 334 */       result |= (buf[(o + 2)] & 0xFF) << 16;
/*     */     case 2:
/* 337 */       result |= (buf[(o + 1)] & 0xFF) << 8;
/*     */     case 1:
/* 340 */       result |= buf[o] & 0xFF;
/* 341 */       break;
/*     */     default:
/* 343 */       throw new ExceptionWithContext("Invalid size %d for sized uint at offset 0x%x", new Object[] { Integer.valueOf(bytes), Integer.valueOf(this.offset) });
/*     */     }
/* 345 */     this.offset = (o + bytes);
/* 346 */     return result;
/*     */   }
/*     */ 
/*     */   public int readSizedRightExtendedInt(int bytes) {
/* 350 */     int o = this.offset;
/* 351 */     byte[] buf = this.dexBuf.buf;
/*     */     int result;
/* 354 */     switch (bytes) {
/*     */     case 4:
/* 356 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | buf[(o + 3)] << 24;
/*     */ 
/* 360 */       break;
/*     */     case 3:
/* 362 */       result = (buf[o] & 0xFF) << 8 | (buf[(o + 1)] & 0xFF) << 16 | buf[(o + 2)] << 24;
/*     */ 
/* 365 */       break;
/*     */     case 2:
/* 367 */       result = (buf[o] & 0xFF) << 16 | buf[(o + 1)] << 24;
/*     */ 
/* 369 */       break;
/*     */     case 1:
/* 371 */       result = buf[o] << 24;
/* 372 */       break;
/*     */     default:
/* 374 */       throw new ExceptionWithContext("Invalid size %d for sized, right extended int at offset 0x%x", new Object[] { Integer.valueOf(bytes), Integer.valueOf(this.offset) });
/*     */     }
/*     */ 
/* 377 */     this.offset = (o + bytes);
/* 378 */     return result;
/*     */   }
/*     */ 
/*     */   public long readSizedRightExtendedLong(int bytes) {
/* 382 */     int o = this.offset;
/* 383 */     byte[] buf = this.dexBuf.buf;
/*     */     long result;
/* 386 */     switch (bytes) {
/*     */     case 8:
/* 388 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | (buf[(o + 3)] & 0xFF) << 24 | (buf[(o + 4)] & 0xFF) << 32 | (buf[(o + 5)] & 0xFF) << 40 | (buf[(o + 6)] & 0xFF) << 48 | buf[(o + 7)] << 56;
/*     */ 
/* 396 */       break;
/*     */     case 7:
/* 398 */       result = (buf[o] & 0xFF) << 8 | (buf[(o + 1)] & 0xFF) << 16 | (buf[(o + 2)] & 0xFF) << 24 | (buf[(o + 3)] & 0xFF) << 32 | (buf[(o + 4)] & 0xFF) << 40 | (buf[(o + 5)] & 0xFF) << 48 | buf[(o + 6)] << 56;
/*     */ 
/* 405 */       break;
/*     */     case 6:
/* 407 */       result = (buf[o] & 0xFF) << 16 | (buf[(o + 1)] & 0xFF) << 24 | (buf[(o + 2)] & 0xFF) << 32 | (buf[(o + 3)] & 0xFF) << 40 | (buf[(o + 4)] & 0xFF) << 48 | buf[(o + 5)] << 56;
/*     */ 
/* 413 */       break;
/*     */     case 5:
/* 415 */       result = (buf[o] & 0xFF) << 24 | (buf[(o + 1)] & 0xFF) << 32 | (buf[(o + 2)] & 0xFF) << 40 | (buf[(o + 3)] & 0xFF) << 48 | buf[(o + 4)] << 56;
/*     */ 
/* 420 */       break;
/*     */     case 4:
/* 422 */       result = (buf[o] & 0xFF) << 32 | (buf[(o + 1)] & 0xFF) << 40 | (buf[(o + 2)] & 0xFF) << 48 | buf[(o + 3)] << 56;
/*     */ 
/* 426 */       break;
/*     */     case 3:
/* 428 */       result = (buf[o] & 0xFF) << 40 | (buf[(o + 1)] & 0xFF) << 48 | buf[(o + 2)] << 56;
/*     */ 
/* 431 */       break;
/*     */     case 2:
/* 433 */       result = (buf[o] & 0xFF) << 48 | buf[(o + 1)] << 56;
/*     */ 
/* 435 */       break;
/*     */     case 1:
/* 437 */       result = buf[o] << 56;
/* 438 */       break;
/*     */     default:
/* 440 */       throw new ExceptionWithContext("Invalid size %d for sized, right extended long at offset 0x%x", new Object[] { Integer.valueOf(bytes), Integer.valueOf(this.offset) });
/*     */     }
/*     */ 
/* 443 */     this.offset = (o + bytes);
/* 444 */     return result;
/*     */   }
/*     */ 
/*     */   public long readSizedLong(int bytes) {
/* 448 */     int o = this.offset;
/* 449 */     byte[] buf = this.dexBuf.buf;
/*     */     long result;
/* 452 */     switch (bytes) {
/*     */     case 8:
/* 454 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | (buf[(o + 3)] & 0xFF) << 24 | (buf[(o + 4)] & 0xFF) << 32 | (buf[(o + 5)] & 0xFF) << 40 | (buf[(o + 6)] & 0xFF) << 48 | buf[(o + 7)] << 56;
/*     */ 
/* 462 */       break;
/*     */     case 7:
/* 464 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | (buf[(o + 3)] & 0xFF) << 24 | (buf[(o + 4)] & 0xFF) << 32 | (buf[(o + 5)] & 0xFF) << 40 | buf[(o + 6)] << 48;
/*     */ 
/* 471 */       break;
/*     */     case 6:
/* 473 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | (buf[(o + 3)] & 0xFF) << 24 | (buf[(o + 4)] & 0xFF) << 32 | buf[(o + 5)] << 40;
/*     */ 
/* 479 */       break;
/*     */     case 5:
/* 481 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | (buf[(o + 3)] & 0xFF) << 24 | buf[(o + 4)] << 32;
/*     */ 
/* 486 */       break;
/*     */     case 4:
/* 488 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | (buf[(o + 2)] & 0xFF) << 16 | buf[(o + 3)] << 24;
/*     */ 
/* 492 */       break;
/*     */     case 3:
/* 494 */       result = buf[o] & 0xFF | (buf[(o + 1)] & 0xFF) << 8 | buf[(o + 2)] << 16;
/*     */ 
/* 497 */       break;
/*     */     case 2:
/* 499 */       result = buf[o] & 0xFF | buf[(o + 1)] << 8;
/*     */ 
/* 501 */       break;
/*     */     case 1:
/* 503 */       result = buf[o];
/* 504 */       break;
/*     */     default:
/* 506 */       throw new ExceptionWithContext("Invalid size %d for sized long at offset 0x%x", new Object[] { Integer.valueOf(bytes), Integer.valueOf(this.offset) });
/*     */     }
/*     */ 
/* 509 */     this.offset = (o + bytes);
/* 510 */     return result;
/*     */   }
/*     */ 
/*     */   public String readString(int utf16Length) {
/* 514 */     int[] ret = new int[1];
/* 515 */     String value = Utf8Utils.utf8BytesWithUtf16LengthToString(this.dexBuf.buf, this.offset, utf16Length, ret);
/* 516 */     this.offset += ret[0];
/* 517 */     return value;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.BaseDexReader
 * JD-Core Version:    0.6.0
 */