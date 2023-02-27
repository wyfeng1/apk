/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class BaseDexBuffer
/*     */ {
/*     */   final byte[] buf;
/*     */ 
/*     */   public BaseDexBuffer(byte[] buf)
/*     */   {
/*  42 */     this.buf = buf;
/*     */   }
/*     */ 
/*     */   public int readSmallUint(int offset) {
/*  46 */     byte[] buf = this.buf;
/*  47 */     int result = buf[offset] & 0xFF | (buf[(offset + 1)] & 0xFF) << 8 | (buf[(offset + 2)] & 0xFF) << 16 | buf[(offset + 3)] << 24;
/*     */ 
/*  51 */     if (result < 0) {
/*  52 */       throw new ExceptionWithContext("Encountered small uint that is out of range at offset 0x%x", new Object[] { Integer.valueOf(offset) });
/*     */     }
/*  54 */     return result;
/*     */   }
/*     */ 
/*     */   public int readOptionalUint(int offset) {
/*  58 */     byte[] buf = this.buf;
/*  59 */     int result = buf[offset] & 0xFF | (buf[(offset + 1)] & 0xFF) << 8 | (buf[(offset + 2)] & 0xFF) << 16 | buf[(offset + 3)] << 24;
/*     */ 
/*  63 */     if (result < -1) {
/*  64 */       throw new ExceptionWithContext("Encountered optional uint that is out of range at offset 0x%x", new Object[] { Integer.valueOf(offset) });
/*     */     }
/*  66 */     return result;
/*     */   }
/*     */ 
/*     */   public int readUshort(int offset) {
/*  70 */     byte[] buf = this.buf;
/*  71 */     return buf[offset] & 0xFF | (buf[(offset + 1)] & 0xFF) << 8;
/*     */   }
/*     */ 
/*     */   public int readUbyte(int offset)
/*     */   {
/*  76 */     return this.buf[offset] & 0xFF;
/*     */   }
/*     */ 
/*     */   public long readLong(int offset) {
/*  80 */     byte[] buf = this.buf;
/*  81 */     return buf[offset] & 0xFF | (buf[(offset + 1)] & 0xFF) << 8 | (buf[(offset + 2)] & 0xFF) << 16 | (buf[(offset + 3)] & 0xFF) << 24 | (buf[(offset + 4)] & 0xFF) << 32 | (buf[(offset + 5)] & 0xFF) << 40 | (buf[(offset + 6)] & 0xFF) << 48 | buf[(offset + 7)] << 56;
/*     */   }
/*     */ 
/*     */   public int readInt(int offset)
/*     */   {
/*  92 */     byte[] buf = this.buf;
/*  93 */     return buf[offset] & 0xFF | (buf[(offset + 1)] & 0xFF) << 8 | (buf[(offset + 2)] & 0xFF) << 16 | buf[(offset + 3)] << 24;
/*     */   }
/*     */ 
/*     */   public int readShort(int offset)
/*     */   {
/* 100 */     byte[] buf = this.buf;
/* 101 */     return buf[offset] & 0xFF | buf[(offset + 1)] << 8;
/*     */   }
/*     */ 
/*     */   public int readByte(int offset)
/*     */   {
/* 106 */     return this.buf[offset];
/*     */   }
/*     */ 
/*     */   protected byte[] getBuf()
/*     */   {
/* 116 */     return this.buf;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.BaseDexBuffer
 * JD-Core Version:    0.6.0
 */