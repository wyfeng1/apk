/*     */ package org.jf.dexlib2.writer;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class DexDataWriter extends BufferedOutputStream
/*     */ {
/*     */   private int filePosition;
/*  52 */   private byte[] tempBuf = new byte[8];
/*     */ 
/*  55 */   private byte[] zeroBuf = new byte[3];
/*     */ 
/*     */   public DexDataWriter(OutputStream output, int filePosition)
/*     */   {
/*  64 */     this(output, filePosition, 262144);
/*     */   }
/*     */ 
/*     */   public DexDataWriter(OutputStream output, int filePosition, int bufferSize) {
/*  68 */     super(output, bufferSize);
/*     */ 
/*  70 */     this.filePosition = filePosition;
/*     */   }
/*     */ 
/*     */   public void write(int b) throws IOException
/*     */   {
/*  75 */     this.filePosition += 1;
/*  76 */     super.write(b);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b) throws IOException
/*     */   {
/*  81 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len) throws IOException
/*     */   {
/*  86 */     this.filePosition += len;
/*  87 */     super.write(b, off, len);
/*     */   }
/*     */ 
/*     */   public void writeLong(long value) throws IOException {
/*  91 */     writeInt((int)value);
/*  92 */     writeInt((int)(value >> 32));
/*     */   }
/*     */ 
/*     */   public static void writeInt(OutputStream out, int value) throws IOException {
/*  96 */     out.write(value);
/*  97 */     out.write(value >> 8);
/*  98 */     out.write(value >> 16);
/*  99 */     out.write(value >> 24);
/*     */   }
/*     */ 
/*     */   public void writeInt(int value) throws IOException {
/* 103 */     writeInt(this, value);
/*     */   }
/*     */ 
/*     */   public void writeShort(int value) throws IOException {
/* 107 */     if ((value < -32768) || (value > 32767)) {
/* 108 */       throw new ExceptionWithContext("Short value out of range: %d", new Object[] { Integer.valueOf(value) });
/*     */     }
/* 110 */     write(value);
/* 111 */     write(value >> 8);
/*     */   }
/*     */ 
/*     */   public void writeUshort(int value) throws IOException {
/* 115 */     if ((value < 0) || (value > 65535)) {
/* 116 */       throw new ExceptionWithContext("Unsigned short value out of range: %d", new Object[] { Integer.valueOf(value) });
/*     */     }
/* 118 */     write(value);
/* 119 */     write(value >> 8);
/*     */   }
/*     */ 
/*     */   public void writeUbyte(int value) throws IOException {
/* 123 */     if ((value < 0) || (value > 255)) {
/* 124 */       throw new ExceptionWithContext("Unsigned byte value out of range: %d", new Object[] { Integer.valueOf(value) });
/*     */     }
/* 126 */     write(value);
/*     */   }
/*     */ 
/*     */   public static void writeUleb128(OutputStream out, int value) throws IOException {
/* 130 */     while (value > 127) {
/* 131 */       out.write(value & 0x7F | 0x80);
/* 132 */       value >>>= 7;
/*     */     }
/* 134 */     out.write(value);
/*     */   }
/*     */ 
/*     */   public void writeUleb128(int value) throws IOException {
/* 138 */     writeUleb128(this, value);
/*     */   }
/*     */ 
/*     */   public static void writeSleb128(OutputStream out, int value) throws IOException {
/* 142 */     if (value >= 0) {
/* 143 */       while (value > 63) {
/* 144 */         out.write(value & 0x7F | 0x80);
/* 145 */         value >>>= 7;
/*     */       }
/* 147 */       out.write(value & 0x7F);
/*     */     } else {
/* 149 */       while (value < -64) {
/* 150 */         out.write(value & 0x7F | 0x80);
/* 151 */         value >>= 7;
/*     */       }
/* 153 */       out.write(value & 0x7F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeSleb128(int value) throws IOException {
/* 158 */     writeSleb128(this, value);
/*     */   }
/*     */ 
/*     */   public void writeEncodedValueHeader(int valueType, int valueArg) throws IOException {
/* 162 */     write(valueType | valueArg << 5);
/*     */   }
/*     */ 
/*     */   public void writeEncodedInt(int valueType, int value) throws IOException {
/* 166 */     int index = 0;
/* 167 */     if (value >= 0) {
/* 168 */       while (value > 127) {
/* 169 */         this.tempBuf[(index++)] = (byte)value;
/* 170 */         value >>= 8;
/*     */       }
/*     */     }
/* 173 */     while (value < -128) {
/* 174 */       this.tempBuf[(index++)] = (byte)value;
/* 175 */       value >>= 8;
/*     */     }
/*     */ 
/* 178 */     this.tempBuf[(index++)] = (byte)value;
/* 179 */     writeEncodedValueHeader(valueType, index - 1);
/* 180 */     write(this.tempBuf, 0, index);
/*     */   }
/*     */ 
/*     */   public void writeEncodedLong(int valueType, long value) throws IOException {
/* 184 */     int index = 0;
/* 185 */     if (value >= 0L) {
/* 186 */       while (value > 127L) {
/* 187 */         this.tempBuf[(index++)] = (byte)(int)value;
/* 188 */         value >>= 8;
/*     */       }
/*     */     }
/* 191 */     while (value < -128L) {
/* 192 */       this.tempBuf[(index++)] = (byte)(int)value;
/* 193 */       value >>= 8;
/*     */     }
/*     */ 
/* 196 */     this.tempBuf[(index++)] = (byte)(int)value;
/* 197 */     writeEncodedValueHeader(valueType, index - 1);
/* 198 */     write(this.tempBuf, 0, index);
/*     */   }
/*     */ 
/*     */   public void writeEncodedUint(int valueType, int value) throws IOException {
/* 202 */     int index = 0;
/*     */     do {
/* 204 */       this.tempBuf[(index++)] = (byte)value;
/* 205 */       value >>>= 8;
/* 206 */     }while (value != 0);
/* 207 */     writeEncodedValueHeader(valueType, index - 1);
/* 208 */     write(this.tempBuf, 0, index);
/*     */   }
/*     */ 
/*     */   public void writeEncodedFloat(int valueType, float value) throws IOException {
/* 212 */     writeRightZeroExtendedInt(valueType, Float.floatToRawIntBits(value));
/*     */   }
/*     */ 
/*     */   protected void writeRightZeroExtendedInt(int valueType, int value) throws IOException {
/* 216 */     int index = 3;
/*     */     do {
/* 218 */       this.tempBuf[(index--)] = (byte)((value & 0xFF000000) >>> 24);
/* 219 */       value <<= 8;
/* 220 */     }while (value != 0);
/*     */ 
/* 222 */     int firstElement = index + 1;
/* 223 */     int encodedLength = 4 - firstElement;
/* 224 */     writeEncodedValueHeader(valueType, encodedLength - 1);
/* 225 */     write(this.tempBuf, firstElement, encodedLength);
/*     */   }
/*     */ 
/*     */   public void writeEncodedDouble(int valueType, double value) throws IOException {
/* 229 */     writeRightZeroExtendedLong(valueType, Double.doubleToRawLongBits(value));
/*     */   }
/*     */ 
/*     */   protected void writeRightZeroExtendedLong(int valueType, long value) throws IOException {
/* 233 */     int index = 7;
/*     */     do {
/* 235 */       this.tempBuf[(index--)] = (byte)(int)((value & 0x0) >>> 56);
/* 236 */       value <<= 8;
/* 237 */     }while (value != 0L);
/*     */ 
/* 239 */     int firstElement = index + 1;
/* 240 */     int encodedLength = 8 - firstElement;
/* 241 */     writeEncodedValueHeader(valueType, encodedLength - 1);
/* 242 */     write(this.tempBuf, firstElement, encodedLength);
/*     */   }
/*     */ 
/*     */   public void writeString(String string) throws IOException {
/* 246 */     int len = string.length();
/*     */ 
/* 249 */     if (this.tempBuf.length <= string.length() * 3) {
/* 250 */       this.tempBuf = new byte[string.length() * 3];
/*     */     }
/*     */ 
/* 253 */     byte[] buf = this.tempBuf;
/*     */ 
/* 255 */     int bufPos = 0;
/* 256 */     for (int i = 0; i < len; i++) {
/* 257 */       char c = string.charAt(i);
/* 258 */       if ((c != 0) && (c < '')) {
/* 259 */         buf[(bufPos++)] = (byte)c;
/* 260 */       } else if (c < 'ࠀ') {
/* 261 */         buf[(bufPos++)] = (byte)(c >> '\006' & 0x1F | 0xC0);
/* 262 */         buf[(bufPos++)] = (byte)(c & 0x3F | 0x80);
/*     */       } else {
/* 264 */         buf[(bufPos++)] = (byte)(c >> '\f' & 0xF | 0xE0);
/* 265 */         buf[(bufPos++)] = (byte)(c >> '\006' & 0x3F | 0x80);
/* 266 */         buf[(bufPos++)] = (byte)(c & 0x3F | 0x80);
/*     */       }
/*     */     }
/* 269 */     write(buf, 0, bufPos);
/*     */   }
/*     */ 
/*     */   public void align() throws IOException {
/* 273 */     int zeros = -getPosition() & 0x3;
/* 274 */     if (zeros > 0)
/* 275 */       write(this.zeroBuf, 0, zeros);
/*     */   }
/*     */ 
/*     */   public int getPosition()
/*     */   {
/* 280 */     return this.filePosition;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.DexDataWriter
 * JD-Core Version:    0.6.0
 */